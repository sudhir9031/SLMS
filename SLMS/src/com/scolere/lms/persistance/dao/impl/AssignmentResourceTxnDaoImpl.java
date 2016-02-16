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
import com.scolere.lms.domain.vo.AssignmentResourceTxnVO;
import com.scolere.lms.persistance.dao.iface.AssignmentResourceTxnDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

public class AssignmentResourceTxnDaoImpl extends LmsDaoAbstract implements AssignmentResourceTxnDao {

	
	Logger logger = LoggerFactory.getLogger(AssignmentResourceTxnDaoImpl.class);
	@Override
	public boolean updateAssignmentResourceTxn(AssignmentResourceTxnVO vo) throws LmsDaoException {
		System.out.println("id =" + vo.getAssignmentDtlID());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	
            conn = getConnection();
           String sql = "UPDATE assignment_resource_txn set  ASSIGNMENT_DTL_ID=?, STUDENT_ID=?, UPLODED_RESOURCE_PATH=?, RESOURCE_TYPE_ID=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE RESOURCE_TXN_ID=?";
            /*String sql = "UPDATE assignment_resource_txn set  ASSIGNMENT_DTL_ID=?, STUDENT_ID=?, UPLODED_RESOURCE_PATH=?, RESOURCE_TYPE_ID=?, LAST_USERID_CD=?\n"
                    + "    WHERE RESOURCE_TXN_ID=?";*/
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getAssignmentDtlID());
            stmt.setString(2, vo.getStudentID());
            stmt.setString(3, vo.getUplodedResourcePath());
            stmt.setInt(4, vo.getResourceTypeID());           
            stmt.setString(5, vo.getLastUserIDCd());
            stmt.setInt(6, vo.getResourceTxnID());
           // stmt.setString(6, vo.getLastUpdtTm());
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("AssignmentResourceTxnVO 1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentResourceTxnVO 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
	}

	@Override
	public void saveAssignmentResourceTxn(AssignmentResourceTxnVO vo) throws LmsDaoException {
		
		//Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO assignment_resource_txn(RESOURCE_TXN_ID, ASSIGNMENT_DTL_ID, STUDENT_ID, UPLODED_RESOURCE_PATH, RESOURCE_TYPE_ID," +
            		" LAST_USERID_CD, LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, ?, utc_timestamp)";
           
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getResourceTxnID());
            stmt.setInt(2, vo.getAssignmentDtlID());
            stmt.setString(3, vo.getStudentID());
            stmt.setString(4, vo.getUplodedResourcePath());
            stmt.setInt(5, vo.getResourceTypeID());
            stmt.setString(6, vo.getLastUserIDCd());
            //stmt.setString(7, vo.getLastUpdtTm());
            
            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("AssignmentResourceTxnVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentResourceTxnVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved AssignmentResourceTxnVO....");
	}

	@Override
	public boolean deleteAssignmentResourceTxn(AssignmentResourceTxnVO vo) throws LmsDaoException {
		logger.debug("id =" + vo.getResourceTxnID());
	        boolean status = true;

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();
	           
	            String sql = "DELETE FROM assignment_resource_txn WHERE RESOURCE_TXN_ID = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, vo.getResourceTxnID());
	            stmt.executeUpdate();

	           logger.debug("Deleted records into the table...");

	        } catch (SQLException se) {
	           logger.error("AssignmentResourceTxnVO # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("AssignmentResourceTxnVO # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, null);
	        }

	       logger.debug("Successfully deleted....");
	        return status;
	}

	@Override
	public AssignmentResourceTxnVO getAssignmentResourceTxn(int id) throws LmsDaoException {
		System.out.println("Inside getAssignmentResourceTxn(?) >>");
        //Create object to return
		AssignmentResourceTxnVO assignmentResourceTxnVO = new AssignmentResourceTxnVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();


            String sql = "SELECT * FROM assignment_resource_txn where RESOURCE_TXN_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentResourceTxnVO.getResourceTxnID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

            	assignmentResourceTxnVO.setResourceTxnID(rs.getInt("RESOURCE_TXN_ID"));
            	assignmentResourceTxnVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
            	assignmentResourceTxnVO.setStudentID(rs.getString("STUDENT_ID"));
            	assignmentResourceTxnVO.setUplodedResourcePath(rs.getString("UPLODED_RESOURCE_PATH"));
            	assignmentResourceTxnVO.setResourceTypeID(rs.getInt("RESOURCE_TYPE_ID"));
            	assignmentResourceTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
            	assignmentResourceTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignmentResourceTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentResourceTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return assignmentResourceTxnVO;
	}

	@Override
	public List<AssignmentResourceTxnVO> getAssignmentResourceTxnList() throws LmsDaoException {
		List< AssignmentResourceTxnVO> distList = new ArrayList<AssignmentResourceTxnVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment_resource_txn ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
            	AssignmentResourceTxnVO assignmentResourceTxnVO = new AssignmentResourceTxnVO();
                
            	assignmentResourceTxnVO.setResourceTxnID(rs.getInt("RESOURCE_TXN_ID"));
            	assignmentResourceTxnVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
            	assignmentResourceTxnVO.setStudentID(rs.getString("STUDENT_ID"));
            	assignmentResourceTxnVO.setUplodedResourcePath(rs.getString("UPLODED_RESOURCE_PATH"));
            	assignmentResourceTxnVO.setResourceTypeID(rs.getInt("RESOURCE_TYPE_ID"));
            	assignmentResourceTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
            	assignmentResourceTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));


                //Add into list
                distList.add(assignmentResourceTxnVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("assignmentResourceTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("assignmentResourceTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;

	}

}
