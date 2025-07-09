package com.vitoriobarreto.meu_projeto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária (geralmente Long para IDs numéricos)

    @NotBlank(message = "A descrição da tarefa não pode ser vazia") // Validação para garantir que a descrição não seja vazia
    @Size(min = 5, max = 60, message = "A descrição deve ter entre 5 e 60 caracteres") // Validação para o tamanho da descrição
    private String descricao; // Atributo para a descrição da tarefa

    private boolean concluida; // Atributo para indicar se a tarefa está concluída

    public Tarefa(){}

    public Tarefa(boolean concluida, String descricao) {
        this.concluida = concluida;
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
