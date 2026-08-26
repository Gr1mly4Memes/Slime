package org.bukkit;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

@ApiStatus.Internal
@NullMarked
record FeatureFlagImpl(NamespacedKey key) implements FeatureFlag {

    static final Set<FeatureFlag> ALL_FLAGS = new HashSet<>();

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @ApiStatus.Internal
    record Deprecated(NamespacedKey key) implements FeatureFlag {

        @Override
        public NamespacedKey getKey() {
            return this.key;
        }
    }
}
