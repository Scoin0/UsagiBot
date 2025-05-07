package usagibot.osu.api.v2.enums;

import lombok.Getter;
import usagibot.osu.api.v2.Route;

@Getter
public enum v2Route {

    BEATMAP(new Route(Route.Method.GET, "beatmaps/{beatmap_id}")),
    BEATMAPS(new Route(Route.Method.GET, "beatmaps")),
    BEATMAP_ATTRIBUTES(new Route(Route.Method.POST, "beatmaps/{beatmap_id}/attributes")),
    BEATMAP_SCORES(new Route(Route.Method.GET, "beatmaps/{beatmap_id}/scores")),
    BEATMAP_SOLO_SCORES(new Route(Route.Method.GET, "beatmaps/{beatmap_id}/solo-scores")),
    USER_BEATMAP_SCORE(new Route(Route.Method.GET, "beatmaps/{beatmap_id}/scores/users/{user}")),
    USER_BEATMAP_SCORES(new Route(Route.Method.GET, "beatmaps/{beatmap_id}/scores/users/{user}/all")),
    USER(new Route(Route.Method.GET, "users/{user}/{mode}"));

    private final Route route;

    v2Route(Route route) {
        this.route = route;
    }
}