package software.plusminus.authorisation;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import software.plusminus.security.Security;

@Service
public class AuthorisationService {

    private List<Authoriser> authorisers;

    public void authorise(HttpServletRequest request, @Nullable Security security, Method method) {
        authorisers.forEach(a -> a.authorise(request, security, method));
    }
}
