
CREATE TABLE Marcas (
    id_marca BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_marca VARCHAR(255)
);


CREATE TABLE Tipo_fragancias (
    id_fragancia BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_fragancia VARCHAR(255)
);


CREATE TABLE PRODUCTO (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_producto VARCHAR(255),
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    id_marca BIGINT,
    id_fragancia BIGINT,
    CONSTRAINT fk_producto_marca FOREIGN KEY (id_marca) REFERENCES Marcas(id_marca),
    CONSTRAINT fk_producto_fragancia FOREIGN KEY (id_fragancia) REFERENCES Tipo_fragancias(id_fragancia)
);