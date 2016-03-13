package io.github.podshot.WorldWar.squads;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public class Squad {
	
	private String squadName;
	private UUID leader;
	private List<UUID> members = new ArrayList<UUID>();
	private String alligance;
	private BukkitTask objective_task = null;
	private Location objective_location = null;
	
	public Squad(String squadName, UUID leader, String alligance) {
		this.squadName = squadName;
		this.leader = leader;
		this.alligance = alligance;
	}
	
	public Squad(String squadName, UUID leader, List<String> members_str, String alligance) {
		this.squadName = squadName;
		this.leader = leader;
		this.alligance = alligance;
		for (String member : members_str) {
			this.members.add(UUID.fromString(member));
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
		if (objective_task != null) {
			objective_task.cancel();
			objective_task = null;
			this.addObjective(objective_location);
		}
	}
	
	public void removeSquadMember(UUID member) {
		members.remove(member);
	}
	
	public void addObjective(Location location) {
		if (objective_location == null)
			objective_location = location.add(0, 25, 0);
		List<UUID> toSeeObjective = new ArrayList<UUID>(this.members);
		toSeeObjective.add(this.leader);
		if (objective_task != null) {
			objective_task.cancel();
			objective_task = null;
		}
		objective_task = new SquadObjectiveMarker(objective_location, toSeeObjective).runTaskTimer(WorldWar.getInstance(), 10, 60);
	}
	
	public void removeObjective() {
		objective_task.cancel();
		objective_task = null;
		objective_location = null;
		SquadAPI.removeObjective(this);
	}
	
	public boolean hasObjective() {
		return objective_location != null;
	}
	
	public Location getObjectiveLocation() {
		return objective_location;
	}
}
