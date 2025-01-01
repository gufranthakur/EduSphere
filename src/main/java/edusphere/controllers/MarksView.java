package edusphere.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;
import raven.swing.spinner.SpinnerProgress;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Objects;

public class MarksView {
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JPanel controlPanel;
    private JPanel marksPanel;
    private JComboBox testBox;

    private JButton averageButton;
    private SpinnerProgress highestMarksSpinner;
    private SpinnerProgress averageSpinner;
    private SpinnerProgress lowestSpinner;
    private JPanel statisticsPanel;

    private App app;
    private Class marksheetClass;
    private boolean isUpdatinSpinner = false;

    private int averageMarks, highestMarks, lowestMarks;

    public MarksView(App app) {
        this.app = app;
    }

    public void init() {
        marksPanel.setLayout(new BoxLayout(marksPanel, BoxLayout.Y_AXIS));

        testBox.addItem("Unit Test 1");
        testBox.addItem("Unit Test 2");
        testBox.addItem("Prelims");

        statisticsPanel.setBackground(app.getBackground().darker());
        statisticsPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 30");
    }

    public void initActionListeners() {
        backButton.addActionListener(e -> app.changeState("HomeView"));
        averageButton.addActionListener(e -> {

            ArrayList<Integer> marks = new ArrayList<>();
 
            for (Student student : marksheetClass.getAllStudents()) {
                if (Objects.equals(testBox.getSelectedItem(), "Unit Test 1")) {
                    if (student.getUt1Marks() != null) {
                        marks.add(student.getUt1Marks());
                    }
                } else if (Objects.equals(testBox.getSelectedItem(), "Unit Test 2")) {
                    if (student.getUt2Marks() != null) {
                        marks.add(student.getUt2Marks());
                    }
                } else if (Objects.equals(testBox.getSelectedItem(), "Prelims")) {
                    if (student.getPrelimMarks() != null) {
                        marks.add(student.getPrelimMarks());
                    }
                }
            }

            int totalMarks = 0;

            for (Integer mark : marks) {
                totalMarks += mark;
            }

            try {
                 averageMarks = totalMarks / marks.size();
                 highestMarks = Collections.max(marks);
                 lowestMarks = Collections.min(marks);
            } catch (ArithmeticException ex) {
                 averageMarks = 0;
                 highestMarks = 0;
                 lowestMarks = 0;
            }
            if (Objects.equals(testBox.getSelectedItem(), "Prelims")) {
                highestMarksSpinner.setMaximum(70);
                averageSpinner.setMaximum(70);
                lowestSpinner.setMaximum(70);

                highestMarksSpinner.setValue(highestMarks);
                highestMarksSpinner.setString(String.valueOf(highestMarks));
                averageSpinner.setValue(averageMarks);
                averageSpinner.setString(String.valueOf(averageMarks));
                lowestSpinner.setValue(lowestMarks);
                lowestSpinner.setString(String.valueOf(lowestMarks));
            } else {
                highestMarksSpinner.setMaximum(30);
                averageSpinner.setMaximum(30);
                lowestSpinner.setMaximum(30);

                highestMarksSpinner.setValue(highestMarks);
                highestMarksSpinner.setString(String.valueOf(highestMarks));
                averageSpinner.setValue(averageMarks);
                averageSpinner.setString(String.valueOf(averageMarks));
                lowestSpinner.setValue(lowestMarks);
                lowestSpinner.setString(String.valueOf(lowestMarks));
            }
        });

    }

    public void loadMarksDisplayData() {
        marksPanel.removeAll();

        highestMarksSpinner.setValue(0);
        highestMarksSpinner.setString("-");
        averageSpinner.setValue(0);
        averageSpinner.setString("-");
        lowestSpinner.setValue(0);
        lowestSpinner.setString("-");

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
        JPanel remarkPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel(student.getFullName());
        JLabel remarkLabel = new JLabel("");

        JSpinner marksSpinner = getMarksSpinner(student, spinnerDimension, remarkLabel, remarkPanel);

        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        nameLabel.setFont(app.fontSmall);

        remarkLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        remarkLabel.setFont(app.fontSmall);
        remarkLabel.setOpaque(false);

        detailsPanel.setBackground(app.getBackground().darker());
        detailsPanel.setOpaque(true);
        remarkPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        remarkPanel.setBackground(app.getBackground().brighter());
        remarkPanel.setOpaque(true);

        detailsPanel.add(nameLabel);
        detailsPanel.add(marksSpinner);

        remarkPanel.add(remarkLabel);

        rootPanel.setPreferredSize(rootDimension);
        rootPanel.setMaximumSize(rootDimension);

        rootPanel.setBackground(app.getBackground().darker());
        rootPanel.setOpaque(true);
        rootPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rootPanel.add(detailsPanel);
        rootPanel.add(remarkPanel);

        return rootPanel;
    }

