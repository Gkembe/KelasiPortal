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
    private String firstName, middleName, lastName, gender, subject, qualification, phoneNumber, officeLocation, address, email, isActive;
    private LocalDate hireDate, dateOfBirth;
    private LocalDateTime createdAT;
    private int schoolID;
    
    
    
    public Teachers(){}

    public Teachers(int teacherID, int userID, String firstName, String middleName, String lastName, String gender, String subject, String qualification, String phoneNumber, String officeLocation, String address, String email, String isActive, LocalDate hireDate, LocalDateTime createdAT, int schoolID, LocalDate dateOfBirth) {
        this.teacherID = teacherID;
        this.userID = userID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.subject = subject;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.address = address;
        
        this.email = email;
        this.isActive = isActive;
        this.hireDate = hireDate;
        this.createdAT = createdAT;
        this.schoolID = schoolID;
        this.dateOfBirth = dateOfBirth;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
    
}
