package software.plusminus.authentication.service;

import software.plusminus.authentication.model.TokenPlace;
import software.plusminus.security.Security;

public interface Authenticator {

    default TokenPlace tokenPlace() {
        return TokenPlace.builder().build();
    }

    Security authenticate(String token);

    String provideToken(Security security);
    
}
