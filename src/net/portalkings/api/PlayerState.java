package net.portalkings.api;

public enum PlayerState {
	SPECTATOR("Spectator"), INGAME("Ingame"), INLOBBY("Inlobby");
	String state;

	PlayerState(String state) {
		this.state = state;
	}

}
