package usagibot.osu.api.v2;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a wiki article.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WikiPage {

    /**
     * All available locales for the article.
     */
    @JsonProperty("available_locales")
    private List<String> availableLocales;
    /**
     * The layout type for the page.
     */
    private String layout;
    /**
     * All lowercase BCP 47 language tag.
     */
    private String locale;
    /**
     * Markdown content.
     */
    private String markdown;
    /**
     * Path of the article.
     */
    private String path;
    /**
     * The article's subtitle.
     */
    private String subtitle;
    /**
     * Associated tags for the article.
     */
    private List<String> tags;
    /**
     * The article's title.
     */
    private String title;

}