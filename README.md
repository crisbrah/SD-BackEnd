# SanidadDivina — Backend API REST

Sistema de gestión para iglesia: miembros, asistencias, finanzas, cursos, eventos y más.

---

## Stack tecnológico

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.3.5 |
| Spring Security | JWT stateless |
| PostgreSQL | (servidor local / red) |
| Hibernate / JPA | via Spring Data |
| MapStruct | 1.5.5 |
| Lombok | última compatible |
| OpenFeign | cliente HTTP para RENIEC |
| SpringDoc OpenAPI | 2.6.0 (Swagger UI) |

---

## Arquitectura

El proyecto usa **Arquitectura Hexagonal (Ports & Adapters)**:

```
src/main/java/com/codigo/sanidaddivina/
├── domain/
│   ├── model/          → Modelos de dominio puros (sin anotaciones JPA)
│   └── port/
│       ├── in/         → Interfaces de casos de uso (entrada)
│       └── out/        → Interfaces de repositorios y servicios externos (salida)
├── application/
│   └── usecase/        → Implementaciones de casos de uso (@Service)
├── infrastructure/
│   ├── adapter/
│   │   ├── in/rest/    → Controladores REST (@RestController)
│   │   └── out/
│   │       ├── persistence/  → Adaptadores JPA + MapStruct mappers
│   │       └── external/     → Clientes externos (RENIEC, huellero)
│   └── exception/      → GlobalExceptionHandler + ApiResponse
├── entities/           → Entidades JPA (@Entity)
├── dao/                → Spring Data JPA repositories
├── seguridad/          → JWT: filtros, servicios, configuración de Spring Security
└── request/ / response/ → DTOs de entrada/salida (capa legacy)
```

---

## Configuración (`application.properties`)

```properties
# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://192.168.1.51:5432/iglesia
spring.datasource.username=postgres
spring.datasource.password=

# Hibernate
spring.jpa.hibernate.ddl-auto=update   # crea/actualiza tablas automáticamente
spring.jpa.show-sql=true

# JWT
key.token=85732b878c0f544da4a863804775ef3914e8ccb82b08820a278302c5b826e291
jwt.expiration=28800000          # 8 horas (en ms)
jwt.refresh-expiration=604800000 # 7 días (en ms)

# API RENIEC (api.decolecta.com)
token.reniec=sk_13428.fvzGCaXkBHkopaPBjtf3xaxvJ131fZMP

# Huellero biométrico
fingerprint.enabled=false        # true = hardware real, false = modo stub

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

> **Importante:** Cambiar `spring.datasource.url` si la DB está en otra IP/puerto.

---

## Levantar el proyecto

### Opción A — IntelliJ IDEA
1. Abrir el proyecto
2. Esperar que Maven descargue dependencias
3. Run → `SanidadDivinaApplication`

### Opción B — Maven CLI
```bash
cd SanidadDivina
mvn spring-boot:run
```

La app inicia en: **`http://localhost:8080`**

Swagger UI: **`http://localhost:8080/swagger-ui/index.html`**

---

## Primer uso — Crear el administrador principal

### ¿Dónde se guardan las credenciales?

Las credenciales del administrador se guardan en la base de datos PostgreSQL:

| Tabla | Qué guarda |
|---|---|
| `persona` | Nombres, apellidos, DNI del admin |
| `miembro` | Email + contraseña encriptada (BCrypt) |
| `miembro_rol` | Relación miembro ↔ rol (SUPER_ADMIN) |

> La contraseña **nunca se guarda en texto plano**. Se encripta con BCrypt antes de persistirse.
> No existe ningún archivo de configuración con credenciales: todo va a la BD.

---

### ¿Qué hace `/auth/signup`?

- Crea una fila en `persona` y una en `miembro`
- Devuelve un JWT válido
- **No asigna ningún rol** automáticamente

El miembro recién creado puede usar ese JWT para endpoints que solo requieran estar autenticado
(como asignar roles), pero **no puede acceder** a endpoints que requieran un rol específico
hasta que se le asigne uno.

---

### Paso a paso — primera vez

#### 1. Registrar el administrador principal

