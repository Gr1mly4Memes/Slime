package io.papermc.paper.annotation;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Used to mark classes which are generated.
 * Any manual changes will be overwritten.
 */
@ApiStatus.Internal
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GeneratedClass {
}
