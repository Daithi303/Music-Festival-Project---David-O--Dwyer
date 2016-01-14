package Main_Package;

public class StringFormatException extends Exception {

	private String message;
	private String field;
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public StringFormatException() {

	}

	/**
	 * @param message
	 * @param field
	 */
	public StringFormatException(String message,String field) {
		super(message);
		this.message = message;
		this.field = field;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "An incorrect text format has been entered in the "+field+" field."
				+ "\n"+message;
	}
}
