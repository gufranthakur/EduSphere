package edusphere.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;
import raven.datetime.DatePicker;
import raven.swing.spinner.SpinnerProgress;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendenceView {
    private JPanel rootPanel;
    private JPanel datePickerPanel;
    private JScrollPane attendanceScrollPane;
    private JPanel attendancePanel;
    private JButton backButton;
    private SpinnerProgress presentStudentProgressSpinner;
    private JPanel statisticsPanel;
    private SpinnerProgress attendenceSpinnerProgress;
    private JButton calculateButton;
    private DatePicker datePicker;
    private JFormattedTextField editor;

    private App app;
    private Class attendanceClass;

    private LocalDate currentDate;
    private Dimension rootDimension = new Dimension(300,100);

    public AttendenceView(App app) {
        this.app = app;
    }

    public void init() {
        attendancePanel.setLayout(new BoxLayout(attendancePanel, BoxLayout.Y_AXIS));
        datePicker = new DatePicker();
        editor = new JFormattedTextField();

        editor.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 18));
        editor.setPreferredSize(new Dimension(150, editor.getPreferredSize().height));

        datePicker.setEditor(editor);

        datePickerPanel.add(editor);

        datePicker.addDateSelectionListener(e -> {
            currentDate = datePicker.getSelectedDate();
            if (!attendanceClass.getTotalLectures().contains(currentDate)) {
                attendanceClass.getTotalLectures().add(currentDate);
            }
            loadAttendanceDisplayData();  // This will refresh the checkboxes with the correct states
        });
    }

    public void initActionListeners() {
        backButton.addActionListener(e -> app.changeState("HomeView"));
        calculateButton.addActionListener(e -> calculateStatistics());
    }



    public void loadAttendanceDisplayData() {
        attendancePanel.removeAll();

        presentStudentProgressSpinner.setValue(0);
        attendenceSpinnerProgress.setValue(0);

        presentStudentProgressSpinner.setString("-");
        attendenceSpinnerProgress.setString("-");

        // First, get the currently selected date from the date picker
        currentDate = datePicker.getSelectedDate();

        // Add batch labels and students as before, but with modified checkbox logic
        JLabel aLabel = getBatchLabel("A Batch");
        attendancePanel.add(aLabel);
        addBatchToPanel(attendanceClass.aBatch);

        JLabel bLabel = getBatchLabel("B Batch");
        attendancePanel.add(bLabel);
        addBatchToPanel(attendanceClass.bBatch);

        JLabel cLabel = getBatchLabel("C Batch");
        attendancePanel.add(cLabel);
        addBatchToPanel(attendanceClass.cBatch);



        app.revalidate();
        app.repaint();
    }

    public void calculateStatistics() {
        if (currentDate == null) {
            app.throwError("Please select a date first");
            return;
        }

        // Count present students
        int presentStudents = 0;
        int totalStudents = attendanceClass.aBatch.size() +
                attendanceClass.bBatch.size() +
                attendanceClass.cBatch.size();

        presentStudentProgressSpinner.setMaximum(totalStudents);

        // Count through all batches
        for (Student student : attendanceClass.aBatch) {
            if (student.getPresentLectures().contains(currentDate)) {
                presentStudents++;
            }
        }
        for (Student student : attendanceClass.bBatch) {
            if (student.getPresentLectures().contains(currentDate)) {
                presentStudents++;
            }
        }
        for (Student student : attendanceClass.cBatch) {
            if (student.getPresentLectures().contains(currentDate)) {
                presentStudents++;
            }
        }

        // Calculate attendance percentage
        float attendancePercentage = (float) presentStudents / totalStudents * 100;

        // Update the spinners
        presentStudentProgressSpinner.setValue(presentStudents);
        attendenceSpinnerProgress.setValue((int) attendancePercentage);

        presentStudentProgressSpinner.setString((presentStudents) + "/" + (totalStudents));
        attendenceSpinnerProgress.setString(String.valueOf(attendancePercentage));
    }

    public void addBatchToPanel(ArrayList<Student> batch) {
        for (Student student : batch) {
            JPanel studentAttendancePanel = loadPanels(student);

            attendancePanel.add(studentAttendancePanel);
            attendancePanel.add(Box.createVerticalStrut(10));
        }
    }

    private JPanel loadPanels(Student student) {
        JPanel rootPanel = new JPanel();
        JLabel studentNameLabel = new JLabel(student.getFullName());
        JCheckBox checkBox = new JCheckBox();

        studentNameLabel.setFont(app.fontSmall);
        studentNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkBox.setFont(app.fontSmall);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setPreferredSize(rootDimension);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rootPanel.setBackground(app.getBackground().darker());
        rootPanel.setOpaque(true);
        rootPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");

        if (currentDate != null) {
            if (student.getPresentLectures().contains(currentDate)) {
                checkBox.setSelected(true);
                checkBox.setText("Present");
            } else {
                checkBox.setSelected(false);
                checkBox.setText("Absent");
                rootPanel.setPreferredSize(rootDimension);
            }
        }

        checkBox.addActionListener(e -> {
            if (currentDate != null) {
                if (checkBox.isSelected()) {
                    if (!student.getPresentLectures().contains(currentDate)) {
                        student.getPresentLectures().add(currentDate);
                        System.out.println("Added attendance for " + student.getFullName() + " on " + currentDate);
                        checkBox.setSelected(true);
                        checkBox.setText("Present");
                    }
                } else {
                    student.getPresentLectures().remove(currentDate);
                    System.out.println("Removed attendance for " + student.getFullName() + " on " + currentDate);
                    checkBox.setSelected(false);
                    checkBox.setText("Absent");
                }
            } else {
                checkBox.setSelected(false);
                app.throwError("Select a date");
            }
        });

        rootPanel.add(studentNameLabel);
        rootPanel.add(checkBox);

        return rootPanel;
    }

    private JLabel getBatchLabel(String text) {
        JLabel aLabel = new JLabel(text);
        aLabel.setFont(app.fontLarge);
        aLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensures it stretches horizontally
        aLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, aLabel.getPreferredSize().height)); // Take full width
        aLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        aLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label's text horizontally
        aLabel.setBackground(new Color(66, 64, 64));
        aLabel.setOpaque(true);
        return aLabel;
    }

    public void setAttendanceClass(Class attendanceClass) {
        this.attendanceClass = attendanceClass;
        loadAttendanceDisplayData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
