package dtos;

import java.util.ArrayList;
import java.util.List;

public class EventDTO {
	
	String title;
	String timeStamp;
	String eventKey;
	
	List<QuestionDTO> questions = new ArrayList<>();
	

	public EventDTO(String title, String timeStamp, String eventKey) {
		super();
		this.title = title;
		this.timeStamp = timeStamp;
		this.eventKey = eventKey;
	}

	public EventDTO(String title, String timeStamp, String eventKey, List<QuestionDTO> questions) {
		super();
		this.title = title;
		this.timeStamp = timeStamp;
		this.eventKey = eventKey;
		this.questions = questions;
	}

	public String getTitle() {
		return title;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public String getEventKey() {
		return eventKey;
	}
	
	

}
