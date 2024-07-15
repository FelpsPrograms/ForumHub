package com.felps.forum.controller;

import com.felps.forum.domain.DuplicidadeException;
import com.felps.forum.domain.topico.DadosCadastroTopico;
import com.felps.forum.domain.topico.DadosListagemTopico;
import com.felps.forum.domain.topico.Topico;
import com.felps.forum.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

        return ResponseEntity.created(uri).body(new DadosListagemTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<DadosListagemTopico> page = repository.encontrarTopicosAtivos(pageable)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        Topico topicoEncontrado = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemTopico(topicoEncontrado));
    }
}
