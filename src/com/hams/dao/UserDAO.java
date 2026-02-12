package com.hams.dao;

import com.hams.model.User;
import com.hams.model.Patient;
import com.hams.model.Doctor;

import com.hams.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // 🔐 Login authentication
    public static User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                // String name = rs.getString("username");
                // String id = String.valueOf(rs.getInt("id"));

                // We need to return specific User subclasses (Patient or Doctor)
                // But the users table might not have all details.
                // A better approach is to query the specific table based on email if needed,
                // or just return a generic User if the app allows.
                // However, the app casts User to Patient/Doctor in Dashboards probably.

                if ("PATIENT".equalsIgnoreCase(role)) {
                    // Fetch full patient details
                    return getPatientByEmail(email);
                } else if ("DOCTOR".equalsIgnoreCase(role)) {
                    // Fetch full doctor details
                    return getDoctorByEmail(email);
                } else if ("ADMIN".equalsIgnoreCase(role)) {
                    return new User(email, password, "ADMIN");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }

    private static Patient getPatientByEmail(String email) {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Construct Patient object
                // Patient(String patientId, String name, String email, String password)
                return new Patient(
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        "PROTECTED" // Don't expose password easily or fetch from users table if needed
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Doctor getDoctorByEmail(String email) {
        String sql = "SELECT * FROM doctors WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Doctor(String doctorId, String name, String specialization, String hospital,
                // String email, String password)
                return new Doctor(
                        rs.getString("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("hospital_name"),
                        rs.getString("email"),
                        "PROTECTED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
