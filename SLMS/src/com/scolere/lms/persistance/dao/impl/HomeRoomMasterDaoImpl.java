/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;


import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.persistance.dao.iface.HomeRoomMasterDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.vo.HomeRoomMasterVo;

/**
 *
 * @author admin
 */
public class HomeRoomMasterDaoImpl extends LmsDaoAbstract implements HomeRoomMasterDao {
	
	Logger logger = LoggerFactory.getLogger(HomeRoomMasterDaoImpl.class);

    public HomeRoomMasterVo getHomeRoomMasterDetail(int id) throws LmsDaoException {
       logger.debug("Inside getHomeRoomMasterDetail(?) >>");
        //Create object to return
        HomeRoomMasterVo userDtls = new HomeRoomMasterVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM homeroom_mstr where HRM_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getHomeRoomMstrId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setHomeRoomMstrId(rs.getInt("HRM_ID"));
                userDtls.setHomeRoomMstrName(rs.getString("HRM_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));

                userDtls.setMetadata(rs.getString("METADATA"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setDisplayNo(rs.getInt("DISPLAY_NO"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setCreatedBy(rs.getString("CREATED_BY"));

                userDtls.setLastuserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getHomeRoomMasterDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getHomeRoomMstrId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE homeroom_mstr set HRM_NAME=?, DESC_TXT=?, METADATA=?, DELETED_FL=?, DISPLAY_NO=?, ENABLE_FL=?, CREATED_BY=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE HRM_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, vo.getHomeRoomMstrName());
            stmt.setString(2, vo.getDescTxt());
            stmt.setString(3, vo.getMetadata());
            stmt.setString(4, vo.getDeletedFl());
            stmt.setInt(5, vo.getDisplayNo());
            stmt.setString(6, vo.getEnableFl());
            stmt.setString(7, vo.getCreatedBy());
            stmt.setString(8, vo.getLastuserIdCd());
            stmt.setInt(9, vo.getHomeRoomMstrId());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getHomeRoomMasterDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO homeroom_mstr(HRM_ID, HRM_NAME, DESC_TXT, METADATA, DELETED_FL, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT_TM)   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , utc_timestamp)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getHomeRoomMstrId());
            stmt.setString(2, vo.getHomeRoomMstrName());
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
           logger.error("getHomeRoomMasterDetail # " + se);
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getHomeRoomMstrId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM homeroom_mstr WHERE HRM_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getHomeRoomMstrId());
            stmt.executeUpdate();

        } catch (SQLException se) {
           logger.error("getHomeRoomMasterDetail # " + se);
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    
    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList() throws LmsDaoException {
       List< HomeRoomMasterVo> distList = new ArrayList<HomeRoomMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM homeroom_mstr where DELETED_FL='0' and HRM_ID in (SELECT HRM_ID FROM class_hrm_map where CLASS_ID=?)";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            HomeRoomMasterVo userDtls = null;
            
            while (rs.next()) {

                //3. Set db data to object
                userDtls = new HomeRoomMasterVo();
                userDtls.setHomeRoomMstrId(rs.getInt("HRM_ID"));
                userDtls.setHomeRoomMstrName(rs.getString("HRM_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METADATA"));
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
           logger.error("getHomeRoomMasterDetail() # " + se);
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail() # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }
    

    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList(int clsId) throws LmsDaoException {
        List< HomeRoomMasterVo> distList = new ArrayList<HomeRoomMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM homeroom_mstr where DELETED_FL='0' and HRM_ID in (SELECT HRM_ID FROM class_hrm_map where CLASS_ID=?) and HRM_NAME like '%-Default'";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clsId);
            
            ResultSet rs = stmt.executeQuery();
            HomeRoomMasterVo userDtls = null;
            while (rs.next()) {
                //3. Set db data to object
                userDtls = new HomeRoomMasterVo();
                userDtls.setHomeRoomMstrId(rs.getInt("HRM_ID"));
                userDtls.setHomeRoomMstrName(rs.getString("HRM_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METADATA"));
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
           logger.error("getHomeRoomMasterDetail(clsId) # " + se);
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail(clsId) # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;
    }

    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList(int clsId, int schoolId,int teacherId) throws LmsDaoException {
        List< HomeRoomMasterVo> distList = new ArrayList<HomeRoomMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            
            String sql = "SELECT * FROM homeroom_mstr where DELETED_FL='0' and HRM_ID in (SELECT cs.HRM_ID FROM teacher_course_session_map tcsm inner join teacher_courses cs on tcsm.TEACHER_COURSE_ID=cs.TEACHER_COURSE_ID where tcsm.TEACHER_ID="+teacherId+" ";
            if(clsId>0){
            	sql=sql+" AND cs.CLASS_ID = '"+clsId+"' ";
            }
            if(schoolId>0){
            	sql=sql+" AND cs.SCHOOL_ID = '"+schoolId+"' )";
            }
            
            
            
            stmt = conn.prepareStatement(sql);
           // stmt.setInt(1, clsId);
            
            ResultSet rs = stmt.executeQuery();
            HomeRoomMasterVo userDtls = null;
            while (rs.next()) {
                //3. Set db data to object
                userDtls = new HomeRoomMasterVo();
                userDtls.setHomeRoomMstrId(rs.getInt("HRM_ID"));
                userDtls.setHomeRoomMstrName(rs.getString("HRM_NAME"));
                userDtls.setDescTxt(rs.getString("DESC_TXT"));
                userDtls.setMetadata(rs.getString("METADATA"));
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
           logger.error("getHomeRoomMasterDetail(clsId) # " + se);
        } catch (Exception e) {
           logger.error("getHomeRoomMasterDetail(clsId) # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return distList;
    }
    
    
    
    
}//End of class
