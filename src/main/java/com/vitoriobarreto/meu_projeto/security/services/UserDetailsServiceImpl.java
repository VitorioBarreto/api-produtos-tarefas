package com.vitoriobarreto.meu_projeto.security.services;



import com.vitoriobarreto.meu_projeto.model.User;
import com.vitoriobarreto.meu_projeto.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection; // Importe esta classe
import java.util.stream.Collectors; // Importe esta classe

@Service // 1. Marca como um componente de serviço Spring
public class UserDetailsServiceImpl implements UserDetailsService { // 2. Implementa a interface do Spring Security

    private final UserRepository userRepository; // 3. Injeta o repositório de usuários

    // Injeção de Dependência via Construtor
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override // 4. Sobrescreve o método principal da interface UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 5. Busca o usuário no banco de dados pelo nome de usuário
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 6. Converte a entidade User para um objeto UserDetails do Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // A senha já deve estar criptografada no banco
                mapRolesToAuthorities(user.getRoles()) // Mapeia os papéis para GrantedAuthority
        );
    }

    // NOVO MÉTODO PARA RETORNAR A ENTIDADE USER
    public User getUserEntityByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    // 7. Método auxiliar para converter Set<Role> para Collection<? extends GrantedAuthority>
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(java.util.Set<com.vitoriobarreto.meu_projeto.model.Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}