package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

public class Facts {

	private List<Pair<TA, Lab>> internalAllocation = new ArrayList<Pair<TA, Lab>>();

	private static Random rnd = new Random(System.currentTimeMillis());
	private long creationtime;

	private boolean mutateStopped;

	Constraints constraints;
	/**
	 *  Constructor takes in a constrain object and sets creation time
	 * @param constraints
	 */
	public Facts(Constraints constraints) {
		creationtime = System.currentTimeMillis();
		this.constraints = constraints;

	}
	
	/**
	 * Method to get the collection of all Lab allocations 
	 * @return collection of TA,Lab pairs 
	 */
	public Collection<Pair<TA, Lab>> getAllocation() {
		// if (allocationCache != null) return allocationCache;

		List<Pair<TA, Lab>> allocation2 = new ArrayList<Pair<TA, Lab>>();
		allocation2.addAll(internalAllocation);

		for (TA ta : constraints.getTAs()) {
			for (Lab lab : ta.getLabs()) {
				allocation2.add(new Pair<TA, Lab>(ta, lab));
			}
		}

		return allocation2;
	}
	/**
	 * Gets the creation time of the fact. 
	 * @return long creation time
	 */
	public long getCreationTime() {
		return creationtime;
	}
	/**
	 *  Method to make a new pair TA,Lab pair for allocations 
	 * @param ta
	 * 			TA to pair Lab with 
	 * @param lab
	 * 			Lab to pair TA with
	 */
	public void assign(TA ta, Lab lab) {
		Pair<TA, Lab> pair = new Pair<TA, Lab>(ta, lab);
		if (!hasAssignment(ta, lab)) {
			internalAllocation.add(pair);
		}
		// System.out.println("assign " + ta.getName() + " to " +
		// lab.getCourse().getName() + " " + lab.getName());
	}
	/**
	 *  Assigns all pairs into an internal allocation of a fact 
	 * @param pairs
	 * 				Takes in a list of pairs to assign to a fact. 
	 */
	public void assignAll(List<Pair<TA, Lab>> pairs) {
		for (Pair<TA, Lab> pair : pairs) {
			assign(pair.getKey(), pair.getValue());
		}
	}

	/**
	 * Returns all labs which do not have a TA assigned to them
	 * 
	 * @return
	 */
	public Collection<Lab> getUnassignedLabs() {

		// names of labs with TA assignment
		List<String> assigned = new ArrayList<String>();
		for (Pair<TA, Lab> pair : getAllocation()) {
			Lab lab = pair.getValue();
			assigned.add(lab.getCourse().getName() + " " + lab.getName());
		}

		// unassigned labs
		HashMap<String, Lab> unassigned = new HashMap<String, Lab>();
		// System.out.println(constraints.getLabs().size());

		for (Lab lab : constraints.getLabs()) {
			String name = lab.getCourse().getName() + " " + lab.getName();
			if (!assigned.contains(name)) {
				unassigned.put(lab.getCourse() + " " + lab.getName(), lab);
			}

		}
		return unassigned.values();

		// return labs.values();
	}

	/**
	 * Returns a copy of this instance
	 */
	public Facts clone() {
		Facts newInstance = new Facts(constraints);
		newInstance.assignAll(internalAllocation);
		return newInstance;
	}

	/**
	 *  Checks if there is an assignment of a particular TA and Lab
	 * @param ta
	 * 			TA to check for
	 * @param lab
	 * 			Lab to  check for
	 * @return boolean if there is an assignment
	 * 
	 */
	public boolean hasAssignment(TA ta, Lab lab) {
		for (Pair<TA, Lab> pair : getAllocation()) {
			if (pair.getKey().getName().equals(ta.getName())
					&& pair.getValue().equals(lab))
				return true;
		}
		return false;
	}
	/**
	 *  Method to determine if two facts are equal to one another.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Facts))
			return false;

		Facts facts = (Facts) o;

		if (facts.getAllocation().size() != this.getAllocation().size())
			return false;

		for (Pair<TA, Lab> pair : getAllocation()) {
			if (!hasAssignment(pair.getKey(), pair.getValue()))
				return false;
		}

		return false;
	}
	/**
	 *  Method to return all allocations for a particualar
	 * @param lab
	 * @return
	 */
	public Collection<TA> getAssignments(Lab lab) {
		ArrayList<TA> tas = new ArrayList<TA>();
		for (Pair<TA, Lab> pair : getAllocation()) {
			if (pair.getValue().equals(lab))
				tas.add(pair.getKey());
		}
		return tas;
	}
	/**
	 *  Gets all the labs assigned to a particular TA 
	 * @param ta 
	 * 			The TA to get the labs for 
	 * @return Collection of all the labs a TA is assigned to
	 */
	public Collection<Lab> getAssignments(TA ta) {
		ArrayList<Lab> labs = new ArrayList<Lab>();
		for (Pair<TA, Lab> pair : getAllocation()) {
			if (pair.getKey().getName().equals(ta.getName()))
				labs.add(pair.getValue());
		}
		return labs;
	}
	/**
	 *  Checks if a TA is assigned to  a course
	 * @param ta
	 * 			TA to check
	 * @param course
	 * 				Course to check
	 * @return boolean if the TA is assigned or not
	 */
	public boolean isAssignedToCourse(TA ta, Course course) {
		for (Lab lab : getAssignments(ta)) {
			if (lab.getCourse().equals(course))
				return true;
		}
		return false;
	}

