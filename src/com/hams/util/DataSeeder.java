package com.hams.util;

import com.hams.dao.DoctorDAO;
import com.hams.dao.PatientDAO;
import com.hams.model.Doctor;
import com.hams.model.Patient;

public class DataSeeder {

    public static void seed() {
        // Check if we can login with default user, if not, seed.
        // Or better, check if doctor list is empty.

        if (!DoctorDAO.getAllDoctors().isEmpty()) {
            // Even if doctors exist, ensure admin exists
            seedAdmin();
            System.out.println("Database already seeded.");
            return;
        }

        System.out.println("Seeding database...");
        seedAdmin();

        // Seed Patients
        Patient p1 = new Patient("P101", "Ravi", "patient@gmail.com", "1234");
        PatientDAO.addPatient(p1);

        // Seed Doctors
        Doctor d1 = new Doctor("D201", "Dr. Kumar", "Cardiology", "Apollo Hospital", "doctor@gmail.com", "1234");
        DoctorDAO.addDoctor(d1);

        Doctor d2 = new Doctor("D101", "Dr. John Smith", "Cardiology", "City Hospital", "john@gmail.com", "1234");
        DoctorDAO.addDoctor(d2);

        Doctor d3 = new Doctor("D102", "Dr. Emily Brown", "Neurology", "Apollo Hospital", "emily@gmail.com", "1234");
        DoctorDAO.addDoctor(d3);

        System.out.println("Database seeding completed.");
    }

    private static void seedAdmin() {
        String checkAdmin = "SELECT * FROM users WHERE role = 'ADMIN'";
        String insertAdmin = "INSERT INTO users (username, password, role, email) VALUES ('admin', 'admin123', 'ADMIN', 'admin@hams.com')";

        try (java.sql.Connection conn = DBConnection.getConnection();
                java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkAdmin);
                java.sql.ResultSet rs = checkStmt.executeQuery()) {

            if (!rs.next()) {
                try (java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertAdmin)) {
                    insertStmt.executeUpdate();
                    System.out.println("Admin user created.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
