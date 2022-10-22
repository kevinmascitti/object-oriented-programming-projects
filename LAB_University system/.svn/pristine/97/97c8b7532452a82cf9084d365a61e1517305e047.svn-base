package university;

public class Courses {
	
	private String name;
	private String teacher;
	private int n;
	private Students[] studs;
	protected int[] voti;
	private int i = 0;
	
	private int sommavoti = 0;
	private int numeroesami = 0;
	
	public Courses(String nome, String teacher, int n){
		this.name = nome;
		this.teacher = teacher;
		this.n = n;
		studs = new Students[100];
		voti = new int[1000];
	}
	
	public void putC(Students stud) {
		studs[i++] = stud;
	}
	
	public String getNome() {
		return name;
	}
	public void setNome(String nome) {
		this.name = nome;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public Students[] getStuds() {
		return studs;
	}
	public String toString() {
		return n+", "+name+", "+teacher+"\n";
	}
	
	public void enroll(Students s) {
		for(int i=0; i<studs.length; i++){
			if(studs[i] == null){
				studs[i] = s;
				break;
			}
		}
	}
	
	public String attendees() {
		StringBuffer result = new StringBuffer();
		for(Students s: studs)
			if(s!=null)
				result.append(s.toString());
		return result.toString();
	}
	
	public void addExam (int grade){
		sommavoti+=grade;
		numeroesami++;
	}
	
	public float avgGrade(){
		return (float)sommavoti/numeroesami;
	}

}
