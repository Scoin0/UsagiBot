package usagibot.osu;

public class Route {

    /* ENDPOINTS */
    public static final Route BEATMAP = new Route(Method.GET, "beatmaps/{beatmap_id}");
    public static final Route BEATMAP_ATTRIBUTES = new Route(Method.POST, "beatmaps/{beatmap_id}/attributes");
    public static final Route USER = new Route(Method.GET, "users/{user}/{mode}");
    public static final Route UPDATE_USER = new Route(Method.POST, "update?user={user}&mode={mode}");
    public static final Route ALL_STAT_UPDATES = new Route(Method.GET, "stats_history?user={user}&mode={mode}&from={from}&to={to}");
    public static final Route HIGHSCORES = new Route(Method.GET, "hiscores?user={user}&mode={mode}&from={from}&to={to}");
    public static final Route PEAK = new Route(Method.GET, "peak?user={user}&mode={mode}");
    public static final Route BEST_PLAYS = new Route(Method.GET, "bestplays?mode={mode}&from={from}&to={to}&limit={limit}");

    private Method method;
    private String route;
    private int paramsCount;

    public enum Method {
        GET,
        POST
    }

    /**
     * The Route Constructor
     * @param method    The type of method we're using to get information
     * @param route     The endpoint url
     */
    public Route(Method method, String route) {
        this.method = method;
        this.route = route;
        this.paramsCount = (int) route.chars()
                .filter(i -> i == '{')
                .count();
        if (route.chars().filter(i -> i== '}').count() != paramsCount)
            throw new IllegalArgumentException("Error occurred with " + route);
    }

    /**
     * Compiling the route url to get the information we need
     * @param params    The parameters for the url
     * @return          The formatted url
     */
    public String compile(String... params) {
        if (params.length != paramsCount)
            throw new IllegalArgumentException(String.format("Expected %s params: %s", paramsCount, route));

        StringBuilder compiledRoute = new StringBuilder(route);

        for (String param : params)
            compiledRoute.replace(
                    compiledRoute.indexOf("{"),
                    compiledRoute.indexOf("}") + 1,
                    param
            );
        return compiledRoute.toString();
    }

}
