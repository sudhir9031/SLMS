package com.scolere.lms.persistance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.cross.UserVO;
import com.scolere.lms.persistance.dao.iface.ActivityDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import com.scolere.lms.service.impl.CommonServiceImpl;


public class ActivityDaoImpl extends LmsDaoAbstract implements ActivityDao {
	Logger logger = LoggerFactory.getLogger(ActivityDaoImpl.class);
	
	@Override
	public void saveActivity(String userNm, int activityId) throws LmsDaoException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO activity_txn(USER_ID, ACTIVITY_ID, LAST_UPDT_TM)VALUES((SELECT USER_ID FROM user_login where USER_NM='"+userNm+"'), "+activityId+",utc_timestamp)";
            stmt = conn.prepareStatement(sql);
            
            stmt.executeUpdate();
            
        }catch (Exception e) {
           logger.error("saveActivity #2 " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

    }
	
	
	@Override
	public void saveActivity(int userId, int activityId) throws LmsDaoException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO activity_txn(USER_ID, ACTIVITY_ID, LAST_UPDT_TM)VALUES(?, ?,utc_timestamp)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.setInt(2, activityId);
            
            stmt.executeUpdate();
            
        }catch (Exception e) {
           logger.error("saveActivity #1 " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

    }
		

	@Override
	public List<UserVO> getMostActivUsers() throws LmsDaoException {
			List<UserVO> list = new ArrayList<UserVO>();

			Connection conn = null;
			PreparedStatement cstmt = null;
			ResultSet resultSet = null;

			try {
				String query="SELECT txn.USER_ID,concat(sdtl.FNAME,' ',sdtl.LNAME),PROFILE_IMG,count(ACTIVITY_ID)as activity_count FROM activity_txn txn left join student_dtls sdtl on txn.USER_ID=sdtl.USER_ID group by USER_ID order by activity_count desc limit 3";
				System.out.println("Query : " + query);

				conn = this.getConnection();
				cstmt = conn.prepareStatement(query);
				resultSet = cstmt.executeQuery();

				UserVO userVo=null;
				while (resultSet.next()) {
					//userId  userName  userImg  activityCount
					userVo=new UserVO(); 
					
					userVo.setUserId(resultSet.getInt(1));
					userVo.setUserName(resultSet.getString(2));
					userVo.setProfileImage(resultSet.getString(3));
					userVo.setActivityCount(resultSet.getInt(4));
					
					list.add(userVo);
				}

			} catch (Exception e) {
				logger.error("Error > getMostActivUsers - " + e.getMessage());
				throw new LmsDaoException(e.getMessage());
			} finally {
				closeResources(conn, cstmt, resultSet);
			}

			return list;
	}

		
} //End of class
