package software.plusminus.authentication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityProperties {
    private List<String> openUris = Collections.emptyList();
}
