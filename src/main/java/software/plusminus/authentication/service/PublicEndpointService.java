package software.plusminus.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import software.plusminus.authentication.annotation.Public;
import software.plusminus.authentication.properties.SecurityProperties;
import software.plusminus.util.AnnotationUtils;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Service
public class PublicEndpointService {

    private static final List<Class<?>> PUBLIC_CONTROLLERS
            = Arrays.asList(ErrorController.class);
    
    @Autowired
    private SecurityProperties properties;

    public boolean isPublicEndpoint(HttpServletRequest request, HandlerMethod handlerMethod) {
        Public publicAnnotation = AnnotationUtils.findAnnotation(Public.class, handlerMethod.getMethod());
        if (publicAnnotation != null) {
            return publicAnnotation.value();
        }
        if (isPublicController(handlerMethod)) {
            return true;
        }
        if (isPublicUri(request)) {
            return true;
        }
        return false;
    }

    private boolean isPublicController(HandlerMethod handlerMethod) {
        return PUBLIC_CONTROLLERS.stream()
                .anyMatch(p -> p.isAssignableFrom(handlerMethod.getBeanType()));
    }
    
    private boolean isPublicUri(HttpServletRequest request) {
        if (request.getRequestURI() == null) {
            return false;
        }
        return properties.getOpenUris().stream()
                .anyMatch(request.getRequestURI()::matches);
    }
}
