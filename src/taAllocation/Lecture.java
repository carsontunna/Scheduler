package taAllocation;

import java.util.HashMap;

public class Lecture extends Entity {

	private Course course;
	private Timeslot timeslot;
	private Instructor instructor = null;
	/**
	 * Constructor from entity
	 * @param e 
	 */
	public Lecture(Entity e) {
		super(e);
	}
	/**
	 * Constructor from a string
	 * @param s the name of the lecture
	 */
	public Lecture(String s) {
		super(s);
	}
	/**
	 * Gets the course attached to the lecture
	 * @return course attached to the lecture
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 *  Sets the course for a lecture
	 * @param course to be attached to the lecture
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	/** 
	 * Gets the time slot attached to the lecture
	 * @return returns timeslot
	 */
	public Timeslot getTimeslot() {
		return timeslot;
	}
	/**
	 *  Sets the time slot attached to the lecture
	 * @param timeslot 
	 * 				time slot to attach to the leture
	 */
	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}
	/**
	 * 	Returns the instructor attached to a lecture
	 * @return instructor
	 */
	public Instructor getInstructor() {
		return instructor;
	}
	/**
	 * Sets the instructor attached to this lecture
	 * @param instructor 
	 * 					instructor to be attached to lecture 
	 */
	public void setInstructor(Instructor instructor) {
		if (instructor == null)
			throw new RuntimeException("Dr. Null");
		this.instructor = instructor;
		instructor.addLecture(this);
	}
	/**
	 *  Returns a boolean if the lecture has a time slot
	 *  
	 * @return boolean
	 *
	 */
	public boolean hasTimeslot() {
		if (timeslot == null)
			return false;
		else
			return true;
	}
	/**
	 * Returns a boolean if the lecture has an instructor
	 *
	 * @return boolean
	 */
	public boolean hasInstrctor() {
		return instructor != null;
	}
}
