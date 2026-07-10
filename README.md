# PruebaFullStack_002v
🚗 Sistema Distribuido de Gestión de Reservas y Reportes (Car Rental MS)

Este proyecto consiste en una arquitectura de microservicios desarrollada en **Java 17** con **Spring Boot 3**, estructurada de forma distribuida para gestionar de extremo a extremo el flujo de rentas de vehículos, clientes, empleados, sucursales, pagos y la generación de reportes consolidados unificados mediante comunicación inter-servicio orientada a APIs REST.

---

## 👥 Integrantes del Equipo
* **Tomás Rodríguez**
* **Christian Montolla**

---

## ⚙️ Instrucciones de Ejecución Local

Para levantar el ecosistema de microservicios de manera correcta en un entorno local, se debe respetar el siguiente orden de ejecución para garantizar que los servicios se registren adecuadamente y las bases de datos se inicialicen:

1. **Clonar el repositorio** y abrir la carpeta raíz `PruebaFullStack_002v` en tu IDE (IntelliJ IDEA, Eclipse o VS Code).
2. **Base de Datos:** Asegurarse de tener un servidor MySQL local corriendo en el puerto `3306` (las credenciales por defecto configuradas son `root` / sin contraseña, o las que estén definidas en los archivos `application.properties`).
3. **Iniciar Eureka Server:** Ejecutar la clase principal del microservicio `eureka_server`. Esperar a que levante en el puerto `8761`.
4. **Iniciar API Gateway:** Ejecutar la clase principal del `api_gateway`. Esperar a que levante en el puerto `8080`.
5. **Iniciar Microservicios de Dominio:** Levantar en cualquier orden los siguientes microservicios. (*Nota: Flyway/Liquibase e Hibernate crearán las tablas e insertarán los datos iniciales automáticamente*).
   * `ms-clientes` (Puerto 8081)
   * `ms-vehiculos` (Puerto 8082)
   * `ms-reservas` (Puerto 8083)
   * `ms-pagos` (Puerto 8084)
   * `ms-sucursales` (Puerto 8085)
   * `ms-empleados` (Puerto 8086)
   * `ms-reportes` (Puerto 8087)

---

## 📚 Documentación de APIs (Swagger / OpenAPI)

El proyecto cuenta con documentación unificada e interactiva de los endpoints a través de **Swagger**. Una vez que el ecosistema completo esté en ejecución (Gateway + Microservicios), puedes acceder a la documentación centralizada desde el navegador:

* **Swagger UI (Centralizado en Gateway):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Desde ese panel principal, podrás desplegar el menú superior derecho y explorar la documentación individual de cada uno de los microservicios sin necesidad de cambiar de puerto.

---

## 🗺️ Arquitectura de Microservicios y Puertos

El ecosistema está compuesto por **7 microservicios** de dominio independientes (autorizado para equipo de 2 integrantes) que se comunican entre sí utilizando **OpenFeign** para llamadas síncronas HTTP, persistencia aislada en bases de datos relacionales administradas mediante **Liquibase** o **Flyway**, y validación estricta de datos de entrada.

| Microservicio | Puerto Local | Descripción Principal | Tecnologías Clave |
| :--- | :---: | :--- | :--- |
| **`ms-clientes`** | `8081` | Gestión y registro de clientes de la plataforma. | Spring Data JPA, MySQL |
| **`ms-vehiculos`** | `8082` | Inventario, catálogo y estados de la flota de vehículos. | Validations, Hibernate |
| **`ms-reservas`** | `8083` | Núcleo del negocio: creación y flujo de estados de reservas. | JPQL Queries, Flyway |
| **`ms-pagos`** | `8084` | Procesamiento y auditoría de transacciones financieras. | Entity Mapping, Flyway |
| **`ms-sucursales`** | `8085` | Control de agencias físicas y estados operativos. | DTO Pattern, Web |
| **`ms-empleados`** | `8086` | Administración del personal y roles internos. | Constructor Injection |
| **`ms-reportes`** | `8087` | Orquestador de datos y consolidación analítica por Feign. | OpenFeign, Liquibase |

