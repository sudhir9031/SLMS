/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.bus.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.scolere.lms.application.rest.bus.iface.CourseBusIface;
import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.AssignmentRequest;
import com.scolere.lms.application.rest.vo.request.CourseRequest;
import com.scolere.lms.application.rest.vo.response.AssignmentQuestionRespTO;
import com.scolere.lms.application.rest.vo.response.AssignmentRespTO;
import com.scolere.lms.application.rest.vo.response.CommentRespTO;
import com.scolere.lms.application.rest.vo.response.CourseRespTO;
import com.scolere.lms.application.rest.vo.response.CourseResponse;
import com.scolere.lms.application.rest.vo.response.HomeRoomRespTO;
import com.scolere.lms.application.rest.vo.response.KeyValTypeVO;
import com.scolere.lms.application.rest.vo.response.MCQRespTO;
import com.scolere.lms.application.rest.vo.response.ModuleRespTO;
import com.scolere.lms.application.rest.vo.response.ResourceRespTO;
import com.scolere.lms.application.rest.vo.response.SchoolRespTO;
import com.scolere.lms.common.utils.PropertyManager;
import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.HomeRoomMasterVo;
import com.scolere.lms.domain.vo.SchoolMasterVo;
import com.scolere.lms.domain.vo.cross.AssignmentQuestionVO;
import com.scolere.lms.domain.vo.cross.AssignmentVO;
import com.scolere.lms.domain.vo.cross.CommentVO;
import com.scolere.lms.domain.vo.cross.CourseVO;
import com.scolere.lms.domain.vo.cross.FeedVO;
import com.scolere.lms.domain.vo.cross.ResourseVO;
import com.scolere.lms.service.iface.CommonServiceIface;
import com.scolere.lms.service.iface.CourseServiceIface;
import com.scolere.lms.service.impl.CommonServiceImpl;
import com.scolere.lms.service.impl.CourseServiceImpl;

/**
 *
 * @author dell
 */
public class CourseBusImpl implements CourseBusIface{


	@Override
	public CourseResponse retryCourse(int userId, int schoolId, int hrmId, int courseId) throws RestBusException {
        CourseResponse resp = new CourseResponse();
        CourseServiceIface service = new CourseServiceImpl();

        try {
        	//1)	Reset Course Session Data to started state(0)
			//2)	Delete All submitted assignments & its details

        	 int count = service.retryCourse(userId, schoolId, hrmId, courseId);
        	 if(count>0)
        	 {	 
             resp.setStatus(SLMSRestConstants.status_success);
             resp.setStatusMessage(SLMSRestConstants.message_success);
        	 }else{
                 resp.setStatus(SLMSRestConstants.status_success);
                 resp.setStatusMessage(SLMSRestConstants.message_noUpdate);        		 
        	 }
        	 
        }catch(Exception e){
        System.out.println("Exception # retryCourse "+e.getMessage());
        resp.setStatus(SLMSRestConstants.status_failure);
        resp.setStatusMessage(SLMSRestConstants.message_failure);
        resp.setErrorMessage(e.getMessage());            
        }
        
    return resp;
	}
	

