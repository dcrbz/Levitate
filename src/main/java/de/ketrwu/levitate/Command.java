package de.ketrwu.levitate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String syntax();
	String readable() default "";
	String[] aliases() default {};
	String permission() default "";
	String description() default "";
}
