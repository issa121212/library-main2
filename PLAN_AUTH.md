# Plan de Autenticación - SystemLibrary

## Objetivo

Implementar autenticación segura para usuarios mediante Spring Security y JWT.

## Flujo de autenticación

```mermaid
sequenceDiagram
    participant Cliente
    participant UsersService
    participant DB

    Cliente->>UsersService: Registro de usuario
    UsersService->>DB: Guarda usuario
    DB-->>UsersService: Usuario creado
    UsersService-->>Cliente: Confirmación

    Cliente->>UsersService: Login con email y password
    UsersService->>DB: Valida credenciales
    DB-->>UsersService: Usuario válido
    UsersService-->>Cliente: Token JWT

    Cliente->>UsersService: Solicitud protegida con token
    UsersService-->>Cliente: Respuesta autorizada
```

## Endpoints relacionados

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registrar usuario |
| POST | `/api/auth/login` | Iniciar sesión |
| GET | `/api/users` | Ruta protegida |

## Reglas de seguridad

- Las contraseñas deben guardarse cifradas.
- El login debe devolver un token JWT.
- Las rutas protegidas deben requerir token.
- El token debe enviarse usando `Authorization: Bearer TOKEN`.

## Mejoras futuras

- Roles de usuario.
- Refresh token.
- Recuperación de contraseña.
- Expiración configurable del token.
