package usagibot.osu.enums;

import lombok.Getter;
import usagibot.osu.Route;

@Getter
public enum Routes {

    BEATMAP(new Route(Route.Method.GET, "beatmaps/{beatmap_id}")),
    BEATMAPS(new Route(Route.Method.GET, "beatmaps")),
    BEATMAP_ATTRIBUTES(new Route(Route.Method.POST, "beatmaps/{beatmap_id}/attributes"));

    private final Route route;

    Routes(Route route) {
        this.route = route;
    }
}