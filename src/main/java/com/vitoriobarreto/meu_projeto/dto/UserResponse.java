package com.vitoriobarreto.meu_projeto.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set; // Importe Set
import java.util.List; // Opcional, se preferir List de Roles

@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Set<RoleResponse> roles; // <-- Lista de RoleResponse DTOs

    // ATENÇÃO: NÃO HÁ ATRIBUTO 'password' AQUI!

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Set<RoleResponse> getRoles() { return roles; }
    public void setRoles(Set<RoleResponse> roles) { this.roles = roles; }
}