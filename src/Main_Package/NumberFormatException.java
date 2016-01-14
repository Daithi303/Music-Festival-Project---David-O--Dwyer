package Main_Package;

public class NumberFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NumberFormatException() {

	}

	/**
	 * @param message
	 */
	public NumberFormatException(String message) {
		super(message);

	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "That is not the correct numerical format. Please re-enter.";
	}
	

}
