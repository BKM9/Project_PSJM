# 📋 Guía de Arquitectura - Backend SJM

## 🏗️ Estructura Creada

Tu aplicación sigue el patrón de **Arquitectura en Capas** con una **REST API** (no MVC tradicional con vistas HTML).

```
src/main/java/com/parroquia/sjm/
│
├── model/
│   ├── dto/
│   │   └── ParroquiaDTO.java          (Modelo de datos / Transferencia)
│   └── response/
│       └── ApiResponse.java            (Respuesta genérica para todas las APIs)
│
├── service/
│   └── ParroquiaService.java          (Lógica de negocio)
│
├── controller/
│   └── ParroquiaController.java       (Endpoints REST)
│
└── SjmApplication.java                (Punto de entrada Spring Boot)
```

---

## 🔄 Flujo de Datos (Request → Response)

```
Cliente (Angular, Postman, etc.)
        ↓
    HTTP Request (GET, POST, etc.)
        ↓
[ParroquiaController]  ← Recibe la solicitud
        ↓
[ParroquiaService]     ← Procesa la lógica de negocio
        ↓
[Supabase API]         ← Consulta datos (futuro)
        ↓
JSON Response          ← Retorna datos al cliente
```

---

## 🎯 Responsabilidades de Cada Capa

### 1. **Modelo (Model/DTO)**
- **ParroquiaDTO**: Representa los datos de una Parroquia
- Define la estructura que se envía/recibe en JSON

**Ejemplo JSON:**
```json
{
  "id": 1,
  "nombre": "Parroquia San Juan",
  "ciudad": "Quito",
  "telefonoContacto": "+593-2-123456",
  "email": "sjuan@parroquia.ec"
}
```

### 2. **Servicio (Service)**
- Contiene la lógica de negocio
- Interactúa con Supabase (cuando esté integrado)
- Valida datos
- **Estilo Reactivo**: Usa `Mono<T>` y `Flux<T>` (Project Reactor)

### 3. **Controlador (Controller)**
- Expone los endpoints REST
- Mapea URLs a métodos
- Maneja HTTP (GET, POST, PUT, DELETE)
- Retorna respuestas JSON

---

## 🚀 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/api/v1/parroquias` | Obtiene todas las parroquias |
| GET | `/api/v1/parroquias/{id}` | Obtiene una parroquia por ID |
| POST | `/api/v1/parroquias` | Crea una nueva parroquia |

---

## 🧪 Pruebas Manual (usando cURL)

### Obtener todas las parroquias:
```bash
curl http://localhost:8080/api/v1/parroquias
```

### Obtener parroquia con ID 1:
```bash
curl http://localhost:8080/api/v1/parroquias/1
```

### Crear una nueva parroquia:
```bash
curl -X POST http://localhost:8080/api/v1/parroquias \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Parroquia Nueva",
    "ciudad": "Cuenca",
    "telefonoContacto": "+593-7-111111",
    "email": "nueva@parroquia.ec"
  }'
```

---

## ⚡ Características Clave

✅ **Reactivo (WebFlux)**: Maneja múltiples solicitudes eficientemente  
✅ **REST API**: Retorna JSON puro (consumible por Angular, Electron, Capacitor)  
✅ **Tipado Fuerte**: Java garantiza seguridad de tipos  
✅ **CORS Habilitado**: Permite solicitudes desde el frontend  
✅ **Respuestas Consistentes**: `ApiResponse<T>` normaliza todas las respuestas  

---

## 📝 Próximos Pasos

1. **Integrar Supabase**: Reemplazar datos simulados en `ParroquiaService` con llamadas HTTP a Supabase
2. **Agregar más entidades**: Personas, Misas, Bautizos (mismo patrón: DTO → Service → Controller)
3. **Manejo de errores global**: Crear `@ControllerAdvice` para centralized error handling
4. **Reportes**: Integrar Apache POI para generación de documentos Word

---

## ⚙️ Configuración Actual

- **Java**: 17 (LTS)
- **Spring Boot**: 3.4.2
- **Framework**: WebFlux (reactivo)
- **Build Tool**: Gradle 9.5.1
- **Puerto**: 8080 (configurable en `application.yaml`)


