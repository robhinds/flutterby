/**
 * 
 */
package com.tmm.enterprise.microblog.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author robert.hinds
 *
 * Util class to assist with common user/account processing
 */
public class UserHelper {
	
	/**
	 * Method to determine if request is authenticated
	 * @param request
	 * @return
	 */
	public static boolean isAnonymousUser(HttpServletRequest request) {
		if (request.getRemoteUser() == null){
			return true;
		}
		else{
			return false;
		}
	}

}
