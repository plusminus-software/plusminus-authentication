package software.plusminus.authentication.service.token;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.plusminus.context.Context;
import software.plusminus.security.CookieKey;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Order(2)
@AllArgsConstructor
@Component
public class HttpCookiesTokenFinder implements TokenFinder {

    private Context<HttpServletRequest> httpServletRequestContext;
    private List<CookieKey> cookieKeys;

    @Nullable
    @Override
    public String findToken() {
        HttpServletRequest request = httpServletRequestContext.get();
        if (request.getCookies() == null) {
            return null;
        }
        return Stream.of(request.getCookies())
                .filter(c -> cookieKeys.stream()
                        .map(CookieKey::cookieKey)
                        .anyMatch(cookieKey -> cookieKey != null && cookieKey.equals(c.getName())))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
