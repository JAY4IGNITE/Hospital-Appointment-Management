package com.hams.cli;

import com.hams.controller.LoginController;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import com.hams.model.User;
import com.hams.util.SessionManager;

public class CLIApp {

    public void start() {
        System.out.println("\n=== Welcome to Hospital Appointment Management System ===");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Sign Up (Patient)");
            System.out.println("3. Exit");

            int choice = InputHelper.readInt("Enter your choice");

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    registerPatient();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        String email = InputHelper.readString("Email");
        String password = InputHelper.readString("Password");

        User user = LoginController.login(email, password);

        if (user != null) {
            SessionManager.setUser(user);
            System.out.println("Login successful! Welcome " + user.getUsername());

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                new AdminCLI().showMenu();
            } else if (user instanceof Doctor) {
                new DoctorCLI().showMenu();
            } else if (user instanceof Patient) {
                new PatientCLI().showMenu();
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void registerPatient() {
        System.out.println("\n--- Patient Registration ---");
        System.out.println("Registration via CLI not fully implemented yet. Please use GUI or contact Admin.");
    }
}
