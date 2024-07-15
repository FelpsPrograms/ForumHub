create table resposta(

    id bigint primary key auto_increment,
    mensagem varchar(255) not null,
    id_topico bigint,

    foreign key(id_topico) references topico(id)

);