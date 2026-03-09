/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author kembe
 */
public class Courses implements Serializable{
    
    private int courseID, schoolID;
    private String courseName, courseCode;
    private LocalDateTime createdAT;
    private String status;

    public Courses() {
    }

    public Courses(int courseID, int schoolID, String courseName, String courseCode, LocalDateTime createdAT, String status) {
        this.courseID = courseID;
        this.schoolID = schoolID;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.createdAT = createdAT;
        this.status = status;
    }

    public Courses(String courseName, String courseCode, String status) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
