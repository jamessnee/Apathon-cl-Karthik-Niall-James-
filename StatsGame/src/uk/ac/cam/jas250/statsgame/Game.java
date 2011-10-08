package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Metric;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.Set;



public class Game {
	Set<Player> players;
	final int MINCOUNT = 3;
	int minGranularity;
	
	public Game(){
		players = new HashSet<Player>();
	}
	
	public Set<Player> getPlayers(){
		return players;
	}
	
	public boolean ready(){
		return players.size() >= MINCOUNT;
	}
	
	public Card getCard(Player p){
		return new Card();//DEBUG STUB
	}
	
	public Card choose(){
		return new Card();//DEBUG STUB
	}
	
	public void prepareGame(){
		//TODO calc this
		minGranularity = Metric.LOCALAUTH;
	}

}
