package software.plusminus.authentication.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenPlace {

    private String headersKey = "Authorization";
    private String cookiesKey;

}
