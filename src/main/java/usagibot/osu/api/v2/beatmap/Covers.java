package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Covers {

    private String cover;
    @JsonProperty("cover@2x")
    private String cover2x;
    private String card;
    @JsonProperty("card@2x")
    private String card2x;
    private String list;
    @JsonProperty("list@2x")
    private String list2x;
    @JsonProperty("slim_cover")
    private String slimCover;
    @JsonProperty("slimcover@2x")
    private String slimCover2x;

}