/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.domain.vo;

/**
 *
 * @author admin
 */
public class StudentDetailVo {
    private int studentDetailId;
    private int userId;
    private String title;
    private String fName;
    private String lName;
    private String emailId;
    private String adminEmailId;
    private String contactNo;
    private String birthDt;
    private String joiningDt;
    private String profile;
    private String socialProfile;
    private String address;
    private String lastUserIdCd;
    private String lastUpdtTm;

    //Added in phase-2
    private String description;    
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    public StudentDetailVo() {
    }
    

    public String getAdminEmailId() {
        return adminEmailId;
    }

    public void setAdminEmailId(String adminEmailId) {
        this.adminEmailId = adminEmailId;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    public int getStudentDetailId() {
        return studentDetailId;
    }

    public void setStudentDetailId(int studentDetailId) {
        this.studentDetailId = studentDetailId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(String birthDt) {
        this.birthDt = birthDt;
    }

    public String getJoiningDt() {
        return joiningDt;
    }

    public void setJoiningDt(String joiningDt) {
        this.joiningDt = joiningDt;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSocialProfile() {
        return socialProfile;
    }

    public void setSocialProfile(String socialProfile) {
        this.socialProfile = socialProfile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastUserIdCd() {
        return lastUserIdCd;
    }

    public void setLastUserIdCd(String lastUserIdCd) {
        this.lastUserIdCd = lastUserIdCd;
    }

    public String getLastUpdtTm() {
        return lastUpdtTm;
    }

    public void setLastUpdtTm(String lastUpdtTm) {
        this.lastUpdtTm = lastUpdtTm;
    }

    
    @Override
    public String toString() {
        return "StudentDetailVo{" + "studentDetailId=" + studentDetailId + ", userId=" + userId + ", title=" + title + ", fName=" + fName + ", lName=" + lName + ", emailId=" + emailId + ", adminEmailId=" + adminEmailId + ", contactNo=" + contactNo + ", birthDt=" + birthDt + ", joiningDt=" + joiningDt + ", profile=" + profile + ", socialProfile=" + socialProfile + ", address=" + address + ", lastUserIdCd=" + lastUserIdCd + ", lastUpdtTm=" + lastUpdtTm + '}';
    }
    
    
    
}
