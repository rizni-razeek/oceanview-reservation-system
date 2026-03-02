# Ocean View Resort – Room Reservation System

A simple room reservation system developed as a Java web application using Servlets (no frameworks), vanilla HTML/CSS/JavaScript, and MySQL.

## Tech Stack
- Java + Servlets (Tomcat 9)
- MySQL (XAMPP)
- HTML / CSS / JavaScript (no front-end frameworks)

## How to run (local)
1. Start XAMPP: Apache + MySQL
2. Create database `oceanview` in phpMyAdmin (schema will be documented in the report)
3. Open the project in IntelliJ
4. Configure Tomcat 9 as the Application Server
5. Deploy `oceanview:war exploded`
6. Run and open: http://localhost:8080/oceanview/

## Health check
- http://localhost:8080/oceanview/api/health