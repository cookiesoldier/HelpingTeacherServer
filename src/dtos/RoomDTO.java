package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
	
	
	

}
