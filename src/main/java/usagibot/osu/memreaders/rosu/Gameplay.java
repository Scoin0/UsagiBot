package usagibot.osu.memreaders.rosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Gameplay {

    @JsonProperty("mods")
    public long mods;
    @JsonProperty("username")
    public String username;
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
    @JsonProperty("combo")
    public int combo;
    @JsonProperty("max_combo")
    public int maxCombo;
    @JsonProperty("mode")
    public int mode;
    @JsonProperty("slider_breaks")
    public int sliderBreaks;
    @JsonProperty("unstable_rate")
    public float unstableRate;
    @JsonProperty("passed_objects")
    public int passedObjects;
    @JsonProperty("grade")
    public String grade;
    @JsonProperty("current_hp")
    public int currentHP;
    @JsonProperty("current_hp_smooth")
    public int currentHPSmooth;

}