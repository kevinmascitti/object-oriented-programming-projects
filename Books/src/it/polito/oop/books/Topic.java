package it.polito.oop.books;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Topic {

	String keyword;
	Map<String, Topic> sottoargomenti = new TreeMap<>();
	
	public Topic(String keyword) {
		this.keyword=keyword;
	}

	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
        if(!sottoargomenti.containsKey(topic.keyword)) {
        	sottoargomenti.put(topic.keyword, topic);
        	return true;
        }
        return false;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return sottoargomenti.values().stream()
        		.sorted(Comparator.comparing(Topic::getKeyword))
        		.collect(Collectors.toList());
	}
}
