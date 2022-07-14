package usagibot.osu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import usagibot.osu.objects.Beatmap;
import usagibot.osu.objects.BeatmapAttributes;
import usagibot.osu.objects.GameMode;
import usagibot.osu.objects.User;
import usagibot.utils.RateLimit;

import java.io.IOException;

@Slf4j
public class OsuClient {

    private String token;
    private int tokenTimeout;
    private static RateLimit limit = new RateLimit(60); // Only allow for 60 requests per minute
    private static final String TOKEN_URL = "https://osu.ppy.sh/oauth/token";
    private static final String OSU_ENDPOINT = "https://osu.ppy.sh/api/v2/";

    public OsuClient(String token, int tokenTimeout) {
        this.token = token;
        this.tokenTimeout = tokenTimeout;
    }

    public static OsuClient createClient(String clientID, String clientSecret) {
        log.info("Retrieving token with client credentials...");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "client_credentials");
        body.put("scope", "public");

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            DefaultTokenObject tokenObject = new JsonMapper().readValue(responseBody, DefaultTokenObject.class);
            log.info("Token retrieved!");
            return new OsuClient(tokenObject.getAccessToken(), tokenObject.getExpiresIn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Unable to retrieve token. Is the configuration set up properly?");
        return null;
    }

    public <T> T requestApi(String compiledRoute, String token, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();
        waitForFreeTicket();

        Request request = new Request.Builder()
                .url(OSU_ENDPOINT + compiledRoute)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            return new JsonMapper().readValue(body, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T postApi(String compiledRoute, String token, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();
        waitForFreeTicket();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("mods", "0");
        body.put("ruleset", "osu");

        Request request = new Request.Builder()
                .url(OSU_ENDPOINT + compiledRoute)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return new JsonMapper().readValue(responseBody, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Beatmap getBeatmap(String beatmapId) {
        return requestApi(Route.BEATMAP.compile(beatmapId), token, Beatmap.class);
    }
    public User getUser(String userId, GameMode mode) {
        return requestApi(Route.USER.compile(userId, mode.getName()), token, User.class);
    }

    public BeatmapAttributes getBeatmapAttributes(String beatmapId) {
        return postApi(Route.BEATMAP_ATTRIBUTES.compile(beatmapId), token, BeatmapAttributes.class);
    }

    private void waitForFreeTicket() {
        limit.getOrWaitForTicket();
    }

    public static class DefaultTokenObject {

        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private int expiresIn;
        @JsonProperty("token_type")
        private String tokenType;

        public String getAccessToken() {
            return accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public String getTokenType() {
            return tokenType;
        }

    }
}
