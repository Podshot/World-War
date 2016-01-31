package io.github.podshot.WorldWar.internals;

public class ConfigInternals {

	private static boolean aatoe;
	private static boolean huolagw;
	private static boolean announce;
	private static boolean dpahboe;
	private static String dsts;
	
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

	public static void setDPAHBOE(boolean bool) {
		dpahboe = bool;
	}
	
	public static boolean getDPAHBOE() {
		return dpahboe;
	}

	public static Object getDevelopmentStageToListen() {
		return dsts;
	}
	
	public static void setDevelopmentStageToListen(String stage) {
		dsts = stage;
	}

}
