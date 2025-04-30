package usagibot.osu.api.v2.user;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Cover {

    private String customUrl;
    private String url;
    private int id;

}