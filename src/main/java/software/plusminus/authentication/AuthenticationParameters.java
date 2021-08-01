package software.plusminus.authentication;

import lombok.Data;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Data
public class AuthenticationParameters {

    private String username;
    private Set<String> roles = Collections.emptySet();
    private Map<String, ?> otherParameters = Collections.emptyMap();
    
}
