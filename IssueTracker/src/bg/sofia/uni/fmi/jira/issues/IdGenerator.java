package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;

public class IdGenerator {

	private String id;
	private static int num = 0;

	public IdGenerator(Component component) {
		this.id = component.getShortName() + "-" + num;
		num++;
	}

	public String getId() {
		return this.id;
	}

}
