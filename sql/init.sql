create table reserved_push_register
(
    id bigint auto_increment primary key,
    at_time datetime(6) null,
    email varchar(255) null,
    message varchar(255) null,
    title varchar(255) null
);
