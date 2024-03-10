package usagibot.osu.memreaders.rosu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsScreen {

    @JsonProperty("username")
    public String username;
    @JsonProperty("mods")
    public long mods;
    @JsonProperty("mode")
    public int mode;
    @JsonProperty("max_combo")
    public int maxCombo;
    @JsonProperty("score")
    public int score;
    @JsonProperty("hit_300")
    public int hit300;
    @JsonProperty("hit_100")
    public int hit100;
    @JsonProperty("hit_50")
    public int hit50;
    @JsonProperty("hit_geki")
    public int hitGeki;
    @JsonProperty("hit_katu")
    public int hitKatu;
    @JsonProperty("hit_miss")
    public int hitMiss;
    @JsonProperty("accuracy")
    public int accuracy;

}