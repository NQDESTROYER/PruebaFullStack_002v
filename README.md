# PruebaFullStack_002v
🚗 Sistema Distribuido de Gestión de Reservas y Reportes (Car Rental MS)

Este proyecto consiste en una arquitectura de microservicios desarrollada en **Java 17** con **Spring Boot 3**, estructurada de forma distribuida para gestionar de extremo a extremo el flujo de rentas de vehículos, clientes, empleados, sucursales, pagos y la generación de reportes consolidados unificados mediante comunicación inter-servicio orientada a APIs REST.

---

## 🗺️ Arquitectura de Microservicios y Puertos

El ecosistema está compuesto por **7 microservicios** independientes que se comunican entre sí utilizando **OpenFeign** para llamadas síncronas HTTP, persistencia aislada en bases de datos relacionales administradas mediante **Liquibase** o **Flyway**, y validación estricta de datos de entrada.

| Microservicio | Puerto Local | Descripción Principal | Tecnologías Clave |
| :--- | :---: | :--- | :--- |
| **`ms-clientes`** | `8081` | Gestión y registro de clientes de la plataforma. | Spring Data JPA, MySQL |
| **`ms-vehiculos`** | `8082` | Inventario, catálogo y estados de la flota de vehículos. | Validations, Hibernate |
| **`ms-reservas`** | `8083` | Núcleo del negocio: creación y flujo de estados de reservas. | JPQL Queries, Lombok |
| **`ms-pagos`** | `8084` | Procesamiento y auditoría de transacciones financieras. | Entity Mapping, REST |
| **`ms-sucursales`** | `8085` | Control de agencias físicas y estados operativos. | DTO Pattern, Web |
| **`ms-empleados`** | `8080` | Administración del personal y roles internos. | Constructor Injection |
| **`ms-reportes`** | `8087` | Orquestador de datos y consolidación analítica por Feign. | OpenFeign, Liquibase |

---

## 🛠️ Detalle Funcional de cada Microservicio

### 1. `ms-clientes` (Puerto 8081)
Encargado del ciclo de vida del cliente. Permite el alta, modificación, consulta por identificador y borrado lógico.
* **Endpoints Clave:**
  * `POST /api/clientes` : Registra un nuevo cliente con validaciones avanzadas (`@Valid`).
  * `GET /api/clientes/{id}` : Recupera la información de perfil de un cliente específico.
  * `GET /api/clientes` : Lista la totalidad de clientes registrados en el sistema.

### 2. `ms-vehiculos` (Puerto 8082)
Administra la flota automotriz disponible para arriendo, segmentada por categorías, marcas y estados de disponibilidad.
* **Endpoints Clave:**
  * `POST /api/vehiculos` : Inserta un nuevo vehículo al inventario operativo.
  * `GET /api/vehiculos` : Recupera el catálogo completo de autos para los clientes.

### 3. `ms-reservas` (Puerto 8083)
Controla la lógica transaccional de rentas de autos. Implementa una consulta JPQL personalizada para buscar reservas a partir de ventanas temporales específicas y se conecta con el estado base de las reservas creadas por el equipo.
* **Endpoints Clave:**
  * `GET /api/v1/reservas` : Retorna el histórico completo de reservas del sistema.
  * `GET /api/v1/reservas/por-fecha?fecha=YYYY-MM-DD` : Query JPQL obligatoria para filtrado dinámico desde una fecha dada.
  * `POST /api/v1/reservas` : Crea una solicitud de reserva vinculando un `clienteId` y un `vehiculoId`.

### 4. `ms-pagos` (Puerto 8084)
Gestiona los cobros asociados a las reservas procesadas, guardando constancia del estado del pago (`APROBADO`, `PENDIENTE`, `RECHAZADO`) y códigos de tracking únicos (`TRX-XXXX`).
* **Endpoints Clave:**
  * `GET /api/pagos` : Lista de auditoría de todas las transacciones financieras efectuadas.
  * `POST /api/pagos` : Registra y procesa un nuevo cobro enlazado a una reserva.

### 5. `ms-sucursales` (Puerto 8085)
Mapea la infraestructura física donde los vehículos son retirados y devueltos por los usuarios en el flujo diario.
* **Endpoints Clave:**
  * `GET /api/v1/sucursales` : Obtiene el listado maestro de sucursales del ecosistema.
  * `GET /api/v1/sucursales/operativas` : Filtra únicamente los locales abiertos al público.

### 6. `ms-empleados` (Puerto 8080)
Módulo enfocado en los recursos humanos que operan las sucursales y autorizan las entregas físicas de los vehículos.
* **Endpoints Clave:**
  * `POST /api/empleados` : Alta de personal con inyección de dependencias limpia por constructor.
  * `GET /api/empleados` : Listado completo del equipo de trabajo.

### 7. `ms-reportes` (Puerto 8087) — *Orquestador e Integrador del Ecosistema*
El componente analítico centralizado de la arquitectura. Su función principal **no es almacenar datos operativos estáticos**, sino consumir de forma distribuida a los demás servicios mediante **Spring Cloud OpenFeign**, recopilar las colecciones JSON distribuidas en los puertos, y unificarlas mediante lógica de mapeo en un único consolidado global. Posee su propia base de datos de auditoría controlada mediante scripts estructurados de **Liquibase**.

* **Endpoint Core de Integración:**
  * `GET /api/v1/reportes/consolidado` : Dispara las llamadas concurrentes a `ms-pagos` y `ms-reservas`, calcula totales unificados de registros y genera el balance analítico.

---

## 🚀 Flujo de Comunicación Inter-Servicio (OpenFeign)

El componente `ms-reportes` actúa como un punto único de agregación consumiendo las APIs distribuidas para responder de manera óptima al cliente:

1. El usuario o Postman realiza una petición `GET` a `/api/v1/reportes/consolidado` en el puerto `8087`.
2. `ms-reportes` activa de manera transparente sus clientes declarativos:
   * **`PagoClient`** apunta a la URL real `http://localhost:8084/api/pagos`
   * **`ReservaClient`** apunta a la URL real `http://localhost:8083/api/v1/reservas`
3. Los microservicios responden con sus respectivas colecciones mapeadas nativas.
4. `ms-reportes` procesa e intersecta la data devolviendo un código **HTTP 200 OK** con los totales globales y el desglose de listas en paralelo.

---

## 📊 Evidencia de Funcionamiento de Integración (Postman de Éxito)

A continuación, se integra la captura de pantalla real del sistema en ejecución, donde se evidencia el correcto consumo de la API consolidada, la interconexión exitosa de los puertos y la respuesta unificada con datos reales del ecosistema distribuido:

<img width="1063" height="1212" alt="Captura de pantalla 2026-05-18 005046" src="https://github.com/user-attachments/assets/1df1089f-c7aa-4898-bab8-958351f82712" />
