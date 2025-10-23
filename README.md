# Oop-project
Oop project description 
# Online Quiz and Exam System

A comprehensive Java-based online quiz system with Swing GUI and MySQL database.

## Features
- **Admin**: Manage subjects, quizzes, questions, view students and results
- **Student**: Register, take quizzes, view results
- **Database**: MySQL with JDBC connectivity

## Setup
1. Run `database/schema.sql` in MySQL
2. Update database credentials in `src/util/DatabaseConnection.java`
3. Compile: `javac -cp "lib/mysql-connector-java-8.0.x.jar" src/**/*.java`
4. Run: `java -cp "src:lib/mysql-connector-java-8.0.x.jar" Main`

## Default Logins
- Admin: admin@quiz.com / admin123
- Student: john@student.com / student123
