package io.github.podshot.WorldWar.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarDeclaredEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private float hours;
	private int minutes;
	private int ticks;
	
	public WarDeclaredEvent(String[] time) {
		hours = (float) (Float.parseFloat(time[0]) + (Float.parseFloat(time[1]) * (1f/3600f)));
		minutes = (Integer.parseInt(time[0]) * 3600) + Integer.parseInt(time[1]);
	}
	
	public float getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
