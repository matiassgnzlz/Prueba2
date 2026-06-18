CREATE DATABASE veterinaria_vacunacion;

USE veterinaria_vacunacion;

CREATE TABLE vacunacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mascota_id BIGINT NOT NULL,
    vacuna VARCHAR(100) NOT NULL,
    fecha_aplicacion DATE,
    proxima_dosis DATE
);