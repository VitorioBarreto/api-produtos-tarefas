package com.vitoriobarreto.meu_projeto.service;

import com.vitoriobarreto.meu_projeto.exception.TarefaNotFoundException;
import com.vitoriobarreto.meu_projeto.model.Tarefa;
import com.vitoriobarreto.meu_projeto.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarTarefaPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException("Tarefa com ID " + id + " não encontrada"));
    }

    public List<Tarefa> buscarTarefasPorStatus(Boolean concluida) {
        List<Tarefa> tarefas = tarefaRepository.findByConcluida(concluida);
        return tarefas; // Retorna a lista de tarefas filtradas pelo status
    }

    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefa) {
        Tarefa tarefaExistente = buscarTarefaPorId(id);

        tarefaExistente.setDescricao(tarefa.getDescricao());
        tarefaExistente.setConcluida(tarefa.isConcluida()); // Atualiza o status de conclusão

        return tarefaRepository.save(tarefaExistente);
    }

    public void deletarTarefa(Long id) {
        buscarTarefaPorId(id);
        tarefaRepository.deleteById(id);
    }



}
