package com.hams.view;

import com.hams.util.Theme;
import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DoctorDashboard extends JFrame {

    public DoctorDashboard() {
        setTitle("Doctor Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_COLOR);

        Doctor doctor = (Doctor) SessionManager.getUser();

        // 🔹 HEADER PANEL (Match AdminDashboard)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel header = new JLabel("Welcome Dr. " + doctor.getName(), SwingConstants.CENTER);
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
        
        JLabel tableTitle = new JLabel("My Appointments", SwingConstants.LEFT);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tableTitle.setForeground(Theme.PRIMARY);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {
                "ID",
                "Patient Name",
                "Date",
                "Time Slot",
                "Symptoms",
                "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(40); // Taller rows
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setSelectionBackground(new Color(213, 245, 227));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Theme.PRIMARY);
        tableHeader.setForeground(Theme.WHITE);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.setPreferredSize(new Dimension(100, 45));

        // Load Data
        loadAppointments(model, doctor.getName());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.PRIMARY, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7; // Take up 70% of vertical space
        gbc.fill = GridBagConstraints.BOTH;
        mainContent.add(tablePanel, gbc);

        // 🔹 BUTTON PANEL SECTION (Match AdminDashboard Actions Panel)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setBackground(Theme.BG_COLOR);
        
        Dimension btnSize = new Dimension(220, 60);

        JButton btnCompleted = createDashboardButton("Mark Completed", btnSize, false);
        btnCompleted.addActionListener(e -> updateStatus(table, model, "COMPLETED"));

        JButton btnMissed = createDashboardButton("Mark Missed", btnSize, true); // Danger/Red
        btnMissed.addActionListener(e -> updateStatus(table, model, "MISSED"));

        JButton searchBtn = createDashboardButton("Search", btnSize, false);
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

        bottomPanel.add(btnCompleted);
        bottomPanel.add(btnMissed);
        bottomPanel.add(searchBtn);
        bottomPanel.add(logoutBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3; // Take up remaining vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainContent.add(bottomPanel, gbc);

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

    private void loadAppointments(DefaultTableModel model, String doctorName) {
        model.setRowCount(0);
        List<Appointment> appointments = AppointmentDAO.getAppointmentsByDoctor(doctorName);
        for (Appointment a : appointments) {
            model.addRow(new Object[] {
                    a.getId(),
                    a.getPatientName(),
                    a.getDate(),
                    a.getTimeSlot(),
                    a.getSymptoms(),
                    a.getStatus()
            });
        }
    }

    private void updateStatus(JTable table, DefaultTableModel model, String status) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment.");
            return;
        }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        AppointmentDAO.updateStatus(id, status);

        // Refresh
        Doctor doctor = (Doctor) SessionManager.getUser();
        loadAppointments(model, doctor.getName());
    }
}
