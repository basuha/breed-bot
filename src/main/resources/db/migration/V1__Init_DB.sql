create sequence hibernate_sequence start 1 increment 1;

create table msg (
    id int8 not null,
    data varchar(2048),
    is_bot_message boolean,
    text varchar(255) not null,
    type varchar(32),
    user_id int8,
    primary key (id)
);

create table usr (
    id int8 not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);