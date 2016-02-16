/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.application.rest.bus.iface;

import com.scolere.lms.application.rest.exceptions.RestBusException;
import com.scolere.lms.application.rest.vo.request.CommonRequest;
import com.scolere.lms.application.rest.vo.response.CommonResponse;
import com.scolere.lms.application.rest.vo.response.SearchResponse;

/**
 *
 * @author dell
 */

public interface CommonBusIface {

    public CommonResponse setRating(CommonRequest req) throws RestBusException;
    public CommonResponse getRating(CommonRequest req) throws RestBusException;
	
	SearchResponse search(CommonRequest req) throws RestBusException;
	SearchResponse search(CommonRequest req,String category) throws RestBusException;
	
    CommonResponse getSchoolMasterData(int teacherId) throws RestBusException;
    CommonResponse getSchoolMasterData() throws RestBusException;
    
    CommonResponse getFeedsListWithRating(CommonRequest req) throws RestBusException;
    CommonResponse getFeedsList(CommonRequest req) throws RestBusException;
    CommonResponse getNotificationsList(CommonRequest req) throws RestBusException;
    CommonResponse getFeedComments(CommonRequest req) throws RestBusException;
    CommonResponse getFeedDetailForRating(int userId,int feedId) throws RestBusException;
    CommonResponse getFeedDetail(int userId,int feedId) throws RestBusException;
    
    CommonResponse getCourseDetail(int feedId) throws RestBusException;
    CommonResponse getModuleDetail(int feedId) throws RestBusException;
    CommonResponse getResourseDetail(int feedId) throws RestBusException;
    CommonResponse getUserDetail(int userId) throws RestBusException;
    CommonResponse getAssignmentDetail(int feedId) throws RestBusException;
    
    CommonResponse commentOnFeed(CommonRequest req) throws RestBusException;
    CommonResponse likeOnFeed(int userId,int feedId) throws RestBusException;
    public CommonResponse likeOnComment(int userId, int commentId) throws RestBusException;
    public CommonResponse commentOnComment(CommonRequest req) throws RestBusException;

    public CommonResponse updateNotificationStatus(int userId,int feedId,String status) throws RestBusException;
    
}
