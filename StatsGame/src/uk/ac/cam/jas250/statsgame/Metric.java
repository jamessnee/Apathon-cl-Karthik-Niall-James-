package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Stat;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Metric {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private int granularity;
	
	@Persistent
	private String name;
	
	@Persistent
	private Set<Stat> stats;

	static final int LOCALAUTH = 5;
	static final int MLSOA = 4;
	static final int LLSOA = 3;
	static final int WARD = 2;
	static final int OUTPUTAREA = 1;
	
	
	/*
	 * CONSTRUCTORS
	 */
	public Metric(){
		stats = new HashSet<Stat>(); 
	}
	
	/*
	 * ACCESSORS
	 */
	public int getGranularity(){
		return granularity;
	}
	
	//Key
	public Key getKey(){
		return key;
	}
	
	public void setGranularity(int granularity){
		this.granularity = granularity;
	}
	
	//Name
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	//Stats
	public Set<Stat> getStats(){
		return stats;
	}
	public void setStats(Set<Stat> stats){
		this.stats = stats;
	}
	
	/*
	 * METHODS
	 */
	public void addStat(Stat st){
		stats.add(st);
	}
	
}
