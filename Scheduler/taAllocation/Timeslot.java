package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Timeslot extends Entity {

	protected HashMap<String,Timeslot> conflicts = new HashMap<String,Timeslot>();
	protected List<Entity> entities = new ArrayList<Entity>();
	
	public boolean conflicts(Timeslot t)
	{
		return conflicts.containsKey(t.getName());
	}
	
	public void addConflict(Timeslot t)
	{
		conflicts.put(t.getName(), t);
	}
	
	public Timeslot(Entity e) {
		super(e);
	}

	public Timeslot(String s)	{
		super(s);
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	public Collection<Entity> getEntities()
	{
		return entities;
	}
}
