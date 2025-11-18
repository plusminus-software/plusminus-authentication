package software.plusminus.authentication.service.token;

import javax.annotation.Nullable;

public interface TokenFinder {

    @Nullable
    String findToken();

}
