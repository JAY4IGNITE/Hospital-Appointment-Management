package com.hams.view;

import com.hams.util.Theme;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class DoctorDashboard extends JFrame {

    public DoctorDashboard() {
        Theme.setupFrame(this, "Doctor Dashboard", 700, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Doctor doctor = (Doctor) SessionManager.getUser();

        JLabel header = new JLabel(
                "Welcome Dr. " + doctor.getName(),
                JLabel.CENTER);
        header.setFont(Theme.HEADER_FONT);
        header.setForeground(Theme.PRIMARY);
        add(header, "North");

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
        table.setRowHeight(28);
        table.setFont(Theme.NORMAL_FONT);
        table.getTableHeader().setFont(Theme.BOLD_FONT);

        // Load Data
        loadAppointments(model, doctor.getName());

        // Layout
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Theme.BG_COLOR);

        JButton btnCompleted = new JButton("Mark Completed");
        Theme.styleSuccessButton(btnCompleted);

        JButton btnMissed = new JButton("Mark Missed");
        Theme.styleDangerButton(btnMissed);

        JButton searchBtn = new JButton("Search");
        Theme.styleButton(searchBtn);

        btnCompleted.addActionListener(e -> updateStatus(table, model, "COMPLETED"));
        btnMissed.addActionListener(e -> updateStatus(table, model, "MISSED"));
        searchBtn.addActionListener(e -> new SearchFrame());

        bottomPanel.add(btnCompleted);
        bottomPanel.add(btnMissed);
        bottomPanel.add(searchBtn);

        JButton logoutBtn = new JButton("Logout");
        Theme.styleDangerButton(logoutBtn);

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                com.hams.util.SessionManager.clear();
                new LoginFrame();
                dispose();
            }
        });

        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
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
