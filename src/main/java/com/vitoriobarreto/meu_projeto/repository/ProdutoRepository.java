package com.vitoriobarreto.meu_projeto.repository;

// src/main/java/com/example/meuprimeiroprojeto/repository/ProdutoRepository.java


import com.vitoriobarreto.meu_projeto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository; // Importe esta interface
import org.springframework.stereotype.Repository; // Importe esta anotação

@Repository // 1. Indica que esta interface é um componente de acesso a dados (Repository)
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    /* Esta interface herda métodos CRUD (Create, Read, Update, Delete) do JpaRepository e, por ser uma interface, o Spring Data JPA automaticamente cria uma implementação
    em tempo de execução para você! */

    // Podemos adicionar métodos personalizados aqui, como por exemplo:
    // List<Produto> findByNomeContainingIgnoreCase(String nome);
}