```http
POST http://localhost:8080/auth/signup
Content-Type: application/json

{
  "email": "admin@iglesia.com",
  "password": "Admin123!",
  "nombres": "Admin",
  "apePat": "Principal",
  "apeMat": "Sistema",
  "dni": "12345678",
  "fechaNacimiento": "1990-01-01"
}
```

Respuesta `201 Created`:
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGci...",
    "refreshToken": "eyJhbGci..."
  }
}
```

> Guarda este token. Lo usarás en el siguiente paso.

---

#### 2. Asignar el rol SUPER_ADMIN

> Los roles **ya existen** en la BD desde que levantaste la app (los inserta `docker/init.sql`).
> Solo necesitas asignar el rol al miembro que acabas de crear.

```http
POST http://localhost:8080/api/v1/roles/asignaciones
Authorization: Bearer <token del paso anterior>
Content-Type: application/json

{
  "miembroId": 1,
  "rolId": 1
}
```

> `rolId: 1` = SUPER_ADMIN (primer rol insertado por init.sql).
> `miembroId: 1` = primer miembro creado. Si no sabes el ID, consulta la BD:
> ```sql
> SELECT id_miembro FROM miembro WHERE email = 'admin@iglesia.com';
> SELECT id_rol FROM rol WHERE nombre_rol = 'SUPER_ADMIN';
> ```

---

#### 3. Iniciar sesión (siempre que el token expire)

El token dura **8 horas**. Cuando expire, obtén uno nuevo:

```http
POST http://localhost:8080/auth/signin
Content-Type: application/json

{
  "email": "admin@iglesia.com",
  "password": "Admin123!"
}
```

---

#### 4. Renovar token sin re-autenticar

```http
POST http://localhost:8080/auth/refresh-token
Content-Type: application/json

