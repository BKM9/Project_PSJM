# ==========================================
# Etapa 1: Construcción (Usamos JDK estándar para evitar errores de Gradle)
# ==========================================
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Nos aseguramos de dar permisos de ejecución y corregir saltos de línea por si acaso
RUN chmod +x ./gradlew

# Compilamos saltándonos los tests unitarios para asegurar un despliegue rápido
RUN ./gradlew clean bootJar -x test --no-daemon

# ==========================================
# Etapa 2: Ejecución (Mantenemos JRE Alpine para que pese poquísimo)
# ==========================================
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# OPTIMIZACIÓN DINÁMICA DE MEMORIA PARA RENDER
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0 -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
