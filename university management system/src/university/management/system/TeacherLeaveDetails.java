package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class TeacherLeaveDetails extends JPanel implements ActionListener {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Color ROW_ALT    = new Color(235, 243, 252);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 12);

    JComboBox<String> choiceEmpID;
    JTable table;
    JButton search, cancel, print;
    CardLayout cardLayout;
    JPanel contentPanel;

    public TeacherLeaveDetails(CardLayout cardLayout, JPanel contentPanel) {
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

        JLabel icon = new JLabel("📅");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        icon.setBounds(22, 15, 44, 36);
        titleBar.add(icon);

        JLabel title = new JLabel("Faculty Leave Records");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 17, 400, 32);
        titleBar.add(title);

        // Toolbar
        JPanel tb = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        tb.setBackground(Color.WHITE);
        tb.setBounds(0, 65, 1200, 58);
        card.add(tb);

        JLabel sl = new JLabel("Employee ID:");
        sl.setFont(FONT_LBL); sl.setForeground(LABEL_COL); tb.add(sl);

        choiceEmpID = new JComboBox<>();
        choiceEmpID.setFont(FONT_FIELD); choiceEmpID.setBackground(Color.WHITE);
        choiceEmpID.setPreferredSize(new Dimension(180, 36)); tb.add(choiceEmpID);

        search = toolBtn("🔍 Search", ACCENT, ACCENT_HOV); search.addActionListener(this); tb.add(search);
        print  = toolBtn("🖨 Print", new Color(100,120,150), new Color(80,100,130)); print.addActionListener(this); tb.add(print);
        cancel = toolBtn("✕ Back", DANGER, new Color(160,40,30)); cancel.addActionListener(this); tb.add(cancel);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT Emp_ID FROM teacher");
            while (rs.next()) choiceEmpID.addItem(rs.getString("Emp_ID"));
        } catch (Exception e) { e.printStackTrace(); }

        // Table
        table = new JTable();
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 133, 1180, 650);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        card.add(scroll);

        loadAllData();
    }

    private void loadAllData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT tl.Emp_ID, t.Name, tl.Leave_Date, tl.Duration FROM teacherleave tl JOIN teacher t ON tl.Emp_ID = t.Emp_ID");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            styleTable(table);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadByEmpId(String empId) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT tl.Emp_ID, t.Name, tl.Leave_Date, tl.Duration FROM teacherleave tl JOIN teacher t ON tl.Emp_ID = t.Emp_ID WHERE tl.Emp_ID = '" + empId + "'");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            styleTable(table);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13)); t.setRowHeight(32);
        t.setGridColor(new Color(220,230,245)); t.setSelectionBackground(new Color(20,130,200,60));
        t.setSelectionForeground(NAVY); t.setShowVerticalLines(false);
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Segoe UI", Font.BOLD, 13)); h.setBackground(NAVY); h.setForeground(Color.WHITE); h.setPreferredSize(new Dimension(0, 38));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
                Component c=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,col);
                if(!isSelected) c.setBackground(row%2==0?Color.WHITE:ROW_ALT);
                setBorder(new EmptyBorder(0,10,0,10)); return c;
            }
        });
    }

    private JButton toolBtn(String text, Color base, Color hover) {
        JButton btn=new JButton(text){protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g;g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g2.setColor(getModel().isRollover()?hover:base);g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);super.paintComponent(g);}};
        btn.setFont(FONT_BTN);btn.setForeground(Color.WHITE);btn.setPreferredSize(new Dimension(115,36));btn.setBorder(new EmptyBorder(0,0,0,0));btn.setContentAreaFilled(false);btn.setFocusPainted(false);btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==search) loadByEmpId((String)choiceEmpID.getSelectedItem());
        else if(e.getSource()==print){try{table.print();}catch(Exception ex){ex.printStackTrace();}}
        else if(e.getSource()==cancel) cardLayout.show(contentPanel,"default");
    }
}
