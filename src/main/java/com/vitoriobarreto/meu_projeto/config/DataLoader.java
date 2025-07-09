package com.vitoriobarreto.meu_projeto.config;

import com.vitoriobarreto.meu_projeto.model.Role; // Importe a entidade Role
import com.vitoriobarreto.meu_projeto.repository.RoleRepository; // Importe o RoleRepository
import org.springframework.boot.CommandLineRunner; // Importe esta interface
import org.springframework.context.annotation.Bean; // Importe esta anotação
import org.springframework.context.annotation.Configuration; // Importe esta anotação
import org.slf4j.Logger; // Importe o Logger
import org.slf4j.LoggerFactory; // Importe o LoggerFactory

@Configuration // 1. Marca como classe de configuração
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class); // Logger

    // 2. Define um Bean do tipo CommandLineRunner que será executado na inicialização
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            // Inserir ROLE_USER se não existir
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
                logger.info("ROLE_USER inserido no banco de dados.");
            } else {
                logger.info("ROLE_USER já existe no banco de dados.");
            }

            // Inserir ROLE_ADMIN se não existir
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
                logger.info("ROLE_ADMIN inserido no banco de dados.");
            } else {
                logger.info("ROLE_ADMIN já existe no banco de dados.");
            }
        };
    }
}