---

## 🛠️ Detalle Funcional de cada Microservicio

### 1. `ms-clientes` (Puerto 8081)
Encargado del ciclo de vida del cliente. Permite el alta, modificación, consulta por identificador y borrado lógico.
* **Ruta Gateway:** `/api/clientes/**`
* **Endpoints Clave:**
  * `POST /api/clientes` : Registra un nuevo cliente con validaciones avanzadas (`@Valid`).
  * `GET /api/clientes/{id}` : Recupera la información de perfil de un cliente específico.
  * `GET /api/clientes` : Lista la totalidad de clientes registrados en el sistema.

### 2. `ms-vehiculos` (Puerto 8082)
Administra la flota automotriz disponible para arriendo, segmentada por categorías, marcas y estados de disponibilidad.
* **Ruta Gateway:** `/api/vehiculos/**`
* **Endpoints Clave:**
  * `POST /api/vehiculos` : Inserta un nuevo vehículo al inventario operativo.
  * `GET /api/vehiculos` : Recupera el catálogo completo de autos para los clientes.

### 3. `ms-reservas` (Puerto 8083)
Controla la lógica transaccional de rentas de autos. Implementa una consulta JPQL personalizada para buscar reservas a partir de ventanas temporales específicas y se conecta con el estado base de las reservas creadas por el equipo.
* **Ruta Gateway:** `/api/v1/reservas/**`
* **Endpoints Clave:**
  * `GET /api/v1/reservas` : Retorna el histórico completo de reservas del sistema.
  * `GET /api/v1/reservas/por-fecha?fecha=YYYY-MM-DD` : Query JPQL obligatoria para filtrado dinámico desde una fecha dada.
  * `POST /api/v1/reservas` : Crea una solicitud de reserva vinculando un `clienteId` y un `vehiculoId`.

### 4. `ms-pagos` (Puerto 8084)
Gestiona los cobros asociados a las reservas procesadas, guardando constancia del estado del pago (`APROBADO`, `PENDIENTE`, `RECHAZADO`) y códigos de tracking únicos (`TRX-XXXX`).
* **Ruta Gateway:** `/api/pagos/**`
* **Endpoints Clave:**
  * `GET /api/pagos` : Lista de auditoría de todas las transacciones financieras efectuadas.
  * `POST /api/pagos` : Registra y procesa un nuevo cobro enlazado a una reserva.

### 5. `ms-sucursales` (Puerto 8085)
Mapea la infraestructura física donde los vehículos son retirados y devueltos por los usuarios en el flujo diario.
* **Ruta Gateway:** `/api/v1/sucursales/**`
* **Endpoints Clave:**
  * `GET /api/v1/sucursales` : Obtiene el listado maestro de sucursales del ecosistema.
  * `GET /api/v1/sucursales/operativas` : Filtra únicamente los locales abiertos al público.

### 6. `ms-empleados` (Puerto 8086)
Módulo enfocado en los recursos humanos que operan las sucursales y autorizan las entregas físicas de los vehículos.
* **Ruta Gateway:** `/api/v1/empleados/**`
* **Endpoints Clave:**
  * `POST /api/empleados` : Alta de personal con inyección de dependencias limpia por constructor.
  * `GET /api/empleados` : Listado completo del equipo de trabajo.

### 7. `ms-reportes` (Puerto 8087) — *Orquestador e Integrador del Ecosistema*
El componente analítico centralizado de la arquitectura. Su función principal **no es almacenar datos operativos estáticos**, sino consumir de forma distribuida a los demás servicios mediante **Spring Cloud OpenFeign**, recopilar las colecciones JSON distribuidas en los puertos, y unificarlas mediante lógica de mapeo en un único consolidado global. Posee su propia base de datos de auditoría controlada mediante scripts estructurados de **Liquibase**.
* **Ruta Gateway:** `/api/v1/reportes/**`
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
