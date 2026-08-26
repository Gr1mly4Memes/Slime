package io.papermc.paper.registry.set;

import com.google.common.collect.Lists;
import io.papermc.paper.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
record RegistryValueSetImpl<T>(RegistryKey<T> registryKey, List<T> values) implements RegistryValueSet<T> {

    RegistryValueSetImpl {
        values = List.copyOf(values);
    }

    static <T> RegistryValueSet<T> create(final RegistryKey<T> registryKey, final Iterable<? extends T> values) {
        return new RegistryValueSetImpl<>(registryKey, Lists.newArrayList(values));
    }
}
