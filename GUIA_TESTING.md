# Guía de Testing - SystemLibrary

## Objetivo

Validar que los microservicios de SystemLibrary funcionen correctamente antes de la entrega.

## Herramientas recomendadas

- Visual Studio Code
- Extensión REST Client
- Postman
- Docker Desktop
- Gradle
- PostgreSQL

## Pruebas para Books Service

### 1. Crear libro

Enviar una solicitud `POST /api/books` con datos válidos.

Resultado esperado:

- Código HTTP 200 o 201.
- El libro queda registrado en la base de datos.

### 2. Listar libros

Enviar una solicitud `GET /api/books`.

Resultado esperado:

- Código HTTP 200.
- Se obtiene una lista de libros.

### 3. Buscar libro por ID

Enviar una solicitud `GET /api/books/1`.

Resultado esperado:

- Código HTTP 200 si existe.
- Código HTTP 404 si no existe.

### 4. Actualizar libro

Enviar una solicitud `PUT /api/books/1`.

Resultado esperado:

- Código HTTP 200.
- Los datos del libro quedan actualizados.

### 5. Eliminar libro

Enviar una solicitud `DELETE /api/books/1`.

Resultado esperado:

- Código HTTP 200 o 204.
- El libro ya no aparece en el listado.

## Pruebas para Users Service

### 1. Registrar usuario

Enviar una solicitud `POST /api/auth/register`.

Resultado esperado:

- Código HTTP 200 o 201.
- Usuario registrado correctamente.

### 2. Login

Enviar una solicitud `POST /api/auth/login`.

Resultado esperado:

- Código HTTP 200.
- Se recibe un token JWT.

### 3. Acceso con token

Enviar una solicitud protegida usando:

```http
Authorization: Bearer TOKEN
```

Resultado esperado:

- Código HTTP 200 si el token es válido.
- Código HTTP 401 si el token no existe o es incorrecto.

## Checklist de pruebas

- [ ] El servicio books inicia correctamente.
- [ ] El servicio users inicia correctamente.
- [ ] PostgreSQL se levanta con Docker.
- [ ] Se pueden crear libros.
- [ ] Se pueden listar libros.
- [ ] Se puede registrar un usuario.
- [ ] Se puede iniciar sesión.
- [ ] Se obtiene token JWT.
- [ ] Las rutas protegidas validan el token.
