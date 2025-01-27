# Usando a imagem oficial do OpenJDK 11
FROM openjdk:11-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR compilado para o container
COPY target/matching-0.0.1-SNAPSHOT.jar matching.jar

# Expor a porta que o Spring Boot vai rodar (padrão 8080)
EXPOSE 8080

# Comando para rodar o Spring Boot
ENTRYPOINT ["java", "-jar", "matching.jar"]
