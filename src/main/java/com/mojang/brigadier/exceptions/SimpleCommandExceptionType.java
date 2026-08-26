package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SimpleCommandExceptionType implements CommandExceptionType
{
    private final Message message;
    
    public SimpleCommandExceptionType(final Message message) {
        this.message = message;
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException create() {
        return new com.mojang.brigadier.exceptions.CommandSyntaxException(this, this.message);
    }
    
    public com.mojang.brigadier.exceptions.CommandSyntaxException createWithContext(final ImmutableStringReader reader) {
        return new CommandSyntaxException(this, this.message, reader.getString(), reader.getCursor());
    }
    
    @Override
    public String toString() {
        return this.message.getString();
    }
}
