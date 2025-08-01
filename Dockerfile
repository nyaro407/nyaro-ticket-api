FROM openjdk:17-slim
WORKDIR /app
COPY src/ ./src/
RUN javac src/*.java
CMD ["java", "-cp", "src", "Main"]
EXPOSE 8080