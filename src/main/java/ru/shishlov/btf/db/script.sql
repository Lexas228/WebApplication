
create table people if(
   id BIGSERIAL primary key,
   login varchar(255) unique not null,
   password varchar(255) not null
);

create table people_information(
   id SERIAL primary key,
   person_id BIGINT unique not null,
   name varchar(255) not null,
   surname varchar(255) not null,
   birthday date not null,
   address varchar(255) not null,
   information text not null,
   foreign key (person_id) references people (id) on delete cascade
);

//liquibase