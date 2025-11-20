package software.plusminus.authentication.service.token;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.plusminus.context.Context;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Order(2)
@AllArgsConstructor
@Component
public class HttpCookiesTokenFinder implements TokenFinder {

    public static final String AUTHORIZATION_COOKIE_KEY = "Authorization";

    private Context<HttpServletRequest> httpServletRequestContext;

    @Nullable
    @Override
    public String findToken() {
        HttpServletRequest request = httpServletRequestContext.get();
        if (request.getCookies() == null) {
            return null;
        }
        return Stream.of(request.getCookies())
                .filter(c -> AUTHORIZATION_COOKIE_KEY.equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
