package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Instructor extends Entity {

	private List<Pair<TA, Course>> prefers = new ArrayList<Pair<TA, Course>>();
	private HashMap<String, Lecture> lectures = new HashMap<String, Lecture>();
	/**
	 * Constructor using entity
	 * @param e
	 * 			Entity
	 */		
	public Instructor(Entity e) {
		super(e);
	}
	/**
	 * Constructor using String that represents Instructors name
	 * @param s	
	 * 			Professors name
	 */
	public Instructor(String s) {
		super(s);
	}
	/**
	 *  Method adds a preferred TA to instruct a lab for a professor
	 * @param ta	
	 * 			The preferred TA of the professor
	 * @param course
	 * 				The course that the professor prefers the TA to instruct
	 */
	public void addPrefers(TA ta, Course course) {
		prefers.add(new Pair<TA, Course>(ta, course));
	}
	/**
	 *  Determines if a Professor has a particular preferred TA and returns a boolean if an instructor object does or not
	 * @param ta
	 * 			TA for which the instructor prefers
	 * @param course	
	 * 				Course in which the instructor prefers a particular TA to teach
	 * @return booleans if TA and Course pair is found
	 * 		
	 */
	public boolean hasPrefers(TA ta, Course course) {
		for (Pair<TA, Course> pair : prefers) {
			if (pair.getKey().equals(ta) && pair.getValue().equals(course))
				return true;
		}
		return false;
	}
	/**
	 *  Returns a collection of all the TA, Course pairs that an instructor prefers to have Teach
	 * @return A collection of TA,Course pairs
	 */
	public Collection<Pair<TA, Course>> getPrefers() {
		return prefers;
	}
	/**
	 * Method to return all the lectures that a particular Instructor is teaching 
	 * 
	 * @return collection of lectures 
	 */
	public Collection<Lecture> getLectures() {
		return lectures.values();
	}
	/**
	 *  Method to add a lecture that an instructor is teaching. 
	 * @param lecture 
	 * 				lecture that an instructor is teaching
	 */
	public void addLecture(Lecture lecture) {
		lectures.put(lecture.getName(), lecture);
	}
	/**
	 * Method to return all the TA's an instructor prefers for a particular course
	 * @param course
	 * 				course to get all preferred TA's for 
	 * @return a collection of TA's 
	 *
	 */
	public Collection<TA> getPreferred(Course course) {
		ArrayList<TA> tas = new ArrayList<TA>();
		for (Pair<TA, Course> pref : prefers) {
			if (pref.getValue().equals(course))
				tas.add(pref.getKey());
		}
		return tas;
	}
}
