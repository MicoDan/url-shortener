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

### Backend Setup

#### Prerequisites
- JDK 23
- MySQL 8.0+
- Maven 3.9+

#### Database Configuration
1. Create MySQL database:
```sql
CREATE DATABASE urlShortener;
```

2. Configure `application.properties` in `backend/src/main/resources/`:
```properties
# Database Configuration
spring.datasource.url=${YOUR_DATABASE_URL}
spring.datasource.username=${YOUR_DATABASE_USERNAME}
spring.datasource.password=${YOUR_DATABASE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=${YOUR_DATABASE_DIALECT}

frontend.url=${FRONTEND_URL:http://localhost:5173}
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

## Docker Deployment


### Backend Deployment
```bash
cd backend
docker build -t url-shortener-backend .
docker run -p 8080:8080 url-shortener-backend
```

### Frontend Deployment
```bash
cd frontend
docker build -t url-shortener-frontend .
docker run -p 80:80 url-shortener-frontend
```

## Development Guidelines

### Backend Development
- Follow REST API best practices
- Implement proper error handling
- Use DTOs for data transfer
- Write unit tests for services
- Document API endpoints

### Frontend Development
- Use functional components
- Implement proper state management
- Follow React best practices
- Maintain responsive design
- Handle API errors gracefully

## Troubleshooting

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

### Test Configuration
Create `application-test.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

### Coverage Goals
- Service Layer: 80%
- Controller Layer: 70%
- Repository Layer: 60%
- Overall: 75%

### Access Swagger
- http://localhost:8080/swagger-ui/index.html


### Rate Limiting
- IP-based rate limiting
- User-based rate limiting for authenticated endpoints
- Configurable limits in application properties