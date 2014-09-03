package caillou.company.clonemanager.background.exception;

/**
 * @author pierre
 *
 */
public class OrganizerException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -1961395844481344437L;

	public OrganizerException(){
		super();
	}
	
	public OrganizerException(String message){
		super(message);
	}
	
	public OrganizerException(Throwable cause){
		super(cause);
	}
	
	public OrganizerException(String message, Throwable cause){
		super(message, cause);
	}
	
	public OrganizerException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
}
