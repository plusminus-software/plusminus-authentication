package software.plusminus.authentication.service.token;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import software.plusminus.security.model.Security;
import software.plusminus.security.service.SecurityProvider;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

@ConditionalOnBean(TokenParser.class)
@AllArgsConstructor
public class TokenAuthenticator implements SecurityProvider {

    private List<TokenFinder> tokenFinders;
    private List<TokenParser> tokenParsers;

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
        return tokenParsers.stream()
                .map(parser -> parser.parseToken(token))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
