package software.plusminus.authentication.config;

import lombok.AllArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import software.plusminus.authentication.service.UnauthenticatedHandler;
import software.plusminus.security.SecurityRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class UnauthenticatedInterceptor extends HandlerInterceptorAdapter {

    private UnauthenticatedHandler unauthenticatedHandler;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (isAuthenticated(request)) {
            return true;
        }
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = getHandlerMethod(handler);
        return unauthenticatedHandler.handleUnauthenticated(request, response, handlerMethod);
    }
    
    private boolean isAuthenticated(HttpServletRequest request) {
        return request instanceof SecurityRequest;
    }
    
    private HandlerMethod getHandlerMethod(Object handler) {
        if (handler instanceof HandlerMethod) {
            return (HandlerMethod) handler;
        } else {
            throw new IllegalStateException("Expected " + handler.getClass() + " to be an instance of HandlerMethod");
        }
    }
}
