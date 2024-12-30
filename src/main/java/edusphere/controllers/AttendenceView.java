package edusphere.controllers;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;
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


    }

    public void initActionListeners() {
        backButton.addActionListener(e -> app.changeState("HomeView"));
    }

    public void loadAttendanceData() {
        attendancePanel.removeAll();

        JLabel aLabel = new JLabel("A Batch");
        aLabel.setFont(app.fontLarge);
        aLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attendancePanel.add(aLabel);

        addBatchToPanel(attendanceClass.aBatch);

        JLabel bLabel = new JLabel("B Batch");
        bLabel.setFont(app.fontLarge);
        bLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attendancePanel.add(bLabel);

        addBatchToPanel(attendanceClass.bBatch);

        JLabel cLabel = new JLabel("C Batch");
        cLabel.setFont(app.fontLarge);
        cLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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

            attendancePanel.add(checkBox);
        }
    }

    public void setAttendanceClass(Class attendanceClass) {
        this.attendanceClass = attendanceClass;
        loadAttendanceData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
