/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.controller;

import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.application.rest.bus.iface.CourseBusIface;
import com.scolere.lms.application.rest.bus.impl.CourseBusImpl;
import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.AssignmentRequest;
import com.scolere.lms.application.rest.vo.request.CourseRequest;
import com.scolere.lms.application.rest.vo.response.CourseResponse;
import com.scolere.lms.common.utils.AppUtils;
import com.scolere.lms.common.utils.PropertyManager;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


/**
 * 
 * @author dell
 */
@Path("/course")
public class CourseController {

    CourseBusIface restService = new CourseBusImpl();
    Logger logger = LoggerFactory.getLogger(CourseController.class);
    /**
     * Default controller method 
     *
     * @return
     */
    @GET
    public String defaultx() {
        String message = "Welcome to SLMS course webservices....";
        return message;
    }
    
    
    @GET
    @Path("/retryCourse/userId/{userId}/schoolId/{schoolId}/hrmId/{hrmId}/courseId/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse retryCourse(@PathParam("userId") int userId,@PathParam("schoolId") int schoolId,@PathParam("hrmId") int hrmId,@PathParam("courseId") int courseId) {
        logger.debug("Start retryCourse >> "+userId);
        CourseResponse resp = null;

        try {
            resp = restService.retryCourse(userId,schoolId,hrmId,courseId);
        } catch (RestBusException ex) {
            logger.error("Exception # retryCourse - " + ex);
        }

        logger.debug("<< End retryCourse # ");
        return resp;
    }    
        

    
    @GET
    @Path("/getAvailableCourses/userId/{userId}/schoolId/{schoolId}/classId/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse getAvailableCourses(@PathParam("userId") int userId,@PathParam("schoolId") int schoolId,@PathParam("classId") int classId) {
        logger.debug("Start getAvailableCourses >> "+userId);
        CourseResponse resp = null;

        try {
            resp = restService.getAvailableCourses(userId,schoolId,classId);
        } catch (RestBusException ex) {
            logger.error("Exception # getAvailableCourses - " + ex);
        }

        logger.debug("<< End getAvailableCourses # ");
        return resp;
    }    
        
    
    
    @GET
    @Path("/getCourse/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse getCourseDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getCourseDetail for feedId >> "+feedId);
        CourseResponse resp = null;

        try {
            resp = restService.getCourseDetailsByFeedId(feedId);
        } catch (RestBusException ex) {
            logger.error("Exception # getCourseDetail - " + ex);
        }

