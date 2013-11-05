package taAllocation;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Entity {

	private List<Pair<TA,Course>> prefers = new ArrayList<Pair<TA,Course>>();
	private List<Lecture> lectures = new ArrayList<Lecture>();
	
	public Instructor(Entity e) {
		super(e);
	}
	
	public Instructor(String s)
	{
		super(s);
	}

	public void addPrefers(TA ta, Course course)
	{
		prefers.add(new Pair<TA, Course>(ta, course));
	}
	
	public boolean hasPrefers(TA ta, Course course)
	{
		return prefers.contains(new Pair<TA, Course>(ta, course));
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void addLecture(Lecture lecture) {
		if (!lectures.contains(lecture))
			lectures.add(lecture);
	}
}
