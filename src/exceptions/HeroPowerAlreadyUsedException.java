package exceptions;

@SuppressWarnings("serial")
public class HeroPowerAlreadyUsedException extends HearthstoneException {
	
	public HeroPowerAlreadyUsedException() {}
	
	public HeroPowerAlreadyUsedException(String message) {
		super(message);
	}

}
