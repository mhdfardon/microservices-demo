package com.microservices.demo.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DemoDataSet {
	/**
	 * Dataset xml files location.
	 */
	String value() default "";

	String setupOperation() default "CLEAN_INSERT";

	String teardownOperation() default "NONE";

	/**
	 * The datasource name from which the connections should be taken.
	 */
	String datasourceName() default "";
}