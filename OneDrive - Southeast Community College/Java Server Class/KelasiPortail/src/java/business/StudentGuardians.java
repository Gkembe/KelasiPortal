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
public class StudentGuardians implements Serializable{
    
    private int guardianID, studentID;
    private String fullName, phone, email, address, occupation, relationship, isPrimary;
    private LocalDateTime createdAT, updatedAT;

    public StudentGuardians() {
    }

    public StudentGuardians(int guardianID, int studentID, String fullName, String phone, String email, String address, String occupation, String relationship, String isPrimary, LocalDateTime createdAT, LocalDateTime updatedAT) {
        this.guardianID = guardianID;
        this.studentID = studentID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.occupation = occupation;
        this.relationship = relationship;
        this.isPrimary = isPrimary;
        this.createdAT = createdAT;
        this.updatedAT = updatedAT;
    }

    public int getGuardianID() {
        return guardianID;
    }

    public void setGuardianID(int guardianID) {
        this.guardianID = guardianID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }
    
    
    
}
