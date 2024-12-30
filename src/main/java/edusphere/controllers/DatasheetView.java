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

            if (student.isGender()) maleRadioButton.setSelected(true);
            else femaleRadioButton.setSelected(true);

            totalLectureSpinner.setValue(dataSheetClass.getTotalLectures());
            presentSpinner.setValue(student.getPresentLectures());

            // Calculate attendance safely
            if (student.getPresentLectures() != null && dataSheetClass.getTotalLectures() > 0) {
                float attendance = (student.getPresentLectures() * 100.0f) / dataSheetClass.getTotalLectures();
                attendanceSpinner.setValue((int) attendance);
                student.setAttendance((int) attendance);
            }

            ptt1spinner.setValue(student.getPtt1Marks());
            ptt2spinner.setValue(student.getPtt2Marks());

            // Calculate PTT average safely
            if (student.getPtt1Marks() != null && student.getPtt2Marks() != null) {
                int average = (student.getPtt1Marks() + student.getPtt2Marks()) / 2;
                pttAverageSpinner.setValue(average);
                student.setPttAverage(average);
            }

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ArithmeticException a) {
            System.out.println("Arithmetic Exception");
        } catch (IllegalArgumentException i) {
            System.out.println("Error : " + i.getMessage() + i.getCause());
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
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentTree.getLastSelectedPathComponent();
        if (node == null || !(node.getUserObject() instanceof Student)) return;

        Student selectedStudent = (Student) node.getUserObject();
        selectedStudent.setFullName(nameField.getText());
        selectedStudent.setRollNo((Integer) rollNoSpinner.getValue());
        selectedStudent.setEnrollmentNo(enrollmentNoField.getText());
        selectedStudent.setGender(maleRadioButton.isSelected());

        selectedStudent.setPresentLectures((Integer) presentSpinner.getValue());
        selectedStudent.setAttendance((Integer) attendanceSpinner.getValue());

        selectedStudent.setPtt1Marks((Integer) ptt1spinner.getValue());
        selectedStudent.setPtt2Marks((Integer) ptt2spinner.getValue());
        selectedStudent.setPttAverage((Integer) pttAverageSpinner.getValue());

        refreshTree();
    }

    private Student getStudent(String name, String batch) {
        Student newStudent = new Student(name, batch.split(" ")[0]); // Get "A" from "A Batch"

        newStudent.setRollNo(0);
        newStudent.setEnrollmentNo("");
        newStudent.setGender(true);
        newStudent.setPresentLectures(0);
        newStudent.setAttendance(0);
        newStudent.setPtt1Marks(0);
        newStudent.setPtt2Marks(0);
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