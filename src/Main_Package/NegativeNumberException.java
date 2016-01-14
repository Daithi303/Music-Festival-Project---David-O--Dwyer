package Main_Package;

public class NegativeNumberException extends Exception {

	private String message;
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NegativeNumberException() {

	}

	/**
	 * @param message
	 */
	public NegativeNumberException(String message) {
		super(message);
		this.message = message;
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "The number '"+message+"' is a negative number. Please re-enter a positive number.";
	}
}
