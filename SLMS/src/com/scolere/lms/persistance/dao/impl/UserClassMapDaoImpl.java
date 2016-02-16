/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.UserClassMapVo;
import com.scolere.lms.persistance.dao.iface.UserClassMapDao;
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
public class UserClassMapDaoImpl extends LmsDaoAbstract implements UserClassMapDao {
	
	Logger logger = LoggerFactory.getLogger(UserClassMapDaoImpl.class);

    public UserClassMapVo getUserClassMapDetail(int id) throws LmsDaoException {
       logger.debug("Inside getUserClassDetail(?) >>");
        //Create object to return
        UserClassMapVo userDtls = new UserClassMapVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM USER_COURSE_MAP where USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getUserId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setSchoolId(rs.getInt("SCHOOL_ID"));
                userDtls.setClassId(rs.getInt("CLASS_ID"));
                userDtls.setHomeRoomMasterId(rs.getInt("HRM_ID"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserClassMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserClassMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateUserClassMapDetail(UserClassMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_cls_map set  SCHOOL_ID=?, CLASS_ID=?, HRM_ID=?\n" +
"    WHERE USER_ID=?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, vo.getSchoolId());
            stmt.setInt(2, vo.getClassId());
            stmt.setInt(3, vo.getHomeRoomMasterId());
            stmt.setInt(4, vo.getUserId());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getUserClassMapDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserClassMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveUserClassMapDetail(UserClassMapVo vo) throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
//          String sql = "INSERT INTO user_cls_map(USER_ID, SCHOOL_ID, CLASS_ID, HRM_ID, start_dt, end_dt, REQUEST_STATUS)VALUES(?, ?, ?, ?,current_date, null, '1')";
            String sql = "INSERT INTO user_cls_map(USER_ID, SCHOOL_ID, CLASS_ID, HRM_ID, start_dt, end_dt, REQUEST_STATUS) SELECT ?,?,CLASS_ID, HRM_ID,current_date as start_dt,null,'1' as req_status FROM class_hrm_map where CLASS_ID=? ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.setInt(2, vo.getSchoolId());
            stmt.setInt(3, vo.getClassId());
            //stmt.setInt(4, vo.getHomeRoomMasterId());

            stmt.executeUpdate();
 
        } catch (SQLException se) {
           logger.error("saveUserClassMapDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveUserClassMapDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteUserClassMapDetail(UserClassMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM user_cls_map WHERE USER_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserClassMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserClassMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public List< UserClassMapVo> getUserClassMapVoList() throws LmsDaoException {
        List<UserClassMapVo> distList = new ArrayList<UserClassMapVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_cls_map ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                UserClassMapVo userDtls = new UserClassMapVo();

                 userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setSchoolId(rs.getInt("SCHOOL_ID"));
                userDtls.setClassId(rs.getInt("CLASS_ID"));
                userDtls.setHomeRoomMasterId(rs.getInt("HRM_ID"));
                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserClassMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserClassMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }


}
