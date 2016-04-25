package dtos;

import org.json.simple.JSONObject;

public class AnswerDTO {
	
	String answerKey;
	String body;
	String timeStamp;
	UserDTO sender;
	
	public AnswerDTO(String answerKey, String body, String timeStamp, UserDTO sender) {
		super();
		this.answerKey = answerKey;
		this.body = body;
		this.timeStamp = timeStamp;
		this.sender = sender;
	}

	public String getAnswerKey() {
		return answerKey;
	}

	public String getBody() {
		return body;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public UserDTO getSender() {
		return sender;
	}
	
	public JSONObject toJSONObject(){
		JSONObject data = new JSONObject();
		data.put("ANSWERKEY", answerKey);
		data.put("BODY", body);
		data.put("TIMESTAMP", timeStamp);
		data.put("SENDER", sender);
		return data;	
	}

	
	
	
	
	
	


}
