package io.github.podshot;

import io.github.podshot.api.PlayerAPI;
import io.github.podshot.internals.Internals;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class BattleStatistics implements Listener {

	private static WorldWar plugin = WorldWar.getInstance();
	
	private static int deaths_red;
	private static int deaths_blue;
	
	private static int kills_blue_p;
	private static int kills_red_p;
	
	private static int kills_blue_m;
	private static int kills_red_m;

	public BattleStatistics() {
		deaths_blue = plugin.getConfig().getInt("Teams.Blue.Deaths");
		deaths_red = plugin.getConfig().getInt("Teams.Red.Deaths");
		kills_blue_p = plugin.getConfig().getInt("Teams.Blue.Kills.Players");
		kills_red_p = plugin.getConfig().getInt("Teams.Red.Kills.Players");
		kills_blue_m = plugin.getConfig().getInt("Teams.Blue.Kills.Monsters");
		kills_red_m = plugin.getConfig().getInt("Teams.Red.Kills.Monsters");
	}

	@EventHandler
	public void onPlayerDeath(EntityDeathEvent evt) {
		if (!(Internals.isWarDeclared())) {
			return;
		}

		if (evt.getEntity().getType() == EntityType.PLAYER) {
			Player killed = (Player) evt.getEntity();
			String KilledTeam = PlayerAPI.getTeam(killed);
			if (KilledTeam.equals("Blue")) {
				deaths_blue = deaths_blue + 1;
			} else {
				deaths_red = deaths_red + 1;
			}
		} else if (evt.getEntity().getType() == EntityType.CAVE_SPIDER || evt.getEntity().getType() == EntityType.BLAZE 
				|| evt.getEntity().getType() == EntityType.CREEPER || evt.getEntity().getType() == EntityType.ENDERMAN
				|| evt.getEntity().getType() == EntityType.GHAST || evt.getEntity().getType() == EntityType.MAGMA_CUBE
				|| evt.getEntity().getType() == EntityType.PIG_ZOMBIE || evt.getEntity().getType() == EntityType.SILVERFISH
				|| evt.getEntity().getType() == EntityType.SKELETON || evt.getEntity().getType() == EntityType.SLIME
				|| evt.getEntity().getType() == EntityType.SPIDER || evt.getEntity().getType() == EntityType.WITCH
				|| evt.getEntity().getType() == EntityType.WITHER) {
			if (evt.getEntity().getKiller().getType() == EntityType.PLAYER) {
				Player killer = evt.getEntity().getKiller();
				String KillerTeam = PlayerAPI.getTeam(killer);
				if (KillerTeam.equals("Blue")) {
					kills_blue_p = kills_blue_m + 1;
				} else {
					kills_red_p = kills_red_m + 1;
				}
			}
			
		}
		if (evt.getEntity().getKiller().getType() == EntityType.PLAYER) {
			Player killer = evt.getEntity().getKiller();
			String KillerTeam = PlayerAPI.getTeam(killer);
			if (KillerTeam.equals("Blue")) {
				kills_blue_p = kills_blue_p + 1;
			} else {
				kills_red_p = kills_red_p + 1;
			}
		}
	}
	
	public static void saveStatistics() {
		plugin.getConfig().set("Teams.Blue.Deaths", deaths_blue);
		plugin.getConfig().set("Teams.Red.Deaths", deaths_red);
		plugin.getConfig().set("Teams.Blue.Kills.Players", kills_blue_p);
		plugin.getConfig().set("Teams.Red.Kills.Players", kills_red_p);
		plugin.getConfig().set("Teams.Blue.Kills.Monsters", kills_blue_m);
		plugin.getConfig().set("Teams.Red.Kills.Monsters", kills_red_m);
		plugin.saveConfig();
	}
	
	public static int getBlueDeaths() {
		return deaths_blue;
	}
	
	public static int getRedDeaths() {
		return deaths_red;
	}
	
	public static int getBluePlayerKills() {
		return kills_blue_p;
	}
	
	public static int getRedPlayerKills() {
		return kills_red_p;
	}
	
	public static int getBlueMonsterKills() {
		return kills_blue_m;
	}
	
	public static int getRedMonsterKills() {
		return kills_red_m;
	}
}
