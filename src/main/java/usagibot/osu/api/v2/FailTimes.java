package usagibot.osu.api.v2;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * All fields are optional but there's always at least one field returned.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class FailTimes {

    /**
     * Array of length 100.
     */
    @JsonProperty("exit")
    private List<Integer> exit;
    /**
     * Array of length 100.
     */
    @JsonProperty("fail")
    private List<Integer> fail;

}