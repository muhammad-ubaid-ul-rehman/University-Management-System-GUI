package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class FacultyLeave extends JPanel implements ActionListener {

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
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);

    JComboBox<String> choiceEmpId, choiceTime;
    JDateChooser selDate;
    JButton submit, cancel;
    CardLayout cardLayout;
    JPanel contentPanel;
    JLabel nameValueLabel;

    public FacultyLeave(CardLayout cardLayout, JPanel contentPanel) {
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
        bg.setLayout(new GridBagLayout());
        add(bg);

        // Center card
        JPanel card = buildCard();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        bg.add(card, gbc);
    }

    private JPanel buildCard() {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 24, 24);
                g2.setColor(BORDER_COL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 24, 24);
            }
        };
        card.setLayout(null);
        card.setPreferredSize(new Dimension(520, 580));
        card.setOpaque(false);

        // Icon + Title
        JPanel titleBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, ACCENT, getWidth(), 0, NAVY));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
            }
        };
        titleBar.setLayout(null);
        titleBar.setBounds(0, 0, 520, 70);
        titleBar.setOpaque(false);
        card.add(titleBar);

        JLabel icon = new JLabel("📋");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(22, 16, 44, 38);
        titleBar.add(icon);

        JLabel title = new JLabel("Apply Leave — Faculty");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 18, 380, 34);
        titleBar.add(title);

        // Fields
        int y = 95;

        addFL(card, "Employee ID *", 40, y);
        choiceEmpId = addSC(card, new String[]{}, 40, y+24, 440);
        y += 82;

        addFL(card, "Employee Name", 40, y);
        nameValueLabel = new JLabel("—");
        nameValueLabel.setBounds(40, y+24, 440, 40);
        nameValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameValueLabel.setForeground(ACCENT);
        nameValueLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1, true),
                new EmptyBorder(8, 14, 8, 14)));
        nameValueLabel.setBackground(new Color(235, 245, 255));
        nameValueLabel.setOpaque(true);
        card.add(nameValueLabel);
        y += 82;

        addFL(card, "Leave Date *", 40, y);
        selDate = new JDateChooser();
        selDate.setBounds(40, y+24, 440, 44);
        selDate.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        card.add(selDate);
        y += 88;

        addFL(card, "Duration *", 40, y);
        choiceTime = addSC(card, new String[]{"Full Day","Half Day"}, 40, y+24, 440);
        y += 88;

        // Buttons
        submit = buildBtn("✔  Submit Leave", ACCENT, ACCENT_HOV, 40, y, 200, 44);
        submit.addActionListener(this);
        card.add(submit);

        cancel = buildBtn("✕  Cancel", DANGER, new Color(160,40,30), 260, y, 200, 44);
        cancel.addActionListener(this);
        card.add(cancel);

        // Load data
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT Emp_ID FROM teacher");
            while (rs.next()) choiceEmpId.addItem(rs.getString("Emp_ID"));
        } catch (Exception e) { e.printStackTrace(); }

        choiceEmpId.addItemListener(ie -> updateName((String)choiceEmpId.getSelectedItem()));
        if (choiceEmpId.getItemCount() > 0) { choiceEmpId.setSelectedIndex(0); updateName((String)choiceEmpId.getSelectedItem()); }

        return card;
    }

    private void updateName(String empId) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT Name FROM teacher WHERE Emp_ID='" + empId + "'");
            nameValueLabel.setText(rs.next() ? rs.getString("Name") : "Not Found");
        } catch (Exception ex) { nameValueLabel.setText("Error"); }
    }

    private void addFL(JPanel p, String t, int x, int y) { JLabel l=new JLabel(t); l.setFont(FONT_LBL); l.setForeground(LABEL_COL); l.setBounds(x,y,440,18); p.add(l); }

    private JComboBox<String> addSC(JPanel p, String[] items, int x, int y, int w) {
        JComboBox<String> cb=new JComboBox<>(items); cb.setFont(FONT_FIELD); cb.setBackground(Color.WHITE); cb.setForeground(LABEL_COL);
        cb.setBounds(x,y,w,44); cb.setBorder(BorderFactory.createLineBorder(BORDER_COL,2,true)); p.add(cb); return cb;
    }

    private JButton buildBtn(String text, Color base, Color hover, int x, int y, int w, int h) {
        JButton btn=new JButton(text){protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g;g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g2.setColor(getModel().isRollover()?hover:base);g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);super.paintComponent(g);}};
        btn.setFont(FONT_BTN);btn.setForeground(Color.WHITE);btn.setBounds(x,y,w,h);btn.setBorder(new EmptyBorder(0,0,0,0));btn.setContentAreaFilled(false);btn.setFocusPainted(false);btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submit) {
            String empId=(String)choiceEmpId.getSelectedItem();
            java.util.Date selectedDate=selDate.getDate();
            String duration=(String)choiceTime.getSelectedItem();
            if(empId==null||selectedDate==null){JOptionPane.showMessageDialog(this,"All fields are required!"); return;}
            try {
                Conn c=new Conn(); PreparedStatement pst=c.prepare("INSERT INTO teacherleave (Emp_ID, Leave_Date, Duration) VALUES (?,?,?)");
                pst.setString(1,empId); pst.setString(2,new SimpleDateFormat("yyyy-MM-dd").format(selectedDate)); pst.setString(3,duration);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this,"✅ Leave Applied Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(contentPanel,"default");
            } catch(Exception ex){ex.printStackTrace(); JOptionPane.showMessageDialog(this,"❌ Error: "+ex.getMessage());}
        } else if(e.getSource()==cancel) cardLayout.show(contentPanel,"default");
    }
}
