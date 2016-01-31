package io.github.podshot.WorldWar.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method, constructor, or class for refactoring
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
public @interface Refactor {
	
	public enum Priority {
		LOW, MEDIUM, HIGH, URGENT
	}
	
	Priority priority() default Priority.MEDIUM;
}
