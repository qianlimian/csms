package org.smartframework.platform.dto.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DtoProperty {
	Class<?> entityClass();

	String entityProperty() default "";

	Class<?> targetClass() default Object.class;

	boolean nullable() default false;

	boolean readOnly() default false;

	boolean trim() default true;

	boolean isValidate() default true;
}
