package daos.interfaces;

import dtos.EventDTO;

public interface IEventDAO {
	
	boolean createEvent(EventDTO event);
	boolean deleteEvent(EventDTO event);
	EventDTO getEvent(String eventkey);
	EventDTO updateEvent(EventDTO oldEvent, EventDTO newEvent);

}
