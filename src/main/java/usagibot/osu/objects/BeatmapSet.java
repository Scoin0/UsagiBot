package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapSet extends BeatmapSetCompact {

    //Represents a beatmapset
    @JsonProperty("availability.download_disabled")
    private boolean download_disabled;
    @JsonProperty("availability.more_information")
    private String[] more_information;
    @JsonProperty("bpm")
    private float bpm;
    @JsonProperty("can_be_hyped")
    private boolean can_be_hyped;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("discussion_enabled")
    private boolean discussion_enabled;
    @JsonProperty("discussion_locked")
    private boolean discussion_locked;
    @JsonProperty("hype.current")
    private int hype_current;
    @JsonProperty("hype.required")
    private int hype_required;
    @JsonProperty("is_scorable")
    private boolean is_scorable;
    @JsonProperty("last_updated")
    private Timestamp last_updated;
    @JsonProperty("ranked_date")
    private Timestamp ranked_date;
    @JsonProperty("source")
    private String source;
    @JsonProperty("storyboard")
    private boolean storyboard;
    @JsonProperty("tags")
    private String tags;


}
