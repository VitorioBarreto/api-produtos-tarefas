package com.vitoriobarreto.meu_projeto.controller;

// src/main/java/com/example/meuprimeiroprojeto/controller/ProdutoController.java

import com.vitoriobarreto.meu_projeto.dto.ProdutoResponse;
import com.vitoriobarreto.meu_projeto.model.Produto;
import com.vitoriobarreto.meu_projeto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus; // Importe para usar códigos HTTP
import org.springframework.http.ResponseEntity; // Importe para respostas mais robustas
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; // Importe todas as anotações REST

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // 1. Indica que esta classe é um controlador REST
@RequestMapping("/api/produtos") // 2. Define o prefixo base para todas as rotas neste controlador
public class ProdutoController {

    private final ProdutoService produtoService; // 3. Declara a dependência do Serviço

    // 4. Injeção de Dependência via Construtor
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // 5. Endpoint para listar todos os produtos
    @GetMapping // Mapeia requisições GET para /api/produtos
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodosProdutos();
        List<ProdutoResponse> produtosResponse = produtos.stream()
                .map(this::convertToProdutoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosResponse); // Retorna 200 OK com a lista de produtos
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProdutoResponse> buscarProdutoPorId(@PathVariable Long id) { // <-- Retorna ProdutoResponse
        Produto produto = produtoService.buscarProdutoPorId(id); // Ainda busca a entidade
        // Mapeia a entidade Produto para um ProdutoResponse
        ProdutoResponse produtoResponse = convertToProdutoResponse(produto); // <-- Chama o método de conversão
        return ResponseEntity.ok(produtoResponse);
    }
    // 7. Endpoint para criar um novo produto
    @PostMapping // Mapeia requisições POST para /api/produtos
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> criarProduto(@Valid @RequestBody Produto produto) { // @RequestBody converte JSON em objeto Produto
        Produto novoProduto = produtoService.salvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToProdutoResponse(novoProduto)); // Retorna 201 Created com o novo produto
    }

    // 8. Endpoint para atualizar um produto existente
    @PutMapping("/{id}") // Mapeia requisições PUT para /api/produtos/{id}
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id,@Valid @RequestBody Produto produtoDetalhes) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoDetalhes);
        return ResponseEntity.ok(convertToProdutoResponse(produtoAtualizado));
    }

    // 9. Endpoint para deletar um produto
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para /api/produtos/{id}
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após deletar
    }

    // NOVO MÉTODO: Mapeia Entidade Produto para DTO ProdutoResponse
    private ProdutoResponse convertToProdutoResponse(Produto produto) {
        ProdutoResponse response = new ProdutoResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setPreco(produto.getPreco());
        // ATENÇÃO: NÃO COPIAMOS produto.getCustoDeAquisicao() para o DTO

        if (produto.getId() != null) {
            response.setNomeFornecedor("Fornecedor " + produto.getId());
        } else {
            response.setNomeFornecedor("Fornecedor Padrão");
        }

        return response;
    }
}