package com.content.platform.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomException extends RuntimeException
{
    public CustomException(String message)
    {
        super(message);
    }
}
