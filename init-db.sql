CREATE DATABASE IF NOT EXISTS `db_clientes`;
CREATE DATABASE IF NOT EXISTS `db_empleados`;
CREATE DATABASE IF NOT EXISTS `db_pagos`;
CREATE DATABASE IF NOT EXISTS `db_reportes`;
CREATE DATABASE IF NOT EXISTS `db_reservas`;
CREATE DATABASE IF NOT EXISTS `db_sucursales`;
CREATE DATABASE IF NOT EXISTS `db_vehiculos`;

GRANT ALL PRIVILEGES ON `db_clientes`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_empleados`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_pagos`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_reportes`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_reservas`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_sucursales`.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON `db_vehiculos`.* TO 'admin'@'%';
FLUSH PRIVILEGES;
