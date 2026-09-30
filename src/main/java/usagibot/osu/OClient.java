package usagibot.osu;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class OClient {

    private final String clientId;
    private final String clientSecret;
    private String token;
    private Instant tokenExpiration;

    private static OkHttpClient client;
    private static ObjectMapper sharedMapper;

    private static final String tokenUrl = "https://osu.ppy.sh/oauth/token";
    private static final String osuAPIEndpoint = "https://osu.ppy.sh/api/v2";

    public OClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public record DefaultTokenObject(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("token_type") String tokenType
    ){}
}
