
CREATE TABLE Roles (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE AUTENTICACION (
    id_auth BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE Autenticacion_roles (
    id_auth BIGINT NOT NULL,
    id_rol BIGINT NOT NULL,
    PRIMARY KEY (id_auth, id_rol),
    CONSTRAINT fk_auth_rol_auth FOREIGN KEY (id_auth) REFERENCES AUTENTICACION(id_auth),
    CONSTRAINT fk_auth_rol_rol FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
);