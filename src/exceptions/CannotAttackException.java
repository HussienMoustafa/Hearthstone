package exceptions;

@SuppressWarnings("serial")
public class CannotAttackException extends HearthstoneException {
	
	public CannotAttackException() {
		
	}
	
	public CannotAttackException(String message) {
		super(message);
	}

}
