/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.TeacherCourseSessionDtlsVO;
import com.scolere.lms.domain.vo.TeacherCourseSessionVO;
import com.scolere.lms.domain.vo.TeacherCourseVO;
import com.scolere.lms.domain.vo.cross.AssignmentQuestionVO;
import com.scolere.lms.domain.vo.cross.AssignmentVO;
import com.scolere.lms.domain.vo.cross.CommentVO;
import com.scolere.lms.domain.vo.cross.CourseVO;
import com.scolere.lms.domain.vo.cross.ResourseVO;
import com.scolere.lms.persistance.dao.iface.ResourceTypeMstrDao;
import com.scolere.lms.persistance.dao.iface.TeacherCourseDao;
import com.scolere.lms.persistance.dao.iface.TeacherCourseSessionDao;
import com.scolere.lms.persistance.dao.iface.TeacherCourseSessionDtlsDao;
import com.scolere.lms.persistance.factory.LmsDaoFactory;
import com.scolere.lms.service.iface.CourseServiceIface;

/**
 *
 * @author dell
 */
public class CourseServiceImpl implements CourseServiceIface{
	
	Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	
	@Override
	public int setRatingData(int userId, int assignmentResourceTxnId,
			List<CommonKeyValueVO> list) throws LmsServiceException {
		
		int count=0;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            count=dao.setRatingData(userId, assignmentResourceTxnId, list);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRatingMasterData = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return count;
	}
	

