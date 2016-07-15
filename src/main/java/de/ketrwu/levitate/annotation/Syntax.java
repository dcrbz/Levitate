package de.ketrwu.levitate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.ketrwu.levitate.CommandExecutor;

/**
 * The syntax annotation used to register syntaxes in a developer-friendly way.
 * @author Kenneth Wussmann
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {
	String syntax();
	boolean parameter() default false;
	boolean parameterOptional() default false;
	CommandExecutor commandType() default CommandExecutor.ALL;
}
