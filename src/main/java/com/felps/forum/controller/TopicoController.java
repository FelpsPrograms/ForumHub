package com.felps.forum.controller;

import com.felps.forum.domain.DuplicidadeException;
import com.felps.forum.domain.IdNegativoException;
import com.felps.forum.domain.topico.*;
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
        if (id <= 0) {
            throw new IdNegativoException("ID passado na url deve ser positivo!");
        }

        Topico topicoEncontrado = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosListagemTopico(topicoEncontrado));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados) throws DuplicidadeException {
        Optional<Topico> topicoDuplicado = repository.verificarDuplicidadeTopico(dados.titulo(), dados.mensagem());
        if (topicoDuplicado.isPresent()) {
            throw new DuplicidadeException("Já existe um tópico com o mesmo título e mensagem!");
        }

        Topico topico = repository.getReferenceById(id);
        topico.atualizarTopico(dados);

        return ResponseEntity.ok(new DadosListagemTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        Topico topico = repository.getReferenceById(id);
        topico.deletar();

        return ResponseEntity.noContent().build();
    }

}
