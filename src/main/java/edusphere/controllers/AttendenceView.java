package edusphere.controllers;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;

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
        datePicker = new DatePicker();
        editor = new JFormattedTextField();

        editor.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 18));
        editor.setPreferredSize(new Dimension(150, editor.getPreferredSize().height));

        datePicker.setEditor(editor);

        datePickerPanel.add(editor);


    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
