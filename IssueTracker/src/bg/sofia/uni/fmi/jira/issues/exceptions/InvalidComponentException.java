package bg.sofia.uni.fmi.jira.issues.exceptions;

public class InvalidComponentException extends RuntimeException {
	
	public InvalidComponentException(String errorMessage)
	{
		super(errorMessage);
	}

}
