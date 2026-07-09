-- 1. Tabla Padre: Estados de Reserva
CREATE TABLE IF NOT EXISTS estados_reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150) NOT NULL,
    permite_modificacion BOOLEAN NOT NULL,
    nivel_prioridad INT NOT NULL,
    fecha_creacion DATE NOT NULL
);

-- 2. Tabla Hija: Reservas
CREATE TABLE IF NOT EXISTS reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_reserva VARCHAR(50) NOT NULL UNIQUE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    seguro_incluido BOOLEAN NOT NULL,
    estado_id INT NOT NULL,
    cliente_id INT NOT NULL,   -- ms-clientes
    vehiculo_id INT NOT NULL,  -- ms-vehiculos
    CONSTRAINT fk_estado_reserva FOREIGN KEY (estado_id) REFERENCES estados_reserva(id)
);

-- 3. Población de datos inicial (Consistente con las columnas)
INSERT INTO estados_reserva (nombre_estado, descripcion, permite_modificacion, nivel_prioridad, fecha_creacion)
VALUES
('CONFIRMADA', 'Reserva pagada y lista para uso', false, 1, CURRENT_DATE),
('PENDIENTE', 'Esperando confirmación de pago', true, 2, CURRENT_DATE),
('CANCELADA', 'Reserva anulada por el cliente', false, 3, CURRENT_DATE);

INSERT INTO reservas (codigo_reserva, fecha_inicio, fecha_fin, monto_total, seguro_incluido, estado_id, cliente_id, vehiculo_id)
VALUES
('RES-1001', '2025-06-01', '2025-06-05', 150000.00, true, 1, 1, 10),
('RES-1002', '2025-06-10', '2025-06-12', 60000.00, false, 2, 2, 15),
('RES-1003', '2025-07-01', '2025-07-15', 450000.00, true, 3, 3, 22);