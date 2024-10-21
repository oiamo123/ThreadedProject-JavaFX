package org.example.demo.util.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
    boolean id() default false;
    String name();
    boolean isDouble() default false;
    boolean isInt() default false;
    boolean isString() default false;
    boolean isDate() default false;
    int maxLength() default -1;
    Class<?> className() default Void.class;
    boolean isDisplayValue() default false;
}
