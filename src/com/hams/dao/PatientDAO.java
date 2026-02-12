package com.hams.dao;

import com.hams.model.Patient;
import com.hams.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public static void addPatient(Patient patient) {
        String insertUser = "INSERT INTO users (username, password, role, email) VALUES (?, ?, 'PATIENT', ?)";
        String insertPatient = "INSERT INTO patients (patient_id, name, email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Transaction

            try (PreparedStatement uStmt = conn.prepareStatement(insertUser);
                    PreparedStatement pStmt = conn.prepareStatement(insertPatient)) {

                // Insert into users
                uStmt.setString(1, patient.getName());
                uStmt.setString(2, patient.getPassword()); // In real app, hash this
                uStmt.setString(3, patient.getEmail());
                uStmt.executeUpdate();

                // Insert into patients
                pStmt.setString(1, patient.getPatientId());
                pStmt.setString(2, patient.getName());
                pStmt.setString(3, patient.getEmail());
                pStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        "PROTECTED"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static void updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name = ?, email = ? WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getEmail());
            pstmt.setString(3, patient.getPatientId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean deletePatient(String patientId) {
        String selectEmail = "SELECT email FROM patients WHERE patient_id = ?";
        String deletePatient = "DELETE FROM patients WHERE patient_id = ?";
        String deleteUser = "DELETE FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String email = null;
            try (PreparedStatement selStmt = conn.prepareStatement(selectEmail)) {
                selStmt.setString(1, patientId);
                ResultSet rs = selStmt.executeQuery();
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }

            if (email != null) {
                try (PreparedStatement delPatStmt = conn.prepareStatement(deletePatient);
                        PreparedStatement delUserStmt = conn.prepareStatement(deleteUser)) {

                    delPatStmt.setString(1, patientId);
                    delPatStmt.executeUpdate();

                    delUserStmt.setString(1, email);
                    delUserStmt.executeUpdate();

                    conn.commit();
                    return true;
                }
            } else {
                // Patient not found
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getPatientCount() {
        String sql = "SELECT COUNT(*) FROM patients";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
