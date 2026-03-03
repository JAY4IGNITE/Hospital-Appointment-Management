package com.hams.view;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewAppointmentsFrame extends JFrame {

        public ViewAppointmentsFrame() {
                setTitle("My Appointments");
                setSize(700, 300);
                setLocationRelativeTo(null);

                String[] columns = {
                                "Doctor Name",
                                "Date",
                                "Time",
                                "Symptoms",
                                "Status"
                };

                DefaultTableModel model = new DefaultTableModel(columns, 0);

                List<Appointment> appointments;
                com.hams.model.User user = com.hams.util.SessionManager.getUser();

                if (user != null && "PATIENT".equalsIgnoreCase(user.getRole())) {
                        if (user instanceof com.hams.model.Patient) {
                                appointments = AppointmentDAO
                                                .getAppointmentsByPatient(((com.hams.model.Patient) user).getName());
                        } else {
                                appointments = AppointmentDAO.getAllAppointments(); // Fallback
                        }
                } else {
                        appointments = AppointmentDAO.getAllAppointments();
                }

                for (Appointment a : appointments) {
                        model.addRow(new Object[] {
                                        a.getDoctorName(),
                                        a.getDate(),
                                        a.getTimeSlot(),
                                        a.getSymptoms(),
                                        a.getStatus()
                        });
                }

                JTable table = new JTable(model);
                table.setRowHeight(28);

                add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                JButton backBtn = new JButton("Back");
                com.hams.util.Theme.styleButton(backBtn);
                backBtn.addActionListener(e -> dispose());
                buttonPanel.add(backBtn);

                add(buttonPanel, java.awt.BorderLayout.SOUTH);
                setVisible(true);
        }
}
