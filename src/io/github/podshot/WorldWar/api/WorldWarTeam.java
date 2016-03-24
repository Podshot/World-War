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
	
	public WorldWarTeam fromString(String str) {
		if (str.equals("Blue")) {
			return WorldWarTeam.BLUE;
		} else if (str.equals("Red")) {
			return WorldWarTeam.RED;
		} else {
			return null;
		}
	}
}
