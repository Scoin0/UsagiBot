package usagibot.osu.api.v2.user;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ProfileBanner {

    private int id;
    @JsonProperty("tournament_id")
    private int tournamentId;
    private String image;
    @JsonProperty("image@2x")
    private String image2x;

}