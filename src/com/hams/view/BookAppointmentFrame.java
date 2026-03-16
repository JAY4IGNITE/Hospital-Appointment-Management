package com.hams.view;

import com.hams.controller.AppointmentController;
import com.hams.util.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookAppointmentFrame extends JFrame {

    public BookAppointmentFrame(String preSelectedDoctorName) {
        setTitle("Book Appointment - Hospital Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_COLOR);

        // 🔹 HEADER PANEL
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel title = new JLabel("Book Appointment", SwingConstants.CENTER);
        title.setForeground(Theme.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        headerPanel.add(title, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Theme.BG_COLOR);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 15, 4, 15); // Heavily reduced vertical padding to fit all elements onscreen
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        JLabel doctorLabel = createConfiguredLabel("Select Doctor:");
        JLabel patientLabel = createConfiguredLabel("Patient Name:");
        JLabel dateLabel = createConfiguredLabel("Appointment Date:");
        JLabel timeLabel = createConfiguredLabel("Time Slot:");
        JLabel symptomLabel = createConfiguredLabel("Symptoms Overview:");

        // Doctor Dropdown
        JComboBox<String> doctorBox = new JComboBox<>();
        styleComboBox(doctorBox);
        List<com.hams.model.Doctor> doctors = com.hams.dao.DoctorDAO.getAllDoctors();
        for (com.hams.model.Doctor d : doctors) {
            doctorBox.addItem(d.getName() + " - " + d.getDoctorId());
        }

        if (preSelectedDoctorName != null && !preSelectedDoctorName.isEmpty()) {
            for (int i = 0; i < doctorBox.getItemCount(); i++) {
                if (doctorBox.getItemAt(i).startsWith(preSelectedDoctorName)) {
                    doctorBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        // Patient Field
        JTextField patientField = new JTextField();
        Theme.styleTextField(patientField);
        patientField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        patientField.setPreferredSize(new Dimension(0, 45)); // width 0 allows GridBagLayout to determine width
        com.hams.model.User user = com.hams.util.SessionManager.getUser();
        if (user != null && user instanceof com.hams.model.Patient) {
            patientField.setText(((com.hams.model.Patient) user).getName());
            patientField.setEditable(false);
        }

        // Date Selection
        JComboBox<String> yearBox = new JComboBox<>();
        JComboBox<String> monthBox = new JComboBox<>();
        JComboBox<String> dayBox = new JComboBox<>();
        styleComboBox(yearBox);
        styleComboBox(monthBox);
        styleComboBox(dayBox);

        java.time.LocalDate now = java.time.LocalDate.now();
        for (int i = 0; i <= 2; i++) yearBox.addItem(String.valueOf(now.getYear() + i));
        for (int i = 1; i <= 12; i++) monthBox.addItem(String.format("%02d", i));
        for (int i = 1; i <= 31; i++) dayBox.addItem(String.format("%02d", i));

        yearBox.setSelectedItem(String.valueOf(now.getYear()));
        monthBox.setSelectedItem(String.format("%02d", now.getMonthValue()));
        dayBox.setSelectedItem(String.format("%02d", now.getDayOfMonth()));

        JPanel datePickerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        datePickerPanel.setOpaque(false);
        datePickerPanel.add(yearBox);
        datePickerPanel.add(monthBox);
        datePickerPanel.add(dayBox);

        // Time Slot Dropdown
        JComboBox<String> timeBox = new JComboBox<>();
        styleComboBox(timeBox);
        for (String slot : com.hams.util.TimeSlots.getAll()) {
            timeBox.addItem(slot);
        }

        // Symptoms Area
        JTextArea symptomArea = new JTextArea(3, 40);
        symptomArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        symptomArea.setLineWrap(true);
        symptomArea.setWrapStyleWord(true);
        JTextField tempField = new JTextField();
        Theme.styleTextField(tempField);
        symptomArea.setBorder(BorderFactory.createCompoundBorder(
            tempField.getBorder(), 
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JScrollPane symptomScroll = new JScrollPane(symptomArea);
        symptomScroll.setMinimumSize(new Dimension(0, 80));
        symptomScroll.setBorder(BorderFactory.createEmptyBorder()); // remove inner border
        
        // Add to GridBag
        int y = 0;
        
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 1;
        formPanel.add(doctorLabel, gbc);
        gbc.gridy = ++y; gbc.gridwidth = 2;
        formPanel.add(doctorBox, gbc);

        gbc.gridy = ++y; gbc.gridwidth = 1;
        formPanel.add(patientLabel, gbc);
        gbc.gridy = ++y; gbc.gridwidth = 2;
        formPanel.add(patientField, gbc);

        gbc.gridy = ++y; gbc.gridwidth = 1;
        formPanel.add(dateLabel, gbc);
        gbc.gridy = ++y; gbc.gridwidth = 2;
        formPanel.add(datePickerPanel, gbc);

        gbc.gridy = ++y; gbc.gridwidth = 1;
        formPanel.add(timeLabel, gbc);
        gbc.gridy = ++y; gbc.gridwidth = 2;
        formPanel.add(timeBox, gbc);

        gbc.gridy = ++y; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(symptomLabel, gbc);
        gbc.gridy = ++y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1.0; // Allows symptoms box to stretch vertically
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(symptomScroll, gbc);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Theme.BG_COLOR);
        
        // Add a nice border around the form panel to look like an elevated card
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 10, 5, 10),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)
            )
        ));
        
        // Use GridBagConstraints to force formPanel to take up exactly 60% of width and 85% of height
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        
        wrapperGbc.gridx = 0; wrapperGbc.gridy = 1; wrapperGbc.weightx = 0.2; wrapperGbc.fill = GridBagConstraints.BOTH;
        centerWrapper.add(Box.createGlue(), wrapperGbc); // Left padding
        
        wrapperGbc.gridx = 2; wrapperGbc.weightx = 0.2;
        centerWrapper.add(Box.createGlue(), wrapperGbc); // Right padding
        
        wrapperGbc.gridx = 1; wrapperGbc.gridy = 0; wrapperGbc.weightx = 0.6; wrapperGbc.weighty = 0.02;
        centerWrapper.add(Box.createGlue(), wrapperGbc); // Top padding
        
        wrapperGbc.gridx = 1; wrapperGbc.gridy = 2; wrapperGbc.weighty = 0.02;
        centerWrapper.add(Box.createGlue(), wrapperGbc); // Bottom padding
        
        wrapperGbc.gridx = 1; wrapperGbc.gridy = 1; wrapperGbc.weightx = 0.6; wrapperGbc.weighty = 0.96; 
        wrapperGbc.fill = GridBagConstraints.BOTH;
        centerWrapper.add(formPanel, wrapperGbc);

        add(centerWrapper, BorderLayout.CENTER);

        // 🔹 BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Theme.BG_COLOR);

        Dimension btnSize = new Dimension(250, 60);

        JButton bookBtn = new JButton("Confirm Booking");
        bookBtn.setPreferredSize(btnSize);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        Theme.styleButton(bookBtn);

        JButton backBtn = new JButton("Cancel");
        backBtn.setPreferredSize(btnSize);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        Theme.styleDangerButton(backBtn);
        backBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(bookBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 🔹 Button Action
        bookBtn.addActionListener(e -> {

            if (patientField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter patient name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selected = (String) doctorBox.getSelectedItem();
            String[] docParts = selected.split(" - ");
            String docName = docParts[0];
            String docId = docParts.length > 1 ? docParts[1] : "";

            String patId = "";
            if (user != null && user instanceof com.hams.model.Patient) {
                patId = ((com.hams.model.Patient) user).getPatientId();
            }

            String dateValue = yearBox.getSelectedItem() + "-" + monthBox.getSelectedItem() + "-"
                    + dayBox.getSelectedItem();

            PaymentDialog paymentDialog = new PaymentDialog(BookAppointmentFrame.this);
            paymentDialog.setVisible(true);

            if (!paymentDialog.isPaymentSuccessful()) {
                return; // halt booking
            }

            boolean success = AppointmentController.bookAppointment(
                    patId, patientField.getText(), docId, docName, dateValue,
                    timeBox.getSelectedItem().toString(), symptomArea.getText());

            if (success) {
                JOptionPane.showMessageDialog(this, "Appointment booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Doctor is not available at this time. Please choose another slot.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
    
    private JLabel createConfiguredLabel(String title) {
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Theme.PRIMARY);
        return lbl;
    }
    
    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(0, 45)); // Width 0 allows component to safely dynamically expand
    }
}
