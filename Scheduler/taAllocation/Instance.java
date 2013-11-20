package taAllocation;


import java.util.HashMap;
import java.util.List;

import taAllocation.Course;

public class Instance {
	
	private Environment environment;
	private List<Pair<TA,Lab>> allocation;
	
	public Instance(Environment environment)
	{
		this.environment = environment;
		
		//instruct predecate can be present in the input file
		List<TA> TAs = environment.getTAs();
		for(TA ta: TAs)
		{
			for(Lab lab: ta.getLabs())
			{
				allocation.add(new Pair<TA,Lab>(ta, lab));
			}
		}
	}
	
	public void assign(TA ta, Lab lab)
	{
		allocation.add(new Pair<TA,Lab>(ta, lab));
	}
	
	public List<Pair<TA,Lab>> getAllocation()
	{
		return allocation;
	}
	
	public boolean isValid()
	{
		//TODO: remove all duplicate entries in 'allocation'
		
		List<TA> TAs = environment.getTAs();
		int maxlabs = environment.getMaxLabs();
		int minlabs = environment.getMinLabs();
		
		for(TA ta: TAs)
		{
			//every TA is assigned at most MAX_LABS labs
			//FORALL ta:TA . lab-count(ta) <= MAX_LABS)
			if (ta.getLabs().size() > maxlabs) return false;
			
			//every TA is assigned at least MIN_LABS labs (if the TA *has* a lab assignment)
			//FORALL ta:TA . lab-count(ta)/=0 -> lab-count(ta) >= MIN_LABS)
			if (!ta.getLabs().isEmpty() && ta.getLabs().size() < minlabs) return false;
		}
		
		List<Lab> labs = environment.getLabs();
		HashMap<Lab,Integer> labcount = new HashMap<Lab,Integer>();
		HashMap<TA,Integer> tacount = new HashMap<TA,Integer>();
		
		for(Pair<TA,Lab> a: allocation)
		{
			TA ta = a.getKey();
			Lab lab = a.getValue();
			
			labcount.put(lab, labcount.get(lab) + 1);
			tacount.put(ta, tacount.get(ta) + 1);
		}

		for(Lab lab: labcount.keySet())
		{
			//every lab has a TA assigned to it
			//FORALL course:Course, lab:Lab | has-lab(course,?,lab). EXITS ta:TA . instructs(ta,course,lab)
			if (labcount.get(lab) <= 0) return false;
			
			//no lab has more than one TA assigned to it
			//FORALL course:Course, lab:Lab | has-lab(course,?,lab) . ~ EXISTS ta1,ta2:TA | ta1/= ta2 . instructs(ta1,lab) /\ instructs(ta2,lab)
			if (labcount.get(lab) > 1) return false;
		}
		
		for (TA ta: tacount.keySet())
		{		
			//no TA is assigned simultanious labs
			//FORALL ta:TA, c1,c2:Course, b1,b2:Lab | (c1=c2 => b1 /= b2) /\ instructs(ta,c1,b1) /\ instructs(ta,c2,b2) . ~ EXISTS t1,t2 | at(c1,b1,t1) /\ at(c2,b2,t2) . conflicts(t1,t2)
			if(tacount.get(ta) != 1) return false;
		}

		//no TA is assigned a lab that conflicts with his/her own courses
		//FORALL ta:TA, course:Course, lab:Lab | instructs(ta,course,lab) .
		//    ((~ EXISTS c:Course, lec:Lecture | taking(ta,c,lec) . EXISTS t1,t2 | at(course,lab,t1) /\ at(c,lec,t2)) . conflicts(t1,t2)) /\
		//    ((~ EXISTS c:Course, b:Lab | taking(ta,c,b) . EXISTS t1,t2 | at(course,lab,t1) /\ at(c,b,t2)) . conflicts(t1,t2)))
		HashMap<TA,List<Timeslot>> times = new  HashMap<TA,List<Timeslot>>();
		for (Pair<TA,Lab> a: allocation)
		{
			TA ta = a.getKey();
			Lab lab = a.getValue();
			//check for conflicts between labs
			for(Timeslot ts: times.get(ta))
			{
				if (ts.conflicts(lab.getTimeslot())) return false;
			}
			//check for conflicts between courses
			for(Lecture lec: ta.getTaking())
			{
				if (lec.getTimeslot().conflicts(lab.getTimeslot())) return false;
			}
			times.get(ta).add(lab.getTimeslot());
		}
		
		//no hard constraints were violated
		return true;
	}
	
	public int calcWert()
	{
		
		int wert = 0;
		int delta = 0;
		int former = -1;
		int least = -1;
		int current; 
		Lab temp = null; 
		Course hold = null; 

		// TAs should get their first choice
		// course
		for(TA ta: environment.getTAs()){
			// Each TA should be funded (that is,
			// they should teach at least one course)
			current = ta.amountTaking();
		
			if (former == -1)
				former = ta.amountTaking();
			if (least == -1)
				least = ta.amountTaking();
			if (current < least)
				least = current;
			// TA's should teach the same amount of Lab's
			if (current != former)
				wert -= 5;
			// No TA should teach more than 1 more lab then the TA who teaches the least amount of Labs 
			if ((current - least) >= 2)
				wert -= 25;	
				
			
			List<Lab> labs = ta.getLabs();
			List<Course> know = ta.getKnows();
			if(labs == null)
				wert -= 50;
			else
				temp = labs.get(0);

		
			delta = -25;
			String pref1 = ta.getPrefer1().getName();
			String pref2 = ta.getPrefer2().getName();
			String pref3 = ta.getPrefer3().getName();
			for(Lab lab : labs){
				// TAs should get their first choice
				// course
				if(lab.getCourse().getName().equals(pref1)){
					delta = 0;
					break;
				}
				
				// TAs should get their first or second
				// choice course
				if(lab.getCourse().getName().equals(pref2)){
					delta = -5;
					break;
				}
				
				// TAs should get their first or second or
				// third choice course
				if(lab.getCourse().getName().equals(pref3)){
					delta = -15;
					break;
					}
				}
			int counter = 0;
			int distinct = 0;
			for ( Lab lab : labs ){
				hold = lab.getCourse();
				
				//TA Should know what they are teaching
				if (!know.contains(lab.getCourse()))
					wert -= 30;
				// TA Should have all their labs in the same course
			
				if (temp.getCourse() != lab.getCourse()){
					wert -= 20;
					if (hold.isSeniorCourse())
						distinct ++;
					
					counter ++;
		
				}
				
				if (distinct > 1)
					wert -= 10; 
				// TA's should not have their labs in more then 2 different courses
				if (counter > 1)
					wert -= 35;
				temp = lab; 
			}
			
			// Remaining to do Instructor Preferred  
			wert += delta;
			former = current;	
		}
		
		return wert;
	}
}
