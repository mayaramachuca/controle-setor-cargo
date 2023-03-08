package com.projeto.cargos.cargos.app.exceptionHandler;

import com.projeto.cargos.cargos.domain.exception.notacceptable.CargoJaCadastradoException;
import com.projeto.cargos.cargos.domain.exception.notacceptable.CpfJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notacceptable.SetorJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.CargoNaoEncontradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.SetorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.TrabalhadorNaoEncontradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CpfJaRegistradoException.class)
    public ResponseEntity<Object> cpfJaRegistradoHandler(CpfJaRegistradoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        Problema problema = setProblema (ex.getMessage(), status);
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(SetorJaRegistradoException.class)
    public ResponseEntity<Object> setorJaRegistradoHandler(SetorJaRegistradoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        Problema problema = setProblema(ex.getMessage(), status);
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(CargoJaCadastradoException.class)
    public ResponseEntity<Object> cargoJaRegistradoHandler(CargoJaCadastradoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        Problema problema = setProblema(ex.getMessage(), status);
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(TrabalhadorNaoEncontradoException.class)
    public ResponseEntity<Object> trabalhadorNaoEncontradoHandler(TrabalhadorNaoEncontradoException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problema problema = setProblema(ex.getMessage(), status);
        return  handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(CargoNaoEncontradoException.class)
    public ResponseEntity<Object> cargoNaoEncontradoHandler(CargoNaoEncontradoException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problema problema = setProblema(ex.getMessage(), status);
        return  handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(SetorNaoEncontradoException.class)
    public ResponseEntity<Object> setorNaoEncontradoHandler(SetorNaoEncontradoException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problema problema = setProblema(ex.getMessage(), status);
        return  handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        var campos = new ArrayList<Problema.Campo>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            String nome = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            campos.add(new Problema.Campo(nome, mensagem));
        }
        var problema = setProblema("Um ou mais campos não foram preenchidos corretamente.", status);
        problema.setCampos(campos);
        return handleExceptionInternal(ex, problema, headers, status, request);
    }
    private Problema setProblema(String title, HttpStatus status){
        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitle(title);
        problema.setOffsetDateTime(OffsetDateTime.now());
        return problema;
    }
}
