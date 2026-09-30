package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class GradeCounts {

    /**
     * Number of A ranked scores.
     */
    private int a;
    /**
     * Number of S ranked scores.
     */
    private int s;
    /**
     * Number of Silver S ranked scores.
     */
    private int sh;
    /**
     * Number of SS ranked scores.
     */
    private int ss;
    /**
     * Number of Silver SS ranked scores.
     */
    private int ssh;

}