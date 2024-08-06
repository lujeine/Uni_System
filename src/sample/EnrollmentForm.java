package sample;

import sample.models.Course;
import sample.models.Student;
import sample.utils.DBConnection;
import sample.utils.FormInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class EnrollmentForm extends JFrame implements FormInterface {
    DBConnection dbConnection;
    Statement statement;

    private JPanel mainPanel;
    private JPanel btnsPanel;

    private JLabel studentsLabel;
    private JLabel coursesLabel;
    private JLabel label;
    private JComboBox<String> students;
    private JComboBox<String> courses;
    private JButton enrollButton;
    private JButton cancelButton;

    HashMap<String, Student> studentsMap;
    HashMap<String, Course> coursesMap;

    public EnrollmentForm(String title) {
        super(title);
        studentsMap = new HashMap<>();
        coursesMap = new HashMap<>();

        // Set current frame properties
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridLayout(8, 2, 0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(mainPanel, BorderLayout.CENTER);

        enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add to DB
                getFormInfo();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main form = new Main("Main Menu");
                EnrollmentForm.this.setVisible(false);
                form.setVisible(true);
            }
        });

        btnsPanel = new JPanel();
        btnsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnsPanel.add(enrollButton);
        btnsPanel.add(cancelButton);
        add(btnsPanel, BorderLayout.SOUTH);

        createMainComponents(mainPanel);
    }

    @Override
    public void createMainComponents(JPanel panel) {
        studentsLabel = new JLabel("Students:");
        coursesLabel = new JLabel("Courses:");

        // Populate students and courses
        students = new JComboBox<>();
        addStudentsFromDB(students);
        courses = new JComboBox<>();
        addCoursesFromDB(courses);

        panel.add(studentsLabel);
        panel.add(students);
        panel.add(coursesLabel);
        panel.add(courses);

        label = new JLabel();
        panel.add(label);
    }

    @Override
    public Object getFormInfo() {
        String selectedStudent = (String) students.getSelectedItem();
        String selectedCourse = (String) courses.getSelectedItem();

        Student student = studentsMap.get(selectedStudent);
        Course course = coursesMap.get(selectedCourse);

        enrollInDB(student, course);
        return student;
    }

    private void enrollInDB(Student student, Course course) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        String name = student.getFirstName() + " " + student.getLastName();

        try {
            String query = "INSERT INTO enrolls VALUES ('"
                    + student.getId() + "', '" + name + "', '" + course.getId()+ "', '" + course.getName() + "')";
            statement.executeUpdate(query);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // Show that the query was successful
        label.setForeground(Color.GREEN);
        label.setText("Enrollment is successful");
    }

    @Override
    public void clear() {
    }

    private void addStudentsFromDB(JComboBox<String> comboBox) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "SELECT * FROM students";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String key = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                comboBox.addItem(key);

                Student.Builder builder = new Student.Builder();
                builder.setId(resultSet.getInt("std_id"));
                builder.setFirstName(resultSet.getString("first_name"));
                builder.setLastName(resultSet.getString("last_name"));
                builder.setEmail(resultSet.getString("email"));
                builder.setCity(resultSet.getString("city"));
                builder.setCountry(resultSet.getString("country"));

                studentsMap.put(key, builder.build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCoursesFromDB(JComboBox<String> comboBox) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "SELECT * FROM courses";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String key = resultSet.getString("name");
                comboBox.addItem(key);

                Course course = new Course(resultSet.getInt("c_id"), resultSet.getString("name"),
                        resultSet.getString("section"), resultSet.getString("hours"));
                coursesMap.put(key, course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
