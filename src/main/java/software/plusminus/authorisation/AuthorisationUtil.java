package software.plusminus.authorisation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

@UtilityClass
public class AuthorisationUtil {

    @Nullable
    public <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
        A annotation = AnnotationUtils.findAnnotation(method, annotationType);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType);
        }
        return annotation;
    }

}
