package software.plusminus.authorisation.localhost;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import software.plusminus.authorisation.AuthorisationException;
import software.plusminus.authorisation.AuthorisationUtil;
import software.plusminus.authorisation.Authoriser;
import software.plusminus.security.Security;

public class LocalhostOnlyAuthoriser implements Authoriser {

    @Override
    public void authorise(HttpServletRequest request, Security security, Method method) {
        LocalhostOnly localhostOnly = AuthorisationUtil.findAnnotation(method, LocalhostOnly.class);
        if (localhostOnly == null) {
            return;
        }
        String ip = IpUtils.getClientIpAddress(request);
        if (!"localhost".equals(ip) && !"127.0.0.1".equals(ip)) {
            throw new AuthorisationException("Resource is accessible from local network only");
        }
    }

}
