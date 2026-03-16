package com.hams.view;

import com.hams.dao.PatientDAO;
import com.hams.model.Patient;
import com.hams.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPatientFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminPatientFrame() {
        Theme.setupFrame(this, "Manage Patients", 800, 600);
        setLayout(new BorderLayout());

        add(Theme.createHeaderPanel("Patient Management"), BorderLayout.NORTH);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolbar.setOpaque(false);

        JButton deleteBtn = new JButton("Delete Patient");
        Theme.styleDangerButton(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        Theme.styleButton(refreshBtn);

        toolbar.add(deleteBtn);
        toolbar.add(refreshBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(toolbar, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Email" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        Theme.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Actions
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a patient to delete");
                return;
            }
            String id = table.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Delete patient " + id + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (PatientDAO.deletePatient(id)) {
                    JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete patient.");
                }
            }
        });

        refreshBtn.addActionListener(e -> loadData());

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Patient> patients = PatientDAO.getAllPatients();
        for (Patient p : patients) {
            model.addRow(new Object[] {
                    p.getPatientId(),
                    p.getName(),
                    p.getEmail()
            });
        }
    }
}
