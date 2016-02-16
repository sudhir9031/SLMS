/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.ClassMasterVo;
import com.scolere.lms.domain.vo.CourseMasterVo;
import com.scolere.lms.domain.vo.HomeRoomMasterVo;
import com.scolere.lms.domain.vo.LmsFeedTxnVO;
import com.scolere.lms.domain.vo.LmsFeedTypeVO;
import com.scolere.lms.domain.vo.ModuleMasterVo;
import com.scolere.lms.domain.vo.SchoolMasterVo;
import com.scolere.lms.domain.vo.cross.AssignmentVO;
import com.scolere.lms.domain.vo.cross.CommentVO;
import com.scolere.lms.domain.vo.cross.CourseVO;
import com.scolere.lms.domain.vo.cross.FeedVO;
import com.scolere.lms.domain.vo.cross.ResourseVO;
import com.scolere.lms.domain.vo.cross.SearchVO;
import com.scolere.lms.domain.vo.cross.UserVO;
import com.scolere.lms.persistance.dao.iface.ActivityDao;
import com.scolere.lms.persistance.dao.iface.AssignmentDao;
import com.scolere.lms.persistance.dao.iface.ClassMasterDao;
import com.scolere.lms.persistance.dao.iface.CourseMasterDao;
import com.scolere.lms.persistance.dao.iface.FeedDao;
import com.scolere.lms.persistance.dao.iface.HomeRoomMasterDao;
import com.scolere.lms.persistance.dao.iface.ModuleMasterDao;
import com.scolere.lms.persistance.dao.iface.SchoolMasterDao;
import com.scolere.lms.persistance.factory.LmsDaoFactory;
import com.scolere.lms.service.iface.CommonServiceIface;


/**
 *
 * @author dell
 */
