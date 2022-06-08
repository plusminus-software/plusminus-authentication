package software.plusminus.authorisation.role;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import software.plusminus.authorisation.AuthorisationUtil;
import software.plusminus.authorisation.Authoriser;
import software.plusminus.authorisation.AuthorisationException;
import software.plusminus.security.Security;

public class RoleAuthoriser implements Authoriser {

    @Override
    public void authorise(HttpServletRequest request, Security security, Method method) {
        Role role = AuthorisationUtil.findAnnotation(method, Role.class);
        if (role == null) {
            return;
        }
        boolean containsAnyRole = Stream.of(role.value())
                .anyMatch(r -> security.getRoles().contains(r));
        if (!containsAnyRole) {
            throw new AuthorisationException("User '" + security.getUsername()
                    + "' does not have at least one of the required roles: " + Arrays.toString(role.value()));
        }
    }
}
