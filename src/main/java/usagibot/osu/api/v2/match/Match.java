package usagibot.osu.api.v2.match;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Match {

    private int id;
    @JsonProperty("start_time")
    private OffsetDateTime startTime;
    @JsonProperty("end_time")
    private OffsetDateTime endTime;
    private String name;

}