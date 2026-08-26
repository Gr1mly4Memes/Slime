package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.function.Function;

public class DynamicCommandExceptionType implements CommandExceptionType
{
    private final Function<Object, Message> function;
    
    public DynamicCommandExceptionType(final Function<Object, Message> function) {
        this.function = function;
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException create(final Object arg) {
        return new com.mojang.brigadier.exceptions.CommandSyntaxException(this, this.function.apply(arg));
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException createWithContext(final ImmutableStringReader reader, final Object arg) {
        return new CommandSyntaxException(this, this.function.apply(arg), reader.getString(), reader.getCursor());
    }
}
