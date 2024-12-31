package edusphere.models;

import javax.swing.tree.DefaultMutableTreeNode;
import java.time.LocalDate;
import java.util.ArrayList;

public class Class{

    private String className, subjectName,
                    course, semester, classType;

    public ArrayList<Student> aBatch = new ArrayList<>(20);
    public ArrayList<Student> bBatch = new ArrayList<>(20);
    public ArrayList<Student> cBatch = new ArrayList<>(20);
    public ArrayList<LocalDate> totalLectures;

    public Class(String className, String subjectName,
                 String course, String semester,
                 String classType) {

        this.className = className;
        this.subjectName = subjectName;
        this.course = course;
        this.semester = semester;
        this.classType = classType;
    }

    public Class(String className) {
        this.className = className;
        totalLectures = new ArrayList<>();
    }

    //Getters and setters

    public String getClassName() {
        return className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getClassType() {
        return classType;
    }

    public ArrayList<Student> getAllStudents() {
        // Create a new ArrayList to hold all students
        // Initial capacity is sum of all batch sizes for efficiency
        ArrayList<Student> allStudents = new ArrayList<>(
                aBatch.size() + bBatch.size() + cBatch.size()
        );

        // Add all students from each batch to our combined list
        allStudents.addAll(aBatch);
        allStudents.addAll(bBatch);
        allStudents.addAll(cBatch);

        return allStudents;
    }


    public ArrayList<LocalDate> getTotalLectures() {
        return totalLectures;
    }

    @Override
    public String toString() {
        return className;
    }
}
