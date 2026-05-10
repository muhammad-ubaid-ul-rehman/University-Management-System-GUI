package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EnterMarks extends JPanel implements ActionListener, ItemListener {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color MUTED      = new Color(100, 120, 150);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Color INPUT_BG   = Color.WHITE;
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_COL   = new Font("Segoe UI", Font.BOLD, 11);

    JComboBox<String> choicerollno, comboBox;
    JTextField sub1, sub2, sub3, sub4, sub5;
    JTextField mrk1, mrk2, mrk3, mrk4, mrk5;
    JButton submit, cancel;
    JLabel nameValueLabel;
    CardLayout cardLayout;
    JPanel contentPanel;

    public EnterMarks(CardLayout cardLayout, JPanel contentPanel) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        setLayout(new BorderLayout());
        setBounds(0, 0, 1240, 850);

        // Gradient background
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

        // Main card
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
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
        card.setOpaque(false);
        card.setBounds(20, 20, 1200, 800);
        bg.add(card);

        // ── Title bar ─────────────────────────────────────────────────────────
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

        JLabel icon = new JLabel("📝");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(22, 15, 44, 36);
        titleBar.add(icon);

        JLabel title = new JLabel("Enter Student Marks");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 17, 400, 34);
        titleBar.add(title);

        // ── Selection row ─────────────────────────────────────────────────────
        JPanel selPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        selPanel.setBackground(Color.WHITE);
        selPanel.setBounds(0, 65, 1200, 60);
        card.add(selPanel);

        selPanel.add(makeLabel("Roll No:"));
        choicerollno = styledCombo(new String[]{});
        choicerollno.setPreferredSize(new Dimension(180, 36));
        choicerollno.addItemListener(this);
        selPanel.add(choicerollno);

        selPanel.add(makeLabel("Name:"));
        nameValueLabel = new JLabel("—");
        nameValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameValueLabel.setForeground(ACCENT);
        nameValueLabel.setPreferredSize(new Dimension(200, 36));
        selPanel.add(nameValueLabel);

        selPanel.add(makeLabel("Semester:"));
        String[] sems = {"1st Semester","2nd Semester","3rd Semester","4th Semester",
                "5th Semester","6th Semester","7th Semester","8th Semester"};
        comboBox = styledCombo(sems);
        comboBox.setPreferredSize(new Dimension(160, 36));
        selPanel.add(comboBox);

        // ── Marks grid ────────────────────────────────────────────────────────
        // Column headers
        JLabel hSubject = colHeader("Subject Name");
        hSubject.setBounds(80, 140, 400, 32);
        card.add(hSubject);

        JLabel hMarks = colHeader("Marks Obtained");
        hMarks.setBounds(540, 140, 300, 32);
        card.add(hMarks);

        // 5 rows
        int[] rowY = {182, 236, 290, 344, 398};
        JTextField[] subFields = new JTextField[5];
        JTextField[] mrkFields = new JTextField[5];
        String[] labels = {"Subject 1","Subject 2","Subject 3","Subject 4","Subject 5"};

        for (int i = 0; i < 5; i++) {
            // Row number badge
            JLabel badge = new JLabel(String.valueOf(i + 1));
            badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
            badge.setForeground(Color.WHITE);
            badge.setHorizontalAlignment(SwingConstants.CENTER);
            badge.setOpaque(true);
            badge.setBackground(ACCENT);
            badge.setBounds(30, rowY[i] + 6, 34, 34);
            card.add(badge);

            // Subject field
            subFields[i] = marksField(card, labels[i], 80, rowY[i], 420);

            // Marks field
            mrkFields[i] = marksField(card, "e.g. 85", 540, rowY[i], 220);
        }
        sub1=subFields[0]; sub2=subFields[1]; sub3=subFields[2]; sub4=subFields[3]; sub5=subFields[4];
        mrk1=mrkFields[0]; mrk2=mrkFields[1]; mrk3=mrkFields[2]; mrk4=mrkFields[3]; mrk5=mrkFields[4];

        // ── Divider ───────────────────────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 455, 1140, 2);
        sep.setForeground(BORDER_COL);
        card.add(sep);

        // ── Buttons ───────────────────────────────────────────────────────────
        submit = buildBtn("✔  Submit Marks", ACCENT, ACCENT_HOV, 30, 475, 200, 44);
        submit.addActionListener(this);
        card.add(submit);

        cancel = buildBtn("✕  Cancel", DANGER, new Color(160,40,30), 250, 475, 160, 44);
        cancel.addActionListener(this);
        card.add(cancel);

        // ── Load roll numbers ──────────────────────────────────────────────────
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT Enrollment_No FROM student");
            while (rs.next()) choicerollno.addItem(rs.getString("Enrollment_No"));
        } catch (Exception e) { e.printStackTrace(); }

        if (choicerollno.getItemCount() > 0) fetchAndShowName((String) choicerollno.getSelectedItem());
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private JLabel makeLabel(String t) {
        JLabel l = new JLabel(t);
        l.setFont(FONT_LBL);
        l.setForeground(LABEL_COL);
        return l;
    }

    private JLabel colHeader(String t) {
        JLabel l = new JLabel(t);
        l.setFont(FONT_COL);
        l.setForeground(MUTED);
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COL));
        return l;
    }

    private JTextField marksField(JPanel p, String placeholder, int x, int y, int w) {
        JTextField f = new JTextField();
        f.setFont(FONT_FIELD);
        f.setForeground(LABEL_COL);
        f.setBackground(INPUT_BG);
        f.setCaretColor(ACCENT);
        f.setBounds(x, y, w, 40);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 2, true),
                new EmptyBorder(5, 10, 5, 10)));
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT,2,true),new EmptyBorder(5,10,5,10))); }
            public void focusLost(FocusEvent e)   { f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COL,2,true),new EmptyBorder(5,10,5,10))); }
        });
        p.add(f);
        return f;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setBackground(Color.WHITE);
        cb.setForeground(LABEL_COL);
        return cb;
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
        btn.setBorder(new EmptyBorder(0,0,0,0));
        btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void fetchAndShowName(String rollNo) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT Name FROM student WHERE Enrollment_No='" + rollNo + "'");
            nameValueLabel.setText(rs.next() ? rs.getString("Name") : "Not found");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == choicerollno) fetchAndShowName((String) choicerollno.getSelectedItem());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            try {
                Conn c = new Conn();
                String roll = (String) choicerollno.getSelectedItem();
                String sem  = (String) comboBox.getSelectedItem();
                String q1 = "INSERT INTO subject VALUES('" + roll + "','" + sem + "','" + sub1.getText() + "','" + sub2.getText() + "','" + sub3.getText() + "','" + sub4.getText() + "','" + sub5.getText() + "')";
                String q2 = "INSERT INTO marks   VALUES('" + roll + "','" + sem + "','" + mrk1.getText() + "','" + mrk2.getText() + "','" + mrk3.getText() + "','" + mrk4.getText() + "','" + mrk5.getText() + "')";
                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);
                JOptionPane.showMessageDialog(null, "✅ Marks Inserted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(contentPanel, "default");
            } catch (Exception ex) { ex.printStackTrace(); }
        } else if (e.getSource() == cancel) {
            cardLayout.show(contentPanel, "default");
        }
    }
}
