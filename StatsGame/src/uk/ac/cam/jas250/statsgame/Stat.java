package uk.ac.cam.jas250.statsgame;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Stat {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String region;
	
	@Persistent
	private double value;
	
	/*
	 * CONSTRUCTORS
	 */
	public Stat(String region, double value){
		this.setRegion(region);
		this.setValue(value);
	}
	
	/*
	 * ACCESSORS
	 */
	
	//Key
	public Key getKey(){
		return key;
	}
	
	//Region
	public String getRegion(){
		return region;
	}
	public void setRegion(String region){
		this.region = region;
	}
	
	//Value
	public double getValue(){
		return value;
	}
	public void setValue(double value){
		this.value = value;
	}

}
