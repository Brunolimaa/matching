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
git clone https://github.com/Brunolimaa/matching.git
cd matching

git checkout master
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

### Access the Swagger documentation 

```
http://localhost:8080/swagger-ui/index.html
```

![image](https://github.com/user-attachments/assets/ed074c00-6398-4ce3-884d-b7add4e82145)


### 7. Test the Endpoint
The backend exposes an endpoint to match devices based on the provided User-Agent string.

You can test the endpoint using curl, Postman, or any other HTTP client.
```

curl --location 'http://localhost:8080/api/    v1/devices/match' \--header 'Content-Type: application/json' \--data 'Mozilla/5.0 (Linux NT 10.0; Win64;     x64) AppleWebKit/537.36 (KHTML, like     Gecko) Chrome/91.0.4472.124 Safari/537.    36'

```
### Example Response:
```
{
    "deviceId": "linux-91.0.4472.124",
    "hitCount": 1,
    "osName": "Linux",
    "osVersion": "linux",
    "browserName": "Chrome 9",
    "browserVersion": "91.0.4472.124",
    "created": true
}
```






