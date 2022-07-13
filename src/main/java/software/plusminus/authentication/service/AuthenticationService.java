package software.plusminus.authentication.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import software.plusminus.security.Security;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    private List<Authenticator> authenticators;
    private TokenFetcher tokenFetcher;

    @Nullable
    public Security authenticate(HttpServletRequest request) {
        return authenticators.stream()
                .map(a -> authenticate(a, request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private Security authenticate(Authenticator authenticator, HttpServletRequest request) {
        String token = tokenFetcher.fetchToken(authenticator.tokenPlace(), request);
        if (token == null) {
            return null;
        }
        return authenticator.authenticate(token);
    }
    
    @Nullable
    public Map.Entry<Authenticator, String> provideToken(Security security, Predicate<Authenticator> predicate) {
        return authenticators.stream()
                .filter(predicate)
                .map(a -> new AbstractMap.SimpleEntry<>(a, a.provideToken(security)))
                .filter(e -> e.getValue() != null)
                .findFirst()
                .orElse(null);
    }
}
