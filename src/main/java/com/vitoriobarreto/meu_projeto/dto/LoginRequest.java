package com.vitoriobarreto.meu_projeto.dto;


import jakarta.validation.constraints.NotBlank; // Importe esta anotação
import lombok.Getter; // Importe esta anotação (se estiver usando Lombok)
import lombok.Setter; // Importe esta anotação (se estiver usando Lombok)

public class LoginRequest {

    @NotBlank(message = "O nome de usuário não pode ser vazio") // 3. Validação: nome de usuário obrigatório
    private String username;

    @NotBlank(message = "A senha não pode ser vazia") // 4. Validação: senha obrigatória
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}