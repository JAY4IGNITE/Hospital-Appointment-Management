package com.hams.cli;

import com.hams.dao.AppointmentDAO;
import com.hams.dao.DoctorDAO;
import com.hams.dao.PatientDAO;
import com.hams.model.Doctor;

public class AdminCLI {

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Admin Dashboard ===");
            System.out.println("1. View Statistics");
            System.out.println("2. Add Doctor");
            System.out.println("3. View All Doctors");
            System.out.println("4. View All Patients");
            System.out.println("5. Delete Doctor");
            System.out.println("6. Delete Patient");
            System.out.println("7. Logout");

            int choice = InputHelper.readInt("Choice");

            switch (choice) {
                case 1:
                    viewStats();
                    break;
                case 2:
                    addDoctor();
                    break;
                case 3:
                    viewDoctors();
                    break;
                case 4:
                    viewPatients();
                    break;
                case 5:
                    deleteDoctor();
                    break;
                case 6:
                    deletePatient();
                    break;
                case 7:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewStats() {
        System.out.println("\n--- Statistics ---");
        System.out.println("Total Doctors: " + DoctorDAO.getDoctorCount());
        System.out.println("Total Patients: " + PatientDAO.getPatientCount());
        System.out.println("Appointments Today: " + AppointmentDAO.getTodayAppointmentCount());
    }

    private void addDoctor() {
        System.out.println("\n--- Add Doctor ---");
        String name = InputHelper.readString("Name");
        String spec = InputHelper.readString("Specialization");
        String hosp = InputHelper.readString("Hospital");
        String email = InputHelper.readString("Email");
        String pass = InputHelper.readString("Password");

        // Generate ID simply or ask for it
        String id = "D" + System.currentTimeMillis() % 1000;

        // Constructor: id, name, specialization, hospital, email, password
        Doctor doc = new Doctor(id, name, spec, hosp, email, pass);
        if (DoctorDAO.addDoctor(doc)) {
            System.out.println("Doctor added successfully!");
        } else {
            System.out.println("Failed to add doctor.");
        }
    }

    private void viewDoctors() {
        System.out.println("\n--- All Doctors ---");
        for (Doctor d : DoctorDAO.getAllDoctors()) {
            System.out.println(d.getDoctorId() + " | " + d.getName() + " | " + d.getSpecialization());
        }
    }

    private void viewPatients() {
        System.out.println("\n--- All Patients ---");
        try {
            for (com.hams.model.Patient p : PatientDAO.getAllPatients()) {
                System.out.println(p.getPatientId() + " | " + p.getName() + " | " + p.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Error fetching patients: " + e.getMessage());
        }
    }

    private void deleteDoctor() {
        System.out.println("\n--- Delete Doctor ---");
        viewDoctors(); // Show list before asking for ID
        String id = InputHelper.readString("Enter Doctor ID to delete");

        System.out.println("Are you sure? (Y/N)");
        String confirm = InputHelper.readString("Confirm");

        if ("Y".equalsIgnoreCase(confirm)) {
            if (DoctorDAO.deleteDoctor(id)) {
                System.out.println("Doctor deleted successfully.");
            } else {
                System.out.println("Failed to delete doctor (ID not found or error).");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void deletePatient() {
        System.out.println("\n--- Delete Patient ---");
        viewPatients(); // Show list before asking for ID
        String id = InputHelper.readString("Enter Patient ID to delete");

        System.out.println("Are you sure? (Y/N)");
        String confirm = InputHelper.readString("Confirm");

        if ("Y".equalsIgnoreCase(confirm)) {
            if (PatientDAO.deletePatient(id)) {
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("Failed to delete patient (ID not found or error).");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
