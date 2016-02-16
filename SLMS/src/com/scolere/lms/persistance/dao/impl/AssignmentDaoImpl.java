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
import com.scolere.lms.domain.vo.AssignmentVO;
import com.scolere.lms.persistance.dao.iface.AssignmentDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;
import com.scolere.lms.service.impl.CommonServiceImpl;


public class AssignmentDaoImpl extends LmsDaoAbstract implements AssignmentDao {

	Logger logger = LoggerFactory.getLogger(AssignmentDaoImpl.class);
	
    @Override
    public boolean updateAssignment(AssignmentVO vo) throws LmsDaoException {

       logger.debug("id =" + vo.getAssignmentID());
        //System.out.println("id =" +1234);

        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE assignment set ASSIGNMENT_NAME=?, ASSIGNMENT_TYP_ID=?, DESC_TXT=?, DISPLAY_NO=?, ENABLE_FL=?, LAST_USERID_CD=?, LAST_UPDT_TM=utc_timestamp\n"
                    + "    WHERE ASSIGNMENT_ID=?";
            /* String sql = "UPDATE assignment set ASSIGNMENT_NAME=?, ASSIGNMENT_TYP_ID=?, DESC_TXT=?, DISPLAY_NO=?, ENABLE_FL=?, LAST_USERID_CD=?\n"
             + "    WHERE ASSIGNMENT_ID=?";*/
            stmt = conn.prepareStatement(sql);


            stmt.setString(1, vo.getAssignmentName());
            stmt.setInt(2, vo.getAssignmentTypID());
            stmt.setString(3, vo.getDescTxt());
            stmt.setInt(4, vo.getDisplayNo());
            stmt.setString(5, vo.getEnableFl());
            stmt.setString(6, vo.getLastUserIDCD());
            stmt.setInt(7, vo.getAssignmentID());
            // stmt.setString(7, vo.getLastUpdtTm());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getAssignment SQLException1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("getAssignment Exception 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;

    }

    @Override
    public void saveAssignment(AssignmentVO vo) throws LmsDaoException {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            /* String sql = "INSERT INTO assignment(ASSIGNMENT_ID, ASSIGNMENT_NAME, ASSIGNMENT_TYP_ID, DESC_TXT, DISPLAY_NO," +
             " ENABLE_FL, LAST_USERID_CD)  VALUES(?, ?, ?, ?, ?, ?, ?)";*/
            String sql = "INSERT INTO assignment(ASSIGNMENT_ID, ASSIGNMENT_NAME, ASSIGNMENT_TYP_ID, DESC_TXT, "
                    + "DISPLAY_NO, ENABLE_FL, LAST_USERID_CD, LAST_UPDT_TM)  VALUES(?, ?, ?, ?, ?, ?, ?, utc_timestamp)";


            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vo.getAssignmentID());
            stmt.setString(2, vo.getAssignmentName());
            stmt.setInt(3, vo.getAssignmentTypID());
            stmt.setString(4, vo.getDescTxt());
            stmt.setInt(5, vo.getDisplayNo());
            stmt.setString(6, vo.getEnableFl());
            stmt.setString(7, vo.getLastUserIDCD());


            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");

    }

    @Override
    public boolean deleteAssignment(AssignmentVO vo) throws LmsDaoException {

       logger.debug("id =" + vo.getAssignmentID());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM assignment WHERE ASSIGNMENT_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getAssignmentID());
            stmt.executeUpdate();

           logger.debug("Deleted records into the ASSIGNMENT table...");

        } catch (SQLException se) {
           logger.error("ASSIGNMENT # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("ASSIGNMENT # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    @Override
    public AssignmentVO getAssignment(int id) throws LmsDaoException {
       logger.debug("Inside getAssignment(?) >>");
        //Create object to return
        AssignmentVO assignmentVO = new AssignmentVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment where ASSIGNMENT_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentVO.getAssignmentID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object
                assignmentVO.setAssignmentID(rs.getInt("ASSIGNMENT_ID"));
                assignmentVO.setAssignmentName(rs.getString("ASSIGNMENT_NAME"));
                assignmentVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
                assignmentVO.setDescTxt(rs.getString("DESC_TXT"));
                assignmentVO.setDisplayNo(rs.getInt("DISPLAY_NO"));
                assignmentVO.setEnableFl(rs.getString("ENABLE_FL"));
                assignmentVO.setLastUserIDCD(rs.getString("LAST_USERID_CD"));
                assignmentVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return assignmentVO;
    }

    @Override
    public List<AssignmentVO> getAssignmentList() throws LmsDaoException {

        List< AssignmentVO> distList = new ArrayList<AssignmentVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                AssignmentVO assignmentVO = new AssignmentVO();

                assignmentVO.setAssignmentID(rs.getInt("ASSIGNMENT_ID"));
                assignmentVO.setAssignmentName(rs.getString("ASSIGNMENT_NAME"));
                assignmentVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
                assignmentVO.setDescTxt(rs.getString("DESC_TXT"));
                assignmentVO.setDisplayNo(rs.getInt("DISPLAY_NO"));
                assignmentVO.setEnableFl(rs.getString("ENABLE_FL"));
                assignmentVO.setLastUserIDCD(rs.getString("LAST_USERID_CD"));
                assignmentVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));


                //Add into list
                distList.add(assignmentVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("assignment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("assignment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;
    }

	@Override
	public List<AssignmentVO> getAssignmentList(int moduleMasterId,
			int homeRoomMstrId, int classId, int schoolId, int teacherId)
			throws LmsDaoException {
        //Create object to return
		  List< AssignmentVO> distList = new ArrayList<AssignmentVO>();
        AssignmentVO assignmentVO = new AssignmentVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment where ASSIGNMENT_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentVO.getAssignmentID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object
                assignmentVO.setAssignmentID(rs.getInt("ASSIGNMENT_ID"));
                assignmentVO.setAssignmentName(rs.getString("ASSIGNMENT_NAME"));
                assignmentVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
                assignmentVO.setDescTxt(rs.getString("DESC_TXT"));
                assignmentVO.setDisplayNo(rs.getInt("DISPLAY_NO"));
                assignmentVO.setEnableFl(rs.getString("ENABLE_FL"));
                assignmentVO.setLastUserIDCD(rs.getString("LAST_USERID_CD"));
                assignmentVO.setLastUpdtTm(rs.getString("LAST_UPDT_TM"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignment # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignment # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;
	}

	 
}
