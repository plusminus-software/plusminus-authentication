package software.plusminus.authentication.service.token;

import software.plusminus.security.Security;

import javax.annotation.Nullable;

public interface TokenProcessor {

    @Nullable
    Security processToken(String token);

}
