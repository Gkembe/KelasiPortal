/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kembe
 */
public class Teachers implements Serializable{
    
    
    private int teacherID, userID;
    private String firstName, lastName, subject, qualification, phoneNumber, officeLocation, isActive;
    private LocalDate hireDate;
    private LocalDateTime createdAT;
    private int schoolID;
    
    
    
    public Teachers(){}

    public Teachers(int teacherID, int userID, String firstName, String lastName, String subject, String qualification, String phoneNumber, String officeLocation, String isActive, LocalDate hireDate, LocalDateTime createdAT, int schoolID) {
        this.teacherID = teacherID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.isActive = isActive;
        this.hireDate = hireDate;
        this.createdAT = createdAT;
        this.schoolID = schoolID;
    }

    public Teachers(String firstName, String lastName, String subject, String qualification, String phoneNumber, String officeLocation, String isActive, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.isActive = isActive;
        this.hireDate = hireDate;
    }
    
    

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }
    public String getFormattedTime() {

        if (createdAT == null) {
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

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }
    
    
    
}
