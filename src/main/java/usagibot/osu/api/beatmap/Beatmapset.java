package usagibot.osu.api.beatmap;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.beatmap.helpers.Covers;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmapset.
 * @link <a href=https://osu.ppy.sh/docs/#beatmapset>#beatmapset</a>
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
    private String userId;
    private boolean video;

    // Optional Fields
    private BeatmapExtended beatmaps;
    @JsonProperty("has_favourite")
    private boolean hasFavourited;
    @JsonProperty("track_id")
    private int trackId;
}