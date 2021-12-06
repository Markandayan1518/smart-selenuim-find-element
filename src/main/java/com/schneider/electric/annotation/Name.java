package com.schneider.electric.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(value = RetentionPolicy.RUNTIME)

public @interface Name {

  String value() default "";

}
