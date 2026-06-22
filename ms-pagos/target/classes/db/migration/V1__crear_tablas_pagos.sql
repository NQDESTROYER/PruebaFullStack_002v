-- 1. Tabla Padre: Métodos de Pago
CREATE TABLE metodos_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150) NOT NULL,
    activo BOOLEAN NOT NULL
);

-- 2. Tabla Hija: Pagos (Con referencia al ms-reservas)
CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_transaccion VARCHAR(100) NOT NULL UNIQUE,
    reserva_id INT NOT NULL,       -- Viene de ms-reservas (8083)
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    estado_pago VARCHAR(50) NOT NULL, -- Ej: APROBADO, PENDIENTE, RECHAZADO
    metodo_id INT NOT NULL,
    CONSTRAINT fk_metodo_pago FOREIGN KEY (metodo_id) REFERENCES metodos_pago(id)
);

-- 3. Población de datos inicial (3 filas por tabla según rúbrica)
INSERT INTO metodos_pago (nombre, descripcion, activo)
VALUES
('TARJETA_CREDITO', 'Pago con tarjeta de crédito (Visa, MasterCard)', true),
('TRANSFERENCIA', 'Transferencia bancaria directa a cuenta empresa', true),
('EFECTIVO', 'Pago presencial en efectivo en la sucursal', true);

-- Usamos las IDs de las reservas 1, 2 y 3 que creaste en el ms-reservas
INSERT INTO pagos (codigo_transaccion, reserva_id, monto, fecha_pago, estado_pago, metodo_id)
VALUES
('TRX-9001', 1, 150000.00, CURRENT_TIMESTAMP, 'APROBADO', 1),
('TRX-9002', 2, 60000.00, CURRENT_TIMESTAMP, 'PENDIENTE', 2),
('TRX-9003', 3, 450000.00, CURRENT_TIMESTAMP, 'APROBADO', 1);