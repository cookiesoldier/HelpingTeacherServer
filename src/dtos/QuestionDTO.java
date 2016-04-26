package dtos;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class QuestionDTO {

	String title;
	String body;
	String timeStamp;
	String questionKey;
	
	
	List<String> answerKeys = new ArrayList<>();

	

	public QuestionDTO(String title, String body, String timeStamp, String questionKey, List<String> answerKeys) {
		super();
		this.title = title;
		this.body = body;
		this.timeStamp = timeStamp;
		this.questionKey = questionKey;
		this.answerKeys = answerKeys;
	}
	/**
	 * Initial constructor for creation
	 * @param title
	 * @param body
	 * @param timeStamp
	 * @param questionKey
	 */

	public QuestionDTO(String title, String body, String timeStamp, String questionKey) {
		super();
		this.title = title;
		this.body = body;
		this.timeStamp = timeStamp;
		this.questionKey = questionKey;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public List<String> getAnswerKeys() {
		return answerKeys;
	}
	public Object toJSONObject() {
		JSONObject data = new JSONObject();
		data.put("TITLE", title);
		data.put("BODY", body);
		data.put("TIMESTAMP", timeStamp);
		data.put("ANSWERKEYS", answerKeys);
		
		return data;
	}


	
	

}

