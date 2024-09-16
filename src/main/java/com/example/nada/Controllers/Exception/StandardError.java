package com.example.nada.Controllers.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {

    private static final long serialVersionUID=1L;


    private Integer status;
    private String  message;

    public StandardError(String sms){
        super();
        this.message=sms;
    }
}
