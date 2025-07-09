package com.vitoriobarreto.meu_projeto.dto;


import lombok.AllArgsConstructor; // Importe (se estiver usando Lombok)
import lombok.NoArgsConstructor; // Importe (se estiver usando Lombok)
public class RoleResponse {
    private Long id;
    private String name;

    public RoleResponse() {
    }

    public RoleResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}