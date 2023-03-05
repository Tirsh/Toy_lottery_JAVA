DROP TABLE IF EXISTS toys;
CREATE TABLE toys
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    title            VARCHAR,
    quantity         INTEGER DEFAULT 0,
    drop_frequency   INTEGER DEFAULT 50
);

INSERT INTO toys (title, quantity, drop_frequency)
VALUES ('Lego', 5, 20),
       ('Car', 3, 30),
       ('Dog', 1, 40),
       ('Слон', 5, 50),
       ('Гитара', 3, 30),
       ('Frog', 1, 20),
       ('House', 5, 15),
       ('Dendy', 3, 10),
       ('Sega', 1, 22),
       ('Xbox', 5, 30);