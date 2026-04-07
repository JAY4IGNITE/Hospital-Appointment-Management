package com.hams.view;

import com.hams.util.Theme;
import com.hams.dao.DoctorDAO;
import com.hams.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PatientDashboard extends JFrame {

    JTable doctorTable;

    public PatientDashboard() {
        setTitle("Patient Dashboard - Hospital Appointment System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_COLOR);

        // 🔹 HEADER PANEL (Match AdminDashboard)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel header = new JLabel("Welcome to CayCare", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 42));
        header.setForeground(Theme.WHITE);
        headerPanel.add(header, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 MAIN CONTENT WRAPPER with GridBagLayout for centering and spacing
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(Theme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 40, 20, 40);

        // 🔹 TABLE SECTION
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Theme.BG_COLOR);
        
        JLabel tableTitle = new JLabel("Available Doctors", SwingConstants.LEFT);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tableTitle.setForeground(Theme.PRIMARY);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {
                "Doctor Name",
                "Specialization",
                "Hospital",
                "Available Time"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        doctorTable = new JTable(model);
        doctorTable.setRowHeight(40); // Taller rows
        doctorTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        doctorTable.setSelectionBackground(new Color(213, 245, 227));

        JTableHeader tableHeader = doctorTable.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setBackground(Theme.PRIMARY);
        tableHeader.setForeground(Theme.WHITE);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.setPreferredSize(new Dimension(100, 45));

        // Load doctors
        List<Doctor> doctors = DoctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            model.addRow(new Object[] {
                    d.getName(),
                    d.getSpecialization(),
                    d.getHospital(),
                    "09:00 AM - 01:00 PM"
            });
        }

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.PRIMARY, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7; // Take up 70% of vertical space
        gbc.fill = GridBagConstraints.BOTH;
        mainContent.add(tablePanel, gbc);

        // 🔹 BUTTON PANEL SECTION (Match AdminDashboard Actions Panel)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(Theme.BG_COLOR);
        
        Dimension btnSize = new Dimension(220, 60);

        JButton bookBtn = createDashboardButton("Book Appointment", btnSize, false);
        bookBtn.addActionListener(e -> {
            int row = doctorTable.getSelectedRow();
            String doctorName = "";
            if (row != -1) {
                doctorName = doctorTable.getValueAt(row, 0).toString();
            }
            new BookAppointmentFrame(doctorName);
        });

        JButton viewBtn = createDashboardButton("View My Appointments", btnSize, false);
        viewBtn.addActionListener(e -> new ViewAppointmentsFrame());

        JButton searchBtn = createDashboardButton("Search Appointments", btnSize, false);
        searchBtn.addActionListener(e -> new SearchFrame());

        JButton logoutBtn = createDashboardButton("Logout", btnSize, true);
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                com.hams.util.SessionManager.clear();
                new LoginFrame();
                dispose();
            }
        });

        buttonPanel.add(bookBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(logoutBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3; // Take up remaining vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainContent.add(buttonPanel, gbc);

        add(mainContent, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createDashboardButton(String text, Dimension size, boolean isDanger) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(size);
        
        if (isDanger) {
            Theme.styleDangerButton(btn); // Red button
        } else {
            Theme.styleButton(btn); // Blue button
        }
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return btn;
    }
}
