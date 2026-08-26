package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Dynamic3CommandExceptionType implements CommandExceptionType
{
    private final Function function;
    
    public Dynamic3CommandExceptionType(final Function function) {
        this.function = function;
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException create(final Object a, final Object b, final Object c) {
        return new com.mojang.brigadier.exceptions.CommandSyntaxException(this, this.function.apply(a, b, c));
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException createWithContext(final ImmutableStringReader reader, final Object a, final Object b, final Object c) {
        return new CommandSyntaxException(this, this.function.apply(a, b, c), reader.getString(), reader.getCursor());
    }
    
    public interface Function
    {
        Message apply(final Object p0, final Object p1, final Object p2);
    }
}
