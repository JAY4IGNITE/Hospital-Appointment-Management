package com.hams.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_NAME = "hospital_mgmt_db";
    private static final String URL_NO_DB = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Default password, change as needed

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL_NO_DB + DB_NAME, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    // Method to initialize tables if they don't exist
    // Update to create Database separately
    public static void initializeDatabase() {
        // First create database if not exists
        try (Connection conn = DriverManager.getConnection(URL_NO_DB, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Create Users table (for login)
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50), " +
                    "password VARCHAR(50), " + // In a real app, hash this!
                    "role VARCHAR(20), " + // unique ID for doctor/patient
                    "email VARCHAR(100) UNIQUE, " +
                    "phone VARCHAR(20))";
            stmt.execute(createUsersTable);

            // Create Doctors table
            String createDoctorsTable = "CREATE TABLE IF NOT EXISTS doctors (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "doctor_id VARCHAR(20) UNIQUE, " +
                    "name VARCHAR(100), " +
                    "specialization VARCHAR(50), " +
                    "hospital_name VARCHAR(100), " +
                    "email VARCHAR(100), " +
                    "phone VARCHAR(20))";
            stmt.execute(createDoctorsTable);

            // Create Patients table
            String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "patient_id VARCHAR(20) UNIQUE, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100), " +
                    "phone VARCHAR(20), " +
                    "age INT, " +
                    "gender VARCHAR(10), " +
                    "address TEXT)";
            stmt.execute(createPatientsTable);

            // Create Appointments table
            String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "appointment_id VARCHAR(20) UNIQUE, " +
                    "patient_id VARCHAR(20), " +
                    "doctor_id VARCHAR(20), " +
                    "doctor_name VARCHAR(100), " +
                    "patient_name VARCHAR(100), " + // Redundant but useful for display
                    "appointment_date DATE, " +
                    "appointment_time VARCHAR(20), " +
                    "symptoms TEXT, " +
                    "status VARCHAR(20))";
            stmt.execute(createAppointmentsTable);

            // --- New Tables as per Request ---

            // Create patient Table
            String createPatientTable = "CREATE TABLE IF NOT EXISTS patient (" +
                    "patient_id INT PRIMARY KEY, " +
                    "patient_name VARCHAR(100) NOT NULL, " +
                    "age INT, " +
                    "phone VARCHAR(15), " +
                    "address VARCHAR(200))";
            stmt.execute(createPatientTable);

            // Create doctor Table
            String createDoctorTable = "CREATE TABLE IF NOT EXISTS doctor (" +
                    "doctor_id INT PRIMARY KEY, " +
                    "doctor_name VARCHAR(100) NOT NULL, " +
                    "specialization VARCHAR(50), " +
                    "phone VARCHAR(15))";
            stmt.execute(createDoctorTable);

            // Create appointment Table
            String createAppointmentTable = "CREATE TABLE IF NOT EXISTS appointment (" +
                    "appointment_id INT PRIMARY KEY, " +
                    "patient_id INT, " +
                    "doctor_id INT, " +
                    "appointment_date DATE NOT NULL, " +
                    "appointment_time VARCHAR(20) NOT NULL, " +
                    "FOREIGN KEY (patient_id) REFERENCES patient(patient_id), " +
                    "FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id))";
            stmt.execute(createAppointmentTable);

            System.out.println("Database tables initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
