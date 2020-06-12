package bg.sofia.uni.fmi.mjt.spotify.enums;

public enum Command {

	CONNECT("connect"), REGISTER("register"), LOGIN("login"), SEARCH("search"), TOP("top"), CREATE_PLAYLIST("create-playlist"),
	ADD_SONG_TO("add-song-to"), SHOW_PLAYLIST("show-playlist"), PLAY("play"), STOP("stop"), DISCONNECT("disconnect");
	
	private final String command;

	Command(String command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		
		return command;		
	}

}