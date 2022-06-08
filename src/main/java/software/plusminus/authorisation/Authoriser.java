package software.plusminus.authorisation;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import software.plusminus.security.Security;

public interface Authoriser {

    void authorise(HttpServletRequest request, Security security, Method method);

}
