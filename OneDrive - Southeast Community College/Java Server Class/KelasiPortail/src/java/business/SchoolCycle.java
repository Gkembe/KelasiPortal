/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;

/**
 *
 * @author kembe
 */
public class SchoolCycle implements Serializable{
    
    private int schoolCycle, schoolID;
    private String cycleName, status;

    public SchoolCycle() {
    }

    public SchoolCycle(int schoolCycle, int schoolID, String cycleName, String status) {
        this.schoolCycle = schoolCycle;
        this.schoolID = schoolID;
        this.cycleName = cycleName;
        this.status = status;
    }

    public int getSchoolCycle() {
        return schoolCycle;
    }

    public void setSchoolCycle(int schoolCycle) {
        this.schoolCycle = schoolCycle;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
