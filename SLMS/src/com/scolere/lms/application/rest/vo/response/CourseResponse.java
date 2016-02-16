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
public class CourseResponse extends CommonRespTO{
    private CourseRespTO courseDetail;
    private ModuleRespTO moduleDetail;
    private AssignmentRespTO assignmentDetail;
    private List<CourseRespTO> courseList;
    private List<CourseRespTO> requestedCourseList;
    private List<ResourceRespTO> resourceList;
    private List<AssignmentRespTO> assignmentList;
    private List<SchoolRespTO> schoolList;
   
    
    
    public AssignmentRespTO getAssignmentDetail() {
		return assignmentDetail;
	}

	public void setAssignmentDetail(AssignmentRespTO assignmentDetail) {
		this.assignmentDetail = assignmentDetail;
	}

	public ModuleRespTO getModuleDetail() {
        return moduleDetail;
    }

    public void setModuleDetail(ModuleRespTO moduleDetail) {
        this.moduleDetail = moduleDetail;
    }
    
    public CourseRespTO getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseRespTO courseDetail) {
        this.courseDetail = courseDetail;
    }
    public List<CourseRespTO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseRespTO> courseList) {
        this.courseList = courseList;
    }

    public List<ResourceRespTO> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceRespTO> resourceList) {
        this.resourceList = resourceList;
    }

    public List<AssignmentRespTO> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<AssignmentRespTO> assignmentList) {
        this.assignmentList = assignmentList;
    }

	public List<SchoolRespTO> getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(List<SchoolRespTO> schoolList) {
		this.schoolList = schoolList;
	}

	public List<CourseRespTO> getRequestedCourseList() {
		return requestedCourseList;
	}

	public void setRequestedCourseList(List<CourseRespTO> requestedCourseList) {
		this.requestedCourseList = requestedCourseList;
	}

    
    
}//End of class
