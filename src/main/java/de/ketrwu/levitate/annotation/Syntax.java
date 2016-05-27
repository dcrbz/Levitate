package de.ketrwu.levitate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.ketrwu.levitate.CommandExecutor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {
	String syntax();
	boolean parameter() default false;
	boolean parameterOptional() default false;
	CommandExecutor commandType() default CommandExecutor.ALL;
}
