# URL Shortener Application

A comprehensive URL shortening service built with Spring Boot and React, featuring user authentication, analytics tracking, and a responsive dashboard interface.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Detailed Setup Instructions](#detailed-setup-instructions)
  - [Database Setup](#database-setup)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [API Documentation](#api-documentation)
  - [Authentication Endpoints](#authentication-endpoints)
  - [URL Management Endpoints](#url-management-endpoints)
- [Authentication System](#authentication-system)
- [Running the App through Docker](#running-the-app-through-docker)
- [Common Issues and Solutions](#common-issues-and-solutions)

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

### Database Setup

#### MySQL Setup

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
   CREATE DATABASE url_shortener;
   
   # Create user and grant privileges
   CREATE USER 'urluser'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON url_shortener.* TO 'urluser'@'localhost';
   FLUSH PRIVILEGES;
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
```

2. Build and Run the Backend:
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### Frontend Setup

#### Prerequisites
- Node.js 18+
- npm or yarn

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
```

#### Login User
```http
POST /auth/login
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}
```

### URL Management Endpoints

#### Create Short URL
```http
POST /urls/shorten
Authorization: Bearer {jwt_token}
```

#### Get User's URLs
```http
GET /urls/
Authorization: Bearer {jwt_token}
```

#### Get URL Analytics
```http
GET /urls/analytics/{shortUrl}?startDate={date}&endDate={date}
Authorization: Bearer {jwt_token}
```

## Authentication System
- JWT-based authentication
- Secure password storage using BCrypt
- Role-based access control

## Running the App through Docker

1. Ensure Docker is installed and running.
2. Navigate to the project directory:
```bash
you are in the root directory by default if you clone this repository
```
3. Build and start containers:
```bash
docker-compose up --build
```
4. Check running containers:
```bash
docker ps
```
5. Access the applications:
   - **Frontend**: [http://localhost:5173](http://localhost:5173)
   - **Backend API**: [http://localhost:8080](http://localhost:8080)
   - **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Common Issues and Solutions

### Frontend Not Loading
- Ensure the container is running.
- Verify port 5173 is free.
- Check frontend logs: `docker-compose logs -f frontend`.

### Backend API Not Responding
- Ensure port 8080 is free.
- Check backend logs: `docker-compose logs -f backend`.
- Verify database connection.

### Database Connection Failed
- Ensure MySQL container is running: `docker ps`.
- Check database credentials.

### Port Conflicts
Modify ports in `docker-compose.yml`:
```yaml
frontend:
  ports:
    - "3000:80"  # Change 5173 to 3000
backend:
  ports:
    - "8081:8080"  # Change 8080 to 8081
```

## Testing
- Unit tests for backend using JUnit and Mockito.
- Integration tests for API endpoints.
- End-to-end tests using Cypress for frontend.

---
This document is now well-structured, with proper links and formatting for easy navigation.

