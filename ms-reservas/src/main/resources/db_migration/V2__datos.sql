-- V2__datos.sql
INSERT INTO estados_reserva (nombre) VALUES ('PENDIENTE'), ('CONFIRMADA'), ('CANCELADA');

INSERT INTO reservas (codigo_reserva, fecha_inicio, fecha_fin, monto_total, seguro_incluido, estado_id, cliente_id, vehiculo_id)
VALUES ('RES-001', '2026-07-01', '2026-07-10', 500.00, true, 1, 1, 1),
       ('RES-002', '2026-08-01', '2026-08-05', 300.00, false, 2, 2, 2),
       ('RES-003', '2026-09-01', '2026-09-03', 150.00, true, 1, 3, 3);
