CREATE DATABASE IF NOT EXISTS online_quiz_system;
USE online_quiz_system;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'STUDENT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subjects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE quizzes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    time_limit INT DEFAULT 30,
    total_questions INT DEFAULT 10,
    is_active BOOLEAN DEFAULT TRUE,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_id) REFERENCES subjects(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL CHECK (correct_option IN ('A', 'B', 'C', 'D')),
    marks INT DEFAULT 1,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE results (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    quiz_id INT NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DECIMAL(5,2) NOT NULL,
    time_taken INT DEFAULT 0,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

INSERT INTO users (name, email, password, role) VALUES 
('Admin User', 'admin@quiz.com', 'admin123', 'ADMIN'),
('John Student', 'john@student.com', 'student123', 'STUDENT'),
('Jane Doe', 'jane@student.com', 'student123', 'STUDENT');

INSERT INTO subjects (name, description, created_by) VALUES 
('Java Programming', 'Object-oriented programming with Java', 1),
('Database Management', 'SQL and database concepts', 1),
('Data Structures', 'Algorithms and data structures', 1);

INSERT INTO quizzes (subject_id, title, description, time_limit, total_questions, created_by) VALUES 
(1, 'Java Basics', 'Fundamental Java concepts', 30, 5, 1),
(1, 'OOP in Java', 'Object Oriented Programming concepts', 45, 5, 1),
(2, 'SQL Fundamentals', 'Basic SQL queries and concepts', 30, 5, 1);

INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks) VALUES 
(1, 'What is the size of int in Java?', '2 bytes', '4 bytes', '8 bytes', 'Depends on system', 'B', 1),
(1, 'Which keyword is used for inheritance in Java?', 'implements', 'extends', 'inherits', 'uses', 'B', 1),
(1, 'What is the default value of boolean in Java?', 'true', 'false', 'null', '0', 'B', 1),
(1, 'Which collection maintains insertion order?', 'HashSet', 'TreeSet', 'LinkedHashSet', 'HashMap', 'C', 1),
(1, 'What is JVM?', 'Java Virtual Machine', 'Java Visual Machine', 'Java Variable Machine', 'Java Version Machine', 'A', 1);
