# Etapa 1: Build com Maven
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copie o arquivo pom.xml e baixe as dependências primeiro
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código-fonte para a imagem
COPY src /app/src

# Compile o código
RUN mvn clean package 

# Etapa 2: Imagem final
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copie o JAR gerado na etapa anterior
COPY --from=build /app/target/montreal-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta onde o app vai rodar
EXPOSE 8080

# Defina o ponto de entrada para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
