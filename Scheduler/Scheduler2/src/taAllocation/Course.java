package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Course extends Entity {

	private boolean seniorCourse = false;
	private boolean gradCourse = false;
	private HashMap<String, Lecture> lectures = new HashMap<String, Lecture>();
	private HashMap<String, Lab> labs = new HashMap<String, Lab>();
	/**
	 * Constructor using entity 
	 * @param e
	 */
	public Course(Entity e) {
		super(e);
	}
	/**
	 * Constructor using a string
	 * @param s
	 * 			name of the course
	 */
	public Course(String s) {
		super(s);
	}
	/**
	 *  Gets if a course is a senior course or not
	 * @return boolean of senior course
	 *
	 */
	public boolean isSeniorCourse() {
		return seniorCourse;
	}
	/**
	 * Sets the boolean if a a course is senior or not
	 * @param seniorCourse
	 */
	public void setSeniorCourse(boolean seniorCourse) {
		// TODO: check if grad course- not necessary because if this getting
		// called then we know it already is a grad course.
		this.seniorCourse = seniorCourse;
	}
	/**
	 *  Gets the boolean if a course is a grad course or not
	 * @return boolean value 
	 */
	public boolean isGradCourse() {
		return gradCourse;
	}
	/**
	 *  sets a the boolean if a course is a grad course or not.
	 * @param gradCourse
	 */
	public void setGradCourse(boolean gradCourse) {
		// TODO: check if senior course- same thing as grad course here here.
		this.gradCourse = gradCourse;
	}

	// ////////////////////////lectures
	/**
	 *  checks to see if a course has a particular lecture
	 * @param lec
	 * 			the lecture to check
	 * @return boolean if the course has the lecture or not
	 */
	public boolean hasLecture(String lec) {
		return lectures.containsKey(lec);
	}
	/**
	 *  Adds a lecture to a course
	 * @param lec
	 * 			the lecture to add
	 */
	public void addLecture(String lec) {	
		Lecture lecture = new Lecture(lec);
		lecture.setCourse(this);

		lectures.put(lec, lecture);
	}
	/**
	 *  Returns all the lecture associated with a particular course
	 * @return collection of lectures
	 */
	public Collection<Lecture> getLectures() {
		return lectures.values();
	}
	/**
	 * Gets a lecture from a course using its name in the form of a string
	 * @param lec
	 * 			the name of the course to find
	 * @return the lecture
	 */
	public Lecture getLecture(String lec) {
		return lectures.get(lec);
	}

	// ///////////////////////labs
	/**
	 * Checks if a course has a particular lab
	 * @param lab
	 * 			Lab to check if a course has
	 * @return boolean if the course has the lab or not
	 */
	public boolean hasLab(String lab) {
		return labs.containsKey(lab);
	}
	/**
	 *  Adds a lab to a course 
	 * @param lab
	 * 			lab to add to the course
	 * @param lec
	 * 			lecture to  add the lab to 
	 */
	public void addLab(String lab, String lec) {
		Lab laboratory = new Lab(lab);
		laboratory.setCourse(this);
		laboratory.setLecture(this.getLecture(lec));
		labs.put(lab, laboratory);
	}
	/**
	 *  Gets a list of all the labs attached to a lecture for a this course object
	 * @param lecture
	 * 			the lecture to get the labs from
	 * @return collection of labs
	 */
	public Collection<Lab> getLabs(Lecture lecture) {
		ArrayList<Lab> labs2 = new ArrayList<Lab>();
		for (Lab lab : labs.values()) {
			if (lab.getLecture().equals(lecture))
				labs2.add(lab);
		}
		return labs2;
	}
	/**
	 * Gets all the labs for a course
	 * @return collection of labs
	 */
	public Collection<Lab> getLabs() {
		return labs.values();
	}
	/**
	 * Gets a particular lab by its names
	 * @param lab
	 * 			name of the lab
	 * @return lab
	 */
	public Lab getLab(String lab) {
		return labs.get(lab);
	}

}
