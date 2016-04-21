package daos.interfaces;

import dtos.QuestionDTO;

public interface IQuestionDAO {
	
	
	boolean createQuestion(QuestionDTO question);
	boolean deleteQuestion(QuestionDTO question);
	QuestionDTO getQuestion(String questionKey);
	boolean updateQuestion(QuestionDTO oldquestion, QuestionDTO newQuestion);

}