	/**
	 * Checks if any hard constraints are violated
	 * 
	 * @return a boolean
	 */
	public boolean isValid() {
		long maxlabs = constraints.getMaxLabs();
		long minlabs = constraints.getMinLabs();

		final boolean debug = false;

		if (!getUnassignedLabs().isEmpty()) {
			if (debug)
				System.out.println("Unassigned Labs remain");
			return false;
		}

		for (TA ta : constraints.getTAs()) {
			Collection<Lab> labs = getAssignments(ta);

			if (labs.size() > maxlabs) {
				if (debug)
					System.out.println("isValid(): exceeds maxlabs");
				return false;
			}

			if (labs.size() < minlabs && labs.size() > 0) {
				if (debug)
					System.out.println("isValid(): 0 < labs < minlabs");
				return false;
			}

			for (Lab lab1 : labs) {
				for (Lab lab2 : labs) {
					if (!lab1.equals(lab2)
							&& lab1.getTimeslot().conflicts(lab2.getTimeslot())) {
						if (debug)
							System.out.println("isValid(): conflicts with lab");
						return false;
					}
				}

				for (Lecture course : ta.getTaking()) {
					if (course.getTimeslot().conflicts(lab1.getTimeslot())) {
						if (debug)
							System.out
									.println("isValid(): conflicts with lecture");
						return false;
					}
				}

			}

		}

		for (Lab lab : constraints.getLabs()) {
			if (getAssignments(lab).size() > 1) {
				if (getAssignments(lab).size() == 0)
					if (debug)
						System.out.println("isValid(): no TA for Lab "
								+ lab.getCourse().getName() + " "
								+ lab.getName());
					else if (debug)
						System.out.println("isValid(): multiple TAs for Lab "
								+ lab.getCourse().getName() + " "
								+ lab.getName());
				return false;
			}
		}

		return true;
	}

	public int[] violation = new int[11];
	/**
	 *  Calculates the wert of a state
	 *  
	 * @return an integer that represents its wert
	 */
	public int calcWert() {
		Constraints cons = constraints;
		int wert = 0;
		for (int i = 0; i < 11; i++)
			violation[i] = 0;

		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		int min = Integer.MAX_VALUE;
		for (TA ta : cons.getTAs()) {
			int count = getAssignments(ta).size();
			min = Math.min(min, count);

			if (counts.get(count) == null)
				counts.put(count, 0);
			counts.put(count, counts.get(count) + 1);
		}

		int majority = 0;
		if (counts.get(majority) == null)
			counts.put(majority, 0);

		for (Integer numlabs : counts.keySet()) {
			int count = counts.get(numlabs);
			if (count > counts.get(majority))
				majority = numlabs;
		}

		for (TA ta : cons.getTAs()) {
			// Each TA should be funded (that is, they should teach at least one
			if (getAssignments(ta).size() < 1) {
				violation[0]++;
				wert -= 50;
			}

			boolean has1 = (ta.getPrefer1() == null || isAssignedToCourse(ta,
					ta.getPrefer1()));

			boolean has2 = (ta.getPrefer2() == null || isAssignedToCourse(ta,
					ta.getPrefer2()));

			boolean has3 = (ta.getPrefer3() == null || isAssignedToCourse(ta,
					ta.getPrefer3()));

			// TAs should get their first or second or third choice course
			if (!(has1 || has2 || has3)) {
				violation[3]++;
				wert -= 10;
			}
			// TAs should get their first or second choice course
			if (!(has1 || has2)) {
				violation[2]++;
				wert -= 10;
			}
			// TAs should get their first choice course
			if (!has1) {
				violation[1]++;
				wert -= 5;
			}

			ArrayList<String> courses = new ArrayList<String>();
			ArrayList<String> seniorCourses = new ArrayList<String>();
			for (Lab lab : getAssignments(ta)) {
				if (!courses.contains(lab.getCourse().getName())) {
					courses.add(lab.getCourse().getName());
					if (lab.getCourse().isSeniorCourse()) {
						seniorCourses.add(lab.getCourse().getName());
					}
				}

			}
			// TAs should have all their labs in the same course
			if (courses.size() > 1) {
				violation[4]++;
				wert -= 20;
			}
			// TAs should have all their labs in no more than 2 courses
			if (courses.size() > 2) {
				violation[5]++;
				wert -= 35;
			}
			// TAs should not teach a lab for a course for which they don't know
			// the subject matter
			for (String course : seniorCourses) {
				if (!ta.knows(course)) {
					violation[6]++;
					wert -= 30;
				}
			}
			// TAs should not teach two labs of distinct courses at the senior
			// level
			if (seniorCourses.size() > 1) {
				violation[7]++;
				wert -= (seniorCourses.size() - 1) * 10;
			}

			// TAs should not teach more than one more lab than the TA that
			// teaches the least number of labs.
			if (getAssignments(ta).size() > min + 1) {
				violation[8]++;
				wert -= 25;
			}
			// TAs should all teach the same number of labs.
			if (getAssignments(ta).size() != majority) {
				violation[9]++;
				wert -= 5;
			}
		}

		// If the instructor requested particular TAs for his/her course,
		// each of the lecture the instructor is teaching for that course
		// should be taught by one of the requested TAs
		for (Instructor instructor : cons.getInstructors()) {
			for (Lecture lecture : instructor.getLectures()) {
				boolean hasPreferred = false;
				Course course = lecture.getCourse();
				Collection<TA> tas = instructor.getPreferred(course);
				for (Lab lab : course.getLabs(lecture)) {

					for (TA ta : tas) {
						if (getAssignments(lab).contains(ta))
							hasPreferred = true;
					}
				}
				if (!hasPreferred) {
					violation[10]++;
					wert -= 10;
				}
			}
		}
		return wert;
	}

