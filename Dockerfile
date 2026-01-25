# =========================
# Stage 1: Build
# =========================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy project files
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# ✅ Set the Spring profile to dev
ENV SPRING_PROFILES_ACTIVE=dev

# ✅ CRITICAL: Fix Docker + Mongo Atlas DNS issues
ENV JAVA_TOOL_OPTIONS="\
-Djava.net.preferIPv4Stack=true \
-Dsun.net.inetaddr.ttl=60 \
-Dsun.net.inetaddr.negative.ttl=10"

# Run the application
CMD ["java", "-jar", "app.jar"]
