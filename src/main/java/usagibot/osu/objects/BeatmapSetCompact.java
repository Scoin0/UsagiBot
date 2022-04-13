package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapSetCompact {

    // Represents a
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("artist_unicode")
    private String artist_unicode;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("favourite_count")
    private int favourite_count;
    @JsonProperty("id")
    private int id;
    @JsonProperty("nsfw")
    private boolean nsfw;
    @JsonProperty("play_count")
    private int play_count;
    @JsonProperty("preview_url")
    private String preview_url;
    @JsonProperty("source")
    private String source;
    @JsonProperty("status")
    private String status;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_unicode")
    private String title_unicode;
    @JsonProperty("user_id")
    private String user_id;
    @JsonProperty("video")
    private boolean video;

}
