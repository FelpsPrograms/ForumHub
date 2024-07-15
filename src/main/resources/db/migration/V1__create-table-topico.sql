create table topico(

    id bigint primary key auto_increment,
    titulo varchar(255) not null,
    mensagem varchar(255) not null,
    data_criacao date not null,
    status varchar(255),
    autor varchar(255),
    curso varchar(255)

);