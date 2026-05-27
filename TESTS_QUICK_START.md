# Tests Quick Start - SystemLibrary

## 1. Levantar Books Service

```bash
cd books
docker compose up -d
```

Verificar:

```bash
docker ps
```

## 2. Levantar Users Service

```bash
cd users
docker compose up -d
```

## 3. Probar endpoints

Abrir los archivos:

- `books/api.http`
- `users/api.http`

Ejecutar las solicitudes en orden:

1. Crear libro.
2. listar libros.
3. Registrar usuario.
4. Login.
5. Copiar token.
6. Probar rutas protegidas.

## 4. Ejecutar tests con Gradle

```bash
./gradlew test
```

En Windows:

```bash
gradlew.bat test
```

## 5. Revisar errores comunes

### Docker no inicia

Verificar que Docker Desktop esté abierto.

### Puerto ocupado

Cambiar el puerto en `docker-compose.yml`.

### Error de conexión a PostgreSQL

Revisar usuario, contraseña, nombre de base de datos y URL JDBC.
