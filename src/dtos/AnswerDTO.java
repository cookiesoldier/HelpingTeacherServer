package dtos;

public class AnswerDTO {
	
	String answerKey = "";


	String message = "";
	String poster = "";
	String timestamp = "";
	

	public AnswerDTO(String answerKey, String message, String poster, String timestamp) {
		super();
		this.answerKey = answerKey;
		this.message = message;
		this.poster = poster;
		this.timestamp = timestamp;
	}

	public String getAnswerKey() {

		return answerKey;
	}

	public Object getMessage() {
	
		return message;
	}

	public Object getPoster() {
		
		return poster;
	}

	public Object getTimestamp() {
	
		return timestamp;
	}

}
