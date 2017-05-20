package il.ac.tau.cs.sw1.trivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
	protected List<TriviaQuestion> questionsList;
	private int score;
	private int wrong_answers;
	private int questions_cnt; // number of loaded questions
	private int curr_question_cnt; // counter of questions which already
									// answered
	private int correct_answer;

	public Game() {
		this.questionsList = new ArrayList<TriviaQuestion>();
		this.score = 0;
		this.wrong_answers = 0;
		this.questions_cnt = 0;
		this.curr_question_cnt = 0;
		this.correct_answer = 0;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWrong_answers() {
		return wrong_answers;
	}

	public void setWrong_answers(int wrong_answers) {
		this.wrong_answers = wrong_answers;
	}

	public int getQuestions_cnt() {
		return questions_cnt;
	}

	public void setQuestions_cnt(int questions_cnt) {
		this.questions_cnt = questions_cnt;
	}

	public List<TriviaQuestion> getQuestionsList() {
		return questionsList;
	}

	public int getCurr_question_cnt() {
		return curr_question_cnt;
	}

	public void setCurr_question_cnt(int curr_question_cnt) {
		this.curr_question_cnt = curr_question_cnt;
	}

	public int getCorrect_answer() {
		return correct_answer;
	}

	public void setCorrect_answer(int correct_answer) {
		this.correct_answer = correct_answer;
	}

}
