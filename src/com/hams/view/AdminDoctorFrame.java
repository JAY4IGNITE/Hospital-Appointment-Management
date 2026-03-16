package com.hams.view;

import com.hams.dao.DoctorDAO;
import com.hams.model.Doctor;
import com.hams.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDoctorFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminDoctorFrame() {
        Theme.setupFrame(this, "Manage Doctors", 850, 600);
        setLayout(new BorderLayout());

        add(Theme.createHeaderPanel("Doctor Management"), BorderLayout.NORTH);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolbar.setOpaque(false);

        JButton addBtn = new JButton("Add Doctor");
        Theme.styleButton(addBtn);

        JButton deleteBtn = new JButton("Delete Doctor");
        Theme.styleDangerButton(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        Theme.styleButton(refreshBtn);

        toolbar.add(addBtn);
        toolbar.add(deleteBtn);
        toolbar.add(refreshBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(toolbar, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Specialization", "Hospital", "Email" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        Theme.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Actions
        addBtn.addActionListener(e -> {
            AddDoctorDialog dialog = new AddDoctorDialog(this);
            dialog.setVisible(true);
            if (dialog.isSuccess())
                loadData();
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a doctor to delete");
                return;
            }
            String id = table.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Delete doctor " + id + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (DoctorDAO.deleteDoctor(id)) {
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete doctor.");
                }
            }
        });

        refreshBtn.addActionListener(e -> loadData());

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Doctor> doctors = DoctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            model.addRow(new Object[] {
                    d.getDoctorId(),
                    d.getName(),
                    d.getSpecialization(),
                    d.getHospital(),
                    d.getEmail()
            });
        }
    }
}
