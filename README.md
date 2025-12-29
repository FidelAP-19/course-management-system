# 🎓 Course Management System

A full-stack web application for managing students in an educational institution, built with Spring Boot, React, and PostgreSQL. Features complete CRUD operations, live search functionality, and production deployment on Railway.

[![Live Demo](https://img.shields.io/badge/demo-live-success)](https://profound-embrace-production.up.railway.app)
[![Backend API](https://img.shields.io/badge/API-live-blue)](https://course-management-system-production-5be2.up.railway.app/api/students)

## 🌟 Features

- ✅ **Student Management**: Create, read, and delete student records
- 🔍 **Live Search**: Real-time filtering of student list
- ✔️ **Form Validation**: Client and server-side validation
- 🎓 **Graduate Status**: Track graduate vs undergraduate students
- 🔄 **Responsive UI**: Clean, user-friendly interface
- 🚀 **Production Ready**: Deployed with Docker on Railway
- 🔒 **Secure**: CORS configured for cross-origin security

## 🖥️ Live Demo

**Frontend:** https://profound-embrace-production.up.railway.app  
**Backend API:** https://course-management-system-production-5be2.up.railway.app/api/students

Try adding, searching, and deleting students!

## 🛠️ Tech Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.2** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **PostgreSQL** - Production database
- **Maven** - Dependency management
- **Bean Validation** - Input validation

### Frontend
- **React 18** - UI library
- **JavaScript (ES6+)** - Programming language
- **Fetch API** - HTTP requests
- **CSS3** - Styling

### DevOps & Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **nginx** - Web server and reverse proxy
- **Railway** - Cloud platform
- **Git/GitHub** - Version control & CI/CD

## 📐 Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                         Internet                             │
└──────────────────────────┬──────────────────────────────────┘
                           │
                  ┌────────▼────────┐
                  │  Railway Cloud  │
                  └────────┬────────┘
         ┌────────────────┼────────────────┐
         │                │                │
    ┌────▼─────┐    ┌────▼─────┐    ┌────▼─────┐
    │ Frontend │    │ Backend  │    │ Database │
    │  (nginx) │───▶│  (Boot)  │───▶│(Postgres)│
    │  + React │    │  + API   │    │  + Vol   │
    └──────────┘    └──────────┘    └──────────┘
         80              8080            5432
```

### Data Flow
1. User interacts with React frontend (port 3000 → 80 in production)
2. Frontend makes HTTP requests to REST API
3. nginx proxies `/api/*` requests to backend (port 8080)
4. Spring Boot processes requests via service layer
5. JPA/Hibernate translates to SQL queries
6. PostgreSQL stores/retrieves data from persistent volume
7. Response flows back through the stack to user

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Node.js 18 or higher
- Docker & Docker Compose
- Git

### Local Development with Docker Compose (Recommended)

1. **Clone the repository**
```bash
   git clone https://github.com/YOUR_USERNAME/course-management-system.git
   cd course-management-system
```

2. **Start all services**
```bash
   docker-compose up
```

3. **Access the application**
  - Frontend: http://localhost:3000
  - Backend API: http://localhost:8080/api/students
  - Database: localhost:5432 (postgres/password)

4. **Stop all services**
```bash
   docker-compose down
```

### Local Development without Docker

#### Backend Setup

1. **Configure database**

   Create PostgreSQL database:
```sql
   CREATE DATABASE coursedb;
```

2. **Update `application.properties`** (if needed)
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/coursedb
   spring.datasource.username=postgres
   spring.datasource.password=password
```

3. **Run backend**
```bash
   mvn spring-boot:run
```
Backend will start on http://localhost:8080

#### Frontend Setup

1. **Install dependencies**
```bash
   cd frontend
   npm install
```

2. **Start development server**
```bash
   npm start
```
Frontend will start on http://localhost:3000

## 📡 API Endpoints

### Student Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Retrieve all students |
| POST | `/api/students` | Create a new student |
| DELETE | `/api/students/{id}` | Delete a student by ID |

### Request/Response Examples

**Create Student:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "birthYear": 2000,
    "major": "Computer Science",
    "isGraduate": true
  }'
```

**Response:**
```json
{
  "studentID": 1,
  "name": "John Doe",
  "birthYear": 2000,
  "major": "Computer Science",
  "graduate": true
}
```

## 🐳 Docker

### Multi-Stage Dockerfiles

**Backend Dockerfile:**
- Stage 1: Maven build (creates JAR)
- Stage 2: JRE runtime (minimal image)
- Final size: ~250MB

**Frontend Dockerfile:**
- Stage 1: Node.js build (npm run build)
- Stage 2: nginx serve (static files only)
- Final size: ~23MB (40x smaller than single-stage!)

### Building Images Manually
```bash
# Backend
docker build -t course-backend:1.0 .

# Frontend
cd frontend
docker build -t course-frontend:1.0 .
```

### Docker Compose Services
```yaml
services:
  database:  # PostgreSQL 16 with health checks
  backend:   # Spring Boot with environment variables
  frontend:  # React with nginx proxy
```

## 🔐 Security Features

- **CORS Configuration**: Restricts API access to specific domains
- **Input Validation**: Bean Validation on backend (@NotBlank, @Min, @Max)
- **SQL Injection Prevention**: JPA/Hibernate parameterized queries
- **Environment Variables**: Sensitive data not hardcoded

## 🧪 Testing

### Manual Testing Checklist

- [ ] Create student with valid data
- [ ] Create student with invalid data (validation errors)
- [ ] Search/filter students by name
- [ ] Delete student
- [ ] Refresh page (data persists)
- [ ] Test with empty database

### API Testing with curl
```bash
# Get all students
curl http://localhost:8080/api/students

# Create student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","birthYear":2000,"major":"Testing","isGraduate":false}'

# Delete student (replace {id})
curl -X DELETE http://localhost:8080/api/students/{id}
```

## 📁 Project Structure
```
course-management-system/
├── src/
│   └── main/
│       ├── java/com/example/coursemanagement/
│       │   ├── controller/
│       │   │   └── StudentController.java      # REST endpoints
│       │   ├── service/
│       │   │   └── StudentService.java         # Business logic
│       │   ├── repository/
│       │   │   └── StudentRepository.java      # Data access
│       │   ├── model/
│       │   │   ├── Person.java                 # Base entity
│       │   │   └── Student.java                # Student entity
│       │   ├── config/
│       │   │   └── CorsConfig.java             # CORS configuration
│       │   └── CourseManagementApplication.java
│       └── resources/
│           └── application.properties          # Configuration
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── App.js                              # Main React component
│   │   ├── App.css                             # Styles
│   │   └── index.js                            # Entry point
│   ├── .env.development                        # Local API URL
│   ├── .env.production                         # Production API URL
│   ├── Dockerfile                              # Frontend container
│   ├── nginx.conf                              # nginx configuration
│   └── package.json
├── Dockerfile                                  # Backend container
├── docker-compose.yml                          # Multi-service orchestration
├── pom.xml                                     # Maven configuration
└── README.md
```

## 🌱 Environment Variables

### Backend (Spring Boot)
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/coursedb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
```

### Frontend (React)
```bash
# .env.development
REACT_APP_API_URL=http://localhost:8080

# .env.production
REACT_APP_API_URL=https://course-management-system-production-5be2.up.railway.app
```

## 🚢 Deployment

### Railway Deployment (Current)

**Automatic deployment on push to main branch**

1. **Database**: PostgreSQL service with persistent volume
2. **Backend**: Dockerfile build with environment variables
3. **Frontend**: Multi-stage build with nginx

### Manual Deployment Steps

1. **Push to GitHub**
```bash
   git push origin main
```

2. **Railway Auto-Deploys**
  - Backend builds from root Dockerfile
  - Frontend builds from `frontend/Dockerfile`
  - Database provisions automatically

3. **Verify Deployment**
  - Check Railway dashboard for "Deployment successful"
  - Visit frontend URL
  - Test API endpoints

## 🔧 Configuration

### CORS Configuration

Current setup allows requests from:
- `https://profound-embrace-production.up.railway.app` (production)
- `http://localhost:3000` (development)

To add more origins, update `StudentController.java`:
```java
@CrossOrigin(origins = {
    "https://your-frontend.com",
    "http://localhost:3000"
})
```

### Database Configuration

For different database providers, update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://HOST:PORT/DATABASE
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
```

## 🐛 Troubleshooting

### Common Issues

**CORS Error:**
- Check `@CrossOrigin` annotation includes your domain
- Verify frontend URL matches exactly (https vs http)

**Database Connection Failed:**
- Ensure PostgreSQL is running
- Check connection string in environment variables
- Verify database credentials

**Docker Build Fails:**
- Clear Docker cache: `docker system prune -a`
- Check `target/` folder has JAR file (run `mvn package`)
- Verify `nginx.conf` exists in `frontend/` directory

**Frontend Shows Stale Data:**
- Hard refresh browser (Cmd/Ctrl + Shift + R)
- Check browser console for API errors
- Verify backend is running and accessible

## 📚 Learning Outcomes

This project demonstrates:
- ✅ RESTful API design and implementation
- ✅ React component architecture with hooks
- ✅ Docker containerization and optimization
- ✅ Database design and JPA relationships
- ✅ Cloud deployment and CI/CD
- ✅ CORS security configuration
- ✅ Environment-based configuration
- ✅ Full-stack integration

## 🔮 Future Enhancements

- [ ] Add Faculty management (CRUD for faculty members)
- [ ] Add Course management (CRUD for courses)
- [ ] Implement student-course enrollment (many-to-many relationship)
- [ ] Add user authentication (Spring Security + JWT)
- [ ] Implement pagination for large datasets
- [ ] Add sorting functionality
- [ ] Create comprehensive test suite (JUnit, Jest)
- [ ] Add edit/update functionality for students
- [ ] Implement role-based access control
- [ ] Add API documentation (Swagger/OpenAPI)

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Your Name**
- GitHub: [@FidelAP-19](https://github.com/FidelAP-19)
- LinkedIn: [Fidel Perez](https://www.linkedin.com/in/fidel-perez-51288929b/)

## 🙏 Acknowledgments

- Built as part of a software engineering mentorship program
- Transformed from a console-based school project to production-ready application
- Special thanks to the Spring Boot and React communities for excellent documentation

---

⭐ **Star this repo if you found it helpful!**

📧 **Questions?** Open an issue or reach out!