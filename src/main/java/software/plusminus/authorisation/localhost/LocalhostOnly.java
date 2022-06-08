package software.plusminus.authorisation.localhost;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import software.plusminus.authorisation.Authorisation;

@Authorisation
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalhostOnly {
    String[] value();
}
