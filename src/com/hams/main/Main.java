package com.hams.main;

import java.util.Scanner;
import com.hams.view.LoginFrame;
import com.hams.util.DBConnection;


public class Main {
    public static void main(String[] args) {
        DBConnection.initializeDatabase();
        // DataSeeder.seed(); // Uncomment if you want to wipe and re-seed the DB on startup
        String mode;
        if (args.length > 0) {
            mode = args[0].trim().toUpperCase();
        } else {
            System.out.println("Do you want to run in GUI or CLI mode? (G/C): ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                mode = scanner.nextLine().trim().toUpperCase();
            } else {
                mode = "G"; // Default to GUI if no input
            }
        }

        if ("C".equals(mode) || "CLI".equals(mode)) {
            new com.hams.cli.CLIApp().start();
        } else {
            new LoginFrame();
        } 
    }
}