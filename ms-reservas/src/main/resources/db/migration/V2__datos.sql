-- Agrega nuevos estados usando la misma estructura limpia
INSERT INTO estados_reserva (nombre_estado, descripcion, permite_modificacion, nivel_prioridad, fecha_creacion)
VALUES
('EN_REVISION', 'Reserva sujeta a validación de antecedentes', true, 4, CURRENT_DATE),
('FINALIZADA', 'El vehículo fue devuelto y la reserva terminó', false, 5, CURRENT_DATE);

-- Agrega nuevas reservas apuntando a los nuevos estados (id 4 e id 5)
INSERT INTO reservas (codigo_reserva, fecha_inicio, fecha_fin, monto_total, seguro_incluido, estado_id, cliente_id, vehiculo_id)
VALUES
('RES-1004', '2026-10-01', '2026-10-05', 200000.00, true, 4, 4, 30),
('RES-1005', '2026-11-10', '2026-11-15', 250000.00, false, 5, 5, 40);