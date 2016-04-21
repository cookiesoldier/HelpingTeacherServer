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
	         FileInputStream fin = new FileInputStream(new File(DIR+"/rooms.ser"));
	         ObjectInputStream oin = new ObjectInputStream(fin);
	         rooms = (ArrayList<RoomDTO>) oin.readObject();
	         oin.close();
	         fin.close();
	         System.out.println("room list loaded from file.");
	      } catch (IOException | ClassNotFoundException e) {
	         rooms = new ArrayList<>();
	         System.out.println("No list found. A new list has been created.");
	         e.printStackTrace();
	         
	         //creates the directory
	         File dir = new File(DIR);
	         dir.mkdirs();
	      }
	}

	@Override
	public RoomDTO getRoom(String roomKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomDTO updateRoom(RoomDTO room) {
		// TODO Auto-generated method stub
		return new RoomDTO("", "", "", "");
	}

	@Override
	public boolean createRoom(RoomDTO room) {
	     if(getRoom(room.getRoomKey()) != null) return false;
	      rooms.add(room);
	      System.out.println("room added to list");
	      try {
	         FileOutputStream fout = new FileOutputStream(DIR+"/rooms.ser");
	         ObjectOutputStream oout = new ObjectOutputStream(fout);
	         oout.writeObject(rooms);
	         oout.close();
	         fout.close();
	         System.out.println("RoomList was saved to disk.");
	      } catch (IOException e) {
	         System.out.println("RoomList could not be saved to disk.");
	         e.printStackTrace();

	      }
	      return true;
	   }
	

	@Override
	public boolean deleteRoom(RoomDTO room) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
