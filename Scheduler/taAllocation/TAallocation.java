package taAllocation;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class TAallocation extends PredicateReader implements
		TAallocationPredicates {

	static final int DEFAULT_MAX_TIME = 30000;

	static PrintStream traceFile;

	private static long start_time, end_time;

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
		commandMode(env);

		if (traceFile != null) {
			traceFile.println(new java.util.Date());
			traceFile.close();
		}
	}

	/**
	 * Implement "command mode": repeatedly read lines of predicates from
	 * {@link System#in} and either assert them (if the line starts with a "!")
	 * or evaluate them (and return "true" or "false" to {@link System#out}.
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
		// TODO Auto-generated method stub

	}

	@Override
	public void a_minlabs(Long p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void a_TA(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_TA(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_instructor(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_instructor(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_course(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_course(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_senior_course(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_senior_course(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_grad_course(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_grad_course(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_timeslot(String p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_timeslot(String p) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_lab(String c, String lec, String lab) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_instructs(String p, String c, String l) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_instructs(String p, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_at(String c, String l, String t) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_prefers(String instructor, String ta, String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_prefers(String instructor, String ta, String c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_prefers1(String ta, String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_prefers1(String ta, String c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_prefers2(String ta, String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_prefers2(String ta, String c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_prefers3(String ta, String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_prefers3(String ta, String c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_taking(String ta, String c, String l) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_taking(String ta, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_conflicts(String t1, String t2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_conflicts(String t1, String t2) {
		// TODO Auto-generated method stub
		return false;
	}

}
