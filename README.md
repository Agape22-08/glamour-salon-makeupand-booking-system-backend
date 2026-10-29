# Glamour Salon Makeup and Booking System

A Spring Boot REST API for managing beauty salon bookings, services, stylists, customers, products, and dashboard reporting.

## Student
- Mushambokazi Agape
- Student ID: 26847
- Course: INSY 8322 – Web Technology and Internet

## Project Overview
This project provides a backend system for a glamour salon business. It allows staff to manage bookings, beauty services, stylists, customers, product usage, and reporting information through REST API endpoints.

## Features
- Manage beauty services
- Manage stylists and stations
- Manage customers
- Create and update bookings
- Track booking status and cancellations
- View summary dashboard data
- Monitor product usage
- Generate status-based booking reports

## Tech Stack
- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven

## Project Structure
```text
src/
  main/
    java/
      glamour/
        salon/
          rw/
            controller/
            domain/
            repository/
            service/
    resources/
      application.properties
```

## Prerequisites
Before running the application, make sure you have:
- Java 21 installed
- Maven installed
- PostgreSQL installed and running
- A database named `glamour_salon`

## Database Setup
Create the database in PostgreSQL:
```sql
CREATE DATABASE glamour_salon;
```

Update your PostgreSQL username and password in:
```text
src/main/resources/application.properties
```

## Configuration
Example configuration:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/glamour_salon
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

## Run the Application
From the project root, run:
```bash
mvn spring-boot:run
```

The application will start on:
```text
http://localhost:8080
```

## Main API Endpoints
### Beauty Services
- `POST /api/beauty-services/save`
- `GET /api/beauty-services/all`
- `GET /api/beauty-services/{id}`
- `PUT /api/beauty-services/update/{id}`
- `DELETE /api/beauty-services/delete/{id}`
- `GET /api/beauty-services/unused`

### Bookings
- `POST /api/bookings/save`
- `PATCH /api/bookings/{id}/status`
- `GET /api/bookings/stats/by-status`
- `PATCH /api/bookings/cancel-day`
- `GET /api/dashboard/summary`

## Notes
This project is a backend API for salon booking operations and business reporting. It is not an AI-based application; it is a standard Java Spring Boot system built for managing salon data.

## GitHub Submission Guide
1. Create a new repository on GitHub.
2. Push your project files to GitHub.
3. Include this README in the repository root.
4. Add a short project description in the repository settings.

## Basic Git Commands
```bash
git init
git add .
git commit -m "Initial project setup"
git branch -M main
git remote add origin <your-github-repository-url>
git push -u origin main
```

If the repository is already initialized, you can skip `git init` and just use:
```bash
git add .
git commit -m "Project completed"
git push
```

## License
This project is for academic use and coursework purposes.
