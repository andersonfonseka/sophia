package org.sophia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Event {
	
	private String id;
	
	private String title;
	
	private String content;
	
	private String variable;
	
	private String type;
	
	private Map<String, Event> events = new HashMap<>();
	
	public Event(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addEvent(Event event) {
		this.events.put(event.getId(), event);
	}
	
	public Event getEvent(String id) {
		return events.get(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int totalChildEvents() {
		return this.events.size();
	}
	
	public Event[] getEvents() {
		return this.events.values().toArray(new Event[this.events.values().size()]);
	}
	
	public Event next() {
		Event evt = new Event("Fallback");
			  evt.setType("Fallback");	
				
		if (!this.events.isEmpty()) {
			evt = new ArrayList<>(this.events.values()).get(0);
		}
		
		return evt;
	}
	
	public Event next(String id) {
		return this.events.get(id);
	}


	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", content=" + content + ", events=" + events + "]";
	}
	
	
	
}
