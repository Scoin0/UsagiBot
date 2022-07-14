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

import java.io.IOException;

@Slf4j
public class OsuClient {

    private String token;
    private int tokenTimeout;
    private static final String TOKEN_URL = "https://osu.ppy.sh/oauth/token";
    private static final String OSU_ENDPOINT = "https://osu.ppy.sh/api/v2/";

    /**
     * The Osu Client constructor
     * @param token         The Osu API token
     * @param tokenTimeout  The expiry date of the token
     */
    public OsuClient(String token, int tokenTimeout) {
        this.token = token;
        this.tokenTimeout = tokenTimeout;
    }

    /**
     * Create the Osu API client
     * @param clientID      The OAuth client ID
     * @param clientSecret  The OAuth client password
     * @return              The Osu API token and the expiry date
     */
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

    /**
     * Sends a GET to the Osu API
     * @param compiledRoute The compiled url
     * @param token         The compiled url
     * @param tClass        The class object
     * @param <T>           The class object
     * @return              The information from the Osu API
     */
    public <T> T requestApi(String compiledRoute, String token, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();

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

    /**
     * Sends a POST to the OSU API
     * @param compiledRoute The compiled url
     * @param token         The compiled url
     * @param tClass        The class object
     * @param <T>           The class object
     * @return              The information from the Osu API
     */
    public <T> T postApi(String compiledRoute, String token, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();

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

    /**
     * Get Beatmap information
     * @param beatmapId The beatmap ID
     * @return          The information about the beatmap
     */
    public Beatmap getBeatmap(String beatmapId) {
        return requestApi(Route.BEATMAP.compile(beatmapId), token, Beatmap.class);
    }

    /**
     * Get User information
     * @param userId    The user ID
     * @param mode      The gamemode that's requested
     * @return          The information about the user
     */
    public User getUser(String userId, GameMode mode) {
        return requestApi(Route.USER.compile(userId, mode.getName()), token, User.class);
    }

    /**
     * Get BeatmapAttributes information
     * @param beatmapId The information about the beatmap
     * @return          The beatmap attributes
     */
    public BeatmapAttributes getBeatmapAttributes(String beatmapId) {
        return postApi(Route.BEATMAP_ATTRIBUTES.compile(beatmapId), token, BeatmapAttributes.class);
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
