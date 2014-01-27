package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Constraints {

	protected long max_labs = Long.MAX_VALUE;
	protected long min_labs = 0;
	protected HashMap<String, TA> tas = new HashMap<String, TA>();
	protected HashMap<String, Instructor> instructors = new HashMap<String, Instructor>();
	protected HashMap<String, Course> courses = new HashMap<String, Course>();
	protected HashMap<String, Timeslot> timeslots = new HashMap<String, Timeslot>();

	private boolean generateStopped;
	/**
	 * Constructor
	 */
	public Constraints() {

	}
	/**
	 *  Gets all the TA in the constraints
	 * @return a collection of TA's
	 */
	public Collection<TA> getTAs() {
		return tas.values();
	}
	/**
	 *  Gets the minimum number of labs a TA can instruct
	 * @return long for min_Labs
	 */
	public long getMinLabs() {
		return min_labs;
	}
	/**
	 *  Gets the maximum number of labs a ta can instruct
	 * @return long for max_labs
	 */
	public long getMaxLabs() {
		return max_labs;
	}
	/**
	 *   Returns the list of all the labs in a constraint
	 * @return list of labs
	 */
	public List<Lab> getLabs() {
		LinkedList<Lab> labs = new LinkedList<Lab>();
		for (Course course : courses.values()) {
			labs.addAll(course.getLabs());
		}

		return labs;
	}

	/**
	 * Helper function for generateFacts()
	 * 
	 * @throws InterruptedException
	 */
	protected Facts generate(Facts facts) throws InterruptedException {

		LinkedList<Lab> labs = new LinkedList<Lab>();
		labs.addAll(facts.getUnassignedLabs());
		Collections.shuffle(labs);

		if (labs.size() == 0) {
			return facts.isValid() ? facts : null;
		}

		// select a (random) lab
		Lab lab = labs.pop();

		// get TA's in random order
		LinkedList<TA> tas2 = new LinkedList<TA>();
		tas2.addAll(getTAs());
		Collections.shuffle(tas2);

		// try each TA (if none work, there is not solution)
		while (tas2.size() > 0) {

			if (generateStopped)
				throw new InterruptedException();

			TA ta = tas2.pop();

			if (ta.canInstruct(lab, facts.getAssignments(ta))
					&& facts.getLabCount(ta) < getMaxLabs()) {
				Facts soln = facts.clone();
				soln.assign(ta, lab);
				soln = generate(soln);
				if (soln != null) {
					return soln;
				}
			}
		}

		return null;
	}

	/**
	 * Generates a valid instance given the environment We were going to do a
	 * random walk through the OR-Tree model but instead this is just a dynamic
	 * programming algorithm with random selections
	 * 
	 * @return a new instance of Facts which is valid under these Constraints or
	 *         null if there is no facts that satisfy these COntraints
	 * @throws InterruptedException
	 */
	public Facts generateFacts() throws InterruptedException {
		generateStopped = false;
		Facts f = new Facts(this);
		return generate(f);
	}
	/**
	 * Setter takes in a long value and sets the max_labs to that
	 * 
	 */
	public void setMaxLabs(Long p) {
		this.max_labs = p;
	}
	/**
	 * Setter takes in a long value and sets the min_labs to that
	 * @param p
	 * 			Long value
	 */		
	public void setMinLabs(Long p) {
		this.min_labs = p;
	}
	/**
	 *  Adds a TA to the constraints
	 * @param ta
	 * 			The ta to be added
	 */
	public void addTA(TA ta) {
		tas.put(ta.getName(), ta);
	}
	/**
	 *  Checks if there is a TA in the constraints
	 * @param ta
	 * 			name of the TA to look for
	 * @return boolean if the ta is in the constraints or not
	 */
	public boolean hasTA(String ta) {
		return tas.containsKey(ta);
	}
	/**
	 *  Gets a single TA from the constraints using the TA's name
	 * @param ta
	 * 			name of the TA to look for 
	 * @return TA object that one was looking for
	 * 
	 */
	public TA getTA(String ta) {
		return tas.get(ta);
	}
	/**
	 *  Adds an instructor to the constraints
	 * @param instructor
	 * 			instructor to be added
	 */
	public void addInstructor(Instructor instructor) {
		instructors.put(instructor.getName(), instructor);
	}
	/**
	 * Checks if the constraints has a particular instructor
	 * @param instructor
	 * 			name of the instructor to look for 
	 * @return boolean if the instructor is present in the constraints or not
	 */
	public boolean hasInstructor(String instructor) {
		return instructors.containsKey(instructor);
	}
	/**
	 *  Gets an instructor from the list in the constraints
	 * @param instructor
	 * 			name of the instructor to look for
	 * @return instructor
	 */
	public Instructor getInstructor(String instructor) {
		return instructors.get(instructor);
	}	
	/**
	 * Adds a course to the list of courses in the constraint
	 * @param course
	 * 			Course to  be added
	 */
	public void addCourse(Course course) {
		courses.put(course.getName(), course);
	}

	/**
	 * Checks for a course in the list of course for the constraints
	 * @param p
	 * 			name of the course being looked for
	 * @return boolean if the course exists or not
	 */
	public boolean hasCourse(String p) {
		return courses.containsKey(p);
	}
	/**
	 * Gets a particular course from the list of courses in the constraints
	 * @param p
	 * 			name of the course being looked for
	 * @return course 
	 * 
	 */
	public Course getCourse(String p) {
		return courses.get(p);
	}
	/**
	 * Adds a time slot to the list in the constraints
	 * @param ts
	 * 			time slot to be added
	 */
	public void addTimeslot(Timeslot ts) {
		timeslots.put(ts.getName(), ts);

	}
	/**
	 * Checks for a time slot in the current list in the constraints
	 * @param p
	 * 		name of the time slot 
	 * @return a boolean if the time slot is in the list or not
	 */
	public boolean hasTimeslot(String p) {
		return timeslots.containsKey(p);
	}
	/**
	 *  Does nothing except throw an exception should be looking for a lecture through a course object
	 * @param lec 
	 * 			
	 * @return
	 */
	public boolean hasLecture(String lec) {
		throw new RuntimeException("call course.hasLecture instead!");
	}
	/**
	 * Gets a time slot from the list of time slots
	 * @param ts
	 * 			name of the time slot to get
	 * @return the time slot
	 */
	public Timeslot getTimeslot(String ts) {
		return timeslots.get(ts);
	}
	/**
	 * Helper function throws a call to  stop generating facts
	 */
	public void stopGenerate() {
		this.generateStopped = true;
	}
	/** 
	 * Formats the constraint objects into a string
	 * @return string of the formatted constraint object
	 */
	public String toPredicatesString() {
		String s = "";

		if (min_labs != 0) {
			s += ("minlabs(" + min_labs + ")");
			s += "\n";
		}
		if (max_labs != Long.MAX_VALUE) {
			s += ("maxlabs(" + max_labs + ")");
			s += "\n";
		}
		s += ("");
		s += "\n";

		s += ("// Timeslots");
		s += "\n";
		for (Timeslot timeslot : timeslots.values()) {
			// is this optimized in new java?
			s += ("timeslot(" + timeslot.getName() + ")");
			s += "\n";
		}
		for (Timeslot timeslot : timeslots.values())
			for (Timeslot timeslot2 : timeslot.getConflicts()) {
				s += ("conflicts(" + timeslot.getName() + ","
						+ timeslot2.getName() + ")");
				s += "\n";
			}
		s += ("");
		s += "\n";

		s += ("// Instructors");
		s += "\n";
		for (Instructor instructor : instructors.values()) {
			s += ("instructor(" + instructor.getName() + ")");
			s += "\n";
			for (Lecture lecture : instructor.getLectures()) {
				// moved to course print-out
				// s += ("instructs(" + instructor.getName() + "," +
				// lecture.getCourse().getName() + "," + lecture.getName() +
				// ")");
			}
		}
		s += ("");
		s += "\n";

		s += ("// TAs");
		s += "\n";
		for (TA ta : tas.values()) {
			s += ("TA(" + ta.getName() + ")");
			s += "\n";
		}
		s += ("");
		s += "\n";

		s += ("// Courses");
		s += "\n";
		for (Course course : courses.values()) {
			if (course.isGradCourse()) {
				s += ("grad-course(" + course.getName() + ")");
				s += "\n";
			} else if (course.isSeniorCourse()) {
				s += ("senior-course(" + course.getName() + ")");
				s += "\n";
			} else {
				s += ("course(" + course.getName() + ")");
				s += "\n";
			}

			for (Lecture lecture : course.getLectures()) {
				Timeslot timeslot = lecture.getTimeslot();
				s += ("lecture(" + course.getName() + "," + lecture.getName() + ")");
				s += "\n";
				if (timeslot != null)
					s += ("at(" + course.getName() + "," + lecture.getName()
							+ "," + timeslot.getName() + ")");
				s += "\n";
				Instructor instructor = lecture.getInstructor();
				if (instructor != null)
					s += ("instructs(" + instructor.getName() + ","
							+ course.getName() + "," + lecture.getName() + ")");
				s += "\n";
			}

			for (Lab lab : course.getLabs()) {
				Timeslot timeslot = lab.getTimeslot();
				if (lab.getLecture() != null) {
					s += ("lab(" + course.getName() + ","
							+ lab.getLecture().getName() + "," + lab.getName() + ")");
					s += "\n";
					if (timeslot != null)
						s += ("at(" + course.getName() + "," + lab.getName()
								+ "," + timeslot.getName() + ")");
					s += "\n";
				}
			}

			s += ("");
			s += "\n";
		}
		s += ("");
		s += "\n";

		s += ("// TA stuff");
		s += "\n";
		for (TA ta : tas.values()) {
			for (Lab lab : ta.getLabs()) {
				// TA-name, course-name, lab-name
				s += ("instructs(" + ta.getName() + ","
						+ lab.getCourse().getName() + "," + lab.getName() + ")");
				s += "\n";
			}
			if (ta.getPrefer1() != null)
				s += ("prefers1(" + ta.getName() + ","
						+ ta.getPrefer1().getName() + ")");
			s += "\n";
			if (ta.getPrefer2() != null)
				s += ("prefers2(" + ta.getName() + ","
						+ ta.getPrefer2().getName() + ")");
			s += "\n";
			if (ta.getPrefer3() != null)
				s += ("prefers3(" + ta.getName() + ","
						+ ta.getPrefer3().getName() + ")");
			s += "\n";
			for (Course course : ta.getKnows()) {
				s += ("knows(" + ta.getName() + "," + course.getName() + ")");
				s += "\n";
			}
		}
		s += ("");
		s += "\n";
		return s;
	}
	/**
	 *  Gets all the courses in a constraint object
	 * @return collection of Courses in the constraints list
	 */
	public Collection<Course> getCourses() {
		return courses.values();
	}

	public Lab getLab(String lab) {
		for (Lab l : getLabs()) {
			if (l.getName().equals(lab))
				return l;
		}
		return null;
	}
	/**
	 *  Gets a list of all the instructors in a constrain object
	 *  
	 * @return a collection of instructors
	 */
	public Collection<Instructor> getInstructors() {
		return instructors.values();
	}

}
