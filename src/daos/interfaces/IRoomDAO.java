package daos.interfaces;

import dtos.RoomDTO;

public interface IRoomDAO {


	
	boolean getRoom(RoomDTO room);
	boolean updateRoom(RoomDTO room);
	boolean createRoom(RoomDTO room);
	boolean deleteRoom(RoomDTO room);
	
}
