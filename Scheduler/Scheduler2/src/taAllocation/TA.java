package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TA extends Entity {

	private String name;
	private Course prefer1;
	private Course prefer2;
	private Course prefer3;
	private List<Course> knows = new ArrayList<Course>();

	// NOTE: these are ONLY the assignments from the input file
	private HashMap<String, Lab> labs = new HashMap<String, Lab>();
	private HashMap<String, Lecture> taking = new HashMap<String, Lecture>();

	/**
	 * Constructor takes in an entity
	 * @param e
	 */
	public TA(Entity e) {
		super(e);
	}
	/**
	 * Constructor takes in a String given the name of the TA
	 * @param s
	 */
	public TA(String s) {
		super(s);
		name = s;
	}
	/**
	 *  Returns a TA's first choice course.
	 *  
	 * @return
	 */
	public Course getPrefer1() {
		return prefer1;
	}
	/**
	 * Sets the TA's first preference returns nothing.
	 * 
	 * @param prefer1
	 */
	public void setPrefer1(Course prefer1) {
		this.prefer1 = prefer1;
	}

	/**
	 *  Returns A TA's second choice course
	 * 
	 * @return prefer2 
	 */
	public Course getPrefer2() {
		return prefer2;
	}

	/**
	 *  Sets the TA's second choice course
	 *  
	 * @param prefer2
	 */
	public void setPrefer2(Course prefer2) {
		this.prefer2 = prefer2;
	}

	/**
	 * 	Returns a TA's third choice course
	 * 
	 * @return prefer3
	 */
	public Course getPrefer3() {
		return prefer3;
	}

	/**
	 *  Sets the TA's third choice course
	 * @param prefer3
	 */
	public void setPrefer3(Course prefer3) {
		this.prefer3 = prefer3;
	}

	/**
	 * Adds a Course to what a TA knows 
	 * @param course
	 */
	public void addKnows(Course course) {
		if (!knows.contains(course))
			knows.add(course);
	}

	/**
	 *  Checks if a TA knows a course 
	 * 
	 * @param course
	 * @return a boolean 
	 */
	public boolean knows(Course course) {
		return knows.contains(course);
	}
	/**
	 *	Overloaded Knows method, Checks if a TA knows a course using a string instead of the course itself
	 * @param courseName
	 * @return Return a boolean if the TA knows a course.
	 */
	public boolean knows(String courseName) {
		for (Course course : knows) {
			if (course.getName().equals(courseName))
				return true;
		}
		return false;
	}
	/**
	 *  Returns a list of Course that the TA knows
	 * @return List<course> Knows
	 */
	public List<Course> getKnows() {
		return knows;
	}

	/**
	 *  Returns all the labs that a TA is either taking or teaching.
	 * 
	 * @returns the collection of labs
	 */
	public Collection<Lab> getLabs() {
		return labs.values();
	}
	/**
	 *  Add's a lab to the list of labs a TA is either taking or instructing. 
	 * @param lab
	 */
	public void addLab(Lab lab) {
		labs.put(lab.getCourse().getName() + " " + lab.getName(), lab);
	}
	/**
	 * Checks the taking pair list to see if a TA is taking a lecture for a specific course
	 *  
	 * @param course 
	 * 				name of the course
	 * @param lec
	 * 	   			name of  the lecture
	 * @return a boolean if a TA is taking a lecture for a particular course. 
	 */
	public boolean isTaking(String course, String lec) {
		return taking.containsKey(course + " " + lec);
	}
	/**
	 * Adds a Lecture to the list of Lectures that a TA is taking. 
	 * @param lecture
	 * 				 lecture that is added to the internal Lecture list that each TA keeps
	 */
	public void addTaking(Lecture lecture) {
		if (!taking.containsKey(lecture.getName())) {
			taking.put(lecture.getCourse().getName() + " " + lecture.getName(),
					lecture);
		} else {
			throw new RuntimeException("already taking that lecture");
		}
	}
	/**
	 * Returns a collection of all the lectures a TA is taking
	 * @return
	 */
	public Collection<Lecture> getTaking() {
		return taking.values();
	}
	/**
	 * Returns an integer of the amount of courses a TA is taking
	 * @return
	 */
	public int amountTaking() {
		return taking.size();
	}

	/**
	 * Checks if the lab conflicts with any courses/labs the TA is associated
	 * with DOES NOT CHECK OTHER CONSTRAINTS!
	 * 
	 * @param lab
	 * @return
	 */
	public boolean canInstruct(Lab lab, Collection<Lab> mylabs) {
		for (Lab lab2 : mylabs) {
			if (lab2.hasTimeslot()
					&& lab2.getTimeslot().conflicts(lab.getTimeslot())) {
				// System.out.println("conflicitng lab" + lab2.getName() + " " +
				// lab2.getCourse().getName() + ", cannot instruct " +
				// lab.getName() + " " + lab.getCourse().getName());
				return false;
			}
		}

		for (Lecture lec : taking.values()) {
			if (lec.hasTimeslot()
					&& lec.getTimeslot().conflicts(lab.getTimeslot())) {
				// System.out.println("conflicitng lecture " + lec.getName() +
				// " " + lec.getCourse().getName() + ", cannot instruct " +
				// lab.getName() + " " + lab.getCourse().getName());
				return false;
			}
		}
		return true;
	}

	/**
	 * @Override toString operator 
	 * @return 
	 */
	public String toString() {
		return "TA [name=" + name + "]";
	}

}
