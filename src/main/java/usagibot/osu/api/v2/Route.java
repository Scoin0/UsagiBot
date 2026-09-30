package usagibot.osu.api.v2;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public record Route(Method method, String path, int paramsCount) {

    public enum Method {GET, POST}

    public Route(Method method, String path) {
        this(method, path, validateParamCount(path));
    }

    public String compile(String... params) {
        if (params.length != paramsCount) {
            throw new IllegalArgumentException(String.format("Expected %s params %s", paramsCount, path));
        }
        String compiled = path;
        for (String param : params) {
            compiled = compiled.replaceFirst("\\{[^}]+}", param);
        }
        return compiled;
    }

    public String compileWithQuery(String[] pathParams, Map<String, List<String>> queryParams) {
        String base = compile(pathParams);
        if (queryParams == null || queryParams.isEmpty()) return base;

        String query = queryParams.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(value -> entry.getValue() + "=" + value))
                .collect(Collectors.joining("&"));
        return base + "?" + query;
    }

    private static int validateParamCount(String path) {
        long open = path.chars().filter(c -> c == '{').count();
        long close = path.chars().filter(c -> c == '}').count();
        if (open != close) {
            throw new IllegalArgumentException("Unbalanced braces in route: " + path);
        }
        return (int) open;
    }
}