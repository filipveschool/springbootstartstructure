package org.filip.springbootstartstructure.validation.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * With the @Constraint annotation, we defined the class that is going to validate our field
 * the message() is the error message that is showed in the user interface
 * The additional code is most boilerplate code to conforms to the Spring standards.
 */
@Documented
@Constraint(validatedBy = ContactNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumberConstraint {

    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
