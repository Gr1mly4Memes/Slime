package com.mojang.brigadier.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface SuggestionProvider<S>
{
    CompletableFuture<Suggestions> getSuggestions(final CommandContext<S> p0, final SuggestionsBuilder p1) throws CommandSyntaxException;
}
