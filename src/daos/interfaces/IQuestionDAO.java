package daos.interfaces;

import dtos.QuestionDTO;

public interface IQuestionDAO {
	boolean updateQuestion(QuestionDTO question);
	boolean createQuestion(QuestionDTO question);
	boolean deleteQuestion(QuestionDTO question);
	boolean getQuestion(QuestionDTO question);

}
