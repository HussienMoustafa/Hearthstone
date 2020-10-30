package exceptions;

@SuppressWarnings("serial")
public class InvalidTargetException extends HearthstoneException {
	
	public InvalidTargetException() {
		
	}
	
	public InvalidTargetException(String message) {
		super(message);
	}

}
