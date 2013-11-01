package taAllocation;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class TAallocation extends PredicateReader implements
		TAallocationPredicates {

	protected long max_labs = Long.MAX_VALUE;
	protected long min_labs = 0;
	protected HashMap<String, TA> tas = new HashMap<String, TA>();
	protected HashMap<String, Instructor> instructors = new HashMap<String, Instructor>();
	protected HashMap<String, Course> courses = new HashMap<String, Course>();
	protected HashMap<String, Timeslot> timeslots = new HashMap<String, Timeslot>();

	static final int DEFAULT_MAX_TIME = 30000;
	static PrintStream traceFile;

	public void e_show(String t1) {
		System.out.println("\n// Instructors");
		for (Entry<String, Instructor> entry : instructors.entrySet())
			System.out.println((entry.getValue().toString()));

		System.out.println("\n// TA's");
		for (Entry<String, TA> entry : tas.entrySet())
			System.out.println(entry.getValue().getName());

		System.out.println("\n// Courses");
		for (Entry<String, Course> entry : courses.entrySet())
			System.out.println(entry.getValue().getName());

		System.out.println("\n// Timeslots");
		for (Entry<String, Timeslot> entry : timeslots.entrySet())
			System.out.println(entry.getValue().getName());

	}

	public static void main(String[] args) {
		try {
			traceFile = new PrintStream(new FileOutputStream("trace.out"));
			traceFile.print("Trace taAllocation.Test");
			for (String s : args)
				traceFile.print(" " + s);
			traceFile.println("\n" + new java.util.Date());
		} catch (Exception ex) {
			traceFile = null;
		}

		PredicateReader env = Environment.get();
		printSynopsis();
		String outfilename = "saved.out";
		commandMode(new TAallocation("Fred"));

		if (traceFile != null) {
			traceFile.println(new java.util.Date());
			traceFile.close();
		}
	}

	/**
	 * Implement "command mode": repeatedly read lines of predicates from
	 * {@link System#in} and let PredicateReaderher assert them (if the line
	 * starts with a "!") or evaluate them (and return "true" or "false" to
	 * {@link System#out}.
	 * 
	 * @param env
	 *            the environment that can interpret the predicates.
	 */
	public static void commandMode(PredicateReader env) {
		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;
		try {
			print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while ((length = System.in.read(buf)) != -1) {
				String s = new String(buf, 0, length);
				s = s.trim();
				if (s.equals("exit"))
					break;
				if (s.equals("?") || s.equals("help")) {
					s = "!help()";
					println("> !help()");
				}
				if (s.length() > 0) {
					if (s.charAt(0) == '!') {
						env.assert_(s.substring(1));
					} else {
						print(" --> " + env.eval(s));
					}
				}
				print("\n> ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			println("exiting: " + e.toString());
		}
	}

	static void printSynopsis() {
		println("Synopsis: Sisyphus <search-prg> [<env-file> [<maxTimeInMilliseconds:default="
				+ DEFAULT_MAX_TIME + ">]]");
	}

	static void println(String s) {
		System.out.println(s);
		traceFile.println(s);
	}

	static void print(String s) {
		System.out.print(s);
		traceFile.print(s);
	}

	static void write(byte[] s, int offset, int count) throws Exception {
		System.out.write(s, offset, count);
		traceFile.write(s, offset, count);
		;
	}

	public TAallocation(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void a_maxlabs(Long p) {
		max_labs = p;
	}

	@Override
	public void a_minlabs(Long p) {
		min_labs = p;
	}

	@Override
	public void a_TA(String p) {
		if (tas.containsKey(p)) {
			// TODO: warning
		} else {
			tas.put(p, new TA(p));
		}
	}

	@Override
	public boolean e_TA(String p) {
		return tas.containsKey(p);
	}

	@Override
	public void a_instructor(String p) {
		if (instructors.containsKey(p)) {
			// TODO: warning
		} else {
			instructors.put(p, new Instructor(p));
		}
	}

	@Override
	public boolean e_instructor(String p) {
		return instructors.containsKey(p);
	}

	@Override
	public void a_course(String p) {
		if (courses.containsKey(p)) {
			// TODO: warning
		} else {
			courses.put(p, new Course(p));
		}
	}

	@Override
	public boolean e_course(String p) {
		return courses.containsKey(p);
	}

	@Override
	public void a_senior_course(String p) {
		Course c = courses.get(p);
		if (c == null) {
			c = new Course(p);
			courses.put(p, c);
		}
		c.setSeniorCourse(true);
	}

	@Override
	public boolean e_senior_course(String p) {
		Course c = courses.get(p);
		return c != null && c.isSeniorCourse();
	}

	@Override
	public void a_grad_course(String p) {
		Course c = courses.get(p);
		if (c == null) {
			c = new Course(p);
			courses.put(p, c);
		}
		c.setGradCourse(true);
	}

	@Override
	public boolean e_grad_course(String p) {
		Course c = courses.get(p);
		return c != null && c.isGradCourse();
	}

	@Override
	public void a_timeslot(String p) {
		if (timeslots.containsKey(p)) {
			// TODO: warning
		} else {
			timeslots.put(p, new Timeslot(p));
		}
	}

	@Override
	public boolean e_timeslot(String p) {
		return timeslots.containsKey(p);
	}

	@Override
	public void a_lecture(String c, String lec) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean e_lecture(String c, String lec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_lab(String c, String lec, String lab) {

	}

	@Override
	public boolean e_lab(String c, String lec, String lab) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_instructs(String p, String c, String l) {
		Course course = courses.get(c);
		if (c == null) {
			// TODO: error - course does not exist
			return;
		}

		if (instructors.containsKey(p)) {
			Instructor instructor = instructors.get(p);
			Lecture lecture = course.getLecture(l);
			if (lecture == null) {
				// TODO: error - lecture does not exist
				return;
			}
			lecture.setInstructor(instructor);
		} else if (tas.containsKey(p)) {
			TA ta = tas.get(p);
			Lab lab = course.getLab(l);
			if (lab == null) {
				// TODO: error - lab does not exist
				return;
			}
			lab.setTA(ta);
		}

	}

	@Override
	public boolean e_instructs(String p, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_at(String c, String l, String t) {
		Timeslot timeslot = timeslots.get(t);
		if (timeslot == null) {
			// TODO: error - timeslot does not exist
		}
		Course course = courses.get(c);
		if (course == null) {
			// TODO: error - course does not exist
		}
		if (course.hasLecture(l)) {
			Lecture lecture = course.getLecture(l);
			lecture.setTimeslot(timeslot);
			timeslot.addEntity(lecture);
			return;
		}
		if (course.hasLab(l)) {
			Lab lab = course.getLab(l);
			lab.setTimeslot(timeslot);
			timeslot.addEntity(lab);
			return;
		}
		// TODO: error - lecture/lab does not exist
	}

	@Override
	public boolean e_at(String c, String l, String t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_knows(String ta, String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_knows(String ta, String c) {
		TA grad = tas.get(ta);
		Course course = courses.get(c);

		if (grad == null) {
			// TODO: error - ta does not exist
		} else if (course == null) {
			// TODO: error - course does not exist
		} else {
			grad.addKnows(course);
		}
		return false;
	}

	@Override
	public void a_prefers(String instructor, String ta, String c) {
		Instructor ins = instructors.get(instructor);
		if (ins == null) {
			throw new RuntimeException("instructor " + instructor
					+ " does not exist or is not an instructor");
		} else {
			ins.addPrefers(taByName(ta), courseByName(c));
		}
	}

	@Override
	public boolean e_prefers(String instructor, String ta, String c) {
		Instructor ins = instructors.get(instructor);
		if (ins == null) {
			throw new RuntimeException("instructor " + instructor
					+ " does not exist or is not an instructor");
		} else {
			return ins.hasPrefers(taByName(ta), courseByName(c));
		}
	}

	@Override
	public void a_prefers1(String ta, String c) {
		taByName(ta).setPrefer1(courseByName(c));
	}

	@Override
	public boolean e_prefers1(String ta, String c) {
		return taByName(ta).getPrefer1().equals(courseByName(c));
	}

	@Override
	public void a_prefers2(String ta, String c) {
		taByName(ta).setPrefer2(courseByName(c));
	}

	@Override
	public boolean e_prefers2(String ta, String c) {
		return taByName(ta).getPrefer2().equals(courseByName(c));
	}

	@Override
	public void a_prefers3(String ta, String c) {
		taByName(ta).setPrefer3(courseByName(c));
	}

	@Override
	public boolean e_prefers3(String ta, String c) {
		return taByName(ta).getPrefer3().equals(courseByName(c));
	}

	@Override
	public void a_taking(String ta, String c, String l) {
		// taByName(ta).addTaking(courseByName(c),
		// courseByName(c).getLecture(l));
	}

	@Override
	public boolean e_taking(String ta, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_conflicts(String t1, String t2) {
		Timeslot s1 = timeslots.get(t1);
		Timeslot s2 = timeslots.get(t2);
		if (s1 == null) {
			s1 = new Timeslot(t1);
			timeslots.put(t1, s1);
		}
		if (s2 == null) {
			s2 = new Timeslot(t2);
			timeslots.put(t2, s2);
		}
		s1.addConflict(s2);
		s2.addConflict(s1);
	}

	@Override
	public boolean e_conflicts(String t1, String t2) {
		Timeslot s1 = timeslots.get(t1);
		Timeslot s2 = timeslots.get(t2);
		return s1.conflicts(s2) || s2.conflicts(s1);
	}

	private TA taByName(String ta) {
		TA gs = tas.get(ta);
		if (gs == null) {
			throw new RuntimeException("TA " + ta + " does not exist");
		}
		return gs;
	}

	private Course courseByName(String c) {
		Course course = courses.get(c);
		if (course == null) {
			throw new RuntimeException("course " + c + " does not exist");
		}
		return course;
	}

}
