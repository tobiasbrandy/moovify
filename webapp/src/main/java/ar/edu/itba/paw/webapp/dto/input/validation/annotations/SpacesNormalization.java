package ar.edu.itba.paw.webapp.dto.input.validation.annotations;

import ar.edu.itba.paw.webapp.dto.input.validation.constraints.SpacesNormalizationConstrainValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SpacesNormalizationConstrainValidator.class)
@Target({ TYPE, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface SpacesNormalization {

    String message() default "{custom.validation.SpacesNormalization.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
