package io.github.podshot.WorldWar.api;

public enum Team {

	BLUE("Blue"), RED("Red");
	
	private final String team;
	
	private Team(final String team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		return team;
	}
}