	@Override
	public List<CommonKeyValueVO> getRatingMasterData(int schoolId)
			throws LmsServiceException {
        List<CommonKeyValueVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRatingMasterData(schoolId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRatingMasterData = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

	@Override
	public List<CommonKeyValueVO> getRatingValuesMasterData(int schoolId)
			throws LmsServiceException {
        List<CommonKeyValueVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRatingValuesMasterData(schoolId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRatingValuesMasterData = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }	

	@Override
	public List<CommonKeyValueVO> getRatingData(int assignmentResourceTxnId)
			throws LmsServiceException {
        List<CommonKeyValueVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRatingData(assignmentResourceTxnId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

	

    @Override
    public boolean saveResourceComment(int commentBy, int resourceId, String commentTxt) throws LmsServiceException {
      boolean status = false;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.saveResourceComment(commentBy, resourceId, commentTxt);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveResourceComment = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    
    
    @Override
    public boolean saveCommentComment(int commentBy, int commentId, String commentTxt) throws LmsServiceException {
       boolean status = false;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.saveCommentComment(commentBy, commentId, commentTxt);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveCommentComment = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    
    
    @Override
    public boolean saveCommentLike(int commentBy, int commentId) throws LmsServiceException {
       boolean status = false;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.saveCommentLike(commentBy, commentId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveCommentLike = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    

    @Override
    public boolean saveResourceLike(int commentBy, int resourceId) throws LmsServiceException {
       boolean status = false;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.saveResourceLike(commentBy, resourceId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveResourceLike = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

        
	    
	@Override
	public List<ResourseVO> getStudentResourcesWithRating(int moduleId) throws LmsServiceException {
	    List<ResourseVO> list =null;
	    try {
	        TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
	        list = dao.getStudentResources(moduleId);
	    } catch (Exception ex) {
	       logger.error("LmsServiceException # getStudentResourcesWithRating = "+ex);
	        throw new LmsServiceException(ex.getMessage());
	    }
	    
	    return list;
	}    
	    
    @Override
    public List<ResourseVO> getStudentResourcesForRating(int moduleId,int userId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResourcesWithRating(moduleId,userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResourcesForRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }	
	
    @Override
    public List<ResourseVO> getStudentResources(int moduleId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResources(moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<ResourseVO> getStudentResources(int courseId, int moduleId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResources(courseId, moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    
    
    @Override
    public List<ResourseVO> getStudentResourcesForRating(int userId, int courseId, int moduleId, String searchText,int moduleSessionId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResourcesForRating(userId, courseId, moduleId, searchText,moduleSessionId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResourcesForRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<ResourseVO> getStudentResources(int userId, int courseId, int moduleId, String searchText,int moduleSessionId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResources(userId, courseId, moduleId, searchText,moduleSessionId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<ResourseVO> getStudentResourcesWeb(int userId, String courseId, String moduleId, String searchText) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentResourcesWeb(userId, courseId, moduleId, searchText);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<ResourseVO> getTeacherModuleResources(int moduleSessionId) throws LmsServiceException {
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherModuleResources(moduleSessionId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherModuleResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getResourceChildCommentsForRating(int userId,int commentId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceChildCommentsListForRating(userId,commentId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceChildCommentsForRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    
    @Override
    public List<CommentVO> getResourceChildComments(int userId,int commentId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceChildCommentsList(userId,commentId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceComments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getResourceChildComments(int commentId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceChildCommentsList(commentId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceComments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getResourceCommentsForRating(int userId,int resourceId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceCommentsForRating(userId,resourceId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceCommentsForRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    

    @Override
    public List<CommentVO> getResourceComments(int userId,int resourceId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceComments(userId,resourceId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceComments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getResourceComments(int resourceId) throws LmsServiceException {
        
        List<CommentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getResourceComments(resourceId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourceComments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

        
    @Override
    public List<ResourseVO> getRelatedResources(int resourceId,String metaData) throws LmsServiceException {
        
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRelatedResources(resourceId,metaData);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRelatedResources(?,?)= "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    
    
    @Override
    public List<ResourseVO> getRelatedResources(int resourceId) throws LmsServiceException {
        
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRelatedResources(resourceId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRelatedResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    
    @Override
    public List<AssignmentVO> getStudentAssignments(int courseId,int moduleId,int userId) throws LmsServiceException {
        
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignments(courseId, moduleId, userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<AssignmentVO> getStudentAssignmentsByModuleId(int moduleId) throws LmsServiceException {
        
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignmentsByModuleId(moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignmentsByModuleId = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    

    @Override
    public List<AssignmentVO> getStudentAssignmentsByModuleId(String userId,int moduleId) throws LmsServiceException {
        
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignmentsByModuleId(userId,moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignmentsByModuleId = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }  
    
    @Override
    public List<AssignmentVO> getStudentAssignments(int userId) throws LmsServiceException {
        
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignments(userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public AssignmentVO getAssignmentDetail(int userId,int assignmentId) throws LmsServiceException {
        
    	AssignmentVO vo =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            vo = dao.getAssignmentDetail(userId,assignmentId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignments(?,?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }
    
    @Override
    public List<AssignmentVO> getStudentAssignments(int userId,String searchText) throws LmsServiceException {
        
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignments(userId,searchText);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentAssignments(?,?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    

	@Override
	public List<AssignmentVO> getTeacherAssignments(int schoolId, int classId,
			int hrmId, int courseId, int moduleId,int status, int userId, String searchText)
			throws LmsServiceException {
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherAssignments(schoolId, classId, hrmId, courseId, moduleId, status, userId, searchText);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherAssignments .. = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

	
    @Override
    public List<ResourseVO> getAssignmentsResources(int assignmentDtlId) throws LmsServiceException {
        
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAssignmentsResources(assignmentDtlId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignmentsResources(?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    
    @Override
    public List<ResourseVO> getAssignmentsResources(int userId, int assignmentDtlId) throws LmsServiceException {
        
        List<ResourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAssignmentsResources(userId, assignmentDtlId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignmentsResources = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    
    @Override
    public List<CourseVO> getStudentCourses(String userId, String searchText) throws LmsServiceException {
        
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentCourses(userId, searchText);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }
    
    
    @Override
    public CourseVO getStudentCourseDetail(int courseId) throws LmsServiceException {
        CourseVO vo =null;

        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            vo = dao.getStudentCourseDetail(courseId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentCourseDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return vo;
    }
    
    
    @Override
    public List<CourseVO> getStudentCourses(int userId, String searchText) throws LmsServiceException {
        
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentCourses(userId, searchText);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }    

    @Override
    public List<CourseVO> getTeacherCourses(int teacherId) throws LmsServiceException {
        
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCourses(teacherId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }         
    
	@Override
	public List<CourseVO> getTeacherCourseOrganisations(int teacherId,
			int courseId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCourseOrganisations(teacherId,courseId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourseOrganisations = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
	}


    
    
    @Override
    public List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId,int moduleId) throws LmsServiceException {
        
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCourses(userId, schoolId, classId, hrmId, courseId,moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }      
    
    @Override
    public List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId) throws LmsServiceException {
        
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCourses(userId, schoolId, classId, hrmId, courseId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }     

    @Override
    public CourseVO getStudentModuleDetail(int moduleId) throws LmsServiceException {
        CourseVO vo = null;
        
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            vo = dao.getStudentModuleDetail(moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentModuleDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return vo;
    }    
    
    @Override
    public List<CourseVO> getStudentCoursesModules(int courseSessionId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentCoursesModules(courseSessionId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getStudentCoursesModules = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }       
    
    @Override
    public List<CourseVO> getTeacherCoursesModules(int courseSessionId, int schoolId,int classId, int homeRoomMstrId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCoursesModules(courseSessionId,schoolId,classId,homeRoomMstrId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCoursesModules = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }        
    
    @Override
    public List<CourseVO> getTeacherCoursesModules(int courseSessionId,int moduleId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCoursesModules(courseSessionId,moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCoursesModules(?,?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;

    }            
    
    @Override
    public boolean updateTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.updateTeacherCourseSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateTeacherCourseSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    
    @Override
    public void saveTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException {
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            dao.saveTeacherCourseSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveTeacherCourseSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean deleteTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            status = dao.deleteTeacherCourseSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteTeacherCourseSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public TeacherCourseSessionVO getTeacherCourseSession(int id) throws LmsServiceException {

        TeacherCourseSessionVO vo =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            vo = dao.getTeacherCourseSession(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteTeacherCourseSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return vo;
    }

    @Override
    public List<TeacherCourseSessionVO> getTeacherCourseSessionList() throws LmsServiceException {
        List<TeacherCourseSessionVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getTeacherCourseSessionList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourseSessionList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
    }

    @Override
    public boolean updateTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseSessionDtlsDao dao = (TeacherCourseSessionDtlsDao) LmsDaoFactory.getDAO(TeacherCourseSessionDtlsDao.class);
            status = dao.updateTeacherCourseSessionDtls(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateTeacherCourseSessionDtls = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    
    
    @Override
    public void saveTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException {
        
        try {
            TeacherCourseSessionDtlsDao dao = (TeacherCourseSessionDtlsDao) LmsDaoFactory.getDAO(TeacherCourseSessionDtlsDao.class);
            dao.saveTeacherCourseSessionDtls(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveTeacherCourseSessionDtls = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    }

    @Override
    public boolean deleteTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseSessionDtlsDao dao = (TeacherCourseSessionDtlsDao) LmsDaoFactory.getDAO(TeacherCourseSessionDtlsDao.class);
            status = dao.deleteTeacherCourseSessionDtls(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteTeacherCourseSessionDtls = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    
    @Override
    public TeacherCourseSessionDtlsVO getTeacherCourseSessionDtls(int id) throws LmsServiceException {

        TeacherCourseSessionDtlsVO vo = null;
        
        try {
            TeacherCourseSessionDtlsDao dao = (TeacherCourseSessionDtlsDao) LmsDaoFactory.getDAO(TeacherCourseSessionDtlsDao.class);
            vo = dao.getTeacherCourseSessionDtls(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourseSessionDtls = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;        
    
    }

    @Override
    public List<TeacherCourseSessionDtlsVO> getTeacherCourseSessionDtlsList() throws LmsServiceException {

        List<TeacherCourseSessionDtlsVO> list = null;
        
        try {
            TeacherCourseSessionDtlsDao dao = (TeacherCourseSessionDtlsDao) LmsDaoFactory.getDAO(TeacherCourseSessionDtlsDao.class);
            list = dao.getTeacherCourseSessionDtlsList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourseSessionDtlsList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;        
    
    }

    @Override
    public boolean updateTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseDao dao = (TeacherCourseDao) LmsDaoFactory.getDAO(TeacherCourseDao.class);
            status = dao.updateTeacherCourseVO(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateTeacherCourseVO = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public void saveTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException {

        try {
            TeacherCourseDao dao = (TeacherCourseDao) LmsDaoFactory.getDAO(TeacherCourseDao.class);
            dao.saveTeacherCourseVO(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveTeacherCourseVO = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
    }

    @Override
    public boolean deleteTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            TeacherCourseDao dao = (TeacherCourseDao) LmsDaoFactory.getDAO(TeacherCourseDao.class);
            status = dao.deleteTeacherCourseVO(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteTeacherCourseVO = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public TeacherCourseVO getTeacherCourse(int id) throws LmsServiceException {
        
        TeacherCourseVO vo =null;
        try {
            TeacherCourseDao dao = (TeacherCourseDao) LmsDaoFactory.getDAO(TeacherCourseDao.class);
            vo = dao.getTeacherCourse(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteTeacherCourseVO = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }

    @Override
    public List<TeacherCourseVO> getTeacherCourseList() throws LmsServiceException {
        List<TeacherCourseVO> list = null;
        
        try {
            TeacherCourseDao dao = (TeacherCourseDao) LmsDaoFactory.getDAO(TeacherCourseDao.class);
            list = dao.getTeacherCourseList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTeacherCourseList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;        
    
    }

    @Override
    public int uploadAssignment(int assignmentId,String resourceName,String resourceAuthor, String resourceDesc,int userId, String descTxt, String url, String thumbUrl, String authorImgUrl)
			throws LmsServiceException {
		
    		int status = 0;
        try {
        	 ResourceTypeMstrDao  dao = (ResourceTypeMstrDao) LmsDaoFactory.getDAO(ResourceTypeMstrDao.class);
             status = dao.uploadAssignment(assignmentId, resourceName, resourceAuthor, resourceDesc, userId, descTxt, url, thumbUrl, authorImgUrl);
        } catch (Exception ex) {
           logger.error("Exception #  "+ex.getMessage());
            throw new LmsServiceException("Exception # "+ex.getMessage());
        }
        
       return status; 
	}


	@Override
	public List<AssignmentVO> getAssignmentsByModuleId(int moduleId) throws LmsServiceException {

        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAssignmentsByModuleId(moduleId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignmentsByModuleId = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
	}


	@Override
	public List<AssignmentVO> getStudentsByAssignmentId(int assignmentId)
			throws LmsServiceException {
		 List<AssignmentVO> list =null;
	        try {
	            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
	            list = dao.getStudentsByAssignmentId(assignmentId);
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # getStudentsByAssignmentId = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}


	@Override
	public List<AssignmentVO> getStudentsByAssignmentId(int schoolId,
			int classId, int hrmId, int courseId, int moduleId, int userId,
			int assignmentId) throws LmsServiceException {
		 
		List<AssignmentVO> list =null;
	        try {
	            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
	            list = dao.getStudentsByAssignmentId(schoolId, classId, hrmId, courseId, moduleId, userId, assignmentId);
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # getStudentsByAssignmentId.. = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	   return list;
	}


	@Override
	public List<AssignmentVO> getAssignments(int schoolId, int classId,
			int hrmId, int courseId, int moduleId, int userId)
			throws LmsServiceException {
		
        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAssignments(schoolId,classId,hrmId,courseId,moduleId,userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
	}


	@Override
	public List<AssignmentVO> getStudentAssignmentsByModule(int moduleId, int userId) throws LmsServiceException {

        List<AssignmentVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getStudentAssignmentsByModule(moduleId,userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignments = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
	}


	@Override
	public List<AssignmentQuestionVO> getAssignmentQuestionAnswers(AssignmentVO asignment) throws LmsServiceException {
		List<AssignmentQuestionVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAssignmentQuestionAnswers(asignment);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAssignmentQuestionAnswers(?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
	}


	@Override
	public String getCourseReviewGrade(int avgAssignmentReviewGrade) throws LmsServiceException {
        String grade =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            grade = dao.getCourseReviewGrade(avgAssignmentReviewGrade);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getCourseReviewGrade = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return grade;
	}
	
    @Override
    public int submitAssignment(int assignmentResourceTxn,int submittedBy,int assignmentTypeId,List<AssignmentQuestionVO> list,int schoolId,int courseId)throws LmsServiceException
    {
    		int status = 0;
        try {
        	 ResourceTypeMstrDao  dao = (ResourceTypeMstrDao) LmsDaoFactory.getDAO(ResourceTypeMstrDao.class);
             status = dao.submitAssignment(assignmentResourceTxn, submittedBy, assignmentTypeId, list,schoolId,courseId);
        } catch (Exception ex) {
           logger.error("Exception #  submitAssignment "+ex.getMessage());
           throw new LmsServiceException("Exception # submitAssignment "+ex.getMessage());
        }
        
       return status; 
	}


	@Override
	public List<CourseVO> getAvailableCourses(int userId, int schoolId, int classId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAvailableCourses(userId, schoolId,classId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAvailableCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
	}


	@Override
	public List<CourseVO> getAvailableCourseModules(String courseId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAvailableCourseModules(courseId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAvailableCourses = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
	}


	@Override
	public List<CourseVO> getAvailableCoursesSessions(String courseId,int userId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getAvailableCoursesSessions(courseId,userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAvailableCoursesSessions = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
	}


	@Override
	public List<CourseVO> getRequestedCourses(int userId, int schoolId, int classId) throws LmsServiceException {
        List<CourseVO> list =null;
        try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            list = dao.getRequestedCourses(userId, schoolId,classId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAvailableCoursesSessions = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
    return list;
	}


	@Override
	public int retryCourse(int userId, int schoolId, int hrmId, int courseId) throws LmsServiceException {
    
		int count=0;
		try {
            TeacherCourseSessionDao dao = (TeacherCourseSessionDao) LmsDaoFactory.getDAO(TeacherCourseSessionDao.class);
            count = dao.retryCourse(userId,schoolId,hrmId,courseId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # retryCourse = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
		return count;
	}
    
    
}//End of class