        logger.debug("<< End getCourseDetail # ");
        return resp;
    }    
    
    
    
    @GET
    @Path("/getCourse/courseId/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse getCourseDetailByCourseId(@PathParam("courseId") int courseId) {
        logger.debug("Start getCourseDetail for courseId >> "+courseId);
        CourseResponse resp = null;

        try {
            resp = restService.getCourseDetailsByCourseId(courseId);
        } catch (RestBusException ex) {
            logger.error("Exception # getCourseDetail/courseId - " + ex);
        }

        logger.debug("<< End getCourseDetail # courseId ");
        return resp;
    }    
    
    
    
    @POST
    @Path("/getCourses")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getUserCourses(CourseRequest course) {
        logger.debug(">> getCourses "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getUserCourses(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getUserCourses " +ex);
        }
         logger.debug("<< getCourses ");
        return resp;
    }    
    
    
        
    @POST
    @Path("/getCourses/web")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getUserCoursesWeb(CourseRequest course) {
        logger.debug(">> getCourses/web "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getUserCoursesWeb(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getUserCourses " +ex);
        }
         logger.debug("<< getCourses/web ");
        return resp;
    }  
    
    @POST
    @Path("/getStudentCourses")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getStudentCourse(CourseRequest course) {
        logger.debug(">> getStudentCourses "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getStudentCourses(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getStudentCourses " +ex);
        }
         logger.debug("<< getStudentCourses ");
        return resp;
    }  
    
    
    @GET
    @Path("/getTeacherCourses/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getTeacherCourse(@PathParam("teacherId") int teacherId) {
        logger.debug(">> getTeacherCourse "+teacherId);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getTeacherCourses(teacherId);
        } catch (RestBusException ex) {
            logger.error("CourseController#getTeacherCourse " +ex);
        }
         logger.debug("<< getTeacherCourse ");
        return resp;
    }      
    
    
    @POST
    @Path("/getCourses/teacher")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getUserCoursesTeacher(CourseRequest course) {
        logger.debug(">> getCourses/teacher "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getUserCoursesTeacher(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getUserCoursesTeacher " +ex);
        }
         logger.debug("<< getCourses/teacher ");
        return resp;
    }  
    
    
    @POST
    @Path("/getCourseDetail/teacher")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getUserCourseDatailTeacher(CourseRequest course) {
        logger.debug(">> getCourseDetail/teacher "+course.getUserId());
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getUserCourseDetailByTeacher(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getUserCoursesTeacher " +ex);
        }
         logger.debug("<< getCourseDetail/teacher ");
        return resp;
    }  
        
    
    @GET
    @Path("/getModule/feedId/{feedId}/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse getModuleDetailForRating(@PathParam("feedId") int feedId,@PathParam("userId") int userId) {
        logger.debug("Start getModuleDetailForRating for feedId >> "+feedId);
        CourseResponse resp = null;

        try {
            resp = restService.getModuleDetailsByFeedIdForRating(feedId,userId);
        } catch (RestBusException ex) {
            logger.error("Exception # getModuleDetailForRating - " + ex);
        }

        logger.debug("<< End getModuleDetailForRating # ");
        return resp;
    }  
        
    
    @GET
    @Path("/getModule/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse getModuleDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getModuleDetail for feedId >> "+feedId);
        CourseResponse resp = null;

        try {
            resp = restService.getModuleDetailsByFeedId(feedId);
        } catch (RestBusException ex) {
            logger.error("Exception # getModule - " + ex);
        }

        logger.debug("<< End getModule # ");
        return resp;
    }  
    
    
    
    @POST
    @Path("/getModuleDetail")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getModuleDetail(CourseRequest req) {
        logger.debug(">> getModuleDetail "+req);
        CourseResponse resp = new CourseResponse();    
        
        try {
        	
        	if(req.getEnableRating()==1) 
            resp = restService.getModuleResourcesForRating(req);
        	else
        	resp = restService.getModuleResources(req);	
        	
        } catch (RestBusException ex) {
            logger.error("CourseController#getModuleDetail " +ex);
        }
         logger.debug("<< getModuleDetail# ");
        return resp;
    }     
    
    
    @POST
    @Path("/commentOnComment")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse CommentOnComment(CourseRequest course) {
        logger.debug(">> CommentOnComment "+course);
        CourseResponse resp = null;    
        
        try {
        	resp = new CourseResponse();
            //commentId | commentText | userId
        	if(course.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	} 
        	else if(course.getCommentId() == 0 )
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_commentIdRequired);    //Comment Id validation
        	}
        	else
        	{
            resp = restService.commentOnComment(course);
        	}
        } catch (RestBusException ex) {
            logger.error("CourseController#CommentOnComment " +ex);
        }
         logger.debug("<< CommentOnComment ");
        return resp;
    }         
    
    
    @POST
    @Path("/commentOnResourse")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse CommentOnResourse(CourseRequest course) {
        logger.debug(">> CommentOnResourse "+course);
        CourseResponse resp = null;    
        
        try {
        	resp = new CourseResponse();
            //resourceId | commentText | userId
        	if(course.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}   
        	else if(course.getResourceId() == 0 )
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_resourceIdRequired);    //Resource Id validation
        	}
        	else
        	{
            resp = restService.commentOnResource(course);
        	}
        } catch (RestBusException ex) {
            logger.error("CourseController#CommentOnResourse " +ex);
        }
         logger.debug("<< CommentOnResourse "+resp);
        return resp;
    }  
    
    
    @GET
    @Path("/likeOnComment/userId/{userId}/commentId/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse likeOnComment(@PathParam("userId") int userId,@PathParam("commentId") int commentId) {
        logger.debug("Start likeOnComment >> userId="+userId+" | commentId="+commentId);
        CourseResponse resp = null;
        
        try {
        	resp=new CourseResponse();
            //resourceId | commentText | userId
        	if(userId <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}    
        	else if(commentId == 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_commentIdRequired);    //Comment Id validation
        	}
        	else
        	{
            resp = restService.likeOnComment(userId, commentId);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # likeOnComment - "+ex);
        }
        
        logger.debug("<< End likeOnComment # "+resp); 
        
        return resp;
    }    
    
    @GET
    @Path("/likeOnResource/userId/{userId}/resourceId/{resourceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseResponse likeOnResource(@PathParam("userId") int userId,@PathParam("resourceId") int resourceId) {
        logger.debug("Start likeOnResource >> userId="+userId+" | resourceId="+resourceId);
        CourseResponse resp = null;
        
        try {
        	resp = new CourseResponse();
        	if(userId <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}     
        	else if(resourceId == 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_resourceIdRequired);    //Resource Id validation
        	}
        	else
        	{
            resp = restService.likeOnResource(userId, resourceId);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # likeOnResource - "+ex);
        }
        
        logger.debug("<< End likeOnResource # "+resp); 
        
        return resp;
    }    
   
    /***ASSIGNMENT SERVICES***/    
    
    
    @GET
    @Path("/getAssignmentDetail/assignmentId/{assignmentId}/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse getAssignmentDetail(@PathParam("assignmentId") int assignmentId,@PathParam("userId") int userId) {
        logger.debug("Start getAssignmentDetail >> "+assignmentId);
        CourseResponse resp = null;
        
        try {
        	//userId | searchText
            resp = restService.getAssignmentDetail(userId, assignmentId);
        } catch (RestBusException ex) {
            logger.error("Exception # getAssignmentDetail - "+ex);
        }
        logger.debug("<< End getAssignmentDetail # "); 
        
        return resp;
    }       
    
    @GET
    @Path("/getAssignmentDetail2/assignmentId/{assignmentId}/userId/{userId}/schoolId/{schoolId}")
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse getAssignmentDetail2(@PathParam("assignmentId") int assignmentId,@PathParam("userId") int userId,@PathParam("schoolId") int schoolId) {
        logger.debug("Start getAssignmentDetail2 >> "+assignmentId);
        CourseResponse resp = null;
        
        try {
        	//userId | searchText
            resp = restService.getAssignmentDetail2(userId, assignmentId,schoolId);
        } catch (RestBusException ex) {
            logger.error("Exception # getAssignmentDetail2 - "+ex);
        }
        logger.debug("<< End getAssignmentDetail2 # "); 
        
        return resp;
    }      
    
    /**
     * Assignment rating by teacher.
     * @param course
     * @return
     */
    
    @POST
    @Path("/rateAssignment")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse rateAssignment(CourseRequest course) {
        logger.debug("Start rateAssignment >> "+course);
        CourseResponse resp = null;
        
        try {
        	//assignmentResourceTxnId | ratingParameters

            resp = restService.rateAssignment(course);
            
        } catch (RestBusException ex) {
            logger.error("Exception # rateAssignment - "+ex);
        }
        logger.debug("<< End rateAssignment # "); 
        
        return resp;
    }       
    
    @POST
    @Path("/getTeacherAssignments/x")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse getAssignmentsForTeacher(CourseRequest course) {
        logger.debug("Start getAssignmentsForTeacher >> "+course);
        CourseResponse resp = null;
        
        try {
        	//schoolId | classId | hrmId | courseId | moduleId | status  + userId | searchText
            resp = restService.getAssignmentsForTeacher(course);
        } catch (RestBusException ex) {
            logger.error("Exception # getAssignmentsForTeacher - "+ex);
        }
        logger.debug("<< End getAssignmentsForTeacher # "); 
        
        return resp;
    }      
        
    
    
    @POST
    @Path("/getAssignments/teacher")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getTeacherAssignments(CourseRequest course) {
        logger.debug(">> getAssignments/teacher "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getTeacherAssignments(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getTeacherAssignments " +ex);
        }
         logger.debug("<< getAssignments/teacher "+resp);
        return resp;
    }  
             
    
    @POST
    @Path("/getAssignments/teacher2")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CourseResponse getTeacherAssignments2(CourseRequest course) {
        logger.debug(">> getTeacherAssignments2 "+course);
        CourseResponse resp = new CourseResponse();    
        
        try {
            resp = restService.getTeacherAssignments2(course);
        } catch (RestBusException ex) {
            logger.error("CourseController#getTeacherAssignments2 " +ex);
        }
         logger.debug("<< getTeacherAssignments2");
        return resp;
    }      
    
    @POST
    @Path("/getAssignments")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse getAssignments(CourseRequest course) {
        logger.debug("Start getAssignments >> "+course);
        CourseResponse resp = null;
        
        try {
        	//userId | searchText
            resp = restService.getAssignments(course);
        } catch (RestBusException ex) {
            logger.error("Exception # getAssignments - "+ex);
        }
        logger.debug("<< End getAssignments # "); 
        
        return resp;
    }   
    
    
    @POST
    @Path("/submitAssignment")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)  
    public CourseResponse submitAssignment(AssignmentRequest req) {
        logger.debug("Start submitAssignment >> "+req);
        CourseResponse resp = new CourseResponse();

		try {
			// assignmentSubmittedById | assignmentResourceTxnId |
			// assignmentTypeId | List assignmentQuestionsAnswers

			if (req.getAssignmentResourceTxnId() <= 0) {
				resp.setStatus(SLMSRestConstants.status_fieldRequired);
				resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
				resp.setErrorMessage(SLMSRestConstants.message_assignmentResourceTxnId);
			} else if (req.getAssignmentSubmittedById() <= 0) {
				resp.setStatus(SLMSRestConstants.status_fieldRequired);
				resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
				resp.setErrorMessage(SLMSRestConstants.message_assignmentSubmittedById);
			} else if (req.getAssignmentTypeId() <= 0) {
				resp.setStatus(SLMSRestConstants.status_fieldRequired);
				resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
				resp.setErrorMessage(SLMSRestConstants.message_assignmentTypeId);
			} else {
				resp = restService.submitAssignment(req);
			}
		} catch (RestBusException ex) {
			logger.error("Exception # submitAssignment - " + ex);
		}
		logger.debug("<< End submitAssignment # ");

		return resp;
	}    
    
    @POST
    @Path("/uploadResourceDetail")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)          
    public CourseResponse uploadImageDetail(
            @FormDataParam("assignmentId") int assignmentId,
            @FormDataParam("resourceName") String resourceName,
            @FormDataParam("resourceAuthor") String resourceAuthor,
            @FormDataParam("fileName") InputStream uploadedInputStream,
            @FormDataParam("fileName") FormDataContentDisposition fileDetail,
            @FormDataParam("userId") int userId,
            @FormDataParam("descTxt") String descTxt,
            @FormDataParam("uploadedUrl") String uploadedUrl
    		) {

    	 CourseResponse resp = new CourseResponse();
         
         try {
        	 
        	 if(assignmentId <= 0)
         	{
         		resp.setStatus(SLMSRestConstants.status_fieldRequired);
         		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
         		resp.setErrorMessage(SLMSRestConstants.message_assignmentIdRequired);    //Assignment Id validation
         	}  
        	 else if(resourceName == null || (resourceName != null && resourceName.trim().isEmpty()))
          	{
          		resp.setStatus(SLMSRestConstants.status_fieldRequired);
          		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
          		resp.setErrorMessage(SLMSRestConstants.message_resourceNameRequired);    //Resource name  validation
          	} 
        	 else if(resourceAuthor == null || (resourceAuthor != null && resourceAuthor.trim().isEmpty()))
           	{
           		resp.setStatus(SLMSRestConstants.status_fieldRequired);
           		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
           		resp.setErrorMessage(SLMSRestConstants.message_resourceAuthorRequired);    //Resource Author validation
           	}
        	 else if(userId <= 0)
         	{
         		resp.setStatus(SLMSRestConstants.status_fieldRequired);
         		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
         		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
         	}  
         	  else if(descTxt == null || (descTxt != null && descTxt.trim().isEmpty()))
            	{
            		resp.setStatus(SLMSRestConstants.status_fieldRequired);
            		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
            		resp.setErrorMessage(SLMSRestConstants.message_desc_textRequired);    //Description validation
            	}
	        	else if(fileDetail.getFileName().equals("") && (uploadedUrl==null ||(uploadedUrl != null && uploadedUrl.trim().isEmpty())))
	         	{
	         		resp.setStatus(SLMSRestConstants.status_fieldRequired);
	         		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
	         		resp.setErrorMessage(SLMSRestConstants.message_fileDetailRequired);  //File Details or Empty Url validation
	         	}
        	 
        	 else {
        	 String thumbImgUrl="common.png";
        	 String authorImgUrl="default-author.png";
        	 String resourceUrl;
        	 
        	//Case : File Uploading
        	 if(!fileDetail.getFileName().equals(""))
        	 {
        		 logger.debug("New file submitting ... ");
        		 //Generating file name
        		 Date currentDate = new Date();
        		 String fileExt = fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf("."));
        		 String tempFileName = userId+"_"+currentDate.getTime()+fileExt;
        		 logger.debug("Temp file name = "+tempFileName);
        		 
        		 //Writing file
        		 String uploadedFileLocation = PropertyManager.getProperty(SLMSRestConstants.location_assignments)+tempFileName;
        		 logger.debug("Temp file location = "+uploadedFileLocation);
        		 AppUtils.writeToFile(uploadedInputStream, uploadedFileLocation);
        		 logger.debug("Assignment resource successfully uploaded..");
        		 
        		 //Updating into database
        		 resourceUrl = PropertyManager.getProperty(SLMSRestConstants.baseUrl_resources)+tempFileName;
     		
        		 int updateStatus = restService.uploadAssignment(assignmentId, resourceName, resourceAuthor, descTxt, userId, descTxt, resourceUrl, thumbImgUrl, authorImgUrl);
            	 logger.debug("File Upload status ? "+updateStatus);  
            	 
            	 //Condition if uploaded url is also adding along with file upload
            	 /*
            	 if(uploadedUrl != null && !uploadedUrl.isEmpty())
            	 {
             		logger.debug("URL adding ... ");
            		resourceUrl = uploadedUrl;
            		int updateStatus2 = restService.uploadAssignment(assignmentId, resourceName, resourceAuthor, descTxt, userName, descTxt, resourceUrl, thumbImgUrl, authorImgUrl);
            		logger.debug("URL adding status ? "+updateStatus2);            		 
           		 
            	 }	 
            	 */
        	 }
        	        		 
        	 //Case : File Not Uploading
        	 if(fileDetail.getFileName().equals(""))
        	 {
        		logger.debug("URL submitting ... ");
        		resourceUrl = uploadedUrl;
        		
        		int updateStatus = restService.uploadAssignment(assignmentId, resourceName, resourceAuthor, descTxt, userId, descTxt, resourceUrl, thumbImgUrl, authorImgUrl);
        		
        		logger.debug("URL submitted status ? "+updateStatus);
        	 }

         //Common status---
         resp.setStatus(SLMSRestConstants.status_success);
         resp.setStatusMessage(SLMSRestConstants.message_success);  
       }
       }catch(Exception e){
    	   logger.error("Exception while uploading assignment.."+e);
           resp.setStatus(SLMSRestConstants.status_failure);
           resp.setStatusMessage(SLMSRestConstants.message_failure);  
           resp.setErrorMessage(e.getMessage());
       }
         

    return resp;
    }
    
   
    
}//End of class