	// public TA getWorstTA(){
	// Integer min = Integer.MAX_VALUE;
	// TA worst = null;
	//
	// for(Pair<TA, Lab> assig : internalAllocation)
	// if(assig.getKey().goodness() < min)
	// worst = assig.getKey();
	//
	// return worst;
	// }
	/**
	 *  Method to mutate facts  
	 * @return a mutated fact
	 * @throws InterruptedException
	 */
	public Facts mutate() throws InterruptedException {
		mutateStopped = false;

		// Find two labs at the same that that have different TA's and try to
		// swap them

		Facts clone = clone();

		if (clone.internalAllocation.size() <= 1)
			return null;

		for (int i = 0; i < 10; i++) {

			Pair<TA, Lab> a1 = clone.internalAllocation.get(rnd
					.nextInt(clone.internalAllocation.size()));

			for (Pair<TA, Lab> a2 : clone.internalAllocation) {
				// System.out.println(a2.getKey());
				if (mutateStopped)
					throw new InterruptedException();

				if (a1.getKey().getName().equals(a2.getKey().getName()))
					continue;

				Lab lab1 = a1.getValue();
				Lab lab2 = a2.getValue();

				if (lab1.getTimeslot().conflicts(lab2.getTimeslot())) {
					a1.setValue(lab2);
					a2.setValue(lab1);
				}

				if (clone.isValid()) {
					return clone;
				} else {
					a1.setValue(lab1); // If not valid undo changes
					a2.setValue(lab2);
				}
			}
		}

		return null;

	}
	
	/**
	 *  Method to compare 2 facts against one another 
	 * @param ins1
	 * 			fact 1 to compare
	 * @param ins2
	 * 			fact 2 to compare
	 * @return the better fact
	 */
	public static Facts compete(Facts ins1, Facts ins2) {
		double r = rnd.nextDouble();
		double w1 = ins1.calcWert();
		double w2 = ins2.calcWert();

		// // w1 = -100, w2 = -900 ===>> -100/-1000 = 0.1 required for w1 to win
		// if (r > w1 / (w1+w2))
		// {
		// return ins1;
		// } else {
		// return ins2;
		// }

		if (r > 0.92)
			return ins2; // Act of god

		if (Math.abs(w1 - w2) < 25)
			return rnd.nextBoolean() ? ins2 : ins1; // Coin flip

		if (w2 < w1) // Survival of the fittest
			return ins1;
		return ins2;
	}
	/**
	 * Formats the internal input of a fact and puts it to a String for output 
	 * @return
	 */
	public String toPredicatesString() {
		String s = "";
		s += ("// TA Allocations (wert = " + calcWert() + ", size = "
				+ getAllocation().size() + ")");
		s += "\n";

		for (Pair<TA, Lab> a : getAllocation()) {
			TA ta = a.getKey();
			Lab lab = a.getValue();

			// TA-name, course-name, lab-name
			s += ("instructs(" + ta.getName() + "," + lab.getCourse().getName()
					+ "," + lab.getName() + ")");
			s += "\n";

			// debug info
			s += "// at " + lab.getTimeslot().getName()
					+ " and also instructing:\n";
			for (Lab l : getAssignments(ta)) {
				s += "//\t" + l.getCourse().getName() + " " + l.getName()
						+ " at " + l.getTimeslot().getName() + "\n";
			}
			s += "\n";
		}
		s += "\n";
		return s;
	}
	/**
	 *  Gets the amount of labs a TA is assigned too 
	 * @param ta
	 * 			TA to check the assignments of
	 * @return long value of the amount of labs a TA is assigned to. 
	 */
	public long getLabCount(TA ta) {
		int count = 0;
		for (Pair<TA, Lab> a : getAllocation()) {
			if (a.getKey().equals(ta))
				count++;
		}
		return count;
	}
	/**
	 * Helper function stops mutating facts
	 */
	public void stopMutate() {
		mutateStopped = true;
	}
}
