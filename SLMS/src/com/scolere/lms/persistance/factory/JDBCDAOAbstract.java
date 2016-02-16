/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scolere.lms.persistance.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.service.impl.TeacherServiceImpl;

/**
 *
 * @author anilk
 */
public class JDBCDAOAbstract {
	
	Logger logger = LoggerFactory.getLogger(JDBCDAOAbstract.class);

    private Connection conn=null;
    

    public Connection getConnection() throws Exception{
       // if(conn==null){
        try{
        // Get DataSource from JNDI (defined in context.xml file)
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/slmsdb_ds");
        conn=ds.getConnection();
       logger.debug("New Connection created from ds.."+conn);
        }catch(Exception e){
           logger.error("Exception while getting JDBC Connection : "+e.getMessage());
        }
      //  }
        if(conn==null)
        {
            conn=getLocalDbConnection();
        }

        return conn;
    }

    public Connection getConnection(String dataSource) throws Exception{
       
        if(dataSource!=null)
        {
        try{
        // Get DataSource from JNDI (defined in context.xml file)
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("java:comp/env/"+dataSource);
        conn=ds.getConnection();
       logger.debug("New Connection created from ds2.."+conn);
        }catch(Exception e){
           logger.error("Exception while getting JDBC Connection : "+e.getMessage());
        }
        }
        
        //for local testing
        if(conn==null)
        {
            conn=getLocalDbConnection();
        }

        return conn;
    }

    
    public Connection getLocalDbConnection() throws Exception{

        String driver="com.mysql.jdbc.Driver";
        String driver_url="jdbc:mysql://localhost:3306/slmsdb?zeroDateTimeBehavior=convertToNull";
        String user="root";
        String pwd="root";
        
        try{
        Class.forName(driver);
        conn=DriverManager.getConnection(driver_url, user, pwd);
       logger.debug("Local db connection.."+conn);
        }catch(Exception e){
           logger.error("Exception while getting local JDBC Connection : "+e.getMessage());
        }

        return conn;
    }
    
    
}