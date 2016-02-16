/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.vo.request;

import java.util.List;

import com.scolere.lms.application.rest.vo.response.AssignmentQuestionRespTO;

/**
 *
 * @author dell
 */

public class AssignmentRequest {
    private int assignmentResourceTxnId;
    private int assignmentSubmittedById;
    private int assignmentTypeId;
    private List<AssignmentQuestionRespTO> assignmentQuestions;

    //Added to handle auto grading of self lead courses
    private int schoolId;
    private int courseId;
    
    
    
    
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

	public int getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public List<AssignmentQuestionRespTO> getAssignmentQuestions() {
		return assignmentQuestions;
	}

	public void setAssignmentQuestions(List<AssignmentQuestionRespTO> assignmentQuestions) {
		this.assignmentQuestions = assignmentQuestions;
	}

	@Override
	public String toString() {
		return "AssignmentRequest [assignmentResourceTxnId=" + assignmentResourceTxnId + ", assignmentSubmittedById="
				+ assignmentSubmittedById + ", assignmentTypeId=" + assignmentTypeId + "]";
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
    
    
    
}

//End of class
