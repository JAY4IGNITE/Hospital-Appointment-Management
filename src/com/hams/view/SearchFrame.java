package com.hams.view;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchFrame extends JFrame {

    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchFrame() {
        Theme.setupFrame(this, "Search Appointments", 800, 550);
        setLayout(new BorderLayout());

        // 🎨 Header
        add(Theme.createHeaderPanel("Appointment Search"), BorderLayout.NORTH);

        // 🔍 Search Bar Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        searchField = new JTextField(30);
        Theme.styleTextField(searchField);

        JButton searchButton = new JButton("Search");
        Theme.styleButton(searchButton);

        JLabel promptLabel = new JLabel("Enter Patient/Doctor Name or Date:");
        Theme.styleLabel(promptLabel);

        searchPanel.add(promptLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.CENTER); // Will be at top of center, but FlowLayout is centered

        // 📊 Results Table
        String[] columnNames = { "Patient Name", "Doctor Name", "Date", "Time", "Symptoms" };
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        Theme.styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.getViewport().setBackground(Theme.BG_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Combine Search Panel and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action Listener
        searchButton.addActionListener(e -> performSearch());

        setVisible(true);
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
            return;
        }

        List<Appointment> results = AppointmentDAO.searchAppointments(query);
        tableModel.setRowCount(0); // Clear previous results

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments found.");
        } else {
            for (Appointment app : results) {
                tableModel.addRow(new Object[] {
                        app.getPatientName(),
                        app.getDoctorName(),
                        app.getDate(),
                        app.getTimeSlot(),
                        app.getSymptoms()
                });
            }
        }
    }
}
