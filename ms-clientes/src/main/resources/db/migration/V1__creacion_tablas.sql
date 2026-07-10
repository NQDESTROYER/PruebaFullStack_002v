-- ===================================================================
-- 1. CREACIÓN DE LA TABLA CLIENTES
-- ===================================================================
CREATE TABLE clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  rut VARCHAR(12) NOT NULL UNIQUE,
  nombre_completo VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  ingreso_mensual DECIMAL(15,2) NOT NULL,
  activo BOOLEAN DEFAULT TRUE NOT NULL,
  fecha_nacimiento DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- 2. CREACIÓN DE LA TABLA DIRECCIONES
-- ===================================================================
CREATE TABLE direcciones (
 id INT AUTO_INCREMENT PRIMARY KEY,
 calle VARCHAR(150) NOT NULL,
 numero INT NOT NULL,
 ciudad VARCHAR(100) NOT NULL,
 principal BOOLEAN DEFAULT FALSE NOT NULL,
 fecha_registro DATETIME NOT NULL,
 cliente_id INT NOT NULL,
 CONSTRAINT fk_direcciones_clientes
     FOREIGN KEY (cliente_id)
         REFERENCES clientes(id)
         ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;