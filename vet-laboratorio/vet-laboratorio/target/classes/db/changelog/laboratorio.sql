--liquibase formatted sql
 
--changeset laboratorio:1
 
CREATE TABLE laboratorio (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_laboratorio VARCHAR(255) NOT NULL,
    persona_a_cargo   VARCHAR(255) NOT NULL,
    telefono          VARCHAR(20),
    direccion         VARCHAR(255),
    fecha_registro    DATE         NOT NULL
);
 
--changeset laboratorio:2
 
CREATE TABLE muestra (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_muestra     VARCHAR(100)  NOT NULL,
    descripcion      VARCHAR(500),
    fecha_toma       DATE          NOT NULL,
    estado           VARCHAR(50)   NOT NULL,
    resultado        VARCHAR(500),
    fecha_resultado  DATE,
    laboratorio_id   BIGINT        NOT NULL,
    CONSTRAINT fk_muestra_laboratorio FOREIGN KEY (laboratorio_id) REFERENCES laboratorio(id)
);
 
--changeset laboratorio:3
 
INSERT INTO laboratorio (nombre_laboratorio, persona_a_cargo, telefono, direccion, fecha_registro) VALUES
('Lab Veterinario Central',   'Dra. Ana González',    '+56912345678', 'Av. Principal 123, Santiago',   '2025-01-10'),
('Lab BioAnimal Norte',       'Dr. Luis Martínez',    '+56923456789', 'Calle Norte 456, Concepción',   '2025-02-15'),
('Lab Clínico Mascotas',      'Dra. Sofía Torres',    '+56934567890', 'Pasaje Sur 789, Valparaíso',    '2025-03-20'),
('Lab Diagnóstico Veterinario','Dr. Pedro Ramírez',   '+56945678901', 'Av. Central 321, Temuco',       '2025-04-05'),
('Lab Animal Health',         'Dra. Camila Vargas',   '+56956789012', 'Calle Oriente 654, La Serena',  '2025-05-01');
 
--changeset laboratorio:4
 
INSERT INTO muestra (tipo_muestra, descripcion, fecha_toma, estado, resultado, fecha_resultado, laboratorio_id) VALUES
('Sangre',      'Hemograma completo canino',              '2026-05-01', 'PROCESADA',  'Valores dentro de rango normal',         '2026-05-03', 1),
('Orina',       'Urianálisis felino',                     '2026-05-02', 'PROCESADA',  'Leve proteinuria detectada',             '2026-05-04', 1),
('Heces',       'Coprológico parasitológico',             '2026-05-03', 'PROCESADA',  'Negativo a parásitos intestinales',      '2026-05-05', 2),
('Piel',        'Raspado dérmico cultivo hongos',         '2026-05-04', 'EN PROCESO', NULL,                                     NULL,          2),
('Sangre',      'Perfil bioquímico hepático',             '2026-05-05', 'PROCESADA',  'Elevación leve de ALT',                  '2026-05-07', 3),
('Secreción',   'Cultivo bacteriano herida',              '2026-05-06', 'EN PROCESO', NULL,                                     NULL,          3),
('Sangre',      'Serología Leishmania',                   '2026-05-07', 'PENDIENTE',  NULL,                                     NULL,          4),
('Orina',       'Cultivo bacteriano urinario',            '2026-05-08', 'PROCESADA',  'Escherichia coli sensible a amoxicilina','2026-05-10', 4),
('Tejido',      'Histopatología biopsia cutánea',         '2026-05-09', 'EN PROCESO', NULL,                                     NULL,          5),
('Sangre',      'Tipificación sanguínea transfusión',     '2026-05-10', 'PROCESADA',  'Tipo A positivo',                        '2026-05-11', 5);