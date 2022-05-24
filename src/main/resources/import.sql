INSERT INTO known_fruits(id, name) VALUES (1, 'Cherry');
INSERT INTO known_fruits(id, name) VALUES (2, 'Apple');
INSERT INTO known_fruits(id, name) VALUES (3, 'Banana');

CREATE TABLE BASKET (
    id int,
    fruits_id int,
    amount int
);

INSERT INTO BASKET(id, fruits_id ,amount ) VALUES (1, 1,100);
INSERT INTO BASKET(id, fruits_id ,amount ) VALUES (2, 2,20);