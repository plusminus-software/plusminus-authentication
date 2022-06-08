package software.plusminus.authentication;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import software.plusminus.security.Security;

@Service
public class AuthenticationService {

    private List<Authenticator> authenticators;
    private TokenFetcher tokenFetcher;

    @Nullable
    public Security authenticate(HttpServletRequest request) {
        return authenticators.stream()
                .map(p -> authenticate(p, request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private Security authenticate(Authenticator processor, HttpServletRequest request) {
        String token = tokenFetcher.fetchToken(processor.tokenPlace(), request);
        if (token == null) {
            return null;
        }
        return processor.authenticate(token);
    }

}
