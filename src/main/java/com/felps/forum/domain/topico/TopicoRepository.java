package com.felps.forum.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("""
            select t
            from Topico t
            where titulo = :titulo
            and mensagem = :mensagem
            and status != DELETADO
            """)
    Optional<Topico> verificarDuplicidadeTopico(String titulo, String mensagem);

    @Query("""
            select t
            from Topico t
            where status != DELETADO
            """)
    Page<Topico> encontrarTopicosAtivos(Pageable pageable);
}
