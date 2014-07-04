package io.github.podshot.handlers;

public enum PlayerClasses {
	
	Soldier("Soldier"), Spy("Spy"), Engineer("Engineer"), Builder("Builder"), Sniper("Sniper");
	
	private final String classChoosen;
	
	private PlayerClasses(final String text) {
		this.classChoosen = text;
	}
	
	@Override
	public String toString() {
		return this.classChoosen;
	}
}
