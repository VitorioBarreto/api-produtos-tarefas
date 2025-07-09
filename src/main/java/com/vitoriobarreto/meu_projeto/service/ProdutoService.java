package com.vitoriobarreto.meu_projeto.service;

// src/main/java/com/example/meuprimeiroprojeto/service/ProdutoService.java

import com.vitoriobarreto.meu_projeto.exception.ProductNotFoundException;
import com.vitoriobarreto.meu_projeto.model.Produto;
import com.vitoriobarreto.meu_projeto.repository.ProdutoRepository;
import org.springframework.stereotype.Service; // Importe esta anotação

import java.util.List;
import java.util.Optional;

@Service // 1. Indica que esta classe é um componente de serviço e será gerenciada pelo Spring
public class ProdutoService {

    private final ProdutoRepository produtoRepository; // 2. Declara a dependência do Repository

    // 3. Injeção de Dependência via Construtor (melhor prática!)
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método para listar todos os produtos
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll(); // Usa o método findAll() do JpaRepository
    }

    // Modificado: Agora busca o produto e lança ProductNotFoundException se não encontrar
    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + id + " não encontrado"));
    }

    // Método para salvar um novo produto (ou atualizar um existente)
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto); // Usa o método save() do JpaRepository
    }

    // Novo método para atualizar um produto, usando o buscarProdutoPorId modificado
    public Produto atualizarProduto(Long id, Produto produtoDetalhes) {
        // Busca o produto, se não encontrar, lança ProductNotFoundException
        Produto produtoExistente = buscarProdutoPorId(id); // Usa o método que já lança a exceção

        // Atualiza os campos do produto encontrado
        produtoExistente.setNome(produtoDetalhes.getNome());
        produtoExistente.setPreco(produtoDetalhes.getPreco());
        produtoExistente.setCustoDeAquisicao(produtoDetalhes.getCustoDeAquisicao());

        return produtoRepository.save(produtoExistente); // Salva as alterações (update)
    }

    // Modificado: Agora usa o buscarProdutoPorId para verificar existência antes de deletar
    public void deletarProduto(Long id) {
        // Tenta buscar o produto. Se não encontrar, ProductNotFoundException será lançada.
        // Se encontrar, a execução continua e o produto é deletado.
        buscarProdutoPorId(id);
        produtoRepository.deleteById(id);
    }
}
