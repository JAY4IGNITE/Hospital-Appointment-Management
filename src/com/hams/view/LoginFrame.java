package com.hams.view;

import com.hams.controller.LoginController;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import com.hams.model.User;
import com.hams.util.SessionManager;
import com.hams.util.Theme;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginFrame extends JFrame {

    private Image bgImage;

    public LoginFrame() {
        // Load background image
        try {
            URL imgUrl = getClass().getResource("/com/hams/resources/login_bg.png");
            if (imgUrl != null) {
                bgImage = new ImageIcon(imgUrl).getImage();
            }
        } catch (Exception e) {
            System.err.println("Background image could not be loaded: " + e.getMessage());
        }

        Theme.setupFrame(this, "Hospital Management System - Login", 800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback gradient if image not available
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, Theme.PRIMARY, 0, getHeight(), Theme.SECONDARY);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        bgPanel.setLayout(new GridBagLayout()); // Center the login box

        // Translucent card
        JPanel loginCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230)); // 230/255 opacity
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        loginCard.setOpaque(false); // Make standard background invisible
        loginCard.setPreferredSize(new Dimension(400, 420));
        loginCard.setLayout(null); // Absolute positioning within the card

        // Title
        JLabel title = new JLabel("Welcome Back to CayCare", JLabel.CENTER);
        title.setBounds(0, 30, 400, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Theme.PRIMARY);
        loginCard.add(title);
        
        JLabel subtitle = new JLabel("Sign in to continue", JLabel.CENTER);
        subtitle.setBounds(0, 70, 400, 20);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 100, 100));
        loginCard.add(subtitle);

        int labelX = 50;
        int fieldX = 50;
        int fieldWidth = 300;

        JLabel lEmail = new JLabel("Email");
        lEmail.setBounds(labelX, 110, fieldWidth, 25);
        Theme.styleLabel(lEmail);
        lEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginCard.add(lEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(fieldX, 140, fieldWidth, 40);
        Theme.styleTextField(emailField);
        loginCard.add(emailField);

        JLabel lPass = new JLabel("Password");
        lPass.setBounds(labelX, 190, fieldWidth, 25);
        Theme.styleLabel(lPass);
        lPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginCard.add(lPass);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(fieldX, 220, fieldWidth, 40);
        Theme.styleTextField(passwordField);
        loginCard.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 290, 300, 45); 
        Theme.styleButton(loginBtn);
        loginCard.add(loginBtn);

        JButton signupBtn = new JButton("New Patient? Create an account");
        signupBtn.setBounds(50, 350, 300, 30);
        signupBtn.setBorderPainted(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(Theme.SECONDARY);
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });
        loginCard.add(signupBtn);

        loginBtn.addActionListener(e -> {
            User user = LoginController.login(
                    emailField.getText(),
                    new String(passwordField.getPassword()));

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
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

        bgPanel.add(loginCard);
        setContentPane(bgPanel);
        setVisible(true);
        setResizable(false);
    }
}
