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


    public ArrayList<LocalDate> getTotalLectures() {
        return totalLectures;
    }

    @Override
    public String toString() {
        return className;
    }
}
