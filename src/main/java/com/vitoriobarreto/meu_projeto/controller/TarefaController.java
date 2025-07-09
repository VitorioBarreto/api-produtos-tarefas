package com.vitoriobarreto.meu_projeto.controller;

import com.vitoriobarreto.meu_projeto.model.Tarefa;
import com.vitoriobarreto.meu_projeto.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas") // Define o prefixo base para todas as rotas deste controlador
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        List<Tarefa> tarefas = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefas); // Retorna 200 OK com a lista de tarefas
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.buscarTarefaPorId(id);
        return ResponseEntity.ok(tarefa); // Retorna 200 OK com a tarefa encontrada
    }

    @GetMapping("/status/{concluida}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Tarefa>> buscarTarefasPorStatus(@PathVariable boolean concluida) {
        List<Tarefa> tarefas = tarefaService.buscarTarefasPorStatus(concluida);
        return ResponseEntity.ok(tarefas); // Retorna 200 OK com a lista de tarefas filtradas por status
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.status(201).body(novaTarefa); // Retorna 201 Created com a nova tarefa
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody Tarefa tarefaDetalhes) {
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefaDetalhes); // Busca a tarefa pelo ID
        return ResponseEntity.ok(tarefaAtualizada); // Retorna 200 OK com a tarefa atualizada

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após deletar
    }
}