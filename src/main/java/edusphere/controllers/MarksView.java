package edusphere.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;

public class MarksView {
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JButton saveButton;
    private JButton backButton;
    private JPanel testPanel;
    private JPanel marksPanel;
    private JComboBox testBox;
    private JComboBox sortComboBox;

    private App app;
    private Class marksheetClass;

    public MarksView(App app) {
        this.app = app;
    }

    public void init() {

        marksPanel.setLayout(new BoxLayout(marksPanel, BoxLayout.Y_AXIS));

        testBox.addItem("Unit test 1");
        testBox.addItem("Unit test 2");
        testBox.addItem("Prelims");

        sortComboBox.addItem("Roll No.");
        sortComboBox.addItem("Highest Marks");
        sortComboBox.addItem("Lowest Marks");

        backButton.addActionListener(e -> app.changeState("HomeView"));

    }

    public void loadMarksDisplayData() {
        marksPanel.removeAll();

        JLabel aLabel = getBatchLabel("A Batch");
        marksPanel.add(aLabel);
        addBatchToPanel(marksheetClass.aBatch);

        JLabel bLabel = getBatchLabel("B Batch");
        marksPanel.add(bLabel);
        addBatchToPanel(marksheetClass.bBatch);

        JLabel cLabel = getBatchLabel("C Batch");
        marksPanel.add(cLabel);

        addBatchToPanel(marksheetClass.cBatch);

        app.revalidate();
        app.repaint();
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

    public void addBatchToPanel(ArrayList<Student> batch) {
        for (Student student : batch) {
            marksPanel.add(studentMarksStatus(student, new Dimension(300, 100),
                    new Dimension(100, 30)));
            marksPanel.add(Box.createVerticalStrut(5));
        }
    }

    public JPanel studentMarksStatus(Student student, Dimension rootDimension, Dimension spinnerDimension){
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        JPanel detailsPanel = new JPanel(new FlowLayout());
        JPanel statusPanel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel(student.getFullName());
        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        nameLabel.setFont(app.fontSmall);

        JSpinner marksSpinner = new JSpinner();
        marksSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        marksSpinner.setFont(app.fontSmall);
        marksSpinner.setPreferredSize(spinnerDimension);
        marksSpinner.addChangeListener(e -> {

        });

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        statusLabel.setFont(app.fontSmall);

        JLabel passFailLabel = new JLabel("");
        passFailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passFailLabel.setFont(app.fontSmall);

        detailsPanel.add(nameLabel);
        detailsPanel.add(marksSpinner);

        statusPanel.add(statusLabel);
        statusLabel.add(passFailLabel);

        detailsPanel.setBackground(app.getBackground().darker());
        detailsPanel.setOpaque(true);
        statusPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        statusPanel.setBackground(app.getBackground().brighter());
        statusPanel.setOpaque(true);

        rootPanel.setPreferredSize(rootDimension);
        rootPanel.setMaximumSize(rootDimension);

        rootPanel.setBackground(app.getBackground().darker());
        rootPanel.setOpaque(true);
        rootPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rootPanel.add(detailsPanel);
        rootPanel.add(statusPanel);

        return rootPanel;
    }

    public void setMarkSheetClass(Class marksheetClass) {
        this.marksheetClass = marksheetClass;
        loadMarksDisplayData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
