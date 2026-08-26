package io.papermc.paper.annotation;

import java.lang.annotation.*;

/**
 * Indicates that API may change with no or fewer compatibility guarantees across Minecraft versions,
 * as it is more or less directly representing the underlying Vanilla data.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
    ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE
})
public @interface MinecraftVersionDependent {
}
