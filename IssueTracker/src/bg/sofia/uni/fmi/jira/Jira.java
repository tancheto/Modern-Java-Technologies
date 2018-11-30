package bg.sofia.uni.fmi.jira;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Issue;

public class Jira implements IssueTracker {

	private Issue[] issues;

	public Jira(Issue[] issues) {
		this.issues = issues;
	}

	public Issue[] findAll(Component component, IssueStatus status) {
		
		int index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && status == issue.getStatus()) {
				index++;
			}
		}

		Issue[] statusIssues = new Issue[index];

		index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && status == issue.getStatus()) {
				statusIssues[index] = issue;
				index++;
			}
		}

		return statusIssues;
	}

	public Issue[] findAll(Component component, IssuePriority priority) {

		int index = 0;

		for (Issue issue : this.issues) {
			if (issue.getComponent().equals(component) && priority == issue.getPriority()) {
				index++;
			}
		}
		
		Issue[] priorityIssues = new Issue[index];

		index = 0;

		for (Issue issue : this.issues) {
			if (issue.getComponent().equals(component) && priority == issue.getPriority()) {
				priorityIssues[index] = issue;
				index++;
			}
		}

		return priorityIssues;
	}

	public Issue[] findAll(Component component, IssueType type) {

		int index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && type == issue.getType()) {
				index++;
			}
		}

		Issue[] typeIssues = new Issue[index];

		index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && type == issue.getType()) {
				typeIssues[index] = issue;
				index++;
			}
		}

		return typeIssues;
	}

	public Issue[] findAll(Component component, IssueResolution resolution) {
		
		int index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && resolution == issue.getResolution()) {
				index++;
			}
		}

		Issue[] resolutionIssues = new Issue[index];
		index = 0;

		for (Issue issue : issues) {
			if (issue.getComponent().equals(component) && resolution == issue.getResolution()) {
				resolutionIssues[index] = issue;
				index++;
			}
		}

		return resolutionIssues;
	}

	public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
		
		int index = 0;

		for (Issue issue : issues) {
			if (issue.getCreatedAt().isAfter(startTime) && issue.getCreatedAt().isBefore(endTime)) {
				index++;
			}
		}

		Issue[] createdBetween = new Issue[index];

		index = 0;

		for (Issue issue : issues) {
			if (issue.getCreatedAt().isAfter(startTime) && issue.getCreatedAt().isBefore(endTime)) {
				createdBetween[index] = issue;
				index++;
			}
		}

		return createdBetween;
	}

	public Issue[] findAllBefore(LocalDateTime dueTime) {
		
		int index = 0;

		for (Issue issue : issues) {
			if (issue.getDueTime().isBefore(dueTime)) {
				index++;
			}
		}

		Issue[] allBefore = new Issue[index];
		index = 0;

		for (Issue issue : issues) {
			if (issue.getCreatedAt().isBefore(dueTime)) {
				allBefore[index] = issue;
				index++;
			}
		}

		return allBefore;
	}

}
