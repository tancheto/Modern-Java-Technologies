package bg.sofia.uni.fmi.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IIssue;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public abstract class Issue implements IIssue {

	private IdGenerator id;
	private IssuePriority priority;
	private IssueResolution resolution;
	private IssueStatus status;
	private Component component;
	private User reporter;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String description;

	protected IssueType type;
	protected LocalDateTime dueTime;

	public Issue(IssuePriority priority, Component component, User reporter, String description)
			throws InvalidReporterException {

		if (reporter == null) {
			throw new InvalidReporterException("Invalid reporter.");
		} else {
			this.reporter = reporter;
		}

		if (component == null) {
			throw new InvalidComponentException("Invalid component.");
		} else {
			this.component = component;
		}

		if (priority == null) {
			throw new InvalidPriorityException("Invalid priority.");
		} else {
			this.priority = priority;
		}

		if (description == null) {
			throw new InvalidDescriptionException("Invalid description.");
		} else {
			this.description = description;
		}

		this.id = new IdGenerator(component);
		this.resolution = IssueResolution.UNRESOLVED;
		this.status = IssueStatus.OPEN;
		this.createdAt = LocalDateTime.now();
		this.lastModifiedAt = LocalDateTime.now();
	}

	public String getId() {

		return this.id.getId();
	}

	public void resolve(IssueResolution resolution) {

		this.resolution = resolution;
		this.lastModifiedAt = LocalDateTime.now();
	}

	public void setStatus(IssueStatus status) {

		this.status = status;
		this.lastModifiedAt = LocalDateTime.now();
	}

	public abstract IssueType getType();

	public abstract LocalDateTime getDueTime();

	public Component getComponent() {

		return this.component;
	}

	public IssueStatus getStatus() {

		return this.status;
	}

	public IssuePriority getPriority() {

		return this.priority;
	}

	public IssueResolution getResolution() {

		return this.resolution;
	}

	public LocalDateTime getCreatedAt() {

		return this.createdAt;
	}

	public LocalDateTime getLastModifiedAt() {

		return this.lastModifiedAt;
	}

}
