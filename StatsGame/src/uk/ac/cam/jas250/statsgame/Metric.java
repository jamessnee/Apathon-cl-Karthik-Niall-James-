package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Stat;

import java.util.HashSet;
import java.util.Set;


public class Metric {
	int granularity;
	String name;
	Set<Stat> stats;

	static final int LOCALAUTH = 1;
	static final int MLSOA = 2;
	static final int LLSOA = 3;
	static final int WARD = 4;
	static final int OUTPUTAREA = 5;
	
	public Metric(){
		stats = new HashSet<Stat>(); 
	}
	
	public int getGranularity(){
		return granularity;
	}

}
