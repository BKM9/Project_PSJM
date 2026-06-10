# ==========================================
# Etapa 1: Construcción (Usamos la imagen OFICIAL de Gradle)
# ==========================================
FROM gradle:9.5.1-jdk17 AS build
WORKDIR /app

# Copiamos todo el proyecto al contenedor
COPY . .

# 🌟 LA LÍNEA SALVADORA: Le damos permisos de ejecución al wrapper en Linux
RUN chmod +x ./gradlew

# Desactivamos la auto-descarga de Toolchains y obligamos a usar el JDK de la imagen
RUN ./gradlew clean bootJar -x test --no-daemon -Porg.gradle.java.installations.auto-download=false

# ==========================================
# Etapa 2: Ejecución (Imagen ultra ligera para Render)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# OPTIMIZACIÓN DINÁMICA DE MEMORIA PARA RENDER
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0 -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
