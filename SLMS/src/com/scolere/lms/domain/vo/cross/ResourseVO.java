/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.domain.vo.cross;

import java.math.BigDecimal;

/**
 *
 * @author dell
 */
public class ResourseVO {
    private int resourceSessionId;
    private int resourceId;
    private String resourceName;
    private int moduleSessionId;
    private String moduleName;
    private String moduleDesc;
    private String resourceUrl;
    private String thumbUrl="default.png";
    private String resourceDesc;
    private String startedOn;
    private String completedOn;
    private String uploadedBy;
    private String uploadedDate;
    private String authorName;
    private String authorImg;
    private String moduleStatus;
    private int likeCounts;
    private int shareCounts;
    private int commentCounts;
    private boolean isLiked;
    private String completedStatus;
    private BigDecimal rating;
    private BigDecimal avgRating;
    
    private String moduleAssignmentsEnableAllStatus;
    private String moduleAssignmentsCancelAllStatus;
    private int resourceTypeId;
    
    
    
    
   	public int getResourceSessionId() {
		return resourceSessionId;
	}

	public void setResourceSessionId(int resourceSessionId) {
		this.resourceSessionId = resourceSessionId;
	}

	public String getCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(String completedStatus) {
		this.completedStatus = completedStatus;
	}

	public boolean isIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
    
    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
   
    
    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public int getShareCounts() {
        return shareCounts;
    }

    public void setShareCounts(int shareCounts) {
        this.shareCounts = shareCounts;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }
   
    
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public int getModuleSessionId() {
		return moduleSessionId;
	}

	public void setModuleSessionId(int moduleSessionId) {
		this.moduleSessionId = moduleSessionId;
	}

	public String getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}

	public String getModuleAssignmentsEnableAllStatus() {
		return moduleAssignmentsEnableAllStatus;
	}

	public void setModuleAssignmentsEnableAllStatus(String moduleAssignmentsEnableAllStatus) {
		this.moduleAssignmentsEnableAllStatus = moduleAssignmentsEnableAllStatus;
	}

	public String getModuleAssignmentsCancelAllStatus() {
		return moduleAssignmentsCancelAllStatus;
	}

	public void setModuleAssignmentsCancelAllStatus(String moduleAssignmentsCancelAllStatus) {
		this.moduleAssignmentsCancelAllStatus = moduleAssignmentsCancelAllStatus;
	}

	public int getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(int resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

    
    
}
