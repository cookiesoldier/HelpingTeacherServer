package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class QuestionDTO implements Serializable {

	String title;
	String body;
	String timeStamp;
	String questionKey;
	String sender;

	List<String> answerKeys = new ArrayList<>();

	/**
	 * 
	 * @param title
	 * @param body
	 * @param timeStamp
	 * @param questionKey
	 * @param sender
	 * @param answerKeys
	 */
	public QuestionDTO(String title, String body, String timeStamp, String questionKey, String sender,
			List<String> answerKeys) {
		super();
		this.title = title;
		this.body = body;
		this.timeStamp = timeStamp;
		this.questionKey = questionKey;
		this.answerKeys = answerKeys;
		this.sender = sender;
	}

	/**
	 * 
	 * @param title
	 * @param body
	 * @param timeStamp
	 * @param questionKey
	 * @param sender
	 */
	public QuestionDTO(String title, String body, String timeStamp, String questionKey, String sender) {
		super();
		this.title = title;
		this.body = body;
		this.timeStamp = timeStamp;
		this.questionKey = questionKey;
		this.sender = sender;
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

	public String getSender() {
		return sender;

	}

	public Object toJSONObject() {
		JSONObject data = new JSONObject();
		data.put("SENDER", sender);
		data.put("TITLE", title);
		data.put("BODY", body);
		data.put("TIMESTAMP", timeStamp);
		data.put("ANSWERKEYS", answerKeys);

		return data;
	}

}
