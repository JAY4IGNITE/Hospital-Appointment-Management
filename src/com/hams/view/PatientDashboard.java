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
        Theme.setupFrame(this, "Patient Dashboard - Hospital Appointment System", 900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Override
        setLayout(new BorderLayout());

        // 🔹 HEADER PANEL
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Theme.PRIMARY);
        headerPanel.setPreferredSize(new Dimension(100, 60));

        JLabel header = new JLabel("Welcome to CayCare");
        header.setFont(Theme.HEADER_FONT);
        header.setForeground(Theme.WHITE);

        headerPanel.add(header);
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 TABLE
        String[] columns = {
                "Doctor Name",
                "Specialization",
                "Hospital",
                "Available Time"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(model);
        doctorTable.setRowHeight(30);
        doctorTable.setFont(Theme.NORMAL_FONT);
        doctorTable.setSelectionBackground(new Color(213, 245, 227));

        JTableHeader tableHeader = doctorTable.getTableHeader();
        tableHeader.setBackground(new Color(213, 245, 227));
        tableHeader.setFont(Theme.BOLD_FONT);

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

        add(new JScrollPane(doctorTable), BorderLayout.CENTER);

        // 🔹 BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Theme.BG_COLOR);

        JButton bookBtn = new JButton("Book Appointment");
        Theme.styleSuccessButton(bookBtn);

        JButton viewBtn = new JButton("View My Appointments");
        Theme.styleButton(viewBtn);

        JButton searchBtn = new JButton("Search Appointments");
        Theme.styleButton(searchBtn);

        buttonPanel.add(bookBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // 🔹 Actions
        bookBtn.addActionListener(e -> {
            int row = doctorTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a doctor first");
            } else {
                String doctorName = doctorTable.getValueAt(row, 0).toString();
                new BookAppointmentFrame(doctorName);
            }
        });

        viewBtn.addActionListener(e -> new ViewAppointmentsFrame());
        searchBtn.addActionListener(e -> new SearchFrame());

        setVisible(true);
    }
}
