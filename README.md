# API Fintech - UTN Diplomatura

TP de la Clase 14 de la **Diplomatura en Desarrollo de Software FinTech: IA y Microservicios**.

API REST en Spring Boot que gestiona usuarios y cuentas bancarias (ahorro / corriente) con saldo en USD, y devuelve el saldo equivalente en ARS usando la **cotización compra del dólar MEP** consumida en tiempo real desde [DolarAPI](https://dolarapi.com/).

> **Nota sobre la base de datos:** la consigna sugiere MySQL, pero usé **H2 en modo memoria** (base relacional embebida que vive dentro de la app). La decisión fue para que el proyecto sea totalmente portable: el corrector no necesita instalar ni configurar ningún motor externo ni dar permisos de disco, solo correr la app. La capa JPA es agnóstica al motor, así que cambiar a MySQL/Postgres implica solo modificar el driver y la URL en `application.properties`.

## Tecnologías

- Java 17
- Spring Boot 3.2.5 (Web, Data JPA, Validation)
- H2 Database (modo memoria)
- Hibernate
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito

## Requisitos previos

- Java 17
- Maven 3.8+

> No hace falta instalar ninguna base de datos: H2 viene embebida en el proyecto.

## Cómo levantarlo localmente

```bash
mvn spring-boot:run
```

La app queda disponible en `http://localhost:8080`. La primera vez Hibernate crea las tablas automáticamente y `data.sql` inserta un usuario de prueba (id=1).

### Usuario de prueba precargado

Al arrancar la app, el script `src/main/resources/data.sql` carga automáticamente un usuario:

```sql
INSERT INTO usuarios (nombre, apellido, email, dni)
VALUES ('Juan', 'Perez', 'juan.perez@mail.com', '30123456');
```

Es el `usuarioId=1` que se usa en los ejemplos de POST `/cuentas`.

### Inspeccionar la base (opcional)

Con la app corriendo, abrir `http://localhost:8080/h2-console` y usar:

- **JDBC URL**: `jdbc:h2:mem:fintech_db`
- **User**: `sa`
- **Password**: *(vacío)*

## Documentación Swagger / OpenAPI

Con la app corriendo:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Endpoints

| Método | Endpoint         | Descripción                                                |
|--------|------------------|------------------------------------------------------------|
| GET    | /cuentas         | Lista todas las cuentas con saldo en USD y en ARS (MEP)    |
| GET    | /cuentas/{id}    | Devuelve una cuenta por ID (404 si no existe)              |
| POST   | /cuentas         | Crea una cuenta (AHORRO o CORRIENTE) asociada a un usuario |
| PUT    | /cuentas/{id}    | Actualiza el saldo USD de una cuenta                       |
| DELETE | /cuentas/{id}    | Elimina una cuenta                                         |

### Ejemplo POST /cuentas

```json
{
  "tipoCuenta": "AHORRO",
  "saldoUSD": 1000.0,
  "usuarioId": 1,
  "limiteExtraccionDiario": 500.0
}
```

Respuesta:

```json
{
  "id": 1,
  "numeroCuenta": "CBU-A1B2C3D4",
  "tipoCuenta": "AHORRO",
  "saldoUSD": 1000.0,
  "saldoARS": 1185000.0,
  "tasaMEP": 1185.0,
  "nombreUsuario": "Juan Perez"
}
```

## Colección de Postman

Importar en Postman el archivo:

```
postman/fintech-api.postman_collection.json
```

Incluye los 6 requests (GET listar, GET por id, POST AHORRO, POST CORRIENTE, PUT, DELETE) con la variable `baseUrl = http://localhost:8080`.

## Tests

```bash
mvn test
```

Cubre:
- **Service (unitarios con Mockito):** `CuentaServiceImplTest` — listar, obtener, crear (caso ok, usuario inexistente, tipo inválido), eliminar (ok y 404).
- **Controller (slice test con MockMvc):** `CuentaControllerTest` — códigos HTTP y manejo de excepciones (200, 201, 204, 404).

## Estructura del proyecto

```
src/
├── main/java/com/utn/fintech/
│   ├── config/         AppConfig (RestTemplate), SwaggerConfig
│   ├── controller/     CuentaController
│   ├── dto/            CuentaRequestDTO, CuentaResponseDTO, DolarMepDTO
│   ├── exception/      CuentaNoEncontradaException, UsuarioNoEncontradoException,
│   │                   TipoCuentaInvalidoException, DolarApiException,
│   │                   GlobalExceptionHandler
│   ├── model/          Convertible (interfaz), Cuenta (abstracta),
│   │                   CuentaAhorro, CuentaCorriente, Usuario
│   ├── repository/     CuentaRepository, UsuarioRepository (Spring Data JPA)
│   └── service/        CuentaService + Impl, DolarApiClient + Impl
├── main/resources/     application.properties
└── test/java/com/utn/fintech/
    ├── controller/     CuentaControllerTest
    └── service/        CuentaServiceImplTest
postman/                Colección de Postman lista para importar
```

## Decisiones de diseño

- **Herencia:** `Cuenta` es abstracta con dos subclases (`CuentaAhorro`, `CuentaCorriente`) usando `@Inheritance(strategy = JOINED)` (una tabla por subclase, más normalizado).
- **Interfaz `Convertible`:** define `convertirARS(double tasa)` para desacoplar la conversión de moneda del modelo.
- **`saldoARS` es `@Transient`:** no se persiste porque depende de una cotización que cambia constantemente; se calcula al momento de la respuesta.
- **DTOs separados** para request y response: evita exponer la entidad y permite validar entradas con `jakarta.validation`.
- **Manejo de errores centralizado** en `GlobalExceptionHandler` con excepciones custom (`CuentaNoEncontradaException`, `UsuarioNoEncontradoException`, `TipoCuentaInvalidoException`, `DolarApiException`).
- **Cliente DolarAPI** envuelto en una interfaz (`DolarApiClient`) para poder mockearlo en los tests.

## Sobre el uso de IA

Para la investigación de la API de cotizaciones, la estructura inicial del proyecto y la generación de los esqueletos de tests me apoyé en asistentes de IA (Copilot / ChatGPT), tal como vimos en las unidades 3 y 7 de la diplomatura. Las decisiones de diseño, el modelado del dominio y la integración final son propias.
