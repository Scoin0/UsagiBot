package usagibot.osu.osutrack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import usagibot.osu.Route;
import usagibot.osu.api.GameMode;
import usagibot.osu.osutrack.api.UpdateUser;

import java.io.IOException;

public class OsuTracker {

    private static final String OSUTRACKER_ENDPOINT = "https://osutrack-api.ameo.dev/";

    public <T> T requestApi(String compiledRoute, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(OSUTRACKER_ENDPOINT + compiledRoute)
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
     * @param tClass        The class object
     * @param <T>           The class object
     * @return              The information from the Osu API
     */
    public <T> T postApi(String compiledRoute, Class<T> tClass) {

        OkHttpClient client = new OkHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();

        Request request = new Request.Builder()
                .url(OSUTRACKER_ENDPOINT + compiledRoute)
                .addHeader("Authorization", "Bearer ")
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
     * Get User information
     * @param userId    The user ID
     * @param mode
     * @return          The information about the user
     */
    public UpdateUser postOsuStats(String userId, GameMode mode) {
        return postApi(Route.UPDATE_USER.compile(userId, String.valueOf(mode.getId())), UpdateUser.class);
    }

}
