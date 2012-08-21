/**
 * 
 */
package com.tmm.enterprise.microblog.core.exception;

/**
 * @author robert.hinds
 * 
 */
public enum ButterflyExceptionCode {

	USER001_INVALIDUSER("USER001", "Invalid Account/Person Object - Account object must be linked 1-to-1 with Person object"), USER002_INVALIDUSERID(
			"USER002", "Invalid Person ID - Error caught attempting to parse the ID"), USER003_INVALIDUSER("USER003",
			"Invalid Person - Attempting to create ToDo for invalid Person"), CONTACT001_CONTACTABLENOTFOUND("CONTACT001",
			"Contactable not found - Team/User not found matchin provided name"), CONTACT003_UNSUPPORTEDCONTACTABLE("CONTACT003",
			"Unsupported Contactable type referenced"), CONTACT002_ERRORMAKINGCONNECTION("CONTACT002", "Error making connection"), SEARCH001_INVALIDSEARCHCONTEXT(
			"SEARCH001", "Error performing activity search");

	private String code;
	private String description;

	private ButterflyExceptionCode(String c, String desc) {
		setCode(c);
		setDescription(desc);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}