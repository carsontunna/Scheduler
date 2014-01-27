package taAllocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Solution extends Thread {

	private static int POPSIZE = 50;

	protected LinkedList<Facts> state = new LinkedList<Facts>();
	protected Facts best = null;
	protected Environment env;

	private long creationtime;
	private long stoptime;
	/**
	 *  Constructor takes in an environment and set a creation time
	 * @param env
	 */
	public Solution(Environment env) {
		creationtime = System.currentTimeMillis();
		this.env = env;
	}

	/**
	 *  Returns a boolean value if the system is running or not
	 * @returns boolean  
	 */
	public boolean isComplete() {
		return !running;
	}

	/**
	 * Returns a positive boolean if there is more then one state (All states must be valid) 
	 *  
	 * @return boolean if state has more then one in it
	 */
	public boolean isSolved() {
		return state.size() > 0;
	}

	/**
	 *  Gets the wert of the current best Fact
	 * @returns an integer value of the best facts wert.
	 */
	public int getGoodness() {
		if (best != null) {
			return (int) best.calcWert();
		} else {
			return Integer.MIN_VALUE;
		}
	}

	private boolean stop = false;
	private boolean running = false;
	/**
	 *  Sets stop to false useful  in terminating program.
	 */
	public void requestStop() {
		stop = true;
	}

	/**
	 *  Method controller for the system in generating facts, mutating facts 
	 *  
	 */
	public void run() {
		Constraints cons = env.getConstraints();
		running = true;
		long loopcount = 0;

		runloop: while (!stop) {

			// trick to return in some reasonable amount of time (when nothing
			// interesting has happened for a while)
			if (best != null && best.calcWert() == 0)
				break runloop;
			// if (best != null)
			// {
			// long besttime = creationtime - best.getCreationTime();
			// long runtime = System.currentTimeMillis() - creationtime;
			// if (runtime > besttime * POPSIZE*POPSIZE && loopcount > POPSIZE )
			// break runloop;
			// }

			// generate POPSIZE/2 new instances of Facts
			GenerateThread gen[] = new GenerateThread[10];
			for (int i = 0; i < 10; i++) {
				gen[i] = new GenerateThread(cons);
				gen[i].start();
			}

			MutateThread mut[] = new MutateThread[state.size()
					+ (best != null ? 1 : 0)];
			// mutate all instances of Facts in state
			for (int i = 0; i < state.size(); i++) {
				mut[i] = new MutateThread(state.get(i));
				mut[i].start();
			}
			// also mutate "best" ;)
			if (best != null) {
				mut[mut.length - 1] = new MutateThread(best);
				mut[mut.length - 1].start();
			}

			// wait for mutate and generate threads to finish
			int alive;
			do {
				alive = 0;
				for (Thread t : gen)
					if (t.isAlive())
						alive++;
				for (Thread t : mut)
					if (t.isAlive())
						alive++;

				if (stop) {
					for (GenerateThread t : gen)
						t.requestStop();
					for (MutateThread t : mut)
						t.requestStop();
					break runloop;
				}
				Thread.yield();
				// System.out.println(alive + " threads alive");
			} while (alive > 0);

			// add new facts from generate and mutate to state
			for (GenerateThread t : gen) {
				Facts f = t.getNewFacts();
				if (f != null) {
					if (!f.isValid())
						System.out.println("generate() is wrong");
					state.add(f);
				}
			}
			for (MutateThread t : mut) {
				Facts f = t.getNewFacts();
				if (f != null) {
					if (!f.isValid())
						System.out.println("mutate() is wrong");
					state.add(f);
				}
			}

			// check for new best
			for (Facts f : state) {
				if (stop)
					break runloop;
				if (best == null || f.calcWert() > best.calcWert()) {
					// new best
					best = f;
				}
			}

			HashMap<Integer, LinkedList<Facts>> rank = new HashMap<Integer, LinkedList<Facts>>();
			for (Facts facts : state) {
				int wert = facts.calcWert();
				if (!rank.containsKey(wert))
					rank.put(wert, new LinkedList<Facts>());
				rank.get(wert).add(facts);
			}

			state.clear();

			for (LinkedList<Facts> level : rank.values()) {
				while (level.size() > 4) {
					level.addLast(Facts.compete(level.removeFirst(),
							level.removeFirst()));
				}
				state.addAll(level);
			}

			// equal chances to compete
			if (state.size() > POPSIZE)
				Collections.shuffle(state);

			// cull state back down to POPSIZE by competition of instances of
			// Facts
			// against one another
			while (state.size() > POPSIZE) {
				if (stop)
					break runloop;
				Facts f1 = state.removeLast();
				Facts f2 = state.removeLast();
				Facts won = Facts.compete(f1, f2);
				state.addFirst(won);
			}

			// if there are no solutions, exit
			if (state.size() == 0)
				break runloop;

			loopcount++;
			System.out.print("// generation " + loopcount);
			if (best != null)
				System.out.print(" (" + best.calcWert() + " )");
			System.out.println();
		}
		System.out.println("// runloop ran for " + loopcount + " generations");
		running = false;
		stoptime = System.currentTimeMillis();

		if (best != null) {
			best.calcWert();
			for (int i = 0; i < 11; i++) {
				// System.out.println("// violation #" + i + " = " +
				// best.violation[i]);
			}
		}
	}
	/**
	 *  Formats a string pleasantly designed to inform a user of the results of the solution controller. 
	 * 
	 * @return a formatted output string
	 */
	public String toPredicatesString() {
		if (!isComplete()) {
			throw new RuntimeException(
					"Solution is not complete, cannot form predicates!");
		} else if (!isSolved()) {
			return "// The problem was not solveable";
		} else {
			String s = "";
			s += "// Solution ran for " + (stoptime - creationtime)
					+ "ms and took " + (best.getCreationTime() - creationtime)
					+ "ms to find the best solution.";
			s += "\n";
			s += best.toPredicatesString();
			s += "\n";
			return s;
		}
	}

}
