package daos.interfaces;

import dtos.AnswerDTO;

public interface IAnswerDAO {

	boolean updateAnswers(AnswerDTO answer);
	boolean createAnswer(AnswerDTO answer);
	boolean deleteAnswers(AnswerDTO answer);
	boolean getAnswers(AnswerDTO answer);
}
