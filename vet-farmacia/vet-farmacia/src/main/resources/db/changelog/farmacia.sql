--liquibase formatted sql
 
--changeset fran:1
 
CREATE TABLE farmacia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_farmacia VARCHAR(255) NOT NULL
);
 
--changeset fran:2
 
CREATE TABLE empleado_farmacia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_empleado VARCHAR(255) NOT NULL,
    cargo VARCHAR(255),
    farmacia_id BIGINT NOT NULL,
    CONSTRAINT fk_empleado_farmacia FOREIGN KEY (farmacia_id) REFERENCES farmacia(id)
);
 
--changeset fran:3
 
INSERT INTO farmacia (nombre_farmacia) VALUES
('Farmacia Central'),
('Farmacia Norte'),
('Farmacia Sur'),
('Farmacia Veterinaria Plaza'),
('Farmacia Animal Care');
 
--changeset fran:4
 
INSERT INTO empleado_farmacia (nombre_empleado, cargo, farmacia_id) VALUES
('Carlos Pérez',    'Farmacéutico',          1),
('Ana González',    'Asistente',             1),
('Luis Martínez',   'Farmacéutico',          2),
('María López',     'Jefe de Farmacia',      2),
('Pedro Ramírez',   'Asistente',             3),
('Sofía Torres',    'Farmacéutica',          3),
('Diego Herrera',   'Jefe de Farmacia',      4),
('Valentina Ríos',  'Asistente',             4),
('Felipe Castro',   'Farmacéutico',          5),
('Camila Vargas',   'Jefe de Farmacia',      5);
 