create database LibrarySystem;
Use LibrarySystem;

create table authors(
                        id int auto_increment primary key,
                        author_name varchar(100) not null,
                        author_surname varchar(100) not null,
                        biography text
);
create table categories(
                           id int auto_increment primary key,
                           category_name varchar(50) not null,
                           description text
);

create table users(
                      id int auto_increment primary key,
                      first_name varchar(50) not null,
                      last_name varchar(50) not null,
                      email varchar(100) unique,
                      password varchar(20) not null,
                      role varchar (50) not null,
                      created_at date
);

create table books(
                      id int auto_increment primary key,
                      isbn varchar(13) unique,
                      name varchar(100) not null,
                      publish_year date,
                      author_id int,
                      foreign key (author_id) references authors(id),
                      category_id int,
                      foreign key (category_id) references categories(id)
);

create table loans(
                      id int auto_increment primary key,
                      loan_date date ,
                      due_date date,
                      return_date date,
                      status varchar(50),
                      user_id int,
                      foreign key (user_id) references users(id),
                      book_id int,
                      foreign key(book_id) references books(id)
);
create table fines(
                      id int auto_increment primary key,
                      amount decimal,
                      is_paid boolean,
                      loan_id int,
                      foreign key (loan_id) references loans(id),
                      user_id int,
                      foreign key (user_id) references users(id)
);

