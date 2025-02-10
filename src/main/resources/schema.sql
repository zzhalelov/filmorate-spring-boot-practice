drop table if exists likes;
drop table if exists friends;
drop table if exists film_genres;
drop table if exists films;
drop table if exists ratings;
drop table if exists users;
drop table if exists genres;

create table if not exists users
(
    id       serial primary key,
    email    varchar not null unique,
    login    varchar not null unique,
    name     varchar not null,
    birthday date    not null
);

create table if not exists ratings
(
    id   serial primary key,
    name varchar not null unique
        check ( name in ('G', 'PG', 'PG-13', 'R', 'NC-17'))
);
create table if not exists genres
(
    id   serial primary key,
    name varchar not null unique
);

create table if not exists films
(
    id           serial primary key,
    name         varchar not null,
    description  varchar not null,
    release_date date    not null,
    duration     int     not null,
    rating_id    int references ratings (id)
);

create table if not exists likes
(
    id      serial primary key,
    film_id int references films (id),
    user_id int references users (id)
);

create table if not exists friends
(
    id        serial primary key,
    user_id   int references users (id),
    friend_id int references users (id)
);

create table if not exists film_genres
(
    id       serial primary key,
    film_id  int references films (id),
    genre_id int references genres (id)
);