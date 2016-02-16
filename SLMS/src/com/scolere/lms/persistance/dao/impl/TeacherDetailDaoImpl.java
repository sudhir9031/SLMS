/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.vo.TeacherDetailVo;
import com.scolere.lms.persistance.dao.iface.TeacherDetailDao;
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
public class TeacherDetailDaoImpl extends LmsDaoAbstract implements TeacherDetailDao {
	
	Logger logger = LoggerFactory.getLogger(TeacherDetailDaoImpl.class);

    public TeacherDetailVo getTeacherDetail(int id) {
    	
       logger.debug("Inside getTeacherDetail(?) >>");
        //Create object to return
        TeacherDetailVo userDtls = new TeacherDetailVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM student_dtls where TEACHER_DETAILS_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getTeacherDetailId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setUserId(rs.getInt("TEACHER_DETAILS_ID"));
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setfName(rs.getString("F_NAME"));
                userDtls.setlName(rs.getString("L_NAME"));
                userDtls.setEmailId(rs.getString("EMAIL_id"));
                userDtls.setJoiningDt(rs.getString("JOINING_DT"));
                userDtls.setContactNo(rs.getString("CONTACT_NO"));
                userDtls.setBirthDt(rs.getString("BIRTH_DT"));
                userDtls.setExperience(rs.getString("EXPERIENCE"));
                userDtls.setContractExpDt(rs.getString("CONTRACT_EXP_DT"));
                userDtls.setAddress(rs.getString("ADDRESS"));

                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getTeacherDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getTeacherDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateTeacherDetail(TeacherDetailVo vo) {
       logger.debug("id =" + vo.getTeacherDetailId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE student_dtls set USER_ID=?, F_NAME=?, L_NAME=?, EMAIL_ID=?, CONTACT_NO=?, BIRTH_DT=?, JOINING_DATE=?, EXPERIENCE=?, CONTRACT_EXP_DT=?, ADDRESS=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE TEACHER_DETAILS_ID=?";
            stmt = conn.prepareStatement(sql);


            stmt.setString(1, vo.getfName());
            stmt.setString(2, vo.getlName());
            stmt.setString(3, vo.getEmailId());
            stmt.setString(4, vo.getContactNo());
            stmt.setString(5, vo.getBirthDt());
            stmt.setString(6, vo.getJoiningDt());
            stmt.setString(7, vo.getExperience());
            stmt.setString(8, vo.getContractExpDt());
            stmt.setString(9, vo.getAddress());
            stmt.setString(10, vo.getLastUserIdCd());
            stmt.setInt(11, vo.getTeacherDetailId());
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getTeacherDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getTeacherDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveTeacherDetail(TeacherDetailVo vo) {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO student_dtls(USER_ID, F_NAME, L_NAME, EMAIL_ID, CONTACT_NO, BIRTH_DT, JOINING_DATE, EXPERIENCE, CONTRACT_EXP_DT, ADDRESS, LAST_USERID_CD, LAST_UPDT_TM) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  utc_timestamp)";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getUserId());
            stmt.setString(2, vo.getfName());
            stmt.setString(3, vo.getlName());
            stmt.setString(4, vo.getEmailId());
            stmt.setString(5, vo.getContactNo());
            stmt.setString(6, vo.getBirthDt());
            stmt.setString(7, vo.getJoiningDt());
            stmt.setString(8, vo.getExperience());
            stmt.setString(9, vo.getContractExpDt());
            stmt.setString(10, vo.getAddress());
            stmt.setString(11, vo.getLastUserIdCd());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getTeacherDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getTeacherDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteTeacherDetail(TeacherDetailVo vo) {
       logger.debug("id =" + vo.getTeacherDetailId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM student_dtls WHERE TEACHER_DETAILS_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getTeacherDetailId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getTeacherDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getTeacherDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public List<TeacherDetailVo> getTeacherDetailVoList() {

        List< TeacherDetailVo> distList = new ArrayList<TeacherDetailVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM student_dtls ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                TeacherDetailVo userDtls = new TeacherDetailVo();

                userDtls.setUserId(rs.getInt("TEACHER_DETAILS_ID"));
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setfName(rs.getString("F_NAME"));
                userDtls.setlName(rs.getString("L_NAME"));
                userDtls.setEmailId(rs.getString("EMAIL_id"));
                userDtls.setJoiningDt(rs.getString("JOINING_DATE"));
                userDtls.setContactNo(rs.getString("CONTACT_NO"));
                userDtls.setBirthDt(rs.getString("BIRTH_DT"));
                userDtls.setExperience(rs.getString("EXPERIENCE"));
                userDtls.setContractExpDt(rs.getString("CONTRACT_EXP_DT"));
                userDtls.setAddress(rs.getString("ADDRESS"));

                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }
}
