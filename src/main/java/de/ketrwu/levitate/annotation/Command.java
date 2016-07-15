package de.ketrwu.levitate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The command annotation used to register commands in a developer-friendly way.
 * @author Kenneth Wussmann
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String syntax();
	String readable() default "";
	String[] aliases() default {};
	String permission() default "";
	String description() default "";
}
