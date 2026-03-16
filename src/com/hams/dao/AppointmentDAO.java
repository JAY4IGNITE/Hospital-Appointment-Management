package com.hams.dao;

import com.hams.model.Appointment;
import com.hams.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public static boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (appointment_id, patient_id, patient_name, doctor_id, doctor_name, appointment_date, appointment_time, symptoms, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'SCHEDULED')";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Generate a simple unique ID
            String appointmentId = "APT" + System.currentTimeMillis() % 10000;

            pstmt.setString(1, appointmentId);
            pstmt.setString(2, appointment.getPatientId());
            pstmt.setString(3, appointment.getPatientName());
            pstmt.setString(4, appointment.getDoctorId());
            pstmt.setString(5, appointment.getDoctorName());
            pstmt.setString(6, appointment.getDate());
            pstmt.setString(7, appointment.getTimeSlot());
            pstmt.setString(8, appointment.getSymptoms());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding appointment: " + e.getMessage()); // Be explicit
            e.printStackTrace();
        }
        return false;
    }

    public static List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("symptoms"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static List<Appointment> getAppointmentsByDoctor(String doctorName) {
        List<Appointment> result = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_name = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctorName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("symptoms"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Search methods
    public static List<Appointment> searchAppointments(String criteria) {
        List<Appointment> result = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_name LIKE ? OR doctor_name LIKE ? OR appointment_date LIKE ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String pattern = "%" + criteria + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("symptoms"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, appointmentId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTodayAppointmentCount() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE appointment_date = CURDATE()";
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

    public static boolean isTimeSlotAvailable(String doctorName, String date, String timeSlot) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_name = ? AND appointment_date = ? AND appointment_time = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctorName);
            // Fix: date is string in DB
            pstmt.setString(2, date);
            pstmt.setString(3, timeSlot);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Fail safe
    }

    public static List<Appointment> getAppointmentsByPatient(String patientName) {
        List<Appointment> result = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_name = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patientName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("symptoms"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
