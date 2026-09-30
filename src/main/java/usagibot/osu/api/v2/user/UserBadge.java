package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserBadge {

    @JsonProperty("awarded_at")
    private OffsetDateTime awardedAt;
    private String description;
    @JsonProperty("image@2x_url")
    private String image2xUrl;
    @JsonProperty("image_url")
    private String imageUrl;
    private String url;

}