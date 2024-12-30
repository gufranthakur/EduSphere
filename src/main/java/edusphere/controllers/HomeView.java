package edusphere.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import edusphere.App;
import edusphere.controllers.forms.CreateClass;
import edusphere.models.Class;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class HomeView {
    private JPanel rootPanel;
    private JPanel contentPanel;
    private JComboBox classBox;
    private JButton createClassButton;
    private JButton datasheetButton;
    private JButton attendanceButton;
    private JButton marksButton;
    private JButton questionBanksButton;
    private JButton signOutButton;

    private App app;

    public ArrayList<Class> classes;

    boolean createProcess = false;

    public HomeView(App app) {
        this.app = app;
    }

    public void init() {
        classes = new ArrayList<>(15);

        Dimension dimension = new Dimension(datasheetButton.getPreferredSize().width, 80);

        contentPanel.setBackground(app.getBackground().brighter());
        contentPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 30");

        datasheetButton.setPreferredSize(dimension);
        attendanceButton.setPreferredSize(dimension);
        marksButton.setPreferredSize(dimension);
        questionBanksButton.setPreferredSize(dimension);
        signOutButton.setPreferredSize(dimension);

        createClassButton.setBackground(new Color(81, 109, 237));


    }

    public void addActionListeners() {
        createClassButton.addActionListener(e -> {
            if (!createProcess) {
                new CreateClass(app);
                createProcess = true;

            } else {
                app.throwError("Another class is being created");
            }
        });

        datasheetButton.addActionListener(e -> {
            if (classBox.getSelectedItem() == null) app.throwError("No class selected");
             else app.changeState("DatasheetView");
        });

        attendanceButton.addActionListener(e -> {
            if (classBox.getSelectedItem() == null) app.throwError("No class selected");
            else app.changeState("AttendanceView");
        });

        classBox.addActionListener(e -> app.datasheetView.setDataSheetClass((Class)
                Objects.requireNonNull(classBox.getSelectedItem())));
    }

    public void addClassToBox(Class addedClass) {

        classes.add(addedClass);
        classBox.addItem(addedClass);

        createProcess = false;

    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
