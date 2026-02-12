package com.hams.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class Theme {

    // Colors
    public static final Color PRIMARY = new Color(44, 62, 80); // Navy Blue
    public static final Color SECONDARY = new Color(52, 152, 219); // Blue
    public static final Color ACCENT = new Color(46, 204, 113); // Green
    public static final Color DANGER = new Color(231, 76, 60); // Red
    public static final Color BG_COLOR = new Color(236, 240, 241); // Off-White
    public static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark Grey
    public static final Color WHITE = Color.WHITE;

    // Fonts
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(null); // Or use layouts, but setting BG is key
    }

    public static void styleButton(JButton btn) {
        btn.setFont(BOLD_FONT);
        btn.setBackground(SECONDARY);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                g2.dispose();
                super.paint(g, c); // Paint text/icon
            }
        });
    }

    public static void styleDangerButton(JButton btn) {
        styleButton(btn);
        btn.setBackground(DANGER);
    }

    public static void styleSuccessButton(JButton btn) {
        styleButton(btn);
        btn.setBackground(ACCENT);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(NORMAL_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public static void styleHeader(JLabel label) {
        label.setFont(HEADER_FONT);
        label.setForeground(PRIMARY);
    }
}
