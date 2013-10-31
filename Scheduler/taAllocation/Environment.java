package taAllocation;

public class Environment {

	protected static PredicateReader p;

	public static PredicateReader get() {
		return new PredicateReader(p);

	}

	public Solution currentSolution;

	public void fromFile(String datafile) {
		// TODO Auto-generated method stub

	}

	public static void reset() {
		p = null;

	}

}
