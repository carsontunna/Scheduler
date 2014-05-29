package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Timeslot extends Entity {

	protected HashMap<String, Timeslot> conflicts = new HashMap<String, Timeslot>();

	/**
	 * 
	 * @param t timeslot is taken in and 
	 * @return a boolean is returned if it conflicts
	 */
	public boolean conflicts(Timeslot t) {
		// System.out.println(t.getName());
		// if (conflicts.containsKey(t.getName()))
		// {
		// System.out.println(t.getName() + " conflicts with " +
		// this.getName());
		// return false;
		// }
		// return true;
		return t.getName().equals(getName())
				|| conflicts.containsKey(t.getName());
	}

	/**
	 *  
	 * @param t takes in a timeslot and adds it to the internal list of another timeslot object
	 */
	public void addConflict(Timeslot t) {
		conflicts.put(t.getName(), t);
	}

	/**
	 * 
	 * @returns a collection of the internal Hash map of collections
	 */
	public Collection<Timeslot> getConflicts() {
		return conflicts.values();
	}

	/**
	 * 
	 * @param s 
	 * Constructor
	 */
	public Timeslot(String s) {
		super(s);
	}
}
