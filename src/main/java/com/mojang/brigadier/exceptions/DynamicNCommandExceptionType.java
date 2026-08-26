package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class DynamicNCommandExceptionType implements CommandExceptionType
{
    private final Function function;
    
    public DynamicNCommandExceptionType(final Function function) {
        this.function = function;
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException create(final Object a, final Object... args) {
        return new com.mojang.brigadier.exceptions.CommandSyntaxException(this, this.function.apply(args));
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException createWithContext(final ImmutableStringReader reader, final Object... args) {
        return new CommandSyntaxException(this, this.function.apply(args), reader.getString(), reader.getCursor());
    }
    
    public interface Function
    {
        Message apply(final Object[] p0);
    }
}
