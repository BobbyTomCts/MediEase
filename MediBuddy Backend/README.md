# MediEase Backend - Spring Boot Application

## Overview
MediEase is a comprehensive insurance management system built with Spring Boot that handles user management, insurance plans, network hospitals, and claims processing.

---

## 🔒 JWT Authentication (Simple & Secure)

### How It Works
**ONE file handles all authentication: `JwtUtil.java`**

This file automatically:
- ✅ Generates JWT tokens during login
- ✅ Validates tokens on every request
- ✅ Protects all endpoints except login/register
- ✅ Returns 401 for invalid/missing tokens

### Protected vs Public Endpoints

**Public (No Token Required):**
- `POST /api/users/register`
- `POST /api/users/login`
- `/swagger-ui/**`
- `/v3/api-docs/**`

**Protected (Token Required):**
- All other endpoints require `Authorization: Bearer <token>` header

### Quick Start

**1. Login:**
```bash
POST /api/users/login?email=user@example.com&password=password123
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "userId": 123,
  "role": "EMPLOYEE"
}
```

**2. Use Token in Requests:**
```bash
GET /api/users/123
Headers: 
  Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

### Accessing User Info in Controllers
```java
@GetMapping("/{id}")
public User getUser(@PathVariable Long id, HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    String email = (String) request.getAttribute("userEmail");
    String role = (String) request.getAttribute("userRole");
    
    return userService.getUserById(id);
}
```

---

## 🏗️ Project Structure

```
src/main/java/com/backend/mediassist/
├── config/
│   ├── JwtUtil.java              # JWT authentication (generates & validates tokens)
│   ├── SecurityConfig.java       # CORS and security configuration
│   ├── SwaggerConfig.java        # API documentation configuration
│   └── DataInitializer.java      # Database initialization
│
├── controller/
│   ├── UserController.java       # User management endpoints
│   ├── RequestController.java    # Claims/requests management
│   ├── InsuranceController.java  # Insurance plans management
│   └── NetworkHospitalController.java  # Hospital network management
│
├── service/
│   ├── UserService.java          # User business logic
│   ├── RequestService.java       # Request processing logic
│   ├── InsuranceService.java     # Insurance plan logic
│   └── NetworkHospitalService.java  # Hospital network logic
│
├── repository/
│   ├── UserRepository.java
│   ├── RequestRepository.java
│   ├── InsuranceRepository.java
│   ├── DependantRepository.java
│   └── NetworkHospitalRepository.java
│
└── model/
    ├── User.java
    ├── Request.java
    ├── Insurance.java
    ├── Dependant.java
    ├── NetworkHospital.java
    └── LoginResponse.java
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MediBuddy Backend
   ```

2. **Configure Database**
   
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/mediease
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

---

## 📚 API Documentation

### Swagger UI
Once the application is running, access the interactive API documentation:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/v3/api-docs

### Main Endpoints

#### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `GET /api/users/isAdmin/{userId}` - Check admin status

#### Insurance Plans
- `GET /api/insurance/all` - Get all insurance plans
- `GET /api/insurance/{id}` - Get insurance by ID
- `POST /api/insurance/select` - Select insurance plan

#### Claims/Requests
- `GET /api/requests/all` - Get all requests
- `POST /api/requests/create` - Create new request
- `PUT /api/requests/approve/{id}` - Approve request
- `PUT /api/requests/reject/{id}` - Reject request
- `GET /api/requests/filtered` - Get filtered requests

#### Network Hospitals
- `GET /api/hospitals/all` - Get all network hospitals
- `GET /api/hospitals/search` - Search hospitals

---

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/mediease
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
# Token validity: 24 hours (configured in JwtUtil.java)

# CORS Configuration
# Allowed origin: http://localhost:4200 (configured in SecurityConfig.java)
```

---

## 🔐 Security Features

- ✅ **JWT Authentication** - Stateless token-based authentication
- ✅ **Password Encryption** - BCrypt password hashing
- ✅ **CORS Protection** - Configured for Angular frontend (localhost:4200)
- ✅ **Role-based Access** - Admin and Employee roles
- ✅ **Token Expiration** - 24-hour token validity
- ✅ **Automatic Protection** - All endpoints secured by default

---

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test with Postman

1. **Login**
   ```
   POST http://localhost:8080/api/users/login
   Params: email=admin@example.com&password=admin123
   ```

2. **Copy the token from response**

3. **Make authenticated request**
   ```
   GET http://localhost:8080/api/users/1
   Headers: Authorization: Bearer <paste-token-here>
   ```

---

## 🗄️ Database Schema

The application uses MySQL with JPA/Hibernate for ORM.

**Main Tables:**
- `users` - User accounts and profiles
- `insurance` - Insurance plans
- `requests` - Claims and requests
- `dependants` - User dependants
- `network_hospitals` - Approved hospital network

---

## 🌐 CORS Configuration

Configured to allow requests from Angular frontend:
- **Allowed Origin:** http://localhost:4200
- **Allowed Methods:** GET, POST, PUT, DELETE, OPTIONS
- **Allowed Headers:** All
- **Allow Credentials:** Yes

---

## 📝 Development Notes

### Adding New Protected Endpoints
All endpoints are automatically protected except those in JwtUtil's skip list:
- `/login`
- `/register`
- `/swagger`
- `/api-docs`

No additional configuration needed!

### Accessing Current User in Controllers
```java
HttpServletRequest request // Inject this
Long userId = (Long) request.getAttribute("userId");
String role = (String) request.getAttribute("userRole");
```

---

## 🐛 Troubleshooting

### 401 Unauthorized Error
- **Check:** Authorization header format: `Bearer <token>`
- **Check:** Token hasn't expired (24 hours)
- **Check:** Token is valid (not modified)

### CORS Error
- **Check:** Frontend is running on http://localhost:4200
- **Fix:** Update `SecurityConfig.java` if using different port

### Database Connection Error
- **Check:** MySQL is running
- **Check:** Database credentials in `application.properties`
- **Check:** Database `mediease` exists

---

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

---

## 📄 License

This project is licensed under the MIT License.

---

## 📞 Support

For issues and questions:
- Check Swagger documentation
- Review this README
- Check application logs

---

**Built with ❤️ using Spring Boot**
