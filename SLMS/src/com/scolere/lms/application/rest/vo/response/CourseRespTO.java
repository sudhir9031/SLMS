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
public class CourseRespTO extends CompletedStatusTO{
    private int courseSessionId;
	private int schoolId;
    private String schoolName;
    private int classId;
    private String classeName;
    private int hrmId;
    private String hrmName;
    private String courseId;
    private String courseName;
    private String courseDesc;
    private String authorName;
    private String authorImg;
    private List<ModuleRespTO> moduleList;
    private List<CourseRespTO> sessionList;
    private List<CourseRespTO> orgList;
        
//	Added in Phase 3 
    private String courseIcon;
    private int studentCount;
    private String sessionStartDate;
    private String sessionEndDate;    
    private String isSelfDriven;
    private String courseCompletionGrade;
    private String lastReviewedDate; //Phase-3 for ios
    private String courseReqStatus;
    
    
    public CourseRespTO() {
    }

    public CourseRespTO(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    @Override
    public String toString() {
        return "CourseRespTO{" + "courseId=" + courseId + ", courseName=" + courseName + '}';
    }
    
    
    
    public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public int getCourseSessionId() {
		return courseSessionId;
	}

	public void setCourseSessionId(int courseSessionId) {
		this.courseSessionId = courseSessionId;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClasseName() {
		return classeName;
	}

	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}

	public int getHrmId() {
		return hrmId;
	}

	public void setHrmId(int hrmId) {
		this.hrmId = hrmId;
	}

	public String getHrmName() {
		return hrmName;
	}

	public void setHrmName(String hrmName) {
		this.hrmName = hrmName;
	}

	public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<ModuleRespTO> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleRespTO> moduleList) {
        this.moduleList = moduleList;
    }

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public String getCourseIcon() {
		return courseIcon;
	}

	public void setCourseIcon(String courseIcon) {
		this.courseIcon = courseIcon;
	}

	public List<CourseRespTO> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<CourseRespTO> orgList) {
		this.orgList = orgList;
	}

	public String getSessionStartDate() {
		return sessionStartDate;
	}

	public void setSessionStartDate(String sessionStartDate) {
		this.sessionStartDate = sessionStartDate;
	}

	public String getSessionEndDate() {
		return sessionEndDate;
	}

	public void setSessionEndDate(String sessionEndDate) {
		this.sessionEndDate = sessionEndDate;
	}

	public String getIsSelfDriven() {
		return isSelfDriven;
	}

	public void setIsSelfDriven(String isSelfDriven) {
		this.isSelfDriven = isSelfDriven;
	}

	public String getCourseCompletionGrade() {
		return courseCompletionGrade;
	}

	public void setCourseCompletionGrade(String courseCompletionGrade) {
		this.courseCompletionGrade = courseCompletionGrade;
	}

	public List<CourseRespTO> getSessionList() {
		return sessionList;
	}

	public void setSessionList(List<CourseRespTO> sessionList) {
		this.sessionList = sessionList;
	}

	public String getCourseReqStatus() {
		return courseReqStatus;
	}

	public void setCourseReqStatus(String courseReqStatus) {
		this.courseReqStatus = courseReqStatus;
	}

	public String getLastReviewedDate() {
		return lastReviewedDate;
	}

	public void setLastReviewedDate(String lastReviewedDate) {
		this.lastReviewedDate = lastReviewedDate;
	}

    
}
