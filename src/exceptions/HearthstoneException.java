package exceptions;

@SuppressWarnings("serial")
public abstract class HearthstoneException extends Exception {
	
	public HearthstoneException() {
		
	}
	
	public HearthstoneException(String message) {
		super(message);
	}

}
