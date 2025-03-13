# URL Shortener Application

A comprehensive URL shortening service built with Spring Boot and React, featuring user authentication, analytics tracking, and a responsive dashboard interface.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Detailed Setup Instructions](#detailed-setup-instructions)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [API Documentation](#api-documentation)
- [Authentication System](#authentication-system)
- [Docker Deployment](#docker-deployment)
- [Development Guidelines](#development-guidelines)
- [Troubleshooting](#troubleshooting)

## Features
- URL shortening with custom analytics
- User registration and authentication
- Real-time click tracking
- Interactive analytics dashboard
- Rate limiting protection
- Mobile-responsive design
- Secure JWT-based authentication
- Cross-origin resource sharing (CORS) support

## Tech Stack

### Backend Technologies
- Java 23
- Spring Boot 3.4.0
- Spring Security
- Spring Data JPA
- MySQL Database
- JWT Authentication
- Maven Build Tool
- Bucket4j for Rate Limiting

### Frontend Technologies
- React 18.3.1
- React Router v7
- Tailwind CSS
- Chart.js for Analytics
- React Query
- Axios for HTTP requests
- Material-UI Components
- Framer Motion for animations

## Detailed Setup Instructions

## Database Setup

### MySQL Setup

1. **Install MySQL**
   ```bash
   # For Ubuntu/Debian
   sudo apt install mysql-server
   
   # For Windows
   # Download MySQL Installer from https://dev.mysql.com/downloads/installer/
   ```

2. **Start MySQL Service**
   ```bash
   # For Ubuntu/Debian
   sudo systemctl start mysql
   
   # For Windows
   # MySQL service starts automatically after installation
   ```

3. **Create Database and User**
   ```sql
   # Log into MySQL
   mysql -u root -p
   
   # Create database
   CREATE DATABASE your_database_name;
   
   # Create user and grant privileges
   CREATE USER 'urluser'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON url_shortener.* TO 'urluser'@'localhost';
   FLUSH PRIVILEGES;
   ```


### Common Issues

1. **Connection Refused**
   - Verify MySQL is running
   - Check port availability (default 3306)
   - Ensure firewall allows MySQL connections

2. **Access Denied**
   - Verify username and password
   - Check user privileges
   ```sql
   SHOW GRANTS FOR 'urluser'@'localhost';
   ```

3. **Database Not Found**
   - Verify database name
   - Check if database exists
   ```sql
   SHOW DATABASES;
   ```

### Backend Setup

#### Prerequisites
- JDK 23
- MySQL 8.0+
- Maven 3.9+


1. Configure `application.properties` in `backend/src/main/resources/`:
```properties
# Database Configuration
spring.datasource.url=${YOUR_DATABASE_URL}
spring.datasource.username=${YOUR_DATABASE_USERNAME}
spring.datasource.password=${YOUR_DATABASE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=${YOUR_DATABASE_DIALECT}

frontend.url=http://localhost:5173
logging.level.org.springframework.security=DEBUG
logging.level.org.url.shortener=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework=DEBUG
logging.level.org.springframework.web.servlet=TRACE


# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000

# Server Configuration
server.port=8080
```

#### Building and Running
1. Navigate to backend directory:
```bash
cd backend
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

### Frontend Setup

#### Prerequisites
- Node.js 18+
- npm or yarn

#### Installation Steps
1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Configure environment variables in `.env`:
```env
VITE_BACKEND_URL=http://localhost:8080
VITE_REACT_FRONT_END_URL=http://localhost:5173
VITE_REACT_SUBDOMAIN=http://url.localhost:5173
```

4. Start development server:
```bash
npm run dev
```

## API Documentation

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
    "username": "string",
    "email": "string",
    "password": "string"
}

Response: 200 OK
{
    "message": "User registered successfully"
}
```

#### Login User
```http
POST /auth/login
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}

Response: 200 OK
{
    "token": "JWT_TOKEN_STRING"
}
```

### URL Management Endpoints

#### Create Short URL
```http
POST /urls/shorten
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
    "originalUrl": "string"
}

Response: 200 OK
{
    "id": "number",
    "originalUrl": "string",
    "shortUrl": "string",
    "clickCount": "number",
    "createdDate": "datetime",
    "username": "string"
}
```

#### Get User's URLs
```http
GET /urls/
Authorization: Bearer {jwt_token}

Response: 200 OK
[
    {
        "id": "number",
        "originalUrl": "string",
        "shortUrl": "string",
        "clickCount": "number",
        "createdDate": "datetime",
        "username": "string"
    }
]
```

#### Get URL Analytics
```http
GET /urls/analytics/{shortUrl}?startDate={date}&endDate={date}
Authorization: Bearer {jwt_token}

Response: 200 OK
[
    {
        "clickDate": "date",
        "count": "number"
    }
]
```

## Authentication System

### JWT Token Structure
- Header: Algorithm & token type
- Payload: User data & expiration
- Signature: Verification signature

### Token Management
1. **Token Generation**: Upon successful login
2. **Token Storage**: Client-side in localStorage
3. **Token Usage**: Included in Authorization header
4. **Token Expiration**: 24 hours by default

### Security Measures
- CORS protection
- Rate limiting
- Password encryption using BCrypt
- Protected routes requiring authentication
- XSS protection headers

### Rate Limiting
- IP-based rate limiting
- User-based rate limiting for authenticated endpoints
- Configurable limits in application properties

## Running the app through docker



### **Step 1: Start Docker**
Ensure Docker Desktop is installed and running on your system.

### **Step 2: Navigate to the Directory**
Go to the directory containing your `docker-compose.yml` file:
By default it's the root directory


### **Step 3: Build and Start the Containers**
Run the following command to start all services:
```sh
docker-compose up --build
```
- For background mode, use:
  ```sh
  docker-compose up -d --build
  ```

### **Step 4: Check Running Containers**
Verify that all containers are running:
```sh
docker ps
```
You should see three containers:
- `frontend` (React application)
- `backend` (Spring Boot application)
- `db` (MySQL database)

### **Step 5: Access the Applications**
Once everything is running:
- Frontend: **http://localhost:5173**
- Backend API: **http://localhost:8080**
- Swagger UI: **http://localhost:8080/swagger-ui/index.html**
- MySQL Database: **localhost:3306**

### **Step 6: Debugging (if not working)**
If you encounter issues:

1. **Check Service Logs**
   ```sh
   # All services
   docker-compose logs -f

   # Specific service
   docker-compose logs -f backend
   docker-compose logs -f frontend
   docker-compose logs -f db
   ```

2. **Restart Services**
   ```sh
   # Restart everything
   docker-compose down
   docker-compose up -d --build

   # Restart specific service
   docker-compose restart backend
   ```

3. **Check Database Connection**
   ```sh
   # Connect to MySQL
   docker exec -it url-shortener-db mysql -u root urlShortener
   ```

4. **Clean Start**
   ```sh
   # Remove all containers and volumes
   docker-compose down -v
   # Rebuild and start
   docker-compose up --build -d
   ```

### **Common Issues and Solutions**

1. **Frontend Not Loading**
   - Check if the container is running
   - Verify port 5173 is not in use
   - Check frontend logs for errors

2. **Backend API Not Responding**
   - Ensure port 8080 is free
   - Check backend logs for startup errors
   - Verify database connection

3. **Database Connection Failed**
   - Check if MySQL container is healthy
   - Verify database credentials
   - Ensure database is initialized properly

4. **Port Conflicts**
   If you have port conflicts, modify the ports in `docker-compose.yml`:
   ```yaml
   frontend:
     ports:
       - "3000:80"  # Change 5173 to 3000
   backend:
     ports:
       - "8081:8080"  # Change 8080 to 8081
   ```

## Testing

### Test Structure
```bash
src/test/java/
├── service/          # Unit tests
├── controller/       # Integration tests
└── repository/      # Repository tests
```

### Unit Tests
Tests individual components using mocks:
- `UserServiceTest`: User management operations
- `UrlMappingServiceTest`: URL shortening operations

### Integration Tests
Tests components working together:
- `AuthControllerIntegrationTest`: Authentication flows
- `UrlMappingControllerIntegrationTest`: URL management endpoints
- `UrlMappingRepositoryTest`: Database operations

### Running Tests
```bash
# Run all tests
./mvnw test

# Run specific test
./mvnw test -Dtest=UserServiceTest

# Run with coverage
./mvnw verify
```


### Coverage Goals
- Service Layer: 80%
- Controller Layer: 70%
- Repository Layer: 60%
- Overall: 75%

## Troubleshooting whole application

### Common Backend Issues
1. Database connection errors
   - Verify MySQL is running
   - Check connection credentials
   - Ensure database exists

2. JWT token issues
   - Verify secret key configuration
   - Check token expiration
   - Validate token format

### Common Frontend Issues
1. API connection errors
   - Verify backend is running
   - Check CORS configuration
   - Validate API endpoint URLs

2. Authentication issues
   - Clear localStorage
   - Check token expiration
   - Verify credentials

### Access Swagger
- http://localhost:8080/swagger-ui/index.html

