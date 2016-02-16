/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.controller;

import com.scolere.lms.application.rest.bus.iface.UserBusIface;
import com.scolere.lms.application.rest.bus.impl.UserBusImpl;
import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.UserRequest;
import com.scolere.lms.application.rest.vo.response.UserResponse;
import com.scolere.lms.common.utils.AppUtils;
import com.scolere.lms.common.utils.PropertyManager;
import com.scolere.lms.common.utils.StringEncrypter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dell
 */
@Path("/user")
public class UserController {

    UserBusIface restService = new UserBusImpl();
    Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * Default controller method
     * @return 
     */
    @GET
    public String defaultx() {
        String message = "Welcome to SLMS user webservices....";
        return message;
    }
    


    /**
     * User Request new Group
     * 
     * @param UserRequest
     * @param HttpServletRequest
     * @return UserResponse
     */
    @POST
    @Path("/requestCourse")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse registerNewGroup(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start requestCourse >>"+req);
        UserResponse userResponse = new UserResponse();
        
        try {
        	if(req.getUserid() == null || (req.getUserid() != null && req.getUserid().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userIdRequired);   //UserId validation     		
        	}
        	else if(req.getSchoolId() == null || (req.getSchoolId() != null && req.getSchoolId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_schoolIdRequired);        // School Id validation		
        	}
        	else if(req.getClassId() == null || (req.getClassId() != null && req.getClassId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_classIdRequired);        // Class Id Validation		
        	}
        	else if(req.getHomeRoomId() == null || (req.getHomeRoomId() != null && req.getHomeRoomId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_homeRoomIdRequired);       //Home Room Id Validation 		
        	}
        	else
        	{
            userResponse = restService.requestCourse(req);
        	}
			
        } catch (RestBusException ex) {
            logger.error("Exception # requestCourse - "+ex);
        }
        
        logger.debug("<< End requestCourse "); 
        
        return userResponse;
    }

    
    /**
     * User registration service.
     * 
     * @param UserRequest
     * @param HttpServletRequest
     * @return UserResponse
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse registration(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start registration >>"+req);
        UserResponse userResponse = new UserResponse();
        
        try {
        	
			if(req.getUserName() == null || (req.getUserName() != null && req.getUserName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userNameRequired);    //user name validation
        	}
        	else if(req.getFirstName() == null || (req.getFirstName() != null && req.getFirstName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_firstNameRequired);     //First name validation   		
        	}
        	else if(req.getLastName() == null || (req.getLastName() != null && req.getLastName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_lastNameRequired);    //    last Name Validation		
        	}
        	else if(req.getEmailId() == null || (req.getEmailId() != null && req.getEmailId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_emailIdRequired);      //  		Email Id validation
        	}
        	else if(req.getAdminEmailId() == null || (req.getAdminEmailId() != null && req.getAdminEmailId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_adminEmailIdRequired);      //  Admin Email Id validation
        	}
        	else if(req.getSchoolId() == null || (req.getSchoolId() != null && req.getSchoolId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_schoolIdRequired);        // School Id validation		
        	}
        	else if(req.getClassId() == null || (req.getClassId() != null && req.getClassId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_classIdRequired);        // Class Id Validation		
        	}
        	else if(req.getHomeRoomId() == null || (req.getHomeRoomId() != null && req.getHomeRoomId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_homeRoomIdRequired);       //Home Room Id Validation 		
        	}
        	else if(req.getUserPassword() == null || (req.getUserPassword() != null && req.getUserPassword().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userPasswordRequired);   //Password validation     		
        	}
        	else if(req.getTitle() == null || (req.getTitle() != null && req.getTitle().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_titleRequired);   //Title validation     		
        	}
        	else
        	{
            userResponse = restService.registration(req);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # registration - "+ex);
        }
        
        logger.debug("<< End registration "+userResponse); 
        
        return userResponse;
    }

    /**
     * Update Profile service.
     * 
     * @param UserRequest
     * @param HttpServletRequest
     * @return UserResponse
     */
    @POST
    @Path("/updateProfile")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse updateProfile(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start updateProfile >>"+req);
        UserResponse userResponse = new UserResponse();
        
        try {
        	if(req.getUserid() == null || (req.getUserid() != null && req.getUserid().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userIdRequired);   //UserId validation     		
        	}	
        	else if(req.getUserName() == null || (req.getUserName() != null && req.getUserName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userNameRequired);   //UserName validation     		
        	}	
        	else if(req.getTitle() == null || (req.getTitle() != null && req.getTitle().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_titleRequired);   //Title validation     		
        	}	
        	 else if(req.getFirstName() == null || (req.getFirstName() != null && req.getFirstName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_firstNameRequired);     //First name validation   		
        	}
        	else if(req.getLastName() == null || (req.getLastName() != null && req.getLastName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_lastNameRequired);    //    last Name Validation		
        	}	
        	else if(req.getEmailId() == null || (req.getEmailId() != null && req.getEmailId().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_emailIdRequired);      //  		Email Id validation
        	}
        	else if(req.getDob() == null || (req.getDob() != null && req.getDob().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userdobRequired);      //  		DOB validation
        	}
        	else if(req.getDescription() == null || (req.getDescription() != null && req.getDescription().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userDescRequired);      //  		description validation
        	}
        	else
        	{
            userResponse = restService.updateProfile(req);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # updateProfile - "+ex);
        }
        
        logger.debug("<< End updateProfile "); 
        
        return userResponse;
    }
    
    
    /**
     * Upload profile image
     * @param vin
     * @param customerId
     * @param uploadedInputStream
     * @param fileDetail
     * @return 
     */
    @POST
    @Path("/uploadProfileImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)          
    public UserResponse uploadProfileImage(
            @FormDataParam("userName") String userName,
            @FormDataParam("profilePhoto") InputStream uploadedInputStream,
            @FormDataParam("profilePhoto") FormDataContentDisposition fileDetail) {

        logger.debug("Start uploadProfileImage >>"+userName);
        UserResponse resp = new UserResponse();
        
        try {
        
        //Update PROFILE_IMG to database

    	if(userName == null || (userName != null && userName.trim().isEmpty()))
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_userNameRequired);	//user name validation
    	}
    	else if(fileDetail.getFileName().equals("") )
    	{
    		resp.setStatus(SLMSRestConstants.status_fieldRequired);
    		resp.setStatusMessage(SLMSRestConstants.message_fieldRequired);
    		resp.setErrorMessage(SLMSRestConstants.message_fileDetailRequired);	//file url validation
    	}
    	else
    	{
            String profileImgName = userName.substring(0, userName.indexOf("@"))+System.currentTimeMillis()+SLMSRestConstants.image_extension; //System.currentTimeMillis()
        boolean updateStatus =  restService.updateProfilePhoto(profileImgName,userName);
    	logger.debug("Image name saved into db ? "+updateStatus);
    	
        String uploadedFileLocation = PropertyManager.getProperty(SLMSRestConstants.location_userprofile)+profileImgName;
        logger.debug("Uploading file @: "+uploadedFileLocation);
        AppUtils.writeToFile(uploadedInputStream, uploadedFileLocation);
        logger.debug("Profile image successfully uploaded..");
        
        resp.setUploadLocation(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+profileImgName);
    	}
        resp.setStatus(SLMSRestConstants.status_success);
        resp.setStatusMessage(SLMSRestConstants.message_success);          
        } catch (Exception ex) {
            logger.error("Exception # uploadProfileImage - "+ex);
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());            
        }
        logger.debug("<< End uploadProfileImage "+resp); 
        
