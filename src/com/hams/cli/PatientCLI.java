package com.hams.cli;

import com.hams.dao.AppointmentDAO;
import com.hams.dao.DoctorDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import com.hams.util.SessionManager;
import java.util.List;

public class PatientCLI {

    public void showMenu() {
        Patient patient = (Patient) SessionManager.getUser();

        while (true) {
            System.out.println("\n=== Patient Dashboard (" + patient.getName() + ") ===");
            System.out.println("1. View My Appointments");
            System.out.println("2. Book Appointment");
            System.out.println("3. Search Doctors");
            System.out.println("4. Logout");

            int choice = InputHelper.readInt("Choice");

            switch (choice) {
                case 1:
                    viewMyAppointments(patient);
                    break;
                case 2:
                    bookAppointment(patient);
                    break;
                case 3:
                    searchDoctors();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewMyAppointments(Patient patient) {
        System.out.println("\n--- My Appointments ---");
        List<Appointment> all = AppointmentDAO.getAllAppointments();
        boolean found = false;
        System.out.printf("%-5s %-15s %-12s %-20s %-10s%n", "ID", "Doctor", "Date", "Slot", "Status");
        System.out.println("---------------------------------------------------------------");

        for (Appointment a : all) {
            if (a.getPatientName().equalsIgnoreCase(patient.getName())) {
                System.out.printf("%-5d %-15s %-12s %-20s %-10s%n",
                        a.getId(), a.getDoctorName(), a.getDate(), a.getTimeSlot(), a.getStatus());
                found = true;
            }
        }

        if (!found)
            System.out.println("No appointments found.");
    }

    private void bookAppointment(Patient patient) {
        System.out.println("\n--- Book Appointment ---");
        searchDoctors();

        String docId = InputHelper.readString("Enter Doctor ID");
        Doctor doctor = DoctorDAO.getDoctorById(docId);

        if (doctor == null) {
            System.out.println("Invalid Doctor ID.");
            return;
        }

        String docName = doctor.getName();
        String date = InputHelper.readString("Enter Date (YYYY-MM-DD)");

        System.out.println("Select Time Slot:");
        java.util.List<String> slots = com.hams.util.TimeSlots.getAll();
        for (int i = 0; i < slots.size(); i++) {
            System.out.println((i + 1) + ". " + slots.get(i));
        }
        int slotChoice = InputHelper.readInt("Enter Slot Number");
        String time = com.hams.util.TimeSlots.getSlot(slotChoice - 1);

        if (time == null) {
            System.out.println("Invalid slot selected.");
            return;
        }

        String symptoms = InputHelper.readString("Symptoms");

            if (AppointmentDAO.isTimeSlotAvailable(docName, date, time)) {
            Appointment app = new Appointment(patient.getName(), docName, date, time, symptoms);
            if (AppointmentDAO.addAppointment(app)) {
                System.out.println("Appointment booked successfully!");
            } else {
                System.out.println("Failed to book appointment.");
            }
        } else {
            System.out.println("Slot not available. Please try another time.");
        }
    }

    private void searchDoctors() {
        System.out.println("\n--- Available Doctors ---");
        for (Doctor d : DoctorDAO.getAllDoctors()) {
            System.out.println(
                    d.getDoctorId() + " | " + d.getName() + " (" + d.getSpecialization() + ") - " + d.getHospital());
        }
    }
}
