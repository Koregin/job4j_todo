CREATE TABLE if not exists items (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created TIMESTAMP NOT NULL,
    done BOOLEAN NOT NULL
);

ALTER TABLE items ALTER COLUMN done SET DEFAULT FALSE;

INSERT INTO items(description, created, done) VALUES ('Task 1', '2022-05-15', false);
INSERT INTO items(description, created, done) VALUES ('Task 2', '2022-05-16', false);
INSERT INTO items(description, created, done) VALUES ('Task 3', '2022-05-17', false);