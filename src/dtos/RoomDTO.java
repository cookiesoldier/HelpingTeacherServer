package dtos;

public class RoomDTO {
	
	String roomKey = "";
	String owner = "";
	String eventKeys = "";
	String type = "";


	public RoomDTO(String roomKey, String owner, String eventKeys, String type) {
		super();
		this.roomKey = roomKey;
		this.owner = owner;
		this.eventKeys = eventKeys;
		this.type = type;
	}


	public Object getEventKeys() {
		
		return eventKeys;
	}

	public Object getOwner() {
	
		return owner;
	}

	public Object getType() {
		
		return type;
	}

	public String getRoomKey() {
		
		return roomKey;
	}

}
