package com.mojang.brigadier;

import com.mojang.brigadier.tree.CommandNode;

import java.util.Collection;

@FunctionalInterface
public interface AmbiguityConsumer<S>
{
    void ambiguous(final CommandNode<S> p0, final CommandNode<S> p1, final CommandNode<S> p2, final Collection<String> p3);
}
