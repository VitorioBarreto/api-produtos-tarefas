package com.vitoriobarreto.meu_projeto.dto;


public class ProdutoResponse {
    private Long id;
    private String nome;
    private Double preco;

    private String nomeFornecedor;

    // Construtores podem ser úteis para mapeamento (opcional, Lombok pode gerar um construtor com @AllArgsConstructor)
    public ProdutoResponse() {}

    public ProdutoResponse(Long id, String nome, Double preco, String nomeFornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.nomeFornecedor = nomeFornecedor;
    }

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

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}