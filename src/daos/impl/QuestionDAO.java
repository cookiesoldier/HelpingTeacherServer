package daos.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IQuestionDAO;
import dtos.AnswerDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;

public class QuestionDAO implements IQuestionDAO {

	private List<QuestionDTO> questions;
	private static final String DIR = "data/questions";

	public QuestionDAO() {
		try {
			FileInputStream fin = new FileInputStream(new File(DIR + "/questions.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			questions = (ArrayList<QuestionDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("Question list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			questions = new ArrayList<>();
			System.out.println("No question list found. A new list has been created.");
			//e.printStackTrace();

			// creates the directory
			File dir = new File(DIR);
			dir.mkdirs();
		}
	}

	@Override
	public boolean updateQuestion(QuestionDTO oldQuestion,QuestionDTO newQuestion) {
		QuestionDTO question = getQuestion(oldQuestion.getQuestionKey());
		if(question != null){
			int questionNr = questions.indexOf(question);
			questions.remove(questionNr);
			questions.add(questionNr, newQuestion);
			
			if(getQuestion(newQuestion.getQuestionKey()) != null){
				updateQuestionFile();
				return true;
			}
			
		}
		return false;
		
	}

	@Override
	public boolean createQuestion(QuestionDTO question) {
	if(getQuestion(question.getQuestionKey()) != null) return false;
		questions.add(question);
		System.out.println("question added to list");
		updateQuestionFile();
		return true;
	}

	@Override
	public boolean deleteQuestion(QuestionDTO question) {
		if(questions.remove(question)){
			updateQuestionFile();
			return true;
		}
		return false;
	}

	private void updateQuestionFile() {
		try {
			FileOutputStream fout = new FileOutputStream(DIR + "/questions.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(questions);
			oout.close();
			fout.close();
			System.out.println("Question List was saved to disk.");
		} catch (IOException e) {
			System.out.println("Question List could not be saved to disk.");
			e.printStackTrace();

		}
		
	}

	@Override
	public QuestionDTO getQuestion(String questionKey) {
		for (QuestionDTO u : questions) {
			if (u.getQuestionKey().equals(questionKey)) {
				return u;
			}

		}
		return null;
	}
}
