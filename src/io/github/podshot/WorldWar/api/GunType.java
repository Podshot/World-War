package io.github.podshot.WorldWar.api;

public enum GunType {
	
	RIFLE("Rifle"), ROCKET_LAUNCHER("Rocket-Launcher"), SHOTGUN("Shotgun"), PISTOL("Pistol");
	
	private final String gun;
	
	private GunType(final String gun) {
		this.gun = gun;
	}
	
	@Override
	public String toString() {
		return gun;
	}
}