{
  "refreshToken": "eyJhbGci..."
}
```

El refresh token dura **7 días**.

---

### Roles disponibles en el sistema

Los roles se insertan automáticamente al arrancar la app (`docker/init.sql`):

| ID | Rol | Permisos principales |
|---|---|---|
| 1 | `SUPER_ADMIN` | Todo, incluyendo eliminar |
| 2 | `ADMIN` | CRUD completo excepto eliminar |
| 3 | `TESORERO` | Módulo financiero |
| 4 | `ENCARGADO` | Registro de asistencias |
| 5 | `MAESTRO` | Gestión de cursos y notas |

---

## Uso del token en Postman / Swagger

En cada petición a `/api/v1/**` agregar el header:

```
Authorization: Bearer eyJhbGci...
```

En **Postman**: pestaña **Authorization** → tipo **Bearer Token** → pegar el token.

En **Swagger UI**: botón **Authorize** (arriba a la derecha) → escribir `Bearer eyJhbGci...`

---

## Seguridad y roles

### Endpoints públicos (sin token)
| Endpoint | Descripción |
|---|---|
| `POST /auth/signup` | Crear cuenta — no asigna rol |
| `POST /auth/signin` | Iniciar sesión |
| `POST /auth/refresh-token` | Renovar access token |
| `GET /swagger-ui/**` | Documentación interactiva |
| `GET /v3/api-docs/**` | Especificación OpenAPI |

### Endpoints protegidos por rol

| Operación | Roles requeridos |
|---|---|
| `DELETE /api/v1/**` | Solo `SUPER_ADMIN` |
| `POST/GET /api/v1/ingresos/**` | `TESORERO`, `ADMIN`, `SUPER_ADMIN` |
| `POST/GET /api/v1/egresos/**` | `TESORERO`, `ADMIN`, `SUPER_ADMIN` |
| `GET /api/v1/reportes/financiero/**` | `TESORERO`, `ADMIN`, `SUPER_ADMIN` |
| `POST /api/v1/asistencias/**` | `ENCARGADO`, `ADMIN`, `SUPER_ADMIN` |
| `POST /api/v1/sesiones-culto/**` | `ENCARGADO`, `ADMIN`, `SUPER_ADMIN` |
| `POST/PUT /api/v1/cursos/**` | `MAESTRO`, `ADMIN`, `SUPER_ADMIN` |
| Todo lo demás | Cualquier usuario autenticado |

---

## Referencia completa de endpoints

### Autenticación — `/auth`
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| POST | `/auth/signup` | No | Crear cuenta + obtener token (sin rol) |
| POST | `/auth/signin` | No | Login → token |
| POST | `/auth/refresh-token` | No | Renovar token con refreshToken |

### Personas — `/api/v1/personas`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/personas` | Crear persona (enriquece nombres desde RENIEC) |
| GET | `/api/v1/personas/{id}` | Buscar por ID |
| GET | `/api/v1/personas?page=0&size=20` | Listar paginado |
| PUT | `/api/v1/personas/{id}` | Actualizar |
| DELETE | `/api/v1/personas/{id}` | Baja lógica (solo SUPER_ADMIN) |
| GET | `/api/v1/personas/cumpleanios/hoy` | Cumpleaños de hoy |
| GET | `/api/v1/personas/cumpleanios/mes/{mes}` | Cumpleaños del mes |
| GET | `/api/v1/personas/cumpleanios?dias=30` | Próximos cumpleaños |

> Body mínimo para crear persona:
> ```json
> { "dni": "12345678", "fechaNacimiento": "1990-01-01" }
> ```
> Los nombres los completa automáticamente desde RENIEC.

### Miembros — `/api/v1/miembros`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/miembros` | Registrar miembro (requiere personaId) |
| GET | `/api/v1/miembros/{id}` | Buscar por ID |
| GET | `/api/v1/miembros` | Listar paginado |
| PUT | `/api/v1/miembros/{id}` | Actualizar |
| DELETE | `/api/v1/miembros/{id}` | Baja lógica (SUPER_ADMIN) |
| POST | `/api/v1/miembros/{miembroId}/profesor` | Promover a profesor |

### Roles — `/api/v1/roles`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/roles` | Crear rol |
| GET | `/api/v1/roles/{id}` | Buscar rol |
| GET | `/api/v1/roles` | Listar roles |
| PUT | `/api/v1/roles/{id}` | Actualizar rol |
| DELETE | `/api/v1/roles/{id}` | Eliminar rol (SUPER_ADMIN) |
| POST | `/api/v1/roles/asignaciones` | Asignar rol a miembro |
| GET | `/api/v1/roles/asignaciones/miembro/{miembroId}` | Ver roles de un miembro |
| DELETE | `/api/v1/roles/asignaciones/{id}` | Quitar rol (SUPER_ADMIN) |

### Sesiones de Culto — `/api/v1/sesiones-culto`
| Método | Ruta | Rol mínimo | Descripción |
|---|---|---|---|
| POST | `/api/v1/sesiones-culto` | ENCARGADO | Abrir nueva sesión de culto |
| PUT | `/api/v1/sesiones-culto/{id}/cerrar` | ENCARGADO | Cerrar sesión (registra hora de fin) |
| GET | `/api/v1/sesiones-culto/abierta` | Autenticado | Obtener la sesión activa ahora |
| GET | `/api/v1/sesiones-culto` | Autenticado | Listar todas las sesiones |
| GET | `/api/v1/sesiones-culto?sedeId=1` | Autenticado | Filtrar por sede |
| DELETE | `/api/v1/sesiones-culto/{id}` | SUPER_ADMIN | Eliminar sesión cerrada |

> Body para abrir sesión:
> ```json
> {
>   "nombreSesion": "Culto Dominical 23/02",
>   "tipoCulto": "DOMINICAL",
>   "sedeId": 1
> }
> ```
> Tipos disponibles: `DOMINICAL`, `ORACION`, `JOVEN`, `NINOS`, `EVANGELISMO`, `CELULA`, `ESPECIAL`

### Asistencias a Cultos — `/api/v1/asistencias`
| Método | Ruta | Rol mínimo | Descripción |
|---|---|---|---|
| POST | `/api/v1/asistencias/manual` | ENCARGADO | Registrar asistencia manual |
| POST | `/api/v1/asistencias/barcode/{dni}` | ENCARGADO | Registrar por escaneo de DNI (código de barras/QR) |
| POST | `/api/v1/asistencias/huella` | ENCARGADO | Registrar por huella dactilar |
| GET | `/api/v1/asistencias` | Autenticado | Listar asistencias |
| GET | `/api/v1/asistencias/persona/{personaId}` | Autenticado | Asistencias de una persona |
| GET | `/api/v1/asistencias/fecha?fecha=2026-02-19` | Autenticado | Por fecha |
| GET | `/api/v1/asistencias/sesion/{sesionId}` | Autenticado | Todas las asistencias de una sesión |

> **Flujo barcode:** El escáner lee el DNI → envía `POST /api/v1/asistencias/barcode/{dni}` →
> el sistema busca la persona y la vincula automáticamente a la sesión de culto abierta.

> **Flujo manual** — body:
> ```json
> { "personaId": 5, "registradoPorId": 1, "sesionId": 3 }
> ```

### Huellas Dactilares — `/api/v1/huellas`
| Método | Ruta | Rol mínimo | Descripción |
|---|---|---|---|
| POST | `/api/v1/huellas` | ENCARGADO | Registrar template biométrico de una persona |
| GET | `/api/v1/huellas/persona/{personaId}` | Autenticado | Ver huellas activas de una persona |
| PUT | `/api/v1/huellas/{id}/desactivar` | ENCARGADO | Desactivar una huella |
| DELETE | `/api/v1/huellas/{id}` | SUPER_ADMIN | Eliminar huella desactivada |

> Body para registrar huella:
> ```json
> {
>   "personaId": 5,
>   "templateBase64": "BASE64_DEL_TEMPLATE_BIOMETRICO",
>   "dedo": "INDICE_DERECHO"
> }
> ```
> Dedos disponibles: `PULGAR_DERECHO`, `INDICE_DERECHO`, `MEDIO_DERECHO`, `ANULAR_DERECHO`, `MENIQUE_DERECHO`
> (y equivalentes `_IZQUIERDO`).

### Finanzas — Ingresos y Egresos
| Método | Ruta | Rol mínimo | Descripción |
|---|---|---|---|
| POST | `/api/v1/ingresos` | TESORERO | Registrar ingreso |
| GET | `/api/v1/ingresos/{id}` | TESORERO | Buscar ingreso |
| GET | `/api/v1/ingresos` | TESORERO | Listar |
| GET | `/api/v1/ingresos/tipo/{tipo}` | TESORERO | Por tipo |
| GET | `/api/v1/ingresos/mes?anio=2026&mes=2` | TESORERO | Por mes |
| POST | `/api/v1/egresos` | TESORERO | Registrar egreso |
| GET | `/api/v1/egresos` | TESORERO | Listar |
| GET | `/api/v1/egresos/mes?anio=2026&mes=2` | TESORERO | Por mes |

### Celulas — `/api/v1/celulas`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/celulas` | Crear célula |
| GET | `/api/v1/celulas/{id}` | Buscar por ID |
| GET | `/api/v1/celulas` | Listar |
| PUT | `/api/v1/celulas/{id}` | Actualizar |
| DELETE | `/api/v1/celulas/{id}` | Eliminar (SUPER_ADMIN) |

### Ministerios — `/api/v1/ministerios`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/ministerios` | Crear ministerio |
| GET | `/api/v1/ministerios/{id}` | Buscar por ID |
| GET | `/api/v1/ministerios` | Listar |
| PUT | `/api/v1/ministerios/{id}` | Actualizar |
| DELETE | `/api/v1/ministerios/{id}` | Eliminar (SUPER_ADMIN) |
| POST | `/api/v1/ministerios/{ministerioId}/miembros` | Asignar miembro |
| GET | `/api/v1/ministerios/{ministerioId}/miembros` | Ver miembros |
| DELETE | `/api/v1/ministerios/{ministerioId}/miembros/{id}` | Quitar miembro |

### Escuelas y Cursos
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/escuelas` | Crear escuela bíblica |
| GET | `/api/v1/escuelas` | Listar escuelas |
| GET | `/api/v1/escuelas/fase/{fase}` | Por fase |
| PUT | `/api/v1/escuelas/{id}` | Actualizar |
| DELETE | `/api/v1/escuelas/{id}` | Eliminar (SUPER_ADMIN) |
| POST | `/api/v1/cursos` | Crear curso (MAESTRO+) |
| GET | `/api/v1/cursos` | Listar cursos |
| PUT | `/api/v1/cursos/{id}` | Actualizar (MAESTRO+) |
| DELETE | `/api/v1/cursos/{id}` | Eliminar (SUPER_ADMIN) |
| POST | `/api/v1/cursos/{cursoId}/inscripciones` | Inscribir persona en curso |
| PUT | `/api/v1/cursos/{cursoId}/inscripciones/{id}/nota` | Calificar (MAESTRO+) |
| POST | `/api/v1/cursos/{cursoId}/asistencias` | Registrar asistencia manual al curso |
| POST | `/api/v1/cursos/{cursoId}/asistencias/barcode/{dni}` | Registrar por escaneo de DNI |
| GET | `/api/v1/cursos/{cursoId}/asistencias` | Ver asistencias del curso |
| DELETE | `/api/v1/cursos/{cursoId}/asistencias/{id}` | Eliminar registro |

> **Validación:** solo personas inscritas (`MATRICULADO`) pueden registrar asistencia al curso.

### Eventos Cristianos — `/api/v1/eventos`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/eventos` | Crear evento |
| GET | `/api/v1/eventos/{id}` | Buscar evento |
| GET | `/api/v1/eventos` | Listar eventos |
| PUT | `/api/v1/eventos/{id}` | Actualizar |
| DELETE | `/api/v1/eventos/{id}` | Eliminar (SUPER_ADMIN) |
| POST | `/api/v1/eventos/{eventoId}/personas` | Registrar asistente |
| GET | `/api/v1/eventos/{eventoId}/personas` | Ver asistentes |
| DELETE | `/api/v1/eventos/{eventoId}/personas/{id}` | Quitar asistente |

### Sedes — `/api/v1/sedes`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/sedes` | Crear sede |
| GET | `/api/v1/sedes/{id}` | Buscar sede |
| GET | `/api/v1/sedes` | Listar sedes |
| PUT | `/api/v1/sedes/{id}` | Actualizar |
| DELETE | `/api/v1/sedes/{id}` | Eliminar (SUPER_ADMIN) |
| POST | `/api/v1/sedes/{sedeId}/registros` | Registrar visita a sede |
| GET | `/api/v1/sedes/{sedeId}/registros` | Ver visitas |
| DELETE | `/api/v1/sedes/{sedeId}/registros/{registroId}` | Eliminar visita |

### Otras Iglesias — `/api/v1/otras-iglesias`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/otras-iglesias` | Registrar iglesia de procedencia |
| GET | `/api/v1/otras-iglesias/{id}` | Buscar |
| GET | `/api/v1/otras-iglesias` | Listar |
| PUT | `/api/v1/otras-iglesias/{id}` | Actualizar |
| DELETE | `/api/v1/otras-iglesias/{id}` | Eliminar (SUPER_ADMIN) |

### Reportes — `/api/v1/reportes`
| Método | Ruta | Rol mínimo | Descripción |
|---|---|---|---|
| GET | `/api/v1/reportes/financiero/mensual?anio=2026&mes=2` | TESORERO | Resumen financiero mensual |
| GET | `/api/v1/reportes/asistencia/mensual?anio=2026&mes=2` | Autenticado | Reporte de asistencia |

---

## Tablas principales en PostgreSQL

| Tabla | Descripción |
|---|---|
| `persona` | Datos personales de cada individuo (`dni` UNIQUE) |
| `miembro` | Cuenta de acceso — email + password BCrypt (`email` UNIQUE) |
| `rol` | Roles del sistema (pre-cargados por init.sql) |
| `miembro_rol` | Asignación de roles a miembros |
| `sesion_culto` | Sesiones de culto abiertas/cerradas para tomar asistencia |
| `asistencia` | Registro de asistencia a cultos (vinculado a `sesion_culto`) |
| `huella_persona` | Templates biométricos por persona (BYTEA) |
| `celula` | Grupos de células |
| `ministerio` | Ministerios de la iglesia |
| `miembro_ministerio` | Asignación miembro-ministerio |
| `ingreso` | Ingresos financieros |
| `egreso` | Egresos financieros |
| `escuela` | Escuelas bíblicas |
| `curso` | Cursos dentro de escuelas |
| `asistencia_curso_persona` | Asistencia a clases (requiere inscripción previa) |
| `evento_cristiano` | Eventos especiales |
| `persona_evento` | Asistentes a eventos |
| `sede` | Sedes o locales |
| `sede_registro` | Visitas a cada sede |
| `otra_iglesia` | Iglesias de procedencia de miembros |
| `profesor` | Miembros que dictan cursos |

---

## Flujo inicial recomendado

```
1. Levantar la app (IntelliJ o mvn spring-boot:run)
   → init.sql inserta automáticamente los 5 roles en la tabla "rol"

2. POST /auth/signup → registrar el administrador principal
   → devuelve un token JWT válido (sin roles todavía)

3. POST /api/v1/roles/asignaciones  (con el token del paso 2)
   → body: { "miembroId": 1, "rolId": 1 }
   → asigna SUPER_ADMIN al administrador
   → los roles ya existen en BD, NO hace falta crearlos

4. POST /auth/signin → obtener token actualizado con el nuevo rol
   → el rol se incluye en las authorities del JWT

5. Usar el token en Swagger UI (botón "Authorize") o en Postman
   → ya tienes acceso completo al sistema

6. Continuar: crear sedes, células, ministerios, personas, etc.
```

---

## Cambiar contraseña del administrador

No hay endpoint de cambio de contraseña. Alternativas:

**Opción 1 — SQL directo** (necesitas generar hash BCrypt):
```sql
-- Genera el hash en: https://bcrypt-generator.com (rounds = 10)
UPDATE miembro
SET password = '$2a$10$AQUI_EL_HASH_GENERADO'
WHERE email = 'admin@iglesia.com';
```

**Opción 2 — Crear nuevo usuario** vía `/auth/signup` con otro email y asignarle el rol.

---

## Integración con RENIEC

- **API:** `https://api.decolecta.com/v1/reniec/dni?numero={dni}`
- **Token:** configurado en `token.reniec` del `application.properties`
- **Uso:** al crear una persona vía `POST /api/v1/personas`, se consulta RENIEC con el DNI y se obtienen: `nombres`, `apellidoPaterno`, `apellidoMaterno`
- **Comportamiento si RENIEC falla:** guarda la persona sin nombres

---

## Respuestas estándar de la API

Todos los endpoints devuelven el mismo formato:

```json
{
  "success": true,
  "data": { ... },
  "timestamp": "2026-02-19T16:00:00",
  "status": 200
}
```

### Códigos de error

| Código | Situación |
|---|---|
| `400` | Body inválido, campo faltante, o formato incorrecto |
| `401` | Credenciales incorrectas en `/auth/signin` |
| `403` | Token expirado / ausente, o cuenta desactivada |
| `404` | Recurso no encontrado |
| `409` | Duplicado (DNI o email ya registrado) |
| `422` | Error de negocio (BusinessException) — inscripción, sesión cerrada, etc. |
| `423` | Cuenta bloqueada |
| `500` | Error interno del servidor |

---

## Problemas conocidos y soluciones

### Token expira → 403
El JWT dura 8 horas. Cuando expira, cualquier petición retorna 403.
**Solución:** llamar `POST /auth/signin` para obtener token nuevo.

### No hay sesión abierta → 422 al registrar asistencia
Si se intenta registrar asistencia por barcode o huella y no hay sesión abierta:
**Solución:** abrir una sesión primero con `POST /api/v1/sesiones-culto`.

### La persona no está inscrita → 422 al registrar asistencia a curso
Si se intenta registrar asistencia a un curso sin estar matriculado:
**Solución:** matricular primero con `POST /api/v1/cursos/{cursoId}/inscripciones`.

### Puerto 8080 en uso
```
Web server failed to start. Port 8080 was already in use.
```
**Solución:** detener la instancia en IntelliJ antes de usar `mvn spring-boot:run`.

### RENIEC no mapea nombres
Si RENIEC devuelve campos distintos a los esperados, los nombres quedarán vacíos.
