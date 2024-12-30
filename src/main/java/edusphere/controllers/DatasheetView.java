package edusphere.controllers;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import edusphere.App;
import edusphere.models.Class;
import edusphere.models.Student;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;

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
    private JSpinner presentSpinner;
    private JTextArea attendanceGraphTextArea;
    private JSpinner attendanceSpinner;
    private JPanel performancePanel;
    private JSpinner ptt1spinner;
    private JSpinner ptt2spinner;
    private JScrollPane rightScrollPane;
    private JTextArea graphArea;
    private JScrollPane leftScrollPane;
    private JTree studentTree;
    private JScrollPane studentTreeScrollPane;
    private DefaultMutableTreeNode root;
    private JSpinner totalLectureSpinner;
    private JSpinner pttAverageSpinner;
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

        studentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }

                if (selectedNode instanceof Student) {
                    displayStudentDetails((Student) selectedNode);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);

                if(e.isPopupTrigger()) {
                    int row = studentTree.getClosestRowForLocation(e.getX(), e.getY());
                    studentTree.setSelectionRow(row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void loadClassData() {
        aBatch = new DefaultMutableTreeNode("A Batch");
        bBatch = new DefaultMutableTreeNode("B Batch");
        cBatch = new DefaultMutableTreeNode("C Batch");

        for (Student student : dataSheetClass.aBatch) aBatch.add(student);
        for (Student student : dataSheetClass.bBatch) bBatch.add(student);
        for (Student student : dataSheetClass.cBatch) cBatch.add(student);

        root.add(aBatch);
        root.add(bBatch);
        root.add(cBatch);

        refreshTree();
    }

    public void displayStudentDetails(Student student) {
        nameField.setText(student.getFullName());

        try {
            rollNoSpinner.setValue(student.getRollNo());
            enrollmentNoField.setText(student.getEnrollmentNo());

            if (student.isGender()) maleRadioButton.setSelected(true);
            else femaleRadioButton.setSelected(true);

            totalLectureSpinner.setValue(dataSheetClass.getTotalLectures());
            presentSpinner.setValue(student.getTotalLectures());
            student.setAttendance(dataSheetClass.getTotalLectures() / student.getTotalLectures());
            attendanceSpinner.setValue(student.getAttendance());

            ptt1spinner.setValue(student.getPtt1Marks());
            ptt2spinner.setValue(student.getPtt2Marks());
            student.setPttAverage((student.getPtt1Marks() + student.getPtt2Marks()) / 2);
            pttAverageSpinner.setValue(student.getPttAverage());

        } catch (NullPointerException | ArithmeticException e) {
            //Go to hell.
            System.out.println("Exception Caught : " + Arrays.toString(e.getMessage().split("\n", 1)));
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

            switch (batch) {
                case "A Batch":
                    aBatch.add(newStudent);
                    dataSheetClass.aBatch.add(newStudent);
                    break;
                case "B Batch":
                    bBatch.add(newStudent);
                    dataSheetClass.bBatch.add(newStudent);
                    break;
                case "C Batch":
                    cBatch.add(newStudent);
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
        if (selectedNode != null) {
            DefaultTreeModel model = (DefaultTreeModel) studentTree.getModel();
            model.removeNodeFromParent(selectedNode);
        }
        refreshTree();
    }

    private void setStudentDetails() {
        Student selectedStudent = (Student) studentTree.getLastSelectedPathComponent();
        selectedStudent.setFullName(selectedStudent.getFullName());
        selectedStudent.setRollNo((Integer) rollNoSpinner.getValue());
        selectedStudent.setEnrollmentNo(enrollmentNoField.getText());
        selectedStudent.setGender(maleRadioButton.isSelected());

        selectedStudent.setTotalLectures((Integer) totalLectureSpinner.getValue());
        selectedStudent.setAttendance((Integer) attendanceSpinner.getValue());

        selectedStudent.setAttendance((Integer) attendanceSpinner.getValue());
        selectedStudent.setPtt1Marks((Integer) ptt1spinner.getValue());
        selectedStudent.setPtt2Marks((Integer) ptt2spinner.getValue());
        selectedStudent.setPttAverage((Integer) pttAverageSpinner.getValue());


    }

    private Student getStudent(String name, String batch) {
        Student newStudent = new Student(name, batch.split(" ")[0]); // Get "A" from "A Batch"

        newStudent.setRollNo(0);
        newStudent.setEnrollmentNo("");
        newStudent.setGender(true);
        newStudent.setTotalLectures(0);
        newStudent.setPresentLectures(0);
        newStudent.setAttendance(0);
        newStudent.setPtt1Marks(0);
        newStudent.setPtt2Marks(0);
        newStudent.setPttAverage(0);
        return newStudent;
    }

    public void refreshTree() {
        // I got this code from Claude AI and I have no idea how it works, but it works.
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
