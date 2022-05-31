package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapAttributes {

    @JsonProperty("attributes")
    private Attributes attributes;

    // Attributes
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Attributes {
        // Beatmap Difficulty
        @JsonProperty("max_combo")
        private int max_combo;
        @JsonProperty("star_rating")
        private float star_rating;
        @JsonProperty("approach_rate")
        private float approach_rate;
        @JsonProperty("great_hit_window")
        private float great_hit_window;

        // Standard
        @JsonProperty("aim_difficulty")
        private float aim_difficulty;
        @JsonProperty("flashlight_difficulty")
        private float flashlight_difficulty;
        @JsonProperty("overall_difficulty")
        private float overall_difficulty;
        @JsonProperty("slider_factor")
        private float slider_factor;
        @JsonProperty("speed_difficulty")
        private float speed_difficulty;

        // Taiko
        @JsonProperty("stamina_difficulty")
        private float stamina_difficulty;
        @JsonProperty("rhythm_difficulty")
        private float rhythm_difficulty;
        @JsonProperty("colour_difficulty")
        private float colour_difficulty;

        // Maina
        @JsonProperty("score_multiplier")
        private float score_multiplier;
    }
}
