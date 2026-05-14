CREATE TABLE veterinario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO veterinario (nombre, especialidad, matricula) VALUES ('rodrigo', 'cirujano', 'VT0910');
INSERT INTO veterinario (nombre, especialidad, matricula) VALUES ('ignacio', 'recepcion', 'VT2008');
INSERT INTO veterinario (nombre, especialidad, matricula) VALUES ('jacqueline', 'ayudante', 'VT0303');