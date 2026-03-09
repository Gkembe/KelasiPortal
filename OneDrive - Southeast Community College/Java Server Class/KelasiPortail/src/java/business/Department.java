/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kembe
 */
public class Department implements Serializable{
    
    private int departmentID, SchoolID;
    private String departmentName, status;
    LocalDateTime createdAT;

    public Department() {
    }

    public Department(int departmentID, int SchoolID, String departmentName, String status, LocalDateTime createdAT) {
        this.departmentID = departmentID;
        this.SchoolID = SchoolID;
        this.departmentName = departmentName;
        this.status = status;
        this.createdAT = createdAT;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(int SchoolID) {
        this.SchoolID = SchoolID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
    
    public String getFormattedTime() {
        
        if(createdAT== null){
            return "";
        }
        
        LocalDateTime locatTime = createdAT.minusHours(6);
        
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");
        return locatTime.format(formatter);
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }
    
    
    
    
}
