package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserCompletionData {

    /**
     * Ids of the beatmapsets completed by the user (according to the requirements of the pack).
     */
    @JsonProperty("beatmapset_ids")
    private List<Integer> beatmapsetIds;
    /**
     * Whether all beatmapsets are completed or not.
     */
    private boolean completed;

}