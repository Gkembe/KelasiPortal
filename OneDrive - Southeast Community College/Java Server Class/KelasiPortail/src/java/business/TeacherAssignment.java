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
public class TeacherAssignment implements Serializable{
    
    private int assignmentID, schoolID, teacherID, levelID, courseID;
    private String status;
    private LocalDateTime createdAT;

    public TeacherAssignment() {
    }

    public TeacherAssignment(int assignmentID, int schoolID, int teacherID, int levelID, int courseID, String status, LocalDateTime createdAT) {
        this.assignmentID = assignmentID;
        this.schoolID = schoolID;
        this.teacherID = teacherID;
        this.levelID = levelID;
        this.courseID = courseID;
        this.status = status;
        this.createdAT = createdAT;
    }

    public TeacherAssignment(String status) {
        this.status = status;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }
    
    
}
