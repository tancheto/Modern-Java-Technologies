package bg.sofia.uni.fmi.jira.issues.exceptions;

public class InvalidDescriptionException extends RuntimeException {

	public InvalidDescriptionException(String errorMessage)
	{
		super(errorMessage);
	}

}
