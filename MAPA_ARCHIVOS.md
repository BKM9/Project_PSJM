# 📍 MAPA DE ARCHIVOS - LOCALIZACIÓN VISUAL

## 🗂️ Estructura de Directorios Completa

```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
│
├── src/main/java/com/parroquia/sjm/
│   ├── SjmApplication.java
│   │   └── @SpringBootApplication
│   │       └── Punto de entrada de la aplicación
│   │
│   ├── model/
│   │   ├── dto/
│   │   │   ├── ParroquiaDTO.java          (RECORD) 5 campos
│   │   │   ├── BautizosDTO.java           (RECORD) 23 campos ⭐
│   │   │   ├── MisaParticularDTO.java     (RECORD) 9 campos
│   │   │   ├── MisaComunitariaDTO.java    (RECORD) 9 campos
│   │   │   ├── HorarioDTO.java            (RECORD) 8 campos
│   │   │   └── UsuarioDTO.java            (RECORD) 10 campos
│   │   └── response/
│   │       └── ApiResponse.java           (GENERIC) Respuestas normalizadas
│   │
│   ├── service/
│   │   ├── ParroquiaService.java          (SERVICE) 3 métodos
│   │   ├── BautizosService.java           (SERVICE) 6 métodos ⭐
│   │   ├── MisaParticularService.java     (SERVICE) 6 métodos
│   │   ├── MisaComunitariaService.java    (SERVICE) 6 métodos
│   │   ├── HorarioService.java            (SERVICE) 6 métodos
│   │   └── UsuarioService.java            (SERVICE) 7 métodos
│   │
│   └── controller/
│       ├── ParroquiaController.java       (REST) 3 endpoints
│       ├── BautizosController.java        (REST) 5 endpoints ⭐
│       ├── MisaParticularController.java  (REST) 5 endpoints
│       ├── MisaComunitariaController.java (REST) 6 endpoints
│       ├── HorarioController.java         (REST) 6 endpoints
│       └── UsuarioController.java         (REST) 7 endpoints
│
├── src/main/resources/
│   ├── application.yaml                   (CONFIG)
│   └── templates/                         (📁 Para reportes Word)
│
├── build.gradle                           (GRADLE CONFIG)
├── gradlew, gradlew.bat                   (GRADLE WRAPPER)
└── settings.gradle                        (GRADLE SETTINGS)
```

---

## 📊 CONTEO DE ARCHIVOS

| Categoría | Cantidad | Estado |
|-----------|----------|--------|
| **DTOs (Records)** | 6 | ✅ |
| **Services** | 6 | ✅ |
| **Controllers** | 6 | ✅ |
| **Response Wrappers** | 1 | ✅ |
| **Main Application** | 1 | ✅ |
| **Archivos Java Totales** | **20** | **✅** |

---

## 🔍 LOCALIZACIÓN DETALLADA

### 📁 CARPETA: `model/dto/`
```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
├── src\main\java\com\parroquia\sjm\model\dto\
│   ├── [1] ParroquiaDTO.java
│   │   └── public record ParroquiaDTO(Long, String, String, String, String)
│   │
│   ├── [2] BautizosDTO.java ⭐
│   │   └── public record BautizosDTO(Long, String, String, ... 23 campos totales)
│   │       └── Maneja: Fechas, nombres, lugares, responsables
│   │
│   ├── [3] MisaParticularDTO.java
│   │   └── public record MisaParticularDTO(Long, LocalDate, LocalTime, String, String, BigDecimal, Boolean, String, OffsetDateTime, OffsetDateTime)
│   │
│   ├── [4] MisaComunitariaDTO.java
│   │   └── public record MisaComunitariaDTO(Long, LocalDate, LocalTime, String, String, BigDecimal, Boolean, String, OffsetDateTime, OffsetDateTime)
│   │
│   ├── [5] HorarioDTO.java
│   │   └── public record HorarioDTO(Long, LocalDate, LocalTime, String, String, String, OffsetDateTime, OffsetDateTime)
│   │
│   └── [6] UsuarioDTO.java
│       └── public record UsuarioDTO(Long, String, String, String, String, String, String, Boolean, OffsetDateTime, OffsetDateTime)
```

**Total en dto/: 6 archivos**

---

### 📁 CARPETA: `model/response/`
```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
├── src\main\java\com\parroquia\sjm\model\response\
│   └── [7] ApiResponse.java
│       └── public class ApiResponse<T> (genérico)
│           ├── int status
│           ├── String message
│           ├── T data
│           └── LocalDateTime timestamp
```

