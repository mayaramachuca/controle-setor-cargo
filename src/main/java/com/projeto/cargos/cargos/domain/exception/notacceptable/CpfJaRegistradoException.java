package com.projeto.cargos.cargos.domain.exception.notacceptable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CpfJaRegistradoException extends RuntimeException{
    public CpfJaRegistradoException(String mensagem){
        super(mensagem);
    }
}
