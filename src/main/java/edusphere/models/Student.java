package edusphere.models;

import javax.swing.tree.DefaultMutableTreeNode;

public class Student extends DefaultMutableTreeNode {

    private String fullName, batch;
    private int rollNo;
    private String enrollmentNo;
    private boolean gender;

    private Integer totalLectures;
    private Integer presentLectures;
    private Integer attendance;

    private Integer ptt1Marks;
    private Integer ptt2Marks;
    private Integer pttAverage;

    public Student(String fullName, String batch) {
        super(fullName);
        this.fullName = fullName;
        this.batch = batch;
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

    public void setRollNo(int rollNo) {
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

    public Integer getPresentLectures() {
        return presentLectures;
    }

    public void setPresentLectures(Integer presentLectures) {
        this.presentLectures = presentLectures;
    }

    public int getTotalLectures() {
        return presentLectures;
    }

    public void setTotalLectures(Integer totalLectures) {
        this.presentLectures = totalLectures;
    }

    public float getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
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
}
