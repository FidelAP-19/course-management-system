# Course Management System

A full-stack course registration system built with Spring Boot, JPA, and React (frontend in progress).

## 🚀 Features

- REST API with full CRUD operations for Courses, Students, and Faculty
- JPA entities with many-to-many relationships
- Custom Bean Validation validators
- Global exception handling with consistent error responses
- H2 in-memory database for development
- Professional error handling and input validation

## 🛠️ Tech Stack

**Backend:**
- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- Bean Validation
- H2 Database
- Maven

**Frontend (Coming Soon):**
- React
- TypeScript
- Tailwind CSS

## 📋 API Endpoints

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/{dept}/{num}` - Get course by department and number
- `POST /api/courses` - Create new course
- `DELETE /api/courses/{dept}/{num}` - Delete course

### Students
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `POST /api/students` - Create new student
- `DELETE /api/students/{id}` - Delete student

### Faculty
- `GET /api/faculty` - Get all faculty
- `GET /api/faculty/{id}` - Get faculty by ID
- `POST /api/faculty` - Create new faculty
- `DELETE /api/faculty/{id}` - Delete faculty
- `POST /api/faculty/{id}/courses` - Add courses to faculty

## 🏃 Running the Application
```bash
# Clone the repository
git clone https://github.com/YOUR-USERNAME/course-management-system.git

# Navigate to project directory
cd course-management-system

# Run with Maven
./mvnw spring-boot:run

# Application runs on http://localhost:8080
```

## 🧪 Testing

Use Postman or curl to test the API endpoints.

Example:
```bash
# Create a student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "birthYear": 2000,
    "major": "Computer Science",
    "isGraduate": false
  }'
```

## 📚 Project Structure
```
src/main/java/com/TonyPerez/coursemanagement/
├── domain/          # JPA entities
├── repository/      # Spring Data JPA repositories
├── controller/      # REST controllers
├── validation/      # Custom validators
├── exception/       # Exception handling
└── Application.java # Main application class
```

## 🎯 Development Roadmap

- [x] Backend REST API
- [x] Bean Validation
- [x] Exception Handling
- [ ] Query parameter filtering
- [ ] Unit tests
- [ ] Integration tests
- [ ] React frontend
- [ ] Deployment

## 👤 Author

**Your Name**
- GitHub: [@FidelAP-19](https://github.com/FidelAP-19)
- LinkedIn: [Fidel Perez](https://www.linkedin.com/in/fidel-perez-51288929b/)

## 📝 License

This project is for educational purposes.