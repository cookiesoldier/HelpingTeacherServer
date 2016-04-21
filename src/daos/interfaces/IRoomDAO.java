
package daos.interfaces;

import dtos.RoomDTO;

public interface IRoomDAO {


	

	RoomDTO updateRoom(RoomDTO oldRoom,RoomDTO newRoom);
	boolean createRoom(RoomDTO room);
	boolean deleteRoom(RoomDTO room);
	RoomDTO getRoom(String roomKey);
	
}

