package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.ResultSet;

public class FreeStructure extends JPanel {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color ROW_ALT    = new Color(235, 243, 252);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);

    public FreeStructure() {
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

        JLabel icon = new JLabel("💰");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setBounds(22, 14, 44, 38);
        titleBar.add(icon);

        JLabel title = new JLabel("Fee Structure");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setBounds(76, 16, 400, 34);
        titleBar.add(title);

        // Info stripe
        JPanel stripe = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        stripe.setBackground(new Color(235, 243, 252));
        stripe.setBounds(0, 65, 1200, 44);
        card.add(stripe);

        JLabel info = new JLabel("📋  Fee structure per program and semester — All amounts in PKR");
        info.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        info.setForeground(new Color(100, 120, 150));
        stripe.add(info);

        // Table
        JTable table = new JTable();
        styleTable(table);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM fee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            styleTable(table);
        } catch (Exception e) { e.printStackTrace(); }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 118, 1180, 665);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        card.add(scroll);
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13)); t.setRowHeight(34);
        t.setGridColor(new Color(220,230,245)); t.setSelectionBackground(new Color(20,130,200,60));
        t.setSelectionForeground(LABEL_COL); t.setShowVerticalLines(false);
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Segoe UI",Font.BOLD,13)); h.setBackground(NAVY); h.setForeground(Color.WHITE); h.setPreferredSize(new Dimension(0,40));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
                Component c=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,col);
                if(!isSelected) c.setBackground(row%2==0?Color.WHITE:ROW_ALT);
                setBorder(new EmptyBorder(0,12,0,12)); return c;
            }
        });
    }
}
