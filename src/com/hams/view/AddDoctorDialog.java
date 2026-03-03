package com.hams.view;

import com.hams.dao.DoctorDAO;
import com.hams.model.Doctor;
import javax.swing.*;
import java.awt.*;

public class AddDoctorDialog extends JDialog {

    private JTextField nameField, emailField, specialField, hospitalField, idField;
    private boolean success = false;

    public AddDoctorDialog(Frame parent) {
        super(parent, "Add New Doctor", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Doctor ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Specialization:"));
        specialField = new JTextField();
        add(specialField);

        add(new JLabel("Hospital:"));
        hospitalField = new JTextField();
        add(hospitalField);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> saveDoctor());
        add(saveBtn);
    }

    private void saveDoctor() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        Doctor d = new Doctor(
                idField.getText(),
                nameField.getText(),
                specialField.getText(),
                hospitalField.getText(),
                emailField.getText(),
                "1234" // Default password
        );

        if (DoctorDAO.addDoctor(d)) {
            success = true;
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add doctor. ID or Email might presumably exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}
