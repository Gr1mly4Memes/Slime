package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public interface BuiltInExceptionProvider
{
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType doubleTooLow();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType doubleTooHigh();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType floatTooLow();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType floatTooHigh();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType integerTooLow();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType integerTooHigh();
    
    com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType longTooLow();
    
    Dynamic2CommandExceptionType longTooHigh();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType literalIncorrect();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedStartOfQuote();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedEndOfQuote();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidEscape();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidBool();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidInt();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedInt();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidLong();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedLong();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidDouble();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedDouble();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerInvalidFloat();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedFloat();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType readerExpectedBool();
    
    com.mojang.brigadier.exceptions.DynamicCommandExceptionType readerExpectedSymbol();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType dispatcherUnknownCommand();
    
    com.mojang.brigadier.exceptions.SimpleCommandExceptionType dispatcherUnknownArgument();
    
    SimpleCommandExceptionType dispatcherExpectedArgumentSeparator();
    
    DynamicCommandExceptionType dispatcherParseException();
}
