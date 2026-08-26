package com.mojang.brigadier.suggestion;

import com.mojang.brigadier.context.StringRange;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Suggestions
{
    private static final Suggestions EMPTY;
    private final StringRange range;
    private final List<com.mojang.brigadier.suggestion.Suggestion> suggestions;
    
    public Suggestions(final StringRange range, final List<com.mojang.brigadier.suggestion.Suggestion> suggestions) {
        this.range = range;
        this.suggestions = suggestions;
    }
    
    public StringRange getRange() {
        return this.range;
    }
    
    public List<com.mojang.brigadier.suggestion.Suggestion> getList() {
        return this.suggestions;
    }
    
    public boolean isEmpty() {
        return this.suggestions.isEmpty();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suggestions)) {
            return false;
        }
        final Suggestions that = (Suggestions)o;
        return Objects.equals(this.range, that.range) && Objects.equals(this.suggestions, that.suggestions);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.range, this.suggestions);
    }
    
    @Override
    public String toString() {
        return "Suggestions{range=" + this.range + ", suggestions=" + this.suggestions + '}';
    }
    
    public static CompletableFuture<Suggestions> empty() {
        return CompletableFuture.completedFuture(Suggestions.EMPTY);
    }
    
    public static Suggestions merge(final String command, final Collection<Suggestions> input) {
        if (input.isEmpty()) {
            return Suggestions.EMPTY;
        }
        if (input.size() == 1) {
            return input.iterator().next();
        }
        final Set<com.mojang.brigadier.suggestion.Suggestion> texts = new HashSet<com.mojang.brigadier.suggestion.Suggestion>();
        for (final Suggestions suggestions : input) {
            texts.addAll(suggestions.getList());
        }
        return create(command, texts);
    }
    
    public static Suggestions create(final String command, final Collection<com.mojang.brigadier.suggestion.Suggestion> suggestions) {
        if (suggestions.isEmpty()) {
            return Suggestions.EMPTY;
        }
        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        for (final com.mojang.brigadier.suggestion.Suggestion suggestion : suggestions) {
            start = Math.min(suggestion.getRange().getStart(), start);
            end = Math.max(suggestion.getRange().getEnd(), end);
        }
        final StringRange range = new StringRange(start, end);
        final Set<com.mojang.brigadier.suggestion.Suggestion> texts = new HashSet<com.mojang.brigadier.suggestion.Suggestion>();
        for (final com.mojang.brigadier.suggestion.Suggestion suggestion2 : suggestions) {
            texts.add(suggestion2.expand(command, range));
        }
        final List<com.mojang.brigadier.suggestion.Suggestion> sorted = new ArrayList<com.mojang.brigadier.suggestion.Suggestion>(texts);
        sorted.sort((a, b) -> a.compareToIgnoreCase(b));
        return new Suggestions(range, sorted);
    }
    
    static {
        EMPTY = new Suggestions(StringRange.at(0), new ArrayList<Suggestion>());
    }
}
