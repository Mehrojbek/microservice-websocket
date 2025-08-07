# Microservice WebSocket Project

This project is a demonstration of a microservices architecture using Spring Boot, Spring Cloud, and WebSockets.

## About The Project

This project consists of the following modules:

*   **Eureka Server**: A service discovery server that allows microservices to find each other.
*   **API Gateway**: The single entry point for all client requests. It routes requests to the appropriate microservice and handles cross-cutting concerns like security and rate limiting.
*   **WebSocket Service**: A microservice that provides real-time communication using WebSockets.
*   **RabbitMQ**: A message broker used for asynchronous communication between services.

## How It Works

1.  **Service Discovery**: All microservices (`api-gateway` and `websocket-service`) register themselves with the Eureka Server upon startup.
2.  **Request Routing**: When a client sends a request, it first hits the API Gateway.
3.  **Load Balancing**: The API Gateway uses Eureka to look up the address of the requested microservice (e.g., `websocket-service`) and forwards the request.
4.  **WebSocket Communication**: For WebSocket connections, the API Gateway upgrades the connection and forwards it to the WebSocket Service.
5.  **Asynchronous Messaging**: The WebSocket Service uses RabbitMQ to send and receive messages asynchronously, allowing for scalable and resilient communication.

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Java 21 or later
*   Maven 3.6 or later
*   Docker and Docker Compose

### Installation

1.  **Clone the repo**
    ```sh
    git clone https://github.com/your_username_/your_repository.git
    ```
2.  **Start RabbitMQ**
    Navigate to the root of the project and run:
    ```sh
    docker-compose up -d
    ```
3.  **Run Eureka Server**
    Open a new terminal and navigate to the `eureka-server` directory.
    ```sh
    cd eureka-server
    mvn spring-boot:run
    ```
    The Eureka Server will be available at `http://localhost:8761`.

4.  **Run WebSocket Service**
    Open a new terminal and navigate to the `websocket` directory.
    ```sh
    cd websocket
    mvn spring-boot:run
    ```
5.  **Run API Gateway**
    Open a new terminal and navigate to the `api-gateway` directory.
    ```sh
    cd api-gateway
    mvn spring-boot:run
    ```
    The API Gateway is now listening on `http://localhost:8080`.

## Other parts

### API Endpoints

The API Gateway exposes the following endpoints:

*   **WebSocket Connection**: `ws://localhost:8080/ws`
*   **Send Message**: `POST http://localhost:8080/api/send`

### Configuration

Each service has its own `application.yml` file located in `src/main/resources`. You can modify these files to change the configuration, such as port numbers and database connections.
