package software.plusminus.authentication.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import software.plusminus.authentication.model.TokenPlace;

import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
        if (tokenPlace.getHeadersKey() == null) {
            return null;
        }
        return request.getHeader(tokenPlace.getHeadersKey());
    }

    @Nullable
    private String getFromCookies(TokenPlace tokenPlace, HttpServletRequest request) {
        if (tokenPlace.getCookiesKey() == null
                || request.getCookies() == null) {
            return null;
        }
        return Stream.of(request.getCookies())
                .filter(c -> tokenPlace.getCookiesKey().equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
