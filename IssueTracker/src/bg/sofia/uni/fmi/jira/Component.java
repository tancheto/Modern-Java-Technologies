package bg.sofia.uni.fmi.jira;

public class Component {

	private String name;
	private String shortName;
	private User creator;

	public Component(String name, String shortName, User creator) {
		this.name = name;
		this.shortName = shortName;
		this.creator = creator;
	}

	public String getShortName() {
		return this.shortName;
	}

	public User getCreator() {

		return this.creator;
	}

	public String getName() {

		return this.name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Component) {
			Component comp = (Component) obj;
			return (comp.getShortName().equals(this.getShortName()) && comp.getName().equals(this.getName()));
		}
		return false;
	}

}
