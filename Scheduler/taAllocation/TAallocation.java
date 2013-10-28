package taAllocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TAallocation extends PredicateReader implements TAallocationPredicates{

	long max_labs = Long.MAX_VALUE;
	long min_labs = 0;
	
	HashMap<String,TA> tas = new HashMap<String,TA>();
	HashMap<String,Instructor> instructors = new HashMap<String,Instructor>();
	HashMap<String,Course> courses = new HashMap<String,Course>();
	
	public TAallocation(PredicateReader p) {
		super(p);
	}

	@Override
	public void a_maxlabs(Long p) {
		this.max_labs = p;		
	}

	@Override
	public void a_minlabs(Long p) {
		this.min_labs = p;
	}

	@Override
	public void a_TA(String p) {
		if (tas.containsKey(p))
		{
			//TODO: warning
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
		if (instructors.containsKey(p))
		{
			//TODO: warning			
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
		if (courses.containsKey(p))
		{
			//TODO: warning
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
		if (!e_course(p)) a_course(p);
		courses.get(p).setSeniorCourse(true);
	}

	@Override
	public boolean e_senior_course(String p) {
		//here
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
