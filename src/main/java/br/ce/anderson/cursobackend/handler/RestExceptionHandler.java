package br.ce.anderson.cursobackend.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.ce.anderson.cursobackend.model.error.ErrorMessage;
import br.ce.anderson.cursobackend.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleRsourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage error = new ErrorMessage("Not found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
