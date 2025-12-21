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
public class School implements Serializable{
    
    private int schoolID;
    private String schoolName;
    private String shortName;
    private String registrationNumber;
    private String schoolType;
    private String schoolLevel;
    private String country;
    private String schoolCity;
    private String schoolAddress;
    private String schoolEmail;
    private String website;
    private String schoolLogo;
    private boolean isActive;
    private LocalDateTime createdAT;
    
    public School (){}

    public School(int schoolID, String schoolName, String shortName, String registrationNumber, String schoolType, String schoolLevel, 
            String country, String schoolCity, String schoolAddress, String schoolEmail, String website, String schoolLogo, boolean isActive, LocalDateTime createdAT) {
        this.schoolID = schoolID;
        this.schoolName = schoolName;
        this.shortName = shortName;
        this.registrationNumber = registrationNumber;
        this.schoolType = schoolType;
        this.schoolLevel = schoolLevel;
        this.country = country;
        this.schoolCity = schoolCity;
        this.schoolAddress = schoolAddress;
        this.schoolEmail = schoolEmail;
        this.website = website;
        this.schoolLogo = schoolLogo;
        this.isActive = isActive;
    }

    public School(String schoolName, String shortName, String registrationNumber, String schoolType, String schoolLevel, String country, 
            String schoolCity, String schoolAddress, String schoolEmail, String website, String schoolLogo, boolean isActive) {
        this.schoolName = schoolName;
        this.shortName = shortName;
        this.registrationNumber = registrationNumber;
        this.schoolType = schoolType;
        this.schoolLevel = schoolLevel;
        this.country = country;
        this.schoolCity = schoolCity;
        this.schoolAddress = schoolAddress;
        this.schoolEmail = schoolEmail;
        this.website = website;
        this.schoolLogo = schoolLogo;
        this.isActive = isActive;
    }
    
    

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
