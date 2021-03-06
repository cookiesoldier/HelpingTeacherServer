package daos.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IAnswerDAO;
import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.RoomDTO;
import dtos.UserDTO;
import helper.LogMethods;

public class AnswerDAO implements IAnswerDAO {

	private List<AnswerDTO> answers;
	private static final String DIR = "data/answers";

	public AnswerDAO() {
		try {
			FileInputStream fin = new FileInputStream(new File(DIR + "/answers.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			answers = (ArrayList<AnswerDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("Answer list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			answers = new ArrayList<>();

			// System.out.println("No Answer list found. A new list has been
			// created.");
			// e.printStackTrace();

			// creates the directory
			File dir = new File(DIR);
			dir.mkdirs();
		}
	}

	@Override
	public boolean updateAnswer(AnswerDTO oldAnswer, AnswerDTO newAnswer) {
		AnswerDTO answer = getAnswer(oldAnswer.getAnswerKey());
		if (answer != null) {
			int answerNr = answers.indexOf(answer);
			answers.remove(answerNr);
			answers.add(answerNr, newAnswer);
			if (getAnswer(newAnswer.getAnswerKey()) != null) {
				updateAnswersFile();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean createAnswer(AnswerDTO answer) {
		if (getAnswer(answer.getAnswerKey()) != null)
			return false;
		answers.add(answer);

		// System.out.println("Answer added to list");
		updateAnswersFile();
		return true;
	}

	private void updateAnswersFile() {
		try {
			FileOutputStream fout = new FileOutputStream(DIR + "/answers.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(answers);
			oout.close();
			fout.close();

			// System.out.println("AnswersList was saved to disk.");
		} catch (IOException e) {

			System.out.println("Answerslist could not be saved to disk.");
			// e.printStackTrace();

		}

	}

	@Override
	public boolean deleteAnswers(AnswerDTO answer) {
		for (AnswerDTO u : answers) {
			if (u.getAnswerKey().equals(answer.getAnswerKey())){
				answers.remove(u);
				updateAnswersFile();
				return true;
				
			}
		}
	return false;
	}

	@Override
	public AnswerDTO getAnswer(String answerKey) {
		for (AnswerDTO u : answers) {
			if (u.getAnswerKey().equals(answerKey))

				return u;
		}
		return null;

	}

}
