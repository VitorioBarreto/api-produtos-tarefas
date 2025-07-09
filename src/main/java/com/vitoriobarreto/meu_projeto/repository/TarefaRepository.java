package com.vitoriobarreto.meu_projeto.repository;

import com.vitoriobarreto.meu_projeto.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

  List<Tarefa> findByConcluida(Boolean concluida);


}
