package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

    private TreeMap<String, Course> corsi =  new TreeMap<>();
	private Map<String, Student> studenti = new TreeMap<>();
	private Map<String, List<String>> iscrizioni = new TreeMap<>();
    private List<Notifier> listeners = new LinkedList<>();


	/**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        if(name.length()>0 && availablePositions>=0 && !corsi.containsKey(name)) {
        	Course c = new Course(name, availablePositions);
        	corsi.put(name, c);
        }
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return corsi.navigableKeySet();
//        Returns a NavigableSet view of the keys contained 
//        in this map.The set's iterator returns the keys in ascending order. 
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
    	if(id.length()>0 && gradeAverage>=0) {
    		Student s = new Student(id, gradeAverage);
    		if(studenti.containsKey(id))
    			studenti.remove(id);
    		studenti.put(id, s);
    	}
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return studenti.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
    	return studenti.entrySet().stream()
    			.filter(a->a.getValue().gradeAverage>=inf && a.getValue().gradeAverage<=sup)
    			.map(a->a.getKey())
    			.collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses) throws ElectiveException {
        if(id.length()>0 && studenti.containsKey(id) && 
        		courses!=null && courses.size()>0 && courses.size()<4 &&
        		!iscrizioni.containsKey(id)) {
        	for(String s : courses) {
        		if(!corsi.containsKey(s))
                	throw new ElectiveException();
        	}
        	iscrizioni.put(id, courses);
        	for(String s : courses)
        		studenti.get(id).richieste.add(corsi.get(s));
            listeners.forEach(l->l.requestReceived(id));
        	return courses.size();
        }
        else {
        	throw new ElectiveException();
        }
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
    	return corsi.keySet().stream()
    				.collect(Collectors.toMap(
    	                    c-> (String) c,
    	                    c -> (List<Long>) studenti.values().stream()
    	                    	.map(s->s.choiceNo(c))
    	                    	.collect(Collectors.collectingAndThen(
    	                        		 Collectors.groupingBy(n->n, Collectors.counting()),
                        		 		 m -> {
    	                                     ArrayList<Long> res = new ArrayList<>();
    	                                     for(int i=1; i<=3; ++i) {
    	                                         res.add( m.getOrDefault(i, 0L) );
    	                                     }
    	                                     return res;
                        		 		 })
    	                         )
    	                    ));
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
    	 return studenti.values().stream()
    		        .sorted(Comparator.comparing(Student::getGradeAverage).reversed())
    		        .mapToInt(s -> {
    		            if(s.getPreferences()!=null) {
    		                s.getPreferences().stream()
    		                .filter(Course::hasRoom)
    		                .findFirst()
    		                .ifPresent(c->{
    		                    c.studenti.add(s);
    		                    s.enrolledIn=c;
    		                    listeners.forEach(l->l.assignedToCourse(s.getId(), c.getName()));
    		                });  }
    		            return s.isEnrolled()?0:1; })
    		        .sum();
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
    	return corsi.values().stream()
                .collect(Collectors.toMap(
                	Course::getName,
                    Course::getEnrolled
                ))
                ;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
    	listeners.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        return studenti.values().stream()
        .filter(s -> s.isEnrolled(choice))
        .count() / (double)studenti.size();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return studenti.values().stream()
               .filter(s->!s.isEnrolled())
               .map(Student::getId)
               .collect(Collectors.toList());
    }
    
    
}
