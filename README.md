

# Content Management Platform

A robust and secure Content Management Platform built with Spring Boot, designed to manage users, posts, and categories with role-based access control and JWT authentication. This project provides RESTful APIs for user registration, authentication, content creation, and administration, with comprehensive error handling and API documentation using Swagger/OpenAPI.

---

## Table of Contents

- [Project Summary](#project-summary)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Security](#security)
- [Error Handling](#error-handling)
- [Swagger Documentation](#swagger-documentation)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Project Summary

This Content Management Platform allows users to register and authenticate with JWT-based security. It supports multiple user roles (`ADMIN`, `AUTHOR`, and `USER`) with different access privileges:

- **ADMIN**: Manage users and categories.
- **AUTHOR**: Create and manage posts and categories.
- **USER**: View posts and manage their own profile.

The platform supports CRUD operations on posts and categories, with validation and exception handling to ensure data integrity and a smooth user experience.

---

## Features

- **User Management**
  - User registration with role assignment (`ADMIN`, `AUTHOR`, `USER`)
  - User authentication with JWT token issuance and refresh tokens
  - Fetch and update user profiles
  - Admin can fetch all users and specific user details

- **Post Management**
  - Create, read, update posts with various types (`ARTICLE`, `BLOG`, `CASE_STUDY`, `EBOOK`, `DIY_TUTORIAL`, `NEWS`)
  - Fetch posts by ID, title, or category
  - Validation on post data (title length, description length, content length, post type)

- **Category Management**
  - Create and fetch categories
  - Validation to prevent duplicate categories

- **Security**
  - JWT-based authentication and authorization
  - Role-based access control on API endpoints
  - Secure password storage with BCrypt hashing
  - Stateless session management

- **API Documentation**
  - Swagger/OpenAPI integration for interactive API exploration

- **Exception Handling**
  - Custom exceptions for resource not found, resource already exists, and general errors
  - Consistent error response format with detailed messages

---

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA (Hibernate)
- JWT (JSON Web Tokens)
- ModelMapper
- Lombok
- H2 Database (in-memory, can be replaced)
- Swagger/OpenAPI (springdoc-openapi)
- Maven

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VSCode, etc.)

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/content-management-platform.git
   cd content-management-platform
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. The application will start on `http://localhost:8080`.

---

## API Endpoints

### Authentication

- `POST /v1/auth/signUp` - Register a new user
- `POST /v1/auth/login` - Authenticate user and receive JWT token
- `POST /v1/auth/refreshToken` - Refresh JWT token
- `GET /v1/auth/validateToken` - Validate JWT token

### User

- `GET /v1/user/getUserProfile` - Get logged-in user's profile
- `PUT /v1/user/updateUser/{userId}` - Update user details by user ID

### Admin (Requires ADMIN role)

- `GET /v1/admin/getAllUsers` - Get all users
- `GET /v1/admin/getUserByUserId/{userId}` - Get user by ID
- `POST /v1/admin/addCategory` - Add a new category

### Category (Requires AUTHOR role)

- `GET /v1/category/getAllCategories` - Get all categories
- `GET /v1/category/getCategoryById/{categoryId}` - Get category by ID
- `GET /v1/category/getCategoryByName` - Get category by name

### Post

- `POST /v1/posts/addPost` - Add a new post (Requires AUTHOR role)
- `GET /v1/posts/getAllPosts` - Get all posts (AUTHOR and USER roles)
- `GET /v1/posts/getPostById/{postId}` - Get post by ID
- `GET /v1/posts/getPostByTitle` - Get post by title
- `GET /v1/posts/getPostsByCategory` - Get posts by category
- `PUT /v1/posts/updatePost/{postId}` - Update post by ID

---

## Security

- Passwords are stored securely using BCrypt hashing.
- JWT tokens are used for stateless authentication.
- Role-based access control restricts access to endpoints based on user roles.
- Unauthorized access returns a JSON error message with HTTP 401 status.

---

## Error Handling

The platform uses custom exceptions and a global exception handler to return consistent error responses with:

- HTTP status code
- Timestamp
- Error message
- Debug message (optional)
- Validation error details (for input validation failures)

Example error response:

```json
{
  "status": "NOT_FOUND",
  "timestamp": "27-04-2024 10:15:30",
  "message": "Resource Not Found",
  "debugMessage": "No user found with id: 123"
}
```

---

## Swagger Documentation

API documentation is available via Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

This interface allows you to explore and test all available endpoints interactively.

![image](https://github.com/user-attachments/assets/4e9192a8-3b54-4ce4-a7dd-91006f0afbfd)


![image](https://github.com/user-attachments/assets/990dc903-c498-41a1-85d2-4a926b282131)



---

## Project Structure

```
src/main/java/com/content/platform
├── configs
│   ├── Configurations.java          # Swagger and ModelMapper config
│   └── SecurityConfig.java          # Spring Security config
├── controller
│   ├── AdminController.java         # Admin APIs
│   ├── AuthenticationController.java # Auth APIs
│   ├── CategoryController.java      # Category APIs
│   ├── PostController.java          # Post APIs
│   └── UserController.java          # User APIs
├── dto
│   ├── request                      # Request DTOs
│   └── response                     # Response DTOs
├── exception
│   ├── CustomException.java
│   ├── ResourceAlreadyExistException.java
│   ├── ResourceNotFoundException.java
│   └── CustomGeneralExceptionHandler.java # Global exception handler
├── model
│   ├── UserModel.java               # User entity implementing UserDetails
│   ├── PostModel.java
│   ├── CategoryModel.java
│   ├── Role.java                   # Enum for user roles
│   └── PostType.java               # Enum for post types
├── repository
│   ├── UserRepository.java
│   ├── PostRepository.java
│   └── CategoryRepository.java
├── security
│   ├── JwtService.java             # JWT token handling
│   ├── JwtAuthFilter.java          # JWT filter for requests
│   ├── JwtAuthenticationEntryPoint.java # Handles unauthorized access
│   └── UserDetailsServiceImpl.java # Loads user details for auth
├── service
│   ├── AuthenticationService.java
│   ├── UserService.java
│   ├── PostService.java
│   ├── CategoryService.java
│   └── AdminService.java
└── util
    └── PlatformConstants.java      # Constants like post types list
```

---

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your improvements.

---

*Happy coding!* 🚀
