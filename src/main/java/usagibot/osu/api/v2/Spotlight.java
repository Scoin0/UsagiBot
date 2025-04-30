package usagibot.osu.api.v2;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.v2.enums.Ruleset;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The details of a spotlight.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Spotlight {

    /**
     * The end date of a spotlight.
     */
    @JsonProperty("end_date")
    private OffsetDateTime endDate;
    /**
     * The id of this spotlight.
     */
    private int id;
    /**
     * if the spotlight has different mode specific to each {@link Ruleset}.
     */
    @JsonProperty("mode_specific")
    private boolean modeSpecific;
    /**
     * The number of users participating in this spotlight.
     * This is only shown when viewing a single spotlight.
     */
    @JsonProperty("participant_count")
    private Integer participantCount;
    /**
     * The name of the spotlight.
     */
    private String name;
    /**
     * The starting date of the spotlight.
     */
    @JsonProperty("start_date")
    private OffsetDateTime startDate;
    /**
     * The type of spotlight.
     */
    private String type;

}