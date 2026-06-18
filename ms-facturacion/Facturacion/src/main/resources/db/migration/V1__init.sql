CREATE DATABASE veterinaria_facturacion;

USE veterinaria_facturacion;

CREATE TABLE facturas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mascota_id BIGINT NOT NULL,
    cita_id BIGINT NOT NULL,
    monto DOUBLE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha DATE
);