/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.controller;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.application.rest.bus.iface.CommonBusIface;
import com.scolere.lms.application.rest.bus.impl.CommonBusImpl;
import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.CommonRequest;
import com.scolere.lms.application.rest.vo.response.CommonResponse;
import com.scolere.lms.application.rest.vo.response.SearchResponse;
import com.scolere.lms.common.utils.PropertyManager;

/**
 * 
 * @author dell
 */
@Path("/common")
public class CommonController {
    Logger logger = LoggerFactory.getLogger(CommonController.class);

    CommonBusIface restService = new CommonBusImpl();

    /**
     * Default controller method
     * @return
     */
    @GET
    public String defaultx() {
        String message = "Welcome to SLMS common webservices...."+PropertyManager.getProperty("TEST.MESSAGE.WELCOME");
        
        return message;
    }
       
    
    @POST
    @Path("/getRating")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getRating(CommonRequest req) {
    	logger.debug(">> getRating "+req);
        CommonResponse resp = new CommonResponse();    
        
        try {
            //userId | feedId or resourceId 
        	if(req.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	} 
        	else if(req.getResourceId() <= 0 && req.getFeedId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_feedOrResourceRequired);    
        	}
        	else        	
        	{
            resp = restService.getRating(req);
        	}
        } catch (RestBusException ex) {
            logger.error("CommonController#getRating " +ex);
        }
         logger.debug("<< getRating# ");
        return resp;
    }         
    
    
    @POST
    @Path("/setRating")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse setRating(CommonRequest req) {
        logger.debug(">> setRating "+req);
        CommonResponse resp = new CommonResponse();    
        
        try {
            //userId | feedId or resourceId | rating
        	if(req.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	} 
        	else if(req.getRating()==BigDecimal.ZERO)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_ratingRequired);    
        	}
        	else if(req.getResourceId() <= 0 && req.getFeedId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_feedOrResourceRequired);    
        	}
        	else        	
        	{
            resp = restService.setRating(req);
        	}
        } catch (RestBusException ex) {
            logger.error("CommonController#setRating " +ex);
        }
         logger.debug("<< setRating "+resp);
        return resp;
    }         
    
    
    /**
     * Global search    
     * @param req [userId | searchText | offset | noOfRecords]
     * @return
     */
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public SearchResponse search(CommonRequest req) {
        logger.debug(">> search "+req);
        SearchResponse resp = new SearchResponse();    
        
    try {
    	
    	if(req.getNoOfRecords() <= 0)
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_noOfRecordsRequired);    //noOfRecords validation
    	}
    	else
    	{
          resp = restService.search(req); 
    	}
    	
        }catch (Exception ex){
            logger.error("CommonController#search " +ex);
        }
        logger.debug("<< search ");
         
        return resp;
    } 
    
    
    
    /**
     * Search by category    
     * @param req [userId | searchText | offset | noOfRecords]
     * @return
     */
    @POST
    @Path("/search/{category}")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public SearchResponse searchByCategory(CommonRequest req,@PathParam("category") String category) {
        logger.debug(">> searchByCategory "+req);
        SearchResponse resp = new SearchResponse();    
        
    try {
    	
    	if(req.getNoOfRecords() <= 0)
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_noOfRecordsRequired);    //noOfRecords validation
    	}
    	else
    	{
          resp = restService.search(req,category); 
    	}
    	
        }catch (Exception ex){
            logger.error("CommonController#searchByCategory " +ex);
        }
        logger.debug("<< searchByCategory ");
         
        return resp;
    } 
    
    
    @POST
    @Path("/commentOnComment")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse CommentOnComment(CommonRequest req) {
        logger.debug(">> CommentOnComment "+req);
        CommonResponse resp = new CommonResponse();    
        
        try {
            //commentId | commentText | userId
        	if(req.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}  
        	else if(req.getCommentId() == 0 )
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_commentIdRequired);    //Comment Id validation
        	}
        	else
        	{
            resp = restService.commentOnComment(req);
        	}
        } catch (RestBusException ex) {
            logger.error("CourseController#CommentOnComment " +ex);
        }
         logger.debug("<< CommentOnComment "+resp);
        return resp;
    }         
    
    
    @POST
    @Path("/commentOnFeed")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse commentOnFeed(CommonRequest req) {
        logger.debug(">> commentOnFeed "+req);
        CommonResponse resp = new CommonResponse();    
        
        try {
            //feedId | commentText | user
        	if(req.getUserId() <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}  
        	else if(req.getFeedId() == 0 )
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_feedIdRequired);    //Resource Id validation
        	}
        	else
        	{
            resp = restService.commentOnFeed(req);
        	}
        } catch (RestBusException ex) {
            logger.error("CourseController#commentOnFeed " +ex);
        }
         logger.debug("<< commentOnFeed "+resp);
        return resp;
    }  
    
    
    @GET
    @Path("/likeOnComment/userId/{userId}/commentId/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse likeOnComment(@PathParam("userId") int userId,@PathParam("commentId") int commentId) {
        logger.debug("Start likeOnComment >> userId="+userId+" | commentId="+commentId);
        CommonResponse resp = null;
        
        try {
        	resp=new CommonResponse();
        	if(userId <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	}  
        	else if(commentId <= 0)
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
    @Path("/likeOnFeed/userId/{userId}/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse likeOnFeed(@PathParam("userId") int userId,@PathParam("feedId") int feedId) {
        logger.debug("Start likeOnResource >> userId="+userId+" | feedId="+feedId);
        CommonResponse resp = null;
        
        try {
        	resp = new CommonResponse();
        	if(userId <= 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_userIdRequired);    
        	} 
        	else if(feedId == 0)
        	{
        		resp.setStatus(SLMSRestConstants.status_fieldRequired);
        		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		resp.setErrorMessage(SLMSRestConstants.message_feedIdRequired);    //Resource Id validation
        	}
        	else
        	{
            resp = restService.likeOnFeed(userId, feedId);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # likeOnFeed - "+ex);
        }
        
        logger.debug("<< End likeOnFeed # "+resp); 
        
        return resp;
    }    
    
    
    @POST
    @Path("/getFeeds")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getFeeds(CommonRequest req) {
        logger.debug(">> getFeeds "+req);
        CommonResponse resp = new CommonResponse();    
        
    try {
    	
    	if(req.getNoOfRecords() <= 0)
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_noOfRecordsRequired);    //noOfRecords validation
    	}
    	else
    	{
    	  if(req.getEnableRating()==1) 
    		  resp = restService.getFeedsListWithRating(req); 
    	  else
    		  resp = restService.getFeedsList(req);  
    	}
    	
        }catch (Exception ex){
            logger.error("CourseController#getFeeds " +ex);
        }
        logger.debug("<< getFeeds "+resp);
         
        return resp;
    }    

    
    /***
     * Get Notifications list
     * @param req [userId | searchText | offset | noOfRecords]
     * @return
     */
    @POST
    @Path("/getNotifications")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getNotifications(CommonRequest req) {
        logger.debug(">> getNotifications "+req);
        CommonResponse resp = new CommonResponse();    
        
    try {
    	
    	if(req.getNoOfRecords() <= 0)
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_noOfRecordsRequired);    //noOfRecords validation
    	}
    	else
    	{
          resp = restService.getNotificationsList(req);
    	}
    	
        }catch (Exception ex){
            logger.error("CourseController#getNotifications " +ex);
        }
        logger.debug("<< getNotifications "+resp);
         
        return resp;
    }    
    
    
    @GET
    @Path("/updateNotificationStatus/userId/{userId}/feedId/{feedId}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse updateNotificationStatus(@PathParam("userId") int userId,@PathParam("feedId") int feedId,@PathParam("status") String status) {
        logger.debug(">> updateNotificationStatus feedId = "+feedId);
        CommonResponse resp = new CommonResponse();    
        
    try {

          resp = restService.updateNotificationStatus(userId, feedId,status);
    	
        }catch (Exception ex){
            logger.error("CourseController#updateNotificationStatus " +ex);
        }
        logger.debug("<< updateNotificationStatus "+resp);
         
        return resp;
    }    
    
    
    @GET
    @Path("/getFeedDetail/userId/{userId}/feedId/{feedId}/rating")
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getFeedDetailForRating(@PathParam("userId") int userId,@PathParam("feedId") int feedId) {
        logger.debug(">> getFeedDetailForRating feedId = "+feedId);
        CommonResponse resp = new CommonResponse();    
        
    try {
    	
          resp = restService.getFeedDetailForRating(userId, feedId);
    	
        }catch (Exception ex){
            logger.error("CourseController#getFeedDetailForRating " +ex);
        }
        logger.debug("<< getFeedDetailForRating "+resp);
         
        return resp;
    }    
    
    
    @GET
    @Path("/getFeedDetail/userId/{userId}/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getFeedDetail(@PathParam("userId") int userId,@PathParam("feedId") int feedId) {
        logger.debug(">> getFeedDetail feedId = "+feedId);
        CommonResponse resp = new CommonResponse();    
        
    try {
    	
          resp = restService.getFeedDetail(userId, feedId);
    	
        }catch (Exception ex){
            logger.error("CourseController#getFeedDetail " +ex);
        }
        logger.debug("<< getFeedDetail "+resp);
         
        return resp;
    }    

    
    
    /**
     * This method returns list of comments based on request parameter feedId & commentId. 
     * If request does not contain commentId it will return commentsList else sub-comments list.
     *  
     * @param CommonRequest
     * @return CommonResponse
     */
    
    @POST
    @Path("/getFeedComments")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)     
    public CommonResponse getFeedComments(CommonRequest req) {
        logger.debug(">> getFeedComments "+req);
        CommonResponse resp = new CommonResponse();    
        
    try {
    	
	    	if(req.getNoOfRecords() <= 0)
	    	{
	    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
	    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
	    		resp.setErrorMessage(SLMSRestConstants.message_noOfRecordsRequired);    //noOfRecords validation
	    	}
	    	else
	    	{
	          resp = restService.getFeedComments(req);
	    	}
    	
        }catch (Exception ex){
            logger.error("CourseController#getFeedComments " +ex);
        }
        logger.debug("<< getFeedComments "+resp);
         
        return resp;
    }     
    
    
    
    @GET
    @Path("/getCourse/feedId/{feedId}") //Not IN USE
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getCourseDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getCourseDetail for feedId >> "+feedId);
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getCourseDetail(feedId);
        } catch (RestBusException ex) {
            logger.error("Exception # getCourseDetail - " + ex);
        }

        logger.debug("<< End getCourseDetail # " + commonResponse);
        return commonResponse;
    }
    
    
    @GET
    @Path("/getUser/id/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getUserDetail(@PathParam("userId") int userId) {
        logger.debug("Start getUserDetail for id >> "+userId);
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getUserDetail(userId);
        } catch (RestBusException ex) {
            logger.error("Exception # getUserDetail - " + ex);
        }

        logger.debug("<< End getUserDetail # " + commonResponse);
        return commonResponse;
    }
    

    @GET
    @Path("/getModule/feedId/{feedId}") //NOT IN USE
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getModuleDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getModuleDetail for feedId >> "+feedId);
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getModuleDetail(feedId);
        } catch (RestBusException ex) {
            logger.error("Exception # getModuleDetail - " + ex);
        }

        logger.debug("<< End getModuleDetail # " + commonResponse);
        return commonResponse;
    }
    

    @GET
    @Path("/getResource/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getResourceDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getResourceDetail for id >> "+feedId);
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getResourseDetail(feedId); 
        } catch (RestBusException ex) {
            logger.error("Exception # getResourceDetail - " + ex);
        }

        logger.debug("<< End getResourceDetail # " + commonResponse);
        return commonResponse;
    }

    
    @GET
    @Path("/getAssignment/feedId/{feedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getAssignmentDetail(@PathParam("feedId") int feedId) {
        logger.debug("Start getAssignmentDetail for feedId >> "+feedId);
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getAssignmentDetail(feedId);
        } catch (RestBusException ex) {
            logger.error("Exception # getAssignmentDetail - " + ex);
        }

        logger.debug("<< End getAssignmentDetail # " + commonResponse);
        return commonResponse;
    }    
    
    
    /**
     * Master data service - returns school-class-home room mapping details.
     * @return 
     */
    
    @GET
    @Path("/getMasterData")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getMasterData() {
        logger.debug("Start getMasterData >> ");
        CommonResponse commonResponse = null;

        try {
            commonResponse = restService.getSchoolMasterData();
        } catch (RestBusException ex) {
            logger.error("Exception # getMasterData - " + ex);
        }

        logger.debug("<< End getMasterData # " + commonResponse);
        return commonResponse;
    }
    

    @GET
    @Path("/getMasterData/teacherId/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResponse getMasterData(@PathParam("teacherId") int teacherId) {
        logger.debug("Start getMasterData/teacherId >> "+teacherId);
        CommonResponse commonResponse = null;

        try {
        	
            commonResponse = restService.getSchoolMasterData(teacherId);
            
        } catch (RestBusException ex) {
            logger.error("Exception # getMasterData/teacherId - " + ex);
        }

        logger.debug("<< End getMasterData/teacherId # " + commonResponse);
        return commonResponse;
    }
    
    
    
}//End of class
