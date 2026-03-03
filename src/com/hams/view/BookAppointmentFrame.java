package com.hams.view;

import com.hams.controller.AppointmentController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookAppointmentFrame extends JFrame {

    public BookAppointmentFrame(String preSelectedDoctorName) {

        setTitle("Book Appointment");
        setSize(450, 400); // Increased height for better spacing
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🎨 Background
        getContentPane().setBackground(new Color(248, 249, 249));

        // 🔹 HEADER PANEL
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setPreferredSize(new Dimension(100, 55));

        JLabel header = new JLabel("Book Appointment");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));

        headerPanel.add(header);
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(248, 249, 249));
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel doctorLabel = new JLabel("Doctor");
        JLabel patientLabel = new JLabel("Patient Name");
        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd)");
        JLabel timeLabel = new JLabel("Time Slot");
        JLabel symptomLabel = new JLabel("Symptoms");

        doctorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        patientLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        symptomLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Doctor Dropdown
        JComboBox<String> doctorBox = new JComboBox<>();
        List<com.hams.model.Doctor> doctors = com.hams.dao.DoctorDAO.getAllDoctors();
        for (com.hams.model.Doctor d : doctors) {
            doctorBox.addItem(d.getName() + " - " + d.getDoctorId());
        }

        // If a doctor was pre-selected (e.g. from table), try to select it
        if (preSelectedDoctorName != null && !preSelectedDoctorName.isEmpty()) {
            for (int i = 0; i < doctorBox.getItemCount(); i++) {
                if (doctorBox.getItemAt(i).startsWith(preSelectedDoctorName)) {
                    doctorBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        JTextField patientField = new JTextField();
        com.hams.model.User user = com.hams.util.SessionManager.getUser();
        if (user != null && user instanceof com.hams.model.Patient) {
            patientField.setText(((com.hams.model.Patient) user).getName());
            patientField.setEditable(false);
        }

        // Date Field
        JTextField dateField = new JTextField(); // Simple text field for date

        // Time Slot Dropdown
        JComboBox<String> timeBox = new JComboBox<>();
        for (String slot : com.hams.util.TimeSlots.getAll()) {
            timeBox.addItem(slot);
        }

        JTextArea symptomArea = new JTextArea(3, 20);
        symptomArea.setLineWrap(true);
        symptomArea.setWrapStyleWord(true);
        symptomArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane symptomScroll = new JScrollPane(symptomArea);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(doctorLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(doctorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(patientLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(patientField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(symptomLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(symptomScroll, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 🔹 BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(248, 249, 249));

        JButton bookBtn = new JButton("Confirm Booking");
        styleGreenButton(bookBtn);

        buttonPanel.add(bookBtn);

        JButton backBtn = new JButton("Back");
        styleGreenButton(backBtn); // Re-using green style for consistency, or maybe standard style?
        backBtn.setBackground(new Color(149, 165, 166)); // Grey for Back

        backBtn.addActionListener(e -> dispose());
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 🔹 Button Action
        bookBtn.addActionListener(e -> {

            if (patientField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter patient name and date",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Extract doctor name from "Name - ID"
            String selected = (String) doctorBox.getSelectedItem();
            String docName = selected.split(" - ")[0];

            boolean success = AppointmentController.bookAppointment(
                    patientField.getText(),
                    docName,
                    dateField.getText(),
                    timeBox.getSelectedItem().toString(),
                    symptomArea.getText());

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Appointment booked successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Doctor is not available at this time. Please choose another slot.",
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    // 🎨 Reusable green button style
    private void styleGreenButton(JButton btn) {
        btn.setBackground(new Color(39, 174, 96));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
