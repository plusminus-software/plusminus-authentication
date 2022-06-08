package software.plusminus.authorisation.nonpublic;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import software.plusminus.authorisation.AuthorisationUtil;
import software.plusminus.authorisation.Authoriser;
import software.plusminus.security.Security;

@Order(0)
@Component
public class PublicApiAuthoriser implements Authoriser {

    @Override
    public void authorise(HttpServletRequest request, @Nullable Security security, Method method) {
        if (security == null && !isPublic(method)) {
            throw new NonPublicException();
        }
    }

    private boolean isPublic(Method method) {
        return AuthorisationUtil.findAnnotation(method, Public.class) != null;
    }
}
