package daos.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IEventDAO;
import dtos.EventDTO;
import dtos.RoomDTO;

public class EventDAO implements IEventDAO {
	
	private List<EventDTO> events;
	private static final String DIR = "data/events";
	
	public EventDAO(){
		try {
			FileInputStream fin = new FileInputStream(new File(DIR + "/events.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			events = (ArrayList<EventDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("event list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			events = new ArrayList<>();
			System.out.println("No event list found. A new list has been created.");
			//e.printStackTrace();
			// creates the directory
			File dir = new File(DIR);
			dir.mkdirs();
		}
	}
	

	@Override
	public boolean updateEvent(EventDTO oldEvent, EventDTO newEvent) {
		EventDTO event = getEvent(oldEvent.getEventKey());
		if(event != null){
			int eventNr = events.indexOf(event);
			events.remove(eventNr);
			events.add(eventNr, newEvent);
			if(getEvent(newEvent.getEventKey())!= null){
				updateEventFile();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean createEvent(EventDTO event) {
		if(getEvent(event.getEventKey())!= null) return false;
		events.add(event);
		updateEventFile();
		return true;
	}




	@Override
	public boolean deleteEvent(EventDTO event) {
		for (EventDTO u : events) {
			if (u.getEventKey().equals(event.getEventKey())){
				events.remove(u);
				updateEventFile();
				return true;
				
			}
		}
	return false;
		
	}

	@Override
	public EventDTO getEvent(String eventkey) {
		for(EventDTO u : events){
			if(u.getEventKey().equals(eventkey)){
				return u;
			}
		}
		return null;
	}
	
	private void updateEventFile() {
		try {
			FileOutputStream fout = new FileOutputStream(DIR + "/events.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(events);
			oout.close();
			fout.close();
			System.out.println("EventList was saved to disk.");
		} catch (IOException e) {
			System.out.println("EventList could not be saved to disk.");
			e.printStackTrace();

		}
		
	}
}
