
CREATE TABLE VENTA (
    id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(255) NOT NULL,
    total DOUBLE NOT NULL,
    id_pago BIGINT,
    metodo_pago VARCHAR(255) NOT NULL
);


CREATE TABLE Detalle_venta (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    id_venta BIGINT NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES VENTA(id_venta) ON DELETE CASCADE
);