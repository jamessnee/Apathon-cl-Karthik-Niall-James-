package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Stat;

import java.util.Set;


public class Card {
	Set<Stat> value;
	boolean turn;
	boolean gameOver;
	
	public Card(Set<Stat> v, boolean t, boolean go){
		value = v;
		turn = t;
		gameOver = go;
	}

}
