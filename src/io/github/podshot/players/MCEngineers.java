package io.github.podshot.players;

import java.util.ArrayList;
import java.util.List;

public class MCEngineers {
	
	private List<String> mcEngineers = new ArrayList<String>();
	private boolean membersAdded;
	
	public void init() {
		mcEngineers.add("foamytrampoline");
		mcEngineers.add("sapobeast");
		mcEngineers.add("Podshot");
		mcEngineers.add("Destruc7i0n");
		membersAdded = true;
	}
	
	public List<String> getMCEngineersList() {
		if (!membersAdded) {
			this.init();
		}
		return mcEngineers;
		
	}

}