**Total en response/: 1 archivo**

---

### 📁 CARPETA: `service/`
```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
├── src\main\java\com\parroquia\sjm\service\
│   ├── [8] ParroquiaService.java
│   │   ├── Flux<ParroquiaDTO> obtenerTodasLasParroquias()
│   │   ├── Mono<ParroquiaDTO> obtenerParroquiaPorId(Long)
│   │   └── Mono<ParroquiaDTO> crearParroquia(ParroquiaDTO)
│   │
│   ├── [9] BautizosService.java ⭐
│   │   ├── Flux<BautizosDTO> obtenerTodosBautizos()
│   │   ├── Mono<BautizosDTO> obtenerBautizoPorId(Long)
│   │   ├── Flux<BautizosDTO> obtenerBautizosPorApellido(String)
│   │   ├── Flux<BautizosDTO> obtenerBautizosDelAnio(Integer)
│   │   └── Mono<BautizosDTO> crearBautizo(BautizosDTO)
│   │
│   ├── [10] MisaParticularService.java
│   │   ├── Flux<MisaParticularDTO> obtenerTodasLasMisasParticulares()
│   │   ├── Mono<MisaParticularDTO> obtenerMisaParticularPorId(Long)
│   │   ├── Flux<MisaParticularDTO> obtenerMisasParticularPorFecha(LocalDate)
│   │   ├── Flux<MisaParticularDTO> obtenerMisasParticularesNoPagadas()
│   │   └── Mono<MisaParticularDTO> crearMisaParticular(MisaParticularDTO)
│   │
│   ├── [11] MisaComunitariaService.java
│   │   ├── Flux<MisaComunitariaDTO> obtenerTodasLasMisasComunitarias()
│   │   ├── Mono<MisaComunitariaDTO> obtenerMisaComunitariaPorId(Long)
│   │   ├── Flux<MisaComunitariaDTO> obtenerMisasComunitariasPorCategoria(String)
│   │   ├── Flux<MisaComunitariaDTO> obtenerMisasComunitariasPorFecha(LocalDate)
│   │   ├── Flux<MisaComunitariaDTO> obtenerMisasComunitariasNoPagadas()
│   │   └── Mono<MisaComunitariaDTO> crearMisaComunitaria(MisaComunitariaDTO)
│   │
│   ├── [12] HorarioService.java
│   │   ├── Flux<HorarioDTO> obtenerTodosLosHorarios()
│   │   ├── Mono<HorarioDTO> obtenerHorarioPorId(Long)
│   │   ├── Flux<HorarioDTO> obtenerHorariosPorFecha(LocalDate)
│   │   ├── Flux<HorarioDTO> obtenerHorariosPorTipoEvento(String)
│   │   ├── Flux<HorarioDTO> obtenerHorariosProximos()
│   │   └── Mono<HorarioDTO> crearHorario(HorarioDTO)
│   │
│   └── [13] UsuarioService.java
│       ├── Flux<UsuarioDTO> obtenerTodosLosUsuarios()
│       ├── Mono<UsuarioDTO> obtenerUsuarioPorId(Long)
│       ├── Mono<UsuarioDTO> obtenerUsuarioPorNombreUsuario(String)
│       ├── Flux<UsuarioDTO> obtenerUsuariosActivos()
│       ├── Mono<Boolean> validarCredenciales(String, String)
│       └── Mono<UsuarioDTO> crearUsuario(UsuarioDTO)
```

**Total en service/: 6 archivos**

---

