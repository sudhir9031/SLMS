/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.impl;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.ModuleVedioMapVo;
import com.scolere.lms.persistance.dao.iface.ModuleVedioMapDao;
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
public class ModuleVedioMapDaoImpl extends LmsDaoAbstract implements ModuleVedioMapDao {
	
	Logger logger = LoggerFactory.getLogger(ModuleVedioMapDaoImpl.class);

    public ModuleVedioMapVo getModuleVedioMapDetail(int id) throws LmsDaoException {
    	
       logger.debug("Inside getUserCourseMapDetail(?) >>");
        //Create object to return
        ModuleVedioMapVo userDtls = new ModuleVedioMapVo();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM MODULE_VIDEO_MAP where VEDIO_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtls.getVedioId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //3. Set db data to object

                userDtls.setVedioId(rs.getInt("VEDIO_ID"));
                userDtls.setModuleVedioMap(rs.getInt("MODULE_VEDIO_MAP"));

            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return userDtls;
    }

    public boolean updateModuleVedioMapDetail(ModuleVedioMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getVedioId());
        boolean status = true;

        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "UPDATE module_video_map set VEDIO_ID=?\n"
                    + "    WHERE MODULE_VEDIO_MAP=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getVedioId());
            stmt.setInt(2, vo.getModuleVedioMap());

            stmt.executeUpdate();
           logger.debug("updated records into the table...");

        } catch (SQLException e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully updated....");
        return status;
        //End writting code to save into database   
    }
    //save method

    public void saveModuleVedioMapDetail(ModuleVedioMapVo vo) throws LmsDaoException {
        //Database connection start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            String sql = "INSERT INTO module_video_map(VEDIO_ID, MODULE_VEDIO_MAP)  VALUES(?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getVedioId());
            stmt.setInt(2, vo.getModuleVedioMap());

            //...
            stmt.executeUpdate();
           logger.debug("Inserted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully saved....");
    }

    //delete method
    public boolean deleteModuleVedioMapDetail(ModuleVedioMapVo vo) throws LmsDaoException {
       logger.debug("id =" + vo.getVedioId());
        boolean status = true;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM MODULE_VIDEO_MAP WHERE VEDIO_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vo.getVedioId());
            stmt.executeUpdate();

           logger.debug("Deleted records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }

       logger.debug("Successfully deleted....");
        return status;
    }

    public List< ModuleVedioMapVo> getModuleVedioMapVoList() throws LmsDaoException {
        List<ModuleVedioMapVo> distList = new ArrayList<ModuleVedioMapVo>();

        //1 . jdbc code start
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM MODULE_VIDEO_MAP ";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                //3. Set db data to object
                ModuleVedioMapVo userDtls = new ModuleVedioMapVo();

                userDtls.setVedioId(rs.getInt("VEDIO_ID"));
                userDtls.setModuleVedioMap(rs.getInt("MODULE_VEDIO_MAP"));

                //Add into list
                distList.add(userDtls);
            }

           logger.debug("get records into the table...");

        } catch (SQLException se) {
           logger.error("getUserCourseMapDetail # " + se);
            se.printStackTrace();
        } catch (Exception e) {
           logger.error("getUserCourseMapDetail # " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        //1 . jdbc code endd

        //4 Return as required by method
        return distList;

    }

    @Override
    public List<ModuleVedioMapVo> getModuleVedioMapList() throws LmsDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
