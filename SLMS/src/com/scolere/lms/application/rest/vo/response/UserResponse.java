/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.vo.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author dell
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class UserResponse extends CommonRespTO{
    private String userId;
    private String userFbId;
    private String userType;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private String adminEmailId;
    private String schoolId;
    private String schoolName;
    private String address;
    private String classId;
    private String className;
    private String homeRoomId;
    private String homeRoomName;
    private String title;
    private String uploadLocation;
    private String profileImage;
    private String isFollowUpAllowed;

    //Update users
    private int userAccessTypeId;
    private List<KeyValTypeVO> accessTypeList;
    private List<UserResponse> usersList;    
    
    //Added in phase-2
    private String dob;
    private String description;    
    private int activityCount;

     
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}    
    
    public int getUserAccessTypeId() {
		return userAccessTypeId;
	}

	public void setUserAccessTypeId(int userAccessTypeId) {
		this.userAccessTypeId = userAccessTypeId;
	}

	public List<KeyValTypeVO> getAccessTypeList() {
		return accessTypeList;
	}

	public void setAccessTypeList(List<KeyValTypeVO> accessTypeList) {
		this.accessTypeList = accessTypeList;
	}

	public List<UserResponse> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<UserResponse> usersList) {
		this.usersList = usersList;
	}

	public String getIsFollowUpAllowed() {
		return isFollowUpAllowed;
	}

	public void setIsFollowUpAllowed(String isFollowUpAllowed) {
		this.isFollowUpAllowed = isFollowUpAllowed;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFbId() {
        return userFbId;
    }

    public void setUserFbId(String userFbId) {
        this.userFbId = userFbId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHomeRoomId() {
        return homeRoomId;
    }

    public void setHomeRoomId(String homeRoomId) {
        this.homeRoomId = homeRoomId;
    }

    public String getHomeRoomName() {
        return homeRoomName;
    }

    public void setHomeRoomName(String homeRoomName) {
        this.homeRoomName = homeRoomName;
    }

    @Override
    public String toString() {
        return "UserResponse{" + "userId=" + userId + ", userFbId=" + userFbId + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", schoolId=" + schoolId + ", schoolName=" + schoolName + ", address=" + address + ", classId=" + classId + ", className=" + className + ", homeRoomId=" + homeRoomId + ", homeRoomName=" + homeRoomName + ", title=" + title + '}';
    }

	public int getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(int activityCount) {
		this.activityCount = activityCount;
	}


    
}
