# Étape 1 : Utiliser l'image Maven officielle pour construire l'application
FROM maven:3.8.4-openjdk-17 AS build

# Copier les fichiers du projet dans le conteneur
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Construire l'application avec Maven
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser l'image Corretto pour exécuter l'application
FROM amazoncorretto:17-alpine-jdk AS runtime

# Copier le fichier JAR construit précédemment dans le conteneur
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application écoute (remplacez le port si nécessaire)
EXPOSE 8080

# Commande à exécuter lorsque le conteneur démarre
CMD ["java", "-jar", "app.jar"]