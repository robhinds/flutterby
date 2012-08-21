/**
 * 
 */
package com.tmm.enterprise.microblog.core.exception;

/**
 * @author robert.hinds
 * 
 * Custom Exception for the application - each one will have a unique exception 
 * code to help identify exactly where the exception was thrown
 *
 */
public class ButterflyException extends Exception {

	private static final long serialVersionUID = -7989054432428690026L;
	
	private ButterflyExceptionCode exceptionCode;	//error code to identify where in the code has been thrown
	private Throwable cause;			//optional cause (if having caught another exception)
	private String contextualMessage;	//optional additional description of error

	public ButterflyException(ButterflyExceptionCode theExceptionCode, Throwable theCause) {
		super(theExceptionCode.getDescription());
		setExceptionCode(theExceptionCode);
		setCause(theCause);
	}

	public ButterflyException(ButterflyExceptionCode theExceptionCode,
			String theContextualMessage) {
		super(theExceptionCode.getDescription() + (theContextualMessage!=null ? theContextualMessage : "."));
		setExceptionCode(theExceptionCode);
		setContextualMessage(theContextualMessage);
	}

	public ButterflyException(ButterflyExceptionCode theExceptionCode,
			String theContextualMessage, Throwable theCause) {
		this(theExceptionCode,theContextualMessage);
		setCause(theCause);
	}

	

	public ButterflyExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	private void setExceptionCode(ButterflyExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getContextualMessage() {
		return contextualMessage;
	}

	private void setContextualMessage(String contextualMessage) {
		this.contextualMessage = contextualMessage;
	}

	public Throwable getCause() {
		return cause;
	}

	private void setCause(Throwable cause) {
		this.cause = cause;
	}
}