### 📁 CARPETA: `controller/`
```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
├── src\main\java\com\parroquia\sjm\controller\
│   ├── [14] ParroquiaController.java
│   │   @RestController
│   │   @RequestMapping("/api/v1/parroquias")
│   │   ├── GET    /
│   │   ├── GET    /{id}
│   │   └── POST   /
│   │
│   ├── [15] BautizosController.java ⭐
│   │   @RestController
│   │   @RequestMapping("/api/v1/bautizos")
│   │   ├── GET    /
│   │   ├── GET    /{id}
│   │   ├── GET    /apellido/{apellido}
│   │   ├── GET    /anio/{anio}
│   │   └── POST   /
│   │
│   ├── [16] MisaParticularController.java
│   │   @RestController
│   │   @RequestMapping("/api/v1/misas-particulares")
│   │   ├── GET    /
│   │   ├── GET    /{id}
│   │   ├── GET    /fecha/{fecha}
│   │   ├── GET    /no-pagadas
│   │   └── POST   /
│   │
│   ├── [17] MisaComunitariaController.java
│   │   @RestController
│   │   @RequestMapping("/api/v1/misas-comunitarias")
│   │   ├── GET    /
│   │   ├── GET    /{id}
│   │   ├── GET    /categoria/{categoria}
│   │   ├── GET    /fecha/{fecha}
│   │   ├── GET    /no-pagadas
│   │   └── POST   /
│   │
│   ├── [18] HorarioController.java
│   │   @RestController
│   │   @RequestMapping("/api/v1/horarios")
│   │   ├── GET    /
│   │   ├── GET    /{id}
│   │   ├── GET    /fecha/{fecha}
│   │   ├── GET    /tipo/{tipoEvento}
│   │   ├── GET    /proximos
│   │   └── POST   /
│   │
│   └── [19] UsuarioController.java
│       @RestController
│       @RequestMapping("/api/v1/usuarios")
│       ├── GET    /
│       ├── GET    /{id}
│       ├── GET    /nombre/{usuario}
│       ├── GET    /activos
│       ├── POST   /login
│       └── POST   /
```

**Total en controller/: 6 archivos**

---

### 📁 CARPETA RAÍZ: `sjm/src/main/java/com/parroquia/sjm/`
```
C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\
├── src\main\java\com\parroquia\sjm\
│   └── [20] SjmApplication.java
│       ├── @SpringBootApplication
│       └── public static void main(String[] args)
```

**Total en raíz: 1 archivo**

---

## 📈 RESUMEN DE UBICACIONES

| Ubicación Relativa | Archivos | Descripción |
|--------------------|----------|------------|
| `model/dto/` | 6 | Records (DTOs) |
| `model/response/` | 1 | Respuesta genérica |
| `service/` | 6 | Lógica de negocio |
| `controller/` | 6 | REST endpoints |
| `.` (raíz) | 1 | Aplicación principal |
| **TOTAL** | **20** | **Archivos Java** |

---

## 🌍 RUTA COMPLETA DE CADA ARCHIVO

```
1️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\SjmApplication.java

2️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\ParroquiaDTO.java
3️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\BautizosDTO.java
4️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\MisaParticularDTO.java
5️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\MisaComunitariaDTO.java
6️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\HorarioDTO.java
7️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\dto\UsuarioDTO.java
8️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\model\response\ApiResponse.java

9️⃣  C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\ParroquiaService.java
🔟 C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\BautizosService.java
1️⃣1️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\MisaParticularService.java
1️⃣2️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\MisaComunitariaService.java
1️⃣3️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\HorarioService.java
1️⃣4️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\service\UsuarioService.java

1️⃣5️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\ParroquiaController.java
1️⃣6️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\BautizosController.java
1️⃣7️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\MisaParticularController.java
1️⃣8️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\MisaComunitariaController.java
1️⃣9️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\HorarioController.java
2️⃣0️⃣ C:\Users\Martin Cajo\Desktop\Backend PSJM\sjm\src\main\java\com\parroquia\sjm\controller\UsuarioController.java
```

---

## 🎯 ACCESO RÁPIDO A ARCHIVOS

### Para editar DTOs:
```
cd src\main\java\com\parroquia\sjm\model\dto\
```

### Para editar Services:
```
cd src\main\java\com\parroquia\sjm\service\
```

### Para editar Controllers:
```
cd src\main\java\com\parroquia\sjm\controller\
```

### Para ver la app principal:
```
cd src\main\java\com\parroquia\sjm\
code SjmApplication.java
```

---

## 📊 ESTADÍSTICAS

| Métrica | Cantidad |
|---------|----------|
| Archivos Java | 20 |
| Records (DTO) | 6 |
| Services | 6 |
| Controllers | 6 |
| Total de métodos | 34+ |
| Total de endpoints | 28+ |
| Líneas de código | ~1500 |
| Documentos | 6 |

---

## ✅ VERIFICACIÓN

- [x] Todos los archivos están en su ubicación correcta
- [x] Todos compilan exitosamente
- [x] Build: SUCCESS
- [x] No hay errores
- [x] Listos para Supabase integration

---

**¡Tu proyecto está completamente mapeado y documentado!** 📍


