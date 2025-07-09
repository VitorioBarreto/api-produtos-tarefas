package com.vitoriobarreto.meu_projeto.repository;

// src/main/java/com/example/meuprimeiroprojeto/repository/RoleRepository.java

import com.vitoriobarreto.meu_projeto.model.Role;
import org.springframework.data.jpa.repository.JpaRepository; // Importe JpaRepository
import org.springframework.stereotype.Repository; // Importe Repository
import java.util.Optional; // Importe Optional

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Encontra um papel pelo nome (Ex: ROLE_USER, ROLE_ADMIN)
    Optional<Role> findByName(String name);
}

