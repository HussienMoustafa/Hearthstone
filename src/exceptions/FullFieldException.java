package exceptions;

@SuppressWarnings("serial")
public class FullFieldException extends HearthstoneException {
	
	public FullFieldException() {}
	
	public FullFieldException(String message) {
		super(message);
	}

}
