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
        
        // Full screen for everything except Login and Registration pages
        if (!title.toLowerCase().contains("login") && !title.toLowerCase().contains("registration")) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(null); // Used primarily by Login/Signup, dashboards override this
    }

    public static void styleButton(JButton btn) {
        styleButtonWithColor(btn, SECONDARY);
    }

    public static void styleDangerButton(JButton btn) {
        styleButtonWithColor(btn, DANGER);
    }

    public static void styleSuccessButton(JButton btn) {
        styleButtonWithColor(btn, ACCENT);
    }

    private static void styleButtonWithColor(JButton btn, Color color) {
        btn.setFont(BOLD_FONT);
        btn.setBackground(color);
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

        // Add Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
    }

    // Custom Rounded Border Class
    public static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        private Color color;

        public RoundedBorder(Color color, int radius) {
            this.radius = radius;
            this.color = color;
        }

        public java.awt.Insets getBorderInsets(java.awt.Component c) {
            // Using a massive radius as inset collapses the text box height!
            // Provide a small physical stroke bounds margin instead.
            return new java.awt.Insets(2, 5, 2, 5);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new java.awt.BasicStroke(2.0f));
            g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
            g2.dispose();
        }
    }

    public static void styleTextField(JTextField field) {
        field.setFont(NORMAL_FONT);
        field.setOpaque(true); // Important: must be true to render the background properly
        field.setBackground(new Color(250, 250, 250)); // Slightly off-white for contrast
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(150, 150, 150), 20), // 20px custom rounded light gray/black border
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public static void styleHeader(JLabel label) {
        label.setFont(HEADER_FONT);
        label.setForeground(PRIMARY);
    }

    public static void styleTable(javax.swing.JTable table) {
        table.setRowHeight(30);
        table.setFont(NORMAL_FONT);
        table.setSelectionBackground(new Color(213, 245, 227));
        table.setShowGrid(false);
        table.setIntercellSpacing(new java.awt.Dimension(0, 0));

        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(WHITE);
        header.setFont(BOLD_FONT);
        header.setPreferredSize(new java.awt.Dimension(100, 35));
    }

    public static javax.swing.JPanel createHeaderPanel(String titleText) {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(PRIMARY);
        panel.setPreferredSize(new java.awt.Dimension(100, 60));
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 15));

        JLabel label = new JLabel(titleText);
        label.setFont(HEADER_FONT);
        label.setForeground(WHITE);
        panel.add(label);
        return panel;
    }
}
