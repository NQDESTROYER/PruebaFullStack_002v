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

-- 3. Población de Estados de Reserva (Los 5 estados juntos)
INSERT INTO estados_reserva (nombre_estado, descripcion, permite_modificacion, nivel_prioridad, fecha_creacion)
VALUES
('CONFIRMADA', 'Reserva pagada y lista para uso', false, 1, CURRENT_DATE),
('PENDIENTE', 'Esperando confirmación de pago', true, 2, CURRENT_DATE),
('CANCELADA', 'Reserva anulada por el cliente', false, 3, CURRENT_DATE),
('EN_REVISION', 'Reserva sujeta a validación de antecedentes', true, 4, CURRENT_DATE),
('FINALIZADA', 'El vehículo fue devuelto y la reserva terminó', false, 5, CURRENT_DATE);

-- 4. Población de Reservas (Las 5 reservas juntas)
INSERT INTO reservas (codigo_reserva, fecha_inicio, fecha_fin, monto_total, seguro_incluido, estado_id, cliente_id, vehiculo_id)
VALUES
('RES-1001', '2025-06-01', '2025-06-05', 150000.00, true, 1, 1, 10),
('RES-1002', '2025-06-10', '2025-06-12', 60000.00, false, 2, 2, 15),
('RES-1003', '2025-07-01', '2025-07-15', 450000.00, true, 3, 3, 22),
('RES-1004', '2026-10-01', '2026-10-05', 200000.00, true, 4, 4, 30),
('RES-1005', '2026-11-10', '2026-11-15', 250000.00, false, 5, 5, 40);