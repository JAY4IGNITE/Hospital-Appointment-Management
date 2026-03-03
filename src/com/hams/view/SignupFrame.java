package com.hams.view;

import com.hams.util.Theme;
import com.hams.dao.PatientDAO;
import com.hams.model.Patient;
import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class SignupFrame extends JFrame {

    public SignupFrame() {
        Theme.setupFrame(this, "Patient Registration", 400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Override to EXIT for main frames

        JLabel title = new JLabel("New Patient Signup", JLabel.CENTER);
        title.setBounds(0, 20, 400, 30); // Centered
        Theme.styleHeader(title);
        add(title);

        int labelX = 50;
        int fieldX = 140;
        int fieldWidth = 200;

        JLabel lName = new JLabel("Full Name:");
        lName.setBounds(labelX, 83, 100, 25);
        Theme.styleLabel(lName);
        add(lName);

        JTextField nameField = new JTextField();
        nameField.setBounds(fieldX, 80, fieldWidth, 30);
        Theme.styleTextField(nameField);
        add(nameField);

        JLabel lEmail = new JLabel("Email:");
        lEmail.setBounds(labelX, 133, 100, 25);
        Theme.styleLabel(lEmail);
        add(lEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(fieldX, 130, fieldWidth, 30);
        Theme.styleTextField(emailField);
        add(emailField);

        JLabel lPass = new JLabel("Password:");
        lPass.setBounds(labelX, 183, 100, 25);
        Theme.styleLabel(lPass);
        add(lPass);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(fieldX, 180, fieldWidth, 30);
        Theme.styleTextField(passField);
        add(passField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(100, 250, 200, 40); // Centered (400-200)/2 = 100
        Theme.styleSuccessButton(registerBtn);
        add(registerBtn);

        JButton loginBtn = new JButton("Already have an account? Login");
        loginBtn.setBounds(50, 310, 300, 30);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(Theme.SECONDARY);
        loginBtn.setFont(Theme.NORMAL_FONT);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(loginBtn);

        // Actions
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = new String(passField.getPassword());

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            // Generate ID (Simple UUID substring for now)
            String pid = "P-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

            Patient newPatient = new Patient(pid, name, email, pass);
            if (PatientDAO.addPatient(newPatient)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please Login.");
                dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Registration Failed. Email might presumably exist or database error.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
