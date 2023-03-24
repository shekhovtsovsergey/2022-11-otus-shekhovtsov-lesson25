drop table if exists BOOKS, COMMENTS CASCADE;
drop table if exists GENRES;
drop table if exists AUTHORS;

CREATE TABLE AUTHORS
(
    ID             BIGSERIAL        PRIMARY KEY,
    NAME           VARCHAR(255)     NOT NULL
);


CREATE TABLE GENRES
(
    ID             BIGSERIAL        PRIMARY KEY,
    NAME           VARCHAR(255)     NOT NULL
);

CREATE TABLE BOOKS
(
    ID             BIGSERIAL        PRIMARY KEY,
    NAME           VARCHAR(255)     NOT NULL,
    AUTHOR_ID      BIGINT           NOT NULL,
    GENRE_ID       BIGINT           NOT NULL,

    CONSTRAINT fk_authors
        FOREIGN KEY (AUTHOR_ID)
            REFERENCES AUTHORS (ID),

    CONSTRAINT fk_GENRES
        FOREIGN KEY (GENRE_ID)
            REFERENCES GENRES (ID)
);


CREATE TABLE COMMENTS
(
    ID          BIGSERIAL    NOT NULL PRIMARY KEY,
    BOOK_ID     BIGINT    NOT NULL,
    AUTHOR_NAME VARCHAR(255) NOT NULL DEFAULT 'ANON',
    COMMENT     VARCHAR(255) NOT NULL,

    CONSTRAINT fk_books_comments
        FOREIGN KEY (BOOK_ID)
            REFERENCES BOOKS (id)
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created TIMESTAMP,
                       updated TIMESTAMP,
                       status VARCHAR(25) NOT NULL DEFAULT 'ACTIVE'
);


CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                       name VARCHAR(100) NOT NULL UNIQUE,
                       created TIMESTAMP,
                       updated TIMESTAMP,
                       status VARCHAR(25) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE users_roles (
                             user_id BIGINT,
                             role_id BIGINT
);


ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_users
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT;

ALTER TABLE users_roles
    ADD CONSTRAINT fk_user_roles_roles
        FOREIGN KEY (role_id)
            REFERENCES roles(id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT;
