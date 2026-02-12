package com.hams.view;

import com.hams.util.Theme;

import com.hams.dao.AppointmentDAO;
import com.hams.dao.DoctorDAO;
import com.hams.dao.PatientDAO;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        Theme.setupFrame(this, "Admin Dashboard - Hospital Management System", 800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Theme.PRIMARY);
        JLabel title = new JLabel("Admin Dashboard");
        title.setForeground(Theme.WHITE);
        title.setFont(Theme.HEADER_FONT);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(Theme.BG_COLOR);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBackground(Theme.BG_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        statsPanel.add(createStatCard("Doctors", DoctorDAO.getDoctorCount(), Theme.ACCENT));
        statsPanel.add(createStatCard("Patients", PatientDAO.getPatientCount(), Theme.SECONDARY));
        statsPanel.add(createStatCard("Appointments Today", AppointmentDAO.getTodayAppointmentCount(), Theme.DANGER));

        mainPanel.add(statsPanel);

        // Actions Panel
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionsPanel.setBackground(Theme.BG_COLOR);

        JButton btnDoctors = new JButton("Manage Doctors");
        btnDoctors.setPreferredSize(new Dimension(200, 50));
        Theme.styleButton(btnDoctors);
        btnDoctors.addActionListener(e -> new AdminDoctorFrame());

        JButton btnPatients = new JButton("Manage Patients");
        btnPatients.setPreferredSize(new Dimension(200, 50));
        Theme.styleButton(btnPatients);
        btnPatients.addActionListener(e -> new AdminPatientFrame());

        JButton btnAppointments = new JButton("View All Appointments");
        btnAppointments.setPreferredSize(new Dimension(200, 50));
        Theme.styleButton(btnAppointments);
        btnAppointments.addActionListener(e -> new ViewAppointmentsFrame());

        JButton btnDownload = new JButton("Download Data");
        btnDownload.setPreferredSize(new Dimension(200, 50));
        Theme.styleButton(btnDownload);
        btnDownload.addActionListener(e -> downloadData());

        JButton btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(new Dimension(200, 50));
        Theme.styleDangerButton(btnLogout);
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        actionsPanel.add(btnDoctors);
        actionsPanel.add(btnPatients);
        actionsPanel.add(btnAppointments);
        actionsPanel.add(btnDownload);
        actionsPanel.add(btnLogout);

        mainPanel.add(actionsPanel);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createStatCard(String title, int count, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCount = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblCount.setForeground(Color.WHITE);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(Theme.SUBHEADER_FONT);
        lblTitle.setForeground(Color.WHITE);

        panel.add(lblCount, BorderLayout.CENTER);
        panel.add(lblTitle, BorderLayout.SOUTH);

        return panel;
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
