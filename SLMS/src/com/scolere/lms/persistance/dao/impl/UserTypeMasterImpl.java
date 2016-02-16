/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.UserTypeMasterVo;
import com.scolere.lms.persistance.dao.iface.UserTypeMasterDao;
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
public class UserTypeMasterImpl extends LmsDaoAbstract implements UserTypeMasterDao {
	
	Logger logger = LoggerFactory.getLogger(UserTypeMasterImpl.class);

    public UserTypeMasterVo getUserTypeMasterDetail(int id) throws LmsDaoException {
       logger.debug("Inside getUserTypeMasterDetail(?) >>");
        //Create object to return
        UserTypeMasterVo userDtls = new UserTypeMasterVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM USER_TYPE_MSTR where USER_TYPE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getUserTypeId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setUserType(rs.getString("USER_TYPE"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserTypeMasterDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserTypeMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateUserTypeMasterDetail(UserTypeMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserTypeId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_type_mstr set USER_TYPE=?\n"
                    + "    WHERE USER_TYPE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, vo.getUserType());
            stmt.setInt(2, vo.getUserTypeId());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getUserTypeMasterDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.debug("getUserTypeMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveUserTypeMasterDetail(UserTypeMasterVo vo) throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO user_type_mstr(USER_TYPE_ID, USER_TYPE)  VALUES(?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserTypeId());
            stmt.setString(2, vo.getUserType());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

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
    public boolean deleteUserTypeMasterDetail(UserTypeMasterVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserTypeId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM user_type_mstr WHERE USER_TYPE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserTypeId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserTypeMasterDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserTypeMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public List< UserTypeMasterVo> getUserTypeMasterVoList() throws LmsDaoException {
        List<UserTypeMasterVo> distList = new ArrayList<UserTypeMasterVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_type_mstr ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                UserTypeMasterVo userDtls = new UserTypeMasterVo();

                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setUserType(rs.getString("USER_TYPE"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserTypeMasterDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserTypeMasterDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }
}
