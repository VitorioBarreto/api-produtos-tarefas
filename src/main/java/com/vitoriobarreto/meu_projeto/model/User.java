package com.vitoriobarreto.meu_projeto.model;


import jakarta.persistence.*; // Importe todas as anotações JPA
import lombok.Getter; // Importe (se estiver usando Lombok)
import lombok.Setter; // Importe (se estiver usando Lombok)
import java.util.HashSet; // Importe esta classe
import java.util.Set; // Importe esta classe

@Entity // 1. Marca como entidade JPA
@Table(name = "users") // 2. Define o nome da tabela no banco
public class User {

    @Id // 5. Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 6. ID gerado automaticamente
    private Long id;

    @Column(unique = true, nullable = false) // 7. Nome de usuário deve ser único e não nulo
    private String username;

    @Column(nullable = false) // 8. Senha não pode ser nula
    private String password; // Lembre-se: será armazenada CRIPTOGRAFADA!

    // 9. Relacionamento Many-to-Many com Role (um usuário pode ter muitos papéis, um papel pode ser de muitos usuários)
    @ManyToMany(fetch = FetchType.EAGER) // Carrega os papéis junto com o usuário
    @JoinTable(name = "user_roles", // Tabela de junção para o relacionamento
            joinColumns = @JoinColumn(name = "user_id"), // Coluna que referencia a tabela 'users'
            inverseJoinColumns = @JoinColumn(name = "role_id")) // Coluna que referencia a tabela 'roles'
    private Set<Role> roles = new HashSet<>(); // Conjunto para armazenar os papéis do usuário

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Método para adicionar um papel ao usuário
    public void addRole(Role role) {
        this.roles.add(role);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}