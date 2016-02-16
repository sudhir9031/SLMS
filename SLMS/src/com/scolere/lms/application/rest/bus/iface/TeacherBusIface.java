package com.scolere.lms.application.rest.bus.iface;

import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.CommonRequest;
import com.scolere.lms.application.rest.vo.response.TeacherResponse;


public interface TeacherBusIface {

	TeacherResponse cancelAssignment(int schoolId, int classId, int hrmId, int courseId, int moduleId, int assignmentId,
			int statusCode, int userId) throws RestBusException;
	TeacherResponse updateAssignmentStatus(int schoolId,int classId,int hrmId,int courseId,int moduleId,int statusCode,int userId,String dueDate) throws RestBusException;
	TeacherResponse updateAssignmentStatus(int schoolId,int classId,int hrmId,int courseId,int moduleId,int assignmentId,int statusCode,int userId,String dueDate) throws RestBusException;
	TeacherResponse updateCourseStatus(int courseSessionId,int statusCode) throws RestBusException;
	TeacherResponse updateCourseModuleStatus(int moduleSessionId,int statusCode) throws RestBusException;
	TeacherResponse updateCourseResourceStatus(int resourseSessionId,int statusCode) throws RestBusException;
	TeacherResponse getPercentage(CommonRequest req) throws RestBusException;
	
}
