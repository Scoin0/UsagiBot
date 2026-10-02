package usagibot;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import usagibot.osu.OsuClient;
import usagibot.osu.Route;
import usagibot.osu.api.enums.Ruleset;
import usagibot.osu.api.mod.ModSet;
import usagibot.utilities.Constants;

import java.util.Arrays;

@Slf4j
public class UsagiBot {

    public static String clientId = "Oh no you don't";
    public static String clientSecret = "Oh no you don't";

    public static void main(String[] args) {
        System.out.println(Constants.logo);
        log.info("Welcome to UsagiBot.");

        OsuClient client = new OsuClient(clientId, clientSecret);
        if (args.length >= 2) {
            run(client, Route.Method.valueOf(args[0].toUpperCase()), args[1],
                    Arrays.copyOfRange(args, 2, args.length));
            return;
        }
        log.info("{}", client.getBeatmapDifficultyAttributes("5855215", Ruleset.OSU, ModSet.parse("HDHRDT").orElseThrow()).getStarRating());
    }

    private static void run(OsuClient client, Route.Method method, String template, String... params) {
        Route route = new Route(method, template);
        String compiled = route.compile(params);
        System.out.println("\n=== " + method + " " + compiled + " ===");
        try {
            JsonNode json = client.request(route, compiled, JsonNode.class);
            System.out.println(OsuClient.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json));
        } catch (OsuClient.OsuApiException e) {
            System.out.println("FAILED (HTTP " + e.status() + "): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}