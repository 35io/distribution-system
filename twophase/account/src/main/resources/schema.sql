drop table account if exists;

create table account (
    id int not null,
    name varchar(100),
    num int,
    created_date timestamp,
    primary key (id)
);