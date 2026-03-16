package com.hams.view;

import com.hams.dao.DoctorDAO;
import com.hams.model.Doctor;
import com.hams.util.Theme;

import javax.swing.*;
import java.awt.*;

public class AddDoctorDialog extends JDialog {

    private JTextField nameField, emailField, specialField, hospitalField, idField;
    private boolean success = false;

    public AddDoctorDialog(Frame parent) {
        super(parent, "Add New Doctor", true);
        setSize(450, 520);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Theme.BG_COLOR);
        setLayout(new BorderLayout());

        add(Theme.createHeaderPanel("Add New Doctor"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        specialField = new JTextField();
        hospitalField = new JTextField();

        addFormField(content, gbc, "Doctor ID:", idField, row++);
        addFormField(content, gbc, "Name:", nameField, row++);
        addFormField(content, gbc, "Email:", emailField, row++);
        addFormField(content, gbc, "Specialization:", specialField, row++);
        addFormField(content, gbc, "Hospital:", hospitalField, row++);

        add(content, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton saveBtn = new JButton("Save Doctor");
        Theme.styleButton(saveBtn);
        saveBtn.addActionListener(e -> saveDoctor());

        JButton cancelBtn = new JButton("Cancel");
        Theme.styleDangerButton(cancelBtn);
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int row) {
        JLabel label = new JLabel(labelText);
        Theme.styleLabel(label);
        Theme.styleTextField(field);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private void saveDoctor() {
        if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()
                || idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill ID, Name, and Email");
            return;
        }

        Doctor d = new Doctor(
                idField.getText().trim(),
                nameField.getText().trim(),
                specialField.getText().trim(),
                hospitalField.getText().trim(),
                emailField.getText().trim(),
                "1234" // Default password
        );

        if (DoctorDAO.addDoctor(d)) {
            success = true;
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add doctor. ID or Email might already exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}