        return resp;        
    }

    
    /**
     * 
     * User login service.
     * 
     * @param UserRequest
     * @param HttpServletRequest
     * @return UserResponse
     * 
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse login(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start login >>"+req);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.login(req);
        } catch (RestBusException ex) {
            logger.error("Exception # login - "+ex);
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
        
        logger.debug("<< End login # "); 
        
        return userResponse;
    }    
    
    /**
     * Update facebook id into database. 
     * @param User Id
     * @param Facebook Id
     * @return UserResponse
     */
    @GET
    @Path("/setFBId/userId/{userId}/userFbId/{fbId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse setFBId(@PathParam("userId") int userId,@PathParam("fbId") String fbId) {
        logger.debug("Start setFBId >> userId="+userId+" | fbid="+fbId);
        UserResponse userResponse = new UserResponse();
        
        try {
        	if(userId <= 0)
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userIdRequired);
        	}
        	else if(fbId == null || (fbId != null && fbId.trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_facebookIdRequired);        		
        	}
        	else
        	{
            userResponse = restService.setFBId(userId, fbId);
        	}
            
        } catch (RestBusException ex) {
            logger.error("Exception # setFBId - "+ex);
        }
        
        logger.debug("<< End setFBId # "+userResponse); 
        
        return userResponse;
    }    
    
    
    /**
     * Get user details by Facebook id
     * @param fbId
     * @return 
     */
    @GET
    @Path("/getByFBId/userFbId/{fbId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getByFBId(@PathParam("fbId") String fbId) {
        logger.debug("Start getByFBId >> fbid = "+fbId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.getByFBId(fbId);
        } catch (RestBusException ex) {
            logger.error("Exception # getByFBId - "+ex);
        }
        
        logger.debug("<< End getByFBId # "+userResponse); 
        
        return userResponse;
    }    
        
    
    /**
     * Get user details by Facebook id & userType
     * @param fbId
     * @return 
     */
    @GET
    @Path("/getByFBId/userFbId/{fbId}/userType/{userType}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getByFBId(@PathParam("fbId") String fbId,@PathParam("userType") int userType) {
        logger.debug("Start getByFBId >> fbid = "+fbId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.getByFBId(fbId,userType);
        } catch (RestBusException ex) {
            logger.error("Exception # getByFBId - "+ex);
        }
        
        logger.debug("<< End getByFBId # "+userResponse); 
        
        return userResponse;
    }      
    
    /**
     * Email user password to registered email id.
     * @param userId
     * @return UserResponse
     */
    @GET
    @Path("/forgetPwd/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse forgetPwd(@PathParam("userId") String userId) {
        logger.debug("Start forgetPwd >> userId = "+userId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.forgetPwd(userId);
        } catch (RestBusException ex) {
            logger.error("Exception # forgetPwd - "+ex);
        }
        
        logger.debug("<< End forgetPwd # "+userResponse); 
        return userResponse;
    }
    
        
    /**
     * Change user password.
     * @param UserRequest
     * @param HttpServletRequest
     * @return UserResponse
     */
    @POST
    @Path("/changePwd")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse changePwd(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start changePwd >> "+req);
        UserResponse userResponse = null;
        
        try {
        	userResponse = new UserResponse();
        	if(req.getUserName() == null || (req.getUserName() != null && req.getUserName().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userNameRequired);    //user name validation
        	}
        	else if(req.getUserPassword() == null || (req.getUserPassword() != null && req.getUserPassword().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userPasswordRequired);   //Password validation     		
        	}
        	else if(req.getUserNewPassword() == null || (req.getUserNewPassword() != null && req.getUserNewPassword().trim().isEmpty()))
        	{
        		userResponse.setStatus(SLMSRestConstants.status_fieldRequired);
        		userResponse.setStatusMessage(SLMSRestConstants.message_fieldRequired);
        		userResponse.setErrorMessage(SLMSRestConstants.message_userNewPasswordRequired);   //New Password validation     		
        	}
        	else{
        	userResponse = restService.changePwd(req);
        	}
        } catch (RestBusException ex) {
            logger.error("Exception # changePwd - "+ex);
        }
        
        logger.debug("<< End changePwd # "+userResponse); 
        
        return userResponse;
    }      
    
    
    @GET
    @Path("/getFeedAccessType/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse getFeedAccessType(@PathParam("userId") int userId) {
        logger.debug("Start getFeedAccessType >> userId = "+userId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.getFeedAccessType(userId);
        } catch (RestBusException ex) {
            logger.error("Exception # getFeedAccessType - "+ex);
        }
        
        logger.debug("<< End getFeedAccessType # "+userResponse); 
        return userResponse;
    }    
    
    
    @GET
    @Path("/setFeedAccessType/userId/{userId}/accessTypeId/{accessTypeId}")
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse setFeedAccessType(@PathParam("userId") int userId,@PathParam("accessTypeId") int accessTypeId) {
        logger.debug("Start setFeedAccessType >> userId = "+userId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.setFeedAccessType(userId,accessTypeId);
        } catch (RestBusException ex) {
            logger.error("Exception # setFeedAccessType - "+ex);
        }
        
        logger.debug("<< End setFeedAccessType # "); 
        return userResponse;
    } 
    
    
    
    @GET
    @Path("/getFeedUsers/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse getFeedUsers(@PathParam("userId") int userId) {
        logger.debug("Start getFeedUsers >> userId = "+userId);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.getFeedUsers(userId);
        } catch (RestBusException ex) {
            logger.error("Exception # getFeedUsers - "+ex);
        }
        
        logger.debug("<< End getFeedUsers # "); 
        return userResponse;
    } 
    
    
    @GET
    @Path("/getMostActiveUsers")
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse getMostActiveUsers() {
        logger.debug("Start getMostActiveUsers >>");
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.getMostActivUsers();
        } catch (RestBusException ex) {
            logger.error("Exception # getMostActiveUsers - "+ex);
        }
        
        logger.debug("<< End getMostActiveUsers # "); 
        return userResponse;
    } 
    
    
    @POST
    @Path("/updateFollowersStatus")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)      
    public UserResponse updateFollowersStatus(UserRequest req,@Context HttpServletRequest request) {
        logger.debug("Start updateFollowersStatus >>"+req);
        UserResponse userResponse = null;
        
        try {
            userResponse = restService.updateFollowersStatus(req);
        } catch (RestBusException ex) {
            logger.debug("Exception # updateFollowersStatus - "+ex);
        }
        
        logger.debug("<< End updateFollowersStatus # "); 
        
        return userResponse;
    }        
    
    
    
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)     
    public UserRequest testUserReqVo()
    {
    
        UserRequest userDetail = new UserRequest();
        //UserTO userDetail=new UserTO();
        userDetail.setUserName("mayankd@ixeet.com");
        String pwd=new StringEncrypter().encrypt("ixeet@123");
        
        logger.debug("pwd = "+new StringEncrypter().decrypt(pwd));
        //userDetail.setUserPassword(pwd);
        
        userDetail.setFirstName("Mayank");
        userDetail.setLastName("Dixit");
        userDetail.setEmailId("mayankd@ixeet.com");
        userDetail.setSchoolId("s01");
        userDetail.setSchoolName("Sample School");
        userDetail.setHomeRoomId("hrm01");
        userDetail.setHomeRoomName("Sample HRM");
        userDetail.setClassId("cls01");
        userDetail.setClassName("Sample Class");
        userDetail.setAddress("Testtttt Address");
        userDetail.setAdminEmailId("admin@ixeet.com");
        userDetail.setTitle("Mr.");
        
        List<UserRequest> userList=new ArrayList<UserRequest>();
        UserRequest ur1=new UserRequest();
        ur1.setUserid("1");
        ur1.setIsFollowUpAllowed("1");
        userList.add(ur1 );
        

        ur1.setUserid("2");
        ur1.setIsFollowUpAllowed("0");
        userList.add(ur1 );
        
        ur1.setUserid("3");
        ur1.setIsFollowUpAllowed("0");
        userList.add(ur1 );

        userDetail.setUsersList(userList);
        
        return userDetail;
    }
    
    
    
    
    
}//End of class
