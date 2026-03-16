package com.hams.view;

import com.hams.util.Theme;
import javax.swing.*;
import java.awt.*;

public class PaymentDialog extends JDialog {
    private boolean isPaymentSuccessful = false;

    public PaymentDialog(JFrame parent) {
        super(parent, "Secure Payment Gateway", true);
        setSize(550, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_COLOR);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Theme.PRIMARY);
        JLabel title = new JLabel("Appointment Payment");
        title.setForeground(Theme.WHITE);
        title.setFont(Theme.HEADER_FONT);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        bodyPanel.setBackground(Theme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel warningLabel = new JLabel("Pay to confirm your booking:");
        warningLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        warningLabel.setForeground(Theme.DANGER);
        
        JLabel amountLabel = new JLabel("Consultation Fee:");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        amountLabel.setForeground(Theme.PRIMARY);
        
        JLabel amountValue = new JLabel("$50.00");
        amountValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        amountValue.setForeground(Theme.ACCENT);

        JLabel cardLabel = new JLabel("Card Number:");
        Theme.styleLabel(cardLabel);
        JTextField cardField = new JTextField();
        Theme.styleTextField(cardField);
        cardField.setPreferredSize(new Dimension(250, 40));

        JLabel expiryLabel = new JLabel("Expiry (MM/YY):");
        Theme.styleLabel(expiryLabel);
        JTextField expiryField = new JTextField();
        Theme.styleTextField(expiryField);
        expiryField.setPreferredSize(new Dimension(250, 40));

        JLabel cvvLabel = new JLabel("CVV:");
        Theme.styleLabel(cvvLabel);
        JPasswordField cvvField = new JPasswordField();
        Theme.styleTextField(cvvField);
        cvvField.setPreferredSize(new Dimension(250, 40));

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        bodyPanel.add(warningLabel, gbc);
        
        gbc.gridy = ++y; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        bodyPanel.add(amountLabel, gbc);
        gbc.gridx = 1; 
        bodyPanel.add(amountValue, gbc);
        
        gbc.gridy = ++y; gbc.gridx = 0;
        bodyPanel.add(cardLabel, gbc);
        gbc.gridx = 1;
        bodyPanel.add(cardField, gbc);
        
        gbc.gridy = ++y; gbc.gridx = 0;
        bodyPanel.add(expiryLabel, gbc);
        gbc.gridx = 1;
        bodyPanel.add(expiryField, gbc);
        
        gbc.gridy = ++y; gbc.gridx = 0;
        bodyPanel.add(cvvLabel, gbc);
        gbc.gridx = 1;
        bodyPanel.add(cvvField, gbc);

        add(bodyPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(Theme.BG_COLOR);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton payBtn = new JButton("Pay $50.00");
        payBtn.setPreferredSize(new Dimension(150, 40));
        Theme.styleButton(payBtn);
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 40));
        Theme.styleDangerButton(cancelBtn);

        btnPanel.add(payBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        payBtn.addActionListener(e -> {
            if (cardField.getText().trim().isEmpty() || 
                expiryField.getText().trim().isEmpty() || 
                new String(cvvField.getPassword()).trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all payment details properly.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment of $50.00 Successful!", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
                isPaymentSuccessful = true;
                dispose();
            }
        });

        cancelBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment cancelled. Booking was not confirmed.", "Payment Cancelled", JOptionPane.WARNING_MESSAGE);
            isPaymentSuccessful = false;
            dispose();
        });
    }

    public boolean isPaymentSuccessful() {
        return isPaymentSuccessful;
    }
}
