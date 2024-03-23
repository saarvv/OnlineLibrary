package com.example.onlinelibrary.constraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE}) //describes the object range modified by annotation
@Retention(RUNTIME) //the life cycle of the described annotation
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented //is used to include elements in annotations into javadoc
public @interface FieldMatch {
    //payload: body in http req and response message
    String message() default "{constraint.field-match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String first();

    String second();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldMatch[] value();
    }
}
