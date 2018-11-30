package bg.sofia.uni.fmi.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class Bug extends Issue {

	public Bug(IssuePriority priority, Component component, User reporter, String description)
			throws InvalidReporterException {
		super(priority, component, reporter, description);

		this.type = IssueType.BUG;
		this.dueTime = null;
	}

	@Override
	public IssueType getType() {
		return this.type;
	}
	
	@Override
	public LocalDateTime getDueTime() {
		
		return this.dueTime;
	}

}
