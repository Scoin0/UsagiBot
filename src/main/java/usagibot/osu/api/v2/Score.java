package usagibot.osu.api.v2;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

/**
 * The following is the format returned when API v2 version header is 20220705 or higher.
 * Exceptions apply (f.ex. doesn't apply for legacy match score).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Score {

    private float accuracy;
    @JsonProperty("beatmap_id")
    private float beatmapId;
    @JsonProperty("best_id")
    private Integer bestId;
    @JsonProperty("build_id")
    private Integer buildId;
    /**
     * Only for {@code solo_score} type.
     */
    @JsonProperty("classicTotalScore")
    private int classicTotalScore;
    @JsonProperty("ended_at")
    private OffsetDateTime endedAt;
    @JsonProperty("has_replay")
    private boolean hasReplay;
    private int id;
    @JsonProperty("is_perfect_combo")
    private boolean isPerfectCombo;
    @JsonProperty("legacy_perfect")
    private boolean legacyPerfect;
    @JsonProperty("legacy_score_id")
    private Integer legacyScoreId;
    @JsonProperty("legacy_total_score")
    private int legacyTotalScore;
    @JsonProperty("max_combo")
    private int maxCombo;
    @JsonProperty("maximum_statistics")
    private ScoreStatistics maximumStatistics;
    private List<Mods> mods;
    private boolean passed;
    /**
     * Only for multiplayer score
     */
    @JsonProperty("playlist_item_id")
    private int playlistItemId;
    private Float pp;
    /**
     * Whether the score may eventually be deleted. Only for solo_score type
     */
    private boolean preserve;
    /**
     * Only for {@code solo_score} type.
     */
    private boolean processed;
    private String rank;
    /**
     * Whether the score can have pp. Only for {@code solos_score} type.
     */
    private boolean ranked;
    /**
     * Only for multiplayer score.
     */
    @JsonProperty("room_id")
    private int roomId;
    @JsonProperty("ruleset_id")
    private int rulesetId;
    @JsonProperty("started_at")
    private OffsetDateTime startedAt;
    @JsonProperty("total_score")
    private int totalScore;
    private String type;
    @JsonProperty("userId")
    private int userId;

    // Initial Version: The following is the format returned when API v2 header is 20220704 or lower.
    private Statistics statistics;

    // Optional Attributes
    private Beatmap beatmap;
    private Beatmapset beatmapset;
    @JsonProperty("current_user_attributes")
    private Integer currentUserAttributes;
    private Integer position;

}