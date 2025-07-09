package com.vitoriobarreto.meu_projeto.controller;


import com.vitoriobarreto.meu_projeto.dto.LoginRequest;
import com.vitoriobarreto.meu_projeto.dto.RegisterRequest;
import com.vitoriobarreto.meu_projeto.dto.RoleResponse;
import com.vitoriobarreto.meu_projeto.dto.UserResponse;
import com.vitoriobarreto.meu_projeto.exception.UsernameAlreadyExists;
import com.vitoriobarreto.meu_projeto.model.User;
import com.vitoriobarreto.meu_projeto.security.jwt.JwtUtil;
import com.vitoriobarreto.meu_projeto.security.services.UserDetailsServiceImpl;
import com.vitoriobarreto.meu_projeto.service.AuthService;
import jakarta.validation.Valid; // Importe para a validação do DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Importe para retorno de resposta
import org.springframework.security.authentication.AuthenticationManager; // Importe AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importe UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication; // Importe Authentication
import org.springframework.security.core.userdetails.UserDetails; // Importe UserDetails
import org.springframework.web.bind.annotation.*; // Importe as anotações REST

import com.vitoriobarreto.meu_projeto.model.User; // Importe a entidade User
import com.vitoriobarreto.meu_projeto.model.Role; // Importe a entidade Role

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth") // 1. Define o caminho base para endpoints de autenticação
public class AuthController {

//    @Autowired // 2. Injeta o AuthenticationManager (para autenticar credenciais)
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private AuthService authService; // Injeta o AuthService para registrar usuários
//    @Autowired // 3. Injeta o JwtUtil (para gerar o token)
//    private JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping("/login") // 4. Mapeia requisições POST para /api/auth/login
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) { // 5. Recebe o DTO de login

        // 6. Autentica o usuário usando o AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 7. Se a autenticação for bem-sucedida, obtém os detalhes do usuário
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 8. Gera o token JWT para o usuário autenticado
        String jwt = jwtUtil.generateToken(userDetails);

        User userEntity = userDetailsServiceImpl.getUserEntityByUsername(userDetails.getUsername()); // <-- Obtém a entidade User

        // 9. Retorna o token JWT na resposta
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("user", convertToUserResponse(userEntity)); // <-- Adiciona o UserResponse aqui

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) { // <-- Retorna UserResponse
        try {
            User newUser = authService.registerUser(registerRequest); // authService retorna a entidade User
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToUserResponse(newUser)); // Retorna o UserResponse
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Em caso de erro, talvez um erro DTO mais específico
        }
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRoles(user.getRoles().stream()
                .map(this::convertToRoleResponse) // Mapeia Role para RoleResponse
                .collect(Collectors.toSet()));
        return response;
    }

    private RoleResponse convertToRoleResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }
}