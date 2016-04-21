package daos.interfaces;

import dtos.EventDTO;

public interface IEventDAO {
	
	boolean updateEvent(EventDTO event);
	boolean createEvent(EventDTO event);
	boolean deleteEvent(EventDTO event);
	boolean getEvent(EventDTO event);

}
