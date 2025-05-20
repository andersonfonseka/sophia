package org.sophia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Flow {
	
	private String id = UUID.randomUUID().toString();
	
	private Event firstEvent;

	private Map<String, Event> events = new HashMap<>();
	
	private Map<String, String> variables = new HashMap();
	
	public String getId() {
		return id;
	}
	
	public void addEvent(Event event) {
		if (firstEvent == null) {
			this.firstEvent = event;
			this.events.put(event.getId(), event);
		} else {
			this.events.put(event.getId(), event);
		}
	}
	
	public Event getEvent(String id) {
		return events.get(id);
	}
	
	public void addVariable(String key, String value) {
		if (key.trim().length() > 0 && value.trim().length() > 0) {
			this.variables.put(key, value);
		}
	}
	
	public String getValue(String key) {
		return this.variables.get(key);
	}

	public String[] getVariables() {
		return this.variables.keySet().toArray(new String[this.variables.values().size()]);
	}
	
	@Override
	public String toString() {
		return "Flow [id=" + id + ", events=" + events + "]";
	}
	
	public void play() {
		EventStrategy eventStrategy = new EventStrategy(this);
		eventStrategy.execute(this.firstEvent);
	}
	
}
