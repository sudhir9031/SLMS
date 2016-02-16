/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.iface;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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


/**
 *
 * @author dell
 */
public interface CommonServiceIface {
	/*Rating services*/
	int setRating(int userId,int feedId,int resourceId,BigDecimal rating) throws LmsServiceException;
	FeedVO getRating(int userId, int feedId, int resourceId) throws LmsServiceException;
	
    /*User Activity methods*/
	void saveActivity(String userNm,int activityId) throws LmsServiceException;
	void saveActivity(int userId,int activityId) throws LmsServiceException;
    List<UserVO> getMostActivUsers() throws LmsServiceException;
	
	/*Search methods*/
    List<SearchVO> getSearchList(int userId,String searchTxt,int offset,int noOfRecords) throws LmsServiceException;
    List<SearchVO> getSearchList(int userId,String searchTxt,int offset,int noOfRecords,String category) throws LmsServiceException;
    int getSearchRecordsCount(int userId,String searchTxt,String category) throws LmsServiceException;

	
    /*FEED RELATED SERVICES*/
    
	long updateNotificationStatus(int userId,int feedId,String status) throws LmsServiceException;
        // Comment & Likes
    boolean saveFeedComment(int commentBy,int resourceId,String commentTxt) throws LmsServiceException;
    boolean saveCommentComment(int commentBy,int commentId,String commentTxt) throws LmsServiceException;
    boolean saveCommentLike(int commentBy,int commentId) throws LmsServiceException;
    boolean saveFeedLike(int commentBy,int resourceId) throws LmsServiceException;
    
    FeedVO getFeedDetailForRating(int userId,int feedId) throws LmsServiceException;
    FeedVO getFeedDetail(int userId,int feedId) throws LmsServiceException;
    List<FeedVO> getNotificationsList(int userId,String searchTxt,int offset,int noOfRecords) throws LmsServiceException;
    List<FeedVO> getFeedsListWithRating(int userId,String searchTxt,int offset,int noOfRecords) throws LmsServiceException;
    List<FeedVO> getFeedsList(int userId,String searchTxt,int offset,int noOfRecords) throws LmsServiceException;
    List<CommentVO> getFeedCommentsListForRating(int feedId,int userId,int offset,int noOfRecords) throws LmsServiceException;
    List<CommentVO> getFeedCommentsList(int feedId,int userId,int offset,int noOfRecords) throws LmsServiceException;
    List<CommentVO> getFeedChildCommentsListForRating(int commentId,int userId,int offset,int noOfRecords) throws LmsServiceException;
    List<CommentVO> getFeedChildCommentsList(int commentId,int userId,int offset,int noOfRecords) throws LmsServiceException;
    ResourseVO getDefaultResourseDetail(int feedId) throws LmsServiceException;
    
    //total record counts
    long getTotalFeedsCount(int userId,String searchText) throws LmsServiceException;
    long getTotalFeedsCount(int userId) throws LmsServiceException;
    int getUnreadFeedCount(int userId) throws LmsServiceException;
    long getTotalCommentsCount(int feedId) throws LmsServiceException;
    long getTotalCommentsCount(int feedId,int commentId) throws LmsServiceException;
    
    
    FeedVO getFeedDetail(int feedId) throws LmsServiceException;            
    UserVO getUserDetail(String userName) throws LmsServiceException;
    UserVO getUserDetail(int userId) throws LmsServiceException;
    CourseVO getCourseDetail(int feedId) throws LmsServiceException;
    CourseVO getModuleDetail(int feedId) throws LmsServiceException;
    ResourseVO getResourseDetail(int feedId) throws LmsServiceException;
    AssignmentVO getAssignmentDetail(int feedId) throws LmsServiceException;
    
    HashMap<Integer,LmsFeedTypeVO> getFeedTemplates() throws LmsServiceException;
    List<LmsFeedTxnVO> getAllFeeds(int userId,String searchText) throws LmsServiceException; 
    String getResourceFeedText(int resourceId) throws LmsServiceException;
    String getCourseFeedText(int courseId) throws LmsServiceException;
    String getUserFeedText(int userId) throws LmsServiceException;
    String getAssignmentFeedText(int assignmentId) throws LmsServiceException;
    String getModuleFeedText(int moduleId) throws LmsServiceException;
	String getHomeRoomFeedText(int hrmId) throws LmsServiceException;    

    
    /*SCHOOL RELATED METHODS*/
    boolean updateSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException;
    void saveSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException;
    boolean deleteSchoolMasterDetail(SchoolMasterVo vo) throws LmsServiceException;
    SchoolMasterVo getSchoolMasterDetail(int id) throws LmsServiceException;
    List<SchoolMasterVo> getSchoolMasterVoList(int teacherId) throws LmsServiceException;
    List<SchoolMasterVo> getSchoolMasterVoList() throws LmsServiceException;
    List<SchoolMasterVo> getSchoolMasterVoList(int schoolId, int teacherId) throws LmsServiceException;
    
    /* CLASS RELATED METHODS */
    boolean updateClassDetail(ClassMasterVo  vo) throws LmsServiceException;
    void saveClassDetail(ClassMasterVo   vo) throws LmsServiceException;
    boolean deleteClassDetail(ClassMasterVo   vo) throws LmsServiceException;
    ClassMasterVo  getClassDetail(int id) throws LmsServiceException;
    List<ClassMasterVo > getClassVoList() throws LmsServiceException; 
    List<ClassMasterVo > getClassVoList(int classId) throws LmsServiceException; 
    List<ClassMasterVo > getClassVoList(int classId,int teacherId) throws LmsServiceException; 
    
    /*HRM RELATED METHODS*/
    boolean updateHomeRoomMasterDetail(HomeRoomMasterVo  vo) throws LmsServiceException;
    void saveHomeRoomMasterDetail(HomeRoomMasterVo   vo) throws LmsServiceException;
    boolean deleteHomeRoomMasterDetail(HomeRoomMasterVo   vo) throws LmsServiceException;
    HomeRoomMasterVo  getHomeRoomMasterDetail(int id) throws LmsServiceException;
    List<HomeRoomMasterVo > getHomeRoomMasterVoList() throws LmsServiceException;    
    List<HomeRoomMasterVo > getHomeRoomMasterVoList(int clsId) throws LmsServiceException;
    List<HomeRoomMasterVo> getHomeRoomMasterVoList(int classId, int schoolId,int teacherId) throws LmsServiceException;
    
    /*COURSE RELATED METHODS*/
    List<CourseMasterVo> getCourseVoList(int homeRoomMstrId, int classId,int schoolId, int teacherId)throws LmsServiceException;
	List<ModuleMasterVo> getModuleVoList(int courseId, int homeRoomMstrId,int classId, int schoolId, int teacherId)throws LmsServiceException;
	List<com.scolere.lms.domain.vo.AssignmentVO> getAssignVoList(int moduleMasterId, int homeRoomMstrId, int classId, int schoolId,int teacherId) throws LmsServiceException;

    
}
