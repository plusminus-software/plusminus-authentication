package software.plusminus.authentication;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class AuthenticationParameters {

    private String username;
    private Set<String> roles;
    private Map<String, ?> otherParameters;
    
}
