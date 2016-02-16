package com.scolere.lms.persistance.dao.iface;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.CommonKeyValueVO;
import com.scolere.lms.domain.vo.TeacherCourseSessionVO;
import com.scolere.lms.domain.vo.cross.AssignmentQuestionVO;
import com.scolere.lms.domain.vo.cross.AssignmentVO;
import com.scolere.lms.domain.vo.cross.CommentVO;
import com.scolere.lms.domain.vo.cross.CourseVO;
import com.scolere.lms.domain.vo.cross.ResourseVO;
import java.util.List;


public interface TeacherCourseSessionDao
{
	List<CommonKeyValueVO> getRatingMasterData(int schoolId) throws LmsDaoException;
	List<CommonKeyValueVO> getRatingValuesMasterData(int schoolId) throws LmsDaoException;
	List<CommonKeyValueVO> getRatingData(int assignmentResourceTxnId) throws LmsDaoException;
	int setRatingData(int userId,int assignmentResourceTxnId,List<CommonKeyValueVO> list) throws LmsDaoException;
	
    // Comment & Likes
    boolean saveResourceComment(int commentBy,int resourceId,String commentTxt) throws LmsDaoException;
    boolean saveCommentComment(int commentBy,int commentId,String commentTxt) throws LmsDaoException;
    boolean saveCommentLike(int commentBy,int commentId) throws LmsDaoException;
    boolean saveResourceLike(int commentBy,int resourceId) throws LmsDaoException;
    
    //New methods for students details >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
    List<CourseVO> getTeacherCourses(int teacherId) throws LmsDaoException;
    List<CourseVO> getTeacherCourseOrganisations(int teacherId,int courseId) throws LmsDaoException;
    
