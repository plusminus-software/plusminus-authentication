package software.plusminus.authentication.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import software.plusminus.authentication.exception.NonPublicApiException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UnauthenticatedHandler {
    
    private PublicEndpointService publicEndpointService;
    @Value("${security.loginPage:/login}")
    private String loginPage; 

    @SuppressFBWarnings("UNVALIDATED_REDIRECT")
    public boolean handleUnauthenticated(HttpServletRequest request, 
                                         HttpServletResponse response, 
                                         HandlerMethod handlerMethod) {
        
        if (publicEndpointService.isPublicEndpoint(handlerMethod)) {
            return true;
        }
        if (isApiEndpoint(request)) {
            throw new NonPublicApiException();
        } else {
            try {
                response.sendRedirect(loginPage);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return false;
        }
    }
    
    private boolean isApiEndpoint(HttpServletRequest request) {
        return Collections.list(request.getHeaders("accept")).stream()
                .anyMatch(header -> header.startsWith("text/html"));
        
    }
}
