/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.domain.vo.cross;

/**
 *
 * @author dell
 */
public class AssignmentVO {
    private int assignmentResourceTxnId;
    private int assignmentId;
    private String assignmentName;
    private String assignmentDesc;
    private String assignmentStatus;
    private String assignmentSubmittedDate;
    private int assignmentSubmittedById;
    private String assignmentSubmittedBy;
    private String assignmentDueDate;
    private String enableStatus;
    private String cancelStatus;
    
    //These parameter has been aded for Assignment uses- 
    private int courseId;
    private String courseName;
    private int moduleId;
    private String moduleName;
    //schoolid    
    private int schoolId;
    //Phase-3
    private String assignmentType;
    private int assignmentTypeId;
    private int uploadedResourceId;
    private String uploadedResourceName;
    private String uploadedResourceDesc;
    private String uploadedResourceUrl;
    private int reviewGrade;
   
    
    public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getAssignmentResourceTxnId() {
		return assignmentResourceTxnId;
	}

	public void setAssignmentResourceTxnId(int assignmentResourceTxnId) {
		this.assignmentResourceTxnId = assignmentResourceTxnId;
	}

	public int getAssignmentSubmittedById() {
		return assignmentSubmittedById;
	}

	public void setAssignmentSubmittedById(int assignmentSubmittedById) {
		this.assignmentSubmittedById = assignmentSubmittedById;
	}

	public String getAssignmentDesc() {
		return assignmentDesc;
	}

	public void setAssignmentDesc(String assignmentDesc) {
		this.assignmentDesc = assignmentDesc;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public void setAssignmentDueDate(String assignmentDueDate) {
        this.assignmentDueDate = assignmentDueDate;
    }
    
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public String getAssignmentSubmittedDate() {
        return assignmentSubmittedDate;
    }

    public void setAssignmentSubmittedDate(String assignmentSubmittedDate) {
        this.assignmentSubmittedDate = assignmentSubmittedDate;
    }

    public String getAssignmentSubmittedBy() {
        return assignmentSubmittedBy;
    }

    public void setAssignmentSubmittedBy(String assignmentSubmittedBy) {
        this.assignmentSubmittedBy = assignmentSubmittedBy;
    }

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public int getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public int getUploadedResourceId() {
		return uploadedResourceId;
	}

	public void setUploadedResourceId(int uploadedResourceId) {
		this.uploadedResourceId = uploadedResourceId;
	}

	public String getUploadedResourceName() {
		return uploadedResourceName;
	}

	public void setUploadedResourceName(String uploadedResourceName) {
		this.uploadedResourceName = uploadedResourceName;
	}

	public String getUploadedResourceDesc() {
		return uploadedResourceDesc;
	}

	public void setUploadedResourceDesc(String uploadedResourceDesc) {
		this.uploadedResourceDesc = uploadedResourceDesc;
	}

	public String getUploadedResourceUrl() {
		return uploadedResourceUrl;
	}

	public void setUploadedResourceUrl(String uploadedResourceUrl) {
		this.uploadedResourceUrl = uploadedResourceUrl;
	}

	public int getReviewGrade() {
		return reviewGrade;
	}

	public void setReviewGrade(int reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
    
    
    
}
