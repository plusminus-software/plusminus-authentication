package software.plusminus.authentication.service.token;

import software.plusminus.security.model.Security;

import javax.annotation.Nullable;

public interface TokenParser {

    @Nullable
    Security parseToken(String token);

}
