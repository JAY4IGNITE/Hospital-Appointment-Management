package com.hams.view;

import com.hams.dao.PatientDAO;
import com.hams.model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPatientFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminPatientFrame() {
        setTitle("Manage Patients");
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Toolbar
        JPanel toolbar = new JPanel();
        JButton deleteBtn = new JButton("Delete Patient");
        JButton refreshBtn = new JButton("Refresh");

        toolbar.add(deleteBtn);
        toolbar.add(refreshBtn);
        add(toolbar, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Email" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
