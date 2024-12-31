package edusphere.controllers;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;
import raven.swing.spinner.SpinnerProgress;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DatasheetView {
    private JPanel rootPanel;
    private JPanel rightPanel;
    private JPanel basicDetails;
    private JTextField nameField;
    private JRadioButton maleRadioButton;
    private JTextField enrollmentNoField;
    private JSpinner rollNoSpinner;
    private JRadioButton femaleRadioButton;
    private JPanel attendanceDetails;
    private JTextField presentSpinner;
    private SpinnerProgress attendanceSpinnerProgress;
    private JTextField attendanceSpinner;
    private JPanel performancePanel;
    private JTextField ut1spinner;
    private JTextField ut2spinner;
    private JScrollPane rightScrollPane;
    private SpinnerProgress marksSpinnerProgress;
    private JScrollPane leftScrollPane;
    private JTree studentTree;
    private JScrollPane studentTreeScrollPane;
    private DefaultMutableTreeNode root;
    private JTextField totalLectureSpinner;
    private JTextField pttAverageSpinner;
    private JButton backButton;
    private JPanel studentPanel;
    private JButton saveButton;

    public Class dataSheetClass;
    private App app;

    private JPopupMenu popupMenu;
    DefaultMutableTreeNode aBatch;
    DefaultMutableTreeNode bBatch;
    DefaultMutableTreeNode cBatch;

    public DatasheetView(App app) {
        this.app = app;

        root = new DefaultMutableTreeNode("Class");
        studentTree = new JTree(root);
        studentTree.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        studentPanel.add(studentTree, BorderLayout.CENTER);

        createPopupMenu();
    }

    public void init() {


        backButton.addActionListener(e -> app.changeState("HomeView"));
        saveButton.addActionListener(e -> setStudentDetails());

        maleRadioButton.addActionListener(e -> femaleRadioButton.setSelected(false));

        femaleRadioButton.addActionListener(e -> maleRadioButton.setSelected(false));

        studentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }

                Object userObject = selectedNode.getUserObject();
                if (userObject instanceof Student) {
                    rightPanel.setVisible(true);
                    displayStudentDetails((Student) userObject);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);

                if(e.isPopupTrigger()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            studentTree.getClosestPathForLocation(e.getX(), e.getY())
                            .getLastPathComponent();
                    if (node != null) {
                        studentTree.setSelectionPath(studentTree.getClosestPathForLocation(e.getX(), e.getY()));
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public void loadClassData() {
        rightPanel.setVisible(false);
        clearTreeNodes();

        aBatch = new DefaultMutableTreeNode("A Batch");
        bBatch = new DefaultMutableTreeNode("B Batch");
        cBatch = new DefaultMutableTreeNode("C Batch");

        if (dataSheetClass != null) {
            for (Student student : dataSheetClass.aBatch) {
                DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(student);
                aBatch.add(studentNode);
            }
            for (Student student : dataSheetClass.bBatch) {
                DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(student);
                bBatch.add(studentNode);
            }
            for (Student student : dataSheetClass.cBatch) {
                DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(student);
                cBatch.add(studentNode);
            }

            root.add(aBatch);
            root.add(bBatch);
            root.add(cBatch);

            root.setUserObject(dataSheetClass.getClassName());
        }

        refreshTree();
    }

    public void displayStudentDetails(Student student) {
        if (student == null) return;

        nameField.setText(student.getFullName());

        try {
            rollNoSpinner.setValue(student.getRollNo());
            enrollmentNoField.setText(student.getEnrollmentNo());
        } catch (NullPointerException e) {
            rollNoSpinner.setValue(0);
            enrollmentNoField.setText("-");
        } try {
            totalLectureSpinner.setText(String.valueOf(dataSheetClass.getTotalLectures().size()));
            presentSpinner.setText(String.valueOf(student.getPresentLectures().size()));

            int totalLectures = dataSheetClass.getTotalLectures().size();
            int presentLectures = student.getPresentLectures().size();

            if (totalLectures > 0) {
                float attendancePercentage = (presentLectures / (float) totalLectures) * 100;
                attendanceSpinner.setText(String.valueOf(attendancePercentage));

                attendanceSpinnerProgress.setString(String.valueOf(attendancePercentage));
                attendanceSpinnerProgress.setValue((int) attendancePercentage);
            } else {
                attendanceSpinner.setText("-");
            }

        } catch (ArithmeticException e) {
            presentSpinner.setText(String.valueOf(student.getPresentLectures().size()));
            attendanceSpinner.setText("-");
        }

        if (student.getUt1Marks() == null) ut1spinner.setText("-");
        else ut1spinner.setText(student.getUt1Marks().toString());

        if (student.getUt2Marks() == null) ut2spinner.setText("-");
        else ut2spinner.setText(student.getUt2Marks().toString());

        if (student.getUt1Marks() == null || student.getUt2Marks() == null) {
            pttAverageSpinner.setText("-");

            marksSpinnerProgress.setString("-");
            marksSpinnerProgress.setValue(0);
        } else {
            int average = (student.getUt1Marks() + student.getUt2Marks()) / 2;
            pttAverageSpinner.setText(String.valueOf(average));

            marksSpinnerProgress.setString(String.valueOf(average));
            marksSpinnerProgress.setValue(average);

        }
        app.revalidate();
        app.repaint();
    }

    public void createPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem addStudent = new JMenuItem("New Student");
        JMenuItem deleteStudent = new JMenuItem("Delete Student");

        addStudent.addActionListener(e -> addStudentNode());
        deleteStudent.addActionListener(e -> deleteStudent());

        popupMenu.add(addStudent);
        popupMenu.add(deleteStudent);
    }

    public void addStudentNode() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
        if (selectedNode == null) return;

        String batch = selectedNode.getUserObject().toString();
        String name = JOptionPane.showInputDialog("Enter student Name");

        if (name != null && !name.trim().isEmpty()) {
            Student newStudent = getStudent(name, batch);

            DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(newStudent);

            switch (batch) {
                case "A Batch":
                    aBatch.add(studentNode);
                    dataSheetClass.aBatch.add(newStudent);
                    break;
                case "B Batch":
                    bBatch.add(studentNode);
                    dataSheetClass.bBatch.add(newStudent);
                    break;
                case "C Batch":
                    cBatch.add(studentNode);
                    dataSheetClass.cBatch.add(newStudent);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Can't add student to another student.");
                    return;
            }
            refreshTree();
        }
    }

    public void deleteStudent() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof Student) {
            Student studentToRemove = (Student) selectedNode.getUserObject();
            DefaultTreeModel model = (DefaultTreeModel) studentTree.getModel();

            // Remove from tree
            model.removeNodeFromParent(selectedNode);

            // Remove from class's batch list
            if (studentToRemove.getBatch().equals("A")) {
                dataSheetClass.aBatch.remove(studentToRemove);
            } else if (studentToRemove.getBatch().equals("B")) {
                dataSheetClass.bBatch.remove(studentToRemove);
            } else if (studentToRemove.getBatch().equals("C")) {
                dataSheetClass.cBatch.remove(studentToRemove);
            }
        }
        refreshTree();
    }

    private void setStudentDetails() {
        System.out.println("Saved");
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
        if (node == null || !(node.getUserObject() instanceof Student)) return;

        Student selectedStudent = (Student) node.getUserObject();
        selectedStudent.setFullName(nameField.getText());
        selectedStudent.setRollNo((Integer) rollNoSpinner.getValue());
        selectedStudent.setEnrollmentNo(enrollmentNoField.getText());
        selectedStudent.setGender(maleRadioButton.isSelected());

        refreshTree();
    }

    private Student getStudent(String name, String batch) {
        Student newStudent = new Student(name, batch.split(" ")[0]); // Get "A" from "A Batch"

        newStudent.setRollNo(0);
        newStudent.setEnrollmentNo("");
        newStudent.setGender(true);
        newStudent.setUt1Marks(0);
        newStudent.setUt2Marks(0);
        newStudent.setPttAverage(0);
        return newStudent;
    }

    private void clearTreeNodes() {
        if (aBatch != null) aBatch.removeAllChildren();
        if (bBatch != null) bBatch.removeAllChildren();
        if (cBatch != null) cBatch.removeAllChildren();
        root.removeAllChildren();
    }

    public void refreshTree() {
        java.util.Enumeration<javax.swing.tree.TreePath> expanded = studentTree.getExpandedDescendants(new javax.swing.tree.TreePath(root));

        DefaultTreeModel model = (DefaultTreeModel) studentTree.getModel();
        model.reload();

        if (expanded != null) {
            while (expanded.hasMoreElements()) {
                javax.swing.tree.TreePath treePath = expanded.nextElement();
                studentTree.expandPath(treePath);
            }
        }
        studentTree.expandRow(0);
    }

    public void setDataSheetClass(Class dataSheetClass) {
        this.dataSheetClass = dataSheetClass;
        loadClassData();
        root.setUserObject(dataSheetClass.getClassName());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}