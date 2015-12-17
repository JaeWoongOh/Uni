package com.markjmind.uni.annotiation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target(ElementType.FIELD)
public @interface Param
{
	String value() default "";
}