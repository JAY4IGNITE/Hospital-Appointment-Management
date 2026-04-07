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

        // 3. Seed Realistic Indian Patients
        Patient p1 = new Patient("P-1001", "Arjun Sharma", "arjun.sharma@gmail.com", "password123");
        Patient p2 = new Patient("P-1002", "Priya Patel", "priya.patel@gmail.com", "password123");
        Patient p3 = new Patient("P-1003", "Rahul Verma", "rahul.verma@gmail.com", "password123");
        Patient p4 = new Patient("P-1004", "Sneha Reddy", "sneha.reddy@gmail.com", "password123");
        Patient p5 = new Patient("P-1005", "Vikram Singh", "vikram.singh@gmail.com", "password123");
        Patient p6 = new Patient("P-1006", "Anjali Nair", "anjali.nair@gmail.com", "password123");
        Patient p7 = new Patient("P-1007", "Rohit Kumar", "rohit.kumar@gmail.com", "password123");
        Patient p8 = new Patient("P-1008", "Deepika Mehta", "deepika.mehta@gmail.com", "password123");
        Patient p9 = new Patient("P-1009", "Kiran Joshi", "kiran.joshi@gmail.com", "password123");
        Patient p10 = new Patient("P-1010", "Suresh Iyer", "suresh.iyer@gmail.com", "password123");
        Patient p11 = new Patient("P-1011", "Meera Pillai", "meera.pillai@gmail.com", "password123");
        Patient p12 = new Patient("P-1012", "Aakash Gupta", "aakash.gupta@gmail.com", "password123");
        Patient p13 = new Patient("P-1013", "Divya Krishnan", "divya.k@gmail.com", "password123");
        Patient p14 = new Patient("P-1014", "Sandeep Rao", "sandeep.rao@gmail.com", "password123");
        Patient p15 = new Patient("P-1015", "Lakshmi Desai", "lakshmi.desai@gmail.com", "password123");
        
        PatientDAO.addPatient(p1); PatientDAO.addPatient(p2); PatientDAO.addPatient(p3);
        PatientDAO.addPatient(p4); PatientDAO.addPatient(p5); PatientDAO.addPatient(p6);
        PatientDAO.addPatient(p7); PatientDAO.addPatient(p8); PatientDAO.addPatient(p9);
        PatientDAO.addPatient(p10); PatientDAO.addPatient(p11); PatientDAO.addPatient(p12);
        PatientDAO.addPatient(p13); PatientDAO.addPatient(p14); PatientDAO.addPatient(p15);

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

        // 5. Seed 100 Appointments (5 per doctor)
        Doctor[] doctors = {d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20};
        Patient[] patients = {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15};
        String[] symptoms = {"Fever", "Cough", "Headache", "Body Ache", "Joint Pain", "Migraine", "Dizziness", "Fatigue", "Nausea", "Skin Rash"};
        String[] times = {"09:00 AM", "10:30 AM", "11:00 AM", "02:00 PM", "04:30 PM"};
        
        int aptId = 1;
        for (Doctor doc : doctors) {
            for (int i = 0; i < 5; i++) {
                Patient pat = patients[(aptId - 1) % patients.length];
                String date = java.time.LocalDate.now().plusDays((aptId % 10) + 1).toString();
                String time = times[i % times.length];
                String symptom = symptoms[aptId % symptoms.length];
                String status = (aptId % 3 == 0) ? "COMPLETED" : "SCHEDULED";
                
                Appointment apt = new Appointment(0, pat.getPatientId(), pat.getName(), 
                    doc.getDoctorId(), doc.getName(), date, time, symptom, status);
                apt.setAppointmentId("APT-" + String.format("%03d", aptId));
                AppointmentDAO.addAppointment(apt);
                aptId++;
            }
        }

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
