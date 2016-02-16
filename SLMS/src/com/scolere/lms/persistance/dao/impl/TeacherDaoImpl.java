package com.scolere.lms.persistance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.cross.FeedVO;
import com.scolere.lms.persistance.dao.iface.TeacherDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import com.scolere.lms.service.iface.NotificationServiceIface;
import com.scolere.lms.service.impl.NotificationServiceImpl;


public class TeacherDaoImpl extends LmsDaoAbstract implements TeacherDao{
	
	Logger logger = LoggerFactory.getLogger(TeacherDaoImpl.class);

	@Override
	public int updateCourseStatus(int courseSessionId,int statusCode) throws LmsDaoException {
		int updateCount=0;
		
		if(statusCode==0)
		{
			//Course started
			String queryUpdateCourseStatus="update teacher_course_sessions set IS_COMPLETE='0',START_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where IS_COMPLETE='2' and COURSE_SESSION_ID="+courseSessionId;
			updateCount = updateByQuery(queryUpdateCourseStatus);
			System.out.println("queryUpdateCourseStatus #2 status = "+updateCount);		
			
			if(updateCount>0)
			{
	         //Insert all Student & their assignments into assignment txn table for a course
	           String assignmentPopulateQuery="INSERT INTO assignment_resource_txn(ASSIGNMENT_ID, STUDENT_ID, UPLODED_RESOURCE_ID, UPLOADED_ON, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, DUE_ON,ENABLE_FL,RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL, ASSIGNMENT_NAME, ASSIGNMENT_DESC_TXT,CANCEL_FL) SELECT mam.ASSIGNMENT_ID,ucm.USER_ID,null,null,'1',ucm.USER_ID,current_timestamp,date(mod_sess.END_SESSION_TM),mod_sess.IS_COMPLETED ,'','',0,'',0,'','','','','0',asmnt.ASSIGNMENT_NAME,asmnt.DESC_TXT,'0' FROM teacher_courses course inner join teacher_course_sessions course_sess on course.TEACHER_COURSE_ID=course_sess.TEACHER_COURSE_ID inner join teacher_course_session_dtls mod_sess on course_sess.COURSE_SESSION_ID=mod_sess.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=mod_sess.MODULE_ID inner join assignment asmnt on asmnt.ASSIGNMENT_ID=mam.ASSIGNMENT_ID inner join user_cls_map ucm on ucm.SCHOOL_ID=course.SCHOOL_ID where course_sess.COURSE_SESSION_ID="+courseSessionId;
			   int assignmentsTxnCount = updateByQuery(assignmentPopulateQuery);
			   System.out.println("Assignment txn records count = "+assignmentsTxnCount);	
				
			//Create A feed | lms_feed_type = 5 |  $ has started course $      <-   user,course 
	            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM) SELECT  5,'Course Started',tc.TEACHER_ID ,tc.COURSE_ID,null,null,null,tc.HRM_ID,tc.TEACHER_ID,current_timestamp FROM teacher_courses tc inner join teacher_course_sessions crs on tc.TEACHER_COURSE_ID=crs.TEACHER_COURSE_ID where crs.COURSE_SESSION_ID="+courseSessionId;
	            manageUpdate(feedQuery);
			}
		}else{
			updateCount=-1;
		}		
		
		
	    return updateCount;
	}
	
	

	/**
	 * Course/Module status code :
	 * (2) Module not started yet
	 * (1) Module completed
	 * (0) Module in progress or started
	 */
	@Override
	public int updateCourseModuleStatus(int moduleSessionId,int statusCode)
			throws LmsDaoException {
	     
		int updateCount=0;

		if(statusCode==1)
		{
		//update module status
		String queryUpdateModuleStatus="update teacher_course_session_dtls set IS_COMPLETED='1',END_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where IS_COMPLETED='0' and COURSE_SESSION_DTLS_ID="+moduleSessionId;
		updateCount = updateByQuery(queryUpdateModuleStatus);
		System.out.println("Module update status = "+updateCount);		
		
		if(updateCount>0)
		{
		//Phase-3 : Enable all associated assignments	
			String queryEnableModuleAssignments="update assignment_resource_txn set ENABLE_FL='1',CANCEL_FL='2' where ASSIGNMENT_ID in (select mam.ASSIGNMENT_ID from teacher_course_session_dtls tcsdtl inner join module_assignment_map mam on mam.MODULE_ID=tcsdtl.MODULE_ID where COURSE_SESSION_DTLS_ID="+moduleSessionId+")";
			int t3 = updateByQuery(queryEnableModuleAssignments);
			System.out.println("queryEnableModuleAssignments status = "+t3);			
			
		//update all associated resources
			String queryUpdateModuleResources="update teacher_module_session_dtls set IS_COMPLETED='1',END_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where COURSE_SESSION_DTLS_ID="+moduleSessionId;
			int t1 = updateByQuery(queryUpdateModuleResources);
			System.out.println("queryUpdateModuleResources status = "+t1);			
		
		//update course status on the basis sibling modules
			String queryCountNonCompletedModules="SELECT count(*) FROM teacher_course_session_dtls where IS_COMPLETED != '1' and COURSE_SESSION_ID=(SELECT COURSE_SESSION_ID FROM teacher_course_session_dtls where COURSE_SESSION_DTLS_ID="+moduleSessionId+")";
			int nonCompletedModules=getCountQueryResult(queryCountNonCompletedModules);
			
				if(nonCompletedModules>0)
				{
					//Not all modules are completed => start course
					String queryUpdateCourseStatus="update teacher_course_sessions set IS_COMPLETE='0',START_SESSION_TM=current_timestamp where TEACHER_COURSE_ID=(SELECT COURSE_SESSION_ID FROM teacher_course_session_dtls where COURSE_SESSION_DTLS_ID="+moduleSessionId+")";
					int t2 = updateByQuery(queryUpdateCourseStatus);
					
					System.out.println("queryUpdateCourseStatus status = "+t2);	
				}
				else
				{
					//All modules are completed
					String queryUpdateCourseStatus="update teacher_course_sessions set IS_COMPLETE='1',END_SESSION_TM=current_timestamp where TEACHER_COURSE_ID=(SELECT COURSE_SESSION_ID FROM teacher_course_session_dtls where COURSE_SESSION_DTLS_ID="+moduleSessionId+")";
					int t2 = updateByQuery(queryUpdateCourseStatus);
					
					System.out.println("queryUpdateCourseStatus status = "+t2);	
					
					if(t2>0)
					{
					//Feed for course completed
		            //Create A feed | lms_feed_type = 4 |  $ has completed course $  <- user,course
		            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM)SELECT  4,'Course Completed',modl.TEACHER_ID as user_id,tc.COURSE_ID,null,null,null,tc.HRM_ID,modl.TEACHER_ID,current_timestamp FROM teacher_courses tc inner join teacher_course_sessions crs on tc.TEACHER_COURSE_ID=crs.TEACHER_COURSE_ID inner join teacher_course_session_dtls modl on crs.COURSE_SESSION_ID=modl.COURSE_SESSION_ID where modl.COURSE_SESSION_DTLS_ID="+moduleSessionId;
//		            boolean feedStatus = deleteOrUpdateByQuery(feedQuery);
//		           logger.debug("Feed Creation ? "+feedStatus);
		            manageUpdate(feedQuery);
					}
				}
		}
		}else if(statusCode==0)
		{
			//Start module 
			//update module status
			String queryUpdateModule="update teacher_course_session_dtls set IS_COMPLETED='0',START_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where IS_COMPLETED='2' and COURSE_SESSION_DTLS_ID="+moduleSessionId;
			updateCount = updateByQuery(queryUpdateModule);
			
			System.out.println("queryUpdateModule status = "+updateCount);		
		
			//Start the associated course if it not started yet
			String queryUpdateCourse="update teacher_course_sessions set IS_COMPLETE='0',START_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where IS_COMPLETE='2' and COURSE_SESSION_ID=(SELECT COURSE_SESSION_ID FROM teacher_course_session_dtls where COURSE_SESSION_DTLS_ID="+moduleSessionId+")";
            boolean queryCourseStatus = deleteOrUpdateByQuery(queryUpdateCourse);
            
           logger.debug("queryUpdateCourse ? "+queryCourseStatus);
			
            if(updateCount>0)
            {
			//New feed for starting the module  <= resourceSessionId
            //Create A feed | lms_feed_type = 3 |  $ has started Module $ of course $  <- user,module,course
            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM)SELECT  3,'Module Started',modl.TEACHER_ID as user_id,tc.COURSE_ID,(SELECT distinct min(mrm.RESOURCE_ID) FROM module_mstr mm inner join module_resource_map mrm Where mrm.MODULE_ID=mm.MODULE_ID and mm.MODULE_ID=modl.MODULE_ID),null,modl.MODULE_ID,tc.HRM_ID,modl.TEACHER_ID,current_timestamp FROM teacher_courses tc inner join teacher_course_sessions crs on tc.TEACHER_COURSE_ID=crs.TEACHER_COURSE_ID inner join teacher_course_session_dtls modl on crs.COURSE_SESSION_ID=modl.COURSE_SESSION_ID where modl.COURSE_SESSION_DTLS_ID="+moduleSessionId;
//            boolean feedStatus = deleteOrUpdateByQuery(feedQuery);
//           logger.debug("Feed Creation ? "+feedStatus);			
            manageUpdate(feedQuery);
            }
		}
		else{
			//Need to start any resource first
			updateCount=-1;
		}
		
		
	    return updateCount;
	}
	

	/**
	 * Resource status code :
	 * (1) Resource completed
	 * (0) Resource incomplete
	 */	
	@Override
	public int updateCourseResourceStatus(int resourseSessionId,int statusCode)
			throws LmsDaoException {
	     
		int updateCount=0;

		if(statusCode==1)
		{
		//update resource status
		String queryUpdateResource="update teacher_module_session_dtls set IS_COMPLETED='1',END_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where MODULE_SESSION_DTLS_ID="+resourseSessionId;
		updateCount = updateByQuery(queryUpdateResource);
		
		System.out.println("queryUpdateResource status = "+updateCount);			
		
		if(updateCount>0)
		{
		
			String queryCountNonCompletedResources="SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED!='1' and COURSE_SESSION_DTLS_ID=(SELECT COURSE_SESSION_DTLS_ID FROM teacher_module_session_dtls where MODULE_SESSION_DTLS_ID="+resourseSessionId+")";
			int countNonCompletedResources = getCountQueryResult(queryCountNonCompletedResources);
			
				if(countNonCompletedResources>0)
				{
					//Not all resources are completed ********
					
					//update module status on the basis of sibling resources
					String queryUpdateModule="update teacher_course_session_dtls set IS_COMPLETED='0',START_SESSION_TM=current_timestamp,LAST_UPDT_TM=current_timestamp where IS_COMPLETED='2' and COURSE_SESSION_DTLS_ID=(SELECT COURSE_SESSION_DTLS_ID FROM teacher_module_session_dtls where MODULE_SESSION_DTLS_ID="+resourseSessionId+")";
					int t1 = updateByQuery(queryUpdateModule);
					
					System.out.println("queryUpdateModule status = "+t1);		
					
					//update course status on the basis sibling modules
					String queryUpdateCourseStatus="update teacher_course_sessions set IS_COMPLETE='0',START_SESSION_TM=current_timestamp where TEACHER_COURSE_ID=(SELECT COURSE_SESSION_ID FROM teacher_course_session_dtls where COURSE_SESSION_DTLS_ID=(SELECT COURSE_SESSION_DTLS_ID FROM teacher_module_session_dtls where MODULE_SESSION_DTLS_ID="+resourseSessionId+"))";
					int t2 = updateByQuery(queryUpdateCourseStatus);
					
					System.out.println("queryUpdateCourseStatus status = "+t2);					
					
					if(t1>0)
					{
					//New feed for starting the module  <= resourceSessionId
		            //Create A feed | lms_feed_type = 3 |  $ has started Module $ of course $  <- user,module,course
		            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM)SELECT  3,'Module Started',modl.TEACHER_ID as user_id,tc.COURSE_ID,null,null,modl.MODULE_ID,tc.HRM_ID,modl.TEACHER_ID,current_timestamp FROM teacher_courses tc inner join teacher_course_sessions crs on tc.TEACHER_COURSE_ID=crs.TEACHER_COURSE_ID inner join teacher_course_session_dtls modl on crs.COURSE_SESSION_ID=modl.COURSE_SESSION_ID where modl.COURSE_SESSION_DTLS_ID=(SELECT COURSE_SESSION_DTLS_ID FROM teacher_module_session_dtls where MODULE_SESSION_DTLS_ID="+resourseSessionId+")";
//		            boolean feedStatus = deleteOrUpdateByQuery(feedQuery);
//		           logger.debug("Feed Creation ? "+feedStatus);	
		            manageUpdate(feedQuery);
					}
				}
				else
				{
					//All resources are completed *********
					//Complete module based on moduleSessionId
					String queryForModuleSessionId="SELECT COURSE_SESSION_DTLS_ID FROM teacher_module_session_dtls where MODULE_SESSION_DTLS_ID="+resourseSessionId;
					int moduleSessionId=getCountQueryResult(queryForModuleSessionId);
					//Update module status = 1
					updateCourseModuleStatus(moduleSessionId, 1);
				}
			
		}	
		}else{
			//Not applicable
			updateCount=-1;
		}
	    
	    return updateCount;
	}


	@Override
	public List<Integer> getCoursePercentage(int userId, int schoolId,
			int classId, int hrmId) throws LmsDaoException {
		List<Integer> list = new ArrayList<Integer>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            StringBuffer sql = new StringBuffer("SELECT tcs.IS_COMPLETE FROM teacher_courses  tc, teacher_course_sessions tcs WHERE tc.TEACHER_ID='"+userId+"' and tcs.TEACHER_COURSE_ID=tc.TEACHER_COURSE_ID");
            if(schoolId>0)
            	sql.append(" AND tc.SCHOOL_ID = ").append(schoolId);
            if(classId>0)
            	sql.append(" AND tc.CLASS_ID = ").append(classId);
            if(hrmId>0)
            	sql.append(" AND tc.HRM_ID = ").append(hrmId);
            
           logger.debug("Generated query : "+sql);
            
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }

        } catch (SQLException se) {
           logger.error("getTeacherCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}



	@Override
	public List<Integer> getAssignmentPercentage(int userId, int schoolId,
			int classId, int hrmId) throws LmsDaoException {
		List<Integer> list = new ArrayList<Integer>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            StringBuffer sql = new StringBuffer("SELECT distinct artxn.IS_COMPLETED, artxn.STUDENT_ID, artxn.ASSIGNMENT_ID,tc.SCHOOL_ID FROM teacher_course_session_dtls tcsd INNER JOIN module_assignment_map mam ON mam.MODULE_ID=tcsd.MODULE_ID INNER JOIN assignment_resource_txn artxn ON artxn.ASSIGNMENT_ID=mam.ASSIGNMENT_ID INNER JOIN teacher_courses tc ON tc.TEACHER_ID=tcsd.TEACHER_ID INNER JOIN teacher_course_sessions tcs on tcs.COURSE_SESSION_ID=tc.TEACHER_COURSE_ID and tcsd.COURSE_SESSION_ID = tcs.COURSE_SESSION_ID WHERE tcsd.TEACHER_ID ="+userId);
            if(schoolId>0)
            	sql.append(" AND tc.SCHOOL_ID = ").append(schoolId);
            if(classId>0)
            	sql.append(" AND tc.CLASS_ID = ").append(classId);
            if(hrmId>0)
            	sql.append(" AND tc.HRM_ID = ").append(hrmId);
            
           logger.debug("Generated query : "+sql);
            
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }

        } catch (SQLException se) {
           logger.error("getTeacherCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId, int statusCode, int userId,
			String dueDate) throws LmsDaoException {
		int updateCount=0;

		if(statusCode==1)
		{
			//Activate assignments
			String queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='1',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where ENABLE_FL != '1' and ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("updateAssignmentStatus # status = "+updateCount);		
			
			if(updateCount>0)
			{
            //Create A feed | lms_feed_type = 6 |  Assignments for module $ of Course $ are open for submission.  <-   module,course 
            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM)VALUES(6,'open assignments',"+userId+","+courseId+",null,null,"+moduleId+","+hrmId+","+userId+",current_timestamp)";
//          boolean feedStatus = deleteOrUpdateByQuery(feedQuery);
//         logger.debug("Feed Creation ? "+feedStatus);	
            manageUpdate(feedQuery);
			}
		}else if(statusCode==0)
		{
			//Deactivate
			String queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='0',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("updateAssignmentStatus # status = "+updateCount);		

		}else{
			updateCount=-1;
		}		
		
		
	    return updateCount;
	}
	

	@Override
	public int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId,int assignmentId, int statusCode, int userId,
			String dueDate) throws LmsDaoException {
		int updateCount=0;

		if(statusCode==1)
		{
			//Activate assignments
			String queryUpdateAssignmentStatus= null;
			
			if(assignmentId>0)
			{
				queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='1', CANCEL_FL='2',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where ENABLE_FL != '0' and ASSIGNMENT_ID = "+assignmentId+" and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";	
			}else{
				queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='1',CANCEL_FL='2',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where CANCEL_FL!=2 and ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			}
			
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("updateAssignmentStatus 2 # status = "+updateCount);		
			
			if(updateCount>0)
			{
            //Create A feed | lms_feed_type = 6 |  Assignments for module $ of Course $ are open for submission.  <-   module,course 
            String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM)VALUES(6,'open assignments',"+userId+","+courseId+",null,null,"+moduleId+","+hrmId+","+userId+",current_timestamp)";

            manageUpdate(feedQuery);
			}
		}else if(statusCode==0)
		{
			//Deactivate
			String queryUpdateAssignmentStatus= null;
			
			if(assignmentId>0)
			{
				queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='0',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID = "+assignmentId+" and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";	
			}else{
				queryUpdateAssignmentStatus="update assignment_resource_txn set ENABLE_FL='0',LAST_USERID_CD="+userId+",DUE_ON='"+dueDate+"',LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			}
			
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("updateAssignmentStatus 2 # status = "+updateCount);		

		}else{
			updateCount=-1;
		}		
		
		
	    return updateCount;
	}
	

	@Override
	public int cancelAssignment(int schoolId, int classId, int hrmId,int courseId, int moduleId,int assignmentId, int statusCode, int userId) throws LmsDaoException {
		int updateCount=0;

		if(statusCode==1)
		{
			//Activate assignments
			String queryUpdateAssignmentStatus= null;
			
			if(assignmentId>0)
			{
				queryUpdateAssignmentStatus="update assignment_resource_txn set CANCEL_FL='1',LAST_USERID_CD="+userId+",LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID = "+assignmentId+" and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";	
			}else{
				queryUpdateAssignmentStatus="update assignment_resource_txn set CANCEL_FL='1',LAST_USERID_CD="+userId+",LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			}
			
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("cancelAssignment 2 # status = "+updateCount);		

		}else if(statusCode==0)
		{
			//Deactivate
			String queryUpdateAssignmentStatus= null;
			
			if(assignmentId>0)
			{
				queryUpdateAssignmentStatus="update assignment_resource_txn set CANCEL_FL='1',LAST_USERID_CD="+userId+",LAST_UPDT_TM=current_timestamp where ASSIGNMENT_ID = "+assignmentId+" and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";	
			}else{
				queryUpdateAssignmentStatus="update assignment_resource_txn set CANCEL_FL='1',LAST_USERID_CD="+userId+",LAST_UPDT_TM=current_timestamp where CANCEL_FL!=2 and ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID where tcm.TEACHER_ID="+userId+" and tcm.MODULE_ID="+moduleId+") and STUDENT_ID in (SELECT ucm.USER_ID FROM user_cls_map ucm inner join user_login ulogin on ucm.USER_ID=ulogin.USER_ID where ucm.SCHOOL_ID="+schoolId+" and ucm.HRM_ID="+hrmId+")";
			}
			
			updateCount = updateByQuery(queryUpdateAssignmentStatus);
			System.out.println("cancelAssignment 2 # status = "+updateCount);		

		}else{
			updateCount=-1;
		}		
		
		
	    return updateCount;
	}
	

	
	void manageUpdate(String Query)
	{
		try{
			
		 int feedId = getInsertedAutoId(Query);
		 
		 if(feedId>0)
		 {
			 FeedDaoImpl feedDao=new FeedDaoImpl();
			 FeedVO feed = feedDao.getFeedDetail(feedId);
		
			 //Send push notification
			 if(feed != null)
			 {
				 String usersListStr=feedDao.getFeedUsersStr(feed.getUserId()); //Get users list
				 if(!usersListStr.equals("0"))
				 {
		    	 NotificationServiceIface service = new NotificationServiceImpl();
		    	 service.pushNotifications(feed.getFeedRefName(), usersListStr);
				 }
			 }
			 
		 }
		 else
		 {
			logger.debug("Feed creation failed .. no push notification.");
		 }
		 
	
		}catch(Exception e){
			logger.error("Feed creation failed .. no push notification.");
		}
		
	}
	
	
	
}//End of class

