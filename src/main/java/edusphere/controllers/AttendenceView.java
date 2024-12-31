package edusphere.controllers;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;
import raven.datetime.DatePicker;

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
    private JButton saveButton;
    private DatePicker datePicker;
    private JFormattedTextField editor;

    private App app;
    private Class attendanceClass;

    private LocalDate currentDate;

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
        saveButton.addActionListener(e -> {

        });
    }



    public void loadAttendanceDisplayData() {
        attendancePanel.removeAll();

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

    public void addBatchToPanel(ArrayList<Student> batch) {
        for (Student student : batch) {
            JCheckBox checkBox = new JCheckBox(student.getFullName());
            checkBox.setFont(app.fontSmall);
            checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Set the initial state of the checkbox based on stored attendance
            if (currentDate != null && student.getPresentLectures().contains(currentDate)) {
                checkBox.setSelected(true);
            }

            checkBox.addActionListener(e -> {
                if (currentDate != null) {
                    if (checkBox.isSelected()) {
                        // Add attendance when checked
                        if (!student.getPresentLectures().contains(currentDate)) {
                            student.getPresentLectures().add(currentDate);
                            System.out.println("Added attendance for " + student.getFullName() + " on " + currentDate);
                        }
                    } else {
                        // Remove attendance when unchecked
                        student.getPresentLectures().remove(currentDate);
                        System.out.println("Removed attendance for " + student.getFullName() + " on " + currentDate);
                    }
                } else {
                    checkBox.setSelected(false);
                    app.throwError("Select a date");
                }
            });

            attendancePanel.add(checkBox);
        }
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
