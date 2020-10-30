package exceptions;

@SuppressWarnings("serial")
public class NotSummonedException extends HearthstoneException {
	
	public NotSummonedException() {
		
	}
	
	public NotSummonedException(String message) {
		super(message);
	}

}
