package software.plusminus.authentication.service;

import software.plusminus.security.Security;
import software.plusminus.security.TokenPlace;

import java.util.Collections;
import java.util.Map;

public interface Authenticator {

    TokenPlace tokenPlace();

    Security authenticate(String token);

    default String provideToken(Security security) {
        return provideToken(security, Collections.emptyMap());
    }

    String provideToken(Security security, Map<String, String> others);
    
}
