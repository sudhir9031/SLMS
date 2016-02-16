/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.vo.response;

import java.math.BigDecimal;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author dell
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class CommonResponse extends CommonRespTO{
    
    private List<SchoolRespTO> schoolList;
    private List<FeedRespTO> feedList;
    
    private UserResponse userDetail;
    private CourseRespTO courseDetail;
    private ModuleRespTO moduleDetail;
    private AssignmentRespTO assignmentDetail;
    private ResourceRespTO resourceDetail;
    private FeedRespTO feedDetail;
    
    private long totalRecords; //pagination parameter
    private List<CommentRespTO> commentsList;
    //Unread feed counts
    private long unreadNotificationCount; //pagination parameter
    //Rating parameters
    private BigDecimal rating;
    private BigDecimal avgRating;
    
    //Getter-setters
    
    
    public UserResponse getUserDetail() {
        return userDetail;
    }
	public long getUnreadNotificationCount() {
		return unreadNotificationCount;
	}
	public void setUnreadNotificationCount(long unreadNotificationCount) {
		this.unreadNotificationCount = unreadNotificationCount;
	}
	public FeedRespTO getFeedDetail() {
		return feedDetail;
	}
	public void setFeedDetail(FeedRespTO feedDetail) {
		this.feedDetail = feedDetail;
	}
	public List<CommentRespTO> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<CommentRespTO> commentsList) {
		this.commentsList = commentsList;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setUserDetail(UserResponse userDetail) {
        this.userDetail = userDetail;
    }

    public CourseRespTO getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseRespTO courseDetail) {
        this.courseDetail = courseDetail;
    }

    public ModuleRespTO getModuleDetail() {
        return moduleDetail;
    }

    public void setModuleDetail(ModuleRespTO moduleDetail) {
        this.moduleDetail = moduleDetail;
    }

    public AssignmentRespTO getAssignmentDetail() {
        return assignmentDetail;
    }

    public void setAssignmentDetail(AssignmentRespTO assignmentDetail) {
        this.assignmentDetail = assignmentDetail;
    }

    public ResourceRespTO getResourceDetail() {
        return resourceDetail;
    }

    public void setResourceDetail(ResourceRespTO resourceDetail) {
        this.resourceDetail = resourceDetail;
    }
    
    public List<FeedRespTO> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<FeedRespTO> feedList) {
        this.feedList = feedList;
    }
    
    public List<SchoolRespTO> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<SchoolRespTO> schoolList) {
        this.schoolList = schoolList;
    }
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	public BigDecimal getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(BigDecimal avgRating) {
		this.avgRating = avgRating;
	}

    
}//End of class
