package io.github.podshot.internals;


public class Internals {

	private static boolean warDeclared = false;
	
	public static boolean isWarDeclared() {
		return warDeclared;
	}
	
	public static void setWarDeclared(boolean bool) {
		warDeclared = bool;
	}
}
