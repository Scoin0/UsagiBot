package usagibot.utils.version;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubAPI {

    @JsonProperty("tag_name")
    private String tag_name;
    @JsonProperty("html_url")
    private String html_url;
    @JsonProperty("prerelease")
    private boolean prerelease;
    @JsonProperty("body")
    private String body;

    public String getTag_name() {
        return tag_name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public boolean isPrerelease() {
        return prerelease;
    }

    public String getBody() {
        return body;
    }
}