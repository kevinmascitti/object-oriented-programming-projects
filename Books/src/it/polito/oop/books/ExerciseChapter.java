package it.polito.oop.books;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {

    String title;
	int numPages;
	List<Question> domande = new LinkedList<>();


	public ExerciseChapter(String title, int numPages) {
		this.title=title;
		this.numPages=numPages;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
    	this.title=newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages=newPages;
    }
    

	public void addQuestion(Question question) {
		if(question!=null && !domande.contains(question))
			domande.add(question);
	}
	
	public List<Topic> getTopics() {
		return domande.stream()
				.map(a->a.getMainTopic())
				.sorted(Comparator.comparing(Topic::getKeyword))
        		.distinct()
        		.collect(Collectors.toList());
	}
	
}
