/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.common.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Web application lifecycle listener. 
 *
 * @author dell
 */
@WebListener()
public class ApplContextListener implements ServletContextListener {
    Logger logger = LoggerFactory.getLogger(ApplContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Context initialized...........");
        PropertyManager propertyManager= new PropertyManager();
        sce.getServletContext().log("Application property loaded..."+propertyManager.getProperty("TEST.MESSAGE.WELCOME"));
     
        logger.info("logger configured.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	sce.getServletContext().log("Context destroyed...........");
    }
    
    
}