    private JSpinner getMarksSpinner(Student student, Dimension spinnerDimension, JLabel remarkLabel, JPanel statusPanel) {
        JSpinner marksSpinner = new JSpinner();
        marksSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        marksSpinner.setFont(app.fontSmall);
        marksSpinner.setPreferredSize(spinnerDimension);

            marksSpinner.addChangeListener(e -> {

                if (isUpdatinSpinner) return;

                String selectedTest = (String) testBox.getSelectedItem();
                int value = (int) marksSpinner.getValue();

                // Handle new value input based on test type
                if (Objects.equals(selectedTest, "Unit Test 1")) {
                    if (value > 30) {
                        app.throwError("Unit test marks cannot be greater than 30");
                        marksSpinner.setValue(30);
                    } else {
                        student.setUt1Marks(value);
                        System.out.println("UT1 Marks updated: " + student.getUt1Marks());
                        setRemark(value, remarkLabel, statusPanel, false);
                    }
                } else if (Objects.equals(selectedTest, "Unit Test 2")) {
                    if (value > 30) {
                        app.throwError("Unit test marks cannot be greater than 30");
                        marksSpinner.setValue(30);
                    } else {
                        student.setUt2Marks(value);
                        System.out.println("UT2 Marks updated: " + student.getUt2Marks());
                        setRemark(value, remarkLabel, statusPanel, false);
                    }
                } else if (Objects.equals(selectedTest, "Prelims")) {
                    if (value > 70) {
                        app.throwError("Preliminary exam marks cannot be greater than 70");
                        marksSpinner.setValue(70);
                    } else {
                        student.setPrelimMarks(value);
                        System.out.println("Prelim Marks updated: " + student.getPrelimMarks());
                        setRemark(value, remarkLabel, statusPanel, true);
                    }
                }
            });


        testBox.addItemListener(e -> {
            isUpdatinSpinner = true;
            String selectedTest = (String) testBox.getSelectedItem();

            // Update spinner value when switching between tests
            if (Objects.equals(selectedTest, "Unit Test 1") && student.getUt1Marks() != null) {
                marksSpinner.setValue(student.getUt1Marks());
                setRemark((Integer) marksSpinner.getValue(), remarkLabel, statusPanel, false);
            } else if (Objects.equals(selectedTest, "Unit Test 2") && student.getUt2Marks() != null) {
                marksSpinner.setValue(student.getUt2Marks());
                setRemark((Integer) marksSpinner.getValue(), remarkLabel, statusPanel, false);
            } else if (Objects.equals(selectedTest, "Prelims") && student.getPrelimMarks() != null) {
                marksSpinner.setValue(student.getPrelimMarks());
                setRemark((Integer) marksSpinner.getValue(), remarkLabel, statusPanel, true);
            } else {
                remarkLabel.setText("");
                statusPanel.setBackground(app.getBackground());
                marksSpinner.setValue(0);  // Default to 0 if no marks exist
            }
            isUpdatinSpinner = false;
        });

        return marksSpinner;
    }

    private void setRemark(int value, JLabel remarkLabel, JPanel remarkPanel, boolean isPrelims) {
        if (isPrelims) {
            if (value < 25) {
                remarkLabel.setText("Fail");
                remarkPanel.setBackground(new Color(165, 7, 7));
            } else if (value < 40) {
                remarkLabel.setText("Okay");
                remarkPanel.setBackground(new Color(174, 81, 5));
            } else if (value < 60) {
                remarkLabel.setText("Good");
                remarkPanel.setBackground(new Color(43, 161, 12));
            } else {
                remarkLabel.setText("Excellent");
                remarkPanel.setBackground(new Color(31, 186, 116));
            }
        } else {
            if (value < 11) {
                remarkLabel.setText("Fail");
                remarkPanel.setBackground(new Color(165, 7, 7));
            } else if (value < 20) {
                remarkLabel.setText("Okay");
                remarkPanel.setBackground(new Color(174, 81, 5));
            } else if (value < 25) {
                remarkLabel.setText("Good");
                remarkPanel.setBackground(new Color(43, 161, 12));
            } else {
                remarkLabel.setText("Excellent");
                remarkPanel.setBackground(new Color(31, 186, 116));
            }
        }
    }

    public void setMarkSheetClass(Class marksheetClass) {
        this.marksheetClass = marksheetClass;
        loadMarksDisplayData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
