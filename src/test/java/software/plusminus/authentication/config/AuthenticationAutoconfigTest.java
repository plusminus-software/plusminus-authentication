package software.plusminus.authentication.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import software.plusminus.authentication.properties.SecurityProperties;
import software.plusminus.authentication.service.AuthenticationService;

import static software.plusminus.check.Checks.check;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationAutoconfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthenticationAutoconfigTest {
    
    @Autowired(required = false)
    private AuthenticationService authenticationService;
    @Autowired(required = false)
    private SecurityProperties securityProperties;
    
    @Test
    public void defaultAuthenticationServiceIsPresent() {
        check(authenticationService).isNotNull();
    }
    
    @Test
    public void securityPropertiesIsPresent() {
        check(securityProperties).isNotNull();
    }
}