package daos.interfaces;

import dtos.RoomDTO;

public interface IRoomDAO {


	

	RoomDTO updateRoom(RoomDTO room);
	boolean createRoom(RoomDTO room);
	boolean deleteRoom(RoomDTO room);
	RoomDTO getRoom(String roomKey);
	
}
