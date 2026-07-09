CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(10) NOT NULL UNIQUE,
    nombre_completo VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    sueldo_base DECIMAL(10, 2) NOT NULL,
    con_contrato_indefinido BOOLEAN NOT NULL,
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN NOT NULL
);