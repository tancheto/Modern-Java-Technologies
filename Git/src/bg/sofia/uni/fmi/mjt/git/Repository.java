package bg.sofia.uni.fmi.mjt.git;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Repository {

	private Branch masterBranch;
	private Branch currentBranch;

	private Map<String, Branch> repoBranches;

	private int counterChanges;

	public Repository() {

		// this.repoFiles = new HashMap<>();
		this.repoBranches = new HashMap<>();
		this.masterBranch = new Branch(new HashMap<String, Commit>(), "master");
		this.currentBranch = this.masterBranch;
		this.repoBranches.put("master", this.masterBranch);
		this.counterChanges = 0;
	}

	public Result add(String... files) {

		for (String newFile : files) {
			if (this.currentBranch.getStage().containsKey(newFile) || this.findFileInBranchBool(newFile)) {
				return new Result("'" + newFile + "'" + " already exists", false);
			}
		}

		for (String newFile : files) {
			this.currentBranch.getStage().put(newFile, new File(newFile));
			this.counterChanges++;
		}

		return new Result("added {} to stage", files, true);
	}

	public Result commit(String message) {

		if (this.counterChanges == 0) {

			return new Result("nothing to commit, working tree clean", false);
		}

		else {

			Commit newCommit = new Commit(message);

			newCommit.getCommitFiles().putAll(this.currentBranch.getStage());

			this.currentBranch.getStage().clear();

			this.currentBranch.addCommit(newCommit);

			String msg = this.counterChanges + " files changed";
			this.counterChanges = 0;
			return new Result(msg, true);
		}
	}

	public boolean findFileInBranchBool(String fileName) {

		Iterator<String> itCom = this.currentBranch.getBranchCommits().keySet().iterator();

		while (itCom.hasNext()) {

			String hash = itCom.next();
			if (this.currentBranch.getBranchCommits().get(hash).getCommitFiles().containsKey(fileName)) {

				return true;
			}
		}

		return false;
	}

	public String findFileInBranchHash(String fileName) {

		Iterator<String> itCom = this.currentBranch.getBranchCommits().keySet().iterator();

		while (itCom.hasNext()) {

			String hash = itCom.next();
			if (this.currentBranch.getBranchCommits().get(hash).getCommitFiles().containsKey(fileName)) {

				return hash;
			}
		}

		return null;
	}

	public Result remove(String... files) {

		for (String newFile : files) {

			if (!this.currentBranch.getStage().containsKey(newFile) && !this.findFileInBranchBool(newFile)) {

				return new Result("'" + newFile + "'" + " did not match any files", false);
			}
		}

		for (String newFile : files) {

			if (this.currentBranch.getStage().containsKey(newFile)) {
				this.currentBranch.getStage().remove(newFile);
				counterChanges--;
			}

			else {
				this.currentBranch.getBranchCommits().get(findFileInBranchHash(newFile)).getCommitFiles()
						.remove(newFile);
				counterChanges++;
			}

		}

		return new Result("added {} for removal", files, true);
	}

	public Commit getHead() {

		if (this.currentBranch.getCounterCommits() == 0) {

			return null;
		}

		else {

			return this.currentBranch.getLastCommit();
		}
	}

	public Result log() {

		if (this.currentBranch.getCounterCommits() == 0) {
			return new Result("branch " + this.currentBranch.getName() + " does not have any commits yet", false);
		}

		else {

			String msg = new String("");

			List<String> keyList = new ArrayList<>(this.currentBranch.getBranchCommits().keySet());

			int idx = this.currentBranch.getBranchCommits().size();

			for (int i = idx - 1; i >= 0; i--) {
				Commit commit = this.currentBranch.getBranchCommits().get(keyList.get(i));

				msg = msg + "commit " + commit.getHash() + "\nDate: " + commit.getDate() + "\n\n\t"
						+ commit.getMessage();

				if (i != 0) {
					msg = msg + "\n\n";
				}
			}

			return new Result(msg, true);
		}
	}

	public String getBranch() { 

		return this.currentBranch.getName();
	}

	public Result createBranch(String name) {

		if (this.repoBranches.containsKey(name)) {

			return new Result("branch " + name + " already exists", false);
		}

		else {

			Branch newBranch = new Branch(this.currentBranch.getBranchCommits(), name);

			this.repoBranches.put(newBranch.getName(), newBranch);

			return new Result("created branch " + name, true);
		}
	}

	public Result checkoutBranch(String name) {

		if (!repoBranches.containsKey(name)) {

			return new Result("branch " + name + " does not exist", false);
		}

		this.currentBranch = this.repoBranches.get(name);

		return new Result("switched to branch " + name, true);
	}

	public Result checkoutCommit(String hash) {

		if (!this.currentBranch.getBranchCommits().containsKey(hash)) {

			return new Result("commit " + hash + " does not exist", false);
		}

		Iterator<String> it = this.currentBranch.getBranchCommits().keySet().iterator();

		while (it.hasNext()) {
			if (it.next().equals(hash)) {
				break;
			}
		}

		while (it.hasNext()) {
			it.next();
			it.remove();
		}

		return new Result("HEAD is now at " + hash, true);

	}

}
