package taAllocation;

public class Environment extends PredicateReader implements
		TAallocationPredicates {
	/**
	 *  Constructor string taking Environment name
	 * @param name
	 */
	public Environment(String name) {
		super(name);
	}

	private Constraints cons = new Constraints();

	/**
	 * Getter to get the constraints of an environment
	 * @return
	 */
	public Constraints getConstraints() {
		return cons;
	}
	/**
	 *  Sets the max labs for Constraints
	 */
	@Override
	public void a_maxlabs(Long p) {
		cons.setMaxLabs(p);
	}
	/**
	 * Sets the min labs for Constraints
	 */
	@Override
	public void a_minlabs(Long p) {
		cons.setMinLabs(p);

	}
	/**
	 *  Asserts a TA in to the constraints
	 */
	@Override
	public void a_TA(String p) {
		TA ta = new TA(p);
		// 2nd occurrence: do nothing (warning message)
		if (cons.hasTA(p)) {
			predicateWarning("2nd occurence of TA " + p);
		} else {
			cons.addTA(ta);
		}
	}
	/**
	 *  Boolean checks if a TA already exists
	 */
	@Override
	public boolean e_TA(String p) {
		return cons.hasTA(p);
	}
	/**
	 * Adds an instructor to the constraints
	 */
	@Override
	public void a_instructor(String p) {
		Instructor i = new Instructor(p);
		// 2nd occurrence: do nothing (warning message)
		if (cons.hasInstructor(p)) {
			predicateWarning("2nd occurence of Instructor " + p);
		} else {
			cons.addInstructor(i);
		}
	}
	/**
	 * Boolean checks if a Instructor is already in constraints
	 */
	@Override
	public boolean e_instructor(String p) {
		return cons.hasInstructor(p);
	}
	/**
	 * Adds a course to the constraints
	 */
	@Override
	public void a_course(String p) {
		Course c = new Course(p);
		// 2nd occurrence: do nothing (warning message)
		if (cons.hasCourse(p)) {
			predicateWarning("2nd occurence of Course " + p);
		} else {
			cons.addCourse(c);
		}
	}
	/**
	 * Boolean checks if a course is already in the constraints
	 */
	@Override
	public boolean e_course(String p) {
		return cons.hasCourse(p);
	}
	/**
	 * Adds a senior course to the constraints
	 */
	@Override
	public void a_senior_course(String p) {
		Course c = cons.getCourse(p);
		if (c == null) {
			c = new Course(p);
			cons.addCourse(c);
		} else {
			// 2nd occurrence: set the course to senior (regardless)
			predicateWarning("2nd occurence of (Senior) Course " + p);
		}
		// TODO: what if it is a grad course?
		c.setSeniorCourse(true);
	}
	/**
	 * Checks if a senior course is already in the constraints
	 */
	@Override
	public boolean e_senior_course(String p) {
		Course c = cons.getCourse(p);
		return (c != null && c.isSeniorCourse());
	}
	/**
	 * Adds a grad course to the constraints
	 */
	@Override
	public void a_grad_course(String p) {
		Course c = cons.getCourse(p);
		if (c == null) {
			c = new Course(p);
			cons.addCourse(c);
		} else {
			// 2nd occurrence: set the course to senior (regardless)
			predicateWarning("2nd occurence of (Grad) Course " + p);
		}
		// TODO: what if it is a senior course?
		c.setGradCourse(true);
	}
	/**
	 * Boolean checks if a grad course is already in the constraints
	 */
	@Override
	public boolean e_grad_course(String p) {
		Course c = cons.getCourse(p);
		return (c != null && c.isGradCourse());
	}
	/**
	 * Adds a time slot to the constraints
	 */
	@Override
	public void a_timeslot(String p) {
		// 2nd occurrence: do nothing (warning message)
		if (cons.hasTimeslot(p)) {
			predicateWarning("2nd occurence of Timeslot " + p);
		} else {
			cons.addTimeslot(new Timeslot(p));
		}
	}
	/**
	 * Checks if a time slot is already in the constraints
	 */
	@Override
	public boolean e_timeslot(String p) {
		return cons.hasTimeslot(p);
	}
	/**
	 * Adds a lecture to the constraints
	 */
	@Override
	public void a_lecture(String c, String lec) {

		if (!cons.hasCourse(c)) {
			// Course doesn't exist: ignore (error message)
			predicateError("Course " + c + " does not exist (for Lecture "
					+ lec + ")");
			return;
		}
		Course course = cons.getCourse(c);
		if (course.hasLecture(lec)) {
			predicateWarning("2nd occurence of Lecture " + lec);
		} else {
			course.addLecture(lec);
		}
	}
	/**
	 * Boolean checks if a lecture is already in the constraints
	 */
	@Override
	public boolean e_lecture(String c, String lec) {
		return cons.hasCourse(c) && cons.getCourse(c).hasLecture(lec);
	}
	/**
	 * Adds a lab to the constraints
	 */
	@Override
	public void a_lab(String c, String lec, String lab) {
		Course course = cons.getCourse(c);
		if (course == null) {
			// Course or lecture doesn't exist: ignore (error message);
			predicateError("Course " + c + " does not exist (for Lab " + lab
					+ ")");
			return;
		}

		Lecture lecture = course.getLecture(lec);
		if (lecture == null) {
			// Course or lecture doesn't exist: ignore (error message);
			predicateError("Lecture " + lec + " does not exist (for Lab " + lab
					+ ")");
			return;
		}
		if (course.hasLab(lab)) {
			// 2nd occurrence: do nothing (warning message)
			predicateWarning("2nd occurence of Lab " + lec + " (" + c + ","
					+ lec + ")");
		} else {
			course.addLab(lab, lec);
		}

	}
	/**
	 * Boolean checks if a lab is already in the constraints
	 */
	@Override
	public boolean e_lab(String c, String lec, String lab) {
		return cons.hasCourse(c) && cons.getCourse(c).hasLab(lab);
	}
	/**
	 * Adds a professor instructs into the constraints
	 */
	@Override
	public void a_instructs(String p, String c, String l) {
		if (!cons.hasCourse(c)) {
			// course doesn't exist: ignore (error message);
			predicateError("Course " + c + " does not exist");
			return;
		}
		Course course = cons.getCourse(c);

		// instructor doesn't exist: ignore (error message);
		if (cons.hasInstructor(p)) {
			Instructor instructor = cons.getInstructor(p);

			if (!course.hasLecture(l)) {
				// lecture doesn't exist: ignore (error message);
				predicateError("Lecture " + l + " does not exist");
				return;
			}

			Lecture lecture = course.getLecture(l);

			if (lecture.hasInstrctor()) {
				// NOTE: does not assign the new Instructor!
				predicateWarning("Lecture " + l + "(" + c
						+ ") already has an Instructor, cannot assign " + p);
			}
			lecture.setInstructor(instructor);

		} else if (cons.hasTA(p)) {

			TA ta = cons.getTA(p);

			if (!course.hasLab(l)) {
				// lab doesn't exist: ignore (error message);
				predicateError("Lab " + l + " does not exist");
				return;
			}

			Lab lab = course.getLab(l);

			if (lab.hasTA()) {
				// NOTE: does not assign the new TA!
				predicateWarning("Lab " + l + "(" + c
						+ ") already has a TA, cannot assign " + p);
			} else {
				lab.setTA(ta);
				ta.addLab(lab);
			}

		} else {
			predicateError("There is no TA or Instructor named " + p);
			return;
		}
	}
	/**
	 * boolean check if an instructor is already instructing a course in the constraints
	 */
	@Override
	public boolean e_instructs(String p, String c, String l) {
		if (!cons.hasCourse(c)) {
			// course doesn't exist: ignore (error message);
			predicateError("Course " + c + " does not exist");
			return false;
		}
		Course course = cons.getCourse(c);

		// instructor doesn't exist: ignore (error message);
		if (cons.hasInstructor(p)) {
			Instructor instructor = cons.getInstructor(l);

			if (!course.hasLecture(l)) {
				// lecture doesn't exist: ignore (error message);
				predicateError("Lecture " + l + " does not exist");
				return false;
			}

			Lecture lecture = course.getLecture(l);

			return lecture.getInstructor().equals(instructor);

		} else if (cons.hasTA(p)) {

			TA ta = cons.getTA(p);

			if (!course.hasLab(l)) {
				// lab doesn't exist: ignore (error message);
				predicateError("Lab " + l + " does not exist");
				return false;
			}

			Lab lab = course.getLab(l);

			return lab.getTA().equals(ta);

		} else {
			predicateError("There is no TA or Instructor named " + p);
			return false;
		}
	}
	/**
	 * Adds a time for a lecture/lab to the constraints
	 */
	@Override
	public void a_at(String c, String l, String t) {
		if (!cons.hasCourse(c)) {
			// course/lab/lecture/timeslot doesn't exit: ignore (error message);
			predicateError("Course " + c + " does not exist (for at)");
			return;
		}
		if (!cons.hasTimeslot(t)) {
			// course/lab/lecture/timeslot doesn't exit: ignore (error message);
			predicateError("Timeslot " + t + " does not exist (for at " + c
					+ ")");
			return;
		}
		Course course = cons.getCourse(c);
		Timeslot timeslot = cons.getTimeslot(t);
		if (course.hasLecture(l)) {
			Lecture lecture = course.getLecture(l);
			if (lecture.hasTimeslot()) {
				// lab/lecture already has a timeslot: ignore (error message);
				predicateError("Lecture " + c + "/" + l
						+ " already has timeslot (for at)");
			} else {
				lecture.setTimeslot(timeslot);
			}
		} else if (course.hasLab(l)) {
			Lab lab = course.getLab(l);
			if (lab.hasTimeslot()) {
				// lab/lecture already has a timeslot: ignore (error message);
				predicateError("Lab " + c + "/" + l
						+ " already has timeslot (for at)");
			} else {
				lab.setTimeslot(timeslot);
			}
		} else {
			// course/lab/lecture/timeslot doesn't exit: ignore (error message);
			predicateError("Lab/Lecture " + l + " does not exist (for at " + c
					+ ")");
			return;
		}
	}
	/**
	 * boolean checks if a lecture/lab already has a time in the 
	 */
	@Override
	public boolean e_at(String c, String l, String t) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Adds a TA knows a course to the constraints
	 */
	@Override
	public void a_knows(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)) {
			predicateError("TA: " + ta + "for course " + c + " doesn't exist");
			return;
		}

		if (!cons.getTA(ta).knows(cons.getCourse(c))) {
			cons.getTA(ta).addKnows(cons.getCourse(c));
		}
	}
	/**
	 * boolean checks if a TA knows a course already in the contraints
	 */
	@Override
	public boolean e_knows(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)) {
			predicateError("TA/course doesn't exist");
			return false;
		} else {
			return cons.getTA(ta).getKnows().equals(c);
		}
	}
	/** 
	 * Adds an instructor prefers a TA for a course to the constraints
	 */
	@Override
	public void a_prefers(String instructor, String ta, String c) {
		Instructor prof = cons.getInstructor(instructor);
		TA obj = cons.getTA(ta);
		Course course = cons.getCourse(c);

		// if instructor/ta/c doesn't exist print error
		if ((prof == null) || (obj == null) || (course == null))
			predicateError("TA/course/instructor doesn't exist");

		// if doesn't already exist, add it
		if (!prof.hasPrefers(obj, course))
			prof.addPrefers(obj, course);

	}
	/**
	 * Checks if an instructor already prefers a TA for a course in the constraints
	 */
	@Override
	public boolean e_prefers(String instructor, String ta, String c) {
		Instructor prof = cons.getInstructor(instructor);
		TA obj = cons.getTA(ta);
		Course course = cons.getCourse(c);

		// if instructor/ta/c doesn't exist print error
		if ((prof == null) || (obj == null) || (course == null)) {
			predicateError("TA/course/instructor doesn't exist");
			return false;
		}

		return prof.hasPrefers(obj, course);
	}
	/**
	 * Adds a TA's first preference course to the constraints
	 */
	@Override
	public void a_prefers1(String ta, String c) {
		TA Temp = cons.getTA(ta);
		Course temp = cons.getCourse(c);
		if (!cons.hasTA(ta) || !cons.hasCourse(c))
			predicateError("TA/course doesn't exist");
		else
			Temp.setPrefer1(temp);

	}
	/**
	 * Checks if a TA's First preference couse is already in the constraints
	 */
	@Override
	public boolean e_prefers1(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)) {
			predicateError("TA/course doesn't exist");
			return false;
		}
		return cons.getTA(ta).getPrefer1().equals(cons.getCourse(c));

	}
	/**
	 *  Adds a TA's second preference course to the constraints
	 */
	@Override
	public void a_prefers2(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c))
			predicateError("TA/course doesn't exist");
		else
			cons.getTA(ta).setPrefer2(cons.getCourse(c));

	}
	/**
	 * Checks if a TA already has a second preference course in constraints
	 */
	@Override
	public boolean e_prefers2(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)) {
			predicateError("TA/course doesn't exist");
			return false;
		}
		return cons.getTA(ta).getPrefer2().equals(cons.getCourse(c));

	}
	/** 
	 * Adds a TA's third preference course to the constraints 
	 */
	@Override
	public void a_prefers3(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c))
			predicateError("TA/course doesn't exist");
		else
			cons.getTA(ta).setPrefer3(cons.getCourse(c));

	}
	/**
	 * Checks if a TA's third preference course is already in constraints
	 */
	@Override	
	public boolean e_prefers3(String ta, String c) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)) {
			predicateError("TA/course doesn't exist");
			return false;
		}
		return cons.getTA(ta).getPrefer3().equals(cons.getCourse(c));

	}
	/** 
	 * Adds a TA is taking a class to the constraints
	 */
	@Override
	public void a_taking(String ta, String c, String l) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)
				|| !cons.getCourse(c).hasLecture(l)) {
			// TA/course/lecture doesn't exist: ignore (error message);
			if (!cons.hasTA(ta))
				predicateError("TA " + ta + " does not exist (for taking)");
			if (!cons.hasCourse(ta))
				predicateError("Course " + c + " does not exist (for taking)");
			if (!cons.getCourse(c).hasLecture(l))
				predicateError("No Lecture " + l + " for Course " + c
						+ " (for taking)");
			return;
		}

		TA t = cons.getTA(ta);
		if (t.isTaking(c, l)) {
			predicateWarning("2nd occurence for " + ta + " taking " + c + "/"
					+ l);
		} else {
			t.addTaking(cons.getCourse(c).getLecture(l));
		}
	}
	/**
	 * Checks if a TA is already taking a course in the constraints
	 */
	@Override
	public boolean e_taking(String ta, String c, String l) {
		if (!cons.hasTA(ta) || !cons.hasCourse(c)
				|| !cons.getCourse(c).hasLecture(l)) {
			return false;
		} else {
			return cons.getTA(ta).isTaking(c, l);
		}
	}
	/**
	 * Adds a time conflict to the constraints
	 */
	@Override	
	public void a_conflicts(String t1, String t2) {

		Timeslot ob1, ob2;

		if (!cons.hasTimeslot(t1)) {
			ob1 = new Timeslot(t1);
			cons.addTimeslot(ob1);
		} else {
			ob1 = cons.getTimeslot(t1);
		}

		if (!cons.hasTimeslot(t2)) {
			ob2 = new Timeslot(t2);
			cons.addTimeslot(ob2);
		} else {
			ob2 = cons.getTimeslot(t2);
		}

		ob1.addConflict(ob2);
		ob2.addConflict(ob1);
	}
	/**
	 * Checks if a conflict already exists within the constraints
	 */
	@Override
	public boolean e_conflicts(String t1, String t2) {
		// TODO: make this better
		return cons.hasTimeslot(t1) && cons.hasTimeslot(t2)
				&& cons.getTimeslot(t1).conflicts(cons.getTimeslot(t2));
	}
	/**
	 *  Helper function generates an error message for predicate violations
	 * @param s
	 */
	private void predicateError(String s) {
		System.err.println("PREDICATE ERROR: " + s);
	}
	/**
	 * Helper function generates a warning meessage for predicate duplicates
	 * @param s
	 */
	private void predicateWarning(String s) {
		System.err.println("PREDICATE WARNING: " + s);
	}
	/**
	 * Formats the environment for a string output
	 * @return
	 */
	public String toPredicatesString() {
		return "// Environment \"" + this.getName() + "\"\n"
				+ cons.toPredicatesString();
	}
}
