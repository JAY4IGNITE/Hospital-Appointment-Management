package com.hams.view;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewAppointmentsFrame extends JFrame {

        public ViewAppointmentsFrame() {
                Theme.setupFrame(this, "My Appointments", 750, 500);
                setLayout(new BorderLayout());

                add(Theme.createHeaderPanel("My Appointments"), BorderLayout.NORTH);

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
                Theme.styleTable(table);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.getViewport().setBackground(Theme.BG_COLOR);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                add(scrollPane, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                JButton backBtn = new JButton("Back");
                Theme.styleButton(backBtn);
                backBtn.addActionListener(e -> dispose());
                buttonPanel.add(backBtn);

                add(buttonPanel, BorderLayout.SOUTH);
                setVisible(true);
        }
}
