# Etapa 1: Construcción
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

# Etapa 2: Ejecución
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# OPTIMIZACIÓN DINÁMICA DE MEMORIA PARA RENDER
# -XX:MaxRAMPercentage=80.0 : Usa el 80% de la RAM asignada al contenedor (aprox 400MB en plan free)
# -XX:+UseSerialGC : GC eficiente para 0.1vCPU de Render
# -XX:+ExitOnOutOfMemoryError : Reinicia el contenedor si se queda sin memoria para evitar estados corruptos
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0 -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
