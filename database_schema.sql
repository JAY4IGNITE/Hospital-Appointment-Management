CREATE DATABASE IF NOT EXISTS hospital_mgmt_db;
USE hospital_mgmt_db;
-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20)
);

-- Doctors Table
CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(20) UNIQUE,
    name VARCHAR(100),
    specialization VARCHAR(50),
    hospital_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

-- Patients Table
CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id VARCHAR(20) UNIQUE,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    age INT,
    gender VARCHAR(10),
    address TEXT
);

-- Appointments Table
CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id VARCHAR(20) UNIQUE,
    patient_id VARCHAR(20),
    doctor_id VARCHAR(20),
    doctor_name VARCHAR(100),
    patient_name VARCHAR(100),
    appointment_date DATE,
    appointment_time VARCHAR(20),
    symptoms TEXT,
    status VARCHAR(20)
);
