package usagibot.osu.api.v2;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Availability {

    @JsonProperty("download_disabled")
    private boolean downloadDisabled;
    @JsonProperty("more_information")
    private String moreInformation;

}