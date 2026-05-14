CREATE TABLE historia_clinica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mascota_id BIGINT NOT NULL,
    fecha DATETIME NOT NULL,
    descripcion VARCHAR(255),
    tratamiento VARCHAR(255),
    veterinario VARCHAR(255)
);