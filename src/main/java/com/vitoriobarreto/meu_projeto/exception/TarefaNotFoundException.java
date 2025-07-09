package com.vitoriobarreto.meu_projeto.exception;

import org.springframework.http.HttpStatus; // Importe esta anotação
import org.springframework.web.bind.annotation.ResponseStatus; // Importe esta anotação

@ResponseStatus(HttpStatus.NOT_FOUND) // 1. Define o status HTTP padrão para esta exceção
public class TarefaNotFoundException extends RuntimeException { // 2. Estende RuntimeException

    public TarefaNotFoundException(String message) { // 3. Construtor que aceita uma mensagem
        super(message); // Passa a mensagem para o construtor da superclasse (RuntimeException)
    }
}