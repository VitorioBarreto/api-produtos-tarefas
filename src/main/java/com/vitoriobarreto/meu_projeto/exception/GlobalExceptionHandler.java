package com.vitoriobarreto.meu_projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // Importe esta exceção
import org.springframework.web.bind.annotation.ControllerAdvice; // Importe esta anotação
import org.springframework.web.bind.annotation.ExceptionHandler; // Importe esta anotação

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // 1. Indica que esta classe lida com exceções de todos os controladores
public class GlobalExceptionHandler {

    // 2. Este método lida especificamente com MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField(); // Nome do campo com erro
            String errorMessage = error.getDefaultMessage(); // Mensagem de erro da anotação
            errors.put(fieldName, errorMessage);
        });
        // Retorna um status 400 Bad Request com os detalhes dos erros
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        // Retorna o status 404 Not Found e a mensagem da exceção como corpo da resposta
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TarefaNotFoundException.class)
    public ResponseEntity<String> handleTarefaNotFoundException(TarefaNotFoundException ex) {
        // Retorna o status 404 Not Found e a mensagem da exceção como corpo da resposta
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExists ex) {
        // Retorna o status 409 Conflict e a mensagem da exceção como corpo da resposta
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}