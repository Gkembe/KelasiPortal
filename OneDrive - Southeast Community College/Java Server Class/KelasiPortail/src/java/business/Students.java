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
public class Students implements Serializable{
    
    private int schoolID, userID;
    private String registrationNumber, firstName, lastName, gender;
    private LocalDate dateOfBirth;
    private String gradeLevel,department;
    private LocalDate enrollmentDate;
    private String academicYear, phoneNumber, address, isActive;
    private LocalDateTime createdAT, updatedAT;
    
    
    public Students (){}

    public Students(int schoolID, int userID, String registrationNumber, String firstName, String lastName, String gender, LocalDate dateOfBirth, String gradeLevel, String department, LocalDate enrollmentDate, String academicYear, String phoneNumber, String address, String isActive, LocalDateTime createdAT, LocalDateTime updatedAT) {
        this.schoolID = schoolID;
        this.userID = userID;
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.gradeLevel = gradeLevel;
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.academicYear = academicYear;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.createdAT = createdAT;
        this.updatedAT = updatedAT;
    }

    public Students(String registrationNumber, String firstName, String lastName, String gender, LocalDate dateOfBirth, String gradeLevel, String department, LocalDate enrollmentDate, String academicYear, String phoneNumber, String address, String isActive, LocalDateTime createdAT, LocalDateTime updatedAT) {
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.gradeLevel = gradeLevel;
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.academicYear = academicYear;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.createdAT = createdAT;
        this.updatedAT = updatedAT;
    }
    
    

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }
    
    public String getCreatedFormattedTime() {

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

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }
    
    public String getUpdatedFormattedTime() {

        if (updatedAT == null) {
            return "";
        }

        LocalDateTime locatTime = updatedAT.minusHours(6);

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");
        return locatTime.format(formatter);
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }

    
    
    
}
