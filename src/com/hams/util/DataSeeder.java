package com.hams.util;

import com.hams.dao.AppointmentDAO;
import com.hams.dao.DoctorDAO;
import com.hams.dao.PatientDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.model.Patient;

public class DataSeeder {

    public static void seed() {
        System.out.println("Resetting & Seeding database...");

        // 1. Clear Old Data
        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            
            // Delete all records from all tables to reset testing state safely
            stmt.executeUpdate("DELETE FROM appointments");
            stmt.executeUpdate("DELETE FROM doctors");
            stmt.executeUpdate("DELETE FROM patients");
            stmt.executeUpdate("DELETE FROM users");
            
            System.out.println("Old data cleared successfully.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Setup Baseline
        seedAdmin();

        // 3. Seed Realistic Patients
        Patient p1 = new Patient("P-1001", "Emma Watson", "emma@gmail.com", "password123");
        Patient p2 = new Patient("P-1002", "Robert Downey", "robert@gmail.com", "password123");
        Patient p3 = new Patient("P-1003", "Chris Evans", "chris@gmail.com", "password123");
        
        PatientDAO.addPatient(p1);
        PatientDAO.addPatient(p2);
        PatientDAO.addPatient(p3);

        // 4. Seed Realistic Doctors
        Doctor d1 = new Doctor("D-201", "Dr. Gregory House", "Diagnostician", "Princeton-Plainsboro", "house@hospital.com", "password123");
        Doctor d2 = new Doctor("D-202", "Dr. Stephen Strange", "Neurology", "Metro General", "strange@hospital.com", "password123");
        Doctor d3 = new Doctor("D-203", "Dr. Shaun Murphy", "Surgery", "San Jose St. Bonaventure", "shaun@hospital.com", "password123");
        Doctor d4 = new Doctor("D-204", "Dr. Meredith Grey", "General Surgery", "Grey Sloan Memorial", "meredith@hospital.com", "password123");

        DoctorDAO.addDoctor(d1);
        DoctorDAO.addDoctor(d2);
        DoctorDAO.addDoctor(d3);
        DoctorDAO.addDoctor(d4);

        // 5. Seed Test Appointments
        java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);
        java.time.LocalDate today = java.time.LocalDate.now();

        Appointment a1 = new Appointment(0, "P-1001", "Emma Watson", "D-202", "Dr. Stephen Strange", tomorrow.toString(), "10:30 AM", "Severe Migraine", "SCHEDULED");
        Appointment a2 = new Appointment(0, "P-1002", "Robert Downey", "D-201", "Dr. Gregory House", today.toString(), "09:00 AM", "Unexplained joint pain", "SCHEDULED");
        Appointment a3 = new Appointment(0, "P-1003", "Chris Evans", "D-203", "Dr. Shaun Murphy", tomorrow.toString(), "02:00 PM", "Pre-op checkup", "SCHEDULED");

        AppointmentDAO.addAppointment(a1);
        AppointmentDAO.addAppointment(a2);
        AppointmentDAO.addAppointment(a3);

        System.out.println("Database seeding completed with realistic test variables.");
    }

    private static void seedAdmin() {
        String insertAdmin = "INSERT INTO users (username, password, role, email) VALUES ('admin', 'admin123', 'ADMIN', 'admin@hams.com')";

        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertAdmin)) {
            insertStmt.executeUpdate();
            System.out.println("Admin user created.");
        } catch (Exception e) {
            System.out.println("Admin possibly exists or error inserting: " + e.getMessage());
        }
    }
}
