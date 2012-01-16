package org.codemash;

import java.util.ArrayList;

import org.simpleframework.xml.*;

@Root(name="Sessions")
public class Codemash {
	@ElementList(inline=true)
	public ArrayList<Session> sessions;
}

@Root(name="Session")
class Session {
	@Element(required=false)
	private String URI;
	@Element(required=false)
	private String Title;
	@Element(required=false)
	private String Abstract;
	@Element(required=false)
	private String SpeakerName;
	@Element(required=false)
	private String SpeakerURI;
	@Element(required=false)
	private String Start;
	@Element(required=false)
	private String Room;
	@Element(required=false)
	private String Technology;
	@Element(required=false)
	private String Difficulty;
	
	private Boolean Interested;
	
	public Boolean getInterested() {
		return Interested;
	}
	public void setInterested(Boolean interested) {
		Interested = interested;
	}
	public String getURI() {
		return URI;
	}
	public String getTitle() {
		return Title;
	}
	public String getAbstract() {
		return Abstract;
	}
	public String getSpeakerName() {
		return SpeakerName;
	}
	public String getSpeakerURI() {
		return SpeakerURI;
	}
	public String getStart() {
		return Start;
	}
	public String getRoom() {
		return Room;
	}
	public String getTechnology() {
		return Technology;
	}
	public String getDifficulty() {
		return Difficulty;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public void setSpeakerName(String speakerName) {
		SpeakerName = speakerName;
	}
	public void setSpeakerURI(String speakerURI) {
		SpeakerURI = speakerURI;
	}
	public void setStart(String start) {
		Start = start;
	}
	public void setRoom(String room) {
		Room = room;
	}
	public void setTechnology(String technology) {
		Technology = technology;
	}
	public void setDifficulty(String difficulty) {
		Difficulty = difficulty;
	}
	
}
