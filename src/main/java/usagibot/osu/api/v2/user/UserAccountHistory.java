package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserAccountHistory {

    private String description;
    private int id;
    /**
     * In seconds.
     */
    private int length;
    private boolean permanent;
    private OffsetDateTime timestamp;
    /**
     * {@code note}, {@code restriction}, or {@code silence}.
     */
    private String type;

}