    CourseVO getStudentCourseDetail(int courseID) throws LmsDaoException;
    List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId,int moduleId) throws LmsDaoException;
    List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId) throws LmsDaoException;
    List<CourseVO> getStudentCourses(int userId,String searchText) throws LmsDaoException;
    List<CourseVO> getStudentCourses(String userNm,String searchText) throws LmsDaoException;
    CourseVO getStudentModuleDetail(int moduleId) throws LmsDaoException;
    List<CourseVO> getStudentCoursesModules(int courseSessionId) throws LmsDaoException;
    List<CourseVO> getTeacherCoursesModules(int courseSessionId,int schoolId,int classId,int homeRoomMstrId) throws LmsDaoException;
    List<CourseVO> getTeacherCoursesModules(int courseSessionId,int moduleId) throws LmsDaoException;
    //Get module details service -Resources & Comments
    List<ResourseVO> getStudentResourcesWithRating(int moduleId,int userId) throws LmsDaoException;
    List<ResourseVO> getStudentResources(int moduleId) throws LmsDaoException;
    List<ResourseVO> getStudentResources(int courseId,int moduleId) throws LmsDaoException;
    List<ResourseVO> getStudentResourcesForRating(int userId,int courseId,int moduleId,String searchText,int moduleSessionId) throws LmsDaoException;
    List<ResourseVO> getStudentResources(int userId,int courseId,int moduleId,String searchText,int moduleSessionId) throws LmsDaoException;
    List<ResourseVO> getStudentResourcesWeb(int userId,String courseId,String moduleId,String searchText) throws LmsDaoException;
    List<ResourseVO> getTeacherModuleResources(int moduleSessionId) throws LmsDaoException;
    
    List<CommentVO> getResourceCommentsForRating(int userId,int resourceId) throws LmsDaoException;
    List<CommentVO> getResourceComments(int userId,int resourceId) throws LmsDaoException;
    List<CommentVO> getResourceComments(int resourceId) throws LmsDaoException;
	List<CommentVO> getResourceChildCommentsList(int commentId)throws LmsDaoException;
	List<CommentVO> getResourceChildCommentsListForRating(int userId, int commentId)throws LmsDaoException;
	List<CommentVO> getResourceChildCommentsList(int userId, int commentId)throws LmsDaoException;
    List<ResourseVO> getRelatedResources(int resourceId) throws LmsDaoException;
	List<ResourseVO> getRelatedResources(int resourceId, String metaData) throws LmsDaoException;

    //Get assignments service -Resources & Comments
    List<AssignmentVO> getStudentAssignments(int courseId,int moduleId,int userId) throws LmsDaoException;

    List<AssignmentVO> getStudentAssignments(int userId) throws LmsDaoException;
    AssignmentVO getAssignmentDetail(int userId,int assignmentId) throws LmsDaoException;
    //Phase-3 methods for getting assignment questions
    List<AssignmentQuestionVO> getAssignmentQuestionAnswers(AssignmentVO asignment) throws LmsDaoException;

    List<AssignmentVO> getStudentAssignments(int userId,String searchText) throws LmsDaoException;
    List<AssignmentVO> getTeacherAssignments(int schoolId ,int classId ,int hrmId ,int courseId ,int moduleId ,int status,int userId,String searchText) throws LmsDaoException;
    
    List<AssignmentVO> getStudentAssignmentsByModuleId(int moduleId) throws LmsDaoException;
    List<AssignmentVO> getStudentAssignmentsByModuleId(String userName,int moduleId) throws LmsDaoException;
    
    List<ResourseVO> getAssignmentsResources(int userId,int assignmentDtlId) throws LmsDaoException;
    List<ResourseVO> getAssignmentsResources(int assignmentDtlId) throws LmsDaoException;

    
    
    boolean updateTeacherCourseSession(TeacherCourseSessionVO  vo)throws LmsDaoException;
    /**
     * This method is used for save user login
     * @param vo 
     */

    void saveTeacherCourseSession(TeacherCourseSessionVO  vo)throws LmsDaoException;
    /**
     * This method  used for delete
     * @param vo
     * @return true/false
     */

    boolean deleteTeacherCourseSession(TeacherCourseSessionVO  vo)throws LmsDaoException;
    
    /**
     * This method used for get user login id.
     * @param id
     * @return teacherDtls
     */

    TeacherCourseSessionVO  getTeacherCourseSession(int id)throws LmsDaoException;

    /**
     * 
     * @return
     */
    List<TeacherCourseSessionVO > getTeacherCourseSessionList()throws LmsDaoException;
    
    /**
     * This method  used for get AssignmentList by moduleId
     * @param vo
     * @return assignmentList
     */
	List<AssignmentVO> getAssignmentsByModuleId(int moduleId)throws LmsDaoException;
	
	 /**
     * This method  used for get studentList by assignmentId
     * @param vo
     * @return assignmentList
     */
	List<AssignmentVO> getStudentsByAssignmentId(int assignmentId) throws LmsDaoException;


	List<AssignmentVO> getStudentsByAssignmentId(int schoolId ,int classId ,int hrmId ,int courseId ,int moduleId ,int userId,int assignmentId) throws LmsDaoException;
	
	List<AssignmentVO> getAssignments(int schoolId, int classId, int hrmId,int courseId, int moduleId, int userId) throws LmsDaoException;
	
	List<AssignmentVO> getStudentAssignmentsByModule(int moduleId, int userId) throws LmsDaoException;
	String getCourseReviewGrade(int avgAssignmentReviewGrade) throws LmsDaoException;
	
	List<CourseVO> getAvailableCourses(int userId, int schoolId, int classId) throws LmsDaoException;
	List<CourseVO> getAvailableCourseModules(String courseId)throws LmsDaoException;
	List<CourseVO> getAvailableCoursesSessions(String courseId,int userId)throws LmsDaoException;
	List<CourseVO> getRequestedCourses(int userId, int schoolId, int classId)throws LmsDaoException;
	int retryCourse(int userId, int schoolId, int hrmId, int courseId)throws LmsDaoException;
    
}
