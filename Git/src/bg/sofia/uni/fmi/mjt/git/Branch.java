package bg.sofia.uni.fmi.mjt.git;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Branch {

	private String name;

	private Map<String, Commit> branchCommits;

	private Commit lastCommit;

	private Map<String, File> stage;

	public Branch(Map<String, Commit> commits, String name) {

		this.branchCommits = new LinkedHashMap<>();
		
		this.branchCommits.putAll(commits);

		this.stage = new HashMap<>();

		this.name = name;
	}

	public String getName() {

		return this.name;
	}

	public Map<String, Commit> getBranchCommits() {

		return this.branchCommits;
	}

	public Commit getLastCommit() {

		return lastCommit;
	}

	public void addCommit(Commit commit) {

		this.branchCommits.put(commit.getHash(), commit);
		this.lastCommit = commit;
	}

	public Map<String, File> getStage() {

		return this.stage;
	}

	public int getCounterCommits() {

		return this.branchCommits.size();
	}

}
