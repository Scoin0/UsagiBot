package usagibot.osu;

public class Route {

    /* ENDPOINT */

    public static final Route BEATMAP = new Route(Method.GET, "beatmaps/{beatmap_id}");
    public static final Route USER = new Route(Method.GET, "users/{user}/{mode}");

    private Method method;
    private String route;
    private int paramsCount;

    public Route(Method method, String route) {
        this.method = method;
        this.route = route;
        this.paramsCount = (int) route.chars()
                .filter(i -> i == '{')
                .count();
        if (route.chars().filter(i -> i== '}').count() != paramsCount)
            throw new IllegalArgumentException("Error occurred with " + route);
    }

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

    public Method getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public int getParamsCount() {
        return paramsCount;
    }

    public enum Method {
        GET
    }
}
