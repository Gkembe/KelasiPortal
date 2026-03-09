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
public class Levels implements Serializable{
    
    
    private int levelID, schoolID;
    private Integer departmentID;
    private String levelName, levelCode;
    private LocalDateTime createdAT;
    private String status;

    public Levels() {
    }

    public Levels(int levelID, int schoolID, Integer departmenrID, String levelName, String levelCode, LocalDateTime createdAT, String status) {
        this.levelID = levelID;
        this.schoolID = schoolID;
        this.levelName = levelName;
        this.levelCode = levelCode;
        this.createdAT = createdAT;
        this.status = status;
        this.departmentID = departmentID;
    }

    public Levels(String levelName, String levelCode, String status) {
        this.levelName = levelName;
        this.levelCode = levelCode;
        this.status = status;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    
    

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
