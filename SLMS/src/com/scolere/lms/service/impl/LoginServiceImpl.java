/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.impl;

import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.LoginSessionVo;
import com.scolere.lms.domain.vo.StudentDetailVo;
import com.scolere.lms.domain.vo.UserClassMapVo;
import com.scolere.lms.domain.vo.UserLoginVo;
import com.scolere.lms.domain.vo.cross.UserVO;
import com.scolere.lms.persistance.dao.iface.LoginSessionDao;
import com.scolere.lms.persistance.dao.iface.StudentDetailDao;
import com.scolere.lms.persistance.dao.iface.UserClassMapDao;
import com.scolere.lms.persistance.dao.iface.UserLoginDao;
import com.scolere.lms.persistance.factory.LmsDaoFactory;
import com.scolere.lms.service.iface.LoginServiceIface;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dell
 */
public class LoginServiceImpl implements LoginServiceIface{
	
	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public UserVO getUserOrgDetail(String userId) throws LmsServiceException {
        UserVO userVO = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            userVO = dao.getUserOrgDetail(userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUserOrgDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return userVO;
   }
	
    	
   @Override
    public UserVO getUser(String userName, String userPwd) throws LmsServiceException {
        UserVO userVO = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            userVO = dao.getUser(userName, userPwd);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveUserLoginDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return userVO;
   }
   
   @Override
   public UserVO getUser(String userName, String userPwd,int userType) throws LmsServiceException {
       UserVO userVO = null;
       try {
           UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
           userVO = dao.getUser(userName, userPwd,userType);
       } catch (Exception ex) {
          logger.error("LmsServiceException # getUser(?,?,?) = "+ex);
           throw new LmsServiceException(ex.getMessage());
       }
       
       return userVO;
  }
   
   @Override
    public UserVO getUser(String facebookId) throws LmsServiceException {
        UserVO userVO = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            userVO = dao.getUser(facebookId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUser(?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return userVO;
   }   
   
   
   @Override
    public UserVO getUser(String facebookId,int userType) throws LmsServiceException {
        UserVO userVO = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            userVO = dao.getUser(facebookId,userType);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getUser(?) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return userVO;
   }    
   
    @Override
    public boolean updateUserFBId(int userId,String userFbId) throws LmsServiceException {
        
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.updateUserFBId(userId, userFbId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateUserFBId = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }   
    
    @Override
    public boolean updateUserPwd(String userName,String userPwd,String userNewPwd) throws LmsServiceException {
        
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.updateUserPwd(userName, userPwd, userNewPwd);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateUserPwd = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }      
    
    
    @Override
    public boolean updateUserLoginDetail(UserLoginVo vo) throws LmsServiceException {
        
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.updateUserLoginDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateUserLoginDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    

    @Override
    public UserLoginVo saveUserLoginDetail(UserLoginVo vo) throws LmsServiceException {
        UserLoginVo userLoginVo = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            userLoginVo = dao.saveUserLoginDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveUserLoginDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return userLoginVo;
    }
    

    @Override
    public boolean deleteUserLoginDetail(UserLoginVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.deleteUserLoginDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteUserLoginDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    

    @Override
    public UserLoginVo getUserLoginDetail(int id) throws LmsServiceException {
        UserLoginVo vo = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            vo = dao.getUserLoginDetail(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteUserLoginDetail(id) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }

    @Override
    public UserLoginVo getUserLoginDetail(String userName) throws LmsServiceException {
        UserLoginVo vo = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            vo = dao.getUserLoginDetail(userName);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteUserLoginDetail(name) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }    


    @Override
    public UserLoginVo getUserLoginDetail(String userName,String userPwd,boolean isTeacher) throws LmsServiceException {
        UserLoginVo vo = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            vo = dao.getUserLoginDetail(userName,userPwd,isTeacher);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteUserLoginDetail(name,pwd,isTeacher) = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }      
    
    @Override
    public List<UserLoginVo> getUserLoginVoList() throws LmsServiceException {
        List<UserLoginVo> list = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            list = dao.getUserLoginVoList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteUserLoginDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public boolean updateStudentDetail(StudentDetailVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            status = dao.updateStudentDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateStudentDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    
    @Override
    public boolean updateProfilePhoto(String photoPath,String userNm) throws LmsServiceException {
        boolean status = false;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            status = dao.updateProfilePhoto(photoPath,userNm);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateProfilePhoto = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    

    @Override
    public boolean saveStudentDetail(StudentDetailVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            status = dao.saveStudentDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveStudentDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public boolean deleteStudentDetail(StudentDetailVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            status = dao.deleteStudentDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteStudentDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public StudentDetailVo getStudentDetail(int id) throws LmsServiceException {
        StudentDetailVo vo=null;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            vo = dao.getStudentDetail(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteStudentDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }

    
    
    @Override
    public List<StudentDetailVo> getStudentDetailVoList() throws LmsServiceException {
        List<StudentDetailVo> list=null;
        try {
            StudentDetailDao dao = (StudentDetailDao) LmsDaoFactory.getDAO(StudentDetailDao.class);
            list = dao.getStudentDetailVoList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteStudentDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public boolean updateLoginSession(LoginSessionVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            LoginSessionDao dao = (LoginSessionDao) LmsDaoFactory.getDAO(LoginSessionDao.class);
            status = dao.updateLoginSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateLoginSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }
    

    @Override
    public boolean saveLoginSession(LoginSessionVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            LoginSessionDao dao = (LoginSessionDao) LmsDaoFactory.getDAO(LoginSessionDao.class);
            status = dao.saveLoginSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveLoginSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public boolean deleteLoginSession(LoginSessionVo vo) throws LmsServiceException {
        boolean status = false;
        try {
            LoginSessionDao dao = (LoginSessionDao) LmsDaoFactory.getDAO(LoginSessionDao.class);
            status = dao.deleteLoginSession(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteLoginSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    @Override
    public LoginSessionVo getLoginSession(int id) throws LmsServiceException {
        LoginSessionVo vo = null;
        try {
            LoginSessionDao dao = (LoginSessionDao) LmsDaoFactory.getDAO(LoginSessionDao.class);
            vo = dao.getLoginSession(id);
        } catch (Exception ex) {
           logger.error("LmsServiceException # deleteLoginSession = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return vo;
    }

    @Override
    public List<LoginSessionVo> getLoginSessionList() throws LmsServiceException {
        List<LoginSessionVo> list = null;
        try {
            LoginSessionDao dao = (LoginSessionDao) LmsDaoFactory.getDAO(LoginSessionDao.class);
            list = dao.getLoginSessionList();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getLoginSessionList = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public void saveUserClassMapDetail(UserClassMapVo vo) throws LmsServiceException {
        try {
            UserClassMapDao dao = (UserClassMapDao) LmsDaoFactory.getDAO(UserClassMapDao.class);
            dao.saveUserClassMapDetail(vo);
        } catch (Exception ex) {
           logger.error("LmsServiceException # saveUserClassMapDetail = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
    
    }

	@Override
	public boolean defaultFeedsAccessType(int userId,int hrmId) throws LmsServiceException
	{
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.defaultFeedsAccessType(userId, hrmId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # defaultFeedsAccessType = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

    
	@Override
	public boolean defaultUserAssignment(String userName, int schoolId,
			int classId, int hrmId) throws LmsServiceException {
        
        boolean status = false;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.defaultUserAssignment(userName, schoolId, classId, hrmId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # defaultUserAssignment = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }

	
	@Override
	public int getFeedAccessType(int userId) throws LmsServiceException {
		int count = 0;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            count= dao.getFeedAccessType(userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedAccessType = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return count;
    }


	@Override
	public int setFeedAccessType(int userId, int accesTypeId)
			throws LmsServiceException {
		int count = 0;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            count= dao.setFeedAccessType(userId,accesTypeId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # setFeedAccessType = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return count;
    }
	

	@Override
	public List<UserVO> getFeedUsers(int userId) throws LmsServiceException {
		List<UserVO> list = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            list= dao.getFeedUsers(userId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # getFeedUsers = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }		
		return list;
	}

	
	@Override
	public List<CommonKeyValueVO> getAccessTypeMasterData()
			throws LmsServiceException {
		List<CommonKeyValueVO> list = null;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            list= dao.getAccessTypeMasterData();
        } catch (Exception ex) {
           logger.error("LmsServiceException # getAccessTypeMasterData = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }		
		return list;
	}

	@Override
	public int updateFollowersStatus(int userId,List<UserVO> usersList)
			throws LmsServiceException {
		int count = 0;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            count= dao.updateFollowersStatus(userId, usersList);
        } catch (Exception ex) {
           logger.error("LmsServiceException # updateFollowersStatus = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return count;
    }


	@Override
	public boolean isAdminEmailValid(int schoolId, String adminEmail)
		throws LmsServiceException 
		{
	        boolean status = false;
	        try {
	            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
	            status = dao.isAdminEmailValid(schoolId,adminEmail);
	        } catch (Exception ex) {
	           logger.error("LmsServiceException # isAdminEmailValid = "+ex);
	            throw new LmsServiceException(ex.getMessage());
	        }
	        
	        return status;
	    }


	@Override
	public int requestCourse(String userid, String schoolId, String classId, String homeRoomId)
			throws LmsServiceException {
		int status=0;
        try {
            UserLoginDao dao = (UserLoginDao) LmsDaoFactory.getDAO(UserLoginDao.class);
            status = dao.requestCourse(userid,schoolId,classId,homeRoomId);
        } catch (Exception ex) {
           logger.error("LmsServiceException # requestCourse = "+ex);
            throw new LmsServiceException(ex.getMessage());
        }
        
        return status;
    }


}//end of class
