package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel errorLabel;
    JButton loginButton;
    JCheckBox showPasswordCheckbox;

    public Login() {
        setTitle("University Management System - Login");
        setSize(900, 700);
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
                return new Dimension(600, 600);
            }
        };
        centerPanel.setLayout(null);
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Centered white card container
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // White rounded background with shadow
                g2d.setColor(new Color(245, 248, 252));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                
                // Border
                g2d.setColor(new Color(200, 220, 240));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        cardPanel.setLayout(null);
        cardPanel.setSize(550, 590);
        cardPanel.setLocation((centerPanel.getWidth() - 550) / 2, (centerPanel.getHeight() - 590) / 2);
        cardPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        // Icon container with accent background
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Rounded accent background
                g2d.setColor(new Color(20, 130, 200, 30));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        iconPanel.setLayout(new BorderLayout());
        iconPanel.setBounds(235, 25, 80, 80);
        iconPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel("🏫", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        iconPanel.add(iconLabel, BorderLayout.CENTER);
        cardPanel.add(iconPanel);

        // Title
        JLabel titleLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(20, 33, 61));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setBounds(50, 120, 450, 45);
        cardPanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Access your university account", SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(100, 120, 150));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setBounds(50, 165, 450, 20);
        cardPanel.add(subtitleLabel);

        // Error message label (initially hidden)
        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(220, 50, 50));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setBounds(50, 200, 450, 30);
        errorLabel.setVisible(false);
        cardPanel.add(errorLabel);

        // Username label
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(new Color(20, 33, 61));
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setBounds(50, 240, 450, 20);
        cardPanel.add(userLabel);

        // Username field with icon
        JPanel userFieldPanel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        userFieldPanel.setBounds(50, 265, 450, 45);
        userFieldPanel.setOpaque(false);
        userFieldPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 220), 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        userFieldPanel.add(userIcon, BorderLayout.WEST);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(null);
        usernameField.setOpaque(false);
        usernameField.setForeground(new Color(30, 40, 70));
        usernameField.setCaretColor(new Color(20, 130, 200));
        userFieldPanel.add(usernameField, BorderLayout.CENTER);
        cardPanel.add(userFieldPanel);

        // Password label
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(new Color(20, 33, 61));
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setBounds(50, 330, 450, 20);
        cardPanel.add(passLabel);

        // Password field with icon and toggle
        JPanel passFieldPanel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        passFieldPanel.setBounds(50, 355, 450, 45);
        passFieldPanel.setOpaque(false);
        passFieldPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 220), 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));

        JLabel passIcon = new JLabel("🔒");
        passIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        passFieldPanel.add(passIcon, BorderLayout.WEST);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(null);
        passwordField.setOpaque(false);
        passwordField.setForeground(new Color(30, 40, 70));
        passwordField.setCaretColor(new Color(20, 130, 200));
        passFieldPanel.add(passwordField, BorderLayout.CENTER);
        cardPanel.add(passFieldPanel);

        // Show password checkbox
        showPasswordCheckbox = new JCheckBox("Show password");
        showPasswordCheckbox.setBounds(50, 410, 200, 25);
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.setForeground(new Color(80, 100, 140));
        showPasswordCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        showPasswordCheckbox.setFocusPainted(false);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('●');
            }
        });
        cardPanel.add(showPasswordCheckbox);

        // Forgot password link
        JLabel forgotLink = new JLabel("Forgot Password?");
        forgotLink.setBounds(350, 505, 150, 20);
        forgotLink.setForeground(new Color(20, 130, 200));
        forgotLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                forgotLink.setText("<html><u>Forgot Password?</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                forgotLink.setText("Forgot Password?");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new ForgetPassword();
            }
        });
        cardPanel.add(forgotLink);

        // Login button
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(50, 450, 450, 50);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(20, 130, 200));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setOpaque(true);

        // Button hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(15, 110, 170));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(20, 130, 200));
            }
        });

        loginButton.addActionListener(e -> authenticate());
        cardPanel.add(loginButton);

        // Divider line
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 535, 450, 1);
        separator.setBackground(new Color(200, 220, 240));
        cardPanel.add(separator);

        // Sign up section
        JLabel signupLabel = new JLabel("Don't have an account?");
        signupLabel.setBounds(50, 550, 180, 20);
        signupLabel.setForeground(new Color(100, 120, 150));
        signupLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cardPanel.add(signupLabel);

        JLabel signupLink = new JLabel("Register here");
        signupLink.setBounds(250, 550, 250, 20);
        signupLink.setForeground(new Color(20, 130, 200));
        signupLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signupLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signupLink.setText("<html><u>Register here</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                signupLink.setText("Register here");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new Register();
            }
        });
        cardPanel.add(signupLink);

        setVisible(true);

        // Update card position when window is resized or maximized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    centerPanel.setSize(mainPanel.getWidth(), mainPanel.getHeight());
                    int x = (centerPanel.getWidth() - 550) / 2;
                    int y = (centerPanel.getHeight() - 590) / 2;
                    if (x < 0) x = 10;
                    if (y < 0) y = 10;
                    cardPanel.setLocation(x, y);
                });
            }
        });
    }

    private void authenticate() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.trim().isEmpty() || pass.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM login WHERE BINARY username='" + user + "' AND BINARY password='" + pass + "'";
            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                setVisible(false);
                new Desktop();
            } else {
                showError("Invalid Username or Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error connecting to database");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}