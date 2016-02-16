package com.scolere.lms.service.iface;

import java.util.List;

import com.scolere.lms.domain.exception.LmsServiceException;


public interface TeacherServiceIface {

	int updateCourseStatus(int courseSessionId,int statusCode) throws LmsServiceException;
	int updateCourseModuleStatus(int moduleSessionId,int statusCode) throws LmsServiceException;
	int updateCourseResourceStatus(int resourseSessionId,int statusCode) throws LmsServiceException;
	List<Integer> getCoursePercentage(int userId, int schoolId, int classId,int hrmId)throws LmsServiceException;
	List<Integer> getAssignmentPercentage(int userId, int schoolId,int classId, int hrmId)throws LmsServiceException;
	int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId, int statusCode, int userId,
			String dueDate)throws LmsServiceException;

	int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId,int assignmentId, int statusCode, int userId,
			String dueDate)throws LmsServiceException;
	int cancelAssignment(int schoolId, int classId, int hrmId, int courseId, int moduleId, int assignmentId,
			int statusCode, int userId)throws LmsServiceException;
	
	
}
