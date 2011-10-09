package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Stat;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Card {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	Set<Stat> value;
	
	@Persistent
	boolean turn;
	
	@Persistent
	boolean gameOver;
	
	public Card(Set<Stat> v, boolean t, boolean go){
		value = v;
		turn = t;
		gameOver = go;
	}

}
