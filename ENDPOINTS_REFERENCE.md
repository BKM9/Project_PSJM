# 📚 Referencia de Endpoints REST API v1

## 📊 Resumen de Recursos

Tu API REST ahora expone **6 recursos** principales con múltiples endpoints cada uno.

---

## 🏛️ 1. PARROQUIAS
**Base URL:** `/api/v1/parroquias`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todas las parroquias |
| GET | `/{id}` | Obtiene una parroquia por ID |
| POST | `/` | Crea una nueva parroquia |

**Ejemplo Request (POST):**
```json
{
  "nombre": "Parroquia Nueva",
  "ciudad": "Quito",
  "telefonoContacto": "+593-2-123456",
  "email": "info@parroquia.ec"
}
```

---

## ✝️ 2. BAUTIZOS
**Base URL:** `/api/v1/bautizos`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todos los bautizos |
| GET | `/{id}` | Obtiene un bautizo por ID |
| GET | `/apellido/{apellido}` | Busca bautizos por apellido |
| GET | `/anio/{anio}` | Obtiene bautizos de un año específico |
| POST | `/` | Crea un nuevo bautizo |

**Ejemplo Request (POST):**
```json
{
  "apellidos": "PRADO NEGRETE",
  "nombres": "CARLOS",
  "libro": "1",
  "folio": "1",
  "n_de_registro": "1",
  "anio_b": 1972,
  "dia_b": 5,
  "mes_b": "MARZO",
  "ministro": "FRAY VICENTE GUERRERO",
  "lugar_nacimiento": "LIMA",
  "dia_n": 12,
  "mes_n": "DICIEMBRE",
  "anio_n": 1971,
  "nombre_y_apellido_padre": "VICENTE PRADO TEJADA",
  "lugar_nacimiento_p": "LIMA",
  "nombre_y_apellido_madre": "ELENA NEGRETE FLORES",
  "lugar_nacimiento_m": "LIMA",
  "nombre_padrino": "PEDRO MALDONADO",
  "nombre_madrina": "OLGA JIMENEZ DEL MALDONADO",
  "anotaciones": "Primer registro"
}
```

---

## 🙏 3. MISAS PARTICULARES
**Base URL:** `/api/v1/misas-particulares`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todas las misas particulares |
| GET | `/{id}` | Obtiene una misa por ID |
| GET | `/fecha/{fecha}` | Obtiene misas de una fecha específica (YYYY-MM-DD) |
| GET | `/no-pagadas` | Obtiene misas no pagadas |
| POST | `/` | Crea una nueva misa particular |

**Ejemplo Request (POST):**
```json
{
  "fecha_misa": "2026-06-08",
  "hora_misa": "17:00:00",
  "intencion": "Carlos Enrique - 1 año",
  "ofrece": "Su mamá Edith",
  "ofrenda": 15.00,
  "pagado": true,
  "anotaciones": "Misa comunitaria del lunes"
}
```

---

## 🕯️ 4. MISAS COMUNITARIAS
**Base URL:** `/api/v1/misas-comunitarias`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todas las misas comunitarias |
| GET | `/{id}` | Obtiene una misa por ID |
| GET | `/categoria/{categoria}` | Obtiene misas por categoría (Difuntos, Sanación, etc.) |
| GET | `/fecha/{fecha}` | Obtiene misas de una fecha específica |
| GET | `/no-pagadas` | Obtiene misas no pagadas |
| POST | `/` | Crea una nueva misa comunitaria |

**Ejemplo Request (POST):**
```json
{
  "fecha_misa": "2026-06-14",
  "hora_misa": "08:00:00",
  "categoria": "Difuntos",
  "intencion": "Pedro Álvarez Espinoza",
  "ofrenda": 10.00,
  "pagado": true,
  "anotaciones": null
}
```

---

## ⏰ 5. HORARIOS
**Base URL:** `/api/v1/horarios`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todos los horarios |
| GET | `/{id}` | Obtiene un horario por ID |
| GET | `/fecha/{fecha}` | Obtiene horarios de una fecha |
| GET | `/tipo/{tipoEvento}` | Obtiene horarios por tipo de evento (Bautizo, Misa, Boda) |
| GET | `/proximos` | Obtiene horarios próximos (de hoy en adelante) |
| POST | `/` | Crea un nuevo horario |

**Ejemplo Request (POST):**
```json
{
  "fecha": "2026-06-07",
  "hora": "16:00:00",
  "tipo_evento": "Bautizo",
  "celebrante": "Padre Luis",
  "detalles": "Ceremonia particular de la familia Silva"
}
```

