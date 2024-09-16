package com.example.nada.Services.Exceptions;

public class EntitiesNotFoundException extends RuntimeException{

    public EntitiesNotFoundException(String sms){
        //passar a mensagem para o construtor do RuntimeException
        super(sms);
    }
}
