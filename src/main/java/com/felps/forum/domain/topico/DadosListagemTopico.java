package com.felps.forum.domain.topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(

        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso

) {
    public DadosListagemTopico(Topico topico) {
        this(topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso());
    }
}
