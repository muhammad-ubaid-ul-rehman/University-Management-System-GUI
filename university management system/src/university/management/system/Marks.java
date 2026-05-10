package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Vector;

public class Marks extends JPanel {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color ROW_ALT    = new Color(235, 243, 252);
    private static final Color SUCCESS    = new Color(39, 174, 96);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_SUB   = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 13);

    CardLayout cardLayout;
    JPanel contentPanel;

    public Marks(String rollno, CardLayout cardLayout, JPanel contentPanel) {
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
        card.setPreferredSize(new Dimension(700, 520));
        card.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        bg.add(card, gbc);

        // Title bar
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
        titleBar.setBounds(0, 0, 700, 70);
        titleBar.setOpaque(false);
        card.add(titleBar);

        JLabel icon = new JLabel("📊");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(22, 15, 44, 38);
        titleBar.add(icon);

        JLabel title = new JLabel("Examination Result");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 18, 400, 34);
        titleBar.add(title);

        // Info row
        JPanel infoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        infoRow.setBackground(new Color(235, 243, 252));
        infoRow.setBounds(0, 70, 700, 50);
        card.add(infoRow);

        JLabel rollLbl = new JLabel("🎓  Roll No:");
        rollLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        rollLbl.setForeground(LABEL_COL);
        infoRow.add(rollLbl);

        JLabel rollVal = new JLabel(rollno);
        rollVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        rollVal.setForeground(ACCENT);
        infoRow.add(rollVal);

        JLabel semLbl = new JLabel("  📅  Semester:");
        semLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        semLbl.setForeground(LABEL_COL);
        infoRow.add(semLbl);

        JLabel semVal = new JLabel("—");
        semVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        semVal.setForeground(ACCENT);
        infoRow.add(semVal);

        // Table
        JTable table = new JTable();
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 132, 660, 280);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        card.add(scroll);

        // Load data
        try {
            Conn c = new Conn();
            ResultSet rs1 = c.statement.executeQuery("SELECT * FROM subject WHERE rollno='" + rollno + "'");
            Vector<String> subjects = new Vector<>();
            if (rs1.next()) {
                for (int i=1; i<=5; i++) subjects.add(rs1.getString("subj"+i));
            }
            rs1.close();

            ResultSet rs2 = c.statement.executeQuery("SELECT * FROM marks WHERE rollno='" + rollno + "'");
            Vector<String> marks = new Vector<>();
            String semester = null;
            if (rs2.next()) {
                semester = rs2.getString("semester");
                for (int i=1; i<=5; i++) marks.add(rs2.getString("mrk"+i));
            }
            rs2.close();

            if (subjects.size()==5 && marks.size()==5) {
                semVal.setText(semester);
                Vector<String> cols = new Vector<>();
                cols.add("Subject"); cols.add("Marks Obtained"); cols.add("Status");
                Vector<Vector<Object>> data = new Vector<>();
                for (int i=0; i<5; i++) {
                    Vector<Object> row = new Vector<>();
                    row.add(subjects.get(i));
                    row.add(marks.get(i));
                    try {
                        int m = Integer.parseInt(marks.get(i));
                        row.add(m >= 50 ? "✅ Pass" : "❌ Fail");
                    } catch (Exception ex) { row.add("—"); }
                    data.add(row);
                }
                DefaultTableModel model = new DefaultTableModel(data, cols) {
                    public boolean isCellEditable(int r, int c) { return false; }
                };
                table.setModel(model);
                styleTable(table);
                // Color pass/fail column
                table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int col) {
                        Component c = super.getTableCellRendererComponent(t, v, sel, foc, r, col);
                        String val = v == null ? "" : v.toString();
                        setForeground(val.contains("Pass") ? SUCCESS : DANGER);
                        setFont(new Font("Segoe UI", Font.BOLD, 13));
                        setBorder(new EmptyBorder(0, 10, 0, 10));
                        if (!sel) c.setBackground(r%2==0 ? Color.WHITE : ROW_ALT);
                        return c;
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "No result found for this Roll Number.");
                cardLayout.show(contentPanel, "examDetails");
            }
        } catch (Exception e) { e.printStackTrace(); }

        // Back button
        JButton backBtn = buildBtn("← Back to Results", ACCENT, ACCENT_HOV, 20, 430, 220, 44);
        backBtn.addActionListener(e -> cardLayout.show(contentPanel, "examDetails"));
        card.add(backBtn);
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13)); t.setRowHeight(36);
        t.setGridColor(new Color(220,230,245)); t.setSelectionBackground(new Color(20,130,200,60));
        t.setSelectionForeground(NAVY); t.setShowVerticalLines(false);
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Segoe UI",Font.BOLD,13)); h.setBackground(NAVY); h.setForeground(Color.WHITE); h.setPreferredSize(new Dimension(0,40));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
                Component c=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,col);
                if(!isSelected) c.setBackground(row%2==0?Color.WHITE:ROW_ALT);
                setBorder(new EmptyBorder(0,10,0,10)); return c;
            }
        });
    }

    private JButton buildBtn(String text, Color base, Color hover, int x, int y, int w, int h) {
        JButton btn=new JButton(text){protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g;g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g2.setColor(getModel().isRollover()?hover:base);g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);super.paintComponent(g);}};
        btn.setFont(FONT_BTN);btn.setForeground(Color.WHITE);btn.setBounds(x,y,w,h);btn.setBorder(new EmptyBorder(0,0,0,0));btn.setContentAreaFilled(false);btn.setFocusPainted(false);btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return btn;
    }
}