    @Override
    public CourseResponse getAvailableCourses(int userId,int schoolId,int classId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
            	//Course list To Request
	             List<CourseVO> courseListFromDB = service.getAvailableCourses(userId,schoolId,classId); //Organisation Courses-Current department courses
	             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
	             
	             CourseRespTO courseResp = null;
	             ModuleRespTO modResp = null;
	             for(CourseVO vo : courseListFromDB)
	             	{
		             courseResp = new CourseRespTO();
		             courseResp.setCourseId(vo.getCourseId());
		             courseResp.setCourseName(vo.getCourseName());
		             courseResp.setCourseDesc(vo.getCourseDesc());
		             courseResp.setIsSelfDriven(vo.getIsSelfDriven());
		               
	             //Modules list (courseId)
	             
	                List<CourseVO> moduleListFromDB = service.getAvailableCourseModules(vo.getCourseId());
	                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
	                for(CourseVO mod:moduleListFromDB)
	                {
	                modResp = new ModuleRespTO();
	                modResp.setModuleId(mod.getModuleId());
	                modResp.setModuleName(mod.getModuleName());
	                moduleList.add(modResp);
	                }
	                
	               //Session List (courseId)
		             List<CourseVO> courseSessionListFromDB = service.getAvailableCoursesSessions(vo.getCourseId(),userId);
		             ArrayList<CourseRespTO> courseSessions = new ArrayList<CourseRespTO>(courseListFromDB.size());
		             CourseRespTO csresp=null;
		             for(CourseVO csvo : courseSessionListFromDB)
		             	{
		            	 csresp = new CourseRespTO();
		            	 csresp.setHrmId(csvo.getHrmId());
		            	 csresp.setSessionStartDate(csvo.getSessionStartDate());
		            	 courseSessions.add(csresp);
		             	}
	                
		         courseResp.setSessionList(courseSessions);   
	             courseResp.setModuleList(moduleList);
	             courses.add(courseResp);
	             }

	             resp.setCourseList(courses);//First node
	             
	            //Allready Requested Course list
	             List<CourseVO> reqCourseListFromDB = service.getRequestedCourses(userId,schoolId,classId);
	             ArrayList<CourseRespTO> reqCourses = new ArrayList<CourseRespTO>(courseListFromDB.size());
	             CourseRespTO csresp=null;
	             for(CourseVO csvo : reqCourseListFromDB)
	             	{
	            	 csresp = new CourseRespTO();
	            	 csresp.setHrmName(csvo.getHrmName());
	            	 csresp.setSessionStartDate(csvo.getSessionStartDate());
	            	 csresp.setCourseReqStatus(csvo.getCourseReqStatus());
	            	 reqCourses.add(csresp);
	             	}
	             
	             resp.setRequestedCourseList(reqCourses);//2nd node
	             
            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getAvailableCourses "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
	
	
	@Override
    public CourseResponse getAssignments(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
                //Assignment start
                List<AssignmentVO> assignmentListFromDB= service.getStudentAssignments(req.getUserId(),req.getSearchText());
                List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmentListFromDB.size());
                
                AssignmentRespTO assign = null;
                for( AssignmentVO asignment : assignmentListFromDB){
                	assign = new AssignmentRespTO();
                	assign.setAssignmentId(asignment.getAssignmentId());
                	assign.setAssignmentName(asignment.getAssignmentName());
                	assign.setAssignmentDesc(asignment.getAssignmentDesc());
                	assign.setAssignmentSubmittedDate(asignment.getAssignmentSubmittedDate());
                	assign.setAssignmentStatus(asignment.getAssignmentStatus());
                	assign.setAssignmentDueDate(asignment.getAssignmentDueDate());
                	assign.setCourseId(asignment.getCourseId());
                	assign.setCourseName(asignment.getCourseName());
                	assign.setModuleId(asignment.getModuleId());
                	assign.setModuleName(asignment.getModuleName());
                	assign.setAssignmentResourceTxnId(asignment.getAssignmentResourceTxnId());
                	/**
                	 * Assignment rating data - 
                	 * 
                	 * if assignment status=3 (reviewed) -> Add list of review status parameters
                	 * else add -> review parameters master data
                	 */
                	List<KeyValTypeVO> reviewParameters=null;
                	if(asignment.getAssignmentStatus().trim().equals("3"))
                	{
                		//Review status parameters
                		List<CommonKeyValueVO> ratingsFromDb=service.getRatingData(asignment.getAssignmentResourceTxnId());
                		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                		KeyValTypeVO kvtVo=null;
                		for(CommonKeyValueVO vo1: ratingsFromDb)
                		{
                			kvtVo=new KeyValTypeVO();
                			//Param
                			String[] param=vo1.getItemCode().split("-");
                			kvtVo.setKey(param[0]);
                			kvtVo.setValue(param[1]);
                			
                			//values
                			String[] val=vo1.getItemName().split("-");
                			List<KeyValTypeVO> childs=new ArrayList<KeyValTypeVO>(1);
                			childs.add(new KeyValTypeVO(val[0],val[1], null));
                			kvtVo.setChilds(childs);
                			reviewParameters.add(kvtVo);
                		}
                	}
                	assign.setRatingParameters(reviewParameters);
                	//Start - Assignment Resources
                    List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(req.getUserId(), assign.getAssignmentId());
                    List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
                    ResourceRespTO resourceRespTO = null;
                    for(ResourseVO vo5 : attachedResourcesFromDB)
                    {
                    resourceRespTO = new ResourceRespTO();
                    resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                    resourceRespTO.setResourceName(vo5.getResourceName());
                    resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                    resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                    resourceRespTO.setThumbImg(vo5.getThumbUrl());
                    resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                    resourceRespTO.setAuthorName(vo5.getAuthorName());
                    resourceRespTO.setAuthorImg(vo5.getAuthorImg());
                    
                    attachedResources.add(resourceRespTO);
                    }
                    
                    assign.setAttachedResources(attachedResources);
                	//End - Assignment Resources
                	
                	assignmentList.add(assign);
                }
                
                
            //Assignment end
            resp.setAssignmentList(assignmentList);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getAssignments "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
	

	@Override
    public CourseResponse getAssignmentDetail2(int userId,int assignmentId,int schoolId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
                //Assignment start
            	//[Course | Lesson | Assignment Detail | Assignment Type | Assignment Status (1: Not submitted / 2: Submitted / 3: Reviewed) -> Assignment Review Detail]	
            			
            	AssignmentVO asignment = service.getAssignmentDetail(userId, assignmentId);
                if(asignment != null)
                {
            	   AssignmentRespTO assign = new AssignmentRespTO();
                	assign.setAssignmentId(asignment.getAssignmentId());
                	assign.setAssignmentName(asignment.getAssignmentName());
                	assign.setAssignmentDesc(asignment.getAssignmentDesc());
                	assign.setAssignmentSubmittedDate(asignment.getAssignmentSubmittedDate());
                	assign.setAssignmentStatus(asignment.getAssignmentStatus());
                	assign.setAssignmentDueDate(asignment.getAssignmentDueDate());
                	assign.setCourseId(asignment.getCourseId());
                	assign.setCourseName(asignment.getCourseName());
                	assign.setModuleId(asignment.getModuleId());
                	assign.setModuleName(asignment.getModuleName());
                	assign.setAssignmentResourceTxnId(asignment.getAssignmentResourceTxnId());
                	assign.setAssignmentTypeId(asignment.getAssignmentTypeId());
                	assign.setAssignmentType(asignment.getAssignmentType());
                	
                	int assType=assign.getAssignmentTypeId();
 
                	//Start - Setting assignment question answer details
                	//(1) Assignment Type -> (2)Assignment Questions List -> (3)Assignment MCQ list 
                	
                	if(assType==1)
                	{
                		//Assignment Type : File Upload -> Get uploaded resource detail 
                		ResourceRespTO uploadedResource = new ResourceRespTO();	
                		uploadedResource.setResourceId(String.valueOf(asignment.getUploadedResourceId()));
                		uploadedResource.setResourceName(asignment.getUploadedResourceName());
                		uploadedResource.setResourceDesc(asignment.getUploadedResourceDesc());
                		uploadedResource.setResourceUrl(asignment.getUploadedResourceUrl());
                		assign.setUploadedResources(uploadedResource);
                		
                	}else if(assType==2 || assType==4)
                	{
                		//Assignment Type : Quest-Answer & true/false
                		List<AssignmentQuestionVO> questListFromDB=service.getAssignmentQuestionAnswers(asignment);
                		List<AssignmentQuestionRespTO> assignmentQuestions=new ArrayList<AssignmentQuestionRespTO>(questListFromDB.size());
                		AssignmentQuestionRespTO aqResp=null;
                		for(AssignmentQuestionVO qavo : questListFromDB)
                		{
                			aqResp=new AssignmentQuestionRespTO();
                			aqResp.setQuestionId(qavo.getQuestionId());
                			aqResp.setQuestionName(qavo.getQuestionName());
                			aqResp.setAnswerText(qavo.getAnswerText());
                			assignmentQuestions.add(aqResp);
                		}
                		assign.setAssignmentQuestions(assignmentQuestions);
                		
                	}else if(assType==3)
                	{
                		//Assignment Type : MCQ
                		 //[QUESTION_ID     QUESTION_NM]     OPTION_ID     OPTION_NM     ANSWER_ID 
                		List<AssignmentQuestionVO> questListFromDB=service.getAssignmentQuestionAnswers(asignment);
                		List<AssignmentQuestionRespTO> assignmentQuestions=new ArrayList<AssignmentQuestionRespTO>(questListFromDB.size());
                		
                		//Arranging data temporary into map
                		HashMap<String,ArrayList<MCQRespTO>> tempMap=new HashMap<String, ArrayList<MCQRespTO>>();
                		MCQRespTO mcq=null;
                		for(AssignmentQuestionVO qavo : questListFromDB)
                		{
                			String key=qavo.getQuestionId()+"~"+qavo.getQuestionName();
                			mcq=new MCQRespTO();
                			mcq.setOptionId(qavo.getOptionId());
                			mcq.setOptionName(qavo.getOptionName());
                			if(qavo.getAnswerId()==qavo.getOptionId())
                			{
                				mcq.setSelected(true);
                			}else{
                				mcq.setSelected(false);
                			}
                			//Setting into map
                			if(tempMap.get(key)!=null)
                			{
                				tempMap.get(key).add(mcq);
                			}
                			else
                			{
                				tempMap.put(key, new ArrayList<MCQRespTO>());
                				tempMap.get(key).add(mcq);
                			}
                			
                		}
                		
                		//Setting response
                		AssignmentQuestionRespTO aqResp=null;
                		for(String key:tempMap.keySet())
                		{
                			aqResp=new AssignmentQuestionRespTO();
                			String[] idQuest=key.split("~");
                			aqResp.setQuestionId(Integer.parseInt(idQuest[0]));
                			aqResp.setQuestionName(idQuest[1]);
                			aqResp.setOptions(tempMap.get(key));
                			
                			assignmentQuestions.add(aqResp);
                		}
                		
                		assign.setAssignmentQuestions(assignmentQuestions);
                	}//end type=3
                	

                	
                	/**
                	 * Assignment rating data - 
                	 * 
                	 * if assignment status=3 (reviewed) -> Add list of review status parameters
                	 * else add -> review parameters master data
                	 */
                	List<KeyValTypeVO> reviewParameters=null;
                	if(asignment.getAssignmentStatus().trim().equals("3"))
                	{
                		//Review status parameters
                		List<CommonKeyValueVO> ratingsFromDb=service.getRatingData(asignment.getAssignmentResourceTxnId());
                		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                		KeyValTypeVO kvtVo=null;
                		for(CommonKeyValueVO vo1: ratingsFromDb)
                		{
                			kvtVo=new KeyValTypeVO();
                			//Param
                			String[] param=vo1.getItemCode().split("-");
                			kvtVo.setKey(param[0]);
                			kvtVo.setValue(param[1]);
                			
                			//values
                			String[] val=vo1.getItemName().split("-");
                			List<KeyValTypeVO> childs=new ArrayList<KeyValTypeVO>(1);
                			childs.add(new KeyValTypeVO(val[0],val[1], null));
                			kvtVo.setChilds(childs);
                			reviewParameters.add(kvtVo);
                		}
                	}
                	else
                	{
                		//Review Parameters master data
                		List<CommonKeyValueVO> ratingsFromDb=service.getRatingMasterData(schoolId);
                		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                		//Review Parameters values
                   		List<CommonKeyValueVO> ratingsValuesFromDb=service.getRatingValuesMasterData(schoolId);
                   		List<KeyValTypeVO> ratingsValues = null;
                		KeyValTypeVO kvtVo=null;
                		for(CommonKeyValueVO vo1: ratingsFromDb)
                		{
                			kvtVo=new KeyValTypeVO();
                			kvtVo.setKey(vo1.getItemCode());
                			kvtVo.setValue(vo1.getItemName());
                			//Review param values
                       		ratingsValues = new ArrayList<KeyValTypeVO>(ratingsValuesFromDb.size());
                    		KeyValTypeVO kvtVo2=null;   
                    		for(CommonKeyValueVO vo2: ratingsValuesFromDb)
                    		{
                    			kvtVo2=new KeyValTypeVO();
                    			kvtVo2.setKey(vo2.getItemCode());
                    			kvtVo2.setValue(vo2.getItemName());                    			
                    			ratingsValues.add(kvtVo2);
                    		}
                    		kvtVo.setChilds(ratingsValues);
                    		
                			reviewParameters.add(kvtVo);
                		}                		
                		
                	}
                	
                	assign.setRatingParameters(reviewParameters);
                	//End assignment rating data
                	
                    //Assignment end
                    resp.setAssignmentDetail(assign);   
                    resp.setStatus(SLMSRestConstants.status_success);
                    resp.setStatusMessage(SLMSRestConstants.message_success); 
                 }else{
                     resp.setStatus(SLMSRestConstants.status_success);
                     resp.setStatusMessage(SLMSRestConstants.message_recordnotfound);                 	 
                 }

        }catch(Exception e){
            System.out.println("Exception # getAssignmentDetail2 "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
	

	@Override
    public CourseResponse getAssignmentDetail(int userId,int assignmentId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
                //Assignment start

            	AssignmentVO asignment = service.getAssignmentDetail(userId, assignmentId);
                if(asignment != null)
                {
            	   AssignmentRespTO assign = new AssignmentRespTO();
                	assign.setAssignmentId(asignment.getAssignmentId());
                	assign.setAssignmentName(asignment.getAssignmentName());
                	assign.setAssignmentDesc(asignment.getAssignmentDesc());
                	assign.setAssignmentSubmittedDate(asignment.getAssignmentSubmittedDate());
                	assign.setAssignmentStatus(asignment.getAssignmentStatus());
                	assign.setAssignmentDueDate(asignment.getAssignmentDueDate());
                	assign.setCourseId(asignment.getCourseId());
                	assign.setCourseName(asignment.getCourseName());
                	assign.setModuleId(asignment.getModuleId());
                	assign.setModuleName(asignment.getModuleName());
                	assign.setAssignmentResourceTxnId(asignment.getAssignmentResourceTxnId());
                	
                	//Start - Assignment Resources
                    List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(userId, assign.getAssignmentId());
                    List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
                    ResourceRespTO resourceRespTO = null;
                    for(ResourseVO vo5 : attachedResourcesFromDB)
                    {
                    resourceRespTO = new ResourceRespTO();
                    resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                    resourceRespTO.setResourceName(vo5.getResourceName());
                    resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                    resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                    resourceRespTO.setThumbImg(vo5.getThumbUrl());
                    resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                    resourceRespTO.setAuthorName(vo5.getAuthorName());
                    resourceRespTO.setAuthorImg(vo5.getAuthorImg());
                    
                    attachedResources.add(resourceRespTO);
                    }
                    
                    assign.setAttachedResources(attachedResources);
                	//End - Assignment Resources

                    //Assignment end
                    resp.setAssignmentDetail(assign);   
                    resp.setStatus(SLMSRestConstants.status_success);
                    resp.setStatusMessage(SLMSRestConstants.message_success); 
                 }else{
                     resp.setStatus(SLMSRestConstants.status_success);
                     resp.setStatusMessage(SLMSRestConstants.message_recordnotfound);                 	 
                 }

        }catch(Exception e){
            System.out.println("Exception # getAssignmentDetail "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
	
	
	
	@Override
    public CourseResponse getAssignmentsForTeacher(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
                //Assignment start - //schoolId | classId | hrmId | courseId | moduleId | status  + userId | searchText
                List<AssignmentVO> assignmentListFromDB= service.getTeacherAssignments(req.getSchoolId(), req.getClassId(), req.getHrmId(), req.getCourseId(), req.getModuleId(), req.getStatus(), req.getUserId(), req.getSearchText());
                List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmentListFromDB.size());
                
                AssignmentRespTO assign = null;
                for( AssignmentVO asignment : assignmentListFromDB){
                	assign = new AssignmentRespTO();
                	assign.setAssignmentId(asignment.getAssignmentId());
                	assign.setAssignmentName(asignment.getAssignmentName());
                	assign.setAssignmentDesc(asignment.getAssignmentDesc());
                	assign.setAssignmentSubmittedDate(asignment.getAssignmentSubmittedDate());
                	assign.setAssignmentSubmittedBy(asignment.getAssignmentSubmittedBy());
                	assign.setAssignmentSubmittedById(""+asignment.getAssignmentSubmittedById());
                	assign.setAssignmentStatus(asignment.getAssignmentStatus());
                	assign.setAssignmentDueDate(asignment.getAssignmentDueDate());
                	assign.setCourseId(asignment.getCourseId());
                	assign.setCourseName(asignment.getCourseName());
                	assign.setModuleId(asignment.getModuleId());
                	assign.setModuleName(asignment.getModuleName());
                	assign.setAssignmentResourceTxnId(asignment.getAssignmentResourceTxnId());
                	
                	/**
                	 * Assignment rating data - 
                	 * 
                	 * if assignment status=3 (reviewed) -> Add list of review status parameters
                	 * else add -> review parameters master data
                	 */
                	List<KeyValTypeVO> reviewParameters=null;
                	if(asignment.getAssignmentStatus().trim().equals("3"))
                	{
                		//Review status parameters
                		List<CommonKeyValueVO> ratingsFromDb=service.getRatingData(asignment.getAssignmentResourceTxnId());
                		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                		KeyValTypeVO kvtVo=null;
                		for(CommonKeyValueVO vo1: ratingsFromDb)
                		{
                			kvtVo=new KeyValTypeVO();
                			//Param
                			String[] param=vo1.getItemCode().split("-");
                			kvtVo.setKey(param[0]);
                			kvtVo.setValue(param[1]);
                			
                			//values
                			String[] val=vo1.getItemName().split("-");
                			List<KeyValTypeVO> childs=new ArrayList<KeyValTypeVO>(1);
                			childs.add(new KeyValTypeVO(val[0],val[1], null));
                			kvtVo.setChilds(childs);
                			reviewParameters.add(kvtVo);
                		}
                	}
                	else
                	{
                		//Review Parameters master data
                		int schoolId=asignment.getSchoolId();
                		List<CommonKeyValueVO> ratingsFromDb=service.getRatingMasterData(schoolId);
                		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                		KeyValTypeVO kvtVo=null;
                		for(CommonKeyValueVO vo1: ratingsFromDb)
                		{
                			kvtVo=new KeyValTypeVO();
                			kvtVo.setKey(vo1.getItemCode());
                			kvtVo.setValue(vo1.getItemName());
                			//Review param values
                       		List<CommonKeyValueVO> ratingsValuesFromDb=service.getRatingValuesMasterData(Integer.parseInt(vo1.getItemCode()));
                       		List<KeyValTypeVO> ratingsValues = new ArrayList<KeyValTypeVO>(ratingsValuesFromDb.size());
                    		KeyValTypeVO kvtVo2=null;   
                    		for(CommonKeyValueVO vo2: ratingsValuesFromDb)
                    		{
                    			kvtVo2=new KeyValTypeVO();
                    			kvtVo2.setKey(vo2.getItemCode());
                    			kvtVo2.setValue(vo2.getItemName());                    			
                    			ratingsValues.add(kvtVo2);
                    		}
                    		kvtVo.setChilds(ratingsValues);
                    		
                			reviewParameters.add(kvtVo);
                		}                		
                		
                	}
                	
                	assign.setRatingParameters(reviewParameters);
                	//End assignment rating data
                	
                	//Start - Assignment Resources
                    List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(asignment.getAssignmentSubmittedById(), assign.getAssignmentId());
                    List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
                    ResourceRespTO resourceRespTO = null;
                    for(ResourseVO vo5 : attachedResourcesFromDB)
                    {
                    resourceRespTO = new ResourceRespTO();
                    resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                    resourceRespTO.setResourceName(vo5.getResourceName());
                    resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                    resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                    resourceRespTO.setThumbImg(vo5.getThumbUrl());
                    resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                    resourceRespTO.setAuthorName(vo5.getAuthorName());
                    resourceRespTO.setAuthorImg(vo5.getAuthorImg());
                    
                    attachedResources.add(resourceRespTO);
                    }
                    
                    assign.setAttachedResources(attachedResources);
                	//End - Assignment Resources
                	
                	assignmentList.add(assign);
                }
                
                
            //Assignment end
            resp.setAssignmentList(assignmentList);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getAssignments "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
	

	
    public CourseResponse getAssignments_hirarichal(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
            //Courses list
             List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserId(), req.getSearchText());
            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
             courseResp = new CourseRespTO();
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                
                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
                {
                temp=temp+1;
                }
                
                //Assignment start
                List<AssignmentVO> assignmentListFromDB= service.getStudentAssignments(req.getUserId());
                List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmentListFromDB.size());
                for( AssignmentVO asignment : assignmentListFromDB){
                	AssignmentRespTO assign = new AssignmentRespTO();
                	assign.setAssignmentId(asignment.getAssignmentId());
                	assign.setAssignmentName(asignment.getAssignmentName());
                	assign.setAssignmentSubmittedDate(asignment.getAssignmentSubmittedDate());
                	assign.setAssignmentStatus(asignment.getAssignmentStatus());
                	assign.setAssignmentDueDate(asignment.getAssignmentDueDate());
                	assignmentList.add(assign);
                }
                
                
                //Assignment end
                modResp.setAssignmentList(assignmentList);
                
                moduleList.add(modResp);
                
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(completedCoursePercent*100));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getAssignments "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }

    
    @Override
    public CourseResponse getCourseDetailsByFeedId(int feedId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

             CommonServiceIface commonService = new CommonServiceImpl();
             FeedVO feed = commonService.getFeedDetail(feedId);
             
             int courseId = feed.getCourseId();
             System.out.println("FeedId = "+feedId + "CourseId = "+courseId);
            //Courses list
             CourseVO vo = service.getStudentCourseDetail(courseId);
            
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;

             courseResp = new CourseRespTO();
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCourseIcon(vo.getCourseIcon());
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                
                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
                {
                temp=temp+1;
                }
                
            //Resources list >>>>>>>                
                        List<ResourseVO> resourceListFromDB = service.getStudentResources(Integer.parseInt(vo.getCourseId()), Integer.parseInt(mod.getModuleId()));
                        List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());

                        ResourceRespTO resTo = null;
                        for(ResourseVO vo2:resourceListFromDB)
                        {
                            resTo = new ResourceRespTO();
                            resTo.setAuthorName(vo2.getAuthorName());
                            resTo.setAuthorImg(vo2.getAuthorImg());
                            resTo.setResourceId(""+vo2.getResourceId());
                            resTo.setResourceName(vo2.getResourceName());
                            resTo.setResourceDesc(vo2.getResourceDesc());
                            resTo.setResourceUrl(vo2.getResourceUrl());
                            resTo.setThumbImg(vo2.getThumbUrl());
                            resTo.setStartedOn(vo2.getStartedOn());
                            resTo.setCompletedOn(vo2.getCompletedOn());
                            resTo.setLikeCounts(vo2.getLikeCounts());
                            resTo.setCommentCounts(vo2.getCommentCounts());
                            resTo.setShareCounts(vo2.getShareCounts());

                            resourceList.add(resTo);
                        }

                        modResp.setResourceList(resourceList);

             //End resources list               
                
                moduleList.add(modResp);
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent*100))); 

             courseResp.setModuleList(moduleList);
             
            resp.setCourseDetail(courseResp);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getCourseDetailsByFeedId "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }

    
    
    @Override
    public CourseResponse getCourseDetailsByCourseId(int courseId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             CourseVO vo = service.getStudentCourseDetail(courseId);
            
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;

             courseResp = new CourseRespTO();
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCourseIcon(vo.getCourseIcon());
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                
                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
                {
                temp=temp+1;
                }
                
            //Resources list >>>>>>>                
                        List<ResourseVO> resourceListFromDB = service.getStudentResources(Integer.parseInt(vo.getCourseId()), Integer.parseInt(mod.getModuleId()));
                        List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());

