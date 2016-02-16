/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.UserClassMapVo;
import com.scolere.lms.domain.vo.UserLoginVo;
import com.scolere.lms.domain.vo.cross.UserVO;
import com.scolere.lms.persistance.dao.iface.UserLoginDao;
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

/**
 *
 * @author admin
 */
public class UserLoginDaoImpl extends LmsDaoAbstract implements UserLoginDao {
	
	Logger logger = LoggerFactory.getLogger(UserLoginDaoImpl.class);

    /**
     * 
     * @param userName
     * @param userPwd
     * @return
     * @throws LmsDaoException 
     */
    public UserVO getUser(String userName, String userPwd) throws LmsDaoException {
       logger.debug("getUser(name,pwd) >>");
        
        UserVO user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            //String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID where ul.USER_NM=? and ul.USER_PWD=?";
            //Updated@26-10-2015 for deleted_fl
            String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,sdtl.PROFILE_IMG," +
            		"ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID,schol.SCHOOL_NAME,clas.CLASS_NAME,hrm.HRM_NAME,sdtl.TITLE " +
            		"FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id " +
            		"inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID inner join school_mstr schol on schol.SCHOOL_ID=ucm.SCHOOL_ID and schol.DELETED_FL='0' " +
            		" inner join class_mstr clas on clas.CLASS_ID=ucm.CLASS_ID and clas.DELETED_FL='0' inner join homeroom_mstr hrm on hrm.HRM_ID=ucm.HRM_ID and hrm.DELETED_FL='0' " +
            		"where ul.USER_NM=? and ul.USER_PWD=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userPwd);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
            // USER_ID     USER_NM     USER_FB_ID     FNAME     LNAME     EMAIL_ID     ADDRESS     SCHOOL_ID     CLASS_ID     HRM_ID  schol.SCHOOL_NAME clas.CLASS_NAME hrm.HRM_NAME sdtl.TITLE  
                user = new UserVO();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserFbId(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmailId(rs.getString(6));
                user.setAddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                user.setSchoolId(rs.getInt(9));
                user.setClassId(rs.getInt(10));
                user.setHomeRoomId(rs.getInt(11));
                
                user.setSchoolName(rs.getString(12));
                user.setClassName(rs.getString(13));
                user.setHomeRoomName(rs.getString(14));
                user.setTitle(rs.getString(15));
            }

        } catch (SQLException se) {
           logger.error("getUser # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getUser # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return user;
    }

    
    public UserVO getUser(String userName, String userPwd,int userType) throws LmsDaoException {
       logger.debug("getUser(name,pwd,userType) >>");
        
        UserVO user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            if(userType==3) //Student
            {
            logger.debug("Student login.");
            //String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID where ul.USER_NM=? and ul.USER_PWD=?";
            //Updated @ 26-10-2015 for deleted_fl
            String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,sdtl.PROFILE_IMG," +
            		"ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID,schol.SCHOOL_NAME,clas.CLASS_NAME,hrm.HRM_NAME,sdtl.TITLE,sdtl.BIRTH_DT,sdtl.USER_DESC " +
            		"FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id " +
            		"inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID inner join school_mstr schol on schol.SCHOOL_ID=ucm.SCHOOL_ID and schol.DELETED_FL='0'" +
            		" inner join class_mstr clas on clas.CLASS_ID=ucm.CLASS_ID and clas.DELETED_FL='0' inner join homeroom_mstr hrm on hrm.HRM_ID=ucm.HRM_ID and hrm.DELETED_FL='0' " +
            		"where ul.USER_NM=? and ul.USER_PWD=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userPwd);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
            // USER_ID     USER_NM     USER_FB_ID     FNAME     LNAME     EMAIL_ID     ADDRESS     SCHOOL_ID     CLASS_ID     HRM_ID  schol.SCHOOL_NAME clas.CLASS_NAME hrm.HRM_NAME sdtl.TITLE  
                user = new UserVO();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserFbId(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmailId(rs.getString(6));
                user.setAddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                user.setSchoolId(rs.getInt(9));
                user.setClassId(rs.getInt(10));
                user.setHomeRoomId(rs.getInt(11));
                user.setSchoolName(rs.getString(12));
                user.setClassName(rs.getString(13));
                user.setHomeRoomName(rs.getString(14));
                user.setTitle(rs.getString(15));
                user.setDob(rs.getString(16));
                user.setDescription(rs.getString(17));
            }
            }else if(userType==2) //Teacher
            {
            	
            	System.out.println("Teacher login.");
                String sql = "SELECT usr.USER_ID,usr.USER_NM,usr.USER_FB_ID,teachr.FNAME,teachr.LNAME,teachr.EMAIL_ID,teachr.ADDRESS,teachr.PROFILE_IMG,teachr.TITLE,teachr.BIRTH_DT,teachr.USER_DESC FROM student_dtls teachr INNER JOIN user_login usr ON teachr.USER_ID=usr.USER_ID where usr.USER_NM=? AND usr.USER_PWD=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, userName);
                stmt.setString(2, userPwd);
                
                rs = stmt.executeQuery();
                if (rs.next()) {
                // USER_ID     USER_NM  USER_FB_ID     FNAME     LNAME     EMAIL_ID,ADDRESS    USER_TYPE_ID    
                    user = new UserVO();
                    user.setUserId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                    user.setUserFbId(rs.getString(3));
                    user.setFirstName(rs.getString(4));
                    user.setLastName(rs.getString(5));
                    user.setEmailId(rs.getString(6));
                    user.setAddress(rs.getString(7));
                    user.setProfileImage(rs.getString(8));
                    user.setTitle(rs.getString(9));
                    user.setDob(rs.getString(10));
                    user.setDescription(rs.getString(11));
                }
            	
            }else{
            	//throw new LmsDaoException("Unknown user type.");
            	System.out.println("Other user types are not allowed to access."+userType);
            }

        } catch (SQLException se) {
           logger.error("getUser # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getUser # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return user;
    }

    
    public UserVO getUser(String facebookId) throws LmsDaoException {
       logger.debug("getUser(facebookId) >>");
        
        UserVO user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            //Updated@26-10-2015 for deleted_fl
            String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,sdtl.PROFILE_IMG," +
            		"ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID,schol.SCHOOL_NAME,clas.CLASS_NAME,hrm.HRM_NAME,sdtl.TITLE,sdtl.BIRTH_DT,sdtl.USER_DESC " +
            		"FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id " +
            		"inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID inner join school_mstr schol on schol.SCHOOL_ID=ucm.SCHOOL_ID and schol.DELETED_FL='0'" +
            		" inner join class_mstr clas on clas.CLASS_ID=ucm.CLASS_ID and clas.DELETED_FL='0' inner join homeroom_mstr hrm on hrm.HRM_ID=ucm.HRM_ID and hrm.DELETED_FL='0' " +
            		" where ul.USER_FB_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, facebookId);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
            // USER_ID     USER_NM     USER_FB_ID     FNAME     LNAME     EMAIL_ID     ADDRESS     SCHOOL_ID     CLASS_ID     HRM_ID    
                user = new UserVO();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserFbId(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmailId(rs.getString(6));
                user.setAddress(rs.getString(7));  
                user.setProfileImage(rs.getString(8));
                user.setSchoolId(rs.getInt(9));
                user.setClassId(rs.getInt(10));
                user.setHomeRoomId(rs.getInt(11));
                
                user.setSchoolName(rs.getString(12));
                user.setClassName(rs.getString(13));
                user.setHomeRoomName(rs.getString(14));
                user.setTitle(rs.getString(15));
                user.setDob(rs.getString(16));
                user.setDescription(rs.getString(17));                
            }

        } catch (SQLException se) {
           logger.error("getUser # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getUser # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return user;
    }    

    
    public UserVO getUser(String facebookId,int userType) throws LmsDaoException {
       logger.debug("getUser(facebookId,userType) >>");
        
        UserVO user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            //Updated@26-10-2015 for deleted_fl
            String sql = "SELECT ul.USER_ID,ul.USER_NM,ul.USER_FB_ID,sdtl.FNAME,sdtl.LNAME,sdtl.EMAIL_ID,sdtl.ADDRESS,sdtl.PROFILE_IMG," +
            		"ucm.SCHOOL_ID,ucm.CLASS_ID,ucm.HRM_ID,schol.SCHOOL_NAME,clas.CLASS_NAME,hrm.HRM_NAME,sdtl.TITLE,sdtl.BIRTH_DT,sdtl.USER_DESC " +
            		"FROM user_login ul inner join student_dtls sdtl on ul.user_id = sdtl.user_id " +
            		"inner join user_cls_map ucm on ucm.USER_ID = ul.USER_ID inner join school_mstr schol on schol.SCHOOL_ID=ucm.SCHOOL_ID and schol.DELETED_FL='0'" +
            		" inner join class_mstr clas on clas.CLASS_ID=ucm.CLASS_ID and clas.DELETED_FL='0' inner join homeroom_mstr hrm on hrm.HRM_ID=ucm.HRM_ID and hrm.DELETED_FL='0' " +
            		" where ul.USER_FB_ID=? and ul.USER_TYPE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, facebookId);
            stmt.setInt(2,userType);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
            // USER_ID     USER_NM     USER_FB_ID     FNAME     LNAME     EMAIL_ID     ADDRESS     SCHOOL_ID     CLASS_ID     HRM_ID    
                user = new UserVO();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserFbId(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmailId(rs.getString(6));
                user.setAddress(rs.getString(7));  
                user.setProfileImage(rs.getString(8));
                user.setSchoolId(rs.getInt(9));
                user.setClassId(rs.getInt(10));
                user.setHomeRoomId(rs.getInt(11));
                
                user.setSchoolName(rs.getString(12));
                user.setClassName(rs.getString(13));
                user.setHomeRoomName(rs.getString(14));
                user.setTitle(rs.getString(15));
                user.setDob(rs.getString(16));
                user.setDescription(rs.getString(17));                
            }

        } catch (SQLException se) {
           logger.error("getUser(?,?) # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getUser(?,?) # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return user;
    }    
    
    
    public UserLoginVo getUserLoginDetail(int id) throws LmsDaoException {
       logger.debug("Inside getUserLoginDetail(?) >>");
        //Create object to return
        UserLoginVo userDtls =null;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_login where USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	 userDtls = new UserLoginVo();
                //3. Set db data to object
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setUserPwd(rs.getString("USER_PWD"));
                userDtls.setUserFbId(rs.getString("USER_FB_ID"));
                userDtls.setUserName(rs.getString("USER_NM"));
                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return userDtls;
    }

    
    public UserLoginVo getUserLoginDetail(String userName) throws LmsDaoException {
       logger.debug("Inside getUserLoginDetail(userName) >>");
        //Create object to return
        UserLoginVo userDtls = null;

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_login where USER_NM=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userDtls = new UserLoginVo();
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setUserPwd(rs.getString("USER_PWD"));
                userDtls.setUserFbId(rs.getString("USER_FB_ID"));
                userDtls.setUserName(rs.getString("USER_NM"));
                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
            }


        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return userDtls;
    }
    
    
    
    public UserLoginVo getUserLoginDetail(String userName,String userPwd,boolean isTeacher) throws LmsDaoException {
       logger.debug("Inside getUserLoginDetail(userName,userPwd,isTeacher) >>");
        //Create object to return
        UserLoginVo userDtls = null;

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_login where USER_NM=? and USER_PWD=?";
            if(isTeacher==true)
            	sql=sql+" and USER_TYPE_ID=2";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userPwd);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userDtls = new UserLoginVo();
                
                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setUserPwd(rs.getString("USER_PWD"));
                userDtls.setUserFbId(rs.getString("USER_FB_ID"));
                userDtls.setUserName(rs.getString("USER_NM"));
                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));
                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
            }

        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return userDtls;
    }
    
    
    public boolean updateUserLoginDetail(UserLoginVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserName());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_login set USER_NM=?, USER_PWD=?, DELETED_FL=?, ENABLE_FL=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp"
                    + "    WHERE USER_ID=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, vo.getUserName());
            stmt.setString(2, vo.getUserPwd());
            stmt.setString(3, vo.getDeletedFl());
            stmt.setString(4, vo.getEnableFl());
            stmt.setString(5, vo.getLastUserIdCd());
            stmt.setInt(6, vo.getUserId());
            
            stmt.executeUpdate();

        } catch (SQLException e) {
           logger.error("getUserLoginDetail # " + e);
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;

    }

    
    public boolean updateUserPwd(String userName,String userPwd,String userNewPwd) throws LmsDaoException {
       
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_login SET USER_PWD=? WHERE USER_NM = ? and USER_PWD = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userNewPwd);
            stmt.setString(2, userName);
            stmt.setString(3, userPwd);
            
            stmt.executeUpdate();

        } catch (SQLException e) {
           logger.error("updateUserPwd # " + e);
        } catch (Exception e) {
           logger.error("updateUserPwd # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;

    }
    

    public boolean updateUserFBId(int userId,String fbId) throws LmsDaoException {
       
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE user_login SET USER_FB_ID=? WHERE USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fbId);
            stmt.setInt(2, userId);
            
            int t=stmt.executeUpdate();
            if(t>0)
            {
            	//success
            }else{
            	//no update error
            }

        } catch (SQLException e) {
           logger.error("updateUserLoginDetail # " + e);
        } catch (Exception e) {
           logger.error("updateUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;

    }
    
    //save method

    public UserLoginVo saveUserLoginDetail(UserLoginVo vo) throws LmsDaoException {
        UserLoginVo userLoginVo = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO user_login(USER_NM, USER_PWD, USER_TYPE_ID, DELETED_FL, ENABLE_FL, LAST_USERID_CD, LAST_UPDT_TM) VALUES(?, ?, ?, ?, ?, ?,  utc_timestamp)";
            stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, vo.getUserName());
            stmt.setString(2, vo.getUserPwd());
            stmt.setInt(3, vo.getUserTypeId());
            stmt.setString(4, vo.getDeletedFl());
            stmt.setString(5, vo.getEnableFl());
            stmt.setInt(6, vo.getUserId());

            int t=stmt.executeUpdate();
           logger.debug("No of inserted row = "+t);
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
            int last_inserted_id = resultSet.getInt(1);
               logger.debug("Last inserted userId : "+last_inserted_id);
                userLoginVo = vo;
                userLoginVo.setUserId(last_inserted_id);
            }

        } catch (SQLException se) {
           logger.error("saveUserLoginDetail # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveUserLoginDetail # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, resultSet);
        }

        return userLoginVo;
    }

    //delete method
    public boolean deleteUserLoginDetail(UserLoginVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getUserId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM USER_LOGIN WHERE USER_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return status;
    }

    @Override
    public List<UserLoginVo> getUserLoginVoList() throws LmsDaoException {
        List<UserLoginVo> distList = new ArrayList<UserLoginVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM user_login ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            UserLoginVo userDtls = null;
            while (rs.next()) {

                //3. Set db data to object
                userDtls = new UserLoginVo();

                userDtls.setUserId(rs.getInt("USER_ID"));
                userDtls.setUserPwd(rs.getString("USER_PWD"));
                userDtls.setUserTypeId(rs.getInt("USER_TYPE_ID"));
                userDtls.setDeletedFl(rs.getString("DELETED_FL"));
                userDtls.setEnableFl(rs.getString("ENABLE_FL"));

                userDtls.setLastUserIdCd(rs.getString("LAST_USERID_CD"));
                userDtls.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserLoginDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserLoginDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return distList;
    }


	@Override
	public boolean defaultFeedsAccessType(int userId,int hrmId) 
			throws LmsDaoException {
        String defaultFeedsAccess="INSERT INTO feed_user_access(user_id, access_type_id, access_for_id)VALUES("+userId+", 4, "+hrmId+")";
        boolean status = deleteOrUpdateByQuery(defaultFeedsAccess);
       logger.debug("defaultFeedsAccessType ? "+status);
        
        return status;
	}
	
    
	@Override
	public boolean defaultUserAssignment(String userName, int schoolId,
			int classId, int hrmId) throws LmsDaoException {
		
		String defaultAssignment="INSERT INTO assignment_resource_txn(ASSIGNMENT_ID, STUDENT_ID, UPLODED_RESOURCE_ID, UPLOADED_ON, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, DUE_ON,ENABLE_FL,RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL, ASSIGNMENT_NAME, ASSIGNMENT_DESC_TXT) SELECT mam.ASSIGNMENT_ID,(SELECT USER_ID FROM user_login where USER_TYPE_ID=3 and USER_NM='"+userName+"'),null,null,'1',(SELECT USER_ID FROM user_login where USER_TYPE_ID=3 and USER_NM='"+userName+"'),current_timestamp,date(mod_sess.END_SESSION_TM),mod_sess.IS_COMPLETED ,'','',0,'',0,'','','','','0',asmnt.ASSIGNMENT_NAME,asmnt.DESC_TXT FROM teacher_courses course inner join teacher_course_sessions course_sess on course.TEACHER_COURSE_ID=course_sess.TEACHER_COURSE_ID inner join teacher_course_session_dtls mod_sess on course_sess.COURSE_SESSION_ID=mod_sess.COURSE_SESSION_ID inner join module_assignment_map mam on mam.MODULE_ID=mod_sess.MODULE_ID inner join assignment asmnt on asmnt.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where course.SCHOOL_ID="+schoolId+" and course.CLASS_ID="+classId+" and course.HRM_ID="+hrmId;
        boolean assignmentStatus = deleteOrUpdateByQuery(defaultAssignment);
        logger.debug("assignmentStatus ? "+assignmentStatus);
        
        return assignmentStatus;
	}

	
	@Override
	public int getFeedAccessType(int userId) throws LmsDaoException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int accessTypeId = 0;
		try {
			conn = getConnection();

			String sql = "SELECT access_type_id FROM feed_user_access where user_id="+ userId;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				accessTypeId = rs.getInt("access_type_id");
			}

		} catch (SQLException se) {
			System.out.println("getFeedAccessType # " + se);
		} catch (Exception e) {
			System.out.println("getFeedAccessType # " + e);
		} finally {
			closeResources(conn, stmt, rs);
		}

		return accessTypeId;
	}

	
	@Override
	public int setFeedAccessType(int userId, int accesTypeId)
			throws LmsDaoException {
		Connection conn = null;
		PreparedStatement cstmt = null;
		ResultSet resultSet = null;
		int t = 0;
		try {
			conn = getConnection();
			
			//Get access_for_id
			String accessForIdQuery=null;
			if(accesTypeId==1)
				accessForIdQuery="SCHOOL_ID";
			else if(accesTypeId==2)
				accessForIdQuery="HRM_ID";
			else if(accesTypeId==3)
				accessForIdQuery="CLASS_ID";
			else if(accesTypeId==4)
				accessForIdQuery="HRM_ID";

		
			String query = "update feed_user_access set access_type_id="+accesTypeId+",access_for_id=(SELECT "+accessForIdQuery+" FROM user_cls_map where USER_ID="+userId+" union SELECT "+accessForIdQuery+" FROM teacher_courses where TEACHER_ID="+userId+" limit 1) where user_id="+userId;
			System.out.println("Query : " + query);
			cstmt = conn.prepareStatement(query);
			
			t = cstmt.executeUpdate();
			System.out.println("No of affected row = " + t);
		} catch (Exception e) {
			System.out.println("Error setFeedAccessType - "
					+ e.getMessage());
		} finally {
			closeResources(conn, cstmt, resultSet);
		}

		return t;
	}
	

	@Override
	public List<UserVO> getFeedUsers(int userId) throws LmsDaoException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<UserVO> userList = new ArrayList<UserVO>();
		try {
			
			//Get access_for_id : 1)school 2)district 3)class 4)home group + userId
			String accessTypFor=getQueryConcatedResult("SELECT concat(access_type_id,'~',access_for_id) FROM feed_user_access where user_id="+userId);
			if(accessTypFor != null && !accessTypFor.isEmpty())
			{
			String[] accessTypForArr=accessTypFor.split("~");	
			
			//Start getting userslist as per access type
			int accessType=Integer.parseInt(accessTypForArr[0]);
			UserClassMapVo userCls=getUserClassMapDetail(userId);
			
			StringBuffer userListQry=new StringBuffer("SELECT USER_ID FROM user_cls_map where SCHOOL_ID=").append(userCls.getSchoolId()); //Default school
			StringBuffer teacherListQry=new StringBuffer(" union ").append("SELECT distinct ul.USER_ID FROM teacher_courses tc inner join user_login ul on ul.USER_ID=tc.TEACHER_ID where tc.SCHOOL_ID=").append(userCls.getSchoolId());
			if(accessType==1) //School
			{
				//No Action
			}else if(accessType==2) //district
			{
				//Not yet implemented
			}else if(accessType==3) //class
			{
				userListQry.append(" AND CLASS_ID=").append(userCls.getClassId());
				teacherListQry.append(" AND tc.CLASS_ID=").append(userCls.getClassId());
				
			}else if(accessType==4) //Home room
			{
				userListQry.append(" AND CLASS_ID=").append(userCls.getClassId());
				userListQry.append(" AND HRM_ID=").append(userCls.getHomeRoomMasterId());
				teacherListQry.append(" AND tc.CLASS_ID=").append(userCls.getClassId());
				teacherListQry.append(" AND tc.HRM_ID=").append(userCls.getHomeRoomMasterId());
			}

			userListQry.append(teacherListQry);
			
			conn = getConnection();
			//String sql = "SELECT USER_ID,EMAIL_ID,CONCAT(FNAME,' ',LNAME) as USERNM,PROFILE_IMG,(SELECT count(*) FROM feed_restricted_users where userId=? and restricted_userId=sdtl.USER_ID) as usr_count FROM student_dtls sdtl where USER_ID in (SELECT user_id FROM feed_user_access where access_type_id="+accessTypForArr[0]+" and access_for_id="+accessTypForArr[1]+" and user_id !="+userId+")";
			String sql = "SELECT USER_ID,EMAIL_ID,CONCAT(FNAME,' ',LNAME) as USERNM,PROFILE_IMG,(SELECT count(*) FROM feed_restricted_users where userId=? and restricted_userId=sdtl.USER_ID) as usr_count FROM student_dtls sdtl where USER_ID != ? AND USER_ID in ("+userListQry+")";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			
			rs = stmt.executeQuery();
			UserVO user =null;
			while (rs.next()) {
				user = new UserVO();
				user.setUserId(rs.getInt(1));
				user.setEmailId(rs.getString(2));
				user.setUserName(rs.getString(3));
				user.setProfileImage(rs.getString(4));
				int temp=rs.getInt(5);
				if(temp>0)
					user.setIsFollowUpAllowed("0");
				else
					user.setIsFollowUpAllowed("1");
				// Add into list
				userList.add(user);
			}
		  }
		} catch (SQLException se) {
			System.out.println("getFeedUsers # " + se);
		} catch (Exception e) {
			System.out.println("getFeedUsers # " + e);
		} finally {
			closeResources(conn, stmt, rs);
		}

		return userList;
	}

	@Override
	public List<CommonKeyValueVO> getAccessTypeMasterData()
			throws LmsDaoException {
		List<CommonKeyValueVO> accessTypeList = null;
		try {
			String sql = "SELECT access_type_id,access_type_txt FROM feed_access_type";
			accessTypeList = getKeyValuePairList(sql);
		} catch (Exception se) {
			System.out.println("getAccessTypeMasterData # " + se);
		}

		return accessTypeList;
	}


	@Override
	public int updateFollowersStatus(int userId,List<UserVO> usersList)
			throws LmsDaoException {
		int updtCount=0;
		Connection conn = null;
		Statement cstmt = null;
		ResultSet resultSet = null;
		int []t =null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			cstmt=conn.createStatement();
			for(UserVO vo:usersList)
			{
				String query=null;
				if(vo.getIsFollowUpAllowed().equals("0"))
				{
				query = "INSERT INTO feed_restricted_users(userId, restricted_userId)VALUES("+userId+", "+vo.getUserId()+")";
				}else{
				query = "DELETE FROM feed_restricted_users WHERE userId="+userId+" and restricted_userId="+vo.getUserId()+"";	
				}
				System.out.println("Query : " + query);
				cstmt.addBatch(query);	
			}
			
			t = cstmt.executeBatch();
			System.out.println("No of affected row = " + t.length);
			updtCount=t.length;
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.println("Error updateFollowersStatus - "
					+ e.getMessage());
		} finally {
			closeResources(conn, cstmt, resultSet);
		}

		return updtCount;
	}
	
	
	
    private UserClassMapVo getUserClassMapDetail(int userId) throws LmsDaoException {
       logger.debug("Inside getUserClassDetail(?) >>");
        //Create object to return
        UserClassMapVo userDtls = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT USER_ID,SCHOOL_ID,CLASS_ID,HRM_ID FROM user_cls_map where USER_ID=? union SELECT ulogin.USER_ID,tcs.SCHOOL_ID,tcs.CLASS_ID,tcs.HRM_ID FROM teacher_courses tcs inner join user_login ulogin on tcs.TEACHER_ID=ulogin.USER_ID where ulogin.USER_ID=? limit 1";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	userDtls = new UserClassMapVo();
                userDtls.setUserId(rs.getInt(1));
                userDtls.setSchoolId(rs.getInt(2));
                userDtls.setClassId(rs.getInt(3));
                userDtls.setHomeRoomMasterId(rs.getInt(4));
            }

        } catch (SQLException se) {
           logger.error("getUserClassMapDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserClassMapDetail # " + e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return userDtls;
    }


	@Override
	public UserVO getUserOrgDetail(String userId) throws LmsDaoException {
       logger.debug("Inside getUserOrgDetail(?) >>");
        //Create object to return
        UserVO userDtls = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            //Updated@26-10-2015 for deleted_fl
            String sql = "SELECT ucm.USER_ID,smstr.SCHOOL_NAME,cmstr.CLASS_NAME,hmstr.HRM_NAME FROM user_cls_map ucm inner join school_mstr smstr on ucm.SCHOOL_ID=smstr.SCHOOL_ID and smstr.DELETED_FL='0' inner join class_mstr cmstr on ucm.CLASS_ID=cmstr.CLASS_ID and cmstr.DELETED_FL='0' inner join homeroom_mstr hmstr on hmstr.HRM_ID=ucm.HRM_ID and hmstr.DELETED_FL='0' where ucm.USER_ID="+userId;
           logger.debug(sql);
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            if (rs.next()) {
            	userDtls = new UserVO();
            	userDtls.setUserId(rs.getInt(1));
            	userDtls.setSchoolName(rs.getString(2));
            	userDtls.setClassName(rs.getString(3));
            	userDtls.setHomeRoomName(rs.getString(4));
            }

        } catch (SQLException se) {
           logger.error("getUserOrgDetail # " + se);
        } catch (Exception e) {
           logger.error("getUserOrgDetail # " + e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return userDtls;
    }


	@Override
	public boolean isAdminEmailValid(int schoolId, String adminEmail)
			throws LmsDaoException {
		boolean result = false;

		Connection conn = null;
		PreparedStatement cstmt = null;
		ResultSet resultSet = null;

		try {
			
			String query="SELECT ul.USER_ID FROM user_login ul inner join school_user_map sm on sm.USER_ID=ul.USER_ID where sm.SCHOOL_ID=? and ul.USER_NM=?";
			System.out.println("Query : " + query);
			conn = this.getConnection(dataSource);
			cstmt = conn.prepareStatement(query);
			cstmt.setInt(1, schoolId);
			cstmt.setString(2, adminEmail);
			
			resultSet = cstmt.executeQuery();
			if (resultSet.next()) {
				result = true;
			}

			System.out.println("Is valid Admin email - "+result);

		} catch (Exception e) {
			System.out.println("Error > isAdminEmailValid - "
					+ e.getMessage());
		} finally {
			closeResources(conn, cstmt, resultSet);
		}

		return result;
	}


	@Override
	public int requestCourse(String userid, String schoolId, String classId, String homeRoomId) throws LmsDaoException {
		Connection conn = null;
		PreparedStatement cstmt = null;
		ResultSet resultSet = null;
		int t = 0;
		try {
			conn = getConnection();
            conn.setAutoCommit(false);

			String query = "INSERT INTO user_cls_map(USER_ID, SCHOOL_ID, CLASS_ID, HRM_ID, start_dt, end_dt, REQUEST_STATUS,ASSIGN_TYPE)VALUES(?, ?, ?, ?, current_date, null, '2','1')";
			System.out.println("Query : " + query);
			cstmt = conn.prepareStatement(query);
			cstmt.setString(1, userid);
			cstmt.setString(2, schoolId);
			cstmt.setString(3, classId);
			cstmt.setString(4, homeRoomId);
			
			t = cstmt.executeUpdate();
			
			System.out.println("No of affected row = " + t);
			
			//Start creating course session 
			if(t>0)
			{
				String courseTyp = getQueryConcatedResult("SELECT cmstr.is_self_driven FROM hrm_course_map hcm inner join course_mstr cmstr on hcm.COURSE_ID=cmstr.COURSE_ID where hcm.HRM_ID="+homeRoomId);
				if(courseTyp.equals("1")) //self assign course type=1
				{
					createSelfAssignedCourseSession(userid, schoolId, classId, homeRoomId,conn);
				}
			}
			//End creating course session 			
			
			conn.commit();
		} catch (Exception e) {
        	try {
				conn.rollback();
				t=0;
			} catch (SQLException e1) {
	            e=e1;
			}
			throw new LmsDaoException(e.getMessage());
		} finally {
			closeResources(conn, cstmt, resultSet);
		}

		return t;
	}
	
	
	private void createSelfAssignedCourseSession(String userid, String schoolId, String classId, String hrmId,Connection conn) throws Exception 
	{
             /**
             * Create self assign course session
             * INSERT INTO teacher_courses(COURSE_ID, TEACHER_ID, CLASS_ID, SCHOOL_ID, HRM_ID, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT_TM)VALUES((SELECT COURSE_ID FROM hrm_course_map where HRM_ID=3 limit 1), "+userid+", "+schoolId+", "+classId+", "+homeRoomId+", 0, '1', "+userid+", "+userid+", current_timestamp)
             * INSERT INTO teacher_course_sessions(TEACHER_COURSE_ID, START_SESSION_TM, END_SESSION_TM, STATUS_TXT, IS_COMPLETE, LAST_USERID_CD, LAST_UPDT_TM, COURSE_ID, COURSE_NAME, COURSE_AUTHOR, AUTHOR_IMG, COURSE_DURATION, CREATED_DT, DESC_TXT, METADATA, DELETED_FL, COURSE_ICON) SELECT (SELECT TEACHER_COURSE_ID FROM teacher_courses where HRM_ID=hcm.HRM_ID and TEACHER_ID="+userid+" and SCHOOL_ID="+schoolId+" and CLASS_ID="+classId+") as TEACHER_COURSE_ID,current_timestamp,null,'','0',"+userid+",current_timestamp, hcm.COURSE_ID, COURSE_NAME, COURSE_AUTHOR, AUTHOR_IMG, COURSE_DURATION, CREATED_DT, DESC_TXT, METADATA, DELETED_FL,COURSE_ICON FROM course_mstr cm inner join hrm_course_map hcm on hcm.COURSE_ID=cm.COURSE_ID where HRM_ID="+homeRoomId+"
             * INSERT INTO teacher_course_session_dtls(COURSE_SESSION_ID, TEACHER_ID, MODULE_ID, CONTENT_ID, START_SESSION_TM, END_SESSION_TM, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, MODULE_NAME, DESC_TXT, METADATA, DELETED_FL) SELECT tcs.COURSE_SESSION_ID, tc.TEACHER_ID, cmm.MODULE_ID, 0, current_timestamp, null, '0', tc.TEACHER_ID, current_timestamp, mm.MODULE_NAME, mm.DESC_TXT, mm.METADATA, mm.DELETED_FL FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=tcs.COURSE_ID inner join module_mstr mm on mm.MODULE_ID=cmm.MODULE_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId+"
             * INSERT INTO teacher_module_session_dtls(COURSE_SESSION_DTLS_ID, CONTENT_ID, START_SESSION_TM, END_SESSION_TM, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL) SELECT tcsd.COURSE_SESSION_DTLS_ID, rmstr.RESOURSE_ID, current_timestamp, null, '0', tc.TEACHER_ID, current_timestamp, rmstr.RESOURSE_NAME, rmstr.RESOURCE_AUTHOR, rmstr.RESOURCE_DURATION, rmstr.DESC_TXT, rmstr.RESOURCE_TYP_ID, rmstr.METADATA, rmstr.RESOURCE_URL, rmstr.AUTHOR_IMG, rmstr.THUMB_IMG, rmstr.DELETED_FL FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=hcm.COURSE_ID inner join module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID inner join resourse_mstr rmstr on rmstr.RESOURSE_ID=mrm.RESOURCE_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId+"  
             * INSERT INTO assignment_resource_txn(ASSIGNMENT_ID, STUDENT_ID, UPLODED_RESOURCE_ID, UPLOADED_ON, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, DUE_ON, ENABLE_FL, ASSIGNMENT_NAME, ASSIGNMENT_DESC_TXT, RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL, CANCEL_FL) SELECT ass.ASSIGNMENT_ID, tc.TEACHER_ID, null , null, '1', tc.TEACHER_ID, current_timestamp, null, '1', ass.ASSIGNMENT_NAME, ass.DESC_TXT, '', '', 0, '', 0, '', '', '', '', 0, 2 FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=hcm.COURSE_ID inner join module_assignment_map mam on mam.MODULE_ID=cmm.MODULE_ID inner join assignment ass on ass.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId+" 
             */
            boolean allDataInserted=false;
            try{
                String tcQuery="INSERT INTO teacher_courses(COURSE_ID, TEACHER_ID, CLASS_ID, SCHOOL_ID, HRM_ID, DISPLAY_NO, ENABLE_FL, CREATED_BY, LAST_USERID_CD, LAST_UPDT_TM)VALUES((SELECT COURSE_ID FROM hrm_course_map where HRM_ID="+hrmId+" limit 1), "+userid+", "+classId+", "+schoolId+", "+hrmId+", 0, '1', "+userid+", "+userid+", current_timestamp)";
            	int tcQueryCount=updateByQuery(tcQuery, conn);
            	if(tcQueryCount>0)
            	{
                    String tcsQuery="INSERT INTO teacher_course_sessions(TEACHER_COURSE_ID, START_SESSION_TM, END_SESSION_TM, STATUS_TXT, IS_COMPLETE, LAST_USERID_CD, LAST_UPDT_TM, COURSE_ID, COURSE_NAME, COURSE_AUTHOR, AUTHOR_IMG, COURSE_DURATION, CREATED_DT, DESC_TXT, METADATA, DELETED_FL, COURSE_ICON) SELECT tc.TEACHER_COURSE_ID, current_timestamp, null, '', '0', tc.TEACHER_ID, current_timestamp, cms.COURSE_ID, cms.COURSE_NAME, cms.COURSE_AUTHOR, cms.AUTHOR_IMG, cms.COURSE_DURATION, cms.CREATED_DT, cms.DESC_TXT, cms.METADATA, cms.DELETED_FL, cms.COURSE_ICON FROM teacher_courses tc inner join hrm_course_map hcm on hcm.HRM_ID=tc.HRM_ID and hcm.COURSE_ID=tc.COURSE_ID inner join course_mstr cms on cms.COURSE_ID=tc.COURSE_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.HRM_ID="+hrmId;
                    int tcsQueryCount=updateByQuery(tcsQuery,conn);
            		if(tcsQueryCount>0)
            		{
                        String tcsdtlQuery="INSERT INTO teacher_course_session_dtls(COURSE_SESSION_ID, TEACHER_ID, MODULE_ID, CONTENT_ID, START_SESSION_TM, END_SESSION_TM, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, MODULE_NAME, DESC_TXT, METADATA, DELETED_FL) SELECT tcs.COURSE_SESSION_ID, tc.TEACHER_ID, cmm.MODULE_ID, 0, current_timestamp, null, '0', tc.TEACHER_ID, current_timestamp, mm.MODULE_NAME, mm.DESC_TXT, mm.METADATA, mm.DELETED_FL FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=tcs.COURSE_ID inner join module_mstr mm on mm.MODULE_ID=cmm.MODULE_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId;
                        int tcsdtlQueryCount=updateByQuery(tcsdtlQuery, conn);
                        if(tcsdtlQueryCount>0)
                        {
                            String tcsModuleDtlQuery="INSERT INTO teacher_module_session_dtls(COURSE_SESSION_DTLS_ID, CONTENT_ID, START_SESSION_TM, END_SESSION_TM, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL) SELECT tcsd.COURSE_SESSION_DTLS_ID, rmstr.RESOURSE_ID, current_timestamp, null, '0', tc.TEACHER_ID, current_timestamp, rmstr.RESOURSE_NAME, rmstr.RESOURCE_AUTHOR, rmstr.RESOURCE_DURATION, rmstr.DESC_TXT, rmstr.RESOURCE_TYP_ID, rmstr.METADATA, rmstr.RESOURCE_URL, rmstr.AUTHOR_IMG, rmstr.THUMB_IMG, rmstr.DELETED_FL FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=hcm.COURSE_ID inner join module_resource_map mrm on mrm.MODULE_ID=cmm.MODULE_ID inner join resourse_mstr rmstr on rmstr.RESOURSE_ID=mrm.RESOURCE_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId;
                            int tcsModuleDtlQueryCount=updateByQuery(tcsModuleDtlQuery, conn);
                            if(tcsModuleDtlQueryCount>0)
                            {
                                String assgnmentQuery="INSERT INTO assignment_resource_txn(ASSIGNMENT_ID, STUDENT_ID, UPLODED_RESOURCE_ID, UPLOADED_ON, IS_COMPLETED, LAST_USERID_CD, LAST_UPDT_TM, DUE_ON, ENABLE_FL, ASSIGNMENT_NAME, ASSIGNMENT_DESC_TXT, RESOURSE_NAME, RESOURCE_AUTHOR, RESOURCE_DURATION, DESC_TXT, RESOURCE_TYP_ID, METADATA, RESOURCE_URL, AUTHOR_IMG, THUMB_IMG, DELETED_FL, CANCEL_FL) SELECT ass.ASSIGNMENT_ID, tc.TEACHER_ID, null , null, '1', tc.TEACHER_ID, current_timestamp, null, '1', ass.ASSIGNMENT_NAME, ass.DESC_TXT, '', '', 0, '', 0, '', '', '', '', 0, 2 FROM teacher_courses tc inner join teacher_course_sessions tcs on tc.TEACHER_COURSE_ID=tcs.TEACHER_COURSE_ID inner join teacher_course_session_dtls tcsd on tcs.COURSE_SESSION_ID=tcsd.COURSE_SESSION_ID inner join hrm_course_map hcm on hcm.COURSE_ID=tc.COURSE_ID inner join course_module_map cmm on cmm.COURSE_ID=hcm.COURSE_ID inner join module_assignment_map mam on mam.MODULE_ID=cmm.MODULE_ID inner join assignment ass on ass.ASSIGNMENT_ID=mam.ASSIGNMENT_ID where tc.TEACHER_ID="+userid+" and tc.SCHOOL_ID="+schoolId+" and tc.CLASS_ID="+classId+" and tc.HRM_ID="+hrmId;
                                int assgnmentQueryCount=updateByQuery(assgnmentQuery,conn);
                                System.out.println("Self derived course assignment added."+assgnmentQueryCount);
                                if(assgnmentQueryCount>0)
                                {
                                allDataInserted=true;
                                }
                            }
                        }
            		}
            	}
            	
            if(allDataInserted==false)
            {
            	throw new Exception("Master data not configured properly.");
            }
            }catch(Exception e){
            	throw new Exception(e.getMessage());
            }
            
	}
		
	
	
    
}//end of class
