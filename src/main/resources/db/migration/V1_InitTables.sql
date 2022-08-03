create extension if not exists "uuid-ossp";
select uuid_generate_v4();

create table if not exists customer (
    id uuid default uuid_generate_v4(),
    first_name varchar(20) NOT NULL,
    last_name varchar(20) NOT NULL,
    registered_on timestamp NOT NULL,
    primary key(id)
);