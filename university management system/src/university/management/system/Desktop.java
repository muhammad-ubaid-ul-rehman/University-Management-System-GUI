package university.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Desktop extends JFrame implements ActionListener {
    JPanel sidebar;
    CardLayout cardLayout;
    JPanel contentPanel;
    public static CardLayout globalCardLayout;
    public static JPanel globalContentPanel;


    Desktop() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/Desktop.png"));
        Image i2 = i1.getImage().getScaledInstance(1540, 850, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        globalCardLayout = cardLayout;
        globalContentPanel = contentPanel;
        contentPanel.setOpaque(false); // So background image still shows
        contentPanel.setBounds(300, 0, 1240, 850);

        background.setLayout(null);
        background.add(contentPanel);

        setContentPane(background);


        setTitle("University Management System");

        // Sidebar Panel
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(20, 33, 61));
        sidebar.setBounds(0, 0, 300, 850);

        Font btnFont = new Font("Arial", Font.BOLD, 14);
        Color btnColor = new Color(230, 238, 246);

        // Group 1: Add / View Info
        JPanel group1 = createGroupPanel("Add / View Information");
        addSidebarButton(group1, "New Faculty Information", btnFont, btnColor);
        addSidebarButton(group1, "New Student Information", btnFont, btnColor);
        addSidebarButton(group1, "View Faculty Details", btnFont, btnColor);
        addSidebarButton(group1, "View Student Details", btnFont, btnColor);
        sidebar.add(group1);

        // Group 2: Leave
        JPanel group2 = createGroupPanel("Leave Management");
        addSidebarButton(group2, "Faculty Leave", btnFont, btnColor);
        addSidebarButton(group2, "Student Leave", btnFont, btnColor);
        addSidebarButton(group2, "Faculty Leave Details", btnFont, btnColor);
        addSidebarButton(group2, "Student Leave Details", btnFont, btnColor);
        sidebar.add(group2);

        // Group 3: Update
        JPanel group3 = createGroupPanel("Update Information");
        addSidebarButton(group3, "Update Faculty Details", btnFont, btnColor);
        addSidebarButton(group3, "Update Student Details", btnFont, btnColor);
        sidebar.add(group3);

        // Group 4: Examination
        JPanel group4 = createGroupPanel("Examination");
        addSidebarButton(group4, "Enter Marks", btnFont, btnColor);
        addSidebarButton(group4, "Examination Details", btnFont, btnColor);
        sidebar.add(group4);

        // Group 5: Fee
        JPanel group5 = createGroupPanel("Fee");
        addSidebarButton(group5, "Fee Structure", btnFont, btnColor);
        addSidebarButton(group5, "Student Fee Form", btnFont, btnColor);
        sidebar.add(group5);

        // Group 6: Utilities
        JPanel group6 = createGroupPanel("Utilities");
        addSidebarButton(group6, "Calculator", btnFont, btnColor);
        addSidebarButton(group6, "Notepad", btnFont, btnColor);
        sidebar.add(group6);

        // Group 7: Other
        JPanel group7 = createGroupPanel("Other");
        addSidebarButton(group7, "About", btnFont, btnColor);
        addSidebarButton(group7, "Exit", btnFont, btnColor);
        sidebar.add(group7);

        // ScrollPane for full height support
        JScrollPane scrollPane = new JScrollPane(sidebar);
        scrollPane.setBounds(0, 0, 300, 850);
        scrollPane.setBorder(null);
        background.add(scrollPane);
        // Add default background panel (empty panel when app loads)
        JPanel defaultPanel = new JPanel();
        defaultPanel.setOpaque(false);
        contentPanel.add(defaultPanel, "default");
        cardLayout.show(contentPanel, "default");


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1540, 850);
        setLayout(null);
        setVisible(true);
    }

    private JPanel createGroupPanel(String title) {
        JPanel group = new JPanel();
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBackground(new Color(20, 33, 61));
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        group.setBorder(border);
        return group;
    }

    private void addSidebarButton(JPanel panel, String text, Font font, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 28));
        button.setMaximumSize(new Dimension(250, 28));
        button.setMinimumSize(new Dimension(250, 28));
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sm = e.getActionCommand();
        switch (sm) {
            case "Exit":
                System.exit(0);
                break;
            case "Calculator":
                try {
                    new ProcessBuilder("calc.exe").start();
                } catch (Exception E) {
                    E.printStackTrace();
                }
                break;
            case "Notepad":
                try {
                    new ProcessBuilder("notepad.exe").start();
                } catch (Exception E) {
                    E.printStackTrace();
                }
                break;
            case "New Faculty Information":
                JPanel addFacultyPanel = new AddFaculty(() -> {
                    cardLayout.show(contentPanel, "default");
                });
                contentPanel.add(addFacultyPanel, "addFaculty");
                cardLayout.show(contentPanel, "addFaculty");
                break;


            case "New Student Information":
                JPanel addStudentPanel = new AddStudent(() -> {
                    cardLayout.show(contentPanel, "default");
                });
                contentPanel.add(addStudentPanel, "addStudent");
                cardLayout.show(contentPanel, "addStudent");
                break;

            case "View Faculty Details":
                FacultyDetails panel = new FacultyDetails(cardLayout, contentPanel);
                contentPanel.add(panel, "teacherDetails");
                cardLayout.show(contentPanel, "teacherDetails");
                break;

            case "View Student Details":
                StudentDetails sPanel = new StudentDetails(cardLayout, contentPanel);
                contentPanel.add(sPanel, "viewStudent");
                cardLayout.show(contentPanel, "viewStudent");
                break;

            case "Faculty Leave":
                JPanel facultyLeavePanel = new FacultyLeave(cardLayout, contentPanel);
                contentPanel.add(facultyLeavePanel, "facultyLeave");
                cardLayout.show(contentPanel, "facultyLeave");
                break;
            case "Student Leave":
                JPanel studentLeavePanel = new StudentLeave(cardLayout, contentPanel);
                contentPanel.add(studentLeavePanel, "studentLeave");
                cardLayout.show(contentPanel, "studentLeave");
                break;
            case "Faculty Leave Details":
                JPanel facultyLeaveDetailsPanel = new TeacherLeaveDetails(cardLayout, contentPanel);
                contentPanel.add(facultyLeaveDetailsPanel, "facultyLeaveDetails");
                cardLayout.show(contentPanel, "facultyLeaveDetails");
                break;
            case "Student Leave Details":
                JPanel studentLeaveDetailsPanel = new StudentLeaveDetails(cardLayout, contentPanel);
                contentPanel.add(studentLeaveDetailsPanel, "studentLeaveDetails");
                cardLayout.show(contentPanel, "studentLeaveDetails");
                break;
            case "Update Faculty Details":
                JPanel updateTeacherPanel = new UpdateFaculty(() -> {
                    cardLayout.show(contentPanel, "default");
                });
                contentPanel.add(updateTeacherPanel, "updateTeacher");
                cardLayout.show(contentPanel, "updateTeacher");
                break;

            case "Update Student Details":
                JPanel updateStudentPanel = new updateStudent(() -> {
                    cardLayout.show(contentPanel, "default");
                });
                contentPanel.add(updateStudentPanel, "updateStudent");
                cardLayout.show(contentPanel, "updateStudent");
                break;

            case "Enter Marks":
                JPanel enterMarks = new EnterMarks(cardLayout, contentPanel);
                contentPanel.add(enterMarks, "enterMarks");
                cardLayout.show(contentPanel, "enterMarks");
                break;

            case "Examination Details":
                JPanel examDetails = new ExaminationDetails(cardLayout, contentPanel);
                contentPanel.add(examDetails, "examDetails");
                cardLayout.show(contentPanel, "examDetails");
                break;

            case "Fee Structure":
                JPanel feePanel = new FreeStructure();
                contentPanel.add(feePanel, "feeStructure");
                cardLayout.show(contentPanel, "feeStructure");
                break;

            case "Student Fee Form":
                JPanel studentFeePanel = new StudentFeeForm(cardLayout, contentPanel);
                contentPanel.add(studentFeePanel, "studentFee");
                cardLayout.show(contentPanel, "studentFee");
                break;

            case "About":
                JPanel aboutPanel = new About();
                contentPanel.add(aboutPanel, "about");
                cardLayout.show(contentPanel, "about");
                break;
        }
    }
    public static void showPanel(String name) {
        globalCardLayout.show(globalContentPanel, name);
    }
    public void showHome() {
            cardLayout.show(contentPanel, "default");

    }
}