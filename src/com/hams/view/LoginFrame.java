package com.hams.view;

import com.hams.controller.LoginController;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import com.hams.model.User;
import com.hams.util.SessionManager;
import com.hams.util.Theme;

import javax.swing.*;
import java.awt.Cursor;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        Theme.setupFrame(this, "Hospital Management System - Login", 400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome Back", JLabel.CENTER);
        title.setBounds(0, 20, 400, 30);
        Theme.styleHeader(title);
        add(title);

        // Calculate center for 400px width frame
        int labelX = 50;
        int fieldX = 140;
        int fieldWidth = 200;

        JLabel lEmail = new JLabel("Email:");
        lEmail.setBounds(labelX, 83, 80, 25); // Nudged down for vertical alignment
        Theme.styleLabel(lEmail);
        add(lEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(fieldX, 80, fieldWidth, 30);
        Theme.styleTextField(emailField);
        add(emailField);

        JLabel lPass = new JLabel("Password:");
        lPass.setBounds(labelX, 133, 80, 25); // Nudged down
        Theme.styleLabel(lPass);
        add(lPass);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(fieldX, 130, fieldWidth, 30);
        Theme.styleTextField(passwordField);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 200, 200, 40); // Centered (400-200)/2 = 100
        Theme.styleButton(loginBtn);
        add(loginBtn);

        JButton signupBtn = new JButton("New Patient? Sign Up");
        signupBtn.setBounds(50, 250, 300, 20);
        signupBtn.setBorderPainted(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(Theme.SECONDARY);
        signupBtn.setFont(Theme.NORMAL_FONT);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        add(signupBtn);

        loginBtn.addActionListener(e -> {

            User user = LoginController.login(
                    emailField.getText(),
                    new String(passwordField.getPassword()));

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            } else {
                SessionManager.setUser(user);

                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    new AdminDashboard();
                } else if (user instanceof Patient) {
                    new PatientDashboard();
                } else if (user instanceof Doctor) {
                    new DoctorDashboard();
                }

                dispose();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
