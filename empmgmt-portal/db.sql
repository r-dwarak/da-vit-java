CREATE DATABASE ems CHARACTER SET utf8mb4;
USE ems;

CREATE TABLE employees (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,   -- store a hash in real life; for demo you can store plain
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL,
  role ENUM('ADMIN','EMP') NOT NULL
);

-- Demo data (use a hash in production!)
INSERT INTO employees (username, password_hash, full_name, email, role) VALUES
('admin', 'admin123', 'Admin User', 'admin@company.com', 'ADMIN'),
('alice', 'alice123', 'Alice Johnson', 'alice@company.com', 'EMP'),
('bob',   'bob123',   'Bob Singh',     'bob@company.com',   'EMP');
