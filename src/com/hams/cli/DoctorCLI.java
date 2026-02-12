package com.hams.cli;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.util.SessionManager;
import java.util.List;

public class DoctorCLI {

    public void showMenu() {
        Doctor doctor = (Doctor) SessionManager.getUser();

        while (true) {
            System.out.println("\n=== Doctor Dashboard (" + doctor.getName() + ") ===");
            System.out.println("1. View Appointments");
            System.out.println("2. Update Appointment Status");
            System.out.println("3. Logout");

            int choice = InputHelper.readInt("Choice");

            switch (choice) {
                case 1:
                    viewAppointments(doctor);
                    break;
                case 2:
                    updateStatus(doctor);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAppointments(Doctor doctor) {
        System.out.println("\n--- Appointments ---");
        List<Appointment> apps = AppointmentDAO.getAppointmentsByDoctor(doctor.getName());
        if (apps.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.printf("%-5s %-15s %-12s %-20s %-10s%n", "ID", "Patient", "Date", "Slot", "Status");
            System.out.println("---------------------------------------------------------------");
            for (Appointment a : apps) {
                System.out.printf("%-5d %-15s %-12s %-20s %-10s%n",
                        a.getId(), a.getPatientName(), a.getDate(), a.getTimeSlot(), a.getStatus());
            }
        }
    }

    private void updateStatus(Doctor doctor) {
        int id = InputHelper.readInt("Enter Appointment ID to update");
        System.out.println("1. Mark COMPLETED");
        System.out.println("2. Mark MISSED");
        int statusChoice = InputHelper.readInt("Choose status");

        String status = null;
        if (statusChoice == 1)
            status = "COMPLETED";
        else if (statusChoice == 2)
            status = "MISSED";

        if (status != null) {
            AppointmentDAO.updateStatus(id, status);
            System.out.println("Status updated.");
        } else {
            System.out.println("Invalid status choice.");
        }
    }
}
