package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AddFaculty extends JPanel implements ActionListener {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color MUTED      = new Color(100, 120, 150);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);

    JTextField textName, textFather, textAddress, textPhone, textEmail,
               textCNIC, textSpecialization, textExperience;
    JLabel empText;
    JDateChooser dobChooser, joiningDateChooser;
    JComboBox<String> qualificationBox, departmentBox, designationBox;
    JButton submit, cancel;
    Runnable onCancelCallback;

    private JPanel formPanel;

    public AddFaculty(Runnable onCancelCallback) {
        this.onCancelCallback = onCancelCallback;
        setLayout(new BorderLayout());
        setBounds(0, 0, 1240, 850);

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
        gbc.insets = new Insets(-125 , -200, 0, 0);
        wrapper.add(scroll, gbc);
        bg.add(wrapper, BorderLayout.CENTER);
    }

    private JPanel buildCard() {
        formPanel = new JPanel() {
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
        formPanel.setLayout(null);
        formPanel.setPreferredSize(new Dimension(1100, 700));
        formPanel.setOpaque(false);

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
        titleBar.setBounds(0, 0, 1100, 70);
        titleBar.setOpaque(false);
        titleBar.setLayout(null);
        formPanel.add(titleBar);

        JLabel icon = new JLabel("👨‍🏫");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setBounds(30, 15, 50, 40);
        titleBar.add(icon);

        JLabel title = new JLabel("Faculty Registration Portal");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(90, 17, 500, 36);
        titleBar.add(title);

        // Generate employee ID
        Random ran = new Random();
        String generatedEmpId = "1409" + Math.abs((ran.nextLong() % 9000L) + 1000L);

        // Row positions (3 columns)
        int r1 = 95, r2 = 170, r3 = 245, r4 = 320, r5 = 395, r6 = 470;
        int c1 = 30, c2 = 350, c3 = 670;
        int w = 280;

        addSL(formPanel, "Personal Information", 30, 82);

        // Row 1
        addFL(formPanel, "Full Name *", c1, r1);
        textName = addSF(formPanel, c1, r1+22, w);

        addFL(formPanel, "Father's Name", c2, r1);
        textFather = addSF(formPanel, c2, r1+22, w);

        addFL(formPanel, "Employee ID", c3, r1);
        empText = new JLabel(generatedEmpId);
        empText.setBounds(c3, r1+22, w, 40);
        empText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        empText.setForeground(ACCENT);
        empText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 2, true),
                new EmptyBorder(8, 12, 8, 12)));
        formPanel.add(empText);

        // Row 2
        addFL(formPanel, "Date of Birth", c1, r2);
        dobChooser = styledDC(formPanel, c1, r2+22, w);

        addFL(formPanel, "CNIC", c2, r2);
        textCNIC = addSF(formPanel, c2, r2+22, w);

        addFL(formPanel, "Phone", c3, r2);
        textPhone = addSF(formPanel, c3, r2+22, w);

        // Row 3
        addFL(formPanel, "Email", c1, r3);
        textEmail = addSF(formPanel, c1, r3+22, w);

        addFL(formPanel, "Address", c2, r3);
        textAddress = addSF(formPanel, c2, r3+22, w);

        // Row 4 - Academic
        addSL(formPanel, "Academic Information", 30, r4-13);

        // Row 5
        addFL(formPanel, "Qualification *", c1, r4);
        qualificationBox = addSC(formPanel, new String[]{"BS (Hons)","MS / M.Phil","PhD","MBA","Other"}, c1, r4+22, w);

        addFL(formPanel, "Department *", c2, r4);
        departmentBox = addSC(formPanel, new String[]{"Computer Science","Software Engineering","Electrical Engineering","Other"}, c2, r4+22, w);

        addFL(formPanel, "Designation *", c3, r4);
        designationBox = addSC(formPanel, new String[]{"Lecturer","Assistant Professor","Associate Professor","Professor"}, c3, r4+22, w);

        // Row 6
        addFL(formPanel, "Specialization", c1, r5);
        textSpecialization = addSF(formPanel, c1, r5+22, w);

        addFL(formPanel, "Experience (Years)", c2, r5);
        textExperience = addSF(formPanel, c2, r5+22, w);

        addFL(formPanel, "Joining Date", c3, r5);
        joiningDateChooser = styledDC(formPanel, c3, r5+22, w);

        // Buttons
        submit = buildBtn("✔  Submit", ACCENT, ACCENT_HOV, 350, r6+22, 160, 40);
        submit.addActionListener(this);
        formPanel.add(submit);

        cancel = buildBtn("✕  Cancel", DANGER, new Color(160,40,30), 530, r6+22, 160, 40);
        cancel.addActionListener(this);
        formPanel.add(cancel);

        return formPanel;
    }

    private void addSL(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(MUTED);
        l.setBounds(x, y, 400, 14);
        p.add(l);
    }

    private void addFL(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(FONT_LBL);
        l.setForeground(LABEL_COL);
        l.setBounds(x, y, 240, 18);
        p.add(l);
    }

    private JTextField addSF(JPanel p, int x, int y, int w) {
        JTextField f = new JTextField();
        f.setFont(FONT_FIELD);
        f.setForeground(LABEL_COL);
        f.setBackground(Color.WHITE);
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

    private JComboBox<String> addSC(JPanel p, String[] items, int x, int y, int w) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setBackground(Color.WHITE);
        cb.setForeground(LABEL_COL);
        cb.setBounds(x, y, w, 40);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        p.add(cb);
        return cb;
    }

    private JDateChooser styledDC(JPanel p, int x, int y, int w) {
        JDateChooser dc = new JDateChooser();
        dc.setBounds(x, y, w, 40);
        dc.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        p.add(dc);
        return dc;
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
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, w, h);
        btn.setBorder(new EmptyBorder(0,0,0,0));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            JOptionPane.showMessageDialog(this, "✅ Faculty Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == cancel && onCancelCallback != null) {
            onCancelCallback.run();
        }
    }
}
