package com.grcp.weatherrhythm.locationsong.domain.validation.annotation;

import com.grcp.weatherrhythm.locationsong.domain.validation.validator.LatitudeValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LatitudeValidator.class)
@Documented
public @interface Latitude {

    String message() default "{ProductAttribute.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
