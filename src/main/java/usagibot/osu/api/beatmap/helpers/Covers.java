package usagibot.osu.api.beatmap.helpers;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @see <a href=https://osu.ppy.sh/docs/#beatmapset-covers>#beatmapset-covers</a>
 */

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
    @JsonProperty("slimcover")
    private String slimCover;
    @JsonProperty("slimcover@2x")
    private String slimCover2x;

}