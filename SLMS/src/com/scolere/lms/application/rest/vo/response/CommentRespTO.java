/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.vo.response;

import java.util.List;

/**
 *
 * @author dell
 */
public class CommentRespTO extends CountsTO{
    private int commentId;
    private int parentCommentId;
    private String commentBy;
    private int commentById;
    private String commentByImage;
    private String commentTxt;
    private String commentDate;
    private List<CommentRespTO> subComments;

    
    
    public List<CommentRespTO> getSubComments() {
		return subComments;
	}

	public void setSubComments(List<CommentRespTO> subComments) {
		this.subComments = subComments;
	}

	public String getCommentByImage() {
        return commentByImage;
    }

    public void setCommentByImage(String commentByImage) {
        this.commentByImage = commentByImage;
    }
    
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public String getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

	public int getCommentById() {
		return commentById;
	}

	public void setCommentById(int commentById) {
		this.commentById = commentById;
	}

       
}
