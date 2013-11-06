package taAllocation;

import java.util.Collection;
import java.util.HashMap;

public class Course extends Entity {

	private boolean seniorCourse = false;
	private boolean gradCourse = false;
	private HashMap<String,Lecture> lectures = new HashMap<String,Lecture>();
	private HashMap<String,Lab> labs = new HashMap<String,Lab>();
			
	public Course(Entity e) {
		super(e);
	}
	
	public Course(String s) {
		super(s);
	}

	public boolean isSeniorCourse() {
		return seniorCourse;
	}

	public void setSeniorCourse(boolean seniorCourse) {
		//TODO: check if grad course- not necessary because if this getting called then we know it already is a grad course. 
		this.seniorCourse = seniorCourse;
	}

	public boolean isGradCourse() {
		return gradCourse;
	}

	public void setGradCourse(boolean gradCourse) {
		//TODO: check if senior course-  same thing as grad course here here. 
		this.gradCourse = gradCourse;
	}
	
	//////////////////////////lectures

	public boolean hasLecture(String lec)
	{
		return lectures.containsKey(lec);
	}
	
	public void addLecture(String lec)
	{
		Lecture lecture = new Lecture(lec);
		lecture.setCourse(this);
		
		lectures.put(lec, lecture);
	}
	
	public Collection<Lecture> getLectures()
	{
		return lectures.values();
	}

	public Lecture getLecture(String lec) {
		return lectures.get(lec);
	}
	
	/////////////////////////labs

	public boolean hasLab(String lab)
	{
		return labs.containsKey(lab);
	}
	
	public void addLab(String lab, String lec)
	{
		Lab laboratory = new Lab(lab);
		laboratory.setCourse(this);
		laboratory.setLecture(this.getLecture(lec));
		labs.put(lab, laboratory);
	}
	
	public Collection<Lab> getLabs()
	{
		return labs.values();
	}
	
	public Lab getLab(String lab)
	{
		return labs.get(lab);
	}
	
}
