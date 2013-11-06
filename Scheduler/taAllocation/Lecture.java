package taAllocation;

import java.util.HashMap;

public class Lecture extends Entity {

	private Course course;
	private Timeslot timeslot;
	private Instructor instructor;
	
	private HashMap<String,Lecture> lectures = new HashMap<String,Lecture>(); 
		
	public Lecture(Entity e) {
		super(e);
	}

	public Lecture(String s)	{
		super(s);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Timeslot getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public boolean hasTimeslot(){
		if (timeslot == null)
			return false;
		else
			return true;
	}
}
