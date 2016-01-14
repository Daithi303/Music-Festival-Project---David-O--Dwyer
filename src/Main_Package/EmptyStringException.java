package Main_Package;

public class EmptyStringException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public EmptyStringException() {

	}

	/**
	 * @param message
	 */
	public EmptyStringException(String message) {
		super(message);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "There is an empty field within in the form. Please re-enter.";
	}

}
