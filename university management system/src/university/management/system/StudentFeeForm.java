package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class StudentFeeForm extends JPanel implements ActionListener {

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
    private static final Color WARNING    = new Color(230, 126, 34);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_VAL   = new Font("Segoe UI", Font.BOLD, 16);

    JComboBox<String> CrollNumber, programBox, departmentBox, semesterBox;
    JLabel totalAmount, paidAmountLabel, remainingAmountLabel;
    JLabel nameLabel, fatherLabel;
    JTextField paidField;
    JButton pay, update, back;
    CardLayout cardLayout;
    JPanel contentPanel;

    public StudentFeeForm(CardLayout cardLayout, JPanel contentPanel) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        setLayout(new BorderLayout());
        setBounds(0, 0, 1240, 850);

        JPanel bg = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), getHeight(), NAVY_DARK));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(null);
        add(bg);

        // ── Main card ────────────────────────────────────────────────────────
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        card.setLayout(null);
        card.setOpaque(false);
        card.setBounds(20, 20, 1200, 800);
        bg.add(card);

        // Title bar
        JPanel titleBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, ACCENT, getWidth(), 0, NAVY));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
            }
        };
        titleBar.setLayout(null);
        titleBar.setBounds(0, 0, 1200, 65);
        titleBar.setOpaque(false);
        card.add(titleBar);

        JLabel icon = new JLabel("💳");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(22, 14, 44, 38);
        titleBar.add(icon);

        JLabel title = new JLabel("Student Fee Payment Form");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 16, 500, 34);
        titleBar.add(title);

        // ── Left column: Student selection & academic info ─────────────────
        int lx = 40, lw = 360;

        addSL(card, "Student Information", lx, 80);

        addFL(card, "Enrollment No *", lx, 98);
        CrollNumber = new JComboBox<>();
        CrollNumber.setFont(FONT_FIELD); CrollNumber.setBackground(Color.WHITE); CrollNumber.setForeground(LABEL_COL);
        CrollNumber.setBounds(lx, 118, lw, 42);
        CrollNumber.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        card.add(CrollNumber);

        addFL(card, "Student Name", lx, 172);
        nameLabel = infoLabel(card, lx, 192, lw);

        addFL(card, "Father's Name", lx, 244);
        fatherLabel = infoLabel(card, lx, 264, lw);

        addSL(card, "Academic Details", lx, 326);

        addFL(card, "Program", lx, 344);
        programBox = addSC(card, new String[]{"BSCS","MSCS","BBA","MBA","BS Math","MS Math","B.Ed","M.Ed","BS Islamic Studies","MS Islamic Studies","BS Physics","MS Physics","BS Chemistry","MS Chemistry","BS Psychology","MS Psychology","BS English","MS English","BS Economics","MS Economics"}, lx, 364, lw);

        addFL(card, "Department", lx, 416);
        departmentBox = addSC(card, new String[]{"Computer Science","Business Administration","Mathematics","Education","Islamic Studies","Physics","Chemistry","Psychology","English","Economics"}, lx, 436, lw);

        addFL(card, "Semester", lx, 488);
        semesterBox = addSC(card, new String[]{"Semester1","Semester2","Semester3","Semester4","Semester5","Semester6","Semester7","Semester8"}, lx, 508, lw);

        // ── Right column: Fee summary card ───────────────────────────────────
        JPanel feeCard = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(235, 243, 252));
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.setColor(BORDER_COL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
            }
        };
        feeCard.setLayout(null);
        feeCard.setBounds(450, 80, 720, 430);
        feeCard.setOpaque(false);
        card.add(feeCard);

        JLabel feeTitle = new JLabel("Fee Summary");
        feeTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        feeTitle.setForeground(NAVY);
        feeTitle.setBounds(20, 15, 300, 28);
        feeCard.add(feeTitle);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 48, 680, 2);
        sep.setForeground(BORDER_COL);
        feeCard.add(sep);

        // Fee rows
        int fy = 62, fgap = 58;

        addFeeRow(feeCard, "💵  Total Payable Fee", fy);
        totalAmount = feeValueLabel(feeCard, fy + 24);

        addFeeRow(feeCard, "✅  Total Paid So Far", fy + fgap);
        paidAmountLabel = feeValueLabel(feeCard, fy + fgap + 24);
        paidAmountLabel.setForeground(SUCCESS);

        addFeeRow(feeCard, "⏳  Remaining Balance", fy + fgap*2);
        remainingAmountLabel = feeValueLabel(feeCard, fy + fgap*2 + 24);
        remainingAmountLabel.setForeground(DANGER);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(20, fy + fgap*3 + 10, 680, 2);
        sep2.setForeground(BORDER_COL);
        feeCard.add(sep2);

        addFeeRow(feeCard, "💳  Enter Amount to Pay", fy + fgap*3 + 18);
        paidField = new JTextField();
        paidField.setFont(FONT_FIELD);
        paidField.setBackground(Color.WHITE);
        paidField.setForeground(LABEL_COL);
        paidField.setCaretColor(ACCENT);
        paidField.setBounds(20, fy + fgap*3 + 42, 680, 44);
        paidField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 2, true),
                new EmptyBorder(5, 12, 5, 12)));
        paidField.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){paidField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT,2,true),new EmptyBorder(5,12,5,12)));}
            public void focusLost(FocusEvent e){paidField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COL,2,true),new EmptyBorder(5,12,5,12)));}
        });
        feeCard.add(paidField);

        // ── Action Buttons ─────────────────────────────────────────────────
        int btnY = 560;
        update = buildBtn("🔄  Calculate", WARNING, new Color(190,100,20), lx, btnY, 170, 44);
        update.addActionListener(this);
        card.add(update);

        pay = buildBtn("💳  Pay Now", SUCCESS, new Color(30,140,80), lx+190, btnY, 170, 44);
        pay.addActionListener(this);
        card.add(pay);

        back = buildBtn("← Back", new Color(100,120,150), new Color(80,100,130), lx+380, btnY, 130, 44);
        back.addActionListener(this);
        card.add(back);

        // ── Load data ────────────────────────────────────────────────────────
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student");
            while (rs.next()) CrollNumber.addItem(rs.getString("Enrollment_No"));
        } catch (Exception e) { e.printStackTrace(); }

        updateStudentNameFields();
        CrollNumber.addItemListener(ie -> updateStudentNameFields());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void addSL(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(MUTED); l.setBounds(x, y, 400, 14); p.add(l);
    }

    private void addFL(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(FONT_LBL); l.setForeground(LABEL_COL); l.setBounds(x, y, 360, 18); p.add(l);
    }

    private void addFeeRow(JPanel p, String t, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(MUTED); l.setBounds(20, y, 680, 18); p.add(l);
    }

    private JLabel infoLabel(JPanel p, int x, int y, int w) {
        JLabel l = new JLabel("—");
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(ACCENT);
        l.setBounds(x, y, w, 42);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        l.setBackground(Color.WHITE); l.setOpaque(true);
        p.add(l); return l;
    }

    private JLabel feeValueLabel(JPanel p, int y) {
        JLabel l = new JLabel("—");
        l.setFont(FONT_VAL);
        l.setForeground(LABEL_COL);
        l.setBounds(20, y, 680, 28);
        p.add(l); return l;
    }

    private JComboBox<String> addSC(JPanel p, String[] items, int x, int y, int w) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD); cb.setBackground(Color.WHITE); cb.setForeground(LABEL_COL);
        cb.setBounds(x, y, w, 42);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true)); p.add(cb); return cb;
    }

    private JButton buildBtn(String text, Color base, Color hover, int x, int y, int w, int h) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN); btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, w, h);
        btn.setBorder(new EmptyBorder(0,0,0,0)); btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return btn;
    }

    private void updateStudentNameFields() {
        try {
            Conn c = new Conn();
            String roll = (String) CrollNumber.getSelectedItem();
            if (roll == null) return;
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student WHERE Enrollment_No='" + roll + "'");
            if (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                fatherLabel.setText(rs.getString("Father_Name"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            String program  = (String) programBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            String rollno   = (String) CrollNumber.getSelectedItem();
            try {
                Conn c = new Conn();
                ResultSet rs = c.statement.executeQuery("SELECT * FROM fee WHERE program='" + program + "'");
                if (rs.next()) {
                    String total = rs.getString(semester);
                    totalAmount.setText("PKR " + total);
                    ResultSet rs2 = c.statement.executeQuery("SELECT SUM(CAST(paid_amount AS SIGNED)) AS paid_total FROM feecollege WHERE rollno='" + rollno + "' AND course='" + program + "' AND semester='" + semester + "'");
                    int paid = 0;
                    if (rs2.next()) paid = rs2.getInt("paid_total");
                    paidAmountLabel.setText("PKR " + paid);
                    int rem = Integer.parseInt(total) - paid;
                    remainingAmountLabel.setText("PKR " + Math.max(rem, 0));
                }
            } catch (Exception ex) { ex.printStackTrace(); }

        } else if (e.getSource() == pay) {
            String rollno   = (String) CrollNumber.getSelectedItem();
            String program  = (String) programBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            String dept     = (String) departmentBox.getSelectedItem();
            String total    = totalAmount.getText().replace("PKR ", "");
            String paid     = paidField.getText().trim();

            if (paid.isEmpty()) { JOptionPane.showMessageDialog(this, "Please enter the amount to pay."); return; }
            try {
                Conn c = new Conn();
                String Q = "INSERT INTO feecollege (rollno, course, department, semester, total, paid_amount) VALUES ('" + rollno + "','" + program + "','" + dept + "','" + semester + "','" + total + "','" + paid + "')";
                c.statement.executeUpdate(Q);
                JOptionPane.showMessageDialog(this, "✅ Payment Recorded Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                update.doClick();
                paidField.setText("");
            } catch (Exception ex) { ex.printStackTrace(); }

        } else if (e.getSource() == back) {
            cardLayout.show(contentPanel, "default");
        }
    }
}
