package usagibot.osu.enums;

import lombok.Getter;
import usagibot.osu.Route;

@Getter
public enum Routes {

    BEATMAP(new Route(Route.Method.GET, "beatmaps/{beatmap_id}"));

    private final Route route;

    Routes(Route route) {
        this.route = route;
    }
}