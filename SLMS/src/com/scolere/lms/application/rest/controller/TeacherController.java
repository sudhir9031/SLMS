package com.scolere.lms.application.rest.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.application.rest.bus.iface.TeacherBusIface;
import com.scolere.lms.application.rest.bus.impl.TeacherBusImpl;
import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.CommonRequest;
import com.scolere.lms.application.rest.vo.response.TeacherResponse;


@Path("/teacher")
public class TeacherController {

	TeacherBusIface restService = new TeacherBusImpl();
	Logger logger = LoggerFactory.getLogger(TeacherController.class);
	
    /**
     * Default controller method
     *
     * @return
     */
    @GET
    public String defaultx() {
        String message = "Welcome to SLMS teacher webservices....";
        return message;
    }
    	
    
    @GET
    @Path("/updateAssignmentStatus/{userId}/{schoolId}/{classId}/{hrmId}/{courseId}/{moduleId}/statusCode/{statusCode}/{dueDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse updateAssignmentStatus(@PathParam("userId") int userId,@PathParam("schoolId") int schoolId,@PathParam("classId") int classId,@PathParam("hrmId") int hrmId,@PathParam("courseId") int courseId,@PathParam("moduleId") int moduleId,@PathParam("statusCode") int statusCode,@PathParam("dueDate") String dueDate) {
        logger.debug("Start updateAssignmentStatus >> Status code = "+statusCode);
        TeacherResponse resp = null;

        try {

            resp = restService.updateAssignmentStatus(schoolId, classId, hrmId, courseId,moduleId,statusCode,userId,dueDate);
        	
        } catch (RestBusException ex) {
            logger.error("Exception # updateAssignmentStatus - " + ex);
        }

        logger.debug("<< End updateAssignmentStatus # ");
        
        return resp;
    }
    	    
    @GET
    @Path("/updateAssignmentStatus/{userId}/{schoolId}/{classId}/{hrmId}/{courseId}/{moduleId}/assignmentId/{assignmentId}/statusCode/{statusCode}/{dueDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse updateAssignmentStatusWithAll(@PathParam("userId") int userId,@PathParam("schoolId") int schoolId,@PathParam("classId") int classId,@PathParam("hrmId") int hrmId,@PathParam("courseId") int courseId,@PathParam("moduleId") int moduleId,@PathParam("assignmentId") int assignmentId,@PathParam("statusCode") int statusCode,@PathParam("dueDate") String dueDate) {
        logger.debug("Start updateAssignmentStatusWithAll >> Status code = "+statusCode);
        TeacherResponse resp = null;

        try {

            resp = restService.updateAssignmentStatus(schoolId, classId, hrmId, courseId,moduleId,assignmentId,statusCode,userId,dueDate);
        	
        } catch (RestBusException ex) {
            logger.error("Exception # updateAssignmentStatusWithAll - " + ex);
        }

        logger.debug("<< End updateAssignmentStatusWithAll # ");
        
        return resp;
    }
    
    @GET
    @Path("/cancelAssignment/{userId}/{schoolId}/{classId}/{hrmId}/{courseId}/{moduleId}/assignmentId/{assignmentId}/statusCode/{statusCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse cancelAssignment(@PathParam("userId") int userId,@PathParam("schoolId") int schoolId,@PathParam("classId") int classId,@PathParam("hrmId") int hrmId,@PathParam("courseId") int courseId,@PathParam("moduleId") int moduleId,@PathParam("assignmentId") int assignmentId,@PathParam("statusCode") int statusCode) {
        logger.debug("Start cancelAssignment >> Status code = "+statusCode);
        TeacherResponse resp = null;

        try {

            resp = restService.cancelAssignment(schoolId, classId, hrmId, courseId,moduleId,assignmentId,statusCode,userId);
        	
        } catch (RestBusException ex) {
            logger.error("Exception # cancelAssignment - " + ex);
        }

        logger.debug("<< End cancelAssignment # ");
        
        return resp;
    }
        
    
    @GET
    @Path("/updateResourseStatus/id/{resourceSessionId}/statusCode/{statusCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse updateResourseStatus(@PathParam("resourceSessionId") int resourceSessionId,@PathParam("statusCode") int statusCode) {
        logger.debug("Start updateResourseStatus for id >> "+resourceSessionId+" Status code = "+statusCode);
        TeacherResponse resp = null;

        try {
        	
        	if(resourceSessionId <= 0 )
        	{
        		resp = new TeacherResponse();
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_idRequired); 
        	}
        	else
        	{
            resp = restService.updateCourseResourceStatus(resourceSessionId,statusCode);
        	}        	
        	
        } catch (RestBusException ex) {
            logger.error("Exception # updateResourseStatus - " + ex);
        }

        logger.debug("<< End updateResourseStatus # " + resp);
        
        return resp;
    }
    	
	
    
    @GET
    @Path("/updateModuleStatus/id/{moduleSessionId}/statusCode/{statusCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse updateModuleStatus(@PathParam("moduleSessionId") int moduleSessionId,@PathParam("statusCode") int statusCode) {
        logger.debug("Start updateModuleStatus for id >> "+moduleSessionId+" Status code = "+statusCode);
        TeacherResponse resp = null;

        try {
        	
        	if(moduleSessionId <= 0 )
        	{
        		resp = new TeacherResponse();
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_idRequired); 
        	}
        	else
        	{
            resp = restService.updateCourseModuleStatus(moduleSessionId,statusCode);
            		
        	}        	
        	
        } catch (RestBusException ex) {
            logger.error("Exception # updateModuleStatus - " + ex);
        }

        logger.debug("<< End updateModuleStatus # " );
        
        return resp;
    }    
    

	
    @GET
    @Path("/updateCourseStatus/id/{courseSessionId}/statusCode/{statusCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherResponse updateCourseStatus(@PathParam("courseSessionId") int courseSessionId,@PathParam("statusCode") int statusCode) {
        logger.debug("Start updateCourseStatus for id >> "+courseSessionId+" Status code = "+statusCode);
        TeacherResponse resp = null;

        try {
        	
        	if(courseSessionId <= 0 )
        	{
        		resp = new TeacherResponse();
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_idRequired); 
        	}
        	else
        	{
            resp = restService.updateCourseStatus(courseSessionId, statusCode);
            		
        	}        	
        	
        } catch (RestBusException ex) {
            logger.error("Exception # updateCourseStatus - " + ex);
        }

        logger.debug("<< End updateCourseStatus # ");
        
        return resp;
    }    
      
    
    
    /**
     * This method returns percent of course and assignment. 
     * If request does not contain commentId it will return commentsList else sub-comments list.
     *  
     * @param CommonRequest
     * @return CommonResponse
     */
    
    @POST
    @Path("/getPercentage")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public TeacherResponse getFeedComments(CommonRequest req) {
        logger.debug(">> getFeedComments "+req);
        TeacherResponse resp = new TeacherResponse();    
        
    try {
    	
	    	if(req.getUserId() <= 0)
	    	{
	    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
	    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
	    		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
	    	}
	    	else
	    	{
	          resp = restService.getPercentage(req);
	    	}
    	
        }catch (Exception ex){
            logger.error("CourseController#getPercentage " +ex);
        }
        logger.debug("<< getFeedComments "+resp);
         
        return resp;
    }     
	
}//End of class
