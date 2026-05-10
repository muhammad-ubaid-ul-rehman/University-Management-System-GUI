package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AddStudent extends JPanel implements ActionListener {

    // ─── Theme Constants (matches Desktop.java) ───────────────────────────────
    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color MUTED      = new Color(100, 120, 150);
    private static final Color SUCCESS    = new Color(39, 174, 96);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);

    JTextField textName, textFather, textCNIC, textPhone, textEmail, textAddress;
    JLabel enrollmentLabel;
    JDateChooser dobChooser, admissionDateChooser;
    JComboBox<String> departmentBox, programBox, semesterBox, genderBox;
    JButton submit, cancel;
    Random ran = new Random();
    private Runnable onCancelCallback;

    public AddStudent(Runnable onCancelCallback) {
        this.onCancelCallback = onCancelCallback;
        setLayout(new BorderLayout());
        setBounds(0, 0, 1240, 850);

        // ── Gradient background ──────────────────────────────────────────────
        JPanel bg = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), getHeight(), NAVY_DARK));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new BorderLayout());
        add(bg);

        // ── Scrollable center card ────────────────────────────────────────────
        JPanel card = buildCard();
        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(-125, -200, 0, 0);
        wrapper.add(scroll, gbc);
        bg.add(wrapper, BorderLayout.CENTER);
    }

    private JPanel buildCard() {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.setColor(BORDER_COL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        card.setLayout(null);
        card.setPreferredSize(new Dimension(1100, 700));
        card.setOpaque(false);

        // Title strip
        JPanel titleBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, ACCENT, getWidth(), 0, NAVY));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
            }
        };
        titleBar.setBounds(0, 0, 1100, 70);
        titleBar.setOpaque(false);
        titleBar.setLayout(null);
        card.add(titleBar);

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setBounds(30, 15, 50, 40);
        titleBar.add(icon);

        JLabel title = new JLabel("New Student Registration");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(90, 17, 500, 36);
        titleBar.add(title);

        // Section header helper
        int row1Y = 95, row2Y = 170, row3Y = 245, row4Y = 320, row5Y = 395, row6Y = 470;
        int c1 = 30, c2 = 350, c3 = 670;
        int w = 280;

        // — Row 1: Full Name | Father's Name | Enrollment No. ——————————————————
        addSectionLabel(card, "Personal Information", 30, 82);

        addFieldLabel(card, "Full Name *", c1, row1Y);
        textName = addStyledField(card, c1, row1Y + 22, w);

        addFieldLabel(card, "Father's Name *", c2, row1Y);
        textFather = addStyledField(card, c2, row1Y + 22, w);

        addFieldLabel(card, "Enrollment No.", c3, row1Y);
        enrollmentLabel = new JLabel("ENR" + Math.abs(ran.nextInt(9000) + 1000));
        enrollmentLabel.setBounds(c3, row1Y + 22, w, 40);
        enrollmentLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        enrollmentLabel.setForeground(ACCENT);
        enrollmentLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 2, true),
                new EmptyBorder(8, 12, 8, 12)));
        card.add(enrollmentLabel);

        // — Row 2: Date of Birth | CNIC | Phone ——————————————————
        addFieldLabel(card, "Date of Birth *", c1, row2Y);
        dobChooser = styledDateChooser(card, c1, row2Y + 22, w);

        addFieldLabel(card, "CNIC / B-Form", c2, row2Y);
        textCNIC = addStyledField(card, c2, row2Y + 22, w);

        addFieldLabel(card, "Phone Number", c3, row2Y);
        textPhone = addStyledField(card, c3, row2Y + 22, w);

        // — Row 3: Email | Address ————————————————————
        addFieldLabel(card, "Email Address", c1, row3Y);
        textEmail = addStyledField(card, c1, row3Y + 22, w);

        addFieldLabel(card, "Address", c2, row3Y);
        textAddress = addStyledField(card, c2, row3Y + 22, w);

        // — Row 4: Department | Program | Semester ————————————————————————————
        addSectionLabel(card, "Academic Information", 30, row4Y - 13);

        addFieldLabel(card, "Department *", c1, row4Y);
        String[] depts = {"Computer Science","Business Administration","Mathematics","Education",
                "Islamic Studies","Physics","Chemistry","Psychology","English","Economics"};
        departmentBox = addStyledCombo(card, depts, c1, row4Y + 22, w);

        addFieldLabel(card, "Program *", c2, row4Y);
        String[] programs = {"BSCS","MSCS","BSSE","MSSE","BBA","MBA","BS Math","MS Math",
                "B.Ed","M.Ed","BS Islamic Studies","MS Islamic Studies","BS Physics","MS Physics",
                "BS Chemistry","MS Chemistry","BS Psychology","MS Psychology","BS English","MS English",
                "BS Economics","MS Economics"};
        programBox = addStyledCombo(card, programs, c2, row4Y + 22, w);

        addFieldLabel(card, "Semester *", c3, row4Y);
        semesterBox = addStyledCombo(card, new String[]{"1st","2nd","3rd","4th","5th","6th","7th","8th"}, c3, row4Y + 22, w);

        // — Row 5: Gender | Admission Date ————————————————————————————————
        addFieldLabel(card, "Gender *", c1, row5Y);
        genderBox = addStyledCombo(card, new String[]{"Male","Female","Other"}, c1, row5Y + 22, w);

        addFieldLabel(card, "Admission Date *", c2, row5Y);
        admissionDateChooser = styledDateChooser(card, c2, row5Y + 22, w);

        // — Buttons ——————————————————————————————————————————————
        submit = buildButton("✔  Submit", ACCENT, ACCENT_HOV, 350, row6Y + 22, 160, 40);
        submit.addActionListener(this);
        card.add(submit);

        cancel = buildButton("✕  Cancel", DANGER, new Color(160, 40, 30), 530, row6Y + 22, 160, 40);
        cancel.addActionListener(e -> onCancelCallback.run());
        card.add(cancel);

        return card;
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private void addSectionLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(MUTED);
        l.setBounds(x, y, 400, 14);
        p.add(l);
    }

    private void addFieldLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LBL);
        l.setForeground(LABEL_COL);
        l.setBounds(x, y, 240, 18);
        p.add(l);
    }

    private JTextField addStyledField(JPanel p, int x, int y, int w) {
        JTextField f = new JTextField();
        f.setFont(FONT_FIELD);
        f.setForeground(LABEL_COL);
        f.setBackground(Color.WHITE);
        f.setCaretColor(ACCENT);
        f.setBounds(x, y, w, 40);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 2, true),
                new EmptyBorder(5, 10, 5, 10)));
        styleFocusBorder(f);
        p.add(f);
        return f;
    }

    private JComboBox<String> addStyledCombo(JPanel p, String[] items, int x, int y, int w) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setBackground(Color.WHITE);
        cb.setForeground(LABEL_COL);
        cb.setBounds(x, y, w, 40);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        p.add(cb);
        return cb;
    }

    private JDateChooser styledDateChooser(JPanel p, int x, int y, int w) {
        JDateChooser dc = new JDateChooser();
        dc.setBounds(x, y, w, 40);
        dc.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        p.add(dc);
        return dc;
    }

    private JButton buildButton(String text, Color base, Color hover, int x, int y, int w, int h) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, w, h);
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleFocusBorder(JTextField f) {
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT, 2, true),
                        new EmptyBorder(5, 10, 5, 10)));
            }
            public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COL, 2, true),
                        new EmptyBorder(5, 10, 5, 10)));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String Name = textName.getText();
            String Father_Name = textFather.getText();
            String Enrollment_No = enrollmentLabel.getText();
            String DOB = ((JTextField) dobChooser.getDateEditor().getUiComponent()).getText();
            String CNIC = textCNIC.getText();
            String Phone_No = textPhone.getText();
            String Email = textEmail.getText();
            String Address = textAddress.getText();
            String Department = (String) departmentBox.getSelectedItem();
            String Program = (String) programBox.getSelectedItem();
            String Semester = (String) semesterBox.getSelectedItem();
            String Gender = (String) genderBox.getSelectedItem();
            String AdmissionDate = ((JTextField) admissionDateChooser.getDateEditor().getUiComponent()).getText();
            try {
                Conn c = new Conn();
                String q = "INSERT INTO student (Enrollment_No, Name, Father_Name, DOB, CNIC, Phone_No, Email, Address, Department, Program, Semester, Gender, Admission_Date) " +
                        "VALUES ('" + Enrollment_No + "', '" + Name + "', '" + Father_Name + "', '" + DOB + "', '" + CNIC + "', '" + Phone_No + "', '" + Email + "', '" + Address + "', '" + Department + "', '" + Program + "', '" + Semester + "', '" + Gender + "', '" + AdmissionDate + "')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "✅ Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
