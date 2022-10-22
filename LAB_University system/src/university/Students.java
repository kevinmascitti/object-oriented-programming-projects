package university;

public class Students {

	private String sName;
	private String sSurname;
	private int n;
	private Courses[] corsi;
	protected int[] voti;
	protected int totcorsi = 0;
	private int numeroesami = 0;
	
	public Students(String sName, String sSurname, int n) {
		this.sName = sName;
		this.sSurname = sSurname;
		this.n = n;
		corsi = new Courses[25];
		voti = new int[40];
	}
	
	public void putS(Courses corso) {
		corsi[totcorsi++] = corso;
	}
	
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsSurname() {
		return sSurname;
	}
	public void setsSurname(String sSurname) {
		this.sSurname = sSurname;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public Courses[] getCourses() {
		return corsi;
	}
	public String toString() {
		return n+" "+sName+" "+sSurname+"\n";
	}
	
	public void enroll(Courses c){
		if(totcorsi < corsi.length)
			corsi[totcorsi++] = c;
	}
	
	public String courses() {
		StringBuffer result = new StringBuffer();
		for(Courses c : corsi)
			if(c!=null)
				result.append(c.toString());
		return result.toString();
	}
	
	public void addExam(Courses c, int grade){
		for(int i=0; i<corsi.length; i++){
			if(corsi[i]!=null){	
				if(corsi[i] == c){
					voti[i] = grade;
					numeroesami++;
					break;
				}
			}
			else 
				break;
		}
	}
	
	public float avgGrade(){
		int gradeSum = 0;
		
		for(int i=0; i<totcorsi; i++)
			if(!Float.isNaN(voti[i]))
				gradeSum += voti[i];
		return (float)gradeSum/numeroesami;
		// se non ha fatto nessun esame, div 0/0 -> return Float.NaN 
	}
	
	public float awardsPoints(){
		return avgGrade()+((float)10*numeroesami/totcorsi);
	}
		
}