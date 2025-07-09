package com.vitoriobarreto.meu_projeto.model;


import jakarta.persistence.Entity; // Importe esta anotação
import jakarta.persistence.GeneratedValue; // Importe esta anotação
import jakarta.persistence.GenerationType; // Importe esta anotação
import jakarta.persistence.Id; // Importe esta anotação
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity // 1. Indica que esta classe é uma entidade JPA (mapeia para uma tabela no banco)
public class Produto {

    @Id // 2. Indica que este atributo é a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3. Configura a geração automática do ID
    private Long id; // Chave primária (geralmente Long para IDs numéricos)

    @NotBlank(message = "O nome do produto não pode ser vazio") // 1. Não pode ser nulo e nem vazia
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres") // 2. Tamanho mínimo e máximo
    private String nome; // Atributo para o nome do produto

    @NotNull(message = "O preço do produto não pode ser nulo") // 3. Não pode ser nulo
    @Positive(message = "O preço do produto deve ser um valor positivo") // 4. Deve ser um número positivo
    private Double preco; // Atributo para o preço do produto

    private Double custoDeAquisicao;

    // Construtor vazio (obrigatório para JPA)
    public Produto() {}

    // Construtor para criar produtos mais facilmente (sem o ID, que é gerado automaticamente)
    public Produto(String nome, Double preco, Double custoDeAquisicao) {
        this.nome = nome;
        this.preco = preco;
        this.custoDeAquisicao = custoDeAquisicao;
    }

    // Getters e Setters para acessar e modificar os atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getCustoDeAquisicao() {
        return custoDeAquisicao;
    }

    public void setCustoDeAquisicao(Double custoDeAquisicao) {
        this.custoDeAquisicao = custoDeAquisicao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}