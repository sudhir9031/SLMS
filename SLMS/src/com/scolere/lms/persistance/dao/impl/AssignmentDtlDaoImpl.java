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
import com.scolere.lms.domain.vo.AssignmentDtlVO;
import com.scolere.lms.persistance.dao.iface.AssignmentDtlDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

public class AssignmentDtlDaoImpl extends LmsDaoAbstract implements AssignmentDtlDao {
	
	Logger logger = LoggerFactory.getLogger(AssignmentDaoImpl.class);

    @Override
    public boolean updateAssignmentDtl(AssignmentDtlVO vo) throws LmsDaoException {

       logger.debug("id =" + vo.getAssignmentDtlID());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE assignment_dtl set  ASSIGNMENT_ID=?, ASSIGNMENT_TYP_ID=?, DISPLAY_NO=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE ASSIGNMENT_DTL_ID=?";
            /* String sql = "UPDATE assignment_dtl set  ASSIGNMENT_ID=?, ASSIGNMENT_TYP_ID=?, DISPLAY_NO=?, LAST_USERID_CD=?\n"
             + "    WHERE ASSIGNMENT_DTL_ID=?";*/
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getAssignmentID());
            stmt.setInt(2, vo.getAssignmentTypID());
            stmt.setInt(3, vo.getDisplayNo());
            stmt.setString(4, vo.getLastUserIDCd());
            stmt.setInt(5, vo.getAssignmentDtlID());
            //stmt.setString(5, vo.getLastUpdtTm());
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getAssignmentDtlID 1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentDtlID 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
    }

    @Override
    public void saveAssignmentDtl(AssignmentDtlVO vo) throws LmsDaoException {

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO assignment_dtl(ASSIGNMENT_DTL_ID, ASSIGNMENT_ID, ASSIGNMENT_TYP_ID, "
                    + "DISPLAY_NO, LAST_USERID_CD, LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, utc_timestamp)";
            /*String sql = "INSERT INTO assignment_dtl(ASSIGNMENT_DTL_ID, ASSIGNMENT_ID, ASSIGNMENT_TYP_ID," +
             " DISPLAY_NO, LAST_USERID_CD)  VALUES(?, ?, ?, ?, ?)";*/

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getAssignmentDtlID());
            stmt.setInt(2, vo.getAssignmentID());
            stmt.setInt(3, vo.getAssignmentTypID());
            stmt.setInt(4, vo.getDisplayNo());
            stmt.setString(5, vo.getLastUserIDCd());
            //stmt.setString(6, vo.getLastUpdtTm());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("AssignmentDtlVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentDtlVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    @Override
    public boolean deleteAssignmentDtl(AssignmentDtlVO vo) throws LmsDaoException {

       logger.debug("id =" + vo.getAssignmentDtlID());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM assignment_dtl WHERE ASSIGNMENT_DTL_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getAssignmentDtlID());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("ASSIGNMENT_DTL # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("ASSIGNMENT_DTL # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public AssignmentDtlVO getAssignmentDtl(int id) throws LmsDaoException {

       logger.debug("Inside AssignmentDtl(?) >>");
        //Create object to return
        AssignmentDtlVO assignmentDtlVO = new AssignmentDtlVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment_dtl where ASSIGNMENT_DTL_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentDtlVO.getAssignmentDtlID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                assignmentDtlVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
                assignmentDtlVO.setAssignmentID(rs.getInt("ASSIGNMENT_ID"));
                assignmentDtlVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
                assignmentDtlVO.setDisplayNo(rs.getInt("DISPLAY_NO"));
                assignmentDtlVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
                assignmentDtlVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("AssignmentDtl # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentDtl # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return assignmentDtlVO;
    }

    @Override
    public List<AssignmentDtlVO> getAssignmentDtlList() throws LmsDaoException {
        List< AssignmentDtlVO> distList = new ArrayList<AssignmentDtlVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment_dtl ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                AssignmentDtlVO assignmentDtlVO = new AssignmentDtlVO();

                assignmentDtlVO.setAssignmentDtlID(rs.getInt("ASSIGNMENT_DTL_ID"));
                assignmentDtlVO.setAssignmentID(rs.getInt("ASSIGNMENT_ID"));
                assignmentDtlVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
                assignmentDtlVO.setDisplayNo(rs.getInt("DISPLAY_NO"));
                assignmentDtlVO.setLastUserIDCd(rs.getString("LAST_USERID_CD"));
                assignmentDtlVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));

                //Add into list
                distList.add(assignmentDtlVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("assignmentDtl # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("assignmentDtl # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd
        //4 Return as required by method
        return distList;
    }
}
