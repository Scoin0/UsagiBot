package usagibot.osu;

import okhttp3.*;
import lombok.Getter;
import java.util.Locale;
import java.time.Instant;
import java.time.Duration;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import usagibot.osu.api.mod.ModSet;
import usagibot.osu.api.enums.Routes;
import usagibot.osu.api.enums.Ruleset;
import usagibot.osu.api.beatmap.BeatmapExtended;
import usagibot.osu.api.beatmap.helpers.Attributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import usagibot.osu.api.beatmap.BeatmapDifficultyAttributes;
import com.fasterxml.jackson.databind.DeserializationFeature;

@Slf4j
public class OsuClient {

    private final String clientId;
    private final String clientSecret;
    private volatile String token;
    private volatile Instant tokenExpiration = Instant.EPOCH;
    private static final String tokenUrl = "https://osu.ppy.sh/oauth/token";
    private static final String osuAPIEndpoint = "https://osu.ppy.sh/api/v2/";
    private static final MediaType jsonType = MediaType.parse("application/json");
    private static final String defaultPostBody = "{}";

    public OsuClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Getter
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(15))
            .build();

    private String getValidToken() {
        if (token == null || Instant.now().isAfter(tokenExpiration)) {
            refreshToken();
        }
        return token;
    }

    private synchronized void refreshToken() {
        if (token != null && Instant.now().isBefore(tokenExpiration)) return;

        log.info("Refreshing OAuth token...");
        ObjectNode body = mapper.createObjectNode()
                .put("client_id", clientId)
                .put("client_secret", clientSecret)
                .put("grant_type", "client_credentials")
                .put("scope", "public");

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(RequestBody.create(body.toString(), jsonType))
                .build();

        try (Response response = httpClient.newCall(request).execute();
             ResponseBody responseBody = response.body()) {
            if (!response.isSuccessful()) {
                throw new OsuApiException(response.code(), "Token request failed: " + response.code());
            }
            TokenResponse t = mapper.readValue(responseBody.byteStream(), TokenResponse.class);
            this.token = t.accessToken;
            this.tokenExpiration = Instant.now().plusSeconds(t.expiresIn() - 60);
            log.info("Successfully refreshed OAuth token. Expires in: {}", tokenExpiration);
        } catch (IOException e) {
            throw new OsuApiException(-1, "Failed to refresh OAuth token.", e);
        }
    }

    public <T> T request(Route route, String compiledRoute, Class<T> responseType) {
        return switch (route.method()) {
            case GET -> execute(compiledRoute, null, responseType);
            case POST -> execute(compiledRoute, defaultPostBody, responseType);
        };
    }

    public <T> T request(Route route, String compiledRoute, ObjectNode postBody, Class<T> responseType) {
        return switch (route.method()) {
            case GET -> execute(compiledRoute, null, responseType);
            case POST -> execute(compiledRoute, postBody == null ? defaultPostBody : postBody.toString(), responseType);
        };
    }

    private <T> T execute(String compiledRoute, String postJson, Class<T> responseType) {
        for (int attempt = 0; ; attempt++) {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(osuAPIEndpoint + compiledRoute)
                    .header("Authorization", "Bearer " + getValidToken());

            if (postJson != null) {
                requestBuilder.post(RequestBody.create(postJson, jsonType));
            }

            try (Response response = httpClient.newCall(requestBuilder.build()).execute();
                 ResponseBody responseBody = response.body()) {

                if (response.code() == 401 && attempt == 0) {
                    tokenExpiration = Instant.EPOCH;
                    continue;
                }

                if (!response.isSuccessful()) {
                    throw new OsuApiException(response.code(), "osu! API returned " + response.code() + " for " + response.request().url());
                }
                return mapper.readValue(responseBody.byteStream(), responseType);
            } catch (IOException e) {
                throw new OsuApiException(-1, "API request failed: " + compiledRoute, e);
            }
        }
    }

    private <T> T call(Routes r, Class<T> type, String... args) {
        Route route = r.getRoute();
        return request(route, route.compile(args), type);
    }

    public BeatmapExtended getBeatmap(String beatmapId) {
        return call(Routes.BEATMAP,  BeatmapExtended.class, beatmapId);
    }

    public BeatmapDifficultyAttributes getBeatmapDifficultyAttributes(String beatmapId) {
        return fetchBeatmapDifficultyAttributes(beatmapId, null);
    }

    public BeatmapDifficultyAttributes getBeatmapDifficultyAttributes(String beatmapId, Ruleset ruleset) {
        return fetchBeatmapDifficultyAttributes(beatmapId, attributesBody(ruleset, ModSet.NONE));
    }

    public BeatmapDifficultyAttributes getBeatmapDifficultyAttributes(String beatmapId, Ruleset ruleset, int stableMods) {
        return fetchBeatmapDifficultyAttributes(beatmapId, attributesBody(ruleset, ModSet.fromStable(stableMods)));
    }

    public BeatmapDifficultyAttributes getBeatmapDifficultyAttributes(String beatmapId, Ruleset ruleset, ModSet modSet) {
        return fetchBeatmapDifficultyAttributes(beatmapId, attributesBody(ruleset, modSet));
    }

    private BeatmapDifficultyAttributes fetchBeatmapDifficultyAttributes(String beatmapId, ObjectNode body) {
        Route route = Routes.BEATMAP_ATTRIBUTES.getRoute();
        return request(route, route.compile(beatmapId), body, Attributes.class).getAttributes();
    }

    private static ObjectNode attributesBody(Ruleset ruleset, ModSet mods) {
        ObjectNode body = mapper.createObjectNode();
        if (ruleset != null) body.put("ruleset", ruleset.getName().toLowerCase(Locale.ROOT));
        if (mods != null && !mods.isEmpty()) body.set("mods", mods.toJson(mapper));
        return body;
    }

    public record TokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("token_type") String tokenType
    ){}

    public static class OsuApiException extends RuntimeException {

        private final int status;

        public OsuApiException(int status, String message) {
            super(message);
            this.status = status;
        }

        public OsuApiException(int status, String message, Throwable cause) {
            super(message, cause);
            this.status = status;
        }

        public int status() {
            return status;
        }
    }
}