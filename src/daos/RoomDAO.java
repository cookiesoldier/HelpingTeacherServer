package daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IRoomDAO;
import dtos.RoomDTO;
import dtos.UserDTO;

public class RoomDAO implements IRoomDAO {

	private List<RoomDTO> rooms;
	private static final String DIR = "data/rooms";

	public RoomDAO() {
		try {
			FileInputStream fin = new FileInputStream(new File(DIR + "/rooms.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			rooms = (ArrayList<RoomDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("room list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			rooms = new ArrayList<>();
			System.out.println("No roomlist found. A new list has been created.");
			e.printStackTrace();

			// creates the directory
			File dir = new File(DIR);
			dir.mkdirs();
		}
	}

	@Override
	public RoomDTO getRoom(String roomKey) {
		for (RoomDTO u : rooms) {
			if (u.getRoomKey().equals(roomKey))
				return u;
		}
		return null;
	}

	@Override
	public RoomDTO updateRoom(RoomDTO oldRoom, RoomDTO newRoom) {
		int roomNr = rooms.indexOf(oldRoom);
		rooms.add(roomNr, newRoom);
		return rooms.get(roomNr);
	}

	@Override
	public boolean createRoom(RoomDTO room) {
		if (getRoom(room.getRoomKey()) != null)return false;
		rooms.add(room);
		System.out.println("room added to list");
		updateRoomFile();
		return true;
	}

	public void updateRoomFile() {
		try {
			FileOutputStream fout = new FileOutputStream(DIR + "/rooms.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(rooms);
			oout.close();
			fout.close();
			System.out.println("RoomList was saved to disk.");
		} catch (IOException e) {
			System.out.println("RoomList could not be saved to disk.");
			e.printStackTrace();

		}
	}

	@Override
	public boolean deleteRoom(RoomDTO room) {
		if (rooms.remove(room)) {
			updateRoomFile();
			return true;
		}
		return false;

	}

}
