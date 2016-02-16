/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.iface;


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
import java.util.List;

/**
 *
 * @author dell
 */
public interface CourseServiceIface {
	
	//Assignment rating methods
	List<CommonKeyValueVO> getRatingMasterData(int schoolId) throws LmsServiceException;
	List<CommonKeyValueVO> getRatingValuesMasterData(int schoolId) throws LmsServiceException;
	List<CommonKeyValueVO> getRatingData(int assignmentResourceTxnId) throws LmsServiceException;
	int setRatingData(int userId,int assignmentResourceTxnId,List<CommonKeyValueVO> list) throws LmsServiceException;
	
    // Comment & Likes
    boolean saveResourceComment(int commentBy,int resourceId,String commentTxt) throws LmsServiceException;
    boolean saveCommentComment(int commentBy,int commentId,String commentTxt) throws LmsServiceException;
    boolean saveCommentLike(int commentBy,int commentId) throws LmsServiceException;
    boolean saveResourceLike(int commentBy,int resourceId) throws LmsServiceException;
    
    // TeacherCourseSession
    List<CourseVO> getTeacherCourses(int teacherId) throws LmsServiceException;
    List<CourseVO> getTeacherCourseOrganisations(int teacherId,int courseId) throws LmsServiceException;
    
    CourseVO getStudentCourseDetail(int courseId) throws LmsServiceException;
    List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId,int moduleId) throws LmsServiceException;
    List<CourseVO> getTeacherCourses(int userId,int schoolId,int classId,int hrmId,int courseId) throws LmsServiceException;
    List<CourseVO> getStudentCourses(int userId,String searchText) throws LmsServiceException;
    List<CourseVO> getStudentCourses(String userNm,String searchText) throws LmsServiceException;
    CourseVO getStudentModuleDetail(int moduleId) throws LmsServiceException;
    List<CourseVO> getStudentCoursesModules(int courseSessionId) throws LmsServiceException;
    List<CourseVO> getTeacherCoursesModules(int courseSessionId, int schoolId,int classId, int homeRoomMstrId) throws LmsServiceException;
    List<CourseVO> getTeacherCoursesModules(int courseSessionId,int moduleId) throws LmsServiceException;
    //Get module details service -Resources & Comments
    List<ResourseVO> getStudentResourcesWithRating(int moduleId) throws LmsServiceException;
    List<ResourseVO> getStudentResourcesForRating(int moduleId,int userId) throws LmsServiceException;
    List<ResourseVO> getStudentResources(int moduleId) throws LmsServiceException;
    List<ResourseVO> getStudentResources(int courseId,int moduleId) throws LmsServiceException;
    List<ResourseVO> getStudentResourcesForRating(int userId,int courseId,int moduleId,String searchText,int moduleSessionId) throws LmsServiceException;
    List<ResourseVO> getStudentResources(int userId,int courseId,int moduleId,String searchText,int moduleSessionId) throws LmsServiceException;
    List<ResourseVO> getStudentResourcesWeb(int userId,String courseId,String moduleId,String searchText) throws LmsServiceException;
    List<ResourseVO> getTeacherModuleResources(int moduleSessionId) throws LmsServiceException;
    
    List<CommentVO> getResourceCommentsForRating(int userId,int resourceId) throws LmsServiceException;
    List<CommentVO> getResourceComments(int userId,int resourceId) throws LmsServiceException;
    List<CommentVO> getResourceComments(int resourceId) throws LmsServiceException;
	List<CommentVO> getResourceChildComments(int commentId)throws LmsServiceException;
	List<CommentVO> getResourceChildCommentsForRating(int userId, int commentId)throws LmsServiceException;
	List<CommentVO> getResourceChildComments(int userId, int commentId)throws LmsServiceException;

    List<ResourseVO> getRelatedResources(int resourceId,String metadata) throws LmsServiceException;
    List<ResourseVO> getRelatedResources(int resourceId) throws LmsServiceException;
    //Get assignments service -Resources & Comments
    List<AssignmentVO> getStudentAssignments(int courseId,int moduleId,int userId) throws LmsServiceException;
    List<AssignmentVO> getStudentAssignmentsByModuleId(int moduleId) throws LmsServiceException;
    List<AssignmentVO> getStudentAssignmentsByModuleId(String userName,int moduleId) throws LmsServiceException;
    
    List<AssignmentVO> getStudentAssignments(int userId) throws LmsServiceException;
    AssignmentVO getAssignmentDetail(int userId,int assignmentId) throws LmsServiceException;
    //Phase-3 methods for getting assignment questions
    List<AssignmentQuestionVO> getAssignmentQuestionAnswers(AssignmentVO asignment) throws LmsServiceException;
    
    List<AssignmentVO> getStudentAssignments(int userId,String searchText) throws LmsServiceException;
    List<AssignmentVO> getTeacherAssignments(int schoolId ,int classId ,int hrmId ,int courseId ,int moduleId ,int status,int userId,String searchText) throws LmsServiceException;
    
    List<ResourseVO> getAssignmentsResources(int assignmentDtlId) throws LmsServiceException;
    List<ResourseVO> getAssignmentsResources(int userId,int assignmentDtlId) throws LmsServiceException;
    
    boolean updateTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException;

    void saveTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException;

    boolean deleteTeacherCourseSession(TeacherCourseSessionVO vo) throws LmsServiceException;

    TeacherCourseSessionVO getTeacherCourseSession(int id) throws LmsServiceException;

    List<TeacherCourseSessionVO> getTeacherCourseSessionList() throws LmsServiceException;

    
    //TeacherCourseSessionDtls
    boolean updateTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException;

    void saveTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException;

    boolean deleteTeacherCourseSessionDtls(TeacherCourseSessionDtlsVO vo) throws LmsServiceException;

    TeacherCourseSessionDtlsVO getTeacherCourseSessionDtls(int id) throws LmsServiceException;

    List<TeacherCourseSessionDtlsVO> getTeacherCourseSessionDtlsList() throws LmsServiceException;

    
    //TeacherCourse
    boolean updateTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException;

    void saveTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException;

    boolean deleteTeacherCourseVO(TeacherCourseVO vo) throws LmsServiceException;

    TeacherCourseVO getTeacherCourse(int id) throws LmsServiceException;

    List<TeacherCourseVO> getTeacherCourseList() throws LmsServiceException;

	List<AssignmentVO> getAssignmentsByModuleId(int moduleId)throws LmsServiceException;
	List<AssignmentVO> getAssignments(int schoolId ,int classId ,int hrmId ,int courseId ,int moduleId ,int userId)throws LmsServiceException;
	List<AssignmentVO> getStudentsByAssignmentId(int assignmentId)throws LmsServiceException;
	List<AssignmentVO> getStudentsByAssignmentId(int schoolId ,int classId ,int hrmId ,int courseId ,int moduleId ,int userId,int assignmentId)throws LmsServiceException;
	List<AssignmentVO> getStudentAssignmentsByModule(int moduleId, int userId)throws LmsServiceException;
	String getCourseReviewGrade(int avgAssignmentReviewGrade)throws LmsServiceException; 

    int uploadAssignment(int assignmentId,String resourceName,String resourceAuthor, String resourceDesc,int userId, String descTxt, String url, String thumbUrl, String authorImgUrl) throws LmsServiceException;
	int submitAssignment(int assignmentResourceTxn,int submittedBy,int assignmentTypeId,List<AssignmentQuestionVO> list,int schoolId,int courseId)throws LmsServiceException;
	//Course Session related methods
	List<CourseVO> getAvailableCourses(int userId, int schoolId,int classId)throws LmsServiceException;
	List<CourseVO> getAvailableCourseModules(String courseId)throws LmsServiceException;
	List<CourseVO> getAvailableCoursesSessions(String courseId,int userId)throws LmsServiceException;
	List<CourseVO> getRequestedCourses(int userId, int schoolId, int classId)throws LmsServiceException;
	int retryCourse(int userId, int schoolId, int hrmId, int courseId)throws LmsServiceException;
    
}//End of class
