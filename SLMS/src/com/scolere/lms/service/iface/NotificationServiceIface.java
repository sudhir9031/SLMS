/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scolere.lms.service.iface;

import java.util.List;

import com.scolere.lms.domain.exception.LmsServiceException;
import com.scolere.lms.domain.vo.cross.NotificationVO;


/**
 *
 * @author dell
 */
public interface NotificationServiceIface {
        
	int saveUserDevice(NotificationVO vo)throws LmsServiceException;
	boolean isDeviceRegistered(NotificationVO vo)throws LmsServiceException;
	List<NotificationVO> getUserDevicesList(String userStr)throws LmsServiceException;
	List<String> getUserDevicesTokens(String userStr)throws LmsServiceException;
	void pushNotifications(String message,String userStr)throws LmsServiceException;
	
}
