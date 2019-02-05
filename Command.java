
public enum Command {

	REGISTER("register"), LOGIN("login"), SEARCH("search"), TOP("top"), CREATE_PLAYLIST("create-playlist"),
	ADD_SONG_TO("add-song-to"), SHOW_PLAYLIST("show-playlist"), PLAY("play"), STOP("stop");
	// LOGOUT???

	private final String command;

	Command(String command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		
		return command;		
	}

}
