package sample;

import sample.models.Course;
import sample.utils.DBConnection;
import sample.utils.FormInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class CoursesForm extends JFrame implements FormInterface {
    DBConnection dbConnection;
    Statement statement;
    private JLabel label;
    private JTextField nameField;
    private JTextField sectionField;
    private JTextField hoursField;

    public CoursesForm(String title) {
        super(title);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new GridLayout(8, 2, 0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Create bottom panel
        JButton addBtn = new JButton("Save");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create course and save it into the DB
                Course course = (Course) getFormInfo();
                saveCourseIntoDB(course);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main form = new Main("Main Menu");
                CoursesForm.this.setVisible(false);
                form.setVisible(true);
            }
        });

        JPanel btnsPanel = new JPanel();
        btnsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnsPanel.add(addBtn);
        btnsPanel.add(cancelBtn);
        add(btnsPanel, BorderLayout.SOUTH);

        createMainComponents(mainPanel);
    }

    public void createMainComponents(JPanel panel) {
        JLabel name = new JLabel("Course Name");
        JLabel section = new JLabel("Course Section");
        JLabel hours = new JLabel("Hours");

        nameField = new JTextField(12);
        sectionField = new JTextField(12);
        hoursField = new JTextField(12);

        panel.add(name);
        panel.add(nameField);
        panel.add(section);
        panel.add(sectionField);
        panel.add(hours);
        panel.add(hoursField);

        label = new JLabel();
        panel.add(label);
    }

    @Override
    public Object getFormInfo() {
        String name = nameField.getText();
        String section = sectionField.getText();
        String hours = hoursField.getText();

        if (name.isEmpty() || section.isEmpty() || hours.isEmpty()) {
            label.setForeground(Color.RED);
            label.setText("All fields are required");
        }

        Course course = new Course(name, section, hours);
        return course;
    }

    private void saveCourseIntoDB(Course course) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();

        try {
            String query = "INSERT INTO courses(name, section, hours) VALUES ('"
                    + course.getName() + "', '" + course.getSection() + "', '" + course.getHours() + "')";
            statement.executeUpdate(query);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // Show that query was successful
        label.setForeground(Color.GREEN);
        label.setText("Course inserted successfully");

        // Clear all input fields
        clear();
    }

    @Override
    public void clear() {
        nameField.setText("");
        sectionField.setText("");
        hoursField.setText("");
    }
}
