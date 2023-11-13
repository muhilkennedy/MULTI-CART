package com.platform.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Muhil
 * Annotate with this interface for crud op methods to update/evict objects into temp cache.
 * use @Cacheable for read operation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheUpdate {
	
	String cache();

	String key() default "";
	
	boolean evict() default false;
	
}
