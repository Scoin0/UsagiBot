package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent beatmap difficulty attributes.
 * Following fields are always present and then there are additional fields for different rulesets.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapDifficultyAttributes {

    @JsonProperty("star_rating")
    private float starRating;
    @JsonProperty("max_combo")
    private int maxCombo;

    //Osu
    @JsonProperty("aim_difficulty")
    private float aimDifficulty;
    @JsonProperty("aim_difficult_slider_count")
    private float aimSliderCount;
    @JsonProperty("speed_difficulty")
    private float speedDifficulty;
    @JsonProperty("speed_note_count")
    private float speedNoteCount;
    @JsonProperty("slider_factor")
    private float sliderFactor;
    @JsonProperty("aim_difficult_strain_count")
    private float aimStrainCount;
    @JsonProperty("speed_difficult_strain_count")
    private float speedStrainCount;

    //Taiko
    @JsonProperty("mono_stamina_factor")
    private float monoStaminaFactor;

}