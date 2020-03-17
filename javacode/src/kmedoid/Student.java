package kmedoid;

public class Student {

	private String ID;
	private double[] grade;
	public int subnum;

	public Student(String ID,double[] grade) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.grade = grade;
		subnum = grade.length;
	}

	public double getGrade(int sub) {
		return grade[sub];
	}
	
	public String getID() {
		return ID;
	}
	@Override
	public String toString() {
		String stu = ID + ",";
		double total = 0;
		for(int i = 0; i < grade.length; i++) {
			total += grade[i];
			stu += grade[i];
			stu += ",";
		}
		stu += total;
		return stu;
	}
	
	

}
