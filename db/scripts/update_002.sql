CREATE TABLE if not exists categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE if not exists items_categories (
    item_id integer not null references items,
    categories_id integer not null references categories,
    primary key (item_id, categories_id)
);

INSERT INTO categories(name) VALUES ('Домашние');
INSERT INTO categories(name) VALUES ('Хобби');
INSERT INTO categories(name) VALUES ('Бизнес');
INSERT INTO categories(name) VALUES ('Работа');