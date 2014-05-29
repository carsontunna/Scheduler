package taAllocation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class TAallocation {

	private static Solution program;

	/**
	 * TimeOut class implements TimerTask
	 * 
	 */
	static class TimeoutTask extends TimerTask {
		@Override
		/**
		 * Requests the program to stop
		 */
		public void run() {
			System.out.println("program stop timer");
			program.requestStop();
		}
	}

	/**
	 * The main program takes in args in the form of a filename and an integer of 
	 * @param args
	 */
	
	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();

		if (args.length != 2) {
			System.out
					.println("usage: java -classpath myprog.jar taAllocation.TAallocation <filename> <maxtime>");
			return;
		}

		String filename = args[0];
		long maxtime = Long.parseLong(args[1]);

		Environment env = new Environment(filename);
		int ret = env.fromFile(filename);
		if (ret < 0)
			return;

		program = new Solution(env);

		Timer timer = new Timer();
		// maxtime is and integer time in milliseconds specifying the time limit
		// for which the program is not allowed to exceed.
		timer.schedule(new TimeoutTask(), maxtime - 600);

		program.start();
		while (program.isAlive())
			Thread.yield();
		while (!program.isComplete())
			Thread.yield();

		String outstr = "";
		outstr += env.toPredicatesString();
		outstr += program.toPredicatesString();

		// killer threads dude
		timer.cancel();

		try {
			PrintWriter out = new PrintWriter(filename + ".out");
			out.print(program.toPredicatesString());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		long stoptime = System.currentTimeMillis();
		System.out
				.println("Soltuion runtime: " + (stoptime - starttime) + "ms");
		System.out.println("Solution goodness: " + program.getGoodness());
	}
}
