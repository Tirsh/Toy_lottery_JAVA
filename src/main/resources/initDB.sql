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
       ('Dog', 1, 40);