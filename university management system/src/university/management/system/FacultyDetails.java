package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class FacultyDetails extends JPanel implements ActionListener {

    private static final Color NAVY       = new Color(20,  33,  61);
    private static final Color NAVY_DARK  = new Color(14,  24,  46);
    private static final Color ACCENT     = new Color(20, 130, 200);
    private static final Color ACCENT_HOV = new Color(15, 110, 170);
    private static final Color CARD_BG    = new Color(245, 248, 252);
    private static final Color BORDER_COL = new Color(150, 180, 220);
    private static final Color LABEL_COL  = new Color(20,  33,  61);
    private static final Color MUTED      = new Color(100, 120, 150);
    private static final Color DANGER     = new Color(192, 57,  43);
    private static final Color ROW_ALT    = new Color(235, 243, 252);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font  FONT_LBL   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font  FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN   = new Font("Segoe UI", Font.BOLD, 12);

    JComboBox<String> choice;
    JTable table;
    JButton search, print, update, add, cancel;
    CardLayout cardLayout;
    JPanel contentPanel;

    public FacultyDetails(CardLayout cardLayout, JPanel contentPanel) {
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
        bg.setLayout(new BorderLayout(0, 0));
        bg.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(bg);

        // Card
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        card.setLayout(new BorderLayout(0, 0));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(8, 8, 8, 8));
        bg.add(card, BorderLayout.CENTER);

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
        titleBar.setLayout(new BorderLayout(15, 0));
        titleBar.setBorder(new EmptyBorder(12, 20, 12, 20));
        titleBar.setPreferredSize(new Dimension(0, 65));
        titleBar.setOpaque(false);
        card.add(titleBar, BorderLayout.NORTH);

        JLabel icon = new JLabel("👨‍🏫");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        titleBar.add(icon, BorderLayout.WEST);

        JLabel title = new JLabel("Faculty Details");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        titleBar.add(title, BorderLayout.CENTER);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(5, 10, 5, 10));
        card.add(toolbar, BorderLayout.BEFORE_FIRST_LINE);

        JLabel searchLbl = new JLabel("Employee ID:");
        searchLbl.setFont(FONT_LBL);
        searchLbl.setForeground(LABEL_COL);
        toolbar.add(searchLbl);

        choice = new JComboBox<>();
        choice.setFont(FONT_FIELD);
        choice.setBackground(Color.WHITE);
        choice.setPreferredSize(new Dimension(180, 36));
        toolbar.add(choice);

        search = toolBtn("🔍 Search", ACCENT, ACCENT_HOV);
        search.addActionListener(this);
        toolbar.add(search);

        print = toolBtn("🖨 Print", new Color(100, 120, 150), new Color(80, 100, 130));
        print.addActionListener(this);
        toolbar.add(print);

        add = toolBtn("➕ Add", new Color(39, 174, 96), new Color(30, 140, 80));
        add.addActionListener(this);
        toolbar.add(add);

        update = toolBtn("✏️ Update", new Color(230, 126, 34), new Color(190, 100, 20));
        update.addActionListener(this);
        toolbar.add(update);

        cancel = toolBtn("✕ Back", DANGER, new Color(160, 40, 30));
        cancel.addActionListener(this);
        toolbar.add(cancel);

        // Table is added separately above titleBar, so reorder
        card.setLayout(null);
        titleBar.setBounds(0, 0, 1200, 65);
        card.add(titleBar);

        JPanel tbWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
        tbWrapper.setBackground(Color.WHITE);
        tbWrapper.setBounds(0, 65, 1200, 58);
        card.add(tbWrapper);

        JLabel sl2 = new JLabel("Employee ID:");
        sl2.setFont(FONT_LBL); sl2.setForeground(LABEL_COL);
        tbWrapper.add(sl2);

        choice = new JComboBox<>(); choice.setFont(FONT_FIELD); choice.setBackground(Color.WHITE);
        choice.setPreferredSize(new Dimension(180, 36)); tbWrapper.add(choice);

        search = toolBtn("🔍 Search", ACCENT, ACCENT_HOV); search.addActionListener(this); tbWrapper.add(search);
        print  = toolBtn("🖨 Print", new Color(100,120,150), new Color(80,100,130)); print.addActionListener(this); tbWrapper.add(print);
        add    = toolBtn("➕ Add", new Color(39,174,96), new Color(30,140,80)); add.addActionListener(this); tbWrapper.add(add);
        update = toolBtn("✏️ Update", new Color(230,126,34), new Color(190,100,20)); update.addActionListener(this); tbWrapper.add(update);
        cancel = toolBtn("✕ Back", DANGER, new Color(160,40,30)); cancel.addActionListener(this); tbWrapper.add(cancel);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("select * from teacher");
            while (rs.next()) choice.addItem(rs.getString("Emp_ID"));
        } catch (Exception e) { e.printStackTrace(); }

        // Styled Table
        table = new JTable();
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(5, 133, 1230, 650);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        card.add(scroll);
        card.setPreferredSize(new Dimension(1240, 800));

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("select * from teacher");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setRowHeight(32);
        t.setGridColor(new Color(220, 230, 245));
        t.setSelectionBackground(new Color(20, 130, 200, 60));
        t.setSelectionForeground(NAVY);
        t.setShowVerticalLines(false);

        JTableHeader header = t.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(NAVY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 38));

        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : ROW_ALT);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    private JButton toolBtn(String text, Color base, Color hover) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setBorder(new EmptyBorder(0,0,0,0));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            try {
                Conn c = new Conn();
                String sel = (String) choice.getSelectedItem();
                ResultSet rs = c.statement.executeQuery("SELECT * FROM teacher WHERE Emp_ID = '" + sel + "'");
                table.setModel(DbUtils.resultSetToTableModel(rs));
                styleTable(table);
            } catch (Exception ex) { ex.printStackTrace(); }
        } else if (e.getSource() == update) {
            UpdateFaculty panel = new UpdateFaculty(() -> cardLayout.show(contentPanel, "teacherDetails"));
            contentPanel.add(panel, "updateFaculty");
            cardLayout.show(contentPanel, "updateFaculty");
        } else if (e.getSource() == print) {
            try { table.print(); } catch (Exception ex) { ex.printStackTrace(); }
        } else if (e.getSource() == add) {
            AddFaculty panel = new AddFaculty(() -> cardLayout.show(contentPanel, "teacherDetails"));
            contentPanel.add(panel, "addFaculty");
            cardLayout.show(contentPanel, "addFaculty");
        } else if (e.getSource() == cancel) {
            cardLayout.show(contentPanel, "default");
        }
    }
}
