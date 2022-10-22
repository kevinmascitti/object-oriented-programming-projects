package it.polito.oop.books;

import java.util.List;


public class Assignment {

    String id;
	ExerciseChapter chapter;
	double score = 0;

	public Assignment(String id, ExerciseChapter chapter) {
		this.id=id;
		this.chapter=chapter;
	}

	public String getId() {
        return id;
    }

    public ExerciseChapter getChapter() {
        return chapter;
    }

    public double addResponse(Question q, List<String> answers) {
        if(q!=null && answers.size()>0) {
        	long N = q.numAnswers(), FP=0, FN = q.getCorrectAnswers().size();
        	for(String s : answers) {
        		if(q.getIncorrectAnswers().contains(s))
        			FP++;
        		if(q.getCorrectAnswers().contains(s))
        			FN--;
        	}
        	score+=(double)(N-FP-FN)/N;
        	return (double)(N-FP-FN)/N;
        }
        return -1;
    }
    
    public double totalScore() {
        return score;
    }

}
