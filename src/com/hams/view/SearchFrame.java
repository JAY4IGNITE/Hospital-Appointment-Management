package com.hams.view;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import com.hams.util.SessionManager;
import com.hams.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFrame extends JFrame {

    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchFrame() {
        Theme.setupFrame(this, "Search Appointments", 850, 550);
        setLayout(new BorderLayout());

        // 🎨 Header
        add(Theme.createHeaderPanel("Find Appointments"), BorderLayout.NORTH);

        // 🔍 Search Bar Panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel promptLabel = new JLabel("Search By:");
        Theme.styleLabel(promptLabel);
        promptLabel.setDisplayedMnemonic(KeyEvent.VK_B);

        // Context-aware search options
        boolean isPatient = SessionManager.getUser() != null && "PATIENT".equalsIgnoreCase(SessionManager.getUser().getRole());
        String[] searchOptions;
        
        if (isPatient) {
            searchOptions = new String[]{"Doctor Name", "Date (YYYY-MM-DD)", "Symptoms"};
        } else {
            searchOptions = new String[]{"Patient Name", "Doctor Name", "Date (YYYY-MM-DD)"};
        }

        searchTypeCombo = new JComboBox<>(searchOptions);
        promptLabel.setLabelFor(searchTypeCombo);
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchTypeCombo.setPreferredSize(new Dimension(180, 42));
        searchTypeCombo.setBackground(Color.WHITE);
        searchTypeCombo.setBorder(BorderFactory.createCompoundBorder(
                new Theme.RoundedBorder(new Color(150, 150, 150), 20), 
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));

        searchField = new JTextField();
        Theme.styleTextField(searchField);
        searchField.setPreferredSize(new Dimension(250, 42));

        JButton searchBtn = new JButton("Search");
        Theme.styleButton(searchBtn);
        searchBtn.setMnemonic(KeyEvent.VK_S);
        searchBtn.setPreferredSize(new Dimension(120, 42));

        JButton clearBtn = new JButton("Reset");
        Theme.styleButton(clearBtn);
        clearBtn.setMnemonic(KeyEvent.VK_R);
        clearBtn.setPreferredSize(new Dimension(120, 42));

        gbc.gridx = 0; gbc.gridy = 0;
        searchPanel.add(promptLabel, gbc);

        gbc.gridx = 1;
        searchPanel.add(searchTypeCombo, gbc);

        gbc.gridx = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchField, gbc);

        gbc.gridx = 3; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        searchPanel.add(searchBtn, gbc);

        gbc.gridx = 4;
        searchPanel.add(clearBtn, gbc);

        // 📊 Results Table
        String[] columnNames = { "Patient Name", "Doctor Name", "Date", "Time", "Symptoms", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        Theme.styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.getViewport().setBackground(Theme.BG_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Combine Search Panel and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action Listeners
        searchBtn.addActionListener(e -> performSearch());
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            loadInitialData();
        });
        searchTypeCombo.addActionListener(e -> performSearch());
        
        // Debounce timer for real-time search
        Timer debounceTimer = new Timer(400, e -> performSearch());
        debounceTimer.setRepeats(false);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { debounceTimer.restart(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { debounceTimer.restart(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { debounceTimer.restart(); }
        });
        
        // Add Enter key support for quick searching
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    debounceTimer.stop();
                    performSearch();
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

        // Load initially
        loadInitialData();
    }

    private List<Appointment> getContextAppointments() {
        if (SessionManager.getUser() != null) {
            String role = SessionManager.getUser().getRole();
            if ("PATIENT".equalsIgnoreCase(role) && SessionManager.getUser() instanceof Patient) {
                Patient p = (Patient) SessionManager.getUser();
                return AppointmentDAO.getAppointmentsByPatient(p.getName());
            } else if ("DOCTOR".equalsIgnoreCase(role) && SessionManager.getUser() instanceof Doctor) {
                Doctor d = (Doctor) SessionManager.getUser();
                return AppointmentDAO.getAppointmentsByDoctor(d.getName());
            }
        }
        return AppointmentDAO.getAllAppointments();
    }

    private void loadInitialData() {
        populateTable(getContextAppointments());
    }

    private void populateTable(List<Appointment> results) {
        tableModel.setRowCount(0); // Clear previous results
        for (Appointment app : results) {
            tableModel.addRow(new Object[] {
                    app.getPatientName(),
                    app.getDoctorName(),
                    app.getDate(),
                    app.getTimeSlot(),
                    app.getSymptoms(),
                    app.getStatus() != null ? app.getStatus() : "N/A"
            });
        }
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            loadInitialData();
            return;
        }

        // Fetch user-context specific data directly to bypass potentially limited DAO search
        List<Appointment> results = getContextAppointments();
        String searchType = (String) searchTypeCombo.getSelectedItem();
        
        results = results.stream().filter(app -> {
            String lowerQuery = query.toLowerCase();
            if ("Doctor Name".equals(searchType)) {
                return app.getDoctorName() != null && app.getDoctorName().toLowerCase().contains(lowerQuery);
            } else if ("Patient Name".equals(searchType)) {
                return app.getPatientName() != null && app.getPatientName().toLowerCase().contains(lowerQuery);
            } else if ("Date (YYYY-MM-DD)".equals(searchType)) {
                return app.getDate() != null && app.getDate().contains(query);
            } else if ("Symptoms".equals(searchType)) {
                return app.getSymptoms() != null && app.getSymptoms().toLowerCase().contains(lowerQuery);
            }
            return true;
        }).collect(Collectors.toList());

        populateTable(results);
    }
}
