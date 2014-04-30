package io.github.podshot.internals;

public class ConfigInternals {

	private static boolean aatoe;
	private static boolean huolagw;
	private static boolean announce;
	
	public static void setAATOE(boolean bool) {
		aatoe = bool;
	}
	
	public static boolean getAATOE() {
		return aatoe;
	}
	
	public static void setHUOLAQW(boolean bool) {
		huolagw = bool;
	}
	
	public static boolean getHUOLAQW() {
		return huolagw;
	}

	public static void setAnnounce(boolean bool) {
		announce = bool;		
	}
	
	public static boolean getAnnounce() {
		return announce;
	}

}
