package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Beatmap extends BeatmapCompact {

    // Represent a Beatmap
    @JsonProperty("accuracy")
    private float accuracy;
    @JsonProperty("ar")
    private float ar;
    @JsonProperty("beatmapset_id")
    private int beatmapset_id;
    @JsonProperty("bpm")
    private float bpm;
    @JsonProperty("convert")
    private boolean convert;
    @JsonProperty("count_circles")
    private int count_circles;
    @JsonProperty("count_sliders")
    private int count_sliders;
    @JsonProperty("count_spinners")
    private int count_spinners;
    @JsonProperty("cs")
    private float cs;
    @JsonProperty("deleted_at")
    private Timestamp deleted_at;
    @JsonProperty("drain")
    private float drain;
    @JsonProperty("hit_length")
    private int hit_length;
    @JsonProperty("is_scoreable")
    private boolean is_scoreable;
    @JsonProperty("last_updated")
    private Timestamp last_updated;
    @JsonProperty("mode_int")
    private int mode_int;
    @JsonProperty("passcount")
    private int passcount;
    @JsonProperty("playcount")
    private int playcount;
    //@JsonProperty("ranked")
    //private RankedStatus ranked;
    @JsonProperty("url")
    private String url;
    @JsonProperty("title")
    private String title;
}
