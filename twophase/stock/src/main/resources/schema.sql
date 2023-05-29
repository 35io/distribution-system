drop table book_stock if exists;

create table book_stock (
    id int not null,
    name varchar(255),
    num int,
    created_date timestamp,
    primary key (id)
);