CREATE TABLE dueno (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(50),
    rut VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO dueno (rut, nombre, contacto) VALUES 
('11.111.111-1', 'Alberto Hurtado', '+56911112222'),
('22.222.222-2', 'María Ignacia', '+56933334444'),
('33.333.333-3', 'Roberto Gomez', '+56955556666');