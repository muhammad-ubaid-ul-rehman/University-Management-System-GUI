package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class Register extends JFrame {
    JTextField usernameField, securityField, answerField, dobField;
    JPasswordField passwordField, confirmPasswordField;
    JLabel showPass1, showPass2;

    public Register() {
        setTitle("Register - University Management System");
        setSize(620, 750);
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
                return new Dimension(550, 740);
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
        cardPanel.setSize(520, 720);
        cardPanel.setLocation((centerPanel.getWidth() - 520) / 2, (centerPanel.getHeight() - 720) / 2);
        cardPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        // Title
        JLabel title = new JLabel("Create Your Account", SwingConstants.CENTER);
        title.setForeground(new Color(20, 33, 61));
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(30, 10, 460, 40);
        cardPanel.add(title);

        // Subtitle
        JLabel subtitle = new JLabel("Register to join the system", SwingConstants.CENTER);
        subtitle.setForeground(new Color(100, 120, 150));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setBounds(30, 55, 460, 20);
        cardPanel.add(subtitle);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(new Color(20, 33, 61));
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setBounds(40, 100, 440, 20);
        cardPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(40, 125, 440, 45);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        usernameField.setBackground(new Color(245, 248, 252));
        usernameField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(new Color(20, 33, 61));
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setBounds(40, 185, 440, 20);
        cardPanel.add(passLabel);

        // Password field with icon and toggle button
        JPanel passFieldPanel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 248, 252));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        passFieldPanel.setBounds(40, 210, 440, 45);
        passFieldPanel.setOpaque(false);
        passFieldPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
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
        passwordField.setCaretColor(new Color(20, 33, 61));
        passFieldPanel.add(passwordField, BorderLayout.CENTER);

        // Show/Hide password button for password field
        JButton togglePassButton = new JButton("👁");
        togglePassButton.setFont(new Font("Arial", Font.PLAIN, 14));
        togglePassButton.setBorder(null);
        togglePassButton.setOpaque(false);
        togglePassButton.setFocusPainted(false);
        togglePassButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        togglePassButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '●') {
                passwordField.setEchoChar((char) 0);
                togglePassButton.setText("👁‍🗨");
            } else {
                passwordField.setEchoChar('●');
                togglePassButton.setText("👁");
            }
        });
        passFieldPanel.add(togglePassButton, BorderLayout.EAST);
        cardPanel.add(passFieldPanel);

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setForeground(new Color(20, 33, 61));
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        confirmLabel.setBounds(40, 270, 440, 20);
        cardPanel.add(confirmLabel);

        // Confirm password field with icon and toggle button
        JPanel confirmFieldPanel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 248, 252));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        confirmFieldPanel.setBounds(40, 295, 440, 45);
        confirmFieldPanel.setOpaque(false);
        confirmFieldPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));

        JLabel confirmIcon = new JLabel("🔒");
        confirmIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmFieldPanel.add(confirmIcon, BorderLayout.WEST);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordField.setBorder(null);
        confirmPasswordField.setOpaque(false);
        confirmPasswordField.setForeground(new Color(30, 40, 70));
        confirmPasswordField.setCaretColor(new Color(20, 33, 61));
        confirmFieldPanel.add(confirmPasswordField, BorderLayout.CENTER);

        // Show/Hide password button for confirm password field
        JButton toggleConfirmButton = new JButton("👁");
        toggleConfirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        toggleConfirmButton.setBorder(null);
        toggleConfirmButton.setOpaque(false);
        toggleConfirmButton.setFocusPainted(false);
        toggleConfirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleConfirmButton.addActionListener(e -> {
            if (confirmPasswordField.getEchoChar() == '●') {
                confirmPasswordField.setEchoChar((char) 0);
                toggleConfirmButton.setText("👁‍🗨");
            } else {
                confirmPasswordField.setEchoChar('●');
                toggleConfirmButton.setText("👁");
            }
        });
        confirmFieldPanel.add(toggleConfirmButton, BorderLayout.EAST);
        cardPanel.add(confirmFieldPanel);

        // Security Question
        JLabel secLabel = new JLabel("Security Question");
        secLabel.setForeground(new Color(20, 33, 61));
        secLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        secLabel.setBounds(40, 355, 440, 20);
        cardPanel.add(secLabel);

        securityField = new JTextField();
        securityField.setBounds(40, 380, 440, 45);
        securityField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        securityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        securityField.setBackground(new Color(245, 248, 252));
        securityField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(securityField);

        // Answer
        JLabel ansLabel = new JLabel("Security Answer");
        ansLabel.setForeground(new Color(20, 33, 61));
        ansLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        ansLabel.setBounds(40, 440, 440, 20);
        cardPanel.add(ansLabel);

        answerField = new JTextField();
        answerField.setBounds(40, 465, 440, 45);
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
        dobLabel.setBounds(40, 525, 440, 20);
        cardPanel.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(40, 550, 440, 45);
        dobField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dobField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        dobField.setBackground(new Color(245, 248, 252));
        dobField.setCaretColor(new Color(20, 33, 61));
        cardPanel.add(dobField);

        // Register Button
        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(40, 600, 440, 50);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBackground(new Color(20, 130, 200));
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerBtn.setBackground(new Color(15, 110, 170));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerBtn.setBackground(new Color(20, 130, 200));
            }
        });
        
        registerBtn.addActionListener(e -> registerUser());
        cardPanel.add(registerBtn);

        // Back to Login Link
        JLabel backToLogin = new JLabel("Already have an account?");
        backToLogin.setBounds(40, 665, 180, 20);
        backToLogin.setForeground(new Color(100, 120, 150));
        backToLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cardPanel.add(backToLogin);

        JLabel loginLink = new JLabel("Login here");
        loginLink.setBounds(240, 665, 220, 20);
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
                    int x = (centerPanel.getWidth() - 520) / 2;
                    int y = (centerPanel.getHeight() - 720) / 2;
                    if (x < 0) x = 10;
                    if (y < 0) y = 10;
                    cardPanel.setLocation(x, y);
                });
            }
        });
    }

    private void registerUser() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());
        String sec = securityField.getText();
        String ans = answerField.getText();
        String dob = dobField.getText();

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        try {
            Conn c = new Conn();
            String query = "INSERT INTO login(username, password, security_question, answer, dob) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, pass);
            pst.setString(3, sec);
            pst.setString(4, ans);
            pst.setString(5, dob);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account Registered Successfully");
            setVisible(false);
            new Login();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Username may already exist");
        }
    }
}
