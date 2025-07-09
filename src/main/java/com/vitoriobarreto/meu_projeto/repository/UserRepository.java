package com.vitoriobarreto.meu_projeto.repository;

import com.vitoriobarreto.meu_projeto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Método para buscar usuário por nome de usuário

    Boolean existsByUsername(String username); // Método para verificar se o usuário existe pelo nome de usuário

}
