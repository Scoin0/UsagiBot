package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RankHighest {

    private int rank;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

}