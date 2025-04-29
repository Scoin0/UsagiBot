package usagibot.osu.api.v2;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmapset.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Beatmapset {

    private String artist;
    @JsonProperty("artist_unicode")
    private String artistUnicode;
    private Covers covers;
    private String creator;
    @JsonProperty("favourite_count")
    private int favouriteCount;
    private int id;
    private boolean nsfw;
    private int offset;
    @JsonProperty("play_count")
    private int playCount;
    @JsonProperty("preview_url")
    private String previewUrl;
    private String source;
    private String status;
    private boolean spotlight;
    private String title;
    @JsonProperty("title_unicode")
    private String titleUnicode;
    @JsonProperty("user_id")
    private int userId;
    private boolean video;

    private List<Beatmap> beatmaps;
    @JsonProperty("current_nominations")
    private List<Nomination> currentNominations;
    @JsonProperty("has_favourited")
    private boolean hasFavourited;
    @JsonProperty("pack_tags")
    private List<String> packTags;
    @JsonProperty("track_id")
    private int trackId;

}