# 🏛️ SJM Backend - Sistema de Gestión Parroquial

Backend centralizado en Java/Spring Boot para el Sistema de Gestión Parroquial.

---

## 📦 Descripción

Este es un **microservicio REST API** que proporciona la lógica de negocio para:
- Gestión de **Parroquias**
- Gestión de **Misas**
- Gestión de **Bautizos**
- Generación de **Reportes** (Word)

Los datos se obtienen de **Supabase** (base de datos externalizada).  
El frontend (Angular) consumirá estos endpoints JSON.

---

## 🛠️ Tecnología

| Aspecto | Tecnología |
|--------|-----------|
| **Lenguaje** | Java 17 LTS |
| **Framework** | Spring Boot 3.4.2 |
| **Build Tool** | Gradle 9.5.1 |
| **Arquitectura** | WebFlux (Reactivo) |
| **Base de Datos** | Supabase (REST API) |
| **Reportes** | Apache POI |

---

## 🚀 Inicio Rápido

### 1. Compilar
```bash
cd sjm
./gradlew build -x test
```

### 2. Ejecutar
```bash
./gradlew bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📁 Estructura del Proyecto

```
sjm/
├── src/main/java/com/parroquia/sjm/
│   ├── model/          (DTOs y Modelos de datos)
│   ├── service/        (Lógica de negocio)
│   ├── controller/     (Endpoints REST)
│   └── SjmApplication.java
│
├── src/main/resources/
│   ├── application.yaml        (Configuración)
│   └── templates/              (Plantillas Word para reportes)
│
└── build.gradle                (Configuración de dependencias)
```

Para más detalles: Ver `ARCHITECTURE.md`

---

## 🔌 Endpoints Principal (Ejemplo)

### Parroquias
- `GET /api/v1/parroquias` - Listar todas
- `GET /api/v1/parroquias/{id}` - Obtener por ID
- `POST /api/v1/parroquias` - Crear nueva

---

## 🔐 Configuración de Variables de Entorno

En `application.yaml`, configura:

```yaml
supabase:
  url: ${SUPABASE_URL}      # Tu URL de Supabase
  key: ${SUPABASE_KEY}      # Tu API Key de Supabase
```

En **Koyeb** (producción), establece estas variables en el panel de control.

---

## 📊 Restricciones de Koyeb (Free Tier)

- RAM: **512 MB máximo**
- Heap JVM: **256 MB máximo** (`-Xmx256m`)
- CPU: **0.1 vCPU**

La configuración está optimizada para estos límites.

---

## 📝 Pasos Siguientes

- [ ] Integrar Supabase en `ParroquiaService`
- [ ] Crear entidades para Misas y Bautizos
- [ ] Implementar generación de reportes
- [ ] Agregar autenticación/autorización
- [ ] Deploy en Koyeb

---

## 📞 Contacto & Notas

**Desarrollador**: Martin Cajo  
**Proyecto**: Sistema de Gestión Parroquial  
**Stack**: Java + Angular + Supabase

---

**¡Happy Coding!** 🎉


