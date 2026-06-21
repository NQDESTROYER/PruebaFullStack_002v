-- V1__init.sql
CREATE TABLE IF NOT EXISTS estados_reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_reserva VARCHAR(50) NOT NULL UNIQUE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    monto_total DECIMAL(10, 2) NOT NULL,
    seguro_incluido BOOLEAN NOT NULL,
    estado_id INT NOT NULL,
    cliente_id INT NOT NULL,
    vehiculo_id INT NOT NULL,
    FOREIGN KEY (estado_id) REFERENCES estados_reserva(id)
);
