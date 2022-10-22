package ticketing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ticketing.IssueManager.UserClass;
import ticketing.Ticket.State;

public class User {

	private String username;
	private UserClass userClass1;
	private UserClass userClass2;
	
	private Map<Integer,Ticket> tickets = new TreeMap<>();
	private int closed;

	public User(String username, UserClass userClass) {
		this.username=username;
		this.userClass1=userClass;
		this.userClass2=null;
		closed=0;
	}
	
	public User(String username, UserClass userClass, UserClass userClass2) {
		this.username=username;
		this.userClass1=userClass;
		this.userClass2=userClass2;
		closed=0;
	}

	public String getUsername() {
		return username;
	}

	public Set<UserClass> getClassi() {
		Set<UserClass> classi = new HashSet<>();
		if(userClass1!=null)
			classi.add(userClass1);
		if(userClass2!=null)
			classi.add(userClass2);
		return classi;
	}

	public void setTicket(Ticket ticket) {
		tickets.put(ticket.getId(), ticket);
	}
	
	public String toStringR() {
		return username+":"+closed;
	}

	public void addClosed() {
		closed++;
	}
	
	public int getClosed() {
		return closed;
	}
}
