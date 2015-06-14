package io.github.podshot.WorldWar.squads;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

public class Squad {
	
	private String squadName;
	private UUID leader;
	private List<UUID> members = new ArrayList<UUID>();
	private String alligance;
	
	public Squad(String squadName, UUID leader, String alligance) {
		this.squadName = squadName;
		this.leader = leader;
		this.alligance = alligance;
	}
	
	public Squad(String squadName, FileConfiguration config) {
		this.squadName = squadName;
		leader = UUID.fromString(config.getString("Squads." + squadName + ".Leader"));
		alligance = config.getString("Squads." + squadName + ".Alligance");
		for (String uuid : config.getStringList("Squads." + squadName + ".Members")) {
			members.add(UUID.fromString(uuid));
		}
	}
	
	public String getSquadName() {
		return squadName;
	}
	
	public UUID getSquadLeader() {
		return leader;
	}
	
	public List<UUID> getSquadMembers() {
		return members;
	}
	
	public String getAlligance() {
		return alligance;
	}
	
	public void addSquadMember(UUID member) {
		members.add(member);
	}
	
	public void removeSquadMember(UUID member) {
		members.remove(member);
	}
}
