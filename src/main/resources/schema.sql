CREATE TABLE IF NOT EXISTS productos
(
    id         INT PRIMARY KEY,
    nombre     VARCHAR(50)      NOT NULL,
    precio     DOUBLE PRECISION NOT NULL,
    cliente_id INT              NOT NULL,
    version    INT
);