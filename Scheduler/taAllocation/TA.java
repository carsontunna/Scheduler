package taAllocation;

import java.util.ArrayList;
import java.util.List;

public class TA extends Entity {

	private Course prefer1;
	private Course prefer2;
	private Course prefer3;
	private List<Course> knows = new ArrayList<Course>();
	private List<Lab> labs = new ArrayList<Lab>();
	
	public TA(Entity e) {
		super(e);
	}
	
	public TA(String s)
	{
		super(s);
	}

	public Course getPrefer1() {
		return prefer1;
	}

	public void setPrefer1(Course prefer1) {
		this.prefer1 = prefer1;
	}

	public Course getPrefer2() {
		return prefer2;
	}

	public void setPrefer2(Course prefer2) {
		this.prefer2 = prefer2;
	}

	public Course getPrefer3() {
		return prefer3;
	}

	public void setPrefer3(Course prefer3) {
		this.prefer3 = prefer3;
	}

	public void addKnows(Course course)
	{
		if (!knows.contains(course)) knows.add(course);
	}
	
	public boolean knows(Course course)
	{
		return knows.contains(course);
	}

	public List<Course> getKnows()
	{
		return knows;
	}
	
	public List<Lab> getLabs() {
		return labs;
	}

	public void addLabs(Lab lab) {
		if (!labs.contains(lab))
			labs.add(lab);
	}
}
