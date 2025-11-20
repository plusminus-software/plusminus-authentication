package software.plusminus.authentication.service.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.plusminus.security.model.Security;
import software.plusminus.security.service.SecurityProvider;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

@AllArgsConstructor
@Component
public class TokenSecurityProvider implements SecurityProvider {

    private List<TokenFinder> tokenFinders;
    private List<TokenProcessor> tokenProcessors;

    @Nullable
    @Override
    public Security provideSecurity() {
        String token = tokenFinders.stream()
                .map(TokenFinder::findToken)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (token == null) {
            return null;
        }
        return tokenProcessors.stream()
                .map(parser -> parser.processToken(token))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
