package com.hams.main;

import java.util.Scanner;
import com.hams.view.LoginFrame;
import com.hams.util.DBConnection;
import com.hams.util.DataSeeder;

public class Main {
    public static void main(String[] args) {
        DBConnection.initializeDatabase();
        DataSeeder.seed();
        System.out.println("Do you want to run in GUI or CLI mode? (G/C): ");
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine().trim().toUpperCase();
        if ("C".equals(mode) || "CLI".equals(mode)) {
            new com.hams.cli.CLIApp().start();
        } else {
            new LoginFrame();
        }
        scanner.close();
    }
}