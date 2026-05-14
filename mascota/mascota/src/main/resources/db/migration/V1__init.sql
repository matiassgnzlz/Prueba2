CREATE TABLE mascota (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    especie VARCHAR(255),
    raza VARCHAR(255),
    dueno_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO mascota (nombre, especie, raza, dueno_id) VALUES ("lulu", "perro", "bassethound", 1);
INSERT INTO mascota (nombre, especie, raza, dueno_id) VALUES ("cloe", "perro", "westie", 2);
INSERT INTO mascota (nombre, especie, raza, dueno_id) VALUES ("luna", "gato", "mestizo", 3);