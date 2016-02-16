/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.cross.NotificationVO;
import com.scolere.lms.persistance.dao.iface.NotificationDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;



/**
 *
 * @author dell
 */
public class NotificationDaoImpl extends LmsDaoAbstract implements NotificationDao{
	
	Logger logger = LoggerFactory.getLogger(NotificationDaoImpl.class);

	@Override
	public int saveUserDevice(NotificationVO vo) throws LmsDaoException {
        int count=0;
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            String sql = "INSERT INTO user_devices(USER_ID, DEVICE_TYPE, DEVICE_TOKEN)VALUES(?, ?,?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getUserId());
            stmt.setString(2, vo.getDeviceType());
            stmt.setString(3, vo.getDeviceToken());

            count=stmt.executeUpdate();
           logger.debug("Inserted records into the table..."+count);
            
        } catch (SQLException se) {
           logger.error("saveUserDevice # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("saveUserDevice # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return count;
	}

	
	@Override
	public boolean isDeviceRegistered(NotificationVO vo) throws LmsDaoException {
		boolean status = false;
		
		String query="SELECT count(*) FROM user_devices where USER_ID="+vo.getUserId()+" and DEVICE_TYPE='"+vo.getDeviceType()+"' and DEVICE_TOKEN='"+vo.getDeviceToken()+"'";
		int count = getCountQueryResult(query);
		if(count>0)
			status=true;
		
		return status;
	}


	@Override
	public List<NotificationVO> getUserDevicesList(String userStr) throws LmsServiceException {
		List<NotificationVO> list = new ArrayList<NotificationVO>();

		Connection conn = null;
		PreparedStatement cstmt = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection(dataSource);

			String query="SELECT USER_ID,DEVICE_TYPE,DEVICE_TOKEN FROM user_devices where USER_ID in ("+userStr+")";
			System.out.println("Query : " + query);
			cstmt = conn.prepareStatement(query);
			
			rs = cstmt.executeQuery();

			NotificationVO vo = null;
			while (rs.next()) {
				vo = new NotificationVO();
				vo.setUserId(rs.getInt(1));
				vo.setDeviceType(rs.getString(2));
				vo.setDeviceToken(rs.getString(3));
				
				list.add(vo);
			}

			logger.debug("No of Object returned : " + list.size());

		} catch (Exception e) {
			logger.error("Error > getUserDevicesList - " + e.getMessage());
		} finally {
			closeResources(conn, cstmt, rs);
		}

		return list;
	}


	@Override
	public List<String> getUserDevicesTokens(String userStr)
			throws LmsServiceException {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		 
		Connection conn = null;
		PreparedStatement cstmt = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection(dataSource);

			String query="SELECT DEVICE_TOKEN FROM user_devices where USER_ID in (?)";
			System.out.println("Query : " + query);
			cstmt = conn.prepareStatement(query);
			cstmt.setString(1, userStr);
			
			rs = cstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

			logger.debug("No of Object returned : " + list.size());

		} catch (Exception e) {
			logger.error("Error > getUserDevicesTokens - " + e.getMessage());
		} finally {
			closeResources(conn, cstmt, rs);
		}

		return list;
	}

    
}//End of class
