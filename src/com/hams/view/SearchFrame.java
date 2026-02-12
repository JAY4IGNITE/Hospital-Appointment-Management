package com.hams.view;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchFrame extends JFrame {

    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchFrame() {
        setTitle("Search Appointments");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🎨 Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel headerLabel = new JLabel("Appointment Search");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 🔍 Search Bar Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        searchField = new JTextField(30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton searchButton = new JButton("Search");
        styleBlueButton(searchButton);

        searchPanel.add(new JLabel("Enter Patient/Doctor Name or Date:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.CENTER); // Will be at top of center, but FlowLayout is centered

        // 📊 Results Table
        String[] columnNames = { "Patient Name", "Doctor Name", "Date", "Time", "Symptoms" };
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Combine Search Panel and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
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

    private void styleBlueButton(JButton btn) {
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
