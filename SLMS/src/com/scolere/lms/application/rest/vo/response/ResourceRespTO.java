/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.vo.response;

import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author dell
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class ResourceRespTO extends CountsTO{
    private int resourceSessionId;
    private String resourceId;
    private String resourceName;
    private String resourceUrl;
    private String resourceDesc;
    private int moduleSessionId;
    private String moduleName;
    private String moduleDesc;
    private String startedOn;
    private String moduleStatus;
    private String completedOn;
    private String uploadedBy;
    private String uploadedDate;
    private String authorName;
    private String authorImg;
    private String thumbImg;
    private List<CommentRespTO> commentList;
    private List<ResourceRespTO> relatedVideoList;
    private String completedStatus;
    
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

	public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public List<CommentRespTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentRespTO> commentList) {
        this.commentList = commentList;
    }
    
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
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
    
    public List<ResourceRespTO> getRelatedVideoList() {
        return relatedVideoList;
    }

    public void setRelatedVideoList(List<ResourceRespTO> relatedVideoList) {
        this.relatedVideoList = relatedVideoList;
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
