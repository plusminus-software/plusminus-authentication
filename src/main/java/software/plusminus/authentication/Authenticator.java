package software.plusminus.authentication;

import java.util.Collections;
import java.util.Map;
import software.plusminus.security.Security;

public interface Authenticator {

    TokenPlace tokenPlace();

    Security authenticate(String token);

    default String provideToken(Security security) {
        return provideToken(security, Collections.emptyMap());
    }

    String provideToken(Security security, Map<String, String> others);
    
}
