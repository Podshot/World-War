package io.github.podshot.WorldWar.squads;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	}
	
	public void removeSquadMember(UUID member) {
		members.remove(member);
	}
}
