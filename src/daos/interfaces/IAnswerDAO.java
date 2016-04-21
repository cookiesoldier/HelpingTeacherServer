package daos.interfaces;

import dtos.AnswerDTO;

public interface IAnswerDAO {


	boolean createAnswer(AnswerDTO answer);
	boolean deleteAnswers(AnswerDTO answer);
	AnswerDTO updateAnswer(AnswerDTO oldAnswer, AnswerDTO newAnswer);
	AnswerDTO getAnswer(String answerKey);
}
