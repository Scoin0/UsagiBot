package usagibot.osu.memreaders.tosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserProfile {

    @JsonProperty("name")
    public String name;
    @JsonProperty("accuracy")
    public float accuracy;
    @JsonProperty("rankedScore")
    public long rankedScore;
    @JsonProperty("id")
    public long id;
    @JsonProperty("level")
    public float level;
    @JsonProperty("playCount")
    public int playCount;
    @JsonProperty("playMode")
    public int playMode;
    @JsonProperty("rank")
    public int rank;
    @JsonProperty("countryCode")
    public int countryCode;
    @JsonProperty("performancePoints")
    public float performancePoints;
    @JsonProperty("isConnected")
    public boolean isConnected;
    @JsonProperty("backgroundColour")
    public String backgroundColor;

}