package taAllocation;

public class Course extends Entity {

	private boolean seniorCourse;
	private boolean gradCourse;
	
	public Course(Entity e) {
		super(e);
	}
	
	public Course(String s) {
		super(s);
	}

	public boolean isSeniorCourse() {
		return seniorCourse;
	}

	public void setSeniorCourse(boolean seniorCourse) {
		this.seniorCourse = seniorCourse;
	}

	public boolean isGradCourse() {
		return gradCourse;
	}

	public void setGradCourse(boolean gradCourse) {
		this.gradCourse = gradCourse;
	}


}
