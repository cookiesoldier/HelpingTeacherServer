package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class EventDTO implements Serializable {
	
	String title;
	String timeStamp;
	String eventKey;
	List<String> questionkeys = new ArrayList<>();
	
/**
 * Initial creation constructor
 * @param title
 * @param timeStamp
 * @param eventKey
 */
	public EventDTO(String title, String timeStamp, String eventKey) {
		super();
		this.title = title;
		this.timeStamp = timeStamp;
		this.eventKey = eventKey;
	}
/**
 * 
 * @param title
 * @param timeStamp
 * @param eventKey
 * @param questions
 */
	public EventDTO(String title, String timeStamp, String eventKey, List<String> questions) {
		super();
		this.title = title;
		this.timeStamp = timeStamp;
		this.eventKey = eventKey;
		this.questionkeys = questions;
	}

	public String getTitle() {
		return title;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public List<String> getQuestions() {
		return questionkeys;
	}

	public String getEventKey() {
		return eventKey;
	}

	public JSONObject toJSONObject() {
		JSONObject data = new JSONObject();
		data.put("TITLE", title);
		data.put("TIMESTAMP", timeStamp);
		data.put("EVENTKEY", eventKey);
		data.put("QUESTIONKEYS", questionkeys);

		return data;
	}
	
	

}
