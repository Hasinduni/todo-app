# Todo App - Full Stack Application

A full-stack todo application built with Java Spring Boot, React, and PostgreSQL.

## Tech Stack

- **Backend**: Java 17 + Spring Boot 4
- **Frontend**: React + Vite
- **Database**: PostgreSQL 16
- **Containerization**: Docker + Docker Compose

## Project Structure
```
todo-app/
 ├── docker-compose.yml    
 ├── db/
 │    └── init.sql         
 ├── todo-api/             
 └── todo-frontend/        
```

## Running with Docker (Recommended)

### Prerequisites
- Docker Desktop

### Steps
```bash
git clone https://github.com/hasinduni/todo-app.git
cd todo-app
docker compose up --build
```

Open:
- Frontend → http://localhost:3000
- Backend API → http://localhost:4000/api/tasks
- Swagger UI → http://localhost:4000/swagger-ui.html

## Running Manually (Without Docker)

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL

### Database Setup
```sql
CREATE DATABASE todo_db;
```

### Backend
```bash
cd todo-api
./mvnw spring-boot:run
```
Runs on http://localhost:4000

### Frontend
```bash
cd todo-frontend
npm install
npm run dev
```
Runs on http://localhost:5173

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/tasks | Get 5 recent incomplete tasks |
| POST | /api/tasks | Create a new task |
| PATCH | /api/tasks/{id}/complete | Mark task as done |

## Running Tests

### Backend Tests
```bash
cd todo-api
./mvnw test
```

### Frontend Tests
```bash
cd todo-frontend
npm test
```