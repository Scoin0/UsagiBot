package usagibot.osu.api.v2;

import okhttp3.*;
import java.util.List;
import java.time.Instant;
import java.util.Objects;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import usagibot.osu.api.v2.enums.Ruleset;
import usagibot.osu.api.v2.enums.v2Route;
import usagibot.osu.api.v2.user.UserExtended;
import usagibot.osu.api.v2.beatmap.Attributes;
import usagibot.osu.api.v2.beatmap.BeatmapScore;
import usagibot.osu.api.v2.beatmap.BeatmapExtended;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import usagibot.osu.api.v2.beatmap.BeatmapDifficultyAttributes;

@Slf4j
public class OClient {

    private final String clientId;
    private final String clientSecret;
    private String token;
    private Instant tokenExpiry;

    private static final String tokenUrl = "https://osu.ppy.sh/oauth/token";
    private static final String osuApiEndpoint = "https://osu.ppy.sh/api/v2/";
    private static OkHttpClient sharedClient;
    private static ObjectMapper sharedMapper;

    public OClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        refreshToken();
    }

    private static synchronized OkHttpClient getSharedClient() {
        if (sharedClient == null) {
            sharedClient = new OkHttpClient();
        }
        return sharedClient;
    }

    public static synchronized ObjectMapper getSharedMapper() {
        if (sharedMapper == null) {
            sharedMapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
        }
        return sharedMapper;
    }

    private synchronized void refreshToken() {
        log.info("Refreshing OAuth token...");

        ObjectMapper mapper = getSharedMapper();

        ObjectNode body = mapper.createObjectNode();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "client_credentials");
        body.put("scope", "public");

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = getSharedClient().newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            DefaultTokenObject tokenObject = mapper.readValue(responseBody, DefaultTokenObject.class);

            this.token = tokenObject.accessToken();
            this.tokenExpiry = Instant.now().plusSeconds(tokenObject.expiresIn() - 60);
            log.info("OAuth token refreshed. Token: {}", token);
        } catch (IOException e) {
            throw new RuntimeException("Failed to refresh token: ", e);
        }
    }

    private String getValidToken() {
        if (token == null || Instant.now().isAfter(tokenExpiry)) {
            refreshToken();
        }
        return token;
    }

    public <T> T request(Route route, String compiledRoute, Class<T> responseType) {
        return switch (route.method()) {
            case GET -> sendGet(compiledRoute, responseType);
            case POST -> sendPost(compiledRoute, defaultPostBody(), responseType);
        };
    }

    public <T> T request(Route route, String compiledRoute, ObjectNode postBody, Class<T> reponseType) {
        return switch (route.method()) {
            case GET -> sendGet(compiledRoute, reponseType);
            case POST -> sendPost(compiledRoute, postBody, reponseType);
        };
    }

    private ObjectNode defaultPostBody() {
        ObjectMapper mapper = getSharedMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("mods", 0);
        body.put("ruleset", "osu");
        return body;
    }

    private <T> T sendGet(String compiledRoute, Class<T> responseType) {
        Request request = new Request.Builder()
                .url(osuApiEndpoint + compiledRoute)
                .addHeader("Authorization", "Bearer " + getValidToken())
                .build();
        return executeRequest(request, responseType);
    }

    private <T> T sendPost(String compiledRoute, ObjectNode body, Class<T> responseType) {
        Request request = new Request.Builder()
                .url(osuApiEndpoint + compiledRoute)
                .addHeader("Authorization", "Bearer " + getValidToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();
        return executeRequest(request, responseType);
    }

    private <T> T executeRequest(Request request, Class<T> responseType) {
        ObjectMapper mapper = getSharedMapper();
        try (Response response = getSharedClient().newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            System.out.println("RAW: \n" + responseBody);
            return mapper.readValue(responseBody, responseType);
        } catch (IOException e) {
            throw new RuntimeException("API request failed: ", e);
        }
    }

    public BeatmapExtended getBeatmap(String beatmapId) {
        return request(v2Route.BEATMAP.getRoute(), v2Route.BEATMAP.getRoute().compile(beatmapId), BeatmapExtended.class);
    }

    public UserExtended getUser(String userId, Ruleset mode) {
        return request(v2Route.USER.getRoute(), v2Route.USER.getRoute().compile(userId, mode.getName()), UserExtended.class);
    }

    public BeatmapDifficultyAttributes getBeatmapDifficultyAttributes(String beatmapId, ObjectNode body) {
        Attributes response = request(v2Route.BEATMAP_ATTRIBUTES.getRoute(), v2Route.BEATMAP_ATTRIBUTES.getRoute().compile(beatmapId), body, Attributes.class);
        return response.getAttributes();
    }

    public List<Score> getScores(String beatmapId, String user) {
        BeatmapScore response = request(v2Route.USER_BEATMAP_SCORES.getRoute(), v2Route.USER_BEATMAP_SCORES.getRoute().compile(beatmapId, user), BeatmapScore.class);
        return response.getScores();
    }

    public record DefaultTokenObject(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("token_type") String tokenType
    ){}

}