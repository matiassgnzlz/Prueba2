--liquibase formatted sql

--changeset fran:1

CREATE TABLE receta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dueno_id BIGINT NOT NULL,
    nom_mascota VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    fec_emision TIMESTAMP NOT NULL,
    firma_digital VARCHAR(255));

--changeset fran:2

INSERT INTO receta (dueno_id, nom_mascota, descripcion, fec_emision, firma_digital)
VALUES
(1, 'Firulais', 'Vacunación completa al día', '2026-05-01 10:30:00', 'FIRMA001'),

(2, 'Mishi', 'Control veterinario anual aprobado', '2026-05-02 11:00:00', 'FIRMA002'),

(3, 'Rocky', 'Desparasitación realizada correctamente', '2026-05-03 09:15:00', 'FIRMA003'),

(4, 'Luna', 'Certificado de buena salud emitido', '2026-05-04 14:20:00', 'FIRMA004'),

(5, 'Max', 'Chequeo dental satisfactorio', '2026-05-05 16:45:00', 'FIRMA005'),

(6, 'Nala', 'Vacuna antirrábica aplicada', '2026-05-06 08:10:00', 'FIRMA006'),

(7, 'Toby', 'Microchip registrado correctamente', '2026-05-07 13:00:00', 'FIRMA007'),

(8, 'Kiara', 'Control postoperatorio exitoso', '2026-05-08 15:35:00', 'FIRMA008'),

(9, 'Simba', 'Peso y alimentación controlados', '2026-05-09 12:25:00', 'FIRMA009'),

(10, 'Pelusa', 'Examen general sin observaciones', '2026-05-10 17:50:00', 'FIRMA010');
