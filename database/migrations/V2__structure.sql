CREATE TABLE fun_fact (
    id int not null auto_increment primary key,
    fact varchar(250) not null,
    insert_date datetime default now()
);
