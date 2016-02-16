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
import com.scolere.lms.domain.vo.AssignmentVdoTxnVO;
import com.scolere.lms.persistance.dao.iface.AssignmentVdoTxnDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

public class AssignmentVdoTxnDaoImpl extends LmsDaoAbstract implements AssignmentVdoTxnDao{
	
	Logger logger = LoggerFactory.getLogger(AssignmentVdoTxnDaoImpl.class);

	@Override
	public boolean updateAssignmentVdoTxn(AssignmentVdoTxnVO vo) throws LmsDaoException{
		System.out.println("id =" + vo.getAssignmentDtlID());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	
            conn = getConnection();
            String sql = "UPDATE assignment_vdo_txn set  ASSIGNMENT_DTL_ID=?, STUDENT_ID=?, UPLODED_VDO_PATH=?, " +
            		"LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE VDO_TXN_ID=?";
            /*String sql = "UPDATE assignment_vdo_txn set  ASSIGNMENT_DTL_ID=?, STUDENT_ID=?, UPLODED_VDO_PATH=?, LAST_USERID_CD=?\n"
                    + "    WHERE VDO_TXN_ID=?";*/
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getAssignmentDtlID());
            stmt.setString(2, vo.getStudentID());
            stmt.setString(3, vo.getUplodedVdoPath());
            stmt.setString(4, vo.getLastUserIDCd());
            stmt.setInt(5, vo.getVdoTxnID());
            //stmt.setString(5, vo.getLastUpdtTm());
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("AssignmentVdoTxn 1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentVdoTxn 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
	}

	@Override
	public void saveAssignmentVdoTxn(AssignmentVdoTxnVO vo) throws LmsDaoException{
		//Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	
            conn = getConnection();
            String sql = "INSERT INTO assignment_vdo_txn(VDO_TXN_ID, ASSIGNMENT_DTL_ID, STUDENT_ID, " +
            		"UPLODED_VDO_PATH, LAST_USERID_CD, LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, utc_timestamp)";
            /*String sql = "INSERT INTO assignment_vdo_txn(VDO_TXN_ID, ASSIGNMENT_DTL_ID, STUDENT_ID, " +
            		"UPLODED_VDO_PATH, LAST_USERID_CD)  VALUES(?, ?, ?, ?, ?)";*/
            
            stmt=conn.prepareStatement(sql);
            
            stmt.setInt(1, vo.getVdoTxnID());
            stmt.setInt(2, vo.getAssignmentDtlID());
            stmt.setString(3, vo.getStudentID());
            stmt.setString(4, vo.getUplodedVdoPath());
            stmt.setString(5, vo.getLastUserIDCd());
            //stmt.setString(6, vo.getLastUpdtTm());
            
            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("AssignmentVdoTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentVdoTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
		
	}

	@Override
	public boolean deleteAssignmentVdoTxn(AssignmentVdoTxnVO vo) throws LmsDaoException {
		logger.debug("id =" + vo.getVdoTxnID());
	        boolean status = true;

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();
	            
	            String sql = "DELETE FROM assignment_vdo_txn WHERE VDO_TXN_ID = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, vo.getVdoTxnID());
	            stmt.executeUpdate();

	           logger.debug("Deleted records into the table...");

	        } catch (SQLException se) {
	           logger.error("assignment_vdo_txn # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("assignment_vdo_txn # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, null);
	        }

	       logger.debug("Successfully deleted....");
	        return status;
	}

	@Override
	public AssignmentVdoTxnVO getAssignmentVdoTxn(int id) throws LmsDaoException{
		System.out.println("Inside getAssignmentVdoTxn(?) >>");
        //Create object to return
		AssignmentVdoTxnVO assignmentVdoTxnVO = new AssignmentVdoTxnVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
           
            String sql = "SELECT * FROM assignment_vdo_txn where VDO_TXN_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentVdoTxnVO.getVdoTxnID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

            	assignmentVdoTxnVO.setVdoTxnID(rs.getInt("VDO_TXN_ID"));
            	assignmentVdoTxnVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
            	assignmentVdoTxnVO.setStudentID(rs.getString("STUDENT_ID"));
            	assignmentVdoTxnVO.setUplodedVdoPath(rs.getString("UPLODED_VDO_PATH"));
                assignmentVdoTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
                assignmentVdoTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignmentVdoTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentVdoTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return assignmentVdoTxnVO;
	}

	@Override
	public List<AssignmentVdoTxnVO> getAssignmentVdoTxnList() throws LmsDaoException{
		List< AssignmentVdoTxnVO> distList = new ArrayList<AssignmentVdoTxnVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment_vdo_txn ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	
                //3. Set db data to object
            	AssignmentVdoTxnVO assignmentVdoTxnVO = new AssignmentVdoTxnVO();

            	assignmentVdoTxnVO.setVdoTxnID(rs.getInt("VDO_TXN_ID"));
            	assignmentVdoTxnVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
            	assignmentVdoTxnVO.setStudentID(rs.getString("STUDENT_ID"));
            	assignmentVdoTxnVO.setUplodedVdoPath(rs.getString("UPLODED_VDO_PATH"));
            	assignmentVdoTxnVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
            	assignmentVdoTxnVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));


                //Add into list
                distList.add(assignmentVdoTxnVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("assignmentVdoTxn # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("assignmentVdoTxn # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;

	}

}
