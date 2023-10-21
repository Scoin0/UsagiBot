package usagibot.osu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.BeatmapAttributes;
import usagibot.osu.api.GameMode;
import usagibot.osu.api.User;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@Slf4j
public class OsuClient {

    private String token;
    private int tokenTimeout;
    private static final String TOKEN_URL = "https://osu.ppy.sh/oauth/token";
    private static final String OSU_ENDPOINT = "https://osu.ppy.sh/api/v2/";
    private static OkHttpClient sharedClient;

    /**
     * The Osu Client constructor
     * @param token         The Osu API token
     * @param tokenTimeout  The expiry date of the token
     */
    public OsuClient(String token, int tokenTimeout) {
        this.token = token;
        this.tokenTimeout = tokenTimeout;
    }

    public static synchronized OkHttpClient getSharedClient() {
        if (sharedClient == null) {
            sharedClient = new OkHttpClient();
        }
        return sharedClient;
    }

    /**
     * Create the Osu API client
     * @param clientID      The OAuth client ID
     * @param clientSecret  The OAuth client password
     * @return              The Osu API token and the expiry date
     */
    public static OsuClient createClient(String clientID, String clientSecret) {
        log.info("Retrieving token with client credentials...");
        OkHttpClient client = getSharedClient();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "client_credentials");
        body.put("scope", "public");

        RequestBody requestBody = RequestBody.create(body.toString(), MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            DefaultTokenObject tokenObject = new JsonMapper().readValue(responseBody, DefaultTokenObject.class);
            log.info("Token retrieved!");
            return new OsuClient(tokenObject.getAccessToken(), tokenObject.getExpiresIn());
        } catch (IOException e) {
            log.error("Unable to retrieve token. Is the configuration set up properly?", e);
        }
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
        OkHttpClient client = getSharedClient();

        Request request = new Request.Builder()
                .url(OSU_ENDPOINT + compiledRoute)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("mods", "0");
        body.put("ruleset", "osu");

        return executeHttpPost(compiledRoute, token, body, tClass);
    }

    public <T> T postApi(String compiledRoute, String token, GameMode mode, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("mods", "0");
        body.put("ruleset", mode.getName().toLowerCase(Locale.ROOT));

        return executeHttpPost(compiledRoute, token, body, tClass);
    }

    public <T> T postApi(String compiledRoute, String token, GameMode mode, int mods, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("mods", mods);
        body.put("ruleset", mode.getName().toLowerCase(Locale.ROOT));

        return executeHttpPost(compiledRoute, token, body, tClass);
    }

    private <T> T executeHttpPost(String compiledRoute, String token, ObjectNode body, Class<T> tClass) {
        OkHttpClient client = getSharedClient();

        Request request = new Request.Builder()
                .url(OSU_ENDPOINT + compiledRoute)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
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

    /**
     * Get BeatmapAttributes information
     * @param beatmapId The information about the beatmap
     * @return          The beatmap attributes
     */
    public BeatmapAttributes getBeatmapAttributes(String beatmapId, GameMode mode) {
        return postApi(Route.BEATMAP_ATTRIBUTES.compile(beatmapId), token, mode, BeatmapAttributes.class);
    }

    /**
     * Get BeatmapAttributes information
     * @param beatmapId The information about the beatmap
     * @return          The beatmap attributes
     */
    public BeatmapAttributes getBeatmapAttributes(String beatmapId, GameMode mode, int mods) {
        return postApi(Route.BEATMAP_ATTRIBUTES.compile(beatmapId), token, mode, mods, BeatmapAttributes.class);
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