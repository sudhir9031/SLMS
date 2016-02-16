/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.vo.ClassMasterVo;
import com.scolere.lms.persistance.dao.iface.ClassMasterDao;
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
public class ClassMasterDaoImpl extends LmsDaoAbstract implements ClassMasterDao {
	
	Logger logger = LoggerFactory.getLogger(ClassMasterDaoImpl.class);

    public ClassMasterVo getClassDetail(int id) {
       logger.debug("Inside getClassDetail(?) >>");
        //Create object to return
        ClassMasterVo userDtls = new ClassMasterVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM CLASS_MSTR where CLASS_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getClassId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setClassId(rs.getInt("CLASS_ID"));
                userDtls.setClassName(rs.getString("CLASS_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METEDATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastuserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getClassDetail # " + se);
            
        } catch (Exception e) {
           logger.error("getClassDetail # " + e);
            
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateClassDetail(ClassMasterVo vo) {
       logger.debug("id =" + vo.getClassId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE class_mstr set CLASS_NAME=?, DESC_TXT=?, METEDATA=?, DELETED_FL=?, DISPLAY_NO=?, ENABLE_FL=?, CREATED_BY=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE CLASS_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, vo.getClassName());
            stmt.setString(2, vo.getDescTxt());
            stmt.setString(3, vo.getMetadata());
            stmt.setString(4, vo.getDeletedFl());
            stmt.setInt(5, vo.getDisplayNo());
            stmt.setString(6, vo.getEnableFl());
            stmt.setString(7, vo.getCreatedBy());
            stmt.setString(8, vo.getLastuserIdCd());
            stmt.setInt(9, vo.getClassId());

            stmt.executeUpdate();
            
        } catch (SQLException e) {
           logger.error("updateClassDetail # " + e);
            
        } catch (Exception e) {
           logger.error("updateClassDetail # " + e);
            
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveClassDetail(ClassMasterVo vo) {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO class_mstr(CLASS_ID, CLASS_NAME, DESC_TXT, METEDATA, DELETED_FL, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT_TM)   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , utc_timestamp)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getClassId());
            stmt.setString(2, vo.getClassName());
            stmt.setString(3, vo.getDescTxt());
            stmt.setString(4, vo.getMetadata());
            stmt.setString(5, vo.getDeletedFl());
            stmt.setInt(6, vo.getDisplayNo());
            stmt.setString(7, vo.getEnableFl());
            stmt.setString(8, vo.getCreatedBy());
            stmt.setString(9, vo.getLastuserIdCd());
            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getClassDetail # " + se);
            
        } catch (Exception e) {
           logger.error("getClassDetail # " + e);
            
        } finally {
            closeResources(conn, stmt, null);
        }

    }

    //delete method
    public boolean deleteClassDetail(ClassMasterVo vo) {
       logger.debug("id =" + vo.getClassId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM class_mstr WHERE CLASS_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getClassId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getClassDetail # " + se);
            
        } catch (Exception e) {
           logger.error("getClassDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
        return status;
        
    }

    
    public List< ClassMasterVo> getClassMasterVoList() {
        List< ClassMasterVo> distList = new ArrayList<ClassMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM class_mstr where DELETED_FL='0'";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            ClassMasterVo userDtls =null;
            while (rs.next()) {

                //3. Set db data to object
                userDtls = new ClassMasterVo();
                userDtls.setClassId(rs.getInt("CLASS_ID"));
                userDtls.setClassName(rs.getString("CLASS_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METEDATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastuserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

                //Add into list
                distList.add(userDtls);
            }

        } catch (SQLException se) {
           logger.error("getClassMasterVoList(schoolId) # " + se);
        } catch (Exception e) {
           logger.error("getClassMasterVoList(schoolId) # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;
    }
    

    @Override
    public List<ClassMasterVo> getClassMasterVoList(int schoolId) {
        List< ClassMasterVo> distList = new ArrayList<ClassMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM class_mstr where DELETED_FL='0' and CLASS_ID in (SELECT CLASS_ID FROM school_cls_map where SCHOOL_ID = ?) and CLASS_NAME not like '%-DefaultDepartment'";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, schoolId);
            
            ResultSet rs = stmt.executeQuery();
            
            ClassMasterVo userDtls =null;
            while (rs.next()) {

                //3. Set db data to object
                userDtls = new ClassMasterVo();
                userDtls.setClassId(rs.getInt("CLASS_ID"));
                userDtls.setClassName(rs.getString("CLASS_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METEDATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastuserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

                //Add into list
                distList.add(userDtls);
            }

        } catch (SQLException se) {
           logger.error("getClassMasterVoList(schoolId) # " + se);
        } catch (Exception e) {
           logger.error("getClassMasterVoList(schoolId) # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }

	@Override
	public List<ClassMasterVo> getClassMasterVoList(int schoolId, int teacher) {
		
		   List< ClassMasterVo> distList = new ArrayList<ClassMasterVo>();
		  Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();

	            String sql = "SELECT * FROM class_mstr where DELETED_FL='0' and CLASS_ID in (SELECT cs.CLASS_ID FROM teacher_course_session_map tcsm inner join teacher_courses cs on tcsm.TEACHER_COURSE_ID=cs.TEACHER_COURSE_ID where cs.SCHOOL_ID='"+schoolId+"' and tcsm.TEACHER_ID='"+teacher+"')";
	            
	            stmt = conn.prepareStatement(sql);
	            //stmt.setInt(1, schoolId);
	            
	            ResultSet rs = stmt.executeQuery();
	            
	            ClassMasterVo userDtls =null;
	            while (rs.next()) {

	                //3. Set db data to object
	                userDtls = new ClassMasterVo();
	                userDtls.setClassId(rs.getInt("CLASS_ID"));
	                userDtls.setClassName(rs.getString("CLASS_NAME"));
	                userDtls.setDescTxt(rs.getString("DESC_TXT"));
	                userDtls.setMetadata(rs.getString("METEDATA"));
	                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
	                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
	                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
	                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

	                userDtls.setLastuserIdCd(rs.getString("LAST_USERID_CD"));
	                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

	                //Add into list
	                distList.add(userDtls);
	            }

	        } catch (SQLException se) {
	           logger.error("getClassMasterVoList(schoolId) # " + se);
	        } catch (Exception e) {
	           logger.error("getClassMasterVoList(schoolId) # " + e);
	        } finally {
	            closeResources(conn, stmt, null);
	        }
	        //1 . jdbc code endd

	        //4 Return as required by method
	        return distList;
	}
    
    
}//End of class
