package software.plusminus.authentication.service;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import software.plusminus.authentication.annotation.Public;
import software.plusminus.util.AnnotationUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class PublicEndpointService {

    private static final List<Class<?>> PUBLIC_CONTROLLERS
            = Arrays.asList(ErrorController.class);

    public boolean isPublicEndpoint(HandlerMethod handlerMethod) {
        if (isPublicController(handlerMethod)) {
            return true;
        }
        return !AnnotationUtils.findMergedAnnotationsOnMethodAndClass(
                handlerMethod.getMethod(),
                annotation -> annotation.annotationType() == Public.class).isEmpty();
    }

    private boolean isPublicController(HandlerMethod handlerMethod) {
        return PUBLIC_CONTROLLERS.stream()
                .anyMatch(p -> p.isAssignableFrom(handlerMethod.getBeanType()));
    }
}