                        ResourceRespTO resTo = null;
                        for(ResourseVO vo2:resourceListFromDB)
                        {
                            resTo = new ResourceRespTO();
                            resTo.setAuthorName(vo2.getAuthorName());
                            resTo.setAuthorImg(vo2.getAuthorImg());
                            resTo.setResourceId(""+vo2.getResourceId());
                            resTo.setResourceName(vo2.getResourceName());
                            resTo.setResourceDesc(vo2.getResourceDesc());
                            resTo.setResourceUrl(vo2.getResourceUrl());
                            resTo.setThumbImg(vo2.getThumbUrl());
                            resTo.setStartedOn(vo2.getStartedOn());
                            resTo.setCompletedOn(vo2.getCompletedOn());
                            resTo.setLikeCounts(vo2.getLikeCounts());
                            resTo.setCommentCounts(vo2.getCommentCounts());
                            resTo.setShareCounts(vo2.getShareCounts());

                            resourceList.add(resTo);
                        }

                        modResp.setResourceList(resourceList);

             //End resources list               
                
                moduleList.add(modResp);
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent*100))); 

             courseResp.setModuleList(moduleList);
             
            resp.setCourseDetail(courseResp);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getCourseDetailsByCourseId "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    
    /**
     * 
     * @param req (UserId,TextSearch)
     * @return (Array of Courses :
     * {CoursesId,CoursesName,PercentageOfCources,
     * ArrayofModules(
     * ModuleId,ModuleName,Status,PercentOfModule
     * )
     * }Status,StatusMessage
     * )
     * @throws RestBusException 
     */
    @Override
    public CourseResponse getUserCourses(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserId(), req.getSearchText());
            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
             courseResp = new CourseRespTO();
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCourseIcon(vo.getCourseIcon());
             
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                
//                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
//                {
//                temp=temp+1;
//                }
                temp=temp+Double.parseDouble(mod.getCompletedPercentStatus());
                
                moduleList.add(modResp);
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent)));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getUserCourses "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }

    @Override
    public CourseResponse getUserCoursesWeb(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserId(), req.getSearchText());
            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
             courseResp = new CourseRespTO();
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCourseIcon(vo.getCourseIcon());
            // courseResp.set
             
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedOn(mod.getCompletedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                
//              if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
//              {
//              temp=temp+1;
//              }
               temp=temp+Double.parseDouble(mod.getCompletedPercentStatus());
                
            //Resources list >>>>>>>                
                       // List<ResourseVO> resourceListFromDB = service.getStudentResourcesWeb(req.getUserId(),vo.getCourseId(), mod.getModuleId(), req.getSearchText());
                		List<ResourseVO> resourceListFromDB = service.getTeacherModuleResources(mod.getModuleSessionId());
                        List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());

                        ResourceRespTO resTo = null;
                        for(ResourseVO vo2:resourceListFromDB)
                        {
                            resTo = new ResourceRespTO();
                            resTo.setAuthorName(vo2.getAuthorName());
                            resTo.setAuthorImg(vo2.getAuthorImg());
                            resTo.setResourceId(""+vo2.getResourceId());
                            resTo.setResourceName(vo2.getResourceName());
                            resTo.setResourceDesc(vo2.getResourceDesc());
                            resTo.setResourceUrl(vo2.getResourceUrl());
                            resTo.setThumbImg(vo2.getThumbUrl());
                            resTo.setStartedOn(vo2.getStartedOn());
                            resTo.setCompletedOn(vo2.getCompletedOn());
                            resTo.setLikeCounts(vo2.getLikeCounts());
                            resTo.setCommentCounts(vo2.getCommentCounts());
                            resTo.setShareCounts(vo2.getShareCounts());

                            resourceList.add(resTo);
                        }

                        modResp.setResourceList(resourceList);

             //End resources list               
                
                moduleList.add(modResp);
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent)));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getUserCourses "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    


	@Override
	public CourseResponse getTeacherCourses(int teacherId)
			throws RestBusException {
		CourseResponse resp = new CourseResponse();
		CourseServiceIface service = new CourseServiceImpl();
		
        try {

        //Courses list
         List<CourseVO> courseListFromDB = service.getTeacherCourses(teacherId);
         ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
         
         CourseRespTO courseResp = null;
         for(CourseVO vo : courseListFromDB)
         	{
             courseResp = new CourseRespTO();
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setCourseIcon(vo.getCourseIcon());
        // setting organisation -start
             List<CourseVO> courseOrgListFromDB = service.getTeacherCourseOrganisations(teacherId,Integer.parseInt(vo.getCourseId()));
             ArrayList<CourseRespTO> orgs = new ArrayList<CourseRespTO>(courseOrgListFromDB.size());
             CourseRespTO orgResp = null;
             for(CourseVO vo2:courseOrgListFromDB)
             {
            	 orgResp = new CourseRespTO();
            	 orgResp.setSchoolId(vo2.getSchoolId());
            	 orgResp.setSchoolName(vo2.getSchoolName());
            	 orgResp.setClassId(vo2.getClassId());
            	 orgResp.setClasseName(vo2.getClasseName());
            	 orgResp.setHrmId(vo2.getHrmId());
            	 orgResp.setHrmName(vo2.getHrmName());
            	 orgResp.setSessionStartDate(vo2.getSessionStartDate());
            	 orgResp.setSessionEndDate(vo2.getSessionEndDate());
           	 
            	 orgResp.setCompletedStatus(vo2.getCompletedStatus());
            	 orgResp.setCourseSessionId(vo2.getCourseSessionId());
            	 orgResp.setStudentCount(vo2.getStudentCount());
            	 //Adding module & status -start
                 
                 List<CourseVO> moduleListFromDB = service.getTeacherCoursesModules(vo2.getCourseSessionId(), vo2.getSchoolId(), vo2.getClassId(), vo2.getHrmId());
                 List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                 ModuleRespTO modResp = null;

                 for(CourseVO mod:moduleListFromDB)
                 {
                 modResp = new ModuleRespTO();
                 modResp.setModuleId(mod.getModuleId());
                 modResp.setModuleName(mod.getModuleName());
                 modResp.setStartedOn(mod.getStartedOn());
                 modResp.setCompletedOn(mod.getCompletedOn());
                 modResp.setCompletedStatus(mod.getCompletedStatus());
                 modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                 modResp.setModuleSessionId(mod.getModuleSessionId());
                 
              //start Calculating module completed %               
                 //Resources list >>>>>>>   
                         List<ResourseVO> resourceListFromDB = service.getTeacherModuleResources(mod.getModuleSessionId());
                         //List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
                         double temp2=0;
                         for(ResourseVO rvo:resourceListFromDB)
                         {
                             
                             if(rvo.getCompletedStatus().equalsIgnoreCase("y") || rvo.getCompletedStatus().equals("1"))
                             {
                             temp2=temp2+1;
                             }                            

                         }
                 //End resources list               
                 
                       double completedModulePercent=0;
                       if(resourceListFromDB.size()>0){
                     	  completedModulePercent = temp2/(double)resourceListFromDB.size();
                       	}
                       modResp.setCompletedPercentStatus(String.valueOf(Math.round(completedModulePercent*100)));   

                 //end Calculating module completed %
                 moduleList.add(modResp);
                 }

              	 orgResp.setModuleList(moduleList);
            	 //Adding module & status -end
              	 
            	 orgs.add(orgResp);
             }
         
             courseResp.setOrgList(orgs);
          // setting organisation -end
             courses.add(courseResp);
         }

         resp.setCourseList(courses);

        resp.setStatus(SLMSRestConstants.status_success);
        resp.setStatusMessage(SLMSRestConstants.message_success); 
    }catch(Exception e){
        System.out.println("Exception # getTeacherCourses "+e.getMessage());
        resp.setStatus(SLMSRestConstants.status_failure);
        resp.setStatusMessage(SLMSRestConstants.message_failure);
        resp.setErrorMessage(e.getMessage());            
    }
        
    return resp;
}


	
	 @Override
	    public CourseResponse getStudentCourses(CourseRequest req) throws RestBusException {
	            CourseResponse resp = new CourseResponse();
	            CourseServiceIface service = new CourseServiceImpl();
	    
	            try {

	            //Courses list
	             List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserId(), req.getSearchText());
	            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
	             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
	             
	             CourseRespTO courseResp = null;
	             ModuleRespTO modResp = null;
	             for(CourseVO vo : courseListFromDB)
	             	{
		             courseResp = new CourseRespTO();
		             courseResp.setCompletedStatus(vo.getCompletedStatus());
		             courseResp.setCourseId(vo.getCourseId());
		             courseResp.setCourseName(vo.getCourseName());
		             courseResp.setAuthorImg(vo.getAuthorImg());
		             courseResp.setAuthorName(vo.getAuthorName());
		             courseResp.setStartedOn(vo.getStartedOn());
		             courseResp.setCompletedOn(vo.getCompletedOn());
		             courseResp.setHrmName(vo.getHrmName());
		             courseResp.setCourseDesc(vo.getCourseDesc());
		             courseResp.setCourseIcon(vo.getCourseIcon());
		             courseResp.setIsSelfDriven(vo.getIsSelfDriven());
		               
	               //Phase-3 Course grading
  	                int sumOfModuleAssignmentGrades = 0;
  	                int countOfModuleAssignments=0;
		            boolean enableGrade=true; 
	             //Modules list
	             
	                double temp=0;
	                List<CourseVO> moduleListFromDB = service.getStudentCoursesModules(vo.getCourseSessionId());
	                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
	                for(CourseVO mod:moduleListFromDB)
	                {
	                modResp = new ModuleRespTO();
	                modResp.setModuleId(mod.getModuleId());
	                modResp.setModuleName(mod.getModuleName());
	                modResp.setStartedOn(mod.getStartedOn());
	                modResp.setCompletedOn(mod.getCompletedOn());
	                modResp.setCompletedStatus(mod.getCompletedStatus());
	                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
	                
//    		              if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
//    		              {
//    		              temp=temp+1;
//    		              }
	               temp=temp+Double.parseDouble(mod.getCompletedPercentStatus());
	                
	            //Resources list >>>>>>>                
	                       // List<ResourseVO> resourceListFromDB = service.getStudentResourcesWeb(req.getUserId(),vo.getCourseId(), mod.getModuleId(), req.getSearchText());
	                		/*List<ResourseVO> resourceListFromDB = service.getTeacherModuleResources(mod.getModuleSessionId());
	                        List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());

	                        ResourceRespTO resTo = null;
	                        for(ResourseVO vo2:resourceListFromDB)
	                        {
	                            resTo = new ResourceRespTO();
	                            resTo.setAuthorName(vo2.getAuthorName());
	                            resTo.setAuthorImg(vo2.getAuthorImg());
	                            resTo.setResourceId(""+vo2.getResourceId());
	                            resTo.setResourceName(vo2.getResourceName());
	                            resTo.setResourceDesc(vo2.getResourceDesc());
	                            resTo.setResourceUrl(vo2.getResourceUrl());
	                            resTo.setThumbImg(vo2.getThumbUrl());
	                            resTo.setStartedOn(vo2.getStartedOn());
	                            resTo.setCompletedOn(vo2.getCompletedOn());
	                            resTo.setLikeCounts(vo2.getLikeCounts());
	                            resTo.setCommentCounts(vo2.getCommentCounts());
	                            resTo.setShareCounts(vo2.getShareCounts());

	                            resourceList.add(resTo);
	                        }

	                        modResp.setResourceList(resourceList);*/

	             //End resources list    

	                    
	                  /* Start assignment List*/
                    
	                int pendingAssignmentCount=0;
	                int submittedAssignmentCount=0;
	                int reviewedAssignmentCount=0;
	               
                    List<AssignmentVO> assignmentFromDB = service.getStudentAssignmentsByModule(Integer.parseInt(mod.getModuleId()),req.getUserId());
                    List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmentFromDB.size());
                    AssignmentRespTO assRes= null;
                    for(AssignmentVO assignment :assignmentFromDB){
                    	assRes = new AssignmentRespTO();
                    	assRes.setAssignmentId(assignment.getAssignmentId());
                    	assRes.setAssignmentName(assignment.getAssignmentName());
                    	assRes.setAssignmentStatus(assignment.getAssignmentStatus());
                    	assRes.setAssignmentDueDate(assignment.getAssignmentDueDate());
                    	assRes.setAssignmentResourceTxnId(assignment.getAssignmentResourceTxnId());
                    	assRes.setAssignmentSubmittedDate(assignment.getAssignmentSubmittedDate());
                    	assRes.setAssignmentTypeId(assignment.getAssignmentTypeId());
                    	assRes.setAssignmentType(assignment.getAssignmentType());
                    	
                    	//start-Phase-3 updating module assignment counts
                    	if(assignment.getAssignmentStatus().equals(SLMSRestConstants.ASSIGNMENT_STATUS_PENDING))
                    		pendingAssignmentCount=pendingAssignmentCount+1;
                    	if(assignment.getAssignmentStatus().equals(SLMSRestConstants.ASSIGNMENT_STATUS_SUBMITTED))
                    		submittedAssignmentCount=submittedAssignmentCount+1;
                    	if(assignment.getAssignmentStatus().equals(SLMSRestConstants.ASSIGNMENT_STATUS_REVIEWED))
                    		reviewedAssignmentCount=reviewedAssignmentCount+1;
                    	//end-Phase-3 updating module assignment counts

                    	//Phase-3 grading 
                         sumOfModuleAssignmentGrades = sumOfModuleAssignmentGrades+assignment.getReviewGrade();
      	                 countOfModuleAssignments=countOfModuleAssignments+1;
      	                 if(assignment.getReviewGrade()<=0)
      	                 {
      	                	enableGrade=false;
      	                 }
                    	
                    	assignmentList.add(assRes);
                    }
                    modResp.setPendingAssignmentCount(pendingAssignmentCount);
                    modResp.setSubmittedAssignmentCount(submittedAssignmentCount);
                    modResp.setReviewedAssignmentCount(reviewedAssignmentCount);
                    
                    modResp.setAssignmentList(assignmentList);
                    /* end assignment List*/
            
	                moduleList.add(modResp);
	                }
	                
	             //Course percentage calculation   
	             double completedCoursePercent=0;
	             if(moduleListFromDB.size()>0){
	             completedCoursePercent = temp/(double)moduleList.size();
	             }
	             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent)));   
	             //Course grade calculation
	             if(enableGrade==true && countOfModuleAssignments>0)
	             {
	             int avgGrade = sumOfModuleAssignmentGrades/countOfModuleAssignments;
	             courseResp.setCourseCompletionGrade(service.getCourseReviewGrade(avgGrade));
	             }
	             
	             courseResp.setModuleList(moduleList);
	             courses.add(courseResp);
	             }

	             resp.setCourseList(courses);

	            resp.setStatus(SLMSRestConstants.status_success);
	            resp.setStatusMessage(SLMSRestConstants.message_success); 
	        }catch(Exception e){
	            System.out.println("Exception # getUserCourses "+e.getMessage());
	            resp.setStatus(SLMSRestConstants.status_failure);
	            resp.setStatusMessage(SLMSRestConstants.message_failure);
	            resp.setErrorMessage(e.getMessage());            
	        }
	            
	        return resp;
	    }
	 

    @Override
    public CourseResponse getUserCoursesTeacher(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             List<CourseVO> courseListFromDB = service.getTeacherCourses(req.getUserId(), req.getSchoolId(), req.getClassId(), req.getHrmId(), req.getCourseId());
            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
             courseResp = new CourseRespTO();
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setCourseIcon(vo.getCourseIcon());
             
             courseResp.setSchoolId(vo.getSchoolId());
             courseResp.setSchoolName(vo.getSchoolName());
             courseResp.setClassId(vo.getClassId());
             courseResp.setClasseName(vo.getClasseName());
             courseResp.setHrmId(vo.getHrmId());
             courseResp.setHrmName(vo.getHrmName());
             courseResp.setCourseSessionId(vo.getCourseSessionId());
             courseResp.setStudentCount(vo.getStudentCount());
            // courseResp.set
             
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = service.getTeacherCoursesModules(vo.getCourseSessionId(), vo.getSchoolId(), vo.getClassId(), vo.getHrmId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedOn(mod.getCompletedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                modResp.setModuleSessionId(mod.getModuleSessionId());
                
//                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
//                {
//                temp=temp+1;
//                }
                
            //Resources list >>>>>>>                
                        List<ResourseVO> resourceListFromDB = service.getTeacherModuleResources(mod.getModuleSessionId());
                        List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
                        double temp2=0;
                        ResourceRespTO resTo = null;
                        for(ResourseVO vo2:resourceListFromDB)
                        {
                            resTo = new ResourceRespTO();
                            resTo.setAuthorName(vo2.getAuthorName());
                            resTo.setAuthorImg(vo2.getAuthorImg());
                            resTo.setResourceId(""+vo2.getResourceId());
                            resTo.setResourceName(vo2.getResourceName());
                            resTo.setResourceDesc(vo2.getResourceDesc());
                            resTo.setResourceUrl(vo2.getResourceUrl());
                            resTo.setThumbImg(vo2.getThumbUrl());
                            resTo.setStartedOn(vo2.getStartedOn());
                            resTo.setCompletedOn(vo2.getCompletedOn());
                            resTo.setLikeCounts(vo2.getLikeCounts());
                            resTo.setCommentCounts(vo2.getCommentCounts());
                            resTo.setShareCounts(vo2.getShareCounts());
                            resTo.setCompletedStatus(vo2.getCompletedStatus());
                            resTo.setResourceSessionId(vo2.getResourceSessionId());
                            
                            if(resTo.getCompletedStatus().equalsIgnoreCase("y") || resTo.getCompletedStatus().equals("1"))
                            {
                            temp2=temp2+1;
                            }                            
                            resourceList.add(resTo);
                        }

                        modResp.setResourceList(resourceList);

             //End resources list               
                
                      double completedModulePercent=0;
                      if(resourceListFromDB.size()>0){
                    	  completedModulePercent = temp2/(double)resourceList.size();
                      	}
                      temp=temp+completedModulePercent;
                      modResp.setCompletedPercentStatus(String.valueOf(Math.round(completedModulePercent*100)));                         
                        
                moduleList.add(modResp);
                }
             
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent*100)));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getUserCoursesTeacher "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    

	@Override
	public CourseResponse getUserCourseDetailByTeacher(CourseRequest course)
			throws RestBusException {
		CourseResponse resp = new CourseResponse();
		
        CourseServiceIface service = new CourseServiceImpl();
        CommonServiceIface services = new CommonServiceImpl();
        
        try {
        	int teacherId=course.getUserId();
        	  List<SchoolMasterVo> schoolListFromDB = services.getSchoolMasterVoList(course.getSchoolId(),teacherId);
              List<SchoolRespTO> schoolList = new ArrayList<SchoolRespTO>(schoolListFromDB.size());
              
            //  SchoolRespTO schoolRespTO = null;
              for(SchoolMasterVo vo : schoolListFromDB)
              {
            	  SchoolRespTO schoolTO = new SchoolRespTO();
            	  schoolTO.setSchoolId(String.valueOf(vo.getSchoolId()));
            	  schoolTO.setSchoolName(String.valueOf(vo.getSchoolName()));
            	  
      	        //Homeroom data
      	        List<HomeRoomMasterVo> hrmListDB = services.getHomeRoomMasterVoList(course.getClassId(),vo.getSchoolId(),teacherId);
      	        List<HomeRoomRespTO> homeRoomList = new ArrayList<HomeRoomRespTO>(hrmListDB.size());
      	       
      	        for(HomeRoomMasterVo hrmvo  : hrmListDB)
      	        {
      	        	 HomeRoomRespTO homeRoomRespTO = new HomeRoomRespTO();
      	        	 
      	        	 
      	        homeRoomRespTO.setHomeRoomId(String.valueOf(hrmvo.getHomeRoomMstrId()));
      	        homeRoomRespTO.setHomeRoomName(String.valueOf(hrmvo.getHomeRoomMstrName()));
      	        
      	     
            	  
      	         List<CourseVO> courseListFromDB = service.getTeacherCourses(course.getUserId(),vo.getSchoolId(), course.getClassId(), hrmvo.getHomeRoomMstrId(), course.getCourseId());
      	        // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
      	         ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
      	     
      	         CourseRespTO courseResp = null;
      	         ModuleRespTO modResp = null;
      	         for(CourseVO corsvo : courseListFromDB)
      	         {
      	         courseResp = new CourseRespTO();
      	         courseResp.setCompletedStatus(corsvo.getCompletedStatus());
      	         courseResp.setCourseId(corsvo.getCourseId());
      	         courseResp.setCourseName(corsvo.getCourseName());
      	         courseResp.setAuthorImg(corsvo.getAuthorImg());
      	         courseResp.setAuthorName(corsvo.getAuthorName());
      	         courseResp.setStartedOn(corsvo.getStartedOn());
      	         courseResp.setCompletedOn(corsvo.getCompletedOn());
      	         courseResp.setCourseIcon(corsvo.getCourseIcon());
      	         
      	         courseResp.setCourseSessionId(corsvo.getCourseSessionId());
      	         homeRoomRespTO.setClassId(String.valueOf(corsvo.getClassId()));
      	         homeRoomRespTO.setClassName(String.valueOf(corsvo.getClasseName()));
      	         
      	         courseResp.setStudentCount(corsvo.getStudentCount());
      	        // courseResp.set
      	         
      	         //Modules list
      	         
      	            double temp=0;
      	            List<CourseVO> moduleListFromDB = service.getTeacherCoursesModules(corsvo.getCourseSessionId(),vo.getSchoolId(), corsvo.getClassId(), hrmvo.getHomeRoomMstrId());
      	            List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
      	            for(CourseVO mod:moduleListFromDB)
      	            {
      	            modResp = new ModuleRespTO();
      	            modResp.setModuleId(mod.getModuleId());
      	            modResp.setModuleName(mod.getModuleName());
      	            modResp.setStartedOn(mod.getStartedOn());
      	            modResp.setCompletedOn(mod.getCompletedOn());
      	            modResp.setCompletedStatus(mod.getCompletedStatus());
      	            modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
      	            modResp.setModuleSessionId(mod.getModuleSessionId());
      	            modResp.setAssignmentEnableStatus(mod.getAssignmentEnableStatus());
      	            modResp.setModuleDesc(mod.getModuleDesc());
      	            
      	            if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
      	            {
      	            temp=temp+1;
      	            }
      	            
      	        //Resources list >>>>>>>                
      	                    List<ResourseVO> resourceListFromDB = service.getTeacherModuleResources(mod.getModuleSessionId());
      	                    List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
      	                    double temp2=0;
      	                    ResourceRespTO resTo = null;
      	                    for(ResourseVO vo2:resourceListFromDB)
      	                    {
      	                        resTo = new ResourceRespTO();
      	                        resTo.setAuthorName(vo2.getAuthorName());
      	                        resTo.setAuthorImg(vo2.getAuthorImg());
      	                        resTo.setResourceId(""+vo2.getResourceId());
      	                        resTo.setResourceName(vo2.getResourceName());
      	                        resTo.setResourceDesc(vo2.getResourceDesc());
      	                        resTo.setResourceUrl(vo2.getResourceUrl());
      	                        resTo.setThumbImg(vo2.getThumbUrl());
      	                        resTo.setStartedOn(vo2.getStartedOn());
      	                        resTo.setCompletedOn(vo2.getCompletedOn());
      	                        resTo.setLikeCounts(vo2.getLikeCounts());
      	                        resTo.setCommentCounts(vo2.getCommentCounts());
      	                        resTo.setShareCounts(vo2.getShareCounts());
      	                        resTo.setCompletedStatus(vo2.getCompletedStatus());
      	                        resTo.setResourceSessionId(vo2.getResourceSessionId());
      	                        
      	                        if(resTo.getCompletedStatus().equalsIgnoreCase("y") || resTo.getCompletedStatus().equals("1"))
      	                        {
      	                        temp2=temp2+1;
      	                        }                            
      	                        
      	                        
      	                        resourceList.add(resTo);
      	                    }

      	                    modResp.setResourceList(resourceList);

      	         //End resources list               
      	            
      	                  double completedModulePercent=0;
      	                  if(resourceListFromDB.size()>0){
      	                	  completedModulePercent = temp2/(double)resourceList.size();
      	                  	}
      	                  modResp.setCompletedPercentStatus(String.valueOf(Math.round(completedModulePercent*100)));                         
      	                    
      	            moduleList.add(modResp);
      	            }
      	         
      	         double completedCoursePercent=0;
      	         if(moduleListFromDB.size()>0){
      	         completedCoursePercent = temp/(double)moduleList.size();
      	         }
      	         
      	         courseResp.setCompletedPercentStatus(String.valueOf(Math.round(completedCoursePercent*100)));   

      	         courseResp.setModuleList(moduleList);
      	         courses.add(courseResp);
      	         }
      	       homeRoomRespTO.setCourseList(courses);
      	         
      	         
      	     homeRoomList.add(homeRoomRespTO);
      	 //  schoolTO.setCourseList(courses);
      	   	schoolTO.setHomeRoomList(homeRoomList);
      	       } 
      	      schoolList.add(schoolTO);
              }
              resp.setSchoolList(schoolList);

         //resp.setCourseList(courses);

        resp.setStatus(SLMSRestConstants.status_success);
        resp.setStatusMessage(SLMSRestConstants.message_success); 
    }catch(Exception e){
        System.out.println("Exception # getUserCoursesTeacher "+e.getMessage());
        resp.setStatus(SLMSRestConstants.status_failure);
        resp.setStatusMessage(SLMSRestConstants.message_failure);
        resp.setErrorMessage(e.getMessage());            
    }
        
    return resp;
}


    @Override
    public CourseResponse getTeacherAssignments(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             List<CourseVO> courseListFromDB = service.getTeacherCourses(req.getUserId(), req.getSchoolId(), req.getClassId(), req.getHrmId(), req.getCourseId(),req.getModuleId());
            // List<CourseVO> courseListFromDB = service.getStudentCourses(req.getUserName(), req.getSearchText());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
             courseResp = new CourseRespTO();
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setSchoolId(vo.getSchoolId());
             courseResp.setSchoolName(vo.getSchoolName());
             courseResp.setClassId(vo.getClassId());
             courseResp.setClasseName(vo.getClasseName());
             courseResp.setHrmId(vo.getHrmId());
             courseResp.setHrmName(vo.getHrmName());
             courseResp.setCourseSessionId(vo.getCourseSessionId());
            // courseResp.set
             
             //Modules list
             
                double temp=0;
                List<CourseVO> moduleListFromDB = null;
                
                if(req.getModuleId()!=0)
                moduleListFromDB = service.getTeacherCoursesModules(vo.getCourseSessionId(),req.getModuleId());
                else
                moduleListFromDB = service.getTeacherCoursesModules(vo.getCourseSessionId(),vo.getSchoolId(),vo.getClassId(),vo.getHrmId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedOn(mod.getCompletedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                modResp.setModuleSessionId(mod.getModuleSessionId());
                
                String moduleAssignmentEnableStatus = "0";
                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
                {	
                temp=temp+1;
                moduleAssignmentEnableStatus = "1";
                }
                
                //Assignments list start>>>>>>>                
                //List<AssignmentVO> assignmenteListFromDB = service.getAssignmentsByModuleId(Integer.parseInt(mod.getModuleId()));
                List<AssignmentVO> assignmenteListFromDB = service.getAssignments(vo.getSchoolId(),vo.getClassId(),vo.getHrmId(),Integer.parseInt(vo.getCourseId()),Integer.parseInt(mod.getModuleId()),req.getUserId());
                List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmenteListFromDB.size());
                AssignmentRespTO assResp=null;
                for(AssignmentVO ass : assignmenteListFromDB){
                	assResp = new AssignmentRespTO();
                	assResp.setAssignmentId(ass.getAssignmentId());
                	assResp.setAssignmentName(ass.getAssignmentName());
                	assResp.setAssignmentDesc(ass.getAssignmentDesc());
                	//assResp.setEnableStatus(moduleAssignmentEnableStatus); //May need to update later as it does not represent that teacher has really enable the assignment
                	assResp.setEnableStatus(ass.getEnableStatus());
                   // List<AssignmentVO> studentListFromDB = service.getStudentsByAssignmentId(ass.getAssignmentId());
                    List<AssignmentVO> studentListFromDB = service.getStudentsByAssignmentId(vo.getSchoolId(),vo.getClassId(),vo.getHrmId(),Integer.parseInt(vo.getCourseId()),Integer.parseInt(mod.getModuleId()),req.getUserId(),ass.getAssignmentId());
                    List<AssignmentRespTO> studentList = new ArrayList<AssignmentRespTO>(studentListFromDB.size());
                    AssignmentRespTO student=null;
                    for(AssignmentVO avo : studentListFromDB)
                    {
                    	student=new AssignmentRespTO();
                    	student.setAssignmentStatus(avo.getAssignmentStatus());
                    	student.setAssignmentSubmittedDate(avo.getAssignmentSubmittedDate());
                    	student.setAssignmentDueDate(avo.getAssignmentDueDate());
                    	String[] userIdNm=avo.getAssignmentSubmittedBy().split("-");
                    	student.setAssignmentSubmittedBy(userIdNm[1]);
                    	student.setAssignmentSubmittedById(userIdNm[0]);
                    	student.setAssignmentResourceTxnId(avo.getAssignmentResourceTxnId());
                    	/**
                    	 * Assignment rating data - 
                    	 * 
                    	 * if assignment status=3 (reviewed) -> Add list of review status parameters
                    	 * else add -> review parameters master data
                    	 */
                    	List<KeyValTypeVO> reviewParameters=null;
                    	if(student.getAssignmentStatus().trim().equals("3"))
                    	{
                    		//Review status parameters
                    		List<CommonKeyValueVO> ratingsFromDb=service.getRatingData(student.getAssignmentResourceTxnId());
                    		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                    		KeyValTypeVO kvtVo=null;
                    		for(CommonKeyValueVO vo1: ratingsFromDb)
                    		{
                    			kvtVo=new KeyValTypeVO();
                    			//Param
                    			String[] param=vo1.getItemCode().split("-");
                    			kvtVo.setKey(param[0]);
                    			kvtVo.setValue(param[1]);
                    			
                    			//values
                    			String[] val=vo1.getItemName().split("-");
                    			List<KeyValTypeVO> childs=new ArrayList<KeyValTypeVO>(1);
                    			childs.add(new KeyValTypeVO(val[0],val[1], null));
                    			kvtVo.setChilds(childs);
                    			reviewParameters.add(kvtVo);
                    		}
                    	}
                    	else
                    	{
                    		//Review Parameters master data
                    		int schoolId=courseResp.getSchoolId();
                    		List<CommonKeyValueVO> ratingsFromDb=service.getRatingMasterData(schoolId);
                    		reviewParameters = new ArrayList<KeyValTypeVO>(ratingsFromDb.size());
                    		KeyValTypeVO kvtVo=null;
                    		for(CommonKeyValueVO vo1: ratingsFromDb)
                    		{
                    			kvtVo=new KeyValTypeVO();
                    			kvtVo.setKey(vo1.getItemCode());
                    			kvtVo.setValue(vo1.getItemName());
                    			//Review param values
                           		List<CommonKeyValueVO> ratingsValuesFromDb=service.getRatingValuesMasterData(Integer.parseInt(vo1.getItemCode()));
                           		List<KeyValTypeVO> ratingsValues = new ArrayList<KeyValTypeVO>(ratingsValuesFromDb.size());
                        		KeyValTypeVO kvtVo2=null;   
                        		for(CommonKeyValueVO vo2: ratingsValuesFromDb)
                        		{
                        			kvtVo2=new KeyValTypeVO();
                        			kvtVo2.setKey(vo2.getItemCode());
                        			kvtVo2.setValue(vo2.getItemName());                    			
                        			ratingsValues.add(kvtVo2);
                        		}
                        		kvtVo.setChilds(ratingsValues);
                        		
                    			reviewParameters.add(kvtVo);
                    		}                		
                    		
                    	}
                    	
                    	student.setRatingParameters(reviewParameters);
                    	
                    	//Start - Assignment Resources
                        List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(Integer.parseInt(student.getAssignmentSubmittedById()),assResp.getAssignmentId());
                        List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
                        ResourceRespTO resourceRespTO = null;
                        for(ResourseVO vo5 : attachedResourcesFromDB)
                        {
                        resourceRespTO = new ResourceRespTO();
                        resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                        resourceRespTO.setResourceName(vo5.getResourceName());
                        resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                        resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                        resourceRespTO.setThumbImg(vo5.getThumbUrl());
                        resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                        resourceRespTO.setAuthorName(vo5.getAuthorName());
                        resourceRespTO.setAuthorImg(vo5.getAuthorImg());
                        resourceRespTO.setUploadedBy(vo5.getUploadedBy());
                        
                        attachedResources.add(resourceRespTO);
                        }
                        
                        student.setAttachedResources(attachedResources);
                    	//End - Assignment Resources
                        
                        studentList.add(student);
                    }
                    assResp.setStudentList(studentList);
                    assignmentList.add(assResp);
                    
                }
                        
               //Assignment list end<<<<<<
                    
                    modResp.setAssignmentList(assignmentList);        
                moduleList.add(modResp);
                }
                DecimalFormat df = new DecimalFormat("#.##");
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(df.format(completedCoursePercent*100));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getUserCoursesTeacher "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    /**
     * 
     ************Course Assignment Summary (Teacher)******************
    Response Structure –
	[Course | Organisation | Department | Course-Session start date]
	|
	|>List [Lesson]
		|
		|>[Assignment  | Type | Due Date]
			|
			|>List [Student Name | Submit Status | Submit Date] 
	 *******************************************************************	
     * 
     */
    @Override
    public CourseResponse getTeacherAssignments2(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {

            //Courses list
             List<CourseVO> courseListFromDB = service.getTeacherCourses(req.getUserId(), req.getSchoolId(), req.getClassId(), req.getHrmId(), req.getCourseId(),req.getModuleId());
             ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>(courseListFromDB.size());
             
             CourseRespTO courseResp = null;
             ModuleRespTO modResp = null;
             for(CourseVO vo : courseListFromDB)
             {
            	 
             courseResp = new CourseRespTO();
             courseResp.setCompletedStatus(vo.getCompletedStatus());
             courseResp.setCourseId(vo.getCourseId());
             courseResp.setCourseName(vo.getCourseName());
             courseResp.setAuthorImg(vo.getAuthorImg());
             courseResp.setAuthorName(vo.getAuthorName());
             courseResp.setStartedOn(vo.getStartedOn());
             courseResp.setCompletedOn(vo.getCompletedOn());
             courseResp.setSchoolId(vo.getSchoolId());
             courseResp.setSchoolName(vo.getSchoolName());
             courseResp.setClassId(vo.getClassId());
             courseResp.setClasseName(vo.getClasseName());
             courseResp.setHrmId(vo.getHrmId());
             courseResp.setHrmName(vo.getHrmName());
             courseResp.setSessionStartDate(vo.getSessionStartDate());
             courseResp.setSessionEndDate(vo.getSessionEndDate());
             courseResp.setCourseSessionId(vo.getCourseSessionId());
             
             //Modules list
                double temp=0;
                List<CourseVO> moduleListFromDB = null;
                
                if(req.getModuleId()!=0)
                moduleListFromDB = service.getTeacherCoursesModules(vo.getCourseSessionId(),req.getModuleId());
                else
                moduleListFromDB = service.getTeacherCoursesModules(vo.getCourseSessionId(),req.getSchoolId(),req.getClassId(),vo.getHrmId());
                List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>(moduleListFromDB.size());
                for(CourseVO mod:moduleListFromDB)
                {
                modResp = new ModuleRespTO();
                modResp.setModuleId(mod.getModuleId());
                modResp.setModuleName(mod.getModuleName());
                modResp.setStartedOn(mod.getStartedOn());
                modResp.setCompletedOn(mod.getCompletedOn());
                modResp.setCompletedStatus(mod.getCompletedStatus());
                modResp.setCompletedPercentStatus(mod.getCompletedPercentStatus());
                modResp.setModuleSessionId(mod.getModuleSessionId());
                
                if(mod.getCompletedStatus().equalsIgnoreCase("y") || mod.getCompletedStatus().equals("1"))
                {	
                temp=temp+1;
                }
                
                //Assignments list start>>>>>>>                
                List<AssignmentVO> assignmenteListFromDB = service.getAssignments(req.getSchoolId(),req.getClassId(),vo.getHrmId(),Integer.parseInt(vo.getCourseId()),Integer.parseInt(mod.getModuleId()),req.getUserId());
                List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assignmenteListFromDB.size());
                AssignmentRespTO assResp=null;
                for(AssignmentVO ass : assignmenteListFromDB){
                	assResp = new AssignmentRespTO();
                	assResp.setAssignmentId(ass.getAssignmentId());
                	assResp.setAssignmentName(ass.getAssignmentName());
                	assResp.setAssignmentDesc(ass.getAssignmentDesc());
                	assResp.setEnableStatus(ass.getEnableStatus());
                	assResp.setAssignmentType(ass.getAssignmentType());
                	assResp.setAssignmentDueDate(ass.getAssignmentDueDate());
                	assResp.setCancelStatus(ass.getCancelStatus());
                	
                	List<AssignmentVO> studentListFromDB = service.getStudentsByAssignmentId(req.getSchoolId(),req.getClassId(),vo.getHrmId(),Integer.parseInt(vo.getCourseId()),Integer.parseInt(mod.getModuleId()),req.getUserId(),ass.getAssignmentId());
                    List<AssignmentRespTO> studentList = new ArrayList<AssignmentRespTO>(studentListFromDB.size());
                    AssignmentRespTO student=null;
                    for(AssignmentVO avo : studentListFromDB)
                    {
                    	student=new AssignmentRespTO();
                    	student.setAssignmentStatus(avo.getAssignmentStatus());
                    	student.setAssignmentSubmittedDate(avo.getAssignmentSubmittedDate());
                    	student.setAssignmentDueDate(avo.getAssignmentDueDate());
                    	String[] userIdNm=avo.getAssignmentSubmittedBy().split("-");
                    	student.setAssignmentSubmittedBy(userIdNm[1]);
                    	student.setAssignmentSubmittedById(userIdNm[0]);
                    	student.setAssignmentResourceTxnId(avo.getAssignmentResourceTxnId());
                        
                        studentList.add(student);
                    }
                    assResp.setStudentList(studentList);
                    assignmentList.add(assResp);
                    
                }
                        
               //Assignment list end<<<<<<
                    
                modResp.setAssignmentList(assignmentList);        
                moduleList.add(modResp);
                }
                DecimalFormat df = new DecimalFormat("#.##");
             double completedCoursePercent=0;
             if(moduleListFromDB.size()>0){
             completedCoursePercent = temp/(double)moduleList.size();
             }
             
             courseResp.setCompletedPercentStatus(df.format(completedCoursePercent*100));   

             courseResp.setModuleList(moduleList);
             courses.add(courseResp);
             }

             resp.setCourseList(courses);

            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getTeacherAssignments2 "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    
    
    public CourseResponse getUserCourses_x(CourseRequest req) throws RestBusException {
        CourseResponse resp = new CourseResponse();
        
        ArrayList<CourseRespTO> courses = new ArrayList<CourseRespTO>();
        
        CourseRespTO course = new CourseRespTO();
        course.setCourseId("01");
        course.setCourseName("Physics");
        course.setCompletedPercentStatus("100");
        course.setStartedOn("2015-07-01");
        List<ModuleRespTO> moduleList = new ArrayList<ModuleRespTO>();
              ModuleRespTO mod1=new ModuleRespTO("02", "Velocity");
              mod1.setCompletedPercentStatus("100");
              mod1.setStartedOn("2015-07-05");
              moduleList.add(mod1);
              ModuleRespTO mod2=new ModuleRespTO("03", "Mass");
              mod2.setCompletedPercentStatus("100");
              mod2.setStartedOn("2015-07-05");
              moduleList.add(mod2);
              ModuleRespTO mod3=new ModuleRespTO("04", "Acceleration");
              mod3.setCompletedPercentStatus("100");
              mod3.setStartedOn("2015-07-05");
              moduleList.add(mod3);              
        course.setModuleList(moduleList);
        courses.add(course);
        
        CourseRespTO course2 = new CourseRespTO();
        course2.setCourseId("06");
        course2.setCourseName("Mathematics");
        course2.setCompletedPercentStatus("25");
        course2.setStartedOn("2015-07-05");
        List<ModuleRespTO> moduleList2 = new ArrayList<ModuleRespTO>();
              mod1=new ModuleRespTO("01", "Algebra");
              mod1.setCompletedPercentStatus("50");
              mod1.setStartedOn("2015-07-05");
              moduleList2.add(mod1);

              mod2=new ModuleRespTO("02", "Trigonometry");
              mod2.setCompletedPercentStatus("75");
              mod2.setStartedOn("2015-07-05");
              moduleList2.add(mod2);

              mod3=new ModuleRespTO("03", "Statistics");
              mod3.setCompletedPercentStatus("40");
              mod3.setStartedOn("2015-07-05");
              moduleList2.add(mod3);              
              
              ModuleRespTO mod4=new ModuleRespTO("04", "Geometry");
              mod4.setCompletedPercentStatus("100");
              mod4.setStartedOn("2015-07-05");
              moduleList2.add(mod4);  
              
        course2.setModuleList(moduleList2);
        courses.add(course2);
        
        
        resp.setCourseList(courses);
        resp.setStatus(1001);
        resp.setStatusMessage("success");
        
        return resp;
    }
    
    

    @Override
    public CourseResponse getModuleDetailsByFeedIdForRating(int feedId,int userId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl(); 
            ModuleRespTO moduleTo = null;
        
        try{
             CommonServiceIface commonService = new CommonServiceImpl();
             FeedVO feed = commonService.getFeedDetail(feedId);
             int moduleId = feed.getModuleId();
             System.out.println("Feed Id = "+feedId+" Module Id = "+moduleId);
             //Module details
             CourseVO courseVO = service.getStudentModuleDetail(moduleId);

             if(courseVO != null)
             {
             moduleTo = new ModuleRespTO();
             moduleTo.setModuleId(courseVO.getModuleId());
             moduleTo.setModuleName(courseVO.getModuleName());
             moduleTo.setCourseId(courseVO.getCourseId());
                 
            //>>>>>>>>>>resources start
            List<ResourseVO> resourceListFromDB = service.getStudentResourcesForRating(moduleId,userId);
            List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
            
            ResourceRespTO resTo = null;
            for(ResourseVO vo:resourceListFromDB)
            {
                resTo = new ResourceRespTO();
                resTo.setResourceId(""+vo.getResourceId());
                resTo.setResourceName(vo.getResourceName());
                resTo.setResourceDesc(vo.getResourceDesc());
                resTo.setResourceUrl(vo.getResourceUrl());
                resTo.setThumbImg(vo.getThumbUrl());
                resTo.setAuthorName(vo.getAuthorName());
                resTo.setAuthorImg(vo.getAuthorImg());
                
                resTo.setStartedOn(vo.getStartedOn());
                resTo.setCompletedOn(vo.getCompletedOn());
                resTo.setCommentCounts(vo.getCommentCounts());
                
                //Rating
                BigDecimal tempRating=vo.getRating()!=null?vo.getRating():BigDecimal.ZERO;
                BigDecimal tempAvgRating=vo.getAvgRating()!=null?vo.getAvgRating():BigDecimal.ZERO;
                resTo.setRating(tempRating);
                resTo.setAvgRating(tempAvgRating);
                
              //Resource Comments
         //       List<CommentVO> commentListDB = service.getResourceComments(vo.getResourceId(),userId);
                List<CommentVO> commentListDB = service.getResourceCommentsForRating(vo.getResourceId(),userId);
                List<CommentRespTO> commentList = new ArrayList<CommentRespTO>(commentListDB.size());
                
                CommentRespTO resto2,childresto2 = null;
                for(CommentVO vo2 : commentListDB)
                {
                resto2 = new CommentRespTO();    
                resto2.setCommentBy(vo2.getCommentBy());
                resto2.setCommentById(vo2.getCommentById());
                resto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+vo2.getCommentByImage());
                resto2.setCommentCounts(vo2.getCommentCounts());
                resto2.setCommentDate(vo2.getCommentDate());
                resto2.setCommentId(vo2.getCommentId());
                resto2.setCommentTxt(vo2.getCommentTxt());
                resto2.setParentCommentId(vo2.getParentCommentId());
                
				//Setting Child comments
                List<CommentVO> childCommentListDB = service.getResourceChildCommentsForRating(vo2.getCommentId(),userId);
                List<CommentRespTO> childCommentsList = new ArrayList<CommentRespTO>(childCommentListDB.size());
       	         for(CommentVO cvo2 : childCommentListDB)
       	         {
       	        	childresto2 = new CommentRespTO();
       	        	childresto2.setCommentBy(cvo2.getCommentBy());
       	        	childresto2.setCommentById(cvo2.getCommentById());
       	        	childresto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+cvo2.getCommentByImage());
       	        	childresto2.setCommentDate(cvo2.getCommentDate());
       	        	childresto2.setCommentId(cvo2.getCommentId());
       	        	childresto2.setCommentTxt(cvo2.getCommentTxt());
       	        	childresto2.setParentCommentId(cvo2.getParentCommentId());
       	        	childresto2.setCommentCounts(cvo2.getCommentCounts());
       	        	 
       	        	childCommentsList.add(childresto2);
       	         }
                
       	      resto2.setSubComments(childCommentsList);
       	         
                //End - Set Comment List     
                commentList.add(resto2);        
                }
                resTo.setCommentList(commentList);
                            
                //Resources related video
                List<ResourseVO> relatedResFromDB = service.getRelatedResources(vo.getResourceId());
                List<ResourceRespTO> relatedResList = new ArrayList<ResourceRespTO>(relatedResFromDB.size());
                 
                ResourceRespTO temp=null;
                for(ResourseVO vo3 : relatedResFromDB)
                {
                 temp = new ResourceRespTO();
                 temp.setResourceId(""+vo3.getResourceId());
                 temp.setResourceName(vo3.getResourceName());
                 temp.setResourceDesc(vo3.getResourceDesc());
                 temp.setResourceUrl(vo3.getResourceUrl());
                 temp.setThumbImg(vo3.getThumbUrl());
                 temp.setUploadedDate(vo3.getUploadedDate());
                 temp.setAuthorName(vo3.getAuthorName());
                 temp.setAuthorImg(vo3.getAuthorImg());
                 
                 relatedResList.add(temp);
                }
                
                resTo.setRelatedVideoList(relatedResList);
                
                resourceList.add(resTo);
            }
             
            moduleTo.setResourceList(resourceList);
             //>>>>>>>>>>resources end
            
            //Assignment start >>
            List<AssignmentVO> assgnmentsFromDb = service.getStudentAssignmentsByModuleId(feed.getUserName(),moduleId);
            List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assgnmentsFromDb.size());
            AssignmentRespTO assignmentVo = null;
            for(AssignmentVO vo4:assgnmentsFromDb)
            {
            assignmentVo = new AssignmentRespTO();
            assignmentVo.setAssignmentId(vo4.getAssignmentId());
            assignmentVo.setAssignmentName(vo4.getAssignmentName());
            assignmentVo.setAssignmentStatus(vo4.getAssignmentStatus());
            assignmentVo.setAssignmentSubmittedBy(vo4.getAssignmentSubmittedBy());
            assignmentVo.setAssignmentSubmittedDate(vo4.getAssignmentSubmittedDate());
            assignmentVo.setAssignmentDueDate(vo4.getAssignmentDueDate());
            
            //Assignment Resources
            
            List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(vo4.getAssignmentId()); 
            List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
            ResourceRespTO resourceRespTO = null;
            for(ResourseVO vo5 : attachedResourcesFromDB)
            {
                resourceRespTO = new ResourceRespTO();
                resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                resourceRespTO.setResourceName(vo5.getResourceName());
                resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                resourceRespTO.setThumbImg(vo5.getThumbUrl());
                resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                resourceRespTO.setAuthorName(vo5.getAuthorName());
                resourceRespTO.setAuthorImg(vo5.getAuthorImg());
            
                attachedResources.add(resourceRespTO);
            }
            
            assignmentVo.setAttachedResources(attachedResources);
            assignmentList.add(assignmentVo);
            }
            
            moduleTo.setAssignmentList(assignmentList);
            
            resp.setModuleDetail(moduleTo);
            //Status response section
            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
            
            }else{
                 System.out.println("Requested module not found in teacher's courses.");     
                resp.setStatus(SLMSRestConstants.status_failure);
                resp.setStatusMessage(SLMSRestConstants.message_failure);                
                }
        }catch(Exception e){
            System.out.println("Exception # getStudentResourcesForRating "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
    
    return resp;
    }



    @Override
    public CourseResponse getModuleDetailsByFeedId(int feedId) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl(); 
            ModuleRespTO moduleTo = null;
        
        try{
             CommonServiceIface commonService = new CommonServiceImpl();
             FeedVO feed = commonService.getFeedDetail(feedId);
             int moduleId = feed.getModuleId();
             System.out.println("Feed Id = "+feedId+" Module Id = "+moduleId);
             //Module details
             CourseVO courseVO = service.getStudentModuleDetail(moduleId);

             if(courseVO != null)
             {
             moduleTo = new ModuleRespTO();
             moduleTo.setModuleId(courseVO.getModuleId());
             moduleTo.setModuleName(courseVO.getModuleName());
             moduleTo.setCourseId(courseVO.getCourseId());
                 
            //>>>>>>>>>>resources start
            List<ResourseVO> resourceListFromDB = service.getStudentResources(moduleId);
            List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
            
            ResourceRespTO resTo = null;
            for(ResourseVO vo:resourceListFromDB)
            {
                resTo = new ResourceRespTO();
                resTo.setResourceId(""+vo.getResourceId());
                resTo.setResourceName(vo.getResourceName());
                resTo.setResourceDesc(vo.getResourceDesc());
                resTo.setResourceUrl(vo.getResourceUrl());
                resTo.setThumbImg(vo.getThumbUrl());
                resTo.setAuthorName(vo.getAuthorName());
                resTo.setAuthorImg(vo.getAuthorImg());
                
                resTo.setStartedOn(vo.getStartedOn());
                resTo.setCompletedOn(vo.getCompletedOn());
                resTo.setLikeCounts(vo.getLikeCounts());
                resTo.setCommentCounts(vo.getCommentCounts());
                resTo.setShareCounts(vo.getShareCounts());
                resTo.setIsLiked(vo.isIsLiked());
                
              //Resource Comments
                List<CommentVO> commentListDB = service.getResourceComments(vo.getResourceId());
                List<CommentRespTO> commentList = new ArrayList<CommentRespTO>(commentListDB.size());
                
                CommentRespTO resto2,childresto2 = null;
                for(CommentVO vo2 : commentListDB)
                {
                resto2 = new CommentRespTO();    
                resto2.setCommentBy(vo2.getCommentBy());
                resto2.setCommentById(vo2.getCommentById());
                resto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+vo2.getCommentByImage());
                resto2.setCommentCounts(vo2.getCommentCounts());
                resto2.setCommentDate(vo2.getCommentDate());
                resto2.setCommentId(vo2.getCommentId());
                resto2.setCommentTxt(vo2.getCommentTxt());
                resto2.setLikeCounts(vo2.getLikeCounts());
                resto2.setParentCommentId(vo2.getParentCommentId());
                resto2.setShareCounts(vo2.getShareCounts());
                resto2.setIsLiked(vo2.isIsLiked());
                
				//Setting Child comments
                List<CommentVO> childCommentListDB = service.getResourceChildComments(vo2.getCommentId());
                List<CommentRespTO> childCommentsList = new ArrayList<CommentRespTO>(childCommentListDB.size());
       	         for(CommentVO cvo2 : childCommentListDB)
       	         {
       	        	childresto2 = new CommentRespTO();
       	        	childresto2.setCommentBy(cvo2.getCommentBy());
       	        	childresto2.setCommentById(cvo2.getCommentById());
       	        	childresto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+cvo2.getCommentByImage());
       	        	childresto2.setCommentDate(cvo2.getCommentDate());
       	        	childresto2.setCommentId(cvo2.getCommentId());
       	        	childresto2.setCommentTxt(cvo2.getCommentTxt());
       	        	childresto2.setParentCommentId(cvo2.getParentCommentId());
       	        	childresto2.setCommentCounts(cvo2.getCommentCounts());
       	        	childresto2.setLikeCounts(cvo2.getLikeCounts());
       	        	childresto2.setIsLiked(cvo2.isIsLiked());
       	        	 
       	        	 childCommentsList.add(childresto2);
       	         }
                
       	      resto2.setSubComments(childCommentsList);
       	         
                //End - Set Comment List     
                commentList.add(resto2);        
                }
                resTo.setCommentList(commentList);
                            
                //Resources related video
                List<ResourseVO> relatedResFromDB = service.getRelatedResources(vo.getResourceId());
                List<ResourceRespTO> relatedResList = new ArrayList<ResourceRespTO>(relatedResFromDB.size());
                 
                ResourceRespTO temp=null;
                for(ResourseVO vo3 : relatedResFromDB)
                {
                 temp = new ResourceRespTO();
                 temp.setResourceId(""+vo3.getResourceId());
                 temp.setResourceName(vo3.getResourceName());
                 temp.setResourceDesc(vo3.getResourceDesc());
                 temp.setResourceUrl(vo3.getResourceUrl());
                 temp.setThumbImg(vo3.getThumbUrl());
                 temp.setUploadedDate(vo3.getUploadedDate());
                 temp.setAuthorName(vo3.getAuthorName());
                 temp.setAuthorImg(vo3.getAuthorImg());
                 
                 relatedResList.add(temp);
                }
                
                resTo.setRelatedVideoList(relatedResList);
                
                resourceList.add(resTo);
            }
             
            moduleTo.setResourceList(resourceList);
             //>>>>>>>>>>resources end
            
            //Assignment start >>
            List<AssignmentVO> assgnmentsFromDb = service.getStudentAssignmentsByModuleId(feed.getUserName(),moduleId);
            List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assgnmentsFromDb.size());
            AssignmentRespTO assignmentVo = null;
            for(AssignmentVO vo4:assgnmentsFromDb)
            {
            assignmentVo = new AssignmentRespTO();
            assignmentVo.setAssignmentId(vo4.getAssignmentId());
            assignmentVo.setAssignmentName(vo4.getAssignmentName());
            assignmentVo.setAssignmentStatus(vo4.getAssignmentStatus());
            assignmentVo.setAssignmentSubmittedBy(vo4.getAssignmentSubmittedBy());
            assignmentVo.setAssignmentSubmittedDate(vo4.getAssignmentSubmittedDate());
            assignmentVo.setAssignmentDueDate(vo4.getAssignmentDueDate());
            
            //Assignment Resources
            
            List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(vo4.getAssignmentId()); 
            List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
            ResourceRespTO resourceRespTO = null;
            for(ResourseVO vo5 : attachedResourcesFromDB)
            {
                resourceRespTO = new ResourceRespTO();
                resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                resourceRespTO.setResourceName(vo5.getResourceName());
                resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                resourceRespTO.setThumbImg(vo5.getThumbUrl());
                resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                resourceRespTO.setAuthorName(vo5.getAuthorName());
                resourceRespTO.setAuthorImg(vo5.getAuthorImg());
            
                attachedResources.add(resourceRespTO);
            }
            
            assignmentVo.setAttachedResources(attachedResources);
            assignmentList.add(assignmentVo);
            }
            
            moduleTo.setAssignmentList(assignmentList);
            
            resp.setModuleDetail(moduleTo);
            //Status response section
            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
            
            }else{
                 System.out.println("Requested module not found in teacher's courses.");     
                resp.setStatus(SLMSRestConstants.status_failure);
                resp.setStatusMessage(SLMSRestConstants.message_failure);                
                }
        }catch(Exception e){
            System.out.println("Exception # getModuleDetailsByFeedId "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
    
    return resp;
    }

    
    
    /**
     * Returns module resources.
     * @param req (userId/courseId/moduleId/searchText)
     * @return 
     * @throws RestBusException 
     */
    @Override
    public CourseResponse getModuleResourcesForRating(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();    
        
        try{
            //>>>>>>>>>>resources start
            List<ResourseVO> resourceListFromDB = service.getStudentResourcesForRating(req.getUserId(), req.getCourseId(), req.getModuleId(), req.getSearchText(),req.getModuleSessionId());
            List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
            
            ResourceRespTO resTo = null;
            for(ResourseVO vo:resourceListFromDB)
            {
                resTo = new ResourceRespTO();
                resTo.setResourceId(""+vo.getResourceId());
                resTo.setResourceName(vo.getResourceName());
                resTo.setResourceDesc(vo.getResourceDesc());
                resTo.setResourceUrl(vo.getResourceUrl());
                resTo.setThumbImg(vo.getThumbUrl());
                resTo.setAuthorName(vo.getAuthorName());
                resTo.setAuthorImg(vo.getAuthorImg());
                resTo.setModuleSessionId(vo.getModuleSessionId());
                resTo.setModuleStatus(vo.getModuleStatus());
                resTo.setModuleName(vo.getModuleName());
                resTo.setModuleDesc(vo.getModuleDesc());
                resTo.setStartedOn(vo.getStartedOn());
                resTo.setCompletedOn(vo.getCompletedOn());
                resTo.setCommentCounts(vo.getCommentCounts());
                //Phase-3 Resource Type - >video/ppt/pdf etc
                resTo.setResourceTypeId(vo.getResourceTypeId());
                
                //Phase-3 Setting module assignments all status
                resTo.setModuleAssignmentsCancelAllStatus(vo.getModuleAssignmentsCancelAllStatus());
                resTo.setModuleAssignmentsEnableAllStatus(vo.getModuleAssignmentsEnableAllStatus());
                
                //Rating
                BigDecimal tempRating=vo.getRating()!=null?vo.getRating():BigDecimal.ZERO;
                BigDecimal tempAvgRating=vo.getAvgRating()!=null?vo.getAvgRating():BigDecimal.ZERO;
                resTo.setRating(tempRating);
                resTo.setAvgRating(tempAvgRating);
                
                //Resource Comments
                List<CommentVO> commentListDB = service.getResourceCommentsForRating(req.getUserId(),vo.getResourceId());
                List<CommentRespTO> commentList = new ArrayList<CommentRespTO>(commentListDB.size());
                
                CommentRespTO resto2,childResto2 = null;
                for(CommentVO vo2 : commentListDB)
                {
                resto2 = new CommentRespTO();    
                resto2.setCommentBy(vo2.getCommentBy());
                resto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+vo2.getCommentByImage());
                resto2.setCommentById(vo2.getCommentById());
                resto2.setCommentCounts(vo2.getCommentCounts());
                resto2.setCommentDate(vo2.getCommentDate());
                resto2.setCommentId(vo2.getCommentId());
                resto2.setCommentTxt(vo2.getCommentTxt());
                resto2.setParentCommentId(vo2.getParentCommentId());
                resto2.setShareCounts(vo2.getShareCounts());
                
                //Setting Child comments
                List<CommentVO> childCommentListDB = service.getResourceChildCommentsForRating(req.getUserId(),vo2.getCommentId());
                List<CommentRespTO> childCommentsList = new ArrayList<CommentRespTO>(childCommentListDB.size());
       	         for(CommentVO cvo2 : childCommentListDB)
       	         {
       	        	childResto2 = new CommentRespTO();
       	        	childResto2.setCommentBy(cvo2.getCommentBy());
       	        	childResto2.setCommentById(cvo2.getCommentById());
       	        	childResto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+cvo2.getCommentByImage());
       	        	childResto2.setCommentDate(cvo2.getCommentDate());
       	        	childResto2.setCommentId(cvo2.getCommentId());
       	        	childResto2.setCommentTxt(cvo2.getCommentTxt());
       	        	childResto2.setParentCommentId(cvo2.getParentCommentId());
       	        	childResto2.setCommentCounts(cvo2.getCommentCounts());
       	        	 
       	        	 childCommentsList.add(childResto2);
       	         }
       	         resto2.setSubComments(childCommentsList);
                 commentList.add(resto2);        
                 }
                 resTo.setCommentList(commentList);
       	                 
                //Resources related video
                List<ResourseVO> relatedResFromDB = service.getRelatedResources(vo.getResourceId());
                List<ResourceRespTO> relatedResList = new ArrayList<ResourceRespTO>(relatedResFromDB.size());
                                              
                ResourceRespTO temp=null;
                for(ResourseVO vo3 : relatedResFromDB)
                {
                 temp = new ResourceRespTO();
                 temp.setResourceId(""+vo3.getResourceId());
                 temp.setResourceName(vo3.getResourceName());
                 temp.setResourceDesc(vo3.getResourceDesc());
                 temp.setAuthorName(vo3.getAuthorName());
                 temp.setAuthorImg(vo3.getAuthorImg());
                 temp.setResourceUrl(vo3.getResourceUrl());
                 temp.setThumbImg(vo3.getThumbUrl());
                 temp.setUploadedDate(vo3.getUploadedDate());
                 temp.setUploadedBy(vo3.getUploadedBy());
                 //Phase-3 Resource Type - >video/ppt/pdf etc
                 temp.setResourceTypeId(vo3.getResourceTypeId());
                 
                 relatedResList.add(temp);
                }
                
                resTo.setRelatedVideoList(relatedResList);
                
                resourceList.add(resTo);
            }
             
            resp.setResourceList(resourceList);
             //>>>>>>>>>>resources end
            
            //Assignment start >>
            List<AssignmentVO> assgnmentsFromDb = service.getStudentAssignments(req.getModuleSessionId(), req.getModuleId(),req.getUserId());
            List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assgnmentsFromDb.size());
            AssignmentRespTO assignmentVo = null;
            for(AssignmentVO vo4:assgnmentsFromDb)
            {
            assignmentVo = new AssignmentRespTO();
            assignmentVo.setAssignmentId(vo4.getAssignmentId());
            assignmentVo.setAssignmentName(vo4.getAssignmentName());
            assignmentVo.setAssignmentDesc(vo4.getAssignmentDesc());
            assignmentVo.setAssignmentStatus(vo4.getAssignmentStatus());
            assignmentVo.setAssignmentSubmittedBy(vo4.getAssignmentSubmittedBy());
            assignmentVo.setAssignmentSubmittedDate(vo4.getAssignmentSubmittedDate());
            assignmentVo.setAssignmentDueDate(vo4.getAssignmentDueDate());
            assignmentVo.setAssignmentType(vo4.getAssignmentType());
            assignmentVo.setEnableStatus(vo4.getEnableStatus());
            assignmentVo.setCancelStatus(vo4.getCancelStatus());
            assignmentVo.setCourseName(vo4.getCourseName());
            /*
            //Assignment Resources
            List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(req.getUserId(), vo4.getAssignmentId());
            List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
            ResourceRespTO resourceRespTO = null;
            for(ResourseVO vo5 : attachedResourcesFromDB)
            {
                resourceRespTO = new ResourceRespTO();
                resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                resourceRespTO.setResourceName(vo5.getResourceName());
                resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                resourceRespTO.setThumbImg(vo5.getThumbUrl());
                resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                resourceRespTO.setAuthorName(vo5.getAuthorName());
                resourceRespTO.setAuthorImg(vo5.getAuthorImg());
             //Phase-3 Resource Type - >video/ppt/pdf etc
                resTo.setResourceTypeId(vo.getResourceTypeId());
                
                attachedResources.add(resourceRespTO);
            }
            
            assignmentVo.setAttachedResources(attachedResources);
            */
            
            assignmentList.add(assignmentVo);
            }
            
            resp.setAssignmentList(assignmentList);
            //Status response section
            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getModuleResourcesForRating "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
    
    return resp;
    }

    
    /**
     * Returns module resources.
     * @param req (userId/courseId/moduleId/searchText)
     * @return 
     * @throws RestBusException 
     */
    @Override
    public CourseResponse getModuleResources(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();    
        
        try{
            //>>>>>>>>>>resources start
            List<ResourseVO> resourceListFromDB = service.getStudentResources(req.getUserId(), req.getCourseId(), req.getModuleId(), req.getSearchText(),req.getModuleSessionId());
            List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>(resourceListFromDB.size());
            
            ResourceRespTO resTo = null;
            for(ResourseVO vo:resourceListFromDB)
            {
                resTo = new ResourceRespTO();
                resTo.setResourceId(""+vo.getResourceId());
                resTo.setResourceName(vo.getResourceName());
                resTo.setResourceDesc(vo.getResourceDesc());
                resTo.setResourceUrl(vo.getResourceUrl());
                resTo.setThumbImg(vo.getThumbUrl());
                resTo.setAuthorName(vo.getAuthorName());
                resTo.setAuthorImg(vo.getAuthorImg());
                resTo.setModuleSessionId(vo.getModuleSessionId());
                resTo.setModuleStatus(vo.getModuleStatus());
                resTo.setModuleName(vo.getModuleName());
                resTo.setModuleDesc(vo.getModuleDesc());
                resTo.setStartedOn(vo.getStartedOn());
                resTo.setCompletedOn(vo.getCompletedOn());
                resTo.setLikeCounts(vo.getLikeCounts());
                resTo.setCommentCounts(vo.getCommentCounts());
                resTo.setShareCounts(vo.getShareCounts());
                resTo.setIsLiked(vo.isIsLiked());
                System.out.println("isliked"+vo.isIsLiked());
              //Resource Comments
                List<CommentVO> commentListDB = service.getResourceComments(req.getUserId(),vo.getResourceId());
                List<CommentRespTO> commentList = new ArrayList<CommentRespTO>(commentListDB.size());
                
                CommentRespTO resto2,childResto2 = null;
                for(CommentVO vo2 : commentListDB)
                {
                resto2 = new CommentRespTO();    
                resto2.setCommentBy(vo2.getCommentBy());
                resto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+vo2.getCommentByImage());
                resto2.setCommentById(vo2.getCommentById());
                resto2.setCommentCounts(vo2.getCommentCounts());
                resto2.setCommentDate(vo2.getCommentDate());
                resto2.setCommentId(vo2.getCommentId());
                resto2.setCommentTxt(vo2.getCommentTxt());
                resto2.setLikeCounts(vo2.getLikeCounts());
                resto2.setParentCommentId(vo2.getParentCommentId());
                resto2.setShareCounts(vo2.getShareCounts());
                resto2.setIsLiked(vo2.isIsLiked());
                               
                
                //Setting Child comments
                List<CommentVO> childCommentListDB = service.getResourceChildComments(req.getUserId(),vo2.getCommentId());
                List<CommentRespTO> childCommentsList = new ArrayList<CommentRespTO>(childCommentListDB.size());
       	         for(CommentVO cvo2 : childCommentListDB)
       	         {
       	        	childResto2 = new CommentRespTO();
       	        	childResto2.setCommentBy(cvo2.getCommentBy());
       	        	childResto2.setCommentById(cvo2.getCommentById());
       	        	childResto2.setCommentByImage(PropertyManager.getProperty(SLMSRestConstants.baseUrl_userprofile)+cvo2.getCommentByImage());
       	        	childResto2.setCommentDate(cvo2.getCommentDate());
       	        	childResto2.setCommentId(cvo2.getCommentId());
       	        	childResto2.setCommentTxt(cvo2.getCommentTxt());
       	        	childResto2.setParentCommentId(cvo2.getParentCommentId());
       	        	childResto2.setCommentCounts(cvo2.getCommentCounts());
       	        	childResto2.setLikeCounts(cvo2.getLikeCounts());
       	        	childResto2.setIsLiked(cvo2.isIsLiked());
       	        	 
       	        	 childCommentsList.add(childResto2);
       	         }
       	         resto2.setSubComments(childCommentsList);
                 commentList.add(resto2);        
                 }
                 resTo.setCommentList(commentList);
       	                 
                //Resources related video
                List<ResourseVO> relatedResFromDB = service.getRelatedResources(vo.getResourceId());
                List<ResourceRespTO> relatedResList = new ArrayList<ResourceRespTO>(relatedResFromDB.size());
                                              
                ResourceRespTO temp=null;
                for(ResourseVO vo3 : relatedResFromDB)
                {
                 temp = new ResourceRespTO();
                 temp.setResourceId(""+vo3.getResourceId());
                 temp.setResourceName(vo3.getResourceName());
                 temp.setResourceDesc(vo3.getResourceDesc());
                 temp.setAuthorName(vo3.getAuthorName());
                 temp.setAuthorImg(vo3.getAuthorImg());
                 temp.setResourceUrl(vo3.getResourceUrl());
                 temp.setThumbImg(vo3.getThumbUrl());
                 temp.setUploadedDate(vo3.getUploadedDate());
                 temp.setUploadedBy(vo3.getUploadedBy());
                 
                 relatedResList.add(temp);
                }
                
                resTo.setRelatedVideoList(relatedResList);
                
                resourceList.add(resTo);
            }
             
            resp.setResourceList(resourceList);
             //>>>>>>>>>>resources end
            
            //Assignment start >>
            List<AssignmentVO> assgnmentsFromDb = service.getStudentAssignments(0, req.getModuleId(),req.getUserId());
            List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>(assgnmentsFromDb.size());
            AssignmentRespTO assignmentVo = null;
            for(AssignmentVO vo4:assgnmentsFromDb)
            {
            assignmentVo = new AssignmentRespTO();
            assignmentVo.setAssignmentId(vo4.getAssignmentId());
            assignmentVo.setAssignmentName(vo4.getAssignmentName());
            assignmentVo.setAssignmentStatus(vo4.getAssignmentStatus());
            assignmentVo.setAssignmentSubmittedBy(vo4.getAssignmentSubmittedBy());
            assignmentVo.setAssignmentSubmittedDate(vo4.getAssignmentSubmittedDate());
            assignmentVo.setAssignmentDueDate(vo4.getAssignmentDueDate());
            
            //Assignment Resources

            List<ResourseVO> attachedResourcesFromDB=service.getAssignmentsResources(req.getUserId(), vo4.getAssignmentId());
            List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>(attachedResourcesFromDB.size());
            ResourceRespTO resourceRespTO = null;
            for(ResourseVO vo5 : attachedResourcesFromDB)
            {
                resourceRespTO = new ResourceRespTO();
                resourceRespTO.setResourceId(String.valueOf(vo5.getResourceId()));
                resourceRespTO.setResourceName(vo5.getResourceName());
                resourceRespTO.setResourceDesc(vo5.getResourceDesc());
                resourceRespTO.setResourceUrl(vo5.getResourceUrl());
                resourceRespTO.setThumbImg(vo5.getThumbUrl());
                resourceRespTO.setUploadedDate(vo5.getUploadedDate());
                resourceRespTO.setAuthorName(vo5.getAuthorName());
                resourceRespTO.setAuthorImg(vo5.getAuthorImg());
            
                attachedResources.add(resourceRespTO);
            }
            
            assignmentVo.setAttachedResources(attachedResources);
            assignmentList.add(assignmentVo);
            }
            
            resp.setAssignmentList(assignmentList);
            //Status response section
            resp.setStatus(SLMSRestConstants.status_success);
            resp.setStatusMessage(SLMSRestConstants.message_success); 
        }catch(Exception e){
            System.out.println("Exception # getModuleResources "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
    
    return resp;
    }

    
    
    
    /**
     * Returns module resources.
     * @param req (userId/courseId/moduleId/searchText)
     * @return 
     * @throws RestBusException 
     */
    
    public CourseResponse getModuleResources_x(CourseRequest req) throws RestBusException {
        CourseResponse resp = new CourseResponse();
        
    List<ResourceRespTO> resourceList = new ArrayList<ResourceRespTO>();
    
    ResourceRespTO vdo = new ResourceRespTO();
    vdo.setResourceId("1");
    vdo.setResourceDesc("Resource 1");
    vdo.setResourceUrl("testresource1.com");
    vdo.setAuthorName("Irish Allen");
    vdo.setStartedOn("2015-01-01");
    vdo.setCompletedOn("2015-01-21");
    vdo.setLikeCounts(21);
    vdo.setShareCounts(45);
    vdo.setCommentCounts(500);
    
    
   // related videos    
    List<ResourceRespTO> relatedVideoList = new ArrayList<ResourceRespTO>();
    
    ResourceRespTO vdo1 = new ResourceRespTO();
    vdo1.setResourceId("1");
    vdo1.setResourceDesc("Test resource1");
    vdo1.setResourceUrl("testresource1.com");
    vdo1.setUploadedDate("2000-01-01");
    relatedVideoList.add(vdo1);

    ResourceRespTO vdo2 = new ResourceRespTO();
    vdo2.setResourceId("1");
    vdo2.setResourceDesc("Test resource1");
    vdo2.setResourceUrl("testresource1.com");
    vdo2.setUploadedDate("2000-01-01");
    relatedVideoList.add(vdo2);
    
    ResourceRespTO vdo3 = new ResourceRespTO();
    vdo3.setResourceId("1");
    vdo3.setResourceDesc("Test resource1");
    vdo3.setResourceUrl("testresource1.com");
    vdo3.setUploadedDate("2000-01-01");
    relatedVideoList.add(vdo3);    
    
    
    List<CommentRespTO> commentList = new ArrayList<CommentRespTO>();
    
    CommentRespTO crt1 = new CommentRespTO();
    crt1.setCommentBy("Dixit");
    crt1.setCommentCounts(11);
    crt1.setCommentDate("2015-01-05");
    crt1.setCommentTxt("Swimming is a good excercise for the health.");
    crt1.setLikeCounts(23);
    crt1.setShareCounts(45);
    crt1.setCommentId(10);
    crt1.setParentCommentId(0);
    commentList.add(crt1);
    
    CommentRespTO crt2 = new CommentRespTO();
    crt2.setCommentBy("Mayank");
    crt2.setCommentCounts(11);
    crt2.setCommentDate("2015-01-12");
    crt2.setCommentTxt("hdfgsjf kashdajkhd hasdkahsd 8qwyeq8e ajsdhajshd aidsIUD SJDFHSFD");
    crt2.setLikeCounts(23);
    crt2.setShareCounts(45);
    crt2.setCommentId(11);
    crt2.setParentCommentId(0);
    commentList.add(crt2);
    
    vdo.setCommentList(commentList);
    vdo.setRelatedVideoList(relatedVideoList);
    resourceList.add(vdo);

    
    ResourceRespTO vdo2x = vdo;
    vdo2x.setAuthorName("Hunny singh choutala");
    vdo2x.setResourceDesc("Resource 2");
    resourceList.add(vdo2x);

    resp.setResourceList(resourceList);

    //Assignments -------------------- > 
    List<AssignmentRespTO> assignmentList = new ArrayList<AssignmentRespTO>();
    
    AssignmentRespTO assignmentRespTO = new AssignmentRespTO();
    assignmentRespTO.setAssignmentId(1);
    assignmentRespTO.setAssignmentName("Assignment-1");
    assignmentRespTO.setAssignmentStatus("Completed");
    assignmentRespTO.setAssignmentSubmittedBy("Mayankd");
    assignmentRespTO.setAssignmentSubmittedDate("2015-01-07");
    
    List<ResourceRespTO> attachedResources = new ArrayList<ResourceRespTO>();
    attachedResources.add(vdo);
    attachedResources.add(vdo1);
    attachedResources.add(vdo2);
    
    assignmentRespTO.setAttachedResources(attachedResources);
    assignmentList.add(assignmentRespTO);
    
    AssignmentRespTO assignmentRespTO2=assignmentRespTO;
    assignmentRespTO2.setAssignmentName("Assignment-2");
    assignmentRespTO2.setAssignmentId(2);
    
    assignmentRespTO2.setAttachedResources(attachedResources);
    assignmentList.add(assignmentRespTO2);
    resp.setAssignmentList(assignmentList);
    
    
    resp.setStatus(1001);
    resp.setStatusMessage("success");
    
    return resp;
    }

    
    
    @Override
    public CourseResponse commentOnComment(CourseRequest req) throws RestBusException {
       CourseResponse resp = new CourseResponse();
       
       try{
                CourseServiceIface service = new CourseServiceImpl();
                service.saveCommentComment(req.getUserId(), req.getCommentId(), req.getCommentText());

                //Track comment activity
                new CommonServiceImpl().saveActivity(req.getUserId(), SLMSRestConstants.ACTIVITY_CD_COMMMENT);
                
                //setting success into response
                resp.setStatus(SLMSRestConstants.status_success);
                resp.setStatusMessage(SLMSRestConstants.message_success);                   

        } catch (LmsServiceException ex) {
            System.out.println("LmsServiceException # commentOnComment "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception # commentOnComment "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        }
       
       return resp;
    }

    @Override
    public CourseResponse commentOnResource(CourseRequest req) throws RestBusException {
       CourseResponse resp = new CourseResponse();
       
       try{
                CourseServiceIface service = new CourseServiceImpl();
                service.saveResourceComment(req.getUserId(), req.getResourceId(), req.getCommentText());

                //Track comment activity
                new CommonServiceImpl().saveActivity(req.getUserId(), SLMSRestConstants.ACTIVITY_CD_COMMMENT);
                
                //setting success into response
                resp.setStatus(SLMSRestConstants.status_success);
                resp.setStatusMessage(SLMSRestConstants.message_success);                   

        } catch (LmsServiceException ex) {
            System.out.println("LmsServiceException # commentOnResource "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception # commentOnResource "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        }
       
       return resp;
    }
    

    @Override
    public CourseResponse likeOnComment(int userId, int commentId) throws RestBusException {
       CourseResponse resp = new CourseResponse();
       
       try{
                CourseServiceIface service = new CourseServiceImpl();
                service.saveCommentLike(userId, commentId);
           
                //setting success into response
                resp.setStatus(SLMSRestConstants.status_success);
                resp.setStatusMessage(SLMSRestConstants.message_success);                   

        } catch (LmsServiceException ex) {
            System.out.println("LmsServiceException # likeOnComment "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception # likeOnComment "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        }
       
       return resp;
    }

    
    @Override
    public CourseResponse likeOnResource(int userId, int resourceId) throws RestBusException {
       CourseResponse resp = new CourseResponse();
       
       try{
                CourseServiceIface service = new CourseServiceImpl();
                service.saveResourceLike(userId, resourceId);
           
                //setting success into response
                resp.setStatus(SLMSRestConstants.status_success);
                resp.setStatusMessage(SLMSRestConstants.message_success);                   

        } catch (LmsServiceException ex) {
            System.out.println("LmsServiceException # likeOnResource "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception # likeOnResource "+ex.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(ex.getMessage());
        }
       
       return resp;
    }

    
	@Override
    public CourseResponse rateAssignment(CourseRequest req) throws RestBusException {
            CourseResponse resp = new CourseResponse();
            CourseServiceIface service = new CourseServiceImpl();
    
            try {
            	int ratingDataCount=req.getRatingParameters().size();
            	
            	if(ratingDataCount>0)
            	{		
            		ArrayList<CommonKeyValueVO> list=new ArrayList<CommonKeyValueVO>(ratingDataCount);
            		CommonKeyValueVO vo=null;
            		for(KeyValTypeVO temp : req.getRatingParameters())
            		{
            			vo=new CommonKeyValueVO(temp.getKey(),temp.getValue());
            			list.add(vo);
            		}
            		
            	service.setRatingData(req.getUserId(), req.getAssignmentResourceTxnId(),list);	
            	
                resp.setStatus(SLMSRestConstants.status_success);
                resp.setStatusMessage(SLMSRestConstants.message_success); 	
            	}else{
                    resp.setStatus(SLMSRestConstants.status_badrequest);
                    resp.setStatusMessage(SLMSRestConstants.message_badrequest); 	            		
            	}

        }catch(Exception e){
            System.out.println("Exception # rateAssignment "+e.getMessage());
            resp.setStatus(SLMSRestConstants.status_failure);
            resp.setStatusMessage(SLMSRestConstants.message_failure);
            resp.setErrorMessage(e.getMessage());            
        }
            
        return resp;
    }
    
    
	@Override
	public int uploadAssignment(int assignmentId,String resourceName,String resourceAuthor, String resourceDesc,int userId, String descTxt, String url, String thumbUrl, String authorImgUrl)
			throws RestBusException {
			int status = 0;
        
        try {
        	 CourseServiceIface service = new CourseServiceImpl();

        	 status  = service.uploadAssignment(assignmentId, resourceName, resourceAuthor, resourceDesc, userId, descTxt, url, thumbUrl, authorImgUrl);
             
        	 //Track submit assignment activity
             new CommonServiceImpl().saveActivity(userId, SLMSRestConstants.ACTIVITY_CD_SUBMIT_ASSIGNMENT);
             
        } catch (Exception ex) {
            System.out.println("Exception # uploadAssignment "+ex.getMessage());
            throw new RestBusException("Exception # uploadAssignment "+ex.getMessage());
        }
        
       return status;  
	}


	/**
	 * Use to save submitted all type assignments except video upload by a student. 
	 * 1) Update assignment status.
	 * 2) Save answers of different type of questions submitted by a student.
	 */
	@Override
	public CourseResponse submitAssignment(AssignmentRequest req) throws RestBusException {
        CourseResponse resp = new CourseResponse();
        CourseServiceIface service = new CourseServiceImpl();

        try {
        	//Start Getting request data
        	List<AssignmentQuestionRespTO> assignmentQuestions=req.getAssignmentQuestions();
        	List<AssignmentQuestionVO> listToDb=new ArrayList<AssignmentQuestionVO>(assignmentQuestions.size());
        	if(assignmentQuestions.size()>0)
        	{		
        		AssignmentQuestionVO vo=null;
        		for(AssignmentQuestionRespTO to:assignmentQuestions)
        		{
        			if(req.getAssignmentTypeId()==3)//MCQ
        			{
        				for(MCQRespTO mto:to.getOptions())
        				{
                			vo=new AssignmentQuestionVO();
                			vo.setQuestionId(to.getQuestionId());
                			vo.setAnswerText(to.getAnswerText());        					
        					vo.setOptionId(mto.getOptionId());
        					listToDb.add(vo);
        				}
        			}else{
            			vo=new AssignmentQuestionVO();
            			vo.setQuestionId(to.getQuestionId());
            			vo.setAnswerText(to.getAnswerText());        				
        				listToDb.add(vo);	
        			}
        		}//End for
        	//Saving data to db	
        		int status=service.submitAssignment(req.getAssignmentResourceTxnId(),req.getAssignmentSubmittedById(), req.getAssignmentTypeId(), listToDb,req.getSchoolId(),req.getCourseId());
        		
        		if(status>0)
        		{
	            resp.setStatus(SLMSRestConstants.status_success);
	            resp.setStatusMessage(SLMSRestConstants.message_success);
        		}else{
    	            resp.setStatus(SLMSRestConstants.status_noUpdate);
    	            resp.setStatusMessage(SLMSRestConstants.message_noUpdate);
        		}
        	}else{
                resp.setStatus(SLMSRestConstants.status_badrequest);
                resp.setStatusMessage(SLMSRestConstants.message_badrequest); 	            		
        	}

    }catch(Exception e){
        System.out.println("Exception # submitAssignment "+e.getMessage());
        resp.setStatus(SLMSRestConstants.status_failure);
        resp.setStatusMessage(SLMSRestConstants.message_failure);
        resp.setErrorMessage(e.getMessage());            
    }
        
    return resp;
}

    
}//end of class
