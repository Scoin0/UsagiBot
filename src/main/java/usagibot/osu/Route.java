package usagibot.osu;


import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.net.URLEncoder;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

public record Route(Method method, String path) {

    public enum Method { GET, POST }

    private static final Pattern param = Pattern.compile("\\{[^}]+}");

    public Route {
        Objects.requireNonNull(method, "method");
        Objects.requireNonNull(path, "path");
        long braces = path.chars().filter(c -> c == '{' || c == '}').count();
        if (braces != countParams(path) * 2L) {
            throw new IllegalArgumentException(String.format("Invalid route parameters '%s'", path));
        }
    }

    public int paramsCount() {
        return countParams(path);
    }

    public String compile(String... params) {
        Matcher matcher = param.matcher(path);
        StringBuilder compiled = new StringBuilder(path.length() + 16);
        int i = 0;
        while (matcher.find()) {
            if (i == params.length) throw mismatch(params.length);
            matcher.appendReplacement(compiled, Matcher.quoteReplacement(encode(params[i++])));
        }
        if (i != params.length) throw mismatch(params.length);
        matcher.appendTail(compiled);
        return compiled.toString();
    }

    public String compileWithQuery(String[] pathParams, Map<String, List<String>> queryParams) {
        String base = compile(pathParams);
        if (queryParams == null || queryParams.isEmpty()) return base;

        StringJoiner query = new StringJoiner("&");
        queryParams.forEach((key, values) -> {
            for (String value : values) {
                query.add(encode(key) + "=" + encode(value));
            }
        });
        return query.length() == 0 ? base : base + "?" + query;
    }

    private IllegalArgumentException mismatch(int given) {
        return new IllegalArgumentException(String.format("Route '%s' expects %d params but got %d", path, paramsCount(), given));
    }

    private static int countParams(String path) {
        Matcher m = param.matcher(path);
        int count = 0;
        while (m.find()) count++;
        return count;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }
}