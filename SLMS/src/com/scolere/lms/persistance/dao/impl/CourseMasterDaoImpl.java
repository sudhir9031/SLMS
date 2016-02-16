/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.CourseMasterVo;
import com.scolere.lms.persistance.dao.iface.CourseMasterDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author admin
 */
public class CourseMasterDaoImpl extends LmsDaoAbstract implements CourseMasterDao {

	
	Logger logger = LoggerFactory.getLogger(CourseMasterDaoImpl.class);
	
    public CourseMasterVo getCourseMasterDetail(int id) throws LmsDaoException {
       logger.debug("Inside getCourseMasterDetail(?) >>");
        //Create object to return
        CourseMasterVo userDtls = new CourseMasterVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM course_mstr where DELETED_FL='0' and COURSE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getCourseId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object
                userDtls.setCourseId(rs.getInt("COURSE_ID"));
                userDtls.setCourseName(rs.getString("COURSE_NAME"));
                userDtls.setCourseAuthor(rs.getString("COURSE_AUTHOR"));
                userDtls.setCourseDuration(rs.getString("COURSE_DURATION"));
                userDtls.setCreatedDt(rs.getString("CREATED_DT"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METEDATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getClassDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getClassDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateCourseMasterDetail(CourseMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getCourseId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE course_mstr set  COURSE_NAME=?, COURSE_AUTHOR=?, COURSE_DURATION=?, CREATED_DT=?, DESC_TXT=?, METADATA=?, DELETED_FL=?, DISPLAY_NO=?, ENABLE_FL=?, CREATED_BY=?, LAST_USERID_CD=?, LAST_UPDT=utc_timestamp\n"
                    + "    WHERE COURSE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, vo.getCourseName());
            stmt.setString(2, vo.getCourseAuthor());
            stmt.setString(3, vo.getCourseDuration());
            stmt.setString(4, vo.getCreatedDt());
            stmt.setString(5, vo.getDescTxt());
            stmt.setString(6, vo.getMetadata());
            stmt.setString(7, vo.getDeletedFl());
            stmt.setInt(8, vo.getDisplayNo());
            stmt.setString(9, vo.getEnableFl());
            stmt.setString(10, vo.getCreatedBy());
            stmt.setString(11, vo.getLastUserIdCd());
            stmt.setInt(12, vo.getCourseId());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getCourseDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getCourseDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveCourseMasterDetail(CourseMasterVo vo) throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO course_mstr(COURSE_ID, COURSE_NAME, COURSE_AUTHOR, COURSE_DURATION, CREATED_DT, DESC_TXT, METADATA, DELETED_FL, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT)    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ? ,utc_timestamp)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getCourseId());
            stmt.setString(2, vo.getCourseName());
            stmt.setString(3, vo.getCourseAuthor());
            stmt.setString(4, vo.getCourseDuration());
            stmt.setString(5, vo.getCreatedDt());
            stmt.setString(6, vo.getDescTxt());
            stmt.setString(7, vo.getMetadata());
            stmt.setString(8, vo.getDeletedFl());
            stmt.setInt(9, vo.getDisplayNo());
            stmt.setString(10, vo.getEnableFl());
            stmt.setString(11, vo.getCreatedBy());
            stmt.setString(12, vo.getLastUserIdCd());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getCourseDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getCourseDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteCourseMasterDetail(CourseMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getCourseId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM course_mstr WHERE COURSE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getCourseId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getCourseDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getCourseDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public List< CourseMasterVo> getCourseMasterVoList() throws LmsDaoException {
        List< CourseMasterVo> distList = new ArrayList<CourseMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM course_mstr ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                CourseMasterVo userDtls = new CourseMasterVo();
                userDtls.setCourseId(rs.getInt("COURSE_ID"));
                userDtls.setCourseName(rs.getString("COURSE_NAME"));
                userDtls.setCourseAuthor(rs.getString("COURSE_AUTHOR"));
                userDtls.setCourseDuration(rs.getString("COURSE_DURATION"));
                userDtls.setCreatedDt(rs.getString("CREATED_DT"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METADATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getCourseDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getCourseDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }

	@Override
	public List<CourseMasterVo> getCourseList(int homeRoomMstrId, int classId,
			int schoolId, int teacherId) throws LmsDaoException {
		//Create object to return
       
        List< CourseMasterVo> distList = new ArrayList<CourseMasterVo>();
        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            //USERID     
            String sql="SELECT cm.COURSE_ID, cm.COURSE_NAME, cm.METADATA ,cm.COURSE_AUTHOR,cm.DESC_TXT,cm.COURSE_DURATION,cm.COURSE_ICON, tcs.START_SESSION_TM, " +
            		"tcs.LAST_UPDT_TM, tcs.IS_COMPLETE, tcs.END_SESSION_TM FROM course_mstr cm, teacher_course_sessions tcs, teacher_courses tc ," +
            		" student_dtls td where cm.COURSE_ID = tc.COURSE_ID and tc.TEACHER_COURSE_ID = tcs.TEACHER_COURSE_ID and" +
            		" tc.SCHOOL_ID = '"+schoolId+"' and tc.CLASS_ID = '"+classId+"' and tc.HRM_ID='"+homeRoomMstrId+"' and tc.TEACHER_ID=td.USER_ID and td.USER_ID='"+teacherId+"' ";
            
            
            
            stmt = conn.prepareStatement(sql);
           // stmt.setInt(1, userDtls.getCourseId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //3. Set db data to object
            	 CourseMasterVo userDtls = new CourseMasterVo();
                userDtls.setCourseId(rs.getInt("COURSE_ID"));
                userDtls.setCourseName(rs.getString("COURSE_NAME"));
                userDtls.setCourseAuthor(rs.getString("COURSE_AUTHOR"));
                userDtls.setCourseDuration(rs.getString("COURSE_DURATION"));
                userDtls.setCourseIcon(rs.getString("COURSE_ICON"));
                userDtls.setMetadata(rs.getString("IS_COMPLETE"));
                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getClassDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getClassDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;
	}

	 
}
