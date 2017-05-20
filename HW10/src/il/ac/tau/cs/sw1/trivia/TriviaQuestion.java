package il.ac.tau.cs.sw1.trivia;

import java.util.List;

public class TriviaQuestion {


	private String question;
	private List<String> answers;
	private String correct_answer;

	
	public TriviaQuestion(String question, List<String> answers,
			String correct_answer) {
		this.question = question;
		this.answers = answers.subList(0, answers.size());
		this.correct_answer = correct_answer;
	}


	


	public String getQuestion() {
		return question;
	}


	public List<String> getAnswers() {
		return answers;
	}


	public String getCorrect_answer() {
		return correct_answer;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result
				+ ((correct_answer == null) ? 0 : correct_answer.hashCode());
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
		return result;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TriviaQuestion other = (TriviaQuestion) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (correct_answer == null) {
			if (other.correct_answer != null)
				return false;
		} else if (!correct_answer.equals(other.correct_answer))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	
}