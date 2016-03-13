package io.github.podshot.WorldWar.api;

public enum WorldWarTeam {

	BLUE("Blue"), RED("Red");
	
	private final String team;
	
	private WorldWarTeam(final String team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		return team;
	}
}
