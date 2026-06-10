# 🧪 GUÍA DE EJECUCIÓN Y PRUEBA

## 🚀 Pasos para Ejecutar

### Opción 1: Ejecución Interactiva (Recomendado para desarrollo)

```powershell
cd "C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm"
./gradlew bootRun
```

**Salida esperada:**
```
2026-06-06 10:00:00 - The following profiles are active:
2026-06-06 10:00:01 - Started SjmApplication in 5.234 seconds (process running for 5.645s)
```

### Opción 2: Ejecutar JAR (Producción)

```powershell
cd "C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm"
./gradlew build -x test   # Compilar
java -jar build/libs/sjm-0.0.1-SNAPSHOT.jar  # Ejecutar
```

---

## 🔍 Probar los Endpoints

Una vez que la aplicación esté corriendo en `http://localhost:8080`

### 1️⃣ Obtener todas las parroquias

```bash
curl http://localhost:8080/api/v1/parroquias
```

**Respuesta esperada:**
```json
[
  {
    "id": 1,
    "nombre": "Parroquia San Juan",
    "ciudad": "Quito",
    "telefonoContacto": "+593-2-123456",
    "email": "sjuan@parroquia.ec"
  },
  {
    "id": 2,
    "nombre": "Parroquia Santa María",
    "ciudad": "Guayaquil",
    "telefonoContacto": "+593-4-789012",
    "email": "smaria@parroquia.ec"
  }
]
```

### 2️⃣ Obtener parroquia por ID

```bash
curl http://localhost:8080/api/v1/parroquias/1
```

**Respuesta esperada:**
```json
{
  "id": 1,
  "nombre": "Parroquia San Juan",
  "ciudad": "Quito",
  "telefonoContacto": "+593-2-123456",
  "email": "sjuan@parroquia.ec"
}
```

### 3️⃣ Crear una nueva parroquia

```bash
curl -X POST http://localhost:8080/api/v1/parroquias \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Parroquia Inmaculada",
    "ciudad": "Ambato",
    "telefonoContacto": "+593-3-555555",
    "email": "inmaculada@parroquia.ec"
  }'
```

**Respuesta esperada (201 Created):**
```json
{
  "id": 1717680000000,
  "nombre": "Parroquia Inmaculada",
  "ciudad": "Ambato",
  "telefonoContacto": "+593-3-555555",
  "email": "inmaculada@parroquia.ec"
}
```

---

## 🛠️ Probar desde Postman (Alternativa Gráfica)

### 1. Descargar Postman
[https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### 2. Crear Requests

#### Request 1: GET todas las parroquias
- **Método**: GET
- **URL**: `http://localhost:8080/api/v1/parroquias`
- Click **Send**

#### Request 2: GET parroquia por ID
- **Método**: GET
- **URL**: `http://localhost:8080/api/v1/parroquias/1`
- Click **Send**

#### Request 3: POST nueva parroquia
- **Método**: POST
- **URL**: `http://localhost:8080/api/v1/parroquias`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
  ```json
  {
    "nombre": "Test Parroquia",
    "ciudad": "Loja",
    "telefonoContacto": "+593-7-123456",
    "email": "test@parroquia.ec"
  }
  ```
- Click **Send**

---

## 📊 Códigos de Estado HTTP Esperados

| Método | Endpoint | Código | Significado |
|--------|----------|--------|------------|
| GET | `/api/v1/parroquias` | 200 | OK - Devuelve lista |
| GET | `/api/v1/parroquias/1` | 200 | OK - Devuelve una parroquia |
| GET | `/api/v1/parroquias/999` | 404 | Not Found - No existe |
| POST | `/api/v1/parroquias` | 201 | Created - Se creó exitosamente |
| POST | `/api/v1/parroquias` (sin nombre) | 400 | Bad Request - Datos inválidos |

---

## 🔧 Comandos Útiles

### Compilar sin ejecutar
```bash
./gradlew build -x test
```

### Limpiar y compilar
```bash
./gradlew clean build -x test
```

### Ver dependencias
```bash
./gradlew dependencies
```

### Ejecutar tests (cuando los agregues)
```bash
./gradlew test
```

### Ver logs en tiempo real
```bash
./gradlew bootRun --info
```

---

## 🐛 Solucionar Problemas

### ❌ Error: "Cannot find a Java installation"
**Solución:**
```bash
java -version  # Verifica que Java 17+ está instalado
```

### ❌ Error: "Port 8080 already in use"
**Solución:** La aplicación está corriendo en otra terminal. Termínala o usa otro puerto:
```bash
java -jar build/libs/sjm-0.0.1-SNAPSHOT.jar --server.port=9090
```

### ❌ Error: "Failed to start Reactor"
**Solución:** Verifica que tienes WebFlux en las dependencias (ya incluido en `build.gradle`)

---

## 📱 Próximo: Integración con Angular

Cuando tengas tu frontend Angular, configura:

```typescript
// environment.ts
export const environment = {
  apiUrl: 'http://localhost:8080/api/v1'
};

// parroquia.service.ts
import { HttpClient } from '@angular/common/http';

export class ParroquiaService {
  constructor(private http: HttpClient) {}
  
  obtenerParroquias() {
    return this.http.get(`${environment.apiUrl}/parroquias`);
  }
}
```

---

## 🎉 ¡Listo para el Siguiente Paso!

Una vez que verifiques que los endpoints funcionan, estamos listos para:
1. Integrar **Supabase**
2. Agregar **Misas** y **Bautizos**
3. Implementar **Reportes Word**

¿Deseas continuar? 🚀


