/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.UserCourseMapVo;
import com.scolere.lms.persistance.dao.iface.UserCourseMapDao;
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
public class UserCourseMapDaoImpl extends LmsDaoAbstract implements UserCourseMapDao {
	
	Logger logger = LoggerFactory.getLogger(UserCourseMapDaoImpl.class);

    public UserCourseMapVo getUserCourseMapDetail(int id) throws LmsDaoException {
       logger.debug("Inside getUserCourseMapDetail(?) >>");
        //Create object to return
        UserCourseMapVo userDtls = new UserCourseMapVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM USER_COURSE_MAP where USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getCourseId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setCourseId(rs.getInt("COURSE_ID"));

            }

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateUserCourseMapDetail(UserCourseMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_course_map set COURSE_ID=?\n"
                    + "    WHERE USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getCourseId());
            stmt.setInt(2, vo.getUserId());
    
            stmt.executeUpdate();
 
        } catch (SQLException e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveUserCourseMapDetail(UserCourseMapVo vo)  throws LmsDaoException{
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO user_course_map(USER_ID, COURSE_ID)  VALUES(?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.setInt(2, vo.getCourseId());

            stmt.executeUpdate();

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteUserCourseMapDetail(UserCourseMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM user_course_map WHERE USER_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public List< UserCourseMapVo> getUserCourseMapVoList()  throws LmsDaoException{
        List<UserCourseMapVo> distList = new ArrayList<UserCourseMapVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_course_map ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                UserCourseMapVo userDtls = new UserCourseMapVo();

                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setCourseId(rs.getInt("COURSE_ID"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

        return distList;

    }
    

}//end of class
