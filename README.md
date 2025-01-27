# Device Matching Backend Service

This is a simple backend service built using Java 11, Spring Boot 2, and Aerospike. The service accepts User-Agent strings and matches a device with the same characteristics (OS name, OS version, Browser name, and Browser version).

## 🚀 Project Structure

- **Java 11**: The backend is built using Java 11.
- **Spring Boot 2**: The service is built using Spring Boot 2 for ease of development and scalability.
- **Aerospike**: Aerospike is used as the database to store device information.

## Requirements

Before you can run the project, you need to have the following installed:

- **Docker**: Docker is required to run Aerospike using Docker Compose.
- **Docker Compose**: For managing multi-container Docker applications.
- **Java 11**: Make sure Java 11 is installed for building and running the Spring Boot application.
- 

### 🔧 Getting Started

## 1. Clone the Repository

Clone this repository to your local machine:

```
git clone https://github.com/your-username/device-matching-backend.git
cd device-matching-backend```
```

### First, clean the project and compile the source code:
```
mvn clean install
```

Next, package the application:
```
mvn clean package
```

The project includes a docker-compose.yml file that sets up the necessary containers for Aerospike.

### To start the containers, run the following command:

### 3. Docker Compose Setup
```
docker-compose build
```
### 4. Start the Docker Containers
```
docker-compose up -d
```

### 7. Test the Endpoint
The backend exposes an endpoint to match devices based on the provided User-Agent string.

You can test the endpoint using curl, Postman, or any other HTTP client.
```
curl -X POST http://localhost:8080/device/match \
  -H "Content-Type: application/json" \
  -d '{
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
  }'
```
### Example Response:
```
{
  "os": "Windows 10",
  "osVersion": "10.0",
  "browser": "Chrome",
  "browserVersion": "58.0.3029.110"
}
```






