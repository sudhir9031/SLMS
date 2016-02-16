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
import com.scolere.lms.domain.vo.AssignmentTypMstrVO;
import com.scolere.lms.persistance.dao.iface.AssignmentTypMstrDao;
import com.scolere.lms.persistance.factory.LmsDaoAbstract;

public class AssignmentTypMstrDaoImpl extends LmsDaoAbstract implements AssignmentTypMstrDao{
	
	Logger logger = LoggerFactory.getLogger(AssignmentTypMstrDaoImpl.class);

	@Override
	public boolean updateAssignmentTypMstr(AssignmentTypMstrVO vo) throws LmsDaoException {
		System.out.println("id =" + vo.getAssignmentTypID());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	
            conn = getConnection();
            String sql = "UPDATE assignment_typ_mstr set  ASSIGNMENT_TYP_NM=?\n"
                    + "    WHERE ASSIGNMENT_TYP_ID=?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(2, vo.getAssignmentTypID());
            stmt.setString(1, vo.getAssignmentTypNm());
           
            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("AssignmentTypMstr 1# " + e);
            throw new LmsDaoException(e.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentTypMstr 2# " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
	}

	@Override
	public void saveAssignmentTypMstr(AssignmentTypMstrVO vo) throws LmsDaoException {
		//Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	 
            conn = getConnection();
            String sql = "INSERT INTO assignment_typ_mstr(ASSIGNMENT_TYP_ID, ASSIGNMENT_TYP_NM)  VALUES( ?, ?)";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getAssignmentTypID());
            stmt.setString(2, vo.getAssignmentTypNm());
            
            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("AssignmentTypMstrVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("AssignmentTypMstrVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
	}

	@Override
	public boolean deleteAssignmentTypMstr(AssignmentTypMstrVO vo) throws LmsDaoException {
		logger.debug("id =" + vo.getAssignmentTypID());
	        boolean status = true;

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();
	          
	            String sql = "DELETE FROM assignment_typ_mstr WHERE ASSIGNMENT_TYP_ID = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, vo.getAssignmentTypID());
	            stmt.executeUpdate();

	           logger.debug("Deleted records into the table...");

	        } catch (SQLException se) {
	           logger.error("assignment_typ_mstr # " + se);
	            throw new LmsDaoException(se.getMessage());
	        } catch (Exception e) {
	           logger.error("assignment_typ_mstr # " + e);
	            throw new LmsDaoException(e.getMessage());
	        } finally {
	            closeResources(conn, stmt, null);
	        }

	       logger.debug("Successfully deleted....");
	        return status;
	}

	@Override
	public AssignmentTypMstrVO getAssignmentTypMstr(int id) throws LmsDaoException {
		System.out.println("Inside getAssignmentTypMstr(?) >>");
        //Create object to return
		AssignmentTypMstrVO assignmentTypMstrVO = new AssignmentTypMstrVO();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
          
            String sql = "SELECT * FROM assignment_typ_mstr where ASSIGNMENT_TYP_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, assignmentTypMstrVO.getAssignmentTypID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

            	assignmentTypMstrVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
            	assignmentTypMstrVO.setAssignmentTypNm(rs.getString("ASSIGNMENT_TYP_NM"));
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getAssignmentTypMstr # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("getAssignmentTypMstr # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return assignmentTypMstrVO;
	}

	@Override
	public List<AssignmentTypMstrVO> getAssignmentTypMstrList() throws LmsDaoException {
		List< AssignmentTypMstrVO> distList = new ArrayList<AssignmentTypMstrVO>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM assignment_typ_mstr ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	  /* INSERT INTO assignment_typ_mstr(ASSIGNMENT_TYP_ID, ASSIGNMENT_TYP_NM)
	            ASSIGNMENT_TYP_ID	int(11) NOT NULL,
	        	ASSIGNMENT_TYP_NM	varchar(40) NULL */
                //3. Set db data to object
            	AssignmentTypMstrVO assignmentTypMstrVO = new AssignmentTypMstrVO();

            	assignmentTypMstrVO.setAssignmentTypID(rs.getInt("ASSIGNMENT_TYP_ID"));
            	assignmentTypMstrVO.setAssignmentTypNm(rs.getString("ASSIGNMENT_TYP_NM"));
                              //Add into list
                distList.add(assignmentTypMstrVO);
            }

           logger.debug("get records into the list...");

        } catch (SQLException se) {
           logger.error("assignmentTypMstrVO # " + se);
            throw new LmsDaoException(se.getMessage());
        } catch (Exception e) {
           logger.error("assignmentTypMstrVO # " + e);
            throw new LmsDaoException(e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
     //1 . jdbc code endd

        //4 Return as required by method
        return distList;

	}

}
