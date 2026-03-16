package com.hams.view;

import com.hams.util.Theme;
import com.hams.dao.AppointmentDAO;
import com.hams.dao.DoctorDAO;
import com.hams.dao.PatientDAO;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard - Hospital Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.PRIMARY); // Dark blue header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setForeground(Theme.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42)); // Large bold font
        headerPanel.add(title, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel with GridBagLayout for centering and spacing
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(Theme.BG_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 40, 40, 40); // Margins
        
        // 1. Dashboard Section (Stats)
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 50, 0)); // 3 columns, spacing
        statsPanel.setBackground(Theme.BG_COLOR);
        
        int doctorCount = DoctorDAO.getDoctorCount();
        int patientCount = PatientDAO.getPatientCount();
        int appointmentCount = AppointmentDAO.getTodayAppointmentCount();
        
        statsPanel.add(new StatCard("Doctors", doctorCount, Theme.ACCENT)); // Green
        statsPanel.add(new StatCard("Patients", patientCount, Theme.SECONDARY)); // Blue
        statsPanel.add(new StatCard("Appointments Today", appointmentCount, Theme.DANGER)); // Red
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6; // Take up more space for stats if needed
        gbc.fill = GridBagConstraints.BOTH;
        mainContent.add(statsPanel, gbc);

        // 2. Action Section (Buttons)
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        actionsPanel.setBackground(Theme.BG_COLOR);

        Dimension btnSize = new Dimension(220, 60);

        JButton btnDoctors = createHoverButton("Manage Doctors", btnSize, false);
        btnDoctors.addActionListener(e -> new AdminDoctorFrame());

        JButton btnPatients = createHoverButton("Manage Patients", btnSize, false);
        btnPatients.addActionListener(e -> new AdminPatientFrame());

        JButton btnAppointments = createHoverButton("View All Appointments", btnSize, false);
        btnAppointments.addActionListener(e -> new ViewAppointmentsFrame());

        JButton btnDownload = createHoverButton("Download Data", btnSize, false);
        btnDownload.addActionListener(e -> downloadData());

        JButton btnLogout = createHoverButton("Logout", btnSize, true);
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        actionsPanel.add(btnDoctors);
        actionsPanel.add(btnPatients);
        actionsPanel.add(btnAppointments);
        actionsPanel.add(btnDownload);
        actionsPanel.add(btnLogout);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainContent.add(actionsPanel, gbc);

        add(mainContent, BorderLayout.CENTER);
        
        // Finalize
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createHoverButton(String text, Dimension size, boolean isDanger) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(size);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Modern Sans Serif bold font
        
        if (isDanger) {
            Theme.styleDangerButton(btn); // Red button
        } else {
            Theme.styleButton(btn); // Blue button 
        }

        return btn;
    }

    // Custom JPanel for Statistic Cards (Rounded corners, Shadow, Hover)
    class StatCard extends JPanel {
        private Color baseColor;
        private Color currentColor;
        private int radius = 30;

        public StatCard(String title, int count, Color color) {
            this.baseColor = color;
            this.currentColor = color;

            setLayout(new BorderLayout());
            setOpaque(false); // Make transparent so we can draw rounded background
            setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

            JLabel lblCount = new JLabel(String.valueOf(count), SwingConstants.CENTER);
            lblCount.setFont(new Font("Segoe UI", Font.BOLD, 80));
            lblCount.setForeground(Color.WHITE);

            JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblTitle.setForeground(Color.WHITE);

            add(lblCount, BorderLayout.CENTER);
            add(lblTitle, BorderLayout.SOUTH);

            // Hover effect
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    currentColor = baseColor.brighter().brighter(); // noticeable brightness
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    currentColor = baseColor;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Shadow effect
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 10, radius, radius);

            // Card background
            g2.setColor(currentColor);
            g2.fillRoundRect(0, 0, getWidth() - 15, getHeight() - 15, radius, radius);

            g2.dispose();
        }
    }

    private void downloadData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Hospital Data");
        fileChooser.setSelectedFile(new java.io.File("hospital_data.csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try {
                com.hams.util.DataExport.exportToCSV(
                        DoctorDAO.getAllDoctors(),
                        PatientDAO.getAllPatients(),
                        AppointmentDAO.getAllAppointments(),
                        fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Data exported successfully to " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error exporting data: " + ex.getMessage());
            }
        }
    }
}
