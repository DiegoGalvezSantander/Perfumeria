
CREATE TABLE INVENTARIO (
    id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT,
    stock_producto INT NOT NULL
);


CREATE TABLE MOVIMIENTO_STOCK (
    id_movimiento BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT,
    cantidad INT NOT NULL
);