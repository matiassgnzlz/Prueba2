CREATE TABLE citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    tipo VARCHAR(100),          
    estado VARCHAR(20) DEFAULT 'PENDIENTE', 
    veterinario_id BIGINT NOT NULL, 
    mascota_id BIGINT NOT NULL,
    dueno_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);