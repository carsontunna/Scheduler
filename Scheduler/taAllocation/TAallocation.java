package taAllocation;

public class TAallocation extends PredicateReader implements TAallocationPredicates{

	private static long start_time, end_time;
	
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Usage: TAallocation <input file> <milliseconds>");
			return;
		}
		
		start_time = System.currentTimeMillis();
		
	}
	
	public TAallocation(PredicateReader p) {
		super(p);
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