---

## 👤 6. USUARIOS
**Base URL:** `/api/v1/usuarios`

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/` | Obtiene todos los usuarios |
| GET | `/{id}` | Obtiene un usuario por ID |
| GET | `/nombre/{usuario}` | Obtiene un usuario por nombre de usuario |
| GET | `/activos` | Obtiene solo usuarios activos |
| POST | `/login` | Valida credenciales (para autenticación) |
| POST | `/` | Crea un nuevo usuario |

**Ejemplo Request (Login - POST /login):**
```json
{
  "usuario": "mcajo",
  "clave": "password123"
}
```

**Respuesta esperada:**
```json
true
```

**Ejemplo Request (Crear usuario - POST /):**
```json
{
  "usuario": "mcajo",
  "clave": "$2a$06$1x8riRfhpMaWlXD71c0dWeaJRGZ0FaEy9b7Pb7KIah9Vu.x7uJUYC",
  "nombres": "Martin Alexis",
  "apellidos": "Cajo Velit",
  "cargo": "Sacristan/Programador",
  "telefono": "932310725",
  "activo": true
}
```

---

## 🔄 Códigos HTTP Comunes

| Código | Significado |
|--------|------------|
| 200 | OK - Request exitoso |
| 201 | Created - Recurso creado exitosamente |
| 400 | Bad Request - Datos inválidos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Server Error - Error del servidor |

---

## 🔑 Patrones URL

### Rutas Dinámicas
```
GET  /api/v1/{recurso}              # Lista de todos
GET  /api/v1/{recurso}/{id}         # Obtener uno
POST /api/v1/{recurso}              # Crear nuevo
```

### Rutas Filtradas
```
GET  /api/v1/{recurso}/criterio/{valor}    # Filtrado
GET  /api/v1/{recurso}/especial             # Búsqueda especial
```

---

## 🧪 Ejemplos cURL Completos

### Obtener todos los bautizos
```bash
curl http://localhost:8080/api/v1/bautizos
```

### Obtener bautizos por apellido
```bash
curl http://localhost:8080/api/v1/bautizos/apellido/PRADO
```

### Crear una misa particular
```bash
curl -X POST http://localhost:8080/api/v1/misas-particulares \
  -H "Content-Type: application/json" \
  -d '{
    "fecha_misa": "2026-06-15",
    "hora_misa": "18:00:00",
    "intencion": "Intención especial",
    "ofrece": "Donante",
    "ofrenda": 20.00,
    "pagado": false
  }'
```

### Obtener horarios próximos
```bash
curl http://localhost:8080/api/v1/horarios/proximos
```

### Validar login de usuario
```bash
curl -X POST http://localhost:8080/api/v1/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{
    "usuario": "mcajo",
    "clave": "password123"
  }'
```

---

## 📝 DTOs (Records) Soportados

| Recurso | DTO | Campos |
|---------|-----|--------|
| Parroquias | ParroquiaDTO | id, nombre, ciudad, telefonoContacto, email |
| Bautizos | BautizosDTO | 23 campos (ver estructura de datos) |
| Misas Particulares | MisaParticularDTO | id, fechaMisa, horaMisa, intencion, ofrece, ofrenda, pagado, anotaciones |
| Misas Comunitarias | MisaComunitariaDTO | id, fechaMisa, horaMisa, categoria, intencion, ofrenda, pagado, anotaciones |
| Horarios | HorarioDTO | id, fecha, hora, tipoEvento, celebrante, detalles |
| Usuarios | UsuarioDTO | id, usuario, clave, nombres, apellidos, cargo, telefono, activo |

---

## ⚙️ Configuración de Respuestas

### Todas las respuestas incluyen:
- `status`: Código HTTP
- `message`: Descripción
- `data`: Contenido de la respuesta
- `timestamp`: Fecha/hora de la respuesta

---

## 🔐 Notas de Seguridad

⚠️ **Importante:**
- Las contraseñas deben hashearse con BCrypt antes de guardar
- Nunca expongas las contraseñas en logs
- Los endpoints actualmente **NO tienen autenticación JWT** (próximo paso)
- CORS está habilitado para desarrollo (deshabilitarlo en producción)

---

## 📦 Estado de Integración

✅ DTO Records creados  
✅ Services aislados  
✅ Controllers REST listos  
✅ Compilación exitosa  

⏳ **Pendiente:**
- Integración con Supabase API
- Implementación de seguridad JWT
- Manejo global de errores
- Generación de reportes Word

---

**¡Tu API REST está estructurada y lista para integración con Supabase!** 🚀


