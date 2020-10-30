package exceptions;

@SuppressWarnings("serial")
public class NotEnoughManaException extends HearthstoneException {
	
	public NotEnoughManaException() {
		
	}
	
	public NotEnoughManaException(String message) {
		super(message);
	}

}
