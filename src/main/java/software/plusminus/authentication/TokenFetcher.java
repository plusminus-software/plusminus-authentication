package software.plusminus.authentication;

import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TokenFetcher {

    @Nullable
    public String fetchToken(TokenPlace tokenPlace, HttpServletRequest request) {
        String fromHeader = getFromHeader(tokenPlace, request);
        if (fromHeader != null) {
            return fromHeader;
        }
        String fromCookies = getFromCookies(tokenPlace, request);
        if (fromCookies != null) {
            return fromCookies;
        }
        return null;
    }

    @Nullable
    private String getFromHeader(TokenPlace tokenPlace, HttpServletRequest request) {
        if (!tokenPlace.isInHeaders()) {
            return null;
        }
        return request.getHeader(tokenPlace.getKey());
    }

    @Nullable
    private String getFromCookies(TokenPlace tokenPlace, HttpServletRequest request) {
        if (!tokenPlace.isInCookies()) {
            return null;
        }
        return Stream.of(request.getCookies())
                .filter(c -> tokenPlace.getKey().equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
