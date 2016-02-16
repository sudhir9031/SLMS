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
import com.scolere.lms.domain.vo.LmsFeedTxnVO;
import com.scolere.lms.persistance.dao.iface.LmsFeedTxnDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

public class LmsFeedTxnDaoImpl extends LmsDaoAbstract implements LmsFeedTxnDao{
	
	Logger logger = LoggerFactory.getLogger(LmsFeedTxnDaoImpl.class);

	@Override
	public boolean updateLmsFeedTxn(LmsFeedTxnVO vo) throws LmsDaoException {
		logger.debug("id =" + vo.getFeedID());
		 //System.out.println("id =" +1234);
		 
		 boolean status = true;

	        //Database connection start
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	        	
	            conn = getConnection();
	            String sql = "UPDATE lms_feed_txn set FEED_TYPE_ID=?, RESOURCE_ID=?, FEED_ON=?, " +
	            		"LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
	                    + "    WHERE FEED_ID=?";
	           /* String sql = "UPDATE lms_feed_txn set FEED_TYPE_ID=?, RESOURCE_ID=?, FEED_ON=?, LAST_USERID_CD=?\n"
	                    + "    WHERE FEED_ID=?";*/
	            stmt = conn.prepareStatement(sql);
	            	            
	            stmt.setInt(1, vo.getFeedTypeID());
	            stmt.setInt(2, vo.getResourceID());
	            stmt.setString(3, vo.getFeedOn());
	            stmt.setString(4, vo.getLastUserIDCd());
	            stmt.setInt(5, vo.getFeedID());
	           // stmt.setString(7, vo.getLastUpdtTm());
	            
	            stmt.executeUpdate();
	           logger.debug("updated records into the table...");

	        } catch (SQLException e) {
	           logger.error("lms_feed_txn SQLException1# " + e);
	            throw new LmsDaoException(e.getMessage());
	        } catch (Exception e) {
	           logger.error("lms_feed_txn Exception 2# " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, null);
	        }

	       logger.debug("Successfully updated....");
	        return status;
	}

	@Override
	public void saveLmsFeedTxn(LmsFeedTxnVO vo) throws LmsDaoException {
		//Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            /*String sql = "INSERT INTO lms_feed_txn(FEED_ID, FEED_TYPE_ID, RESOURCE_ID, FEED_ON, LAST_USERID_CD)" +
            		"  VALUES(?, ?, ?, ?, ?)";*/
            String sql = "INSERT INTO lms_feed_txn(FEED_ID, FEED_TYPE_ID, RESOURCE_ID, FEED_ON, LAST_USERID_CD," +
            		" LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, utc_timestamp)";
            
            
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, vo.getFeedID());
            stmt.setInt(2, vo.getFeedTypeID());
            stmt.setInt(3, vo.getResourceID());
            stmt.setString(4, vo.getFeedOn());
            stmt.setString(5, vo.getLastUserIDCd());
          /*  stmt.setString(8, vo.getLastUpdtTm());*/
          
            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("LmsFeedTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("LmsFeedTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
		
	}

	@Override
	public boolean deleteLmsFeedTxn(LmsFeedTxnVO vo) throws LmsDaoException {
		logger.debug("id =" + vo.getFeedID());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
           
            String sql = "DELETE FROM lms_feed_txn WHERE FEED_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getFeedID());
            stmt.executeUpdate();

           logger.debug("Deleted records into the lms_feed_txn table...");

        } catch (SQLException se) {
           logger.error("LmsFeedTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("LmsFeedTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
	}

	@Override
	public LmsFeedTxnVO getLmsFeedTxn(int id) throws LmsDaoException {
		logger.debug("Inside getAssignment(?) >>");
        //Create object to return
		LmsFeedTxnVO lmsFeedTxnVO = new LmsFeedTxnVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
           
            String sql = "SELECT * FROM lms_feed_txn where FEED_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lmsFeedTxnVO.getFeedID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object
            	lmsFeedTxnVO.setFeedID(rs.getInt("FEED_ID"));
            	lmsFeedTxnVO.setFeedTypeID(rs.getInt("FEED_TYPE_ID"));
            	lmsFeedTxnVO.setResourceID(rs.getInt("RESOURCE_ID"));
            	lmsFeedTxnVO.setFeedOn(rs.getString("FEED_ON"));
            	lmsFeedTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
            	lmsFeedTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));       
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("LmsFeedTxnVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("LmsFeedTxnVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return lmsFeedTxnVO;
	}

	@Override
	public List<LmsFeedTxnVO> getLmsFeedTxnList() throws LmsDaoException {
		 List< LmsFeedTxnVO> distList = new ArrayList<LmsFeedTxnVO>();

	        //1 . jdbc code start
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();

	            String sql = "SELECT * FROM lms_feed_txn ";
	            stmt = conn.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	            	
	                //3. Set db data to object
	            	LmsFeedTxnVO lmsFeedTxnVO = new LmsFeedTxnVO();

	            	lmsFeedTxnVO.setFeedID(rs.getInt("FEED_ID"));
	            	lmsFeedTxnVO.setResourceID(rs.getInt("RESOURCE_ID"));
	            	lmsFeedTxnVO.setFeedOn(rs.getString("FEED_ON"));
	            	lmsFeedTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
	            	lmsFeedTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));


	                //Add into list
	                distList.add(lmsFeedTxnVO);
	            }

	           logger.debug("get records into the list...");

	        } catch (SQLException se) {
	           logger.error("LmsFeedTxnVO # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("LmsFeedTxnVO # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, null);
	        }
	     //1 . jdbc code endd

	        //4 Return as required by method
	        return distList;
	
	}

}
