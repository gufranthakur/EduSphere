package edusphere.models;


import java.time.LocalDate;
import java.util.ArrayList;

public class Student{

    private String fullName, batch;
    private Integer rollNo;
    private String enrollmentNo;
    private boolean gender;

    private ArrayList<LocalDate> presentLectures;

    private Integer ptt1Marks;
    private Integer ptt2Marks;
    private Integer pttAverage;

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

    public Integer getPtt1Marks() {
        return ptt1Marks;
    }

    public void setPtt1Marks(Integer ptt1Marks) {
        this.ptt1Marks = ptt1Marks;
    }

    public Integer getPtt2Marks() {
        return ptt2Marks;
    }

    public void setPtt2Marks(Integer ptt2Marks) {
        this.ptt2Marks = ptt2Marks;
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

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return fullName;
    }
}



