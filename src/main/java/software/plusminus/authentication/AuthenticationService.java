package software.plusminus.authentication;

public interface AuthenticationService {

    AuthenticationParameters parseToken(String token);
    
    String generateToken(AuthenticationParameters parameters);
    
}
