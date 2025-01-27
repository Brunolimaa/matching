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

### 2. Docker Compose Setup
```
docker-compose build
```
### 3. Start the Docker Containers
```
docker-compose up -d
```

### Access the Swagger documentation 

```
http://localhost:8080/swagger-ui/index.html
```

![image](https://github.com/user-attachments/assets/ed074c00-6398-4ce3-884d-b7add4e82145)


### 4. Test the Endpoint
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

# Architecture

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── device
│   │   │           └── matching
│   │   │               ├── config
│   │   │               │   ├── AerospikeConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller
│   │   │               │   ├── DeviceApi.java
│   │   │               │   └── DeviceController.java
│   │   │               ├── dto
│   │   │               │   ├── request
│   │   │               │   └── response
│   │   │               │       └── DeviceResponseDTO.java
│   │   │               ├── exception
│   │   │               │   ├── ErrorResponse.java
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               ├── mapper
│   │   │               │   └── DeviceMapper.java
│   │   │               ├── MatchingApplication.java
│   │   │               ├── model
│   │   │               │   └── Device.java
│   │   │               ├── repository
│   │   │               │   ├── DeviceAerosPikeRepository.java
│   │   │               │   └── DeviceRepository.java
│   │   │               ├── service
│   │   │               │   ├── DeviceServiceImpl.java
│   │   │               │   └── DeviceService.java
│   │   │               └── util
│   │   │                   ├── ErrorMessages.java
│   │   │                   └── UserAgentParser.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── config
│   │           └── aerospike.conf

```

### 1. AerospikeConfig.java
Purpose: This is a configuration class that initializes the connection to the Aerospike database using the AerospikeClient. 
It reads configuration values from properties (e.g., host, port, timeout) and provides the client as a Spring Bean.

#### Advantages:

Centralizes the configuration related to the Aerospike connection.
Makes it easy to modify or switch configurations (e.g., different database hosts) without altering the business logic.

### 2. DeviceController.java
Purpose: This class handles the HTTP requests related to device operations. It uses DeviceService to create, update, retrieve, or delete devices based on user agent strings or device IDs. 
It implements the DeviceApi interface and returns appropriate HTTP responses.

#### Advantages:

Separation of Concerns: The controller focuses only on handling the web layer (HTTP requests and responses), delegating the business logic to the DeviceService.

### 3. DeviceApi.java
Purpose: This interface defines the contract for the device-related endpoints. 
It provides method signatures for creating, updating, retrieving, and deleting devices.

#### Advantages:

API Documentation: It uses annotations like @Operation and @ApiResponses to document the API, which can be used for Swagger generation.
Loose Coupling: The DeviceController implements this interface, allowing for flexibility in handling the API implementation.

### 4. GlobalExceptionHandler.java
Purpose: This is a global exception handler for the application. It handles different types of exceptions (Exception, ResourceNotFoundException, and ValidationException) and returns appropriate error responses.

#### Advantages:

Centralized Error Handling: Instead of handling exceptions individually in controllers, this class ensures all exceptions are handled uniformly across the application.
Custom Responses: Provides custom error messages in a consistent format, improving client experience when errors occur.

### 5. DeviceService.java
Purpose: This service contains the business logic for handling devices. It processes user agent strings to either create/update a device or fetch devices based on OS name or device ID.

#### Advantages:

Business Logic Isolation: Keeps business logic separate from the controller layer, improving maintainability.
Error Handling: It throws ResourceNotFoundException when devices are not found, allowing for custom error handling.

### 6. DeviceRepository.java
Purpose: This is a repository interface for device-related database operations. It defines methods like save, updateHit, findById, and deleteById.

#### Advantages:

Separation of Data Access: Dependency Inversion By using a repository pattern, the data access layer is isolated from the rest of the application, allowing you to easily switch databases if needed (e.g., switching from Aerospike to another DB).
Consistency: Provides a consistent interface for interacting with the database.

### 7. DeviceAerosPikeRepository.java
Purpose: This implementation of DeviceRepository interacts with the Aerospike database. It uses Aerospike's API to perform CRUD operations on the Device objects.

#### Advantages:

Database Abstraction: It abstracts the Aerospike-specific implementation, allowing for potential future changes without affecting the rest of the application.
Efficient Data Operations: Implements specialized database operations like querying by OS name and indexing for fast lookups.

### 8. UserAgentParser.java
Purpose: This utility class parses a user-agent string to extract device information such as operating system name, version, browser name, and version. It creates a Device object with this data.

#### Advantages:

Utility Class: The class centralizes the logic for parsing user-agent strings, making it reusable throughout the application.
Encapsulation: It abstracts away the complexity of parsing and extracting device details from user-agent strings.

## Advantages of Architecture (Overall Separation)

#### Maintainability: 
By separating the responsibilities into distinct layers (controllers, services, repositories, configuration, exceptions, and utilities), each class has a clear, focused responsibility. This makes your code easier to maintain and extend.

#### Testability: 
The separation of concerns allows for better unit testing of individual components (e.g., you can test DeviceService without worrying about HTTP handling, or test DeviceAerosPikeRepository without involving the business logic).

#### Flexibility: 
If you need to change a part of your application (e.g., change database providers or modify how devices are matched), you can do so without affecting the rest of the system.

#### Reusability: 
By isolating the user-agent parsing logic in a utility class (UserAgentParser), the code for extracting device information can be reused across different services, improving code reuse.

#### Clear API Design: 
The separation of the controller and service layers (and usage of interfaces like DeviceApi) allows for cleaner API designs. You can easily modify the API behavior by adjusting the service layer, without changing how requests are handled in the controller.


This structure is well-aligned with SOLID principles, especially Dependency Inversion (D), where your services and controllers depend on abstractions (interfaces) rather than concrete implementations, promoting flexibility and testability.





