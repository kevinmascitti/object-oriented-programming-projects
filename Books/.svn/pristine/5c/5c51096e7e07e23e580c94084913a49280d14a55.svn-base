package it.polito.oop.books;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {
	
	class Answer{
		String answer;
		boolean correct;
		Answer(String answer, boolean correct){
			this.answer=answer;
			this.correct=correct;
		}
	}
	
	String question;
	Topic mainTopic;
	List<Answer> risposte = new LinkedList<>();

	public Question(String question, Topic mainTopic) {
		this.question=question;
		this.mainTopic=mainTopic;
	}

	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return mainTopic;
	}

	public void addAnswer(String answer, boolean correct) {
		if(answer.length()>0)
			risposte.add(new Answer(answer, correct));
	}
	
    @Override
    public String toString() {
        return question+" ("+mainTopic+")";
    }

	public long numAnswers() {
	    return risposte.size();
	}

	public Set<String> getCorrectAnswers() {
		return risposte.stream()
				.filter(a->a.correct==true)
				.map(a->a.answer)
				.collect(Collectors.toSet());
	}

	public Set<String> getIncorrectAnswers() {
		return risposte.stream()
				.filter(a->a.correct==false)
				.map(a->a.answer)
				.collect(Collectors.toSet());
	}
}
