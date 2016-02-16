/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.application.rest.constants.SLMSRestConstants;
import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.ResourceTypeMstrVo;
import com.scolere.lms.domain.vo.cross.AssignmentQuestionVO;
import com.scolere.lms.persistance.dao.iface.ResourceTypeMstrDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

/**
 *
 * @author admin
 */
public class ResourceTypeMstrDaoImpl extends LmsDaoAbstract implements ResourceTypeMstrDao{
	
	Logger logger = LoggerFactory.getLogger(ResourceTypeMstrDaoImpl.class);
    
    public ResourceTypeMstrVo getResourceTypeMstrDetail(int id) throws LmsDaoException {
       logger.debug("Inside getResourceTypeMstrDetail(?) >>");
        //Create object to return
        ResourceTypeMstrVo userDtls = new ResourceTypeMstrVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM resource_typ_mstr where RESOURCE_TYP_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getResourceTypeId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setResourceTypeId(rs.getInt("RESOURCE_TYPE_ID"));
                userDtls.setResourceTypename(rs.getString("RESOURCE_TYPE_NAME"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getResourceTypeMstrDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateResourceTypeMstrDetail(ResourceTypeMstrVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getResourceTypeId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE resource_typ_mstr set RESOURCE_TYP_NAME=?\n"
                    + "    WHERE RESOURCE_TYPE_ID=?";
            stmt = conn.prepareStatement(sql);
           
            stmt.setString(1, vo.getResourceTypename());
             stmt.setInt(2, vo.getResourceTypeId());
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveResourceTypeMstrDetail(ResourceTypeMstrVo vo)  throws LmsDaoException{
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO resource_typ_mstr (RESOURSE_TYP_ID, RESOURCE_TYP_NAME)  VALUES(?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getResourceTypeId());
            stmt.setString(2, vo.getResourceTypename());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getResourceTypeMstrDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteResourceTypeMstrDetail(ResourceTypeMstrVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getResourceTypeId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM resource_typ_mstr WHERE RESOURCE_TYP_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getResourceTypeId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getResourceTypeMstrDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    
    public List< ResourceTypeMstrVo> getResourceTypeMstrVoList()  throws LmsDaoException{
        List<ResourceTypeMstrVo> distList = new ArrayList<ResourceTypeMstrVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM resource_typ_mstr ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                ResourceTypeMstrVo userDtls = new ResourceTypeMstrVo();

                userDtls.setResourceTypeId(rs.getInt("RESOURCE_TYP_ID"));
                userDtls.setResourceTypename(rs.getString("RESOURCE_TYP_NAME"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getResourceTypeMstrDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getResourceTypeMstrDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

        return distList;

    }

	 
	@Override
	public int uploadAssignment(int assignmentId,String resourceName,String resourceAuthor, String resourceDesc,int studentId, String descTxt, String url, String thumbUrl, String authorImgUrl) throws LmsDaoException {
			
		int status=0;
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO resourse_mstr(RESOURSE_ID, RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, DELETED_FL, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT_TM, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG)" +
            		"VALUES(?, ?, ?, 0, ?, 1, ?, '0', 0, '1', ?, ?,utc_timestamp, ?, ?, ?)";
            
            String res_id=getQueryConcatedResult("SELECT MAX(RESOURSE_ID)+1 FROM resourse_mstr");
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(res_id));
            stmt.setString(2,resourceName);
            stmt.setString(3, resourceAuthor);
            stmt.setString(4, resourceDesc);
            stmt.setString(5, resourceDesc);//Metadata
            stmt.setInt(6, studentId);
            stmt.setInt(7, studentId);//Last updated user
            stmt.setString(8, url);
            stmt.setString(9, authorImgUrl);
            stmt.setString(10, thumbUrl);

            int t=stmt.executeUpdate();
           logger.debug("No of inserted resources = "+t);
            if (res_id!=null && t>0) {
               logger.debug("Last inserted userId : "+res_id);
                //Add module-resource mapping
                String modResMapQuery="INSERT INTO module_resource_map(MODULE_ID, RESOURCE_ID)VALUES((SELECT MODULE_ID FROM module_assignment_map where ASSIGNMENT_ID="+assignmentId+"), "+res_id+")";
                boolean modResMapStatus = deleteOrUpdateByQuery(modResMapQuery);
               logger.debug("Create module-resource mapping ? "+modResMapStatus);     
                
                //Update assignment status
                String updateQuery = "UPDATE assignment_resource_txn SET UPLODED_RESOURCE_ID="+res_id+", UPLOADED_ON=current_date, IS_COMPLETED='2', LAST_USERID_CD="+studentId+", LAST_UPDT_TM=utc_timestamp , RESOURSE_NAME='"+resourceName+"', RESOURCE_AUTHOR='"+resourceAuthor+"', RESOURCE_DURATION=0, DESC_TXT='"+resourceDesc+"', RESOURCE_TYP_ID=1, METADATA='"+resourceDesc+"', RESOURCE_URL='"+url+"', AUTHOR_IMG='"+authorImgUrl+"', THUMB_IMG='"+thumbUrl+"', DELETED_FL='0' WHERE ASSIGNMENT_ID = "+assignmentId+" AND STUDENT_ID="+studentId;
                boolean updateStatus = deleteOrUpdateByQuery(updateQuery);
               logger.debug("Uploaded assignment status updated ? "+updateStatus);               
                
                //Create A feed | lms_feed_type = 2 | $ submitted an assignment $ for the module $ <- user,assignment,module
                String feedQuery = "INSERT INTO lms_feed_txn(FEED_TYPE_ID, REFRENCE_NM, USER_ID, COURSE_ID, RESOURCE_ID, ASSIGNMENT_ID, MODULE_ID, HRM_ID, LAST_USERID_CD, LAST_UPDT_TM) SELECT 2, 'Assignment submitted', ucm.USER_ID,null, null,"+assignmentId+", (SELECT MODULE_ID FROM module_assignment_map where ASSIGNMENT_ID="+assignmentId+") as moduleId, ucm.HRM_ID,ulogin.USER_ID,current_timestamp FROM user_login ulogin inner join user_cls_map ucm on ucm.USER_ID=ulogin.USER_ID where ulogin.USER_ID="+studentId;
//                boolean feedStatus = deleteOrUpdateByQuery(feedQuery);
//               logger.debug("Feed Creation ? "+feedStatus);  
                new TeacherDaoImpl().manageUpdate(feedQuery);
                
            }
            
            status=t;
        } catch (SQLException se) {
           logger.error("uploadAssignment  # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("uploadAssignment  # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, resultSet);
        }
	        
	  return status;
	}

	
	@Override
	public int submitAssignment(int assignmentResourceTxn, int submittedBy, int assignmentTypeId,
			List<AssignmentQuestionVO> list,int schoolId,int courseId) throws LmsDaoException {
		int status=0;
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {

            //Update assignment status
            String updateQuery = "update assignment_resource_txn set IS_COMPLETED='2',LAST_USERID_CD="+submittedBy+",LAST_UPDT_TM=current_timestamp where RESOURCE_TXN_ID="+assignmentResourceTxn;
            boolean updateStatus = deleteOrUpdateByQuery(updateQuery);
           logger.debug("Uploaded assignment staus updated ? "+updateStatus);
           //start saving assignment question answers
           if(updateStatus==true)
           {
            conn = getConnection();
            String sql = "INSERT INTO assignment_txn_detail(QUESTION_ID, ASSIGNMENT_RES_TXN_ID, LAST_USERID_CD, ANSWER_ID, ANSWER_TXT, LAST_UPDT_TM)VALUES(?,?,?,?,?, current_timestamp)";
            stmt = conn.prepareStatement(sql);
            for(AssignmentQuestionVO vo:list)
            {
	            stmt.setInt(1, vo.getQuestionId());
	            stmt.setInt(2, assignmentResourceTxn);
	            stmt.setInt(3,submittedBy);
            	stmt.setInt(4,vo.getOptionId());
            	stmt.setString(5, vo.getAnswerText());

            	stmt.addBatch();
            }//End of for
            
            int[] t=stmt.executeBatch();
            logger.debug("Batch update status = "+t);
            status=t.length;
           }
           //End saving assignment question answers
           
           //*****start-Auto grading of self assign courses
           boolean isSelfDriven = isExist("SELECT is_self_driven FROM course_mstr where is_self_driven='1' and COURSE_ID="+courseId);
           if(isSelfDriven==true)
           {
        	   selfReview(assignmentResourceTxn, submittedBy, assignmentTypeId, schoolId);
           }
           //******end-Auto grading of self assign courses
           
        } catch (SQLException se) {
           logger.error("submitAssignment  # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("submitAssignment  # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, resultSet);
        }
	        
	  return status;
	}

	
	/**
	 * Auto grading of self lead courses
	 *----------------------------------
	 * 	Only for MCQ & True/False
	 *	Automatic grade assign 
	 *	Assignment grade =Nearest grade value of[ (no Of correct Questions/Total questions)*100]
	 *	Update assignment review status=3
	 *-----------------------------------
	 * @param assignmentResourceTxn
	 * @param submittedBy
	 * @param assignmentTypeId
	 * @param schoolId
	 * @param courseId
	 */
	 private int selfReview(int assignmentResourceTxn, int submittedBy, int assignmentTypeId,int schoolId) throws LmsDaoException 
	 {
		 int count=0;
		 try{
			 
			 if(assignmentTypeId==3)//MCQ
			 {
				 String queryAutoGradeMCQ="INSERT INTO assignment_review_txn(RESOURCE_TXN_ID, GRADE_PARAM_ID, GRADE_VALUE_ID, LAST_UPDT_BY, LAST_UPDT_TM) select "+assignmentResourceTxn+",GRADE_PARAM_ID,GRADE_VALUE_ID,"+submittedBy+",current_timestamp from assignment_grade_values where SCHOOL_ID="+schoolId+" and GRADE_VALUE >= (100*(SELECT count(*) FROM assignment_resource_txn txn inner join assignment_quest_mstr aqmstr on txn.ASSIGNMENT_ID=aqmstr.ASSIGNMENT_ID inner join assignment_mcq_options mcq on mcq.QUESTION_ID=aqmstr.QUESTION_ID inner join assignment_txn_detail atdtl on atdtl.ASSIGNMENT_RES_TXN_ID=txn.RESOURCE_TXN_ID and aqmstr.QUESTION_ID=atdtl.QUESTION_ID and mcq.OPTION_ID=atdtl.ANSWER_ID where mcq.answer='1' and txn.RESOURCE_TXN_ID="+assignmentResourceTxn+")/(SELECT count(*) FROM assignment_resource_txn txn inner join assignment_quest_mstr aqmstr on txn.ASSIGNMENT_ID=aqmstr.ASSIGNMENT_ID where txn.RESOURCE_TXN_ID="+assignmentResourceTxn+")) order by GRADE_VALUE limit 1";
				 count = updateByQuery(queryAutoGradeMCQ);
			 }else if(assignmentTypeId==4)//True/False
			 {
				 String queryAutoGradeTrueFalse="INSERT INTO assignment_review_txn(RESOURCE_TXN_ID, GRADE_PARAM_ID, GRADE_VALUE_ID, LAST_UPDT_BY, LAST_UPDT_TM) select "+assignmentResourceTxn+",GRADE_PARAM_ID,GRADE_VALUE_ID,"+submittedBy+",current_timestamp from assignment_grade_values where SCHOOL_ID="+schoolId+" and GRADE_VALUE >= (100*(SELECT count(*) FROM assignment_resource_txn txn inner join assignment_quest_mstr aqmstr on txn.ASSIGNMENT_ID=aqmstr.ASSIGNMENT_ID and aqmstr.answer='1' inner join assignment_txn_detail atdtl on atdtl.ASSIGNMENT_RES_TXN_ID=txn.RESOURCE_TXN_ID and aqmstr.QUESTION_ID=atdtl.QUESTION_ID where trim(atdtl.ANSWER_TXT)='true' and txn.RESOURCE_TXN_ID="+assignmentResourceTxn+")/(SELECT count(*) FROM assignment_resource_txn txn inner join assignment_quest_mstr aqmstr on txn.ASSIGNMENT_ID=aqmstr.ASSIGNMENT_ID where txn.RESOURCE_TXN_ID="+assignmentResourceTxn+")) order by GRADE_VALUE limit 1";
				 count = updateByQuery(queryAutoGradeTrueFalse);
			 }
			 
		 }catch(Exception e){
			 throw new LmsDaoException(e.getMessage());
		 }
		 
		 return count;
	 }

	 
	 
}//End of class
