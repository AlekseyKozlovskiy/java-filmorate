CREATE SCHEMA IF NOT EXISTs films;

CREATE TABLE IF NOT EXISTS films.users
(
    id
    int
    primary
    key,
    name
    varchar
    NOT
    NULL,
    birthday
    date
    NOT
    NULL,
    login
    varchar
    NOT
    NULL,
    email
    varchar
    NOT
    NULL,
    CONSTRAINT
    pk_user
    PRIMARY
    KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS films.films
(
    id
    int
    primary
    key,
    name
    varchar
    NOT
    NULL,
    description
    varchar
    NOT
    NULL,
    release_date
    date
    NOT
    NULL,
    duration
    int
    NOT
    NULL,
    rating
    varchar
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS films.friends
(
    user_id
    int
    NOT
    NULL,
    friend_id
    int
    NOT
    NULL,
    approoved
    varchar
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS films.genre
(
    film_id
    int
    NOT
    NULL
    primary
    key,
    genre_id
    int
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS films.genre_type
(
    genre_id
    int
    generated
    by
    default as
    identity
    primary
    key,
    description
    varchar
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS films.likes
(
    film_id
    int
    NOT
    NULL,
    user_id
    int
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS films.rating
(
    rating_id
    int
    primary
    key,
    description
    varchar
    NOT
    NULL
);

ALTER TABLE films.genre
    ADD CONSTRAINT IF NOT EXISTS fk_id FOREIGN KEY (film_id)
    REFERENCES films.films (id) ON
DELETE
CASCADE;

ALTER TABLE films.films
    ADD CONSTRAINT IF NOT EXISTS fk_film_rating FOREIGN KEY (rating)
    REFERENCES films.rating (rating_id) ON
DELETE
CASCADE;

ALTER TABLE films.friends
    ADD CONSTRAINT IF NOT EXISTS fk_friends_user_id FOREIGN KEY (user_id)
    REFERENCES films.users (id) ON
DELETE
CASCADE;

ALTER TABLE films.friends
    ADD CONSTRAINT IF NOT EXISTS fk_friends_id FOREIGN KEY (friend_id)
    REFERENCES films.users (id) ON
DELETE
CASCADE;

ALTER TABLE films.genre
    ADD CONSTRAINT IF NOT EXISTS fk_genre_genre_id FOREIGN KEY (genre_id)
    REFERENCES films.genre_type (genre_id) ON
DELETE
CASCADE;

ALTER TABLE films.likes
    ADD CONSTRAINT IF NOT EXISTS fk_Likes_film_id FOREIGN KEY (film_id)
    REFERENCES films.films (id) ON
DELETE
CASCADE;

ALTER TABLE films.likes
    ADD CONSTRAINT IF NOT EXISTS fk_Likes_user_id FOREIGN KEY (user_id)
    REFERENCES films.users (id) ON
DELETE
CASCADE;


MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('1', 'COMEDY');
MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('2', 'DRAMA');
MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('3', 'COMEDY');
MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('4', 'CARTOON');
MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('5', 'THRILLER');
MERGE INTO FILMS.GENRE_TYPE (GENRE_ID, DESCRIPTION) values ('6', 'ACTION');

MERGE INTO FILMS.RATING (RATING_ID, DESCRIPTION) VALUES ('1', 'G');
MERGE INTO FILMS.RATING (RATING_ID, DESCRIPTION) VALUES ('2', 'PG');
MERGE INTO FILMS.RATING (RATING_ID, DESCRIPTION) VALUES ('3', 'PG_13');
MERGE INTO FILMS.RATING (RATING_ID, DESCRIPTION) VALUES ('4', 'R');
MERGE INTO FILMS.RATING (RATING_ID, DESCRIPTION) VALUES ('5', 'NC_17');