public class CommonServiceImpl implements CommonServiceIface{

	Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Override
	public FeedVO getRating(int userId, int feedId, int resourceId)
			throws LmsServiceException {
		FeedVO vo = null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
        	vo= dao.getRating(userId,feedId,resourceId);
        	
        } catch (Exception ex) {
           logger.error("LmsServiceException # getRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        } 		
		
		return vo;
	}
	
	@Override
	public int setRating(int userId,int feedId,int resourceId,BigDecimal rating) throws LmsServiceException {
		int count=0;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
        	count= dao.setRating(userId,feedId,resourceId,rating);
        	
        } catch (Exception ex) {
           logger.error("LmsServiceException # setRating = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }    
        return count;
	}	
	
    /*SCHOOL RELATED METHODS*/
    @Override
    public boolean updateSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            status = dao.updateSchoolMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateSchoolMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public void saveSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException {
        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            dao.saveSchoolMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveSchoolMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    }
    

    @Override
    public boolean deleteSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            status = dao.updateSchoolMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteSchoolMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public SchoolMasterVo getSchoolMasterDetail(int id) throws LmsServiceException {
       SchoolMasterVo vo = null; 

        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            vo = dao.getSchoolMasterDetail(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSchoolMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return vo;
    }

    @Override
    public List<SchoolMasterVo> getSchoolMasterVoList() throws LmsServiceException {
       List<SchoolMasterVo> list = null; 

        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            list = dao.getSchoolMasterVoList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSchoolMasterVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;
    }

    @Override
    public List<SchoolMasterVo> getSchoolMasterVoList(int teacherId) throws LmsServiceException {
       List<SchoolMasterVo> list = null; 

        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            list = dao.getSchoolMasterVoList(teacherId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSchoolMasterVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;
    }    
    
    /* CLASS RELATED METHODS */
    @Override
    public boolean updateClassDetail(ClassMasterVo vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            status = dao.updateClassDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateClassDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public void saveClassDetail(ClassMasterVo vo) throws LmsServiceException {
        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            dao.saveClassDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveClassDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean deleteClassDetail(ClassMasterVo vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            status = dao.updateClassDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteClassDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;    
    }
    

    @Override
    public ClassMasterVo getClassDetail(int id) throws LmsServiceException {
       ClassMasterVo vo = null; 

        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            vo = dao.getClassDetail(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getClassDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return vo;
    }

    @Override
    public List<ClassMasterVo> getClassVoList() throws LmsServiceException {
       List<ClassMasterVo> list = null; 

        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            list = dao.getClassMasterVoList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getClassVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;        
    }
    
    
    @Override
    public List<ClassMasterVo> getClassVoList(int mstrId) throws LmsServiceException {
       List<ClassMasterVo> list = null; 

        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            list = dao.getClassMasterVoList(mstrId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getClassVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;        
    }
    

    @Override
    public List<ClassMasterVo> getClassVoList(int clsId,int teacherId) throws LmsServiceException {
       List<ClassMasterVo> list = null; 

        try {
            ClassMasterDao dao = (ClassMasterDao) LmsDaoFactory.getDAO(ClassMasterDao.class);
            list = dao.getClassMasterVoList(clsId,teacherId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getClassVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;        
    }
        

    /*HRM RELATED METHODS*/
    @Override
    public boolean updateHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsServiceException {
        boolean status = false;
        
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            status = dao.updateHomeRoomMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateHomeRoomMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public void saveHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsServiceException {
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            dao.saveHomeRoomMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveHomeRoomMasterDetail = " + ex);
            throw new LmsServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean deleteHomeRoomMasterDetail(HomeRoomMasterVo vo) throws LmsServiceException {
         boolean status = false;
        
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            status = dao.deleteHomeRoomMasterDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteHomeRoomMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public HomeRoomMasterVo getHomeRoomMasterDetail(int id) throws LmsServiceException {
        HomeRoomMasterVo vo =null; 
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            vo = dao.getHomeRoomMasterDetail(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getHomeRoomMasterDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }

    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList() throws LmsServiceException {
        List<HomeRoomMasterVo> list =null; 
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            list = dao.getHomeRoomMasterVoList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getHomeRoomMasterVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList(int clsId) throws LmsServiceException {
        List<HomeRoomMasterVo> list =null; 
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            list = dao.getHomeRoomMasterVoList(clsId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getHomeRoomMasterVoList(clsId) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<HomeRoomMasterVo> getHomeRoomMasterVoList(int clsId, int schoolId,int teacherId ) throws LmsServiceException {
        List<HomeRoomMasterVo> list =null; 
        try {
            HomeRoomMasterDao dao = (HomeRoomMasterDao) LmsDaoFactory.getDAO(HomeRoomMasterDao.class);
            list = dao.getHomeRoomMasterVoList(clsId, schoolId,teacherId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getHomeRoomMasterVoList(clsId) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }


	@Override
	public FeedVO getFeedDetailForRating(int userId, int feedId)
			throws LmsServiceException {
		
		FeedVO  feedVO=null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            feedVO = dao.getFeedDetailForRating(userId, feedId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedsList(x,y) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return feedVO;
    }
	
	@Override
	public FeedVO getFeedDetail(int userId, int feedId)
			throws LmsServiceException {
		
		FeedVO  feedVO=null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            feedVO = dao.getFeedDetail(userId, feedId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedsList(x,y) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return feedVO;
    }
  
    @Override
    public List<FeedVO> getNotificationsList(int userId, String searchTxt,int offset,int noOfRecords) throws LmsServiceException {
        
        List<FeedVO>  list=null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getNotificationsList(userId, searchTxt, offset, noOfRecords);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getNotificationsList(x,y) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    
    
	
    @Override
    public List<FeedVO> getFeedsListWithRating(int userId, String searchTxt,int offset,int noOfRecords) throws LmsServiceException {
        
        List<FeedVO>  list=null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedsListWithRating(userId, searchTxt, offset, noOfRecords);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedsList(x,y) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    
	
    @Override
    public List<FeedVO> getFeedsList(int userId, String searchTxt,int offset,int noOfRecords) throws LmsServiceException {
        
        List<FeedVO>  list=null;
        try {
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedsList(userId, searchTxt, offset, noOfRecords);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedsList(x,y) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<CommentVO> getFeedChildCommentsListForRating(int commentId,int userId,int offset,int noOfRecords) throws LmsServiceException {
       List<CommentVO>  list=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedChildCommentsListForRating(commentId, userId, offset, noOfRecords);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedChildCommentsList(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getFeedChildCommentsList(int commentId,int userId,int offset,int noOfRecords) throws LmsServiceException {
       List<CommentVO>  list=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedChildCommentsList(commentId, userId, offset, noOfRecords);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedChildCommentsList(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public List<CommentVO> getFeedCommentsListForRating(int feedId,int userId,int offset,int noOfRecords) throws LmsServiceException {
       List<CommentVO>  list=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedCommentsListForRating(feedId, userId, offset, noOfRecords);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedCommentsList(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<CommentVO> getFeedCommentsList(int feedId,int userId,int offset,int noOfRecords) throws LmsServiceException {
       List<CommentVO>  list=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            list = dao.getFeedCommentsList(feedId, userId, offset, noOfRecords);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedCommentsList(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }    
    
    @Override
    public UserVO getUserDetail(String userName) throws LmsServiceException {
        
        UserVO vo =null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getUserDetail(userName);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUserDetail(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }
    
    
    @Override
    public UserVO getUserDetail(int userId) throws LmsServiceException {
        
        UserVO vo =null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getUserDetail(userId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUserDetail(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }

    @Override
    public CourseVO getCourseDetail(int feedId) throws LmsServiceException {
        
        CourseVO vo =null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getCourseDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getCourseDetail(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }


    @Override
    public CourseVO getModuleDetail(int feedId) throws LmsServiceException {
        CourseVO vo =null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getModuleDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getModuleDetail(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }


    @Override
    public ResourseVO getResourseDetail(int feedId) throws LmsServiceException {
        
        ResourseVO vo = null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getResourseDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getResourseDetail(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }

    @Override
    public AssignmentVO getAssignmentDetail(int feedId) throws LmsServiceException {
        
        AssignmentVO vo = null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getAssignmentDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }

    @Override
    public HashMap<Integer, LmsFeedTypeVO> getFeedTemplates() throws LmsServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LmsFeedTxnVO> getAllFeeds(int userId, String searchText) throws LmsServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getResourceFeedText(int resourceId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getResourceFeedText(resourceId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }

    @Override
    public String getCourseFeedText(int courseId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getCourseFeedText(courseId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }

    @Override
    public String getUserFeedText(int userId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getUserFeedText(userId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }

    @Override
    public String getAssignmentFeedText(int assignmentId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getAssignmentFeedText(assignmentId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }

    @Override
    public String getModuleFeedText(int moduleId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getModuleFeedText(moduleId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # AssignmentVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }
    
    @Override
    public String getHomeRoomFeedText(int hrmId) throws LmsServiceException {
        String txt="";
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            txt = dao.getHomeRoomFeedText(hrmId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # HrmVO(x) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return txt;
    }



    @Override
    public ResourseVO getDefaultResourseDetail(int feedId) throws LmsServiceException {
            
        ResourseVO vo = null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getDefaultResourseDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getDefaultResourseDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        return vo;
    }

    @Override
    public boolean saveFeedComment(int commentBy, int feedId, String commentTxt) throws LmsServiceException {
        boolean status = false;
        
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            status = dao.saveFeedComment(commentBy, feedId, commentTxt);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveFeedComment = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    
        return status;
    }

    @Override
    public boolean saveCommentComment(int commentBy, int commentId, String commentTxt) throws LmsServiceException {
        boolean status = false;
        
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
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
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            status = dao.saveCommentLike(commentBy, commentId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveCommentLike = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    
        return status;
    }

    @Override
    public boolean saveFeedLike(int commentBy, int resourceId) throws LmsServiceException {
        boolean status = false;
        
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            status = dao.saveFeedLike(commentBy, resourceId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveFeedLike = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    
        return status;
    }

    @Override
    public FeedVO getFeedDetail(int feedId) throws LmsServiceException {
        FeedVO vo =null;
        
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            vo = dao.getFeedDetail(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return vo;
    }

	@Override
	public long getTotalFeedsCount(int userId) throws LmsServiceException {
        
		long count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.getTotalFeedsCount(userId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTotalFeedsCount = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
        		
	}

	@Override
	public int getUnreadFeedCount(int userId) throws LmsServiceException {
        
		int count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.getUnreadFeedsCount(userId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUnreadFeedCount = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
        		
	}

	@Override
	public long getTotalFeedsCount(int userId,String searchText) throws LmsServiceException {
        
		long count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.getTotalFeedsCount(userId,searchText);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTotalFeedsCount = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
        		
	}	
	
	@Override
	public long getTotalCommentsCount(int feedId)
			throws LmsServiceException {
		long count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.getTotalCommentsCount(feedId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTotalCommentsCount#1 = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
	}	

	@Override
	public long getTotalCommentsCount(int feedId, int commentId)
			throws LmsServiceException {
		long count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.getTotalCommentsCount(feedId, commentId);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getTotalCommentsCount#2 = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
	}

	@Override
	public long updateNotificationStatus(int userId, int feedId,
			String status) throws LmsServiceException {
		long count=0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            count = dao.updateNotificationStatus(userId, feedId, status);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateNotificationStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return count;
	}



	public List<ModuleMasterVo> getModuleVoList(int courseId,
			int homeRoomMstrId, int classId, int schoolId, int teacherId)
			throws LmsServiceException {
		 List<ModuleMasterVo>  list=null;
	        try {
	            
	        	ModuleMasterDao dao = (ModuleMasterDao) LmsDaoFactory.getDAO(ModuleMasterDao.class);
	            list = dao.getModuleList(courseId,homeRoomMstrId,classId,schoolId,teacherId);
	                    
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # getModuleVoList = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}

	@Override
	public List<com.scolere.lms.domain.vo.AssignmentVO> getAssignVoList(
			int moduleMasterId, int homeRoomMstrId, int classId, int schoolId,
			int teacherId) throws LmsServiceException {
		 List<com.scolere.lms.domain.vo.AssignmentVO>  list=null;
	        try {
	            
	        	AssignmentDao dao = (AssignmentDao) LmsDaoFactory.getDAO(AssignmentDao.class);
	            list = dao.getAssignmentList(moduleMasterId, homeRoomMstrId , classId , schoolId , teacherId);
	                    
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # getModuleVoList = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}

	 
 
	@Override
	public List<CourseMasterVo> getCourseVoList(int homeRoomMstrId,
			int classId, int schoolId, int teacherId)
			throws LmsServiceException {
		 List<CourseMasterVo>  list=null;
	        try {
	            
	        	CourseMasterDao dao = (CourseMasterDao) LmsDaoFactory.getDAO(CourseMasterDao.class);
	            list = dao.getCourseList(homeRoomMstrId,classId, schoolId, teacherId);
	                    
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # getCourseVoList = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return list;
	}

	@Override
	public List<SchoolMasterVo> getSchoolMasterVoList(int schoolId,
			int teacherId) throws LmsServiceException {
		List<SchoolMasterVo> list = null; 

        try {
            SchoolMasterDao dao = (SchoolMasterDao) LmsDaoFactory.getDAO(SchoolMasterDao.class);
            list = dao.getSchoolMasterVoList(schoolId, teacherId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSchoolMasterVoList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }       
       
       return list;
	}

	
	@Override
	public List<SearchVO> getSearchList(int userId, String searchTxt,
			int offset, int noOfRecords) throws LmsServiceException {
		List<SearchVO> searchList=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            searchList = dao.getSearchList(userId, searchTxt, offset, noOfRecords);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSearchList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return searchList;
	}
 
	@Override
	public List<SearchVO> getSearchList(int userId, String searchTxt,
			int offset, int noOfRecords,String category) throws LmsServiceException {
		List<SearchVO> searchList=null;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            searchList = dao.getSearchList(userId, searchTxt, offset, noOfRecords,category);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSearchList#Category = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return searchList;
	}

	@Override
	public int getSearchRecordsCount(int userId, String searchTxt,String category) throws LmsServiceException {
		int searchCount = 0;
        try {
            
            FeedDao dao = (FeedDao) LmsDaoFactory.getDAO(FeedDao.class);
            searchCount = dao.getSearchRecordsCount(userId, searchTxt, category);
                    
        } catch (Exception ex) {
           logger.error("LmsServiceException # getSearchRecordsCount#Category = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }        
        
        return searchCount;
	}

	@Override
	public void saveActivity(String userNm, int activityId)
			throws LmsServiceException {
        try {
            
        	ActivityDao dao = (ActivityDao) LmsDaoFactory.getDAO(ActivityDao.class);
        	dao.saveActivity(userNm, activityId);
        	
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveActivity = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }    		
	}
	
	@Override
	public void saveActivity(int userId, int activityId)
			throws LmsServiceException {
        try {
            
        	ActivityDao dao = (ActivityDao) LmsDaoFactory.getDAO(ActivityDao.class);
        	dao.saveActivity(userId, activityId);
        	
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveActivity = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }    		
	}

	
	@Override
	public List<UserVO> getMostActivUsers() throws LmsServiceException {
    	List<UserVO> userList=null;

        try {
        	
        	ActivityDao dao = (ActivityDao) LmsDaoFactory.getDAO(ActivityDao.class);
        	userList=dao.getMostActivUsers();
        	
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveActivity = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }    	
        
        return userList;
	}


	
}//End of class
