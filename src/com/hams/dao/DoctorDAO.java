package com.hams.dao;

import com.hams.model.Doctor;
import com.hams.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public static List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getString("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("hospital_name"),
                        rs.getString("email"),
                        "PROTECTED"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // Additional methods if needed for managing doctors (add/delete)

    public static boolean addDoctor(Doctor doctor) {
        String insertUser = "INSERT INTO users (username, password, role, email) VALUES (?, ?, 'DOCTOR', ?)";
        String insertDoctor = "INSERT INTO doctors (doctor_id, name, specialization, hospital_name, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement uStmt = conn.prepareStatement(insertUser);
                    PreparedStatement dStmt = conn.prepareStatement(insertDoctor)) {

                // User table
                uStmt.setString(1, doctor.getName());
                uStmt.setString(2, doctor.getPassword());
                uStmt.setString(3, doctor.getEmail());
                uStmt.executeUpdate();

                // Doctor table
                dStmt.setString(1, doctor.getDoctorId());
                dStmt.setString(2, doctor.getName());
                dStmt.setString(3, doctor.getSpecialization());
                dStmt.setString(4, doctor.getHospital());
                dStmt.setString(5, doctor.getEmail());
                dStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDoctor(String doctorId) {
        String selectEmail = "SELECT email FROM doctors WHERE doctor_id = ?";
        String deleteDoctor = "DELETE FROM doctors WHERE doctor_id = ?";
        String deleteUser = "DELETE FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Transaction

            String email = null;
            try (PreparedStatement selStmt = conn.prepareStatement(selectEmail)) {
                selStmt.setString(1, doctorId);
                ResultSet rs = selStmt.executeQuery();
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }

            if (email != null) {
                try (PreparedStatement delDocStmt = conn.prepareStatement(deleteDoctor);
                        PreparedStatement delUserStmt = conn.prepareStatement(deleteUser)) {

                    // Delete from Doctors first (FK constraint usually on doctor referencing user?
                    // No, likely standalone or user table is parent.
                    // If doctors email is FK to users, we delete doctor first.

                    delDocStmt.setString(1, doctorId);
                    delDocStmt.executeUpdate();

                    delUserStmt.setString(1, email);
                    delUserStmt.executeUpdate();

                    conn.commit();
                    return true;
                } catch (SQLException e) {
                    conn.rollback();
                    e.printStackTrace();
                }
            } else {
                // Doctor not found
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getDoctorCount() {
        String sql = "SELECT COUNT(*) FROM doctors";
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

    public static Doctor getDoctorById(String doctorId) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
