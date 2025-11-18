package software.plusminus.authentication.service.token;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.plusminus.context.Context;
import software.plusminus.security.service.token.TokenFinder;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@Order(1)
@AllArgsConstructor
@Component
public class HttpHeaderTokenFinder implements TokenFinder {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private Context<HttpServletRequest> httpServletRequestContext;

    @Nullable
    @Override
    public String findToken() {
        return httpServletRequestContext.get().getHeader(AUTHORIZATION_HEADER);
    }
}
