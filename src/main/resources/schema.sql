CREATE TABLE IF NOT EXISTS productos
(
    id                   INT PRIMARY KEY,
    nombre               VARCHAR(50)      NOT NULL,
    precio               DOUBLE PRECISION NOT NULL,
    ruta_imagen_producto VARCHAR,
    cliente_id           INT              NOT NULL,
    version              INT
);

CREATE TABLE IF NOT EXISTS carritos
(
    id             INT PRIMARY KEY,
    fecha_creacion DATE        NOT NULL,
    estado         VARCHAR(20) NOT NULL,
    version        INT,
    CHECK (estado IN ('ACTIVO', 'PAGADO', 'CANCELADO'))
);

CREATE TABLE IF NOT EXISTS lineas_carritos
(
    id              INT PRIMARY KEY,
    carrito_id      BIGINT         NOT NULL REFERENCES carritos (id),
    producto_id     INTEGER        NOT NULL REFERENCES productos (id),
    cantidad        INTEGER        NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL,
    version         INT
);