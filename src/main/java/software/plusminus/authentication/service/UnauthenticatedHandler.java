package software.plusminus.authentication.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import software.plusminus.authentication.exception.NonPublicApiException;
import software.plusminus.authentication.exception.NotFoundException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UnauthenticatedHandler {
    
    private PublicEndpointService publicEndpointService;
    @Value("${security.loginPage:#{null}}")
    private String loginPage; 

    @SuppressFBWarnings("UNVALIDATED_REDIRECT")
    public boolean handleUnauthenticated(HttpServletRequest request, 
                                         HttpServletResponse response, 
                                         HandlerMethod handlerMethod) {
        
        if (publicEndpointService.isPublicEndpoint(request, handlerMethod)) {
            return true;
        }
        if (!isHtmlEndpoint(request)) {
            throw new NonPublicApiException();
        }
        if (loginPage == null) {
            throw new NotFoundException();
        }
        if (loginPage.equals(request.getRequestURI())) {
            return true;
        }
        try {
            response.sendRedirect(loginPage);
            return false;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private boolean isHtmlEndpoint(HttpServletRequest request) {
        return Collections.list(request.getHeaders("accept")).stream()
                .anyMatch(header -> header.startsWith("text/html"));
        
    }
}
