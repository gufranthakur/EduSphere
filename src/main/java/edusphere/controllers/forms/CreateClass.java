package edusphere.controllers.forms;

import edusphere.App;
import edusphere.models.Class;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CreateClass extends JFrame{
    private JPanel rootPanel;
    private JTextField nameField;
    private JTextField subjectField;
    private JComboBox courseBox;
    private JComboBox semBox;
    private JRadioButton theoryRadioButton;
    private JRadioButton practicalRadioButton;
    private JButton createButton;
    private JButton cancelButton;

    private App app;

    String className, subjectName;

    public CreateClass(App app) {
        this.app = app;
        this.setSize(650, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(rootPanel);

        init();
    }

    private void init() {
        courseBox.addItem("AI and Machine learning");
        courseBox.addItem("Computer Engineering");
        courseBox.addItem("Automation and Robotics");
        courseBox.addItem("Mechatronics");
        courseBox.addItem("Civil Engineering");
        courseBox.addItem("Mechanical Engineering");

        semBox.addItem("1st Semester");
        semBox.addItem("2nd Semester");
        semBox.addItem("3rd Semester");
        semBox.addItem("4th Semester");
        semBox.addItem("5th Semester");
        semBox.addItem("6th Semester");

        theoryRadioButton.setSelected(true);

        createButton.setBackground(new Color(39, 104, 228));

        theoryRadioButton.addActionListener(e -> {
            practicalRadioButton.setSelected(false);
        });

        practicalRadioButton.addActionListener(e -> {
            theoryRadioButton.setSelected(false);
        });

        createButton.addActionListener(e -> {
            createClass();
        });

        this.setVisible(true);
    }

    private void createClass() {

        if (nameField.getText().isEmpty() || subjectField.getText().isEmpty()) {
            app.throwError("Enter subject name");
            return;
        }
        else {
            className = nameField.getText();
            subjectName = subjectField.getText();
        }

        String courseName = Objects.requireNonNull(courseBox.getSelectedItem()).toString();
        String semester = Objects.requireNonNull(semBox.getSelectedItem()).toString();

        String classType;
        if (theoryRadioButton.isSelected()) classType = "Theory";
        else classType = "Practical";

        Class newClass = new Class(className, subjectName, courseName, semester, classType);
        app.homeView.addClassToBox(newClass);

        this.dispose();
    }

}
