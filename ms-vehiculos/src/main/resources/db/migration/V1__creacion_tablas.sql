-- ===================================================================
-- 1. CREACIÓN DE LA TABLA CATEGORIAS
-- ===================================================================
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    prioridad INT NOT NULL,
    activa BOOLEAN DEFAULT TRUE NOT NULL,
    fecha_registro DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- 2. CREACIÓN DE LA TABLA VEHICULOS
-- ===================================================================
CREATE TABLE vehiculos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   patente VARCHAR(10) NOT NULL UNIQUE,
   marca VARCHAR(50) NOT NULL,
   precio_diario DOUBLE NOT NULL,
   disponible BOOLEAN DEFAULT TRUE NOT NULL,
   fecha_ingreso DATE NOT NULL,
   categoria_id INT NOT NULL,
   CONSTRAINT fk_vehiculos_categorias
       FOREIGN KEY (categoria_id)
           REFERENCES categorias(id)
           ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;