package software.plusminus.authentication.config;

import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import software.plusminus.authentication.service.AuthenticationService;
import software.plusminus.security.Security;
import software.plusminus.security.SecurityRequest;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        Security security = authenticationService.authenticate(request);
        if (security != null) {
            request = new SecurityRequest(request, security);
        }
        chain.doFilter(request, response);
    }
}
