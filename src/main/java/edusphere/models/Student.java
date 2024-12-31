package edusphere.models;


import java.time.LocalDate;
import java.util.ArrayList;

public class Student{

    private String fullName, batch;
    private Integer rollNo;
    private String enrollmentNo;
    private boolean gender;

    private ArrayList<LocalDate> presentLectures;

    private Integer ut1Marks;
    private Integer ut2Marks;
    private Integer pttAverage;
    private Integer prelimMarks;

    public Student(String fullName, String batch) {
        this.fullName = fullName;
        this.batch = batch;

        presentLectures = new ArrayList<>();
    }

    //Getters and setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Integer getUt1Marks() {
        return ut1Marks;
    }

    public void setUt1Marks(Integer ut1Marks) {
        this.ut1Marks = ut1Marks;
    }

    public Integer getUt2Marks() {
        return ut2Marks;
    }

    public void setUt2Marks(Integer ut2Marks) {
        this.ut2Marks = ut2Marks;
    }

    public Integer getPttAverage() {
        return pttAverage;
    }

    public void setPttAverage(Integer pttAverage) {
        this.pttAverage = pttAverage;
    }

    public String getBatch() {
        return batch;
    }

    public ArrayList<LocalDate> getPresentLectures() {
        return presentLectures;
    }

    public Integer getPrelimMarks() {
        return prelimMarks;
    }

    public void setPrelimMarks(Integer prelimMarks) {
        this.prelimMarks = prelimMarks;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return fullName;
    }
}



