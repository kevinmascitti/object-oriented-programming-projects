package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
    
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }

	private int codice;
	private User user;
	private String componentPath;
	private String description;
	private Severity severity;
	private State state;
	private String solution;
	private User maintainer;
	
	public Ticket(int codice, User u, String componentPath, String description, Severity severity) {
		this.codice=codice;
		this.user=u;
		this.componentPath=componentPath;
		this.description=description;
		this.severity=severity;
		state=State.Open;
	}

	public int getId(){
        return codice;
    }

    public String getDescription(){
        return description;
    }
    
    public Severity getSeverity() {
        return severity;
    }

    public String getAuthor(){
        return user.getUsername();
    }
    
    public String getComponent(){
        String[] componenti = componentPath.split("/");
        return componenti[componenti.length-1];
    }
    
    public State getState(){
        return state;
    }
    
    public String getSolutionDescription() throws TicketException {
        if(state!=State.Closed)
        	throw new TicketException();
        else
        	return solution;
    }

	public void setAssigned(User u) {
		state = State.Assigned;
		maintainer = u;
	}

	public void setClosed(String solution) {
		state=State.Closed;
		this.solution=solution;
		maintainer.addClosed();
	}
}
