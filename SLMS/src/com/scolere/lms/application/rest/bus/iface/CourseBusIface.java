/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.bus.iface;

import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.AssignmentRequest;
import com.scolere.lms.application.rest.vo.request.CourseRequest;
import com.scolere.lms.application.rest.vo.response.CourseResponse;

/**
 *
 * @author dell
 */
public interface CourseBusIface {

	CourseResponse retryCourse(int userId, int schoolId, int hrmId, int courseId) throws RestBusException;
	CourseResponse getAvailableCourses(int userId,int schoolId,int classId) throws RestBusException;
    CourseResponse getTeacherCourses(int teacherId) throws RestBusException;
	
    /**
     * This method returns list of all the courses associated with an user.
     * @param req
     * @return CourseResponse (Array of Courses {CoursesId,,CoursesName,PercentageOfCources,ArrayofModules(ModuleId,ModuleName,Status,PercentOfModule)}Status,StatusMessage)
     * @throws RestBusException 
     */
    CourseResponse getCourseDetailsByFeedId(int feedId) throws RestBusException;

    CourseResponse getCourseDetailsByCourseId(int courseId) throws RestBusException;

    CourseResponse getUserCourses(CourseRequest req) throws RestBusException;
    
    CourseResponse getUserCoursesWeb(CourseRequest req) throws RestBusException;
    
    CourseResponse getStudentCourses(CourseRequest req) throws RestBusException;

    CourseResponse getUserCoursesTeacher(CourseRequest req) throws RestBusException;
    
    CourseResponse getUserCourseDetailByTeacher(CourseRequest course) throws RestBusException;


   
    /**
     * Returns all the resources associated with a module.
     * @param req
     * @return CourseResponse
     * @throws RestBusException 
     */
    CourseResponse getModuleDetailsByFeedId(int feedId) throws RestBusException;

    CourseResponse getModuleDetailsByFeedIdForRating(int feedId,int userId) throws RestBusException;
    
    CourseResponse getModuleResources(CourseRequest req) throws RestBusException;

    CourseResponse getModuleResourcesForRating(CourseRequest req) throws RestBusException;
    
    CourseResponse commentOnComment(CourseRequest req) throws RestBusException;
    CourseResponse commentOnResource(CourseRequest req) throws RestBusException;
    
    CourseResponse likeOnComment(int userName,int commentId) throws RestBusException;
    CourseResponse likeOnResource(int userName,int resourceId) throws RestBusException;
    
    /*****Assignment services*****/
    CourseResponse getAssignmentsForTeacher(CourseRequest req) throws RestBusException;
    CourseResponse getAssignments(CourseRequest req) throws RestBusException;

    int uploadAssignment(int assignmentId,String resourceName,String resourceAuthor, String resourceDesc,int userId, String descTxt, String url, String thumbUrl, String authorImgUrl) throws RestBusException;

	CourseResponse rateAssignment(CourseRequest course) throws RestBusException;

	CourseResponse getTeacherAssignments(CourseRequest req) throws RestBusException;
	CourseResponse getTeacherAssignments2(CourseRequest course) throws RestBusException;

    CourseResponse getAssignmentDetail(int userId,int assignmentId) throws RestBusException;
	CourseResponse getAssignmentDetail2(int userId, int assignmentId,int schoolId) throws RestBusException;

	CourseResponse submitAssignment(AssignmentRequest req) throws RestBusException;
		

}
