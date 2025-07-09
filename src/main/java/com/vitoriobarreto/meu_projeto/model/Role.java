package com.vitoriobarreto.meu_projeto.model;


import jakarta.persistence.*; // Importe todas as anotações JPA
import lombok.Getter; // Importe (se estiver usando Lombok)
import lombok.Setter; // Importe (se estiver usando Lombok)

@Entity // 1. Marca como entidade JPA
@Table(name = "roles") // 2. Define o nome da tabela no banco (opcional, mas bom para evitar conflitos com "role" que é palavra reservada)
@Getter // 3. Lombok para getters
@Setter // 4. Lombok para setters
public class Role {

    @Id // 5. Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 6. ID gerado automaticamente
    private Long id;

    @Column(unique = true, nullable = false) // 7. Garante que o nome do papel seja único e não nulo
    private String name; // Ex: ROLE_USER, ROLE_ADMIN

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}