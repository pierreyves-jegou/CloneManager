package caillou.company.clonemanager.background.exception;

/**
 * @author pierre
 *
 */
public class CloneManagerException extends Exception {

    /**
     * Serial
     */
    private static final long serialVersionUID = -1961395844481344437L;

    private String technicalMessageException;

    public CloneManagerException() {
        super();
    }

    public CloneManagerException(String message, String technicalMessageException) {
        super(message);
        this.technicalMessageException = technicalMessageException;
    }

    public CloneManagerException(String message) {
        super(message);
    }

    public CloneManagerException(Throwable cause) {
        super(cause);
    }

    public CloneManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneManagerException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getTechnicalMessageException() {
        return technicalMessageException;
    }

}
