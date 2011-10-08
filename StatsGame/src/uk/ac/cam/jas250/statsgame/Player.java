package uk.ac.cam.jas250.statsgame;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Player {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private int score;
	
	@Persistent
	private String postcode;
	
	
	/*
	 * CONSTRUCTORS
	 */
	public Player(String name, String postcode){
		this.setName(name);
		this.setPostcode(postcode);
	}
	
	/*
	 * ACCESSORS
	 */
	public Key getKey(){
		return key;
	}
	
	//Name
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	//Score
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}
	
	//Postcode
	public String getPostcode(){
		return postcode;
	}
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return key.hashCode();
	}
	
	public void updateScore(int newScore){
		score = newScore;
	}

}
