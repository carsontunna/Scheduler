package taAllocation;

import java.util.ArrayList;
import java.util.List;

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

	public int getMaxLabs()
	{
		return 0;
	}

	public List<TA> getTAs()
	{
		return new ArrayList<TA>();
	}

	public int getMinLabs() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Lab> getLabs() {
		// TODO Auto-generated method stub
		return null;
	}
}
