package io.github.podshot.internals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Internals {

	@Deprecated
	public static Properties playersTeamFile;
	@Deprecated
	public static Properties playersClassFile;
	private static boolean warDeclared = false;
	private static List<String> squadGUIOpen = new ArrayList<String>();
	
	public static boolean isWarDeclared() {
		return warDeclared;
	}
	
	public static void setWarDeclared(boolean bool) {
		warDeclared = bool;
	}
	
	public static void addPlayer(String name) {
		squadGUIOpen.add(name);
	}
	
	public static void removePlayer(String name) {
		squadGUIOpen.remove(name);
	}
	
	public static boolean isPlayerInList(String name) {
		boolean ret = false;
		if (squadGUIOpen.contains(name)) {
			ret = true;
		}
		return ret;
	}
}
