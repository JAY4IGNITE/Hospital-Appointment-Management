package com.hams.view;

import com.hams.util.Theme;
import com.hams.dao.PatientDAO;
import com.hams.model.Patient;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.UUID;

public class SignupFrame extends JFrame {

    private Image bgImage;

    public SignupFrame() {
        // Load background image
        try {
            URL imgUrl = getClass().getResource("/com/hams/resources/login_bg.png");
            if (imgUrl != null) {
                bgImage = new ImageIcon(imgUrl).getImage();
            }
        } catch (Exception e) {
            System.err.println("Background image could not be loaded: " + e.getMessage());
        }

        Theme.setupFrame(this, "Patient Registration", 800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Override to EXIT for main frames

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
        bgPanel.setLayout(new GridBagLayout()); // Center the signup box

        // Translucent card
        JPanel signupCard = new JPanel() {
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
        signupCard.setOpaque(false); // Make standard background invisible
        signupCard.setPreferredSize(new Dimension(450, 480));
        signupCard.setLayout(null); // Absolute positioning within the card

        // Title
        JLabel title = new JLabel("Welcome to CayCare", JLabel.CENTER);
        title.setBounds(0, 30, 450, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Theme.PRIMARY);
        signupCard.add(title);
        
        JLabel subtitle = new JLabel("Create a new patient account", JLabel.CENTER);
        subtitle.setBounds(0, 70, 450, 20);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 100, 100));
        signupCard.add(subtitle);

        int labelX = 50;
        int fieldX = 50;
        int fieldWidth = 350;

        JLabel lName = new JLabel("Full Name");
        lName.setBounds(labelX, 100, fieldWidth, 25);
        Theme.styleLabel(lName);
        lName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupCard.add(lName);

        JTextField nameField = new JTextField();
        nameField.setBounds(fieldX, 130, fieldWidth, 40);
        Theme.styleTextField(nameField);
        signupCard.add(nameField);

        JLabel lEmail = new JLabel("Email");
        lEmail.setBounds(labelX, 180, fieldWidth, 25);
        Theme.styleLabel(lEmail);
        lEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupCard.add(lEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(fieldX, 210, fieldWidth, 40);
        Theme.styleTextField(emailField);
        signupCard.add(emailField);

        JLabel lPass = new JLabel("Password");
        lPass.setBounds(labelX, 260, fieldWidth, 25);
        Theme.styleLabel(lPass);
        lPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupCard.add(lPass);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(fieldX, 290, fieldWidth, 40);
        Theme.styleTextField(passField);
        signupCard.add(passField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 360, 350, 45); 
        Theme.styleButton(registerBtn); // Use the same blue standard button as login
        signupCard.add(registerBtn);

        JButton loginBtn = new JButton("Already have an account? Login here");
        loginBtn.setBounds(50, 420, 350, 30);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(Theme.SECONDARY);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupCard.add(loginBtn);

        // Actions
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = new String(passField.getPassword());

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Generate ID (Simple UUID substring for now)
            String pid = "P-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

            Patient newPatient = new Patient(pid, name, email, pass);
            if (PatientDAO.addPatient(newPatient)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please Login.", "Success", JOptionPane.INFORMATION_MESSAGE);
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

        bgPanel.add(signupCard);
        setContentPane(bgPanel);
        setVisible(true);
        setResizable(false);
    }
}
