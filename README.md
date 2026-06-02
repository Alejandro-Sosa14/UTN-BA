# API Fintech - UTN Diplomatura

API REST para gestión de cuentas bancarias con conversión de saldo USD a ARS usando la cotización MEP de DolarAPI.

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- MySQL
- JPA / Hibernate
- Swagger / OpenAPI
- JUnit 5 + Mockito

## Requisitos previos

- Java 17 instalado
- MySQL corriendo en `localhost:3306`
- Maven instalado

## Configuración

1. Crear la base de datos (opcional, se crea automáticamente):
```sql
CREATE DATABASE fintech_db;
```

2. Editar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

## Cómo ejecutar

```bash
mvn spring-boot:run
```

La API queda disponible en: `http://localhost:8080`

## Documentación Swagger

Una vez corriendo la app, acceder a:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints

| Método | Endpoint         | Descripción                        |
|--------|------------------|------------------------------------|
| GET    | /cuentas         | Listar todas las cuentas           |
| GET    | /cuentas/{id}    | Obtener una cuenta por ID          |
| POST   | /cuentas         | Crear una nueva cuenta             |
| PUT    | /cuentas/{id}    | Actualizar el saldo de una cuenta  |
| DELETE | /cuentas/{id}    | Eliminar una cuenta                |

## Ejemplo de creación de cuenta (POST /cuentas)

```json
{
  "tipoCuenta": "AHORRO",
  "saldoUSD": 1000.0,
  "usuarioId": 1,
  "limiteExtraccionDiario": 500.0
}
```

## Respuesta con conversión MEP

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

## Tests

```bash
mvn test
```

## Estructura del proyecto

```
src/
├── main/java/com/utn/fintech/
│   ├── config/         # AppConfig, SwaggerConfig
│   ├── controller/     # CuentaController
│   ├── dto/            # CuentaRequestDTO, CuentaResponseDTO, DolarMepDTO
│   ├── exception/      # CuentaNoEncontradaException, GlobalExceptionHandler
│   ├── model/          # Cuenta (abstract), CuentaAhorro, CuentaCorriente, Usuario, Convertible
│   ├── repository/     # CuentaRepository, UsuarioRepository
│   └── service/        # CuentaService, CuentaServiceImpl, DolarApiClient, DolarApiClientImpl
└── test/java/com/utn/fintech/
    ├── controller/     # CuentaControllerTest
    └── service/        # CuentaServiceImplTest
```

