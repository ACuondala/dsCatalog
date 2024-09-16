package com.example.nada.Services.Exceptions;

public class IntegrityException extends RuntimeException{
    private static final Long serialVersionUID=1L;
    public IntegrityException(String sms){
        super(sms);
    }
}
