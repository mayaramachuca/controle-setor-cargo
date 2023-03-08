package com.projeto.cargos.cargos.domain.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SetorNaoEncontradoException extends RuntimeException{
    public SetorNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
