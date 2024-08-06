package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel mainPanel;

    private JLabel label;
    private JButton studentsBtn;
    private JButton coursesBtn;
    private JButton enrollmentBtn;

    public Main(String title) {
        super(title);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Create the main panel
        mainPanel = new JPanel(new GridLayout(4, 1, 50, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        add(mainPanel, BorderLayout.CENTER);

        createMainComponents(mainPanel);
    }

    private void createMainComponents(JPanel panel) {
        label = new JLabel("Main Menu");

        studentsBtn = new JButton("Add Student");
        studentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentsForm form = new StudentsForm("Register students");
                Main.this.setVisible(false);
                form.setVisible(true);
            }
        });

        coursesBtn = new JButton("Add Course");
        coursesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursesForm form = new CoursesForm("Register courses");
                Main.this.setVisible(false);
                form.setVisible(true);
            }
        });

        enrollmentBtn = new JButton("Enroll in Courses");
        enrollmentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnrollmentForm form = new EnrollmentForm("Enroll Students in Courses");
                Main.this.setVisible(false);
                form.setVisible(true);
            }
        });

        panel.add(label);
        panel.add(studentsBtn);
        panel.add(coursesBtn);
        panel.add(enrollmentBtn);
    }

    public static void main(String[] args) {
        Main m = new Main("Main Menu");
        m.setVisible(true);
    }
}
