package software.plusminus.authentication.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.authentication.service.AuthenticationService;
import software.plusminus.security.Security;
import software.plusminus.security.SecurityRequest;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.plusminus.check.Checks.check;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private AuthenticationService service;
    @Captor
    private ArgumentCaptor<ServletRequest> requestCaptor;

    private Security security = Security.builder()
            .username("test-user")
            .roles(Collections.singleton("admin"))
            .build();

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Test
    public void successfulAuthentication() throws ServletException, IOException {
        when(service.authenticate(request)).thenReturn(security);

        authenticationFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(requestCaptor.capture(), same(response));
        check(requestCaptor.getValue()).isNotNull();
        check(requestCaptor.getValue() instanceof SecurityRequest).isTrue();
        SecurityRequest securityRequest = (SecurityRequest) requestCaptor.getValue();
        check(securityRequest.getSecurity()).isSame(security);
        check(securityRequest.getRequest()).isSame(request);
    }
    
    @Test
    public void unsuccessfulAuthentication() throws ServletException, IOException {
        when(service.authenticate(request)).thenReturn(null);
        authenticationFilter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(same(request), same(response));
    }
}