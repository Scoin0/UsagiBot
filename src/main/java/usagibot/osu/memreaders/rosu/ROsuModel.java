package usagibot.osu.memreaders.rosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Follows the JSON schema (As of March 10th, 2024)
 * URL:         https://github.com/486c/rosu-memory
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ROsuModel {

    @JsonProperty("skin")
    public String skin;
    @JsonProperty("playtime")
    public int playTime;
    @JsonProperty("menu_mode")
    public int menuMode;
    @JsonProperty("state")
    public int state;
    @JsonProperty("stars")
    public float stars;
    @JsonProperty("star_mods")
    public float starMods;
    @JsonProperty("current_stars")
    public float currentStars;
    @JsonProperty("results_screen")
    public ResultsScreen resultsScreen;
    @JsonProperty("gameplay")
    public Gameplay gameplay;
    @JsonProperty("beatmap")
    public Beatmap beatmap;
    @JsonProperty("current_bpm")
    public float currentBPM;
    @JsonProperty("kiai_now")
    public boolean kiaiNow;
    @JsonProperty("current_pp")
    public float currentPP;
    @JsonProperty("fc_pp")
    public float fcPP;
    @JsonProperty("ss_pp")
    public float ssPP;
    @JsonProperty("menu_mods")
    public long mods;
    @JsonProperty("mods_str")
    public String[] modsString;
    @JsonProperty("plays")
    public int plays;
    // Ignore Key Overlay

}