package io.github.podshot.internals;

import java.util.Properties;

public class Internals {

	@Deprecated
	public static Properties playersTeamFile;
	@Deprecated
	public static Properties playersClassFile;
	public static boolean warDeclared = false;
	
	public static boolean isWarDeclared() {
		return warDeclared;
	}
	
	public static void setWarDeclared(boolean bool) {
		warDeclared = bool;
	}
}
