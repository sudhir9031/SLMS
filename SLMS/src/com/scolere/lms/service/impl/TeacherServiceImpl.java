package com.scolere.lms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.persistance.dao.iface.TeacherDao;
import com.scolere.lms.persistance.factory.LmsDaoFactory;
import com.scolere.lms.service.iface.TeacherServiceIface;


public class TeacherServiceImpl implements TeacherServiceIface {
	
	Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

	@Override
	public int updateCourseStatus(int courseSessionId,int statusCode)
			throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.updateCourseStatus(courseSessionId,statusCode);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateCourseStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}
	

	@Override
	public int updateCourseModuleStatus(int moduleSessionId,int statusCode)
			throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.updateCourseModuleStatus(moduleSessionId,statusCode);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateCourseModuleStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}

	
	@Override
	public int updateCourseResourceStatus(int resourseSessionId,int statusCode)
			throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.updateCourseResourceStatus(resourseSessionId,statusCode);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateCourseResourceStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}


	@Override
	public List<Integer> getCoursePercentage(int userId, int schoolId,
			int classId, int hrmId) throws LmsServiceException {
		List<Integer> list = null;
		 try {
	            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
	            list = (List<Integer>) dao.getCoursePercentage(userId, schoolId,classId, hrmId);
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # updateCourseResourceStatus = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}


	@Override
	public List<Integer> getAssignmentPercentage(int userId, int schoolId,
			int classId, int hrmId) throws LmsServiceException {
		List<Integer> list = null;
		 try {
	            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
	            list = (List<Integer>) dao.getAssignmentPercentage(userId, schoolId,classId, hrmId);
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # updateCourseResourceStatus = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}


	@Override
	public int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId, int statusCode, int userId,
			String dueDate)throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.updateAssignmentStatus(schoolId, classId, hrmId, courseId,moduleId,statusCode,userId,dueDate);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateAssignmentStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}
	

	@Override
	public int updateAssignmentStatus(int schoolId, int classId, int hrmId,int courseId, int moduleId,int assignmentId,int statusCode, int userId,
			String dueDate)throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.updateAssignmentStatus(schoolId, classId, hrmId, courseId,moduleId,assignmentId,statusCode,userId,dueDate);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateAssignmentStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}	


	@Override
	public int cancelAssignment(int schoolId, int classId, int hrmId,int courseId, int moduleId,int assignmentId,int statusCode, int userId)throws LmsServiceException {
        int updateCount = 0;
        
        try {
            TeacherDao dao = (TeacherDao) LmsDaoFactory.getDAO(TeacherDao.class);
            updateCount = dao.cancelAssignment(schoolId, classId, hrmId, courseId,moduleId,assignmentId,statusCode,userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # cancelAssignment = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return updateCount;
	}		
	
}//End of class
