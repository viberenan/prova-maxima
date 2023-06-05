# Imagem base com Maven
FROM maven:3.8.4-openjdk-17-slim

# Diretório de trabalho no contêiner
WORKDIR /app

# Copiar todo o conteúdo do diretório atual para o contêiner
COPY . .

# Build do projeto com Maven
RUN mvn package -DskipTests

# Expor a porta em que o aplicativo está sendo executado
EXPOSE 8082

# Comando para executar o aplicativo
CMD ["java", "-jar", "./target/maxima-0.0.1-SNAPSHOT.jar"]