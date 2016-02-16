package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.TeacherCourseSessionVO;
import com.scolere.lms.domain.vo.cross.AssignmentQuestionVO;
import com.scolere.lms.domain.vo.cross.AssignmentVO;
import com.scolere.lms.domain.vo.cross.CommentVO;
import com.scolere.lms.domain.vo.cross.CourseVO;
import com.scolere.lms.domain.vo.cross.ResourseVO;
import com.scolere.lms.persistance.dao.iface.TeacherCourseSessionDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TeacherCourseSessionDaoImpl extends LmsDaoAbstract implements TeacherCourseSessionDao {

	
	Logger logger = LoggerFactory.getLogger(TeacherCourseSessionDaoImpl.class);

	@Override
	public int setRatingData(int userId, int assignmentResourceTxnId,
			List<CommonKeyValueVO> list) throws LmsDaoException {
		int count=0;
		
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO assignment_review_txn(RESOURCE_TXN_ID, GRADE_PARAM_ID, GRADE_VALUE_ID, LAST_UPDT_BY, LAST_UPDT_TM)VALUES(?, ?, ?, ?, current_timestamp)";
            stmt = conn.prepareStatement(sql);
            
            for(CommonKeyValueVO vo:list)
            {
            	stmt.setInt(1,assignmentResourceTxnId);
            	stmt.setInt(2,Integer.parseInt(vo.getItemCode()));
            	stmt.setInt(3,Integer.parseInt(vo.getItemName()));
            	stmt.setInt(4,userId);
            	
            	stmt.addBatch();
            }

            count = stmt.executeBatch().length;
            if(count>0)
            {
            	//Update assignment status
            	String updtAssignmentQuery="UPDATE assignment_resource_txn SET IS_COMPLETED='3' WHERE RESOURCE_TXN_ID="+assignmentResourceTxnId;
            	updateByQuery(updtAssignmentQuery);
            }
            
        } catch (SQLException se) {
           logger.error("setRatingData # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("setRatingData # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }		
		
       logger.debug("Inserted records ..."+count);
        
		return count;
	}
	
	
	@Override
	public List<CommonKeyValueVO> getRatingMasterData(int schoolId)
			throws LmsDaoException {
		List<CommonKeyValueVO> list = null;
		String qry1="SELECT GRADE_PARAM_ID,GRADE_PARAM FROM assignment_grade_params where SCHOOL_ID="+schoolId;
		list = getKeyValuePairList(qry1);
		return list;
	}

	@Override
	public List<CommonKeyValueVO> getRatingValuesMasterData(int schoolId)
			throws LmsDaoException {
		List<CommonKeyValueVO> list = null;
		String qry1="SELECT GRADE_VALUE_ID,GRADE_LABEL FROM assignment_grade_values where SCHOOL_ID="+schoolId;
		list = getKeyValuePairList(qry1);
		return list;
	}	
	
	
	@Override
	public List<CommonKeyValueVO> getRatingData(int assignmentResourceTxnId)
			throws LmsDaoException {
		List<CommonKeyValueVO> list = null;
		String qry1="SELECT concat(txn.GRADE_PARAM_ID,'-',agp.GRADE_PARAM),concat(txn.GRADE_VALUE_ID,'-',agv.GRADE_LABEL) FROM assignment_review_txn txn inner join assignment_grade_params agp on agp.GRADE_PARAM_ID=txn.GRADE_PARAM_ID inner join assignment_grade_values agv on agv.GRADE_VALUE_ID=txn.GRADE_VALUE_ID where RESOURCE_TXN_ID="+assignmentResourceTxnId;
		list = getKeyValuePairList(qry1);
		return list;
	}
	
	
    @Override
    public boolean saveResourceComment(int commentBy, int resourceId, String commentTxt) throws LmsDaoException {
        
        boolean status=false;
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO resource_comments(RESOURCE_ID, COMMENT_TXT, PARENT_COMMENT_ID, COMMENT_ON, ASSOCIATE_ID, COMMENTED_BY, LAST_USERID_CD, LAST_UPDT_TM)"
                    + " VALUES (?, ?, null, current_date, 0, ?, ?, utc_timestamp)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, resourceId);
            stmt.setString(2, commentTxt);
//            stmt.setInt(3, 0);
            stmt.setInt(3, commentBy);
            stmt.setInt(4, commentBy);

            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");
            status = true;
        } catch (SQLException se) {
           logger.error("saveResourceComment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveResourceComment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;
    }
    
    @Override
    public boolean saveCommentComment(int commentBy, int commentId, String commentTxt) throws LmsDaoException {
        
        boolean status=false;
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO resource_comments(RESOURCE_ID, COMMENT_TXT, PARENT_COMMENT_ID, COMMENT_ON, ASSOCIATE_ID, COMMENTED_BY, LAST_USERID_CD, LAST_UPDT_TM)"
                    + " VALUES (?, ?, ?, current_date, 0, ?, ?, utc_timestamp)";

            String resource_id = getQueryConcatedResult("SELECT RESOURCE_ID FROM resource_comments where RESOURCE_COMMENT_ID="+commentId);
           logger.debug("Resource id = "+resource_id);
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(resource_id));
            stmt.setString(2, commentTxt);
            stmt.setInt(3, commentId);
            stmt.setInt(4, commentBy);
            stmt.setInt(5, commentBy);

            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");
            status = true;
        } catch (SQLException se) {
           logger.error("saveCommentComment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveCommentComment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;
    }

    
    @Override
    public boolean saveResourceLike(int likeBy, int resourceId) throws LmsDaoException {
        boolean status=false;
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO resource_likes(RESOURCE_ID, PARENT_COMMENT_ID, LIKE_ON, ASSOCIATE_ID, LIKE_BY, LAST_USERID_CD, LAST_UPDT_TM)"
                                           + "VALUES(?, null, current_date, 0, ?, ?, utc_timestamp)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, resourceId);
            stmt.setInt(2, likeBy);
            stmt.setInt(3, likeBy);

            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");
            status = true;
        } catch (SQLException se) {
           logger.error("saveResourceLike # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveResourceLike # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;
    }

    
    
    @Override
    public boolean saveCommentLike(int likeBy, int commentId) throws LmsDaoException {
        
        boolean status=false;
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO resource_likes(RESOURCE_ID, PARENT_COMMENT_ID, LIKE_ON, ASSOCIATE_ID, LIKE_BY, LAST_USERID_CD, LAST_UPDT_TM)"
                                            + "VALUES(?, ?, current_date, 0, ?, ?, utc_timestamp)";

            String resource_id = getQueryConcatedResult("SELECT RESOURCE_ID FROM resource_comments where RESOURCE_COMMENT_ID="+commentId);
           logger.debug("Resource id = "+resource_id);
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(resource_id));
            stmt.setInt(2, commentId);
            stmt.setInt(3, likeBy);
            stmt.setInt(4, likeBy);

            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");
            status = true;
        } catch (SQLException se) {
           logger.error("saveCommentLike # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveCommentLike # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;
    }

      
    
    @Override
    public boolean updateTeacherCourseSession(TeacherCourseSessionVO vo)
            throws LmsDaoException {
       logger.debug("id =" + vo.getCourseSessionID());
        //System.out.println("id =" +1234);

        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE teacher_course_sessions set TEACHER_COURSE_ID=?, START_SESSION_TM=?, END_SESSION_TM=?, STATUS_TXT=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE COURSE_SESSION_ID=?";
            /*String sql = "UPDATE teacher_course_sessions set TEACHER_COURSE_ID=?, START_SESSION_TM=?, END_SESSION_TM=?, STATUS_TXT=?, LAST_USERID_CD=?\n"
             + "    WHERE COURSE_SESSION_ID=?";*/
            stmt = conn.prepareStatement(sql);


            stmt.setInt(1, vo.getTeacherCourseID());
            stmt.setString(2, vo.getStartSessionTm());
            stmt.setString(3, vo.getEndSessionTm());
            stmt.setString(4, vo.getStatusTxt());
            stmt.setString(5, vo.getLastUseridCd());
            stmt.setInt(6, vo.getCourseSessionID());
            // stmt.setString(7, vo.getLastUpdtTm());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("TeacherCourseSessionVO SQLException1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("TeacherCourseSessionVO Exception 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;

    }

    @Override
    public void saveTeacherCourseSession(TeacherCourseSessionVO vo)
            throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            /* String sql = "INSERT INTO teacher_course_sessions(COURSE_SESSION_ID, TEACHER_COURSE_ID, START_SESSION_TM," +
             " END_SESSION_TM, STATUS_TXT, LAST_USERID_CD)  VALUES(?, ?, ?, ?, ?, ?)";*/
            String sql = "INSERT INTO teacher_course_sessions(COURSE_SESSION_ID, TEACHER_COURSE_ID, START_SESSION_TM, END_SESSION_TM, "
                    + "STATUS_TXT, LAST_USERID_CD, LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, ?, utc_timestamp)";


            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getCourseSessionID());
            stmt.setInt(2, vo.getTeacherCourseID());
            stmt.setString(3, vo.getStartSessionTm());
            stmt.setString(4, vo.getEndSessionTm());
            stmt.setString(5, vo.getStatusTxt());
            stmt.setString(6, vo.getLastUseridCd());

            /*  stmt.setString(8, vo.getLastUpdtTm());*/

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("saveTeacherCourseSession # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveTeacherCourseSession # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");

    }

    @Override
    public boolean deleteTeacherCourseSession(TeacherCourseSessionVO vo)
            throws LmsDaoException {

       logger.debug("id =" + vo.getCourseSessionID());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM teacher_course_sessions WHERE COURSE_SESSION_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getCourseSessionID());
            stmt.executeUpdate();

           logger.debug("Deleted records into the ASSIGNMENT table...");

        } catch (SQLException se) {
           logger.error("TeacherCourseSessionVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("TeacherCourseSessionVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public TeacherCourseSessionVO getTeacherCourseSession(int id)
            throws LmsDaoException {

       logger.debug("Inside getTeacherCourseSession(?) >>");
        //Create object to return
        TeacherCourseSessionVO teacherCourseSessionVO = new TeacherCourseSessionVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM teacher_course_sessions where COURSE_SESSION_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacherCourseSessionVO.getCourseSessionID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object
                teacherCourseSessionVO.setCourseSessionID(rs.getInt("COURSE_SESSION_ID"));
                teacherCourseSessionVO.setTeacherCourseID(rs.getInt("TEACHER_COURSE_ID"));
                teacherCourseSessionVO.setStartSessionTm(rs.getString("START_SESSION_TM"));
                teacherCourseSessionVO.setEndSessionTm(rs.getString("END_SESSION_TM"));
                teacherCourseSessionVO.setStatusTxt(rs.getString("STATUS_TXT"));
                teacherCourseSessionVO.setLastUseridCd(rs.getString("LAST_USERID_CD"));
                teacherCourseSessionVO.setLastUpdateTm(rs.getString("LAST_UPDT_TM"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getTeacherCourseSession # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherCourseSession # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return teacherCourseSessionVO;
    }

    @Override
    public List<TeacherCourseSessionVO> getTeacherCourseSessionList()
            throws LmsDaoException {

        List< TeacherCourseSessionVO> distList = new ArrayList<TeacherCourseSessionVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM teacher_course_sessions ";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                TeacherCourseSessionVO teacherCourseSessionVO = new TeacherCourseSessionVO();

                teacherCourseSessionVO.setCourseSessionID(rs.getInt("COURSE_SESSION_ID"));
                teacherCourseSessionVO.setTeacherCourseID(rs.getInt("TEACHER_COURSE_ID"));
                teacherCourseSessionVO.setStartSessionTm(rs.getString("START_SESSION_TM"));
                teacherCourseSessionVO.setEndSessionTm(rs.getString("END_SESSION_TM"));
                teacherCourseSessionVO.setStatusTxt(rs.getString("STATUS_TXT"));
                teacherCourseSessionVO.setLastUseridCd(rs.getString("LAST_USERID_CD"));
                teacherCourseSessionVO.setLastUpdateTm(rs.getString("LAST_UPDT_TM"));


                //Add into list
                distList.add(teacherCourseSessionVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("teacherCourseSessionVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("teacherCourseSessionVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return distList;
    }

    @Override
    public List<CourseVO> getStudentCourses(String userName, String searchText) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            /**
             * Student Courses - > user_login + user_cls_map + hrm_course_map 
             * SELECT cmstr.COURSE_ID,cmstr.COURSE_NAME,tc_sess.START_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID FROM user_cls_map ucmap INNER JOIN hrm_course_map cc_map ON cc_map.HRM_ID=ucmap.HRM_ID INNER JOIN course_mstr cmstr ON cmstr.COURSE_ID=cc_map.COURSE_ID INNER JOIN teacher_courses tcourse ON tcourse.COURSE_ID= cc_map.COURSE_ID AND tcourse.CLASS_ID=ucmap.CLASS_ID INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID INNER JOIN user_login ulogin ON ulogin.USER_ID=ucmap.USER_ID where ulogin.USER_NM = ? AND cmstr.METADATA like ?
             */
            //Updated@26-10-2015 for delete_fl
            String sql = "SELECT cmstr.COURSE_ID,cmstr.COURSE_NAME,tc_sess.START_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID FROM user_cls_map ucmap INNER JOIN hrm_course_map cc_map ON cc_map.HRM_ID=ucmap.HRM_ID INNER JOIN course_mstr cmstr ON cmstr.COURSE_ID=cc_map.COURSE_ID and cmstr.DELETED_FL='0' INNER JOIN teacher_courses tcourse ON tcourse.COURSE_ID= cc_map.COURSE_ID AND tcourse.CLASS_ID=ucmap.CLASS_ID INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID INNER JOIN user_login ulogin ON ulogin.USER_ID=ucmap.USER_ID where ulogin.USER_NM = ? AND cmstr.METADATA like ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, "%"+searchText+"%");
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedStatus(rs.getString(4));
                vo.setCourseSessionId(rs.getInt(5));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


    @Override
    public List<CourseVO> getTeacherCourses(int teacherId)  throws LmsDaoException {
    	List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT tcs.COURSE_ID,tcs.COURSE_NAME,tcs.COURSE_ICON FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID where tc.TEACHER_ID=? group by  tcs.COURSE_ID,tcs.COURSE_NAME";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacherId);
                        
            rs = stmt.executeQuery();
            
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setCourseIcon(rs.getString(3));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getTeacherCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.debug("getTeacherCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

    return list;        
    }    
    

    @Override
    public List<CourseVO> getTeacherCourseOrganisations(int teacherId,int courseId)  throws LmsDaoException {
    	List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT tc.CLASS_ID,tc.SCHOOL_ID,tc.HRM_ID,tcs.IS_COMPLETE,smstr.SCHOOL_NAME,cmstr.CLASS_NAME,hmstr.HRM_NAME,tcs.COURSE_SESSION_ID,(SELECT count(*) FROM user_cls_map where SCHOOL_ID=tc.SCHOOL_ID and CLASS_ID=tc.CLASS_ID and HRM_ID=tc.HRM_ID) as student_count,hmstr.start_dt, hmstr.end_dt FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID left join school_mstr smstr on smstr.SCHOOL_ID=tc.SCHOOL_ID left join class_mstr cmstr on cmstr.CLASS_ID=tc.CLASS_ID left join homeroom_mstr hmstr on hmstr.HRM_ID= tc.HRM_ID where tc.TEACHER_ID=? and tc.COURSE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacherId);
            stmt.setInt(2, courseId);
                        
            rs = stmt.executeQuery();
            
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setClassId(rs.getInt(1));
                vo.setSchoolId(rs.getInt(2));
                vo.setHrmId(rs.getInt(3));
                vo.setCompletedStatus(rs.getString(4));
                vo.setSchoolName(rs.getString(5));
                vo.setClasseName(rs.getString(6));
                vo.setHrmName(rs.getString(7));
                vo.setCourseSessionId(rs.getInt(8));
                vo.setStudentCount(rs.getInt(9));
                vo.setSessionStartDate(rs.getString(10));
                vo.setSessionEndDate(rs.getString(11));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getTeacherCourseOrganisations # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherCourseOrganisations # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

    return list;        
    }    
    
    

    @Override
    public CourseVO getStudentCourseDetail(int courseID) throws LmsDaoException {
        CourseVO vo =null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            /**
             * Student Courses - > teacher_course_sessions
             * 
             * Query =SELECT tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess.START_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID,tc_sess.COURSE_ICON FROM teacher_course_sessions tc_sess where tc_sess.COURSE_ID = ? limit 1
             */
            
            String sql = "SELECT tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess.START_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID,tc_sess.COURSE_ICON FROM teacher_course_sessions tc_sess where tc_sess.COURSE_ID = ? limit 1";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseID);
                        
            rs = stmt.executeQuery();
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedStatus(rs.getString(4));
                vo.setCourseSessionId(rs.getInt(5));
                vo.setCourseIcon(rs.getString(6));
            }

        } catch (SQLException se) {
           logger.error("getStudentCourseDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCourseDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

    return vo;        
    }    
    
    
    @Override
    public List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId,int moduleId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            StringBuffer sql = new StringBuffer("SELECT distinct tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess.COURSE_AUTHOR,tc_sess.AUTHOR_IMG,tc_sess.START_SESSION_TM,tc_sess.END_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID,tcourse.SCHOOL_ID,smstr.SCHOOL_NAME,tcourse.CLASS_ID,clsmstr.CLASS_NAME,tcourse.HRM_ID,hmstr.HRM_NAME,hmstr.start_dt,hmstr.end_dt FROM teacher_courses tcourse INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tm_sess ON tm_sess.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN student_dtls teacher ON teacher.USER_ID=tcourse.TEACHER_ID INNER JOIN school_mstr smstr on smstr.SCHOOL_ID=tcourse.SCHOOL_ID INNER JOIN class_mstr clsmstr on clsmstr.CLASS_ID=tcourse.CLASS_ID INNER JOIN homeroom_mstr hmstr on hmstr.HRM_ID=tcourse.HRM_ID where teacher.USER_ID =").append(userId);
            if(courseId>0)
            	sql.append(" AND tcourse.COURSE_ID = ").append(courseId);
            if(schoolId>0)
            	sql.append(" AND tcourse.SCHOOL_ID = ").append(schoolId);
//Phase-3 change : Remove department check            
//            if(classId>0)
//            	sql.append(" AND tcourse.CLASS_ID = ").append(classId);
            if(hrmId>0)
            	sql.append(" AND tcourse.HRM_ID = ").append(hrmId);
            if(moduleId>0)
            	sql.append(" AND tm_sess.MODULE_ID = ").append(moduleId);
            
            
           logger.debug("Generated query : "+sql);
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setAuthorImg(rs.getString(4));
                vo.setStartedOn(rs.getString(5));
                vo.setCompletedOn(rs.getString(6));
                vo.setCompletedStatus(rs.getString(7));
                vo.setCourseSessionId(rs.getInt(8));   //use to get modules list
                vo.setSchoolId(rs.getInt(9));
                vo.setSchoolName(rs.getString(10));
                vo.setClassId(rs.getInt(11));
                vo.setClasseName(rs.getString(12));
                vo.setHrmId(rs.getInt(13));
                vo.setHrmName(rs.getString(14));
                vo.setSessionStartDate(rs.getString(15));
                vo.setSessionEndDate(rs.getString(16));
                
                list.add(vo);
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
    public List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

//            StringBuffer sql = new StringBuffer("SELECT tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess.COURSE_AUTHOR,tc_sess.AUTHOR_IMG,tc_sess.START_SESSION_TM,tc_sess.END_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID,tcourse.SCHOOL_ID,smstr.SCHOOL_NAME,tcourse.CLASS_ID,clsmstr.CLASS_NAME,tcourse.HRM_ID,hmstr.HRM_NAME,(SELECT count(*) FROM user_cls_map where SCHOOL_ID=tcourse.SCHOOL_ID and CLASS_ID=tcourse.CLASS_ID and HRM_ID=tcourse.HRM_ID) as student_count FROM teacher_courses tcourse INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID INNER JOIN student_dtls teacher ON teacher.EMAIL_ID=tcourse.TEACHER_ID INNER JOIN school_mstr smstr on smstr.SCHOOL_ID=tcourse.SCHOOL_ID and smstr.DELETED_FL='0' INNER JOIN class_mstr clsmstr on clsmstr.CLASS_ID=tcourse.CLASS_ID and clsmstr.DELETED_FL='0' INNER JOIN homeroom_mstr hmstr on hmstr.HRM_ID=tcourse.HRM_ID and hmstr.DELETED_FL='0' where teacher.USER_ID =").append(userId);
            StringBuffer sql = new StringBuffer("SELECT tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess.COURSE_AUTHOR,tc_sess.AUTHOR_IMG,tc_sess.START_SESSION_TM,tc_sess.END_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID,tcourse.SCHOOL_ID,smstr.SCHOOL_NAME,tcourse.CLASS_ID,clsmstr.CLASS_NAME,tcourse.HRM_ID,hmstr.HRM_NAME,(SELECT count(*) FROM user_cls_map where SCHOOL_ID=tcourse.SCHOOL_ID and CLASS_ID=tcourse.CLASS_ID and HRM_ID=tcourse.HRM_ID) as student_count,tc_sess.COURSE_ICON FROM teacher_courses tcourse INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID INNER JOIN student_dtls teacher ON teacher.USER_ID=tcourse.TEACHER_ID INNER JOIN school_mstr smstr on smstr.SCHOOL_ID=tcourse.SCHOOL_ID and smstr.DELETED_FL='0' INNER JOIN class_mstr clsmstr on clsmstr.CLASS_ID=tcourse.CLASS_ID and clsmstr.DELETED_FL='0' INNER JOIN homeroom_mstr hmstr on hmstr.HRM_ID=tcourse.HRM_ID and hmstr.DELETED_FL='0' where teacher.USER_ID =").append(userId);
            if(courseId>0)
            	sql.append(" AND tcourse.COURSE_ID = ").append(courseId);
            if(schoolId>0)
            	sql.append(" AND tcourse.SCHOOL_ID = ").append(schoolId);
            if(classId>0)
            	sql.append(" AND tcourse.CLASS_ID = ").append(classId);
            if(hrmId>0)
            	sql.append(" AND tcourse.HRM_ID = ").append(hrmId);
            
           logger.debug("Generated query : "+sql);
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setAuthorImg(rs.getString(4));
                vo.setStartedOn(rs.getString(5));
                vo.setCompletedOn(rs.getString(6));
                vo.setCompletedStatus(rs.getString(7));
                vo.setCourseSessionId(rs.getInt(8));   //use to get modules list
                vo.setSchoolId(rs.getInt(9));
                vo.setSchoolName(rs.getString(10));
                vo.setClassId(rs.getInt(11));
                vo.setClasseName(rs.getString(12));
                vo.setHrmId(rs.getInt(13));
                vo.setHrmName(rs.getString(14));
                vo.setStudentCount(rs.getInt(15));
                vo.setCourseIcon(rs.getString(16));
                list.add(vo);
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
    public List<CourseVO> getStudentCourses(int userId, String searchText) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            /**
             * Student Courses - > user_cls_map + hrm_course_map 
             * 
             * Query = SELECT cmstr.COURSE_ID,cmstr.COURSE_NAME,cmstr.COURSE_AUTHOR,cmstr.AUTHOR_IMG,tc_sess.START_SESSION_TM,tc_sess.END_SESSION_TM,tc_sess.IS_COMPLETE,tc_sess.COURSE_SESSION_ID FROM user_cls_map ucmap INNER JOIN hrm_course_map cc_map ON cc_map.HRM_ID=ucmap.HRM_ID INNER JOIN course_mstr cmstr ON cmstr.COURSE_ID=cc_map.COURSE_ID INNER JOIN teacher_courses tcourse ON tcourse.COURSE_ID= cc_map.COURSE_ID AND tcourse.CLASS_ID=ucmap.CLASS_ID INNER JOIN teacher_course_sessions tc_sess ON tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID where ucmap.USER_ID = ? AND cmstr.METADATA like ?
             */

            String sql = "SELECT distinct cmstr.COURSE_ID,cmstr.COURSE_NAME,cmstr.COURSE_AUTHOR,cmstr.AUTHOR_IMG,cmstr.START_SESSION_TM,cmstr.END_SESSION_TM,cmstr.IS_COMPLETE,cmstr.COURSE_SESSION_ID,hm.HRM_NAME, cmstr.DESC_TXT,cmstr.COURSE_ICON,(SELECT is_self_driven FROM course_mstr where COURSE_ID=cmstr.COURSE_ID)as isSelfDriven FROM user_cls_map ucmap INNER JOIN hrm_course_map cc_map ON cc_map.HRM_ID=ucmap.HRM_ID INNER JOIN homeroom_mstr hm on hm.HRM_ID=ucmap.HRM_ID INNER JOIN teacher_courses tcourse ON tcourse.COURSE_ID= cc_map.COURSE_ID AND tcourse.SCHOOL_ID=ucmap.SCHOOL_ID AND tcourse.HRM_ID=ucmap.HRM_ID INNER JOIN teacher_course_sessions cmstr ON cmstr.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID where ucmap.USER_ID = ? AND cmstr.METADATA like ?";
           logger.debug("query : "+sql);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, "%"+searchText+"%");
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setAuthorImg(rs.getString(4));
                vo.setStartedOn(rs.getString(5));
                vo.setCompletedOn(rs.getString(6));
                vo.setCompletedStatus(rs.getString(7));
                vo.setCourseSessionId(rs.getInt(8));
                vo.setHrmName(rs.getString(9));
                vo.setCourseDesc(rs.getString(10));
                vo.setCourseIcon(rs.getString(11));
                vo.setIsSelfDriven(rs.getString(12));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    


    
    @Override
    public CourseVO getStudentModuleDetail(int moduleId) throws LmsDaoException {
        CourseVO vo = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT modul.MODULE_ID,modul.MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,(SELECT COURSE_ID FROM course_module_map where MODULE_ID=modul.MODULE_ID limit 1) as course_id FROM module_mstr modul INNER JOIN teacher_course_session_dtls tcs_dtl ON modul.MODULE_ID=tcs_dtl.MODULE_ID and modul.DELETED_FL='0' INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tcs_dtl.MODULE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            
            rs = stmt.executeQuery();
           
            if(rs.next()) {
                vo = new CourseVO();
                vo.setModuleId(rs.getString(1));
                vo.setModuleName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedStatus(rs.getString(4));
                vo.setCompletedPercentStatus(String.valueOf(rs.getInt(5)*100));
                vo.setCourseId(rs.getString(6));
            }

        } catch (SQLException se) {
           logger.error("getStudentModuleDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentModuleDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return vo;
    }
    
    
    
    @Override
    public List<CourseVO> getStudentCoursesModules(int courseSessionId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

           // String sql = "SELECT modul.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID FROM module_mstr modul INNER JOIN teacher_course_session_dtls tcs_dtl ON modul.MODULE_ID=tcs_dtl.MODULE_ID and modul.DELETED_FL='0' INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";
            //DB_UPDT
            String sql = "SELECT tcs_dtl.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID FROM teacher_course_session_dtls tcs_dtl INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseSessionId);
            
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setModuleId(rs.getString(1));
                vo.setModuleName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedOn(rs.getString(4));
                vo.setCompletedStatus(rs.getString(5));
                vo.setCompletedPercentStatus(String.valueOf(Math.round(rs.getDouble(6)*100)));
                vo.setModuleSessionId(rs.getInt(7)); //Teacher module session id
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentCoursesModules # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCoursesModules # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    

    @Override
    public List<CourseVO> getTeacherCoursesModules(int courseSessionId,int schoolId,int classId,int homeRoomMstrId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

           // String sql = "SELECT modul.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID FROM module_mstr modul RIGHT JOIN teacher_course_session_dtls tcs_dtl ON modul.MODULE_ID=tcs_dtl.MODULE_ID INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";
            
            //Added modules assignment enable status
            //updated for delet_fl
            //String sql = "SELECT modul.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID,(SELECT astxn.ENABLE_FL FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID inner join assignment_resource_txn astxn on astxn.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where tcm.COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID limit 1) as enableStatus,modul.DESC_TXT FROM module_mstr modul RIGHT JOIN teacher_course_session_dtls tcs_dtl ON modul.MODULE_ID=tcs_dtl.MODULE_ID and modul.DELETED_FL='0' INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";
            //DB_UPDT
            /*String sql = "SELECT tcs_dtl.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID,(SELECT astxn.ENABLE_FL FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID inner join assignment_resource_txn astxn on astxn.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where tcm.COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID limit 1) as enableStatus,tcs_dtl.DESC_TXT FROM teacher_course_session_dtls tcs_dtl INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";*/
            
            String sql = "SELECT tcs_dtl.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,( SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/( SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent, tcs_dtl.COURSE_SESSION_DTLS_ID,(SELECT astxn.ENABLE_FL FROM teacher_course_session_dtls tcm inner join module_assignment_map mam on mam.MODULE_ID=tcm.MODULE_ID inner join assignment_resource_txn astxn on astxn.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where tcm.COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID and astxn.STUDENT_ID in (SELECT ul.USER_ID FROM user_cls_map ucm inner join user_login ul on ul.USER_ID=ucm.USER_ID where ucm.SCHOOL_ID=? and ucm.CLASS_ID=? and ucm.HRM_ID=?) limit 1) as enableStatus,tcs_dtl.DESC_TXT FROM teacher_course_session_dtls tcs_dtl INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, schoolId);
            stmt.setInt(2, classId);
            stmt.setInt(3, homeRoomMstrId);
            stmt.setInt(4, courseSessionId);
            
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setModuleId(rs.getString(1));
                vo.setModuleName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedOn(rs.getString(4));
                vo.setCompletedStatus(rs.getString(5));
                vo.setCompletedPercentStatus(String.valueOf(rs.getInt(6)*100));
                vo.setModuleSessionId(rs.getInt(7)); //teacher Module session id
                String enableStatus=(rs.getString(8)!=null && !rs.getString(8).isEmpty())?rs.getString(8):"0";
                //module assignment enable status
                if(enableStatus.equals("1"))
                	vo.setAssignmentEnableStatus("1");
                else
                	vo.setAssignmentEnableStatus("0");
                
                vo.setModuleDesc(rs.getString(9)); //module desc
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentCoursesModules # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCoursesModules # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


    @Override
    public List<CourseVO> getTeacherCoursesModules(int courseSessionId,int moduleId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            //deleted_fl applied
           // String sql = "SELECT modul.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID FROM module_mstr modul RIGHT JOIN teacher_course_session_dtls tcs_dtl ON modul.MODULE_ID=tcs_dtl.MODULE_ID and modul.DELETED_FL='0' INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ? AND modul.MODULE_ID=? ";
           //DB_UPDT 
            String sql = "SELECT tcs_dtl.MODULE_ID,MODULE_NAME,tcs_dtl.START_SESSION_TM,tcs_dtl.END_SESSION_TM,tcs_dtl.IS_COMPLETED,(SELECT count(*) FROM teacher_module_session_dtls where IS_COMPLETED='1' and COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID)/(SELECT count(*) FROM teacher_module_session_dtls where COURSE_SESSION_DTLS_ID=tcs_dtl.COURSE_SESSION_DTLS_ID) as completedPercent,tcs_dtl.COURSE_SESSION_DTLS_ID FROM teacher_course_session_dtls tcs_dtl INNER JOIN teacher_course_sessions tc_sess ON tc_sess.COURSE_SESSION_ID=tcs_dtl.COURSE_SESSION_ID WHERE tc_sess.COURSE_SESSION_ID = ? AND tcs_dtl.MODULE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseSessionId);
            stmt.setInt(2, moduleId);
            
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setModuleId(rs.getString(1));
                vo.setModuleName(rs.getString(2));
                vo.setStartedOn(rs.getString(3));
                vo.setCompletedOn(rs.getString(4));
                vo.setCompletedStatus(rs.getString(5));
                vo.setCompletedPercentStatus(String.valueOf(rs.getInt(6)*100));
                vo.setModuleSessionId(rs.getInt(7)); //teacher Module session id
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentCoursesModules(?,?) # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentCoursesModules(?,?) # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


    
    @Override
    public List<ResourseVO> getStudentResourcesWithRating(int moduleId,int userId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
 //           String sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount FROM resourse_mstr rc_mstr, module_resource_map mrm, teacher_course_session_dtls tc_sess_dtl Where rc_mstr.DELETED_FL='0' and rc_mstr.RESOURSE_ID = mrm.RESOURCE_ID and tc_sess_dtl.MODULE_ID=mrm.MODULE_ID and mrm.MODULE_ID=?";
            String sql = "SELECT distinct rc_mstr.CONTENT_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.CONTENT_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT RATING FROM resource_rating where USER_ID=? and RESOURCE_ID=rc_mstr.CONTENT_ID limit 1) as rating,(SELECT sum(RATING)/(select count(RATING) FROM resource_rating where RESOURCE_ID=rc_mstr.CONTENT_ID) FROM resource_rating where RESOURCE_ID=rc_mstr.CONTENT_ID limit 1) as avgRating FROM teacher_course_session_dtls tc_sess_dtl inner join teacher_module_session_dtls rc_mstr on tc_sess_dtl.COURSE_SESSION_DTLS_ID=rc_mstr.COURSE_SESSION_DTLS_ID Where tc_sess_dtl.MODULE_ID=? limit 1";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.setInt(2,moduleId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                
                vo.setCommentCounts(rs.getInt(10));
                vo.setRating(rs.getBigDecimal(11));
                vo.setAvgRating(rs.getBigDecimal(12));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResourcesWithRating (?)# " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResourcesWithRating # (?)" + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    @Override
    public List<ResourseVO> getStudentResources(int moduleId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
            /**
             * 
             */
            String sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount FROM resourse_mstr rc_mstr, module_resource_map mrm, teacher_course_session_dtls tc_sess_dtl Where rc_mstr.DELETED_FL='0' and rc_mstr.RESOURSE_ID = mrm.RESOURCE_ID and tc_sess_dtl.MODULE_ID=mrm.MODULE_ID and mrm.MODULE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,moduleId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                
                vo.setCommentCounts(rs.getInt(10));
                vo.setLikeCounts(rs.getInt(11));
                vo.setShareCounts(0);
                vo.setIsLiked(false); //Need to update further to make it dynamic
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResources (?)# " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResources # (?)" + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    @Override
    public List<ResourseVO> getStudentResourcesForRating(int userId, int courseId, int moduleId, String searchText,int moduleSessionId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
            /**
             * SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=2)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID LEFT JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=tc_mod_dtl.CONTENT_ID and rc_mstr.DELETED_FL='0' WHERE tc_sess_dtl.COURSE_SESSION_DTLS_ID=1  AND rc_mstr.METADATA like ?
             */
            String sql=null;
            if(moduleSessionId>0) //Teacher specific
            {
            	sql = "SELECT tc_mod_dtl.CONTENT_ID,tc_mod_dtl.RESOURSE_NAME,tc_mod_dtl.DESC_TXT,tc_mod_dtl.RESOURCE_AUTHOR,tc_mod_dtl.THUMB_IMG,tc_mod_dtl.RESOURCE_URL,tc_mod_dtl.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as commentCount, tc_sess_dtl.MODULE_NAME, tc_sess_dtl.DESC_TXT, tc_sess_dtl.COURSE_SESSION_DTLS_ID,tc_sess_dtl.IS_COMPLETED,(SELECT RATING FROM resource_rating where USER_ID=? and RESOURCE_ID=tc_mod_dtl.CONTENT_ID limit 1) as rating,(SELECT sum(RATING)/(select count(RATING) FROM resource_rating where RESOURCE_ID=tc_mod_dtl.CONTENT_ID) FROM resource_rating where RESOURCE_ID=tc_mod_dtl.CONTENT_ID limit 1) as avgRating,(SELECT count(txn.ENABLE_FL) FROM assignment_resource_txn txn inner join module_assignment_map mam on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where txn.ENABLE_FL !='1' and mam.MODULE_ID=tc_sess_dtl.MODULE_ID) as disableAssignmentsCount,(SELECT count(txn.CANCEL_FL) FROM assignment_resource_txn txn inner join module_assignment_map mam on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where txn.CANCEL_FL !='1' and mam.MODULE_ID=tc_sess_dtl.MODULE_ID) as cancellAssignmentsCount,tc_mod_dtl.RESOURCE_TYP_ID FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID WHERE tc_sess_dtl.COURSE_SESSION_DTLS_ID=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1,userId);
                stmt.setInt(2,moduleSessionId);
                //stmt.setString(3,"%"+searchText+"%");                	
            	
            }else{
            	//Student specific	
            	sql = "SELECT tc_mod_dtl.CONTENT_ID,tc_mod_dtl.RESOURSE_NAME,tc_mod_dtl.DESC_TXT,tc_mod_dtl.RESOURCE_AUTHOR,tc_mod_dtl.THUMB_IMG,tc_mod_dtl.RESOURCE_URL,tc_mod_dtl.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as commentCount,tc_sess_dtl.MODULE_NAME, tc_sess_dtl.DESC_TXT, tc_sess_dtl.COURSE_SESSION_DTLS_ID,tc_sess_dtl.IS_COMPLETED,(SELECT RATING FROM resource_rating where USER_ID=? and RESOURCE_ID=tc_mod_dtl.CONTENT_ID limit 1) as rating,(SELECT sum(RATING)/(select count(RATING) FROM resource_rating where RESOURCE_ID=tc_mod_dtl.CONTENT_ID) FROM resource_rating where RESOURCE_ID=tc_mod_dtl.CONTENT_ID limit 1) as avgRating,(SELECT count(txn.ENABLE_FL) FROM assignment_resource_txn txn inner join module_assignment_map mam on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where txn.ENABLE_FL !='1' and mam.MODULE_ID=tc_sess_dtl.MODULE_ID) as disableAssignmentsCount,(SELECT count(txn.CANCEL_FL) FROM assignment_resource_txn txn inner join module_assignment_map mam on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where txn.CANCEL_FL !='1' and mam.MODULE_ID=tc_sess_dtl.MODULE_ID) as cancellAssignmentsCount,tc_mod_dtl.RESOURCE_TYP_ID FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID INNER JOIN resourse_mstr rm on rm.RESOURSE_ID=tc_mod_dtl.CONTENT_ID INNER JOIN user_cls_map ucm ON ucm.SCHOOL_ID=tc.SCHOOL_ID AND ucm.HRM_ID=tc.HRM_ID WHERE ucm.USER_ID =? AND tc.COURSE_ID =? AND tc_sess_dtl.MODULE_ID=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1,userId);
                stmt.setInt(2,userId);
                stmt.setInt(3,courseId);
                stmt.setInt(4,moduleId);
                //stmt.setString(5,"%"+searchText+"%");            	
            }
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                
                vo.setCommentCounts(rs.getInt(10));
                vo.setModuleName(rs.getString(11));
                vo.setModuleDesc(rs.getString(12));
                vo.setModuleSessionId(rs.getInt(13));
                vo.setModuleStatus(rs.getString(14));
                vo.setRating(rs.getBigDecimal(15));
                vo.setAvgRating(rs.getBigDecimal(16));
                
                int assignmentsDisableCount=rs.getInt(17);
                if(assignmentsDisableCount>0)
                vo.setModuleAssignmentsEnableAllStatus("0");
                else
                vo.setModuleAssignmentsEnableAllStatus("1");	

                int assignmentsCancelCount=rs.getInt(18);
                if(assignmentsCancelCount>0)
                vo.setModuleAssignmentsCancelAllStatus("0");
                else
                vo.setModuleAssignmentsCancelAllStatus("1");	
                vo.setResourceTypeId(rs.getInt(19));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResourcesForRating # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResourcesForRating # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    
    
    @Override
    public List<ResourseVO> getStudentResources(int userId, int courseId, int moduleId, String searchText,int moduleSessionId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
            /**
             * SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=2)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID LEFT JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=tc_mod_dtl.CONTENT_ID and rc_mstr.DELETED_FL='0' WHERE tc_sess_dtl.COURSE_SESSION_DTLS_ID=1  AND rc_mstr.METADATA like ?
             */
            String sql=null;
            if(moduleSessionId>0) //Teacher specific
            {
               // sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID LEFT JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=tc_mod_dtl.CONTENT_ID and rc_mstr.DELETED_FL='0' WHERE tc_sess_dtl.COURSE_SESSION_DTLS_ID=?  AND rc_mstr.METADATA like ?";
               //DB_UPDT 
            	sql = "SELECT tc_mod_dtl.CONTENT_ID,tc_mod_dtl.RESOURSE_NAME,tc_mod_dtl.DESC_TXT,tc_mod_dtl.RESOURCE_AUTHOR,tc_mod_dtl.THUMB_IMG,tc_mod_dtl.RESOURCE_URL,tc_mod_dtl.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as resourceCount, tc_sess_dtl.MODULE_NAME, tc_sess_dtl.DESC_TXT, tc_sess_dtl.COURSE_SESSION_DTLS_ID,tc_sess_dtl.IS_COMPLETED, (SELECT count(*) FROM resource_likes where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID WHERE tc_sess_dtl.COURSE_SESSION_DTLS_ID=?  AND tc_mod_dtl.METADATA like ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1,userId);
                stmt.setInt(2,moduleSessionId);
                stmt.setString(3,"%"+searchText+"%");                	
            	
            }else{
            	//Student specific	
                //sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null) as resourceCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID INNER JOIN user_cls_map ucm ON tc.CLASS_ID = ucm.CLASS_ID AND ucm.SCHOOL_ID=tc.SCHOOL_ID AND ucm.HRM_ID=tc.HRM_ID LEFT JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=tc_mod_dtl.CONTENT_ID and rc_mstr.DELETED_FL='0' WHERE ucm.USER_ID =? AND tc.COURSE_ID =? AND tc_sess_dtl.MODULE_ID=? AND rc_mstr.METADATA like ?";
                //DB_UPDT
            	sql = "SELECT tc_mod_dtl.CONTENT_ID,tc_mod_dtl.RESOURSE_NAME,tc_mod_dtl.DESC_TXT,tc_mod_dtl.RESOURCE_AUTHOR,tc_mod_dtl.THUMB_IMG,tc_mod_dtl.RESOURCE_URL,tc_mod_dtl.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null) as resourceCount, tc_sess_dtl.MODULE_NAME, tc_sess_dtl.DESC_TXT, tc_sess_dtl.COURSE_SESSION_DTLS_ID,tc_sess_dtl.IS_COMPLETED, (SELECT count(*) FROM resource_likes where RESOURCE_ID=tc_mod_dtl.CONTENT_ID and PARENT_COMMENT_ID is null and LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls tc_mod_dtl ON tc_mod_dtl.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID INNER JOIN resourse_mstr rm on rm.RESOURSE_ID=tc_mod_dtl.CONTENT_ID and rm.CREATED_BY='4' INNER JOIN user_cls_map ucm ON tc.CLASS_ID = ucm.CLASS_ID AND ucm.SCHOOL_ID=tc.SCHOOL_ID AND ucm.HRM_ID=tc.HRM_ID WHERE ucm.USER_ID =? AND tc.COURSE_ID =? AND tc_sess_dtl.MODULE_ID=? AND tc_mod_dtl.METADATA like ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1,userId);
                stmt.setInt(2,userId);
                stmt.setInt(3,courseId);
                stmt.setInt(4,moduleId);
                stmt.setString(5,"%"+searchText+"%");            	
            }
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                
                vo.setCommentCounts(rs.getInt(10));
                vo.setLikeCounts(rs.getInt(11));
                vo.setModuleName(rs.getString(12));
                vo.setModuleDesc(rs.getString(13));
                vo.setModuleSessionId(rs.getInt(14));
                vo.setModuleStatus(rs.getString(15));
                vo.setShareCounts(0);
                if((rs.getInt(16)) > 0)
                {
                	vo.setIsLiked(true); //Need to update further to make it dynamic
                }
                else
                {
                	vo.setIsLiked(false);
                }
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    
    @Override
    public List<ResourseVO> getStudentResources(int courseId, int moduleId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
            /**
             * SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID),(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID) FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN user_cls_map ucm ON tc.CLASS_ID = ucm.CLASS_ID AND ucm.SCHOOL_ID=tc.SCHOOL_ID INNER JOIN hrm_course_map ccm on ccm.HRM_ID=ucm.HRM_ID AND tc.COURSE_ID=ccm.COURSE_ID INNER JOIN course_module_map cmm on cmm.COURSE_ID=ccm.COURSE_ID INNER JOIN module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID AND tc_sess_dtl.MODULE_ID=mrm.MODULE_ID INNER JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=mrm.RESOURCE_ID WHERE ccm.COURSE_ID = ? AND tc_sess_dtl.MODULE_ID=?
             */
          //  String sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.RESOURSE_ID),(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.RESOURSE_ID) FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN user_cls_map ucm ON tc.CLASS_ID = ucm.CLASS_ID AND ucm.SCHOOL_ID=tc.SCHOOL_ID INNER JOIN hrm_course_map ccm on ccm.HRM_ID=ucm.HRM_ID AND tc.COURSE_ID=ccm.COURSE_ID INNER JOIN course_module_map cmm on cmm.COURSE_ID=ccm.COURSE_ID INNER JOIN module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID AND tc_sess_dtl.MODULE_ID=mrm.MODULE_ID INNER JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=mrm.RESOURCE_ID and rc_mstr.DELETED_FL='0' WHERE ccm.COURSE_ID = ? AND tc_sess_dtl.MODULE_ID=?";
            //DB_UPDT
            String sql = "SELECT distinct rc_mstr.CONTENT_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,tc_sess_dtl.START_SESSION_TM,tc_sess_dtl.END_SESSION_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=rc_mstr.CONTENT_ID),(SELECT count(*) FROM resource_likes where RESOURCE_ID=rc_mstr.CONTENT_ID) FROM teacher_courses tc INNER JOIN teacher_course_sessions tc_sess ON tc.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID INNER JOIN teacher_course_session_dtls tc_sess_dtl ON tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID INNER JOIN teacher_module_session_dtls rc_mstr ON rc_mstr.COURSE_SESSION_DTLS_ID=tc_sess_dtl.COURSE_SESSION_DTLS_ID WHERE tc.COURSE_ID = ? AND tc_sess_dtl.MODULE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,courseId);
            stmt.setInt(2,moduleId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                
                vo.setCommentCounts(rs.getInt(10));
                vo.setLikeCounts(rs.getInt(11));
                vo.setShareCounts(0);
                vo.setIsLiked(false); //Need to update further to make it dynamic
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    
    @Override
    public List<ResourseVO> getStudentResourcesWeb(int userId, String courseId, String moduleId, String searchText) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            
            /**
             * SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG FROM user_cls_map ucm INNER JOIN hrm_course_map ccm on ccm.HRM_ID=ucm.HRM_ID INNER JOIN course_module_map cmm on cmm.COURSE_ID=ccm.COURSE_ID INNER JOIN module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID INNER JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=mrm.RESOURCE_ID WHERE ucm.USER_ID = ? AND cmm.COURSE_ID = ? AND cmm.MODULE_ID=? AND rc_mstr.METADATA like ?
             */
            String sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG FROM user_cls_map ucm INNER JOIN hrm_course_map ccm on ccm.HRM_ID=ucm.HRM_ID INNER JOIN course_module_map cmm on cmm.COURSE_ID=ccm.COURSE_ID INNER JOIN module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID INNER JOIN resourse_mstr rc_mstr on rc_mstr.RESOURSE_ID=mrm.RESOURCE_ID and rc_mstr.DELETED_FL='0' WHERE ucm.USER_ID = ? AND cmm.COURSE_ID = ? AND cmm.MODULE_ID=? AND rc_mstr.METADATA like ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.setString(2,courseId);
            stmt.setString(3,moduleId);
            stmt.setString(4,"%"+searchText+"%");
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentResourcesWeb # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentResourcesWeb # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    
    
    @Override
    public List<ResourseVO> getTeacherModuleResources(int moduleSessionId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

           // String sql = "SELECT rc_mstr.RESOURSE_ID,rc_mstr.RESOURSE_NAME,rc_mstr.DESC_TXT,rc_mstr.RESOURCE_AUTHOR,rc_mstr.THUMB_IMG,rc_mstr.RESOURCE_URL,rc_mstr.AUTHOR_IMG,msession.START_SESSION_TM,msession.END_SESSION_TM,msession.IS_COMPLETED,msession.MODULE_SESSION_DTLS_ID FROM resourse_mstr rc_mstr RIGHT JOIN teacher_module_session_dtls msession on msession.CONTENT_ID=rc_mstr.RESOURSE_ID and rc_mstr.DELETED_FL='0' where msession.COURSE_SESSION_DTLS_ID = ?";
           //DB_UPDT 
            String sql = "SELECT msession.CONTENT_ID,msession.RESOURSE_NAME,msession.DESC_TXT,msession.RESOURCE_AUTHOR,msession.THUMB_IMG,msession.RESOURCE_URL,msession.AUTHOR_IMG,msession.START_SESSION_TM,msession.END_SESSION_TM,msession.IS_COMPLETED,msession.MODULE_SESSION_DTLS_ID FROM  teacher_module_session_dtls msession INNER JOIN resourse_mstr rm on rm.RESOURSE_ID=msession.CONTENT_ID and rm.CREATED_BY='4' where msession.COURSE_SESSION_DTLS_ID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,moduleSessionId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));
                vo.setAuthorName(rs.getString(4));
                vo.setThumbUrl(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                vo.setStartedOn(rs.getString(8));
                vo.setCompletedOn(rs.getString(9));
                vo.setCompletedStatus(rs.getString(10));
                vo.setResourceSessionId(rs.getInt(11));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getTeacherModuleResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherModuleResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    @Override
    public List<CommentVO> getResourceChildCommentsListForRating(int userId,int commentId) throws LmsDaoException {
    	 List<CommentVO> list = new ArrayList<CommentVO>();

         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs =null;
         try {
             conn = getConnection();

             /**
              *SELECT RESOURCE_COMMENT_ID,COMMENT_TXT,PARENT_COMMENT_ID,COMMENTED_BY,LAST_UPDT_TM FROM resource_comments where RESOURCE _ID=? 
              */
             String sql="SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as commentCount FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where temp.PARENT_COMMENT_ID=? order by temp.LAST_UPDT_TM desc";             
             stmt = conn.prepareStatement(sql);
             stmt.setInt(1, commentId);

             rs = stmt.executeQuery();
             CommentVO vo = null;
             while (rs.next()) {
                 vo = new CommentVO();
                 vo.setCommentId(rs.getInt(1));
                 vo.setCommentTxt(rs.getString(2));
                 vo.setParentCommentId(rs.getInt(3));
                 vo.setCommentBy(rs.getString(4));
                 vo.setCommentById(rs.getInt(5));
                 vo.setCommentByImage(rs.getString(6));
                 vo.setCommentDate(rs.getString(7));
                 vo.setCommentCounts(rs.getInt(8));

                 list.add(vo);
             }

         } catch (SQLException se) {
            logger.error("getResourceChildCommentsListForRating # " + se);
             throw new LmsDaoException(se.getMessage());
         } catch (Exception e) {
            logger.error("getResourceChildCommentsListForRating # " + e);
             throw new LmsDaoException(e.getMessage());
         } finally {
             closeResources(conn, stmt, rs);
         }

         return list;
    }
    
    
    @Override
    public List<CommentVO> getResourceChildCommentsList(int userId,int commentId) throws LmsDaoException {
    	 List<CommentVO> list = new ArrayList<CommentVO>();

         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs =null;
         try {
             conn = getConnection();

             /**
              *SELECT RESOURCE_COMMENT_ID,COMMENT_TXT,PARENT_COMMENT_ID,COMMENTED_BY,LAST_UPDT_TM FROM resource_comments where RESOURCE _ID=? 
              */
             String sql="SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as likeCount,(SELECT COUNT(*) FROM resource_likes INNER JOIN user_login WHERE RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID AND LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where temp.PARENT_COMMENT_ID=? order by temp.LAST_UPDT_TM desc";             
             stmt = conn.prepareStatement(sql);
             stmt.setInt(1, userId);
             stmt.setInt(2, commentId);

             rs = stmt.executeQuery();
             CommentVO vo = null;
             while (rs.next()) {
                 vo = new CommentVO();
                 vo.setCommentId(rs.getInt(1));
                 vo.setCommentTxt(rs.getString(2));
                 vo.setParentCommentId(rs.getInt(3));
                 vo.setCommentBy(rs.getString(4));
                 vo.setCommentById(rs.getInt(5));
                 vo.setCommentByImage(rs.getString(6));
                 vo.setCommentDate(rs.getString(7));
                 
                 vo.setCommentCounts(rs.getInt(8));
                 vo.setLikeCounts(rs.getInt(9));
                 vo.setShareCounts(0);
                 if((rs.getInt(10))>0)
                 {
                	 vo.setIsLiked(true);
                 }
                 else
                 {
                 vo.setIsLiked(false); //Need to update later for dynamic update
                 }
                 list.add(vo);
             }

         } catch (SQLException se) {
            logger.error("getResourceComments # " + se);
             throw new LmsDaoException(se.getMessage());
         } catch (Exception e) {
            logger.error("getResourceComments # " + e);
             throw new LmsDaoException(e.getMessage());
         } finally {
             closeResources(conn, stmt, rs);
         }

         return list;
    }

    
    
    @Override
    public List<CommentVO> getResourceChildCommentsList(int commentId) throws LmsDaoException {
    	 List<CommentVO> list = new ArrayList<CommentVO>();

         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs =null;
         try {
             conn = getConnection();

             /**
              *SELECT RESOURCE_COMMENT_ID,COMMENT_TXT,PARENT_COMMENT_ID,COMMENTED_BY,LAST_UPDT_TM FROM resource_comments where RESOURCE _ID=? 
              */
             String sql="SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as commentCount,(SELECT count(*) FROM resource_likes where PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as likeCount FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where temp.PARENT_COMMENT_ID=? order by temp.LAST_UPDT_TM desc";
             stmt = conn.prepareStatement(sql);
             stmt.setInt(1, commentId);

             rs = stmt.executeQuery();
             CommentVO vo = null;
             while (rs.next()) {
                 vo = new CommentVO();
                 vo.setCommentId(rs.getInt(1));
                 vo.setCommentTxt(rs.getString(2));
                 vo.setParentCommentId(rs.getInt(3));
                 vo.setCommentBy(rs.getString(4));
                 vo.setCommentById(rs.getInt(5));
                 vo.setCommentByImage(rs.getString(6));
                 vo.setCommentDate(rs.getString(7));
                 
                 vo.setCommentCounts(rs.getInt(8));
                 vo.setLikeCounts(rs.getInt(9));
                 vo.setShareCounts(0);
                 vo.setIsLiked(false); //Need to update later for dynamic update
                 
                 list.add(vo);
             }

         } catch (SQLException se) {
            logger.error("getResourceComments # " + se);
             throw new LmsDaoException(se.getMessage());
         } catch (Exception e) {
            logger.error("getResourceComments # " + e);
             throw new LmsDaoException(e.getMessage());
         } finally {
             closeResources(conn, stmt, rs);
         }

         return list;
    }

    
    @Override
    public List<CommentVO> getResourceCommentsForRating(int userId,int resourceId) throws LmsDaoException {
        List<CommentVO> list = new ArrayList<CommentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             *RESOURCE_COMMENT_ID     COMMENT_TXT     PARENT_COMMENT_ID     USERNAME      USER_ID     PROFILE_IMG             LAST_UPDT_TM          commentCount     rating   avgRating     
             */
            String sql = "SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID =temp.RESOURCE_COMMENT_ID) as commentCount FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where RESOURCE_ID=? and PARENT_COMMENT_ID is null order by temp.LAST_UPDT_TM desc";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, resourceId);

            rs = stmt.executeQuery();
            CommentVO vo = null;
            while (rs.next()) {
                vo = new CommentVO();
                vo.setCommentId(rs.getInt(1));
                vo.setCommentTxt(rs.getString(2));
                vo.setParentCommentId(rs.getInt(3));
                vo.setCommentBy(rs.getString(4));
                vo.setCommentById(rs.getInt(5));
                vo.setCommentByImage(rs.getString(6));
                vo.setCommentDate(rs.getString(7));
                vo.setCommentCounts(rs.getInt(8));

                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getResourceCommentsForRating # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getResourceCommentsForRating # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    
    
    @Override
    public List<CommentVO> getResourceComments(int userId,int resourceId) throws LmsDaoException {
        List<CommentVO> list = new ArrayList<CommentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             *RESOURCE_COMMENT_ID     COMMENT_TXT     PARENT_COMMENT_ID     USERNAME      USER_ID     PROFILE_IMG             LAST_UPDT_TM          commentCount     likeCount     
             */
            String sql = "SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID =temp.RESOURCE_COMMENT_ID) as commentCount,(SELECT count(*) FROM resource_likes where RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID =temp.RESOURCE_COMMENT_ID) as likeCount,(SELECT COUNT(*) FROM resource_likes INNER JOIN user_login WHERE RESOURCE_ID=temp.RESOURCE_ID and PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID AND LIKE_BY=(SELECT USER_NM FROM user_login where USER_ID=?)) AS isliked  FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where RESOURCE_ID=? and PARENT_COMMENT_ID is null order by temp.LAST_UPDT_TM desc";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, resourceId);

            rs = stmt.executeQuery();
            CommentVO vo = null;
            while (rs.next()) {
                vo = new CommentVO();
                vo.setCommentId(rs.getInt(1));
                vo.setCommentTxt(rs.getString(2));
                vo.setParentCommentId(rs.getInt(3));
                vo.setCommentBy(rs.getString(4));
                vo.setCommentById(rs.getInt(5));
                vo.setCommentByImage(rs.getString(6));
                vo.setCommentDate(rs.getString(7));
                
                vo.setCommentCounts(rs.getInt(8));
                vo.setLikeCounts(rs.getInt(9));
                vo.setShareCounts(0);
                if((rs.getInt(10))>0)
                {
                vo.setIsLiked(true); //Need to update later for dynamic update
                }
                else
                {
                	vo.setIsLiked(false);
                }
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getResourceComments # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getResourceComments # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    

    @Override
    public List<CommentVO> getResourceComments(int resourceId) throws LmsDaoException {
        List<CommentVO> list = new ArrayList<CommentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             *RESOURCE_COMMENT_ID     COMMENT_TXT     PARENT_COMMENT_ID     USERNAME      USER_ID     PROFILE_IMG             LAST_UPDT_TM          commentCount     likeCount     
             */
            String sql = "SELECT temp.RESOURCE_COMMENT_ID,temp.COMMENT_TXT,temp.PARENT_COMMENT_ID,CONCAT(stdl.FNAME,' ',stdl.LNAME)AS USERNAME,stdl.USER_ID,stdl.PROFILE_IMG,temp.LAST_UPDT_TM,(SELECT count(*) FROM resource_comments where PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as commentCount,(SELECT count(*) FROM resource_likes where PARENT_COMMENT_ID=temp.RESOURCE_COMMENT_ID) as likeCount FROM resource_comments temp INNER JOIN student_dtls stdl  ON temp.COMMENTED_BY=stdl.USER_ID where RESOURCE_ID=? and PARENT_COMMENT_ID is null order by temp.LAST_UPDT_TM desc";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, resourceId);

            rs = stmt.executeQuery();
            CommentVO vo = null;
            while (rs.next()) {
                vo = new CommentVO();
                vo.setCommentId(rs.getInt(1));
                vo.setCommentTxt(rs.getString(2));
                vo.setParentCommentId(rs.getInt(3));
                vo.setCommentBy(rs.getString(4));
                vo.setCommentById(rs.getInt(5));
                vo.setCommentByImage(rs.getString(6));
                vo.setCommentDate(rs.getString(7));
                
                vo.setCommentCounts(rs.getInt(8));
                vo.setLikeCounts(rs.getInt(9));
                vo.setShareCounts(0);
                vo.setIsLiked(false); //Need to update later for dynamic update
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getResourceComments # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getResourceComments # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    @Override
    public List<ResourseVO> getRelatedResources(int resourceId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
        	String metadata=getQueryConcatedResult("SELECT METADATA FROM resourse_mstr where RESOURSE_ID="+resourceId);
            /**
             * SELECT RESOURSE_ID, RESOURSE_NAME, RESOURCE_AUTHOR, DESC_TXT, LAST_UPDT_TM FROM resourse_mstr where RESOURSE_ID=?
             * 
             */
            StringBuffer sql = new StringBuffer("SELECT RESOURSE_ID, RESOURSE_NAME, RESOURCE_AUTHOR, DESC_TXT, LAST_UPDT_TM,RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, CREATED_BY,RESOURCE_TYP_ID FROM resourse_mstr where RESOURSE_ID !=").append(resourceId).append(" and (METADATA like '%").append(metadata).append("%' ");
            String[] temp = metadata.split("\\s+");
            for(String s:temp)
            {
            	sql.append("OR METADATA like '%").append(s).append("%'");
            }
            sql.append(")");
            
           logger.debug("Generated related resource query - "+sql);
            
            conn = getConnection();
            stmt = conn.prepareStatement(sql.toString());
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setResourceDesc(rs.getString(4));
                vo.setUploadedDate(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                vo.setThumbUrl(rs.getString(8));
                vo.setUploadedBy(rs.getString(9));
                vo.setResourceTypeId(rs.getInt(10));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getRelatedResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getRelatedResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    @Override
    public List<ResourseVO> getRelatedResources(int resourceId,String metadata) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            /**
             * SELECT RESOURSE_ID, RESOURSE_NAME, RESOURCE_AUTHOR, DESC_TXT, LAST_UPDT_TM FROM resourse_mstr where RESOURSE_ID=?
             * 
             */
            StringBuffer sql = new StringBuffer("SELECT RESOURSE_ID, RESOURSE_NAME, RESOURCE_AUTHOR, DESC_TXT, LAST_UPDT_TM,RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, CREATED_BY FROM resourse_mstr where RESOURSE_ID !=").append(resourceId).append(" and (METADATA like '%").append(metadata).append("%' ");
            String[] temp = metadata.split("\\s+");
            for(String s:temp)
            {
            	sql.append("OR METADATA like '%").append(s).append("%'");
            }
            sql.append(")");
            
           logger.debug("Generated query - "+sql);
            
            conn = getConnection();
            stmt = conn.prepareStatement(sql.toString());
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setResourceDesc(rs.getString(4));
                vo.setUploadedDate(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setAuthorImg(rs.getString(7));
                vo.setThumbUrl(rs.getString(8));
                vo.setUploadedBy(rs.getString(9));

                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getRelatedResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getRelatedResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    @Override
    public List<AssignmentVO> getStudentAssignments(int courseId,int moduleId,int userId) throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID     ASSIGNMENT_NAME     IS_COMPLETED     CONCAT(ulogin.FNAME,' ',ulogin.LNAME)     TIMESTAMP(txn.UPLOADED_ON)     TIMESTAMP(txn.DUE_ON)     assignmentTyp     ENABLE_FL     CANCEL_FL    
             */
            
            String sql =null;
            
            if(courseId>0)
            {
            //Teacher specific	
                sql = "SELECT txn.ASSIGNMENT_ID,txn.ASSIGNMENT_NAME,'' as IS_COMPLETED,'' as nam,'' as uploaded_on,TIMESTAMP(txn.DUE_ON),(SELECT atm.ASSIGNMENT_TYP_NM FROM assignment asm inner join assignment_typ_mstr atm on asm.ASSIGNMENT_TYP_ID=atm.ASSIGNMENT_TYP_ID where asm.ASSIGNMENT_ID=txn.ASSIGNMENT_ID) as assignmentTyp,txn.ENABLE_FL,txn.CANCEL_FL,txn.ASSIGNMENT_DESC_TXT, cc.COURSE_NAME FROM assignment_resource_txn txn RIGHT JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=txn.ASSIGNMENT_ID INNER JOIN course_module_map mm ON mm.MODULE_ID=mod_assignment.MODULE_ID INNER JOIN course_mstr cc ON cc.COURSE_ID=mm.COURSE_ID where mod_assignment.MODULE_ID = ? and txn.STUDENT_ID !=? group by txn.ASSIGNMENT_ID";
            	
            }else{
            //Student specific
            	sql = "SELECT DISTINCT txn.ASSIGNMENT_ID,txn.ASSIGNMENT_NAME,txn.IS_COMPLETED,CONCAT(ulogin.FNAME,' ',ulogin.LNAME),TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON),(SELECT atm.ASSIGNMENT_TYP_NM FROM assignment asm inner join assignment_typ_mstr atm on asm.ASSIGNMENT_TYP_ID=atm.ASSIGNMENT_TYP_ID where asm.ASSIGNMENT_ID=txn.ASSIGNMENT_ID) as assignmentTyp,txn.ENABLE_FL,txn.CANCEL_FL,txn.ASSIGNMENT_DESC_TXT,'' as courseName FROM assignment_resource_txn txn INNER JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=txn.ASSIGNMENT_ID INNER JOIN student_dtls ulogin ON ulogin.USER_ID=txn.STUDENT_ID where txn.ENABLE_FL='1' and txn.CANCEL_FL!='1' and mod_assignment.MODULE_ID = ? and ulogin.USER_ID=?";
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            stmt.setInt(2, userId);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentStatus(rs.getString(3));
                vo.setAssignmentSubmittedBy(rs.getString(4));
                vo.setAssignmentSubmittedDate(rs.getString(5));
                vo.setAssignmentDueDate(rs.getString(6));
                vo.setAssignmentType(rs.getString(7));
                vo.setEnableStatus(rs.getString(8));
                vo.setCancelStatus(rs.getString(9));
                vo.setAssignmentDesc(rs.getString(10));
                vo.setCourseName(rs.getString(11));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignments (?,?,?)# " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignments (?,?,?)# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    

    @Override
    public List<AssignmentVO> getStudentAssignments(int userId,String searchText) throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE |COURSE_ID | COURSE_NAME  |   MODULE_ID  |  MODULE_NAME    
             */
            
            String sql = "SELECT DISTINCT txn.ASSIGNMENT_ID, txn.RESOURCE_TXN_ID, txn.ASSIGNMENT_NAME,txn.IS_COMPLETED,txn.STUDENT_ID,TIMESTAMP(txn.UPLOADED_ON),tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tc_sess_dtl.MODULE_ID,tc_sess_dtl.MODULE_NAME,txn.ASSIGNMENT_DESC_TXT,TIMESTAMP(txn.DUE_ON) FROM teacher_courses tcourse inner join teacher_course_sessions tc_sess on tc_sess.TEACHER_COURSE_ID=tcourse.TEACHER_COURSE_ID inner join teacher_course_session_dtls tc_sess_dtl on tc_sess_dtl.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID inner join module_assignment_map mam on tc_sess_dtl.MODULE_ID=mam.MODULE_ID inner join assignment_resource_txn txn on txn.ASSIGNMENT_ID=mam.ASSIGNMENT_ID inner join user_login ulogin on ulogin.USER_ID=txn.STUDENT_ID inner join user_cls_map ucm on ucm.SCHOOL_ID=tcourse.SCHOOL_ID and ucm.CLASS_ID=tcourse.CLASS_ID and ucm.HRM_ID=tcourse.HRM_ID where txn.ENABLE_FL='1' and ulogin.USER_ID=? AND txn.ASSIGNMENT_DESC_TXT like ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, "%"+searchText+"%");
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentResourceTxnId(rs.getInt(2));
                vo.setAssignmentName(rs.getString(3));
                vo.setAssignmentStatus(rs.getString(4));
                vo.setAssignmentSubmittedBy(rs.getString(5));
                vo.setAssignmentSubmittedDate(rs.getString(6));
                vo.setCourseId(rs.getInt(7));
                vo.setCourseName(rs.getString(8));
                vo.setModuleId(rs.getInt(9));
                vo.setModuleName(rs.getString(10));
                vo.setAssignmentDesc(rs.getString(11));
                vo.setAssignmentDueDate(rs.getString(12));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignments # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignments # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
	@Override
	public List<AssignmentVO> getTeacherAssignments(int schoolId, int classId,
			int hrmId, int courseId, int moduleId,int status ,int userId, String searchText)
			throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            StringBuffer sql = new StringBuffer("SELECT DISTINCT txn.ASSIGNMENT_ID,txn.ASSIGNMENT_NAME,txn.IS_COMPLETED,sdtls.USER_ID,concat(sdtls.FNAME,' ',sdtls.LNAME),TIMESTAMP(txn.UPLOADED_ON),tc_sess.COURSE_ID,tc_sess.COURSE_NAME,tcsd.MODULE_ID,tcsd.MODULE_NAME,txn.ASSIGNMENT_DESC_TXT,TIMESTAMP(txn.DUE_ON),txn.RESOURCE_TXN_ID,tcourse.SCHOOL_ID FROM teacher_courses tcourse inner join teacher_course_sessions tc_sess on tcourse.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcsd.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID inner join module_assignment_map mam on tcsd.MODULE_ID=mam.MODULE_ID inner join assignment_resource_txn txn on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID AND txn.ENABLE_FL = '1' inner join student_dtls sdtls on sdtls.USER_ID=txn.STUDENT_ID inner join user_cls_map ucm on ucm.USER_ID=sdtls.USER_ID AND ucm.SCHOOL_ID=tcourse.SCHOOL_ID AND ucm.CLASS_ID=tcourse.CLASS_ID AND ucm.HRM_ID=tcourse.HRM_ID where tcourse.TEACHER_ID=").append(userId).append(" AND txn.ASSIGNMENT_DESC_TXT like '%").append(searchText).append("%'");
            
            if(status>0)
            	sql.append(" and txn.IS_COMPLETED = '").append(status).append("'");
            if(schoolId>0)
            	sql.append(" and tcourse.SCHOOL_ID=").append(schoolId);
            if(classId>0)
            	sql.append(" and tcourse.CLASS_ID=").append(classId);
            if(hrmId>0)
            	sql.append(" and tcourse.HRM_ID=").append(hrmId);
            if(courseId>0)
            	sql.append(" and tcourse.COURSE_ID=").append(courseId);
            if(moduleId>0)
            	sql.append(" and tcsd.MODULE_ID=").append(moduleId);
            
           logger.debug("query - "+sql);
            stmt = conn.prepareStatement(sql.toString());
             
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentStatus(rs.getString(3));
                vo.setAssignmentSubmittedById(rs.getInt(4));
                vo.setAssignmentSubmittedBy(rs.getString(5));
                vo.setAssignmentSubmittedDate(rs.getString(6));
                vo.setCourseId(rs.getInt(7));
                vo.setCourseName(rs.getString(8));
                vo.setModuleId(rs.getInt(9));
                vo.setModuleName(rs.getString(10));
                vo.setAssignmentDesc(rs.getString(11));
                vo.setAssignmentDueDate(rs.getString(12));
                vo.setAssignmentResourceTxnId(rs.getInt(13));
                vo.setSchoolId(rs.getInt(14));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getTeacherAssignments # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getTeacherAssignments # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    

    @Override
    public List<AssignmentVO> getStudentAssignments(int userId) throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            String sql = "SELECT DISTINCT asgnmnt.ASSIGNMENT_ID,asgnmnt.ASSIGNMENT_NAME,txn.IS_COMPLETED,txn.STUDENT_ID,TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON) FROM assignment_resource_txn txn INNER JOIN assignment asgnmnt ON txn.ASSIGNMENT_ID = asgnmnt.ASSIGNMENT_ID and asgnmnt.DELETED_FL='0' where txn.STUDENT_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentStatus(rs.getString(3));
                vo.setAssignmentSubmittedBy(rs.getString(4));
                vo.setAssignmentSubmittedDate(rs.getString(5));
                vo.setAssignmentDueDate(rs.getString(6));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignments # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignments # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


    
    @Override
    public AssignmentVO getAssignmentDetail(int userId,int assignmentId) throws LmsDaoException {
    	AssignmentVO vo = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        
        try {
            conn = getConnection();
            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE | ASSIGNNMENT_TYPE
             */

            String sql = "SELECT DISTINCT txn.ASSIGNMENT_ID, txn.RESOURCE_TXN_ID, asignment.ASSIGNMENT_NAME,txn.IS_COMPLETED,txn.STUDENT_ID,TIMESTAMP(txn.UPLOADED_ON),cmstr.COURSE_ID,cmstr.COURSE_NAME,mmstr.MODULE_ID,mmstr.MODULE_NAME,asignment.DESC_TXT,TIMESTAMP(txn.DUE_ON),asignment.ASSIGNMENT_TYP_ID,txn.UPLODED_RESOURCE_ID,txn.RESOURSE_NAME,txn.DESC_TXT,txn.RESOURCE_URL,(SELECT ASSIGNMENT_TYP_NM FROM assignment_typ_mstr where ASSIGNMENT_TYP_ID=asignment.ASSIGNMENT_TYP_ID) as assignmenType FROM assignment_resource_txn txn inner join module_assignment_map mam on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID inner join course_module_map cmm on cmm.MODULE_ID=mam.MODULE_ID inner join user_login ulogin on ulogin.USER_ID=txn.STUDENT_ID inner join course_mstr cmstr on cmstr.COURSE_ID=cmm.COURSE_ID and cmstr.DELETED_FL='0' inner join module_mstr mmstr on mmstr.MODULE_ID=cmm.MODULE_ID and mmstr.DELETED_FL='0' inner join assignment asignment on asignment.ASSIGNMENT_ID=txn.ASSIGNMENT_ID and asignment.DELETED_FL='0' where ulogin.USER_ID=? AND txn.ASSIGNMENT_ID =?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, assignmentId);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentResourceTxnId(rs.getInt(2));
                vo.setAssignmentName(rs.getString(3));
                vo.setAssignmentStatus(rs.getString(4));
                vo.setAssignmentSubmittedBy(rs.getString(5));
                vo.setAssignmentSubmittedDate(rs.getString(6));
                vo.setCourseId(rs.getInt(7));
                vo.setCourseName(rs.getString(8));
                vo.setModuleId(rs.getInt(9));
                vo.setModuleName(rs.getString(10));
                vo.setAssignmentDesc(rs.getString(11));
                vo.setAssignmentDueDate(rs.getString(12));
                vo.setAssignmentTypeId(rs.getInt(13));
                
                vo.setUploadedResourceId(rs.getInt(14));
                vo.setUploadedResourceName(rs.getString(15));
                vo.setUploadedResourceDesc(rs.getString(16));
                vo.setUploadedResourceUrl(rs.getString(17));
                vo.setAssignmentType(rs.getString(18));
            }

        } catch (SQLException se) {
           logger.error("getAssignmentDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return vo;
    }

    

    @Override
    public List<AssignmentVO> getStudentAssignmentsByModuleId(int moduleId) throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            String sql = "SELECT DISTINCT asgnmnt.ASSIGNMENT_ID,asgnmnt.ASSIGNMENT_NAME,txn.IS_COMPLETED,txn.RESOURCE_TXN_ID, (SELECT concat(USER_ID,'-',FNAME,' ',LNAME) FROM student_dtls where USER_ID=txn.STUDENT_ID) as student_nm,TIMESTAMP(txn.UPLOADED_ON),asgnmnt.DESC_TXT,TIMESTAMP(txn.DUE_ON) FROM assignment_resource_txn txn INNER JOIN assignment asgnmnt ON txn.ASSIGNMENT_ID = asgnmnt.ASSIGNMENT_ID and asgnmnt.DELETED_FL='0' INNER JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=asgnmnt.ASSIGNMENT_ID where mod_assignment.MODULE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentStatus(rs.getString(3));
                vo.setAssignmentResourceTxnId(rs.getInt(4));
                vo.setAssignmentSubmittedBy(rs.getString(5));
                vo.setAssignmentSubmittedDate(rs.getString(6));
                vo.setAssignmentDesc(rs.getString(7));
                vo.setAssignmentDueDate(rs.getString(8));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignmentsByModuleId # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignmentsByModuleId # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


    @Override
    public List<AssignmentVO> getStudentAssignmentsByModuleId(String userName,int moduleId) throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            String sql = "SELECT DISTINCT asgnmnt.ASSIGNMENT_ID,asgnmnt.ASSIGNMENT_NAME,txn.IS_COMPLETED,(SELECT CONCAT(ulogin.FNAME,' ',ulogin.LNAME) FROM student_dtls ulogin where ulogin.USER_ID=txn.STUDENT_ID limit 1) as STUDENT_ID,TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON) FROM assignment_resource_txn txn INNER JOIN assignment asgnmnt ON txn.ASSIGNMENT_ID = asgnmnt.ASSIGNMENT_ID and asgnmnt.DELETED_FL='0' INNER JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=asgnmnt.ASSIGNMENT_ID where mod_assignment.MODULE_ID = ? and txn.LAST_USERID_CD=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            stmt.setString(2, userName);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentStatus(rs.getString(3));
                vo.setAssignmentSubmittedBy(rs.getString(4));
                vo.setAssignmentSubmittedDate(rs.getString(5));
                vo.setAssignmentDueDate(rs.getString(6));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignmentsByModuleId # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignmentsByModuleId # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    
    @Override
    public List<ResourseVO> getAssignmentsResources(int assignmentId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * RESOURSE_ID | RESOURSE_NAME | DESC_TXT   |     RESOURCE_AUTHOR | AUTHOR_IMG |  RESOURCE_URL | THUMB_IMG  |  UPLOADED_ON 
             * 
             * SELECT DISTINCT rsm.RESOURSE_ID,rsm.DESC_TXT,rsm.RESOURSE_NAME,TIMESTAMP(txn.UPLOADED_ON) FROM assignment_resource_txn txn INNER JOIN resourse_mstr rsm ON txn.UPLODED_RESOURCE_ID = rsm.RESOURSE_ID WHERE txn.STUDENT_ID=?
             */
            String sql = "SELECT DISTINCT rsm.RESOURSE_ID,rsm.RESOURSE_NAME,rsm.DESC_TXT,rsm.RESOURCE_AUTHOR,rsm.AUTHOR_IMG,rsm.RESOURCE_URL,rsm.THUMB_IMG,TIMESTAMP(txn.UPLOADED_ON),txn.STUDENT_ID FROM assignment_resource_txn txn INNER JOIN resourse_mstr rsm ON txn.UPLODED_RESOURCE_ID = rsm.RESOURSE_ID and rsm.DELETED_FL='0' WHERE txn.ASSIGNMENT_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setResourceDesc(rs.getString(3));

                vo.setAuthorName(rs.getString(4));
                vo.setAuthorImg(rs.getString(5));
                vo.setResourceUrl(rs.getString(6));
                vo.setThumbUrl(rs.getString(7));
                vo.setUploadedDate(rs.getString(8));
                vo.setUploadedBy(rs.getString(9));
                
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getAssignmentsResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentsResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    
    @Override
    public List<ResourseVO> getAssignmentsResources(int userId, int assignmentId) throws LmsDaoException {
        List<ResourseVO> list = new ArrayList<ResourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * RESOURCE_ID | RESOURCE_DESC | RESOURCE_URL | UPLOADED_DATE
             * 
             * SELECT DISTINCT rsm.RESOURSE_ID,rsm.DESC_TXT,rsm.RESOURSE_NAME,TIMESTAMP(txn.UPLOADED_ON) FROM assignment_resource_txn txn INNER JOIN resourse_mstr rsm ON txn.UPLODED_RESOURCE_ID = rsm.RESOURSE_ID WHERE txn.STUDENT_ID=?
             */
           // String sql = "SELECT DISTINCT rsm.RESOURSE_ID,rsm.RESOURSE_NAME,rsm.RESOURCE_AUTHOR,rsm.DESC_TXT,rsm.RESOURCE_URL,rsm.AUTHOR_IMG, rsm.THUMB_IMG,TIMESTAMP(txn.UPLOADED_ON) FROM assignment_resource_txn txn INNER JOIN resourse_mstr rsm ON txn.UPLODED_RESOURCE_ID = rsm.RESOURSE_ID and rsm.DELETED_FL='0' WHERE txn.STUDENT_ID=(SELECT USER_NM FROM user_login where USER_ID=?) and txn.ASSIGNMENT_ID=?";
            //D_UPDT
            String sql = "SELECT DISTINCT rsm.UPLODED_RESOURCE_ID,rsm.RESOURSE_NAME,rsm.RESOURCE_AUTHOR,rsm.DESC_TXT,rsm.RESOURCE_URL,rsm.AUTHOR_IMG, rsm.THUMB_IMG,TIMESTAMP(rsm.UPLOADED_ON) FROM assignment_resource_txn rsm WHERE rsm.STUDENT_ID=? and rsm.ASSIGNMENT_ID=? and rsm.UPLOADED_ON is not null";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, assignmentId);
            
            rs = stmt.executeQuery();
            ResourseVO vo = null;
            while (rs.next()) {
                vo = new ResourseVO();
                vo.setResourceId(rs.getInt(1));
                vo.setResourceName(rs.getString(2));
                vo.setAuthorName(rs.getString(3));
                vo.setResourceDesc(rs.getString(4));
                vo.setResourceUrl(rs.getString(5));
                vo.setAuthorImg(rs.getString(6));
                vo.setThumbUrl(rs.getString(7));
                vo.setUploadedDate(rs.getString(8));

                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getAssignmentsResources # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentsResources # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


	@Override
	public List<AssignmentVO> getAssignmentsByModuleId(int moduleId) throws LmsDaoException{
		List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            //String sql = "SELECT asign.ASSIGNMENT_ID, asign.ASSIGNMENT_NAME, asign.DESC_TXT, asign.ENABLE_FL from assignment asign, module_assignment_map mapassign where asign.DELETED_FL='0' and mapassign.ASSIGNMENT_ID=asign.ASSIGNMENT_ID and mapassign.MODULE_ID=?";
            
            String sql = "SELECT asign.ASSIGNMENT_ID, asign.ASSIGNMENT_NAME, asign.DESC_TXT, asign.ENABLE_FL from assignment asign, module_assignment_map mapassign where asign.DELETED_FL='0' and mapassign.ASSIGNMENT_ID=asign.ASSIGNMENT_ID and mapassign.MODULE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentDesc(rs.getString(3));
                vo.setEnableStatus(rs.getString(4));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignmentsByModuleId # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignmentsByModuleId # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<AssignmentVO> getStudentsByAssignmentId(int assignmentId)
			throws LmsDaoException {
		List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            //String sql = "SELECT DISTINCT txn.IS_COMPLETED,txn.RESOURCE_TXN_ID,(SELECT concat(USER_ID,'-',FNAME,' ',LNAME) FROM student_dtls where EMAIL_ID=txn.STUDENT_ID) as student_nm,TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON) FROM assignment_resource_txn txn INNER JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where mod_assignment.ASSIGNMENT_ID = ?";
            String sql = "SELECT DISTINCT txn.IS_COMPLETED,txn.RESOURCE_TXN_ID, concat(s.USER_ID,'-',s.FNAME,' ',s.LNAME) as student_nm,TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON) FROM  student_dtls s,assignment_resource_txn txn INNER JOIN module_assignment_map mod_assignment ON mod_assignment.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where s.USER_ID=txn.STUDENT_ID and mod_assignment.ASSIGNMENT_ID =?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentId);
            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentStatus(rs.getString(1));
                vo.setAssignmentResourceTxnId(rs.getInt(2));
                vo.setAssignmentSubmittedBy(rs.getString(3));
                vo.setAssignmentSubmittedDate(rs.getString(4));
                vo.setAssignmentDueDate(rs.getString(5));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignmentsByModuleId # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignmentsByModuleId # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<AssignmentVO> getStudentsByAssignmentId(int schoolId,
			int classId, int hrmId, int courseId, int moduleId, int userId,
			int assignmentId) throws LmsDaoException {
		List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            /**
             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE
             */
            
            String sql = "SELECT DISTINCT txn.IS_COMPLETED,txn.RESOURCE_TXN_ID, (SELECT CONCAT(s.USER_ID,'-',s.FNAME,' ',s.LNAME) FROM student_dtls s where s.USER_ID=txn.STUDENT_ID) as student_nm,TIMESTAMP(txn.UPLOADED_ON),TIMESTAMP(txn.DUE_ON) FROM assignment_resource_txn txn where txn.ASSIGNMENT_ID=? and txn.STUDENT_ID in (SELECT USER_ID FROM user_cls_map where SCHOOL_ID=? and CLASS_ID=? and HRM_ID=?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentId);
            stmt.setInt(2, schoolId);
            stmt.setInt(3, classId);
            stmt.setInt(4, hrmId);

            
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
                vo = new AssignmentVO();
                vo.setAssignmentStatus(rs.getString(1));
                vo.setAssignmentResourceTxnId(rs.getInt(2));
                vo.setAssignmentSubmittedBy(rs.getString(3));
                vo.setAssignmentSubmittedDate(rs.getString(4));
                vo.setAssignmentDueDate(rs.getString(5));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getStudentAssignmentsByModuleId # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getStudentAssignmentsByModuleId # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<AssignmentVO> getAssignments(int schoolId, int classId,
			int hrmId, int courseId, int moduleId, int userId)
			throws LmsDaoException {
        List<AssignmentVO> list = new ArrayList<AssignmentVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();

            //DB_UPDT
            StringBuffer sql = new StringBuffer("SELECT DISTINCT txn.ASSIGNMENT_ID,txn.ASSIGNMENT_NAME,txn.ASSIGNMENT_DESC_TXT,txn.ENABLE_FL,asgnmnt.ASSIGNMENT_TYP_ID,txn.DUE_ON,txn.CANCEL_FL FROM teacher_courses tcourse inner join teacher_course_sessions tc_sess on tcourse.TEACHER_COURSE_ID=tc_sess.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcsd.COURSE_SESSION_ID=tc_sess.COURSE_SESSION_ID inner join module_assignment_map mam on tcsd.MODULE_ID=mam.MODULE_ID inner join assignment_resource_txn txn on mam.ASSIGNMENT_ID=txn.ASSIGNMENT_ID inner join student_dtls sdtls on sdtls.USER_ID=txn.STUDENT_ID inner join assignment asgnmnt on asgnmnt.ASSIGNMENT_ID=txn.ASSIGNMENT_ID where tcourse.TEACHER_ID=").append(userId);
            
            if(schoolId>0)
            	sql.append(" and tcourse.SCHOOL_ID=").append(schoolId);
//Phase-3 Remove class change - >
//            if(classId>0)
//            	sql.append(" and tcourse.CLASS_ID=").append(classId);
            if(hrmId>0)
            	sql.append(" and tcourse.HRM_ID=").append(hrmId);
            if(courseId>0)
            	sql.append(" and tcourse.COURSE_ID=").append(courseId);
            if(moduleId>0)
            	sql.append(" and tcsd.MODULE_ID=").append(moduleId);
            
           logger.debug("query - "+sql);
            stmt = conn.prepareStatement(sql.toString());
             
            rs = stmt.executeQuery();
            AssignmentVO vo = null;
            while (rs.next()) {
            	
                vo = new AssignmentVO();
                vo.setAssignmentId(rs.getInt(1));
                vo.setAssignmentName(rs.getString(2));
                vo.setAssignmentDesc(rs.getString(3));
                vo.setEnableStatus(rs.getString(4));
                vo.setAssignmentType(rs.getString(5));
                vo.setAssignmentDueDate(rs.getString(6));
                vo.setCancelStatus(rs.getString(7));
                
                list.add(vo);

            }

        } catch (SQLException se) {
           logger.error("getAssignments(.....) # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignments(.....) # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }


	@Override
	public List<AssignmentVO> getStudentAssignmentsByModule(int moduleId, int userId) throws LmsDaoException {
		 List<AssignmentVO> list = new ArrayList<AssignmentVO>();

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs =null;
	        try {
	            conn = getConnection();

	            /**
	             * ASSIGNMENT_ID | ASSIGNMENT_NAME | ASSIGNMENT_STATUS | ASSIGNMENT_SUBMITTED_BY | ASSIGNMENT_SUBMITTED_DATE | AssignmentTypeId | Assignment type | Review Grade
	             */
	            
	            String sql = "SELECT DISTINCT art.RESOURCE_TXN_ID, assig.ASSIGNMENT_ID,assig.ASSIGNMENT_NAME,art.IS_COMPLETED, TIMESTAMP(art.UPLOADED_ON),TIMESTAMP(art.DUE_ON),assig.ASSIGNMENT_TYP_ID,atype.ASSIGNMENT_TYP_NM,(SELECT avg(agval.GRADE_VALUE) FROM assignment_review_txn artxn inner join assignment_grade_values agval on agval.GRADE_VALUE_ID=artxn.GRADE_VALUE_ID where artxn.RESOURCE_TXN_ID=art.RESOURCE_TXN_ID)as reviewGrade FROM assignment_resource_txn art INNER JOIN assignment assig on assig.ASSIGNMENT_ID=art.ASSIGNMENT_ID INNER JOIN module_assignment_map mam on mam.ASSIGNMENT_ID=assig.ASSIGNMENT_ID INNER JOIN user_login ulog on ulog.USER_ID=art.STUDENT_ID INNER JOIN assignment_typ_mstr atype on atype.ASSIGNMENT_TYP_ID=assig.ASSIGNMENT_TYP_ID WHERE art.ENABLE_FL='1' and CANCEL_FL !='1' and mam.MODULE_ID=? and ulog.USER_ID=?";
	            logger.debug("query - "+sql);
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, moduleId);
	            stmt.setInt(2, userId);
	            
	            rs = stmt.executeQuery();
	            AssignmentVO vo = null;
	            while (rs.next()) {
	                vo = new AssignmentVO();
	                vo.setAssignmentResourceTxnId(rs.getInt(1));
	                vo.setAssignmentId(rs.getInt(2));
	                vo.setAssignmentName(rs.getString(3));
	                vo.setAssignmentStatus(rs.getString(4));
	                vo.setAssignmentSubmittedDate(rs.getString(5));
	                vo.setAssignmentDueDate(rs.getString(6));
	                vo.setAssignmentTypeId(rs.getInt(7));
	                vo.setAssignmentType(rs.getString(8));
	                vo.setReviewGrade(rs.getInt(9));
	                
	                list.add(vo);
	            }

	        } catch (SQLException se) {
	           logger.error("getStudentAssignments # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("getStudentAssignments # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, rs);
	        }

	        return list;
	}


	@Override
	public List<AssignmentQuestionVO> getAssignmentQuestionAnswers(AssignmentVO asignment)
			throws LmsDaoException {
		 	List<AssignmentQuestionVO> list = new ArrayList<AssignmentQuestionVO>();

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs =null;
	        try {
	            conn = getConnection();

	            if(asignment.getAssignmentTypeId()==3)
	            {
		            //assType ==3 (MCQ)
	            	//[ QUESTION_ID     QUESTION_NM     OPTION_ID     OPTION_NM     ANSWER_ID]
		            String sql = "SELECT aqmstr.QUESTION_ID,aqmstr.QUESTION_NM,mcq.OPTION_ID,mcq.OPTION_NM,atxn.ANSWER_ID FROM assignment_quest_mstr aqmstr inner join assignment_mcq_options mcq on aqmstr.QUESTION_ID=mcq.QUESTION_ID left join assignment_txn_detail atxn on atxn.QUESTION_ID=aqmstr.QUESTION_ID and mcq.OPTION_ID=atxn.ANSWER_ID and atxn.ASSIGNMENT_RES_TXN_ID=? where aqmstr.ASSIGNMENT_ID=? order by aqmstr.DISPLAY_NO";
		            stmt = conn.prepareStatement(sql);
		            stmt.setInt(1,asignment.getAssignmentResourceTxnId());
		            stmt.setInt(2, asignment.getAssignmentId());
		            
		            rs = stmt.executeQuery();
		            AssignmentQuestionVO vo = null;
		            while (rs.next()) {
		                vo = new AssignmentQuestionVO();
		                vo.setQuestionId(rs.getInt(1));
		                vo.setQuestionName(rs.getString(2));
		                vo.setOptionId(rs.getInt(3));
		                vo.setOptionName(rs.getString(4));
		                vo.setAnswerId(rs.getInt(5));
		                list.add(vo);
		            }	            	
	            	
	            }else{
	            //assType !=3 (Non MCQ)
	            //[QUESTION_ID     QUESTION_NM     ANSWER_TXT ]	
	            String sql = "SELECT aqmstr.QUESTION_ID,aqmstr.QUESTION_NM,atxn.ANSWER_TXT FROM assignment_quest_mstr aqmstr left join assignment_txn_detail atxn on aqmstr.QUESTION_ID=atxn.QUESTION_ID and aqmstr.ENABLE_FL=1 and aqmstr.DELETED_FL='0' and atxn.ASSIGNMENT_RES_TXN_ID=? where aqmstr.ASSIGNMENT_ID=? order by aqmstr.DISPLAY_NO";
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1,asignment.getAssignmentResourceTxnId());
	            stmt.setInt(2, asignment.getAssignmentId());
	            
	            rs = stmt.executeQuery();
	            AssignmentQuestionVO vo = null;
	            while (rs.next()) {
	                vo = new AssignmentQuestionVO();
	                vo.setQuestionId(rs.getInt(1));
	                vo.setQuestionName(rs.getString(2));
	                vo.setAnswerText(rs.getString(3));
	                list.add(vo);
	            }
	           }//end else
	        } catch (SQLException se) {
	           logger.error("getAssignmentQuestionAnswers # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("getAssignmentQuestionAnswers # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, rs);
	        }

	        return list;
	}


	@Override
	public String getCourseReviewGrade(int avgAssignmentReviewGrade) throws LmsDaoException {
		String grade=null;
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "select GRADE_LABEL from assignment_grade_values where GRADE_VALUE>=? order by GRADE_VALUE limit 1";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, avgAssignmentReviewGrade);
            rs = stmt.executeQuery();
            if(rs.next()) {
            	grade=rs.getString(1);
            }

        } catch (SQLException se) {
           logger.error("getCourseReviewGrade # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getCourseReviewGrade # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
	
        return grade;
	}


	@Override
	public List<CourseVO> getAvailableCourses(int userId, int schoolId, int classId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT COURSE_ID, COURSE_NAME, DESC_TXT,is_self_driven FROM course_mstr where DELETED_FL='0' and COURSE_ID in(SELECT hcm.COURSE_ID FROM school_cls_map scm inner join class_hrm_map chm on scm.CLASS_ID=chm.CLASS_ID inner join hrm_course_map hcm on hcm.HRM_ID=chm.HRM_ID where scm.SCHOOL_ID=? and hcm.HRM_ID not in (SELECT HRM_ID FROM user_cls_map where USER_ID=?))";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, schoolId);
            stmt.setInt(2, userId);
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setCourseId(rs.getString(1));
                vo.setCourseName(rs.getString(2));
                vo.setCourseDesc(rs.getString(3));
                vo.setIsSelfDriven(rs.getString(4));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getAvailableCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAvailableCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<CourseVO> getAvailableCourseModules(String courseId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT MODULE_ID, MODULE_NAME FROM module_mstr where MODULE_ID in (SELECT MODULE_ID FROM course_module_map where COURSE_ID=?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseId);
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setModuleId(rs.getString(1));
                vo.setModuleName(rs.getString(2));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getAvailableCourseModules # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAvailableCourseModules # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<CourseVO> getAvailableCoursesSessions(String courseId,int userId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT hmstr.HRM_ID,hmstr.start_dt FROM homeroom_mstr hmstr inner join hrm_course_map hcm on hcm.HRM_ID=hmstr.HRM_ID where COURSE_ID=? and hmstr.HRM_ID not in (SELECT HRM_ID FROM user_cls_map where USER_ID=?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseId);
            stmt.setInt(2, userId);
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setHrmId(rs.getInt(1));
                vo.setSessionStartDate(rs.getString(2));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getAvailableCoursesSessions # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAvailableCoursesSessions # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}


	@Override
	public List<CourseVO> getRequestedCourses(int userId, int schoolId, int classId) throws LmsDaoException {
        List<CourseVO> list = new ArrayList<CourseVO>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            String sql = "SELECT hmstr.HRM_NAME,ucm.start_dt,ucm.REQUEST_STATUS FROM homeroom_mstr hmstr inner join user_cls_map ucm on hmstr.HRM_ID=ucm.HRM_ID where ASSIGN_TYPE !='0' and USER_ID=? and SCHOOL_ID=? and CLASS_ID =?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, schoolId);
            stmt.setInt(3, classId);
                        
            rs = stmt.executeQuery();
            CourseVO vo = null;
            while (rs.next()) {
                vo = new CourseVO();
                vo.setHrmName(rs.getString(1));
                vo.setSessionStartDate(rs.getString(2));
                vo.setCourseReqStatus(rs.getString(3));
                list.add(vo);
            }

        } catch (SQLException se) {
           logger.error("getRequestedCourses # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getRequestedCourses # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
	}

	
	@Override
	public int retryCourse(int userId, int schoolId, int hrmId, int courseId) throws LmsDaoException{
		int count=0;
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        	//1)	Reset Course Session Data to started state(0)
            /**
             * 
             * update teacher_course_sessions set IS_COMPLETE='2',START_SESSION_TM=null,END_SESSION_TM=null where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID=3 and SCHOOL_ID=1 and CLASS_ID=1 and HRM_ID=2 and COURSE_ID=1) 
			   update teacher_course_session_dtls set IS_COMPLETED='2',START_SESSION_TM=null,END_SESSION_TM=null where COURSE_SESSION_ID in(SELECT COURSE_SESSION_ID FROM teacher_course_sessions where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID=3 and SCHOOL_ID=1 and CLASS_ID=1 and HRM_ID=2 and COURSE_ID=1) )
			   update teacher_module_session_dtls set IS_COMPLETED='2',START_SESSION_TM=null,END_SESSION_TM=null where COURSE_SESSION_DTLS_ID in (SELECT COURSE_SESSION_DTLS_ID FROM teacher_course_session_dtls where COURSE_SESSION_ID in(SELECT COURSE_SESSION_ID FROM teacher_course_sessions where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID=3 and SCHOOL_ID=1 and CLASS_ID=1 and HRM_ID=2 and COURSE_ID=1)))
			 *
             */
			//2)	Delete All submitted assignments & its details
            /**
             * 
             * 
             * delete FROM assignment_txn_detail where ASSIGNMENT_RES_TXN_ID in (SELECT RESOURCE_TXN_ID FROM assignment_resource_txn where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID=3 and tc.SCHOOL_ID=1 and tc.CLASS_ID=1 and tc.HRM_ID=2 and tc.COURSE_ID=1) and STUDENT_ID = 6)
			   delete FROM assignment_resource_txn where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID=3 and tc.SCHOOL_ID=1 and tc.CLASS_ID=1 and tc.HRM_ID=2 and tc.COURSE_ID=1) and STUDENT_ID = 6
             * 
             * <<<<SHUFFLING Question order>>>
             * update assignment_quest_mstr set DISPLAY_NO = ROUND(MINUTE(CURTIME())/QUESTION_ID) where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID="+userId+" and tc.SCHOOL_ID="+schoolId+" and tc.HRM_ID="+hrmId+" and tc.COURSE_ID="+courseId+")
             */
            stmt.addBatch("update teacher_course_sessions set IS_COMPLETE='0',START_SESSION_TM=null,END_SESSION_TM=null where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID="+userId+" and SCHOOL_ID="+schoolId+" and HRM_ID="+hrmId+" and COURSE_ID="+courseId+")");
            stmt.addBatch("update teacher_course_session_dtls set IS_COMPLETED='0',START_SESSION_TM=null,END_SESSION_TM=null where COURSE_SESSION_ID in(SELECT COURSE_SESSION_ID FROM teacher_course_sessions where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID="+userId+" and SCHOOL_ID="+schoolId+" and HRM_ID="+hrmId+" and COURSE_ID="+courseId+"))");
            stmt.addBatch("update teacher_module_session_dtls set IS_COMPLETED='0',START_SESSION_TM=null,END_SESSION_TM=null where COURSE_SESSION_DTLS_ID in (SELECT COURSE_SESSION_DTLS_ID FROM teacher_course_session_dtls where COURSE_SESSION_ID in(SELECT COURSE_SESSION_ID FROM teacher_course_sessions where TEACHER_COURSE_ID in(SELECT TEACHER_COURSE_ID FROM teacher_courses where TEACHER_ID="+userId+" and SCHOOL_ID="+schoolId+" and HRM_ID="+hrmId+" and COURSE_ID="+courseId+")))");
           
            stmt.addBatch("delete FROM assignment_txn_detail where ASSIGNMENT_RES_TXN_ID in (SELECT RESOURCE_TXN_ID FROM assignment_resource_txn where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID="+userId+" and tc.SCHOOL_ID="+schoolId+" and tc.HRM_ID="+hrmId+" and tc.COURSE_ID="+courseId+") and STUDENT_ID = "+userId+")");
            stmt.addBatch("delete FROM assignment_resource_txn where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID="+userId+" and tc.SCHOOL_ID="+schoolId+" and tc.HRM_ID="+hrmId+" and tc.COURSE_ID="+courseId+") and STUDENT_ID ="+userId+" ");
            stmt.addBatch("update assignment_quest_mstr set DISPLAY_NO = ROUND(MINUTE(CURTIME())/QUESTION_ID) where ASSIGNMENT_ID in (SELECT mam.ASSIGNMENT_ID FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=tcsd.MODULE_ID where tc.TEACHER_ID="+userId+" and tc.SCHOOL_ID="+schoolId+" and tc.HRM_ID="+hrmId+" and tc.COURSE_ID="+courseId+")");
            
            int[] temp=stmt.executeBatch();
            logger.debug("Updated records - "+temp.length);
            count=temp.length;
            
            conn.commit();
        } catch (Exception e) {
        	try {
				conn.rollback();
			} catch (SQLException e1) {
	            e=e1;
			}
            logger.error("retryCourse # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }		
		
		return count;
	}


}//end of class 
