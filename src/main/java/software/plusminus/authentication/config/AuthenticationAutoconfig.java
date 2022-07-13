package software.plusminus.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import software.plusminus.authentication.service.AuthenticationService;
import software.plusminus.authentication.service.UnauthenticatedHandler;

@Configuration
@ComponentScan("software.plusminus.authentication")
public class AuthenticationAutoconfig implements WebMvcConfigurer {

    @Autowired
    private UnauthenticatedHandler unauthenticatedHandler;

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationService service) {
        return new AuthenticationFilter(service);
    }

    @Bean
    public FilterRegistrationBean authenticationFilterRegistration(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authenticationFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UnauthenticatedInterceptor(unauthenticatedHandler))
                .order(-1);
    }
    
}
