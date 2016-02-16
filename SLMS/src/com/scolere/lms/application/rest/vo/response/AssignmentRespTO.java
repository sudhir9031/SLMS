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
public class AssignmentRespTO {
    private int assignmentResourceTxnId;
    private int assignmentId;
    private String assignmentName;
    private String assignmentDesc;
    private String assignmentStatus;
    private String assignmentSubmittedDate;
    private String assignmentSubmittedBy;
    private String assignmentSubmittedById;
    private String assignmentDueDate="";
    private String enableStatus;
    private String cancelStatus;

    private int courseId;
    private String courseName;
    private int moduleId;
    private String moduleName;
    
    private List<ResourceRespTO> attachedResources;
    private List<KeyValTypeVO> ratingParameters;
    private List<AssignmentRespTO> studentList;
    //Phase-3
    private int assignmentTypeId;    
    private String assignmentType;    
    private List<AssignmentQuestionRespTO> assignmentQuestions;
    //Uploaded resource params
    ResourceRespTO uploadedResources;
    
    
    public List<KeyValTypeVO> getRatingParameters() {
		return ratingParameters;
	}

	public void setRatingParameters(List<KeyValTypeVO> ratingParameters) {
		this.ratingParameters = ratingParameters;
	}

	public int getAssignmentResourceTxnId() {
		return assignmentResourceTxnId;
	}

	public void setAssignmentResourceTxnId(int assignmentResourceTxnId) {
		this.assignmentResourceTxnId = assignmentResourceTxnId;
	}

	public String getAssignmentSubmittedById() {
		return assignmentSubmittedById;
	}

	public void setAssignmentSubmittedById(String assignmentSubmittedById) {
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

    public List<ResourceRespTO> getAttachedResources() {
        return attachedResources;
    }

    public void setAttachedResources(List<ResourceRespTO> attachedResources) {
        this.attachedResources = attachedResources;
    }

	public List<AssignmentRespTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<AssignmentRespTO> studentList) {
		this.studentList = studentList;
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

	public List<AssignmentQuestionRespTO> getAssignmentQuestions() {
		return assignmentQuestions;
	}

	public void setAssignmentQuestions(List<AssignmentQuestionRespTO> assignmentQuestions) {
		this.assignmentQuestions = assignmentQuestions;
	}

	public int getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public ResourceRespTO getUploadedResources() {
		return uploadedResources;
	}

	public void setUploadedResources(ResourceRespTO uploadedResources) {
		this.uploadedResources = uploadedResources;
	}
    
    
    
}//End of class
