package it.polito.oop.books;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class TheoryChapter {

    String title;
	int numPages;
	String text;
	Map<String, Topic> argomenti = new TreeMap<>();

	public TheoryChapter(String title, int numPages, String text) {
		this.title=title;
		this.numPages=numPages;
		this.text=text;
	}

	public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text=newText;
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
    
    public void addTopic(Topic topic) {
    	if(topic!=null) {
	    	argomenti.put(topic.keyword, topic);
	    	if(topic.sottoargomenti.size()>0)
	    		for(Topic t : topic.sottoargomenti.values())
	    			if(t!=null)
	    				addTopic(t);
    	}
    }
    
	public List<Topic> getTopics() {
        return argomenti.values().stream()
        		.distinct()
        		.sorted(Comparator.comparing(Topic::getKeyword))
        		.collect(Collectors.toList());
	}
    
}
