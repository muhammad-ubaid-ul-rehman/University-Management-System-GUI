package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateFaculty extends JPanel implements ActionListener {

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

    JComboBox<String> empIdDropdown, qualificationBox, departmentBox, designationBox;
    JTextField textName, textFather, textCNIC, textPhone, textEmail, textAddress, textSpecialization, textExperience;
    JDateChooser dobChooser, joiningDateChooser;
    JButton update, cancel;
    Runnable onCancelCallback;
    JPanel formPanel;

    public UpdateFaculty(Runnable onCancelCallback) {
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
        gbc.insets = new Insets(-125, -200, 0, 0);
        wrapper.add(scroll, gbc);
        bg.add(wrapper, BorderLayout.CENTER);
    }

    private JPanel buildCard() {
        formPanel = new JPanel() {
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
        formPanel.setLayout(null);
        formPanel.setPreferredSize(new Dimension(1100, 730));
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

        JLabel icon = new JLabel("✏️");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(30, 18, 44, 36);
        titleBar.add(icon);

        JLabel title = new JLabel("Update Faculty Details");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(85, 17, 500, 36);
        titleBar.add(title);

        // — Employee ID selector ——————————————————————————————————
        addSL(formPanel, "Select Employee", 30, 84);
        JLabel selLabel = new JLabel("Employee ID:");
        selLabel.setFont(FONT_LBL);
        selLabel.setForeground(LABEL_COL);
        selLabel.setBounds(30, 100, 130, 18);
        formPanel.add(selLabel);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setFont(FONT_FIELD);
        empIdDropdown.setBackground(Color.WHITE);
        empIdDropdown.setBounds(170, 97, 220, 38);
        empIdDropdown.setBorder(BorderFactory.createLineBorder(BORDER_COL, 2, true));
        formPanel.add(empIdDropdown);

        JButton loadBtn = buildBtn("🔍 Load", ACCENT, ACCENT_HOV, 405, 97, 130, 38);
        formPanel.add(loadBtn);
        loadBtn.addActionListener(e -> loadFacultyDetails((String) empIdDropdown.getSelectedItem()));

        // Divider
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 148, 1040, 2);
        sep.setForeground(BORDER_COL);
        formPanel.add(sep);

        // — Form rows ————————————————————————————————————————————
        int r1=160, r2=235, r3=310, r4=385, r5=460;

        addSL(formPanel, "Personal Information", 30, r1-13);

        addFL(formPanel, "Full Name", 30, r1);
        textName = addSF(formPanel, 30, r1+22, 240);

        addFL(formPanel, "Father's Name", 310, r1);
        textFather = addSF(formPanel, 310, r1+22, 240);

        addFL(formPanel, "CNIC", 590, r1);
        textCNIC = addSF(formPanel, 590, r1+22, 200);

        addFL(formPanel, "Phone", 830, r1);
        textPhone = addSF(formPanel, 830, r1+22, 200);

        addFL(formPanel, "Email", 30, r2);
        textEmail = addSF(formPanel, 30, r2+22, 240);

        addFL(formPanel, "Address", 310, r2);
        textAddress = addSF(formPanel, 310, r2+22, 240);

        addFL(formPanel, "Date of Birth", 590, r2);
        dobChooser = styledDC(formPanel, 590, r2+22, 200);

        addFL(formPanel, "Joining Date", 830, r2);
        joiningDateChooser = styledDC(formPanel, 830, r2+22, 200);

        addSL(formPanel, "Academic Information", 30, r3-13);

        addFL(formPanel, "Qualification", 30, r3);
        qualificationBox = addSC(formPanel, new String[]{"BS (Hons)","MS / M.Phil","PhD","MA","MBA","M.Ed","B.Ed","LLB","MBBS","Other"}, 30, r3+22, 240);

        addFL(formPanel, "Department", 310, r3);
        departmentBox = addSC(formPanel, new String[]{"Computer Science","Software Engineering","Electrical Engineering","Mechanical Engineering","Civil Engineering","Business Administration","Economics","English","Mathematics","Islamic Studies","Education","Physics","Chemistry","Psychology","Other"}, 310, r3+22, 240);

        addFL(formPanel, "Designation", 590, r3);
        designationBox = addSC(formPanel, new String[]{"Lecturer","Assistant Professor","Associate Professor","Professor"}, 590, r3+22, 200);

        addFL(formPanel, "Specialization", 830, r3);
        textSpecialization = addSF(formPanel, 830, r3+22, 200);

        addFL(formPanel, "Experience (Years)", 30, r4);
        textExperience = addSF(formPanel, 30, r4+22, 240);

        // Buttons
        update = buildBtn("✔  Update", SUCCESS, new Color(30,140,80), 380, r4+22, 160, 40);
        update.addActionListener(this);
        formPanel.add(update);

        cancel = buildBtn("✕  Cancel", DANGER, new Color(160,40,30), 560, r4+22, 160, 40);
        cancel.addActionListener(this);
        formPanel.add(cancel);

        loadEmpIDs();
        empIdDropdown.addActionListener(e -> loadFacultyDetails((String) empIdDropdown.getSelectedItem()));

        return formPanel;
    }

    // helpers
    private void addSL(JPanel p, String t, int x, int y) { JLabel l=new JLabel(t.toUpperCase()); l.setFont(new Font("Segoe UI",Font.BOLD,10)); l.setForeground(MUTED); l.setBounds(x,y,400,14); p.add(l); }
    private void addFL(JPanel p, String t, int x, int y) { JLabel l=new JLabel(t); l.setFont(FONT_LBL); l.setForeground(LABEL_COL); l.setBounds(x,y,240,18); p.add(l); }

    private JTextField addSF(JPanel p, int x, int y, int w) {
        JTextField f=new JTextField(); f.setFont(FONT_FIELD); f.setForeground(LABEL_COL); f.setBackground(Color.WHITE); f.setCaretColor(ACCENT);
        f.setBounds(x,y,w,40);
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COL,2,true),new EmptyBorder(5,10,5,10)));
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
        JButton btn = new JButton(text){
            protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()?hover:base); g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10); super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN); btn.setForeground(Color.WHITE); btn.setBounds(x,y,w,h);
        btn.setBorder(new EmptyBorder(0,0,0,0)); btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return btn;
    }

    private void loadEmpIDs() {
        try { Conn conn=new Conn(); ResultSet rs=conn.statement.executeQuery("SELECT Emp_ID FROM teacher");
            while(rs.next()) empIdDropdown.addItem(rs.getString("Emp_ID"));
        } catch(Exception e){e.printStackTrace();}
    }

    private void loadFacultyDetails(String empId) {
        if(empId==null) return;
        try {
            Conn conn=new Conn(); PreparedStatement pst=conn.connection.prepareStatement("SELECT * FROM teacher WHERE Emp_ID = ?");
            pst.setString(1,empId); ResultSet rs=pst.executeQuery();
            if(rs.next()){
                textName.setText(rs.getString("Name")); textFather.setText(rs.getString("Father_Name"));
                textCNIC.setText(rs.getString("CNIC")); textPhone.setText(rs.getString("Phone_No"));
                textEmail.setText(rs.getString("Email")); textAddress.setText(rs.getString("Address"));
                textSpecialization.setText(rs.getString("Specialization")); textExperience.setText(rs.getString("Experience"));
                ((JTextField)dobChooser.getDateEditor().getUiComponent()).setText(rs.getString("DOB"));
                ((JTextField)joiningDateChooser.getDateEditor().getUiComponent()).setText(rs.getString("Joining_date"));
                qualificationBox.setSelectedItem(rs.getString("Qualification"));
                departmentBox.setSelectedItem(rs.getString("Department"));
                designationBox.setSelectedItem(rs.getString("Designation"));
            }
        } catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==update){
            String empId=(String)empIdDropdown.getSelectedItem();
            String name=textName.getText(), father=textFather.getText(), dob=((JTextField)dobChooser.getDateEditor().getUiComponent()).getText();
            String cnic=textCNIC.getText(), phone=textPhone.getText(), email=textEmail.getText(), address=textAddress.getText();
            String qualification=(String)qualificationBox.getSelectedItem(), department=(String)departmentBox.getSelectedItem(), designation=(String)designationBox.getSelectedItem();
            String specialization=textSpecialization.getText(), experience=textExperience.getText(), joiningDate=((JTextField)joiningDateChooser.getDateEditor().getUiComponent()).getText();
            try {
                Conn conn=new Conn(); String query="UPDATE teacher SET Name=?,Father_Name=?,DOB=?,CNIC=?,Phone_No=?,Email=?,Address=?,Qualification=?,Department=?,Designation=?,Specialization=?,Experience=?,Joining_date=? WHERE Emp_ID=?";
                PreparedStatement pst=conn.connection.prepareStatement(query);
                pst.setString(1,name);pst.setString(2,father);pst.setString(3,dob);pst.setString(4,cnic);pst.setString(5,phone);
                pst.setString(6,email);pst.setString(7,address);pst.setString(8,qualification);pst.setString(9,department);
                pst.setString(10,designation);pst.setString(11,specialization);pst.setString(12,experience);pst.setString(13,joiningDate);pst.setString(14,empId);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"✅ Faculty Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch(Exception ex){ex.printStackTrace(); JOptionPane.showMessageDialog(null,"❌ Update Failed","Error",JOptionPane.ERROR_MESSAGE);}
        } else if(e.getSource()==cancel){
            if(onCancelCallback!=null) onCancelCallback.run();
        }
    }
}
