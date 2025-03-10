# Car Management System

This is a Car Management System built with Spring Boot, GraphQL, and PostgreSQL. Below are the instructions to build and run the project both locally and using Docker.

## Running the Project Locally

1. **Ensure that the PostgreSQL database is set up:**

    - Run the `create-db.sh` script to create the necessary database.

   ```sh
   ./create-db.sh
   ```

2. **Build and Run the Application:**

    - Use Gradle to start the Spring Boot application.

   ```sh
   ./gradlew bootRun
   ```

## Running the Project in a Docker Container

1. **Build the Docker Image:**

    - Navigate to the project root and build the Docker image.

   ```sh
   docker build -t car-management-system .
   ```

2. **Run the Application with Docker Compose:**

    - Start the application and database containers.

   ```sh
   docker-compose up
   ```

This will start both the PostgreSQL database and the application in separate containers.

