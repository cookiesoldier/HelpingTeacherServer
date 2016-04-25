package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;



public class RoomDTO implements Serializable {
	
	String roomKey = "";
	UserDTO owner;
	//String eventKeys = "";
	String type = "";
	
	List<String> eventKeys = new ArrayList<>();

	public RoomDTO(String roomKey, UserDTO owner, String type) {
		super();
		this.roomKey = roomKey;
		this.owner = owner;
		this.type = type;
	}

	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getEventKeys() {
		return eventKeys;
	}
	
	public JSONObject toJSONObject() {
	
		JSONObject data = new JSONObject();
		data.put("ROOMKEY", roomKey);
		data.put("OWNER", owner);
		data.put("TYPE", type);
		data.put("EVENTKEYS", eventKeys);
		
		return data;
		
	}
	
	
	

}
