package io.github.podshot.files;

import io.github.podshot.WorldWar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SavePlayerData {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static File teamPropF;
	private static File classPropF;
	private static Properties classP;
	private static Properties teamP;
	
	public void init() throws IOException {
		teamPropF = new File(plugin.getDataFolder() + plugin.fileSep + "<team-file>.values");
		classPropF = new File(plugin.getDataFolder() + plugin.fileSep + "<class-file>.values");
		//FIXME Rename files
		classP = new Properties();
		teamP = new Properties();
		
		if (!teamPropF.exists()) {
			teamPropF.createNewFile();
		} else {
			teamP.load(new FileInputStream(teamPropF));
		}
		if (!classPropF.exists()) {
			classPropF.createNewFile();
		} else {
			classP.load(new FileInputStream(classPropF));
		}
	}
	
	public static void updateClassFile(String name, String value) throws IOException {
		classP.setProperty(name, value);
		FileOutputStream outputC = new FileOutputStream(classPropF);
		classP.store(outputC, null);
	}
	
	public static void updateTeamFile(String name, String value) throws IOException {
		teamP.setProperty(name, value);
		FileOutputStream outputT = new FileOutputStream(teamPropF);
		teamP.store(outputT, null);
	}
}
