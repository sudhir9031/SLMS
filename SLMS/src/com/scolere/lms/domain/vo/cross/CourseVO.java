/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.domain.vo.cross;

import java.util.List;

/**
 *
 * @author dell
 */
public class CourseVO {
    
    private int courseSessionId;
    private int moduleSessionId;

    private String startedOn;
    private String completedOn;
    private String completedStatus;
    private String completedPercentStatus;
    
    private int schoolId;
    private String schoolName;
    private int classId;
    private String classeName;
    private int hrmId;
    private String hrmName;
    private String sessionStartDate;
    private String sessionEndDate;
    
    private String courseId;
    private String courseName;
    private String authorName;
    private String authorImg;
    
    private String moduleId;
    private String moduleName;
    private String moduleDesc;
    private String courseDesc;

    private String assignmentEnableStatus;
    private int studentCount;
    private List<CourseVO> modules;
    private List<ResourseVO> resources;

//	Added in Phase 3 
    private String courseIcon;    
    private String isSelfDriven;
    private String courseReqStatus;
    
    public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getAssignmentEnableStatus() {
		return assignmentEnableStatus;
	}

	public void setAssignmentEnableStatus(String assignmentEnableStatus) {
		this.assignmentEnableStatus = assignmentEnableStatus;
	}

	public int getModuleSessionId() {
		return moduleSessionId;
	}

	public void setModuleSessionId(int moduleSessionId) {
		this.moduleSessionId = moduleSessionId;
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

	public List<CourseVO> getModules() {
        return modules;
    }

    public void setModules(List<CourseVO> modules) {
        this.modules = modules;
    }

    public List<ResourseVO> getResources() {
        return resources;
    }

    public void setResources(List<ResourseVO> resources) {
        this.resources = resources;
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
    
    public int getCourseSessionId() {
        return courseSessionId;
    }

    public void setCourseSessionId(int courseSessionId) {
        this.courseSessionId = courseSessionId;
    }
    
    public String getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public String getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(String completedStatus) {
        this.completedStatus = completedStatus;
    }

    public String getCompletedPercentStatus() {
        return completedPercentStatus;
    }

    public void setCompletedPercentStatus(String completedPercentStatus) {
        this.completedPercentStatus = completedPercentStatus;
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

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public String getCourseIcon() {
		return courseIcon;
	}

	public void setCourseIcon(String courseIcon) {
		this.courseIcon = courseIcon;
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

	public String getCourseReqStatus() {
		return courseReqStatus;
	}

	public void setCourseReqStatus(String courseReqStatus) {
		this.courseReqStatus = courseReqStatus;
	}

    
    
    
}
