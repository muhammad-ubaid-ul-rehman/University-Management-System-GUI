package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgetPassword extends JFrame {
    JTextField usernameField, answerField, dobField;
    JPasswordField newPasswordField;
    JCheckBox showPassword;

    public ForgetPassword() {
        setTitle("Forgot Password - University Management System");
        setSize(540, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main background with gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient from dark navy to blue
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 33, 61),
                        0, getHeight(), new Color(35, 70, 120)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);
        setContentPane(mainPanel);

        // Centered wrapper panel
        JPanel centerPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(480, 640);
            }
        };
        centerPanel.setLayout(null);
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Card panel for content
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(245, 248, 252));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                
                g2d.setColor(new Color(200, 220, 240));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        cardPanel.setLayout(null);
        cardPanel.setSize(460, 600);
        cardPanel.setLocation((centerPanel.getWidth() - 460) / 2, (centerPanel.getHeight() - 600) / 2);
        cardPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        // Title
        JLabel title = new JLabel("Recover Your Password", SwingConstants.CENTER);
        title.setForeground(new Color(20, 33, 61));
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(20, 20, 420, 40);
        cardPanel.add(title);

        // Subtitle
        JLabel subtitle = new JLabel("Answer security questions to reset password", SwingConstants.CENTER);
        subtitle.setForeground(new Color(100, 120, 150));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setBounds(20, 65, 420, 20);
        cardPanel.add(subtitle);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(new Color(20, 33, 61));
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setBounds(40, 110, 380, 20);
        cardPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(40, 135, 380, 45);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        usernameField.setBackground(new Color(245, 248, 252));
        usernameField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(usernameField);

        // Security Answer
        JLabel ansLabel = new JLabel("Security Answer");
        ansLabel.setForeground(new Color(20, 33, 61));
        ansLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        ansLabel.setBounds(40, 195, 380, 20);
        cardPanel.add(ansLabel);

        answerField = new JTextField();
        answerField.setBounds(40, 220, 380, 45);
        answerField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        answerField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        answerField.setBackground(new Color(245, 248, 252));
        answerField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(answerField);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD)");
        dobLabel.setForeground(new Color(20, 33, 61));
        dobLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        dobLabel.setBounds(40, 280, 380, 20);
        cardPanel.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(40, 305, 380, 45);
        dobField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dobField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        dobField.setBackground(new Color(245, 248, 252));
        dobField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(dobField);

        // New Password
        JLabel passLabel = new JLabel("New Password");
        passLabel.setForeground(new Color(20, 33, 61));
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setBounds(40, 365, 380, 20);
        cardPanel.add(passLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(40, 390, 380, 45);
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        newPasswordField.setBackground(new Color(245, 248, 252));
        newPasswordField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(newPasswordField);

        // Show Password Checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(40, 445, 150, 25);
        showPassword.setOpaque(false);
        showPassword.setForeground(new Color(100, 120, 150));
        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        showPassword.setFocusPainted(false);
        showPassword.addActionListener(e -> {
            newPasswordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022');
        });
        cardPanel.add(showPassword);

        // Reset Button
        JButton resetBtn = new JButton("RESET PASSWORD");
        resetBtn.setBounds(40, 490, 380, 50);
        resetBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setBackground(new Color(20, 130, 200));
        resetBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        resetBtn.setFocusPainted(false);
        resetBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetBtn.setBackground(new Color(15, 110, 170));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                resetBtn.setBackground(new Color(20, 130, 200));
            }
        });
        
        resetBtn.addActionListener(e -> resetPassword());
        cardPanel.add(resetBtn);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(40, 555, 380, 1);
        separator.setBackground(new Color(100, 150, 200));
        cardPanel.add(separator);

        // Back to Login Link
        JLabel backToLogin = new JLabel("Remember your password?");
        backToLogin.setBounds(40, 575, 200, 20);
        backToLogin.setForeground(new Color(100, 120, 150));
        backToLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cardPanel.add(backToLogin);

        JLabel loginLink = new JLabel("Login here");
        loginLink.setBounds(260, 575, 160, 20);
        loginLink.setForeground(new Color(20, 130, 200));
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setForeground(new Color(15, 110, 170));
                loginLink.setText("<html><u>Login here</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setForeground(new Color(20, 130, 200));
                loginLink.setText("Login here");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new Login();
            }
        });
        cardPanel.add(loginLink);

        setVisible(true);

        // Update card position when window is resized or maximized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    centerPanel.setSize(mainPanel.getWidth(), mainPanel.getHeight());
                    int x = (centerPanel.getWidth() - 460) / 2;
                    int y = (centerPanel.getHeight() - 600) / 2;
                    if (x < 0) x = 10;
                    if (y < 0) y = 10;
                    cardPanel.setLocation(x, y);
                });
            }
        });
    }

    private void resetPassword() {
        String username = usernameField.getText();
        String answer = answerField.getText();
        String dob = dobField.getText();
        String newPassword = new String(newPasswordField.getPassword());

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM login WHERE BINARY username='" + username + "' AND BINARY answer='" + answer + "' AND dob='" + dob + "'";
            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                String update = "UPDATE login SET password='" + newPassword + "' WHERE BINARY username='" + username + "'";
                c.statement.executeUpdate(update);
                JOptionPane.showMessageDialog(this, "Password reset successfully!");
                setVisible(false);
                new Login();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid information. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }
}