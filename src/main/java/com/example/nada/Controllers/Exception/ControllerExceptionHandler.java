package com.example.nada.Controllers.Exception;

import com.example.nada.Services.Exceptions.EntitiesNotFoundException;
import com.example.nada.Services.Exceptions.IntegrityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = EntitiesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody StandardError handleException(EntitiesNotFoundException e){
        return new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage());
    }

    @ExceptionHandler(value= IntegrityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> datebase(IntegrityException e){
        HttpStatus status= HttpStatus.BAD_REQUEST;
        StandardError error=new StandardError();
         error.setStatus(status.value());
         error.setMessage(e.getMessage());
         return ResponseEntity.status(status).body(error);

    }
}
