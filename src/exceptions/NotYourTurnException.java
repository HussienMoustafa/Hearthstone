package exceptions;

@SuppressWarnings("serial")
public class NotYourTurnException extends HearthstoneException {
	
	public NotYourTurnException() {
		
	}
	
	public NotYourTurnException(String message) {
		super(message);
	}

}
