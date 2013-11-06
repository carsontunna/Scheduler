package taAllocation;

public class Lab extends Entity {

	private Course course;
	private Lecture lecture;
	private Timeslot timeslot;
	private TA ta;
	
	public Lab(Entity e) {
		super(e);
	}
	
	public Lab(String s) {
		super(s);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public Timeslot getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}

	public TA getTA() {
		return ta;
	}

	public void setTA(TA ta) {
		this.ta = ta;
	}

	public boolean hasTimeslot(){
		if (timeslot == null)
			return false;
		else 
			return true; 
	}
}
