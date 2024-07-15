package com.felps.forum.controller;

import com.felps.forum.domain.DuplicidadeException;
import com.felps.forum.domain.topico.DadosCadastroTopico;
import com.felps.forum.domain.topico.DadosDetalhamentoTopico;
import com.felps.forum.domain.topico.Topico;
import com.felps.forum.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) throws DuplicidadeException {
        Optional<Topico> topicoDuplicado = repository.verificarDuplicidadeTopico(dados.titulo(), dados.mensagem());
        if (topicoDuplicado.isPresent()) {
            throw new DuplicidadeException("Já existe um tópico com o mesmo título e mensagem!");
        }

        Topico topico = new Topico(dados);
        repository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }
}
