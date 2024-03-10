package usagibot.osu.memreaders.rosu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Beatmap {

    @JsonProperty("artist")
    public String artist;
    @JsonProperty("title")
    public String title;
    @JsonProperty("creator")
    public String creator;
    @JsonProperty("difficulty")
    public String difficulty;
    @JsonProperty("map_id")
    public long mapId;
    @JsonProperty("mapset_id")
    public long mapSetId;
    @JsonProperty("ar")
    public float ar;
    @JsonProperty("cs")
    public float cs;
    @JsonProperty("hp")
    public float hp;
    @JsonProperty("od")
    public float od;
    @JsonProperty("beatmap_status")
    public int beatmapStatus;
    @JsonProperty("last_obj_time")
    public long lastObjectTime;
    @JsonProperty("first_obj_time")
    public long firstObjectTime;
    @JsonProperty("bpm")
    public float bpm;
    // Ignore paths
}