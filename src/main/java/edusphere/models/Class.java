package edusphere.models;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class Class extends DefaultMutableTreeNode {

    private String className, subjectName,
                    course, semester, classType;

    public ArrayList<Student> aBatch = new ArrayList<>(20);
    public ArrayList<Student> bBatch = new ArrayList<>(20);
    public ArrayList<Student> cBatch = new ArrayList<>(20);

    private int totalLectures = 0;

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

    public int getTotalLectures() {
        return totalLectures;
    }

    @Override
    public String toString() {
        return className;
    }
}