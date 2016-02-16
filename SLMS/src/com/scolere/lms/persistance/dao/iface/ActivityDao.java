/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.persistance.dao.iface;

import java.util.List;

import com.scolere.lms.domain.exception.LmsDaoException;
import com.scolere.lms.domain.vo.cross.UserVO;

/**
 *
 * @author dell
 */
public interface ActivityDao {

	/**
	 * Capture users activity.
	 * @param userId
	 * @param activityId
	 * @throws LmsDaoException
	 */
	void saveActivity(int userId,int activityId) throws LmsDaoException;
	
	void saveActivity(String userNm,int activityId) throws LmsDaoException;
	
    List<UserVO> getMostActivUsers() throws LmsDaoException;
    
}//End of class



