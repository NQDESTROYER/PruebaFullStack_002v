-- Creación de la tabla Regiones
CREATE TABLE regiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_region VARCHAR(255) NOT NULL,
    codigo_iso VARCHAR(50),
    numero_comunas INT,
    zona_extrema BOOLEAN NOT NULL,
    fecha_creacion DATE
);

-- Creación de la tabla Sucursales con Foreign Key a Regiones
CREATE TABLE sucursales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    capacidad_autos INT NOT NULL,
    operativa BOOLEAN NOT NULL,
    fecha_apertura DATETIME NOT NULL,
    region_id INT NOT NULL,
    CONSTRAINT fk_region_sucursal FOREIGN KEY (region_id) REFERENCES regiones(id)
);