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

        // 4. Seed Doctors across multiple specialization categories

        // --- Diagnostics ---
        Doctor d1  = new Doctor("D-201", "Dr. Gregory House",     "Diagnostics",    "Princeton-Plainsboro Hospital",    "house@hospital.com",      "password123");

        // --- Neurology ---
        Doctor d2  = new Doctor("D-202", "Dr. Stephen Strange",   "Neurology",      "Metro General Hospital",           "strange@hospital.com",    "password123");
        Doctor d3  = new Doctor("D-203", "Dr. Amelia Shepherd",   "Neurology",      "Grey Sloan Memorial",              "amelia@hospital.com",     "password123");

        // --- Surgery ---
        Doctor d4  = new Doctor("D-204", "Dr. Shaun Murphy",      "Surgery",        "San Jose St. Bonaventure",         "shaun@hospital.com",      "password123");
        Doctor d5  = new Doctor("D-205", "Dr. Meredith Grey",     "General Surgery","Grey Sloan Memorial",              "meredith@hospital.com",   "password123");

        // --- Cardiology ---
        Doctor d6  = new Doctor("D-206", "Dr. Cristina Yang",     "Cardiology",     "Grey Sloan Memorial",              "cristina@hospital.com",   "password123");
        Doctor d7  = new Doctor("D-207", "Dr. Rajesh Kapoor",     "Cardiology",     "Apollo Heart Institute",           "rajesh.k@apollo.com",     "password123");

        // --- Orthopedics ---
        Doctor d8  = new Doctor("D-208", "Dr. Lawrence Kutner",   "Orthopedics",    "Princeton-Plainsboro Hospital",    "kutner@hospital.com",     "password123");
        Doctor d9  = new Doctor("D-209", "Dr. Priya Sharma",      "Orthopedics",    "Fortis Bone & Joint Center",      "priya.s@fortis.com",      "password123");

        // --- Pediatrics ---
        Doctor d10 = new Doctor("D-210", "Dr. Miranda Bailey",    "Pediatrics",     "Grey Sloan Memorial",              "bailey@hospital.com",     "password123");
        Doctor d11 = new Doctor("D-211", "Dr. Ananya Menon",      "Pediatrics",     "Rainbow Children's Hospital",      "ananya.m@rainbow.com",    "password123");

        // --- Dermatology ---
        Doctor d12 = new Doctor("D-212", "Dr. Allison Cameron",   "Dermatology",    "Princeton-Plainsboro Hospital",    "cameron@hospital.com",    "password123");
        Doctor d13 = new Doctor("D-213", "Dr. Kavya Nair",        "Dermatology",    "Skin & Hair Clinic",               "kavya.n@skinclinic.com",  "password123");

        // --- Oncology ---
        Doctor d14 = new Doctor("D-214", "Dr. Owen Hunt",         "Oncology",       "Grey Sloan Memorial",              "owen@hospital.com",       "password123");
        Doctor d15 = new Doctor("D-215", "Dr. Arjun Mehta",       "Oncology",       "Tata Cancer Care Center",          "arjun.m@tatacancer.com",  "password123");

        // --- Psychiatry ---
        Doctor d16 = new Doctor("D-216", "Dr. Sean Murphy",       "Psychiatry",     "Westview Behavioral Health",       "sean.m@westview.com",     "password123");
        Doctor d17 = new Doctor("D-217", "Dr. Neha Joshi",        "Psychiatry",     "Mind Matters Clinic",              "neha.j@mindmatters.com",  "password123");

        // --- Endocrinology ---
        Doctor d18 = new Doctor("D-218", "Dr. Vikram Patel",      "Endocrinology",  "Diabetes & Hormone Care",          "vikram.p@endocare.com",   "password123");
        Doctor d19 = new Doctor("D-219", "Dr. Emily Foreman",     "Endocrinology",  "Princeton-Plainsboro Hospital",    "foreman@hospital.com",    "password123");

        // --- ENT ---
        Doctor d20 = new Doctor("D-220", "Dr. Rohan Desai",       "ENT",            "ENT Specialty Hospital",           "rohan.d@ent.com",         "password123");

        DoctorDAO.addDoctor(d1);  DoctorDAO.addDoctor(d2);  DoctorDAO.addDoctor(d3);
        DoctorDAO.addDoctor(d4);  DoctorDAO.addDoctor(d5);  DoctorDAO.addDoctor(d6);
        DoctorDAO.addDoctor(d7);  DoctorDAO.addDoctor(d8);  DoctorDAO.addDoctor(d9);
        DoctorDAO.addDoctor(d10); DoctorDAO.addDoctor(d11); DoctorDAO.addDoctor(d12);
        DoctorDAO.addDoctor(d13); DoctorDAO.addDoctor(d14); DoctorDAO.addDoctor(d15);
        DoctorDAO.addDoctor(d16); DoctorDAO.addDoctor(d17); DoctorDAO.addDoctor(d18);
        DoctorDAO.addDoctor(d19); DoctorDAO.addDoctor(d20);

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
