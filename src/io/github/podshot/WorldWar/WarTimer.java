package io.github.podshot.WorldWar;

import io.github.podshot.WorldWar.handlers.WarHandler;

import java.util.ArrayList;
import java.util.Collection;

import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

public class WarTimer implements Listener {
	
	private int totalTime;
	private int currentTime;
	private BossBar bar;
	private WorldWar plugin;
	
	public WarTimer(WorldWar plugin) {
		this.plugin = plugin;
		FileConfiguration config = plugin.getConfig();
		totalTime = config.getInt("Timer.TotalTime", 120);
		currentTime = config.getInt("Timer.LastTime", 0);
		Collection<Player> players = convertToGenericCollection(plugin.getServer().getOnlinePlayers());
		bar = BossBarAPI.addBar(players, 
				new TextComponent("Time Left"), 
				BossBarAPI.Color.RED,
				BossBarAPI.Style.PROGRESS,
				((float) currentTime / (float) totalTime),
				20,
				2
				);
	}
	
	private Collection<Player> convertToGenericCollection(Collection<? extends Player> players) {
		return new ArrayList<Player>(players);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		final Player player = evt.getPlayer();
		Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {

			@Override
			public void run() {
				bar.addPlayer(player);
				
			}
			
		}, 80L);
	}
}
