# Etapa 1: Build
FROM gradle:8.3.2-jdk17 AS build
WORKDIR /app

# Copiar arquivos do projeto
COPY build.gradle settings.gradle gradle/ gradlew ./
COPY src ./src

# Build do projeto
RUN gradle clean bootJar --no-daemon

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar o JAR construído
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
