package com.vitoriobarreto.meu_projeto.service;

import com.vitoriobarreto.meu_projeto.dto.RegisterRequest; // Importe o DTO de registro
import com.vitoriobarreto.meu_projeto.exception.UsernameAlreadyExists;
import com.vitoriobarreto.meu_projeto.model.Role; // Importe a entidade Role
import com.vitoriobarreto.meu_projeto.model.User; // Importe a entidade User
import com.vitoriobarreto.meu_projeto.repository.RoleRepository; // Importe o RoleRepository
import com.vitoriobarreto.meu_projeto.repository.UserRepository; // Importe o UserRepository
import org.springframework.security.crypto.password.PasswordEncoder; // Importe o PasswordEncoder
import org.springframework.stereotype.Service; // Importe a anotação Service

import java.util.Collections; // Importe Collections (para o set de roles)

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependências via construtor
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para registrar um novo usuário
    public User registerUser(RegisterRequest registerRequest) {
        // 1. Verificar se o username já existe
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExists("Nome de usuário já está em uso!"); // Ou uma exceção mais específica
        }

        // 2. Criar uma nova instância de User
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // 3. Criptografar a senha!

        // 4. Atribuir o papel padrão (ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Erro: Papel de usuário não encontrado.")); // Garante que ROLE_USER exista

        user.setRoles(Collections.singleton(userRole)); // Atribui apenas ROLE_USER por padrão

        // 5. Salvar o usuário no banco de dados
        return userRepository.save(user);
    }
}