package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class updateStudent extends JPanel implements ActionListener {

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

    JComboBox<String> enrollmentDropdown;
    JTextField textName, textFather, textCNIC, textPhone, textEmail, textAddress;
    JDateChooser dobChooser, admissionDateChooser;
    JComboBox<String> departmentBox, programBox, semesterBox, genderBox;
    JButton updateBtn, cancelBtn;
    Conn c = new Conn();

    public updateStudent(Runnable onCancel) {
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

        JPanel card = buildCard(onCancel);
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

    private JPanel buildCard(Runnable onCancel) {
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
        card.setPreferredSize(new Dimension(1100, 730));
        card.setOpaque(false);

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

        JLabel icon = new JLabel("✏️");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(30, 18, 44, 36);
        titleBar.add(icon);

        JLabel title = new JLabel("Update Student Details");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(85, 17, 500, 36);
        titleBar.add(title);

        // Enrollment selector
        addSL(card, "Select Student", 30, 84);
        JLabel selLbl = new JLabel("Enrollment No:");
        selLbl.setFont(FONT_LBL);
        selLbl.setForeground(LABEL_COL);
        selLbl.setBounds(30, 100, 140, 18);
        card.add(selLbl);

        enrollmentDropdown = new JComboBox<>();
        enrollmentDropdown.setFont(FONT_FIELD);
        enrollmentDropdown.setBackground(Color.WHITE);
        enrollmentDropdown.setBounds(175, 97, 220, 38);
        enrollmentDropdown.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        card.add(enrollmentDropdown);

        JButton loadBtn = buildBtn("🔍 Load", ACCENT, ACCENT_HOV, 410, 97, 130, 38);
        card.add(loadBtn);
        loadBtn.addActionListener(e -> loadStudentData((String) enrollmentDropdown.getSelectedItem()));

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 148, 1040, 2);
        sep.setForeground(BORDER_COL);
        card.add(sep);

        int r1=160, r2=235, r3=310, r4=385;

        addSL(card, "Personal Information", 30, r1-13);

        addFL(card, "Full Name", 30, r1);
        textName = addSF(card, 30, r1+22, 240);

        addFL(card, "Father's Name", 310, r1);
        textFather = addSF(card, 310, r1+22, 240);

        addFL(card, "CNIC", 590, r1);
        textCNIC = addSF(card, 590, r1+22, 200);

        addFL(card, "Phone", 830, r1);
        textPhone = addSF(card, 830, r1+22, 200);

        addFL(card, "Email", 30, r2);
        textEmail = addSF(card, 30, r2+22, 240);

        addFL(card, "Address", 310, r2);
        textAddress = addSF(card, 310, r2+22, 240);

        addFL(card, "Date of Birth", 590, r2);
        dobChooser = styledDC(card, 590, r2+22, 200);

        addFL(card, "Admission Date", 830, r2);
        admissionDateChooser = styledDC(card, 830, r2+22, 200);

        addSL(card, "Academic Information", 30, r3-13);

        addFL(card, "Department", 30, r3);
        departmentBox = addSC(card, new String[]{"Computer Science","Electrical Engineering","Business Administration"}, 30, r3+22, 240);

        addFL(card, "Program", 310, r3);
        programBox = addSC(card, new String[]{"BSCS","BBA","BSEE"}, 310, r3+22, 240);

        addFL(card, "Semester", 590, r3);
        semesterBox = addSC(card, new String[]{"1st","2nd","3rd","4th","5th","6th","7th","8th"}, 590, r3+22, 200);

        addFL(card, "Gender", 830, r3);
        genderBox = addSC(card, new String[]{"Male","Female","Other"}, 830, r3+22, 200);

        // Buttons
        updateBtn = buildBtn("✔  Update", SUCCESS, new Color(30,140,80), 380, r4, 160, 40);
        updateBtn.addActionListener(this);
        card.add(updateBtn);

        cancelBtn = buildBtn("✕  Cancel", DANGER, new Color(160,40,30), 560, r4, 160, 40);
        cancelBtn.addActionListener(e -> onCancel.run());
        card.add(cancelBtn);

        loadEnrollmentNumbers();
        enrollmentDropdown.addActionListener(e -> loadStudentData((String) enrollmentDropdown.getSelectedItem()));

        return card;
    }

    private void addSL(JPanel p, String t, int x, int y) { JLabel l=new JLabel(t.toUpperCase()); l.setFont(new Font("Segoe UI",Font.BOLD,10)); l.setForeground(MUTED); l.setBounds(x,y,400,14); p.add(l); }
    private void addFL(JPanel p, String t, int x, int y) { JLabel l=new JLabel(t); l.setFont(FONT_LBL); l.setForeground(LABEL_COL); l.setBounds(x,y,240,18); p.add(l); }

    private JTextField addSF(JPanel p, int x, int y, int w) {
        JTextField f=new JTextField(); f.setFont(FONT_FIELD); f.setForeground(LABEL_COL); f.setBackground(Color.WHITE); f.setCaretColor(ACCENT);
        f.setBounds(x,y,w,40); f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COL,2,true),new EmptyBorder(5,10,5,10)));
        f.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT,2,true),new EmptyBorder(5,10,5,10)));}
            public void focusLost(FocusEvent e){f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COL,2,true),new EmptyBorder(5,10,5,10)));}
        });
        p.add(f); return f;
    }

    private JComboBox<String> addSC(JPanel p, String[] items, int x, int y, int w) {
        JComboBox<String> cb=new JComboBox<>(items); cb.setFont(FONT_FIELD); cb.setBackground(Color.WHITE); cb.setForeground(LABEL_COL);
        cb.setBounds(x,y,w,40); cb.setBorder(BorderFactory.createLineBorder(BORDER_COL,2,true)); p.add(cb); return cb;
    }

    private JDateChooser styledDC(JPanel p, int x, int y, int w) {
        JDateChooser dc=new JDateChooser(); dc.setBounds(x,y,w,40); dc.setBorder(BorderFactory.createLineBorder(BORDER_COL,2,true)); p.add(dc); return dc;
    }

    private JButton buildBtn(String text, Color base, Color hover, int x, int y, int w, int h) {
        JButton btn=new JButton(text){protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g;g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g2.setColor(getModel().isRollover()?hover:base);g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);super.paintComponent(g);}};
        btn.setFont(FONT_BTN);btn.setForeground(Color.WHITE);btn.setBounds(x,y,w,h);btn.setBorder(new EmptyBorder(0,0,0,0));btn.setContentAreaFilled(false);btn.setFocusPainted(false);btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return btn;
    }

    private void loadEnrollmentNumbers() {
        try { ResultSet rs=c.statement.executeQuery("SELECT Enrollment_No FROM student"); while(rs.next()) enrollmentDropdown.addItem(rs.getString("Enrollment_No")); } catch(Exception e){e.printStackTrace();}
    }

    private void loadStudentData(String enrollmentNo) {
        if(enrollmentNo==null) return;
        try {
            PreparedStatement ps=c.prepare("SELECT * FROM student WHERE Enrollment_No = ?"); ps.setString(1,enrollmentNo); ResultSet rs=ps.executeQuery();
            if(rs.next()){
                textName.setText(rs.getString("Name")); textFather.setText(rs.getString("Father_Name")); textCNIC.setText(rs.getString("CNIC")); textPhone.setText(rs.getString("Phone_No")); textEmail.setText(rs.getString("Email")); textAddress.setText(rs.getString("Address"));
                dobChooser.setDate(java.sql.Date.valueOf(rs.getString("DOB"))); admissionDateChooser.setDate(java.sql.Date.valueOf(rs.getString("Admission_Date")));
                departmentBox.setSelectedItem(rs.getString("Department")); programBox.setSelectedItem(rs.getString("Program")); semesterBox.setSelectedItem(rs.getString("Semester")); genderBox.setSelectedItem(rs.getString("Gender"));
            }
        } catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enrollmentNo=(String)enrollmentDropdown.getSelectedItem();
        String name=textName.getText(),father=textFather.getText(),cnic=textCNIC.getText(),phone=textPhone.getText(),email=textEmail.getText(),address=textAddress.getText();
        String dob=((JTextField)dobChooser.getDateEditor().getUiComponent()).getText(), admissionDate=((JTextField)admissionDateChooser.getDateEditor().getUiComponent()).getText();
        String department=(String)departmentBox.getSelectedItem(),program=(String)programBox.getSelectedItem(),semester=(String)semesterBox.getSelectedItem(),gender=(String)genderBox.getSelectedItem();
        try {
            String query="UPDATE student SET Name=?,Father_Name=?,DOB=?,CNIC=?,Phone_No=?,Email=?,Address=?,Department=?,Program=?,Semester=?,Gender=?,Admission_Date=? WHERE Enrollment_No=?";
            PreparedStatement ps=c.prepare(query);
            ps.setString(1,name);ps.setString(2,father);ps.setString(3,dob);ps.setString(4,cnic);ps.setString(5,phone);ps.setString(6,email);ps.setString(7,address);ps.setString(8,department);ps.setString(9,program);ps.setString(10,semester);ps.setString(11,gender);ps.setString(12,admissionDate);ps.setString(13,enrollmentNo);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"✅ Student updated successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception ex){ex.printStackTrace();}
    }
}
