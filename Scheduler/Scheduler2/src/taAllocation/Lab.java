package taAllocation;

public class Lab extends Entity {

	private Course course;
	private Lecture lecture;
	private Timeslot timeslot;
	private TA ta;
	
	/**
	 *  Constructor using entity as an input parameter
	 * @param e
	 */
	public Lab(Entity e) {
		super(e);
	}
	/**
	 *  Constructor using a string of the name of lab
	 * @param s
	 */
	public Lab(String s) {
		super(s);
	}
	/**
	 *  Gets the course attached to a lab
	 * @return course attached to  a lab
	 */
	public Course getCourse() {
		return course;
	}
	/**
	 * Sets the course attached to a lab
	 * @param course to attach to a lab
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	/**
	 * Gets the lecture attached to a lab
	 * 
	 * @return the lecture attached to a lab
	 */
	public Lecture getLecture() {
		return lecture;
	}
	/**
	 * Sets the lecture to attach to a lab
	 * @param lecture to attach to  a lab
	 */
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	/**
	 *  Gets the time slot attached to  a lab
	 * @return timeslot attached to a lab
	 */
	public Timeslot getTimeslot() {
		return timeslot;
	}
	/**
	 *  Sets the time slot attached to a lab
	 * @param timeslot to  attach to  a lab
	 */
	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}
	/**
	 *  Returns a boolean if the lab has a TA assigned to it
	 * @return boolean
	 */
	public boolean hasTA() {
		return ta != null;
	}
	/**
	 *  Returns the TA attached to a lab
	 * 
	 * @return TA attached to lab
	 */
	public TA getTA() {
		return ta;
	}
	/**
	 * Sets a TA that is either Taking or instructing a lab
	 * 
	 * @param ta to  be attached to a lab
	 */
	public void setTA(TA ta) {
		this.ta = ta;
	}
	/**
	 *  Returns a boolean if a lab has a timeslot
	 * @return boolean
	 */
	public boolean hasTimeslot() {
		if (timeslot == null)
			return false;
		else
			return true;
	}
	/**
	 * Determines if two labs are equal to one another.
	 * 
	 *   @return boolean if two labs are equal.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Lab))
			return false;
		Lab lab = (Lab) o;
		if (!(lab.getName().equals(this.getName())))
			return false;
		if (!(lab.getCourse().getName().equals(this.getCourse().getName())))
			return false;
		if (!(lab.getLecture().getName().equals(this.getLecture().getName())))
			return false;
		return true;
	}
}
