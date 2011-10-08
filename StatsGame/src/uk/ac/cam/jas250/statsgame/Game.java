package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Metric;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class Game {
	Set<Player> players;
	final int MINCOUNT = 3;
	int minGranularity;
	Set<Metric> metrics;
	
	//indicates whether the winning value is the highest or the lowest
	static final int HIGHWIN = 0;
	static final int LOWWIN = 1;
	
	public Game(Set<Metric> m){
		metrics = m;
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
	
<<<<<<< HEAD
	/*
	 * Player with turn passes chosen metric, other
	 * players pass null, return winning player
	 */
	public Player choose(Metric chosenMetric, int direction){
		boolean resultAvailable = false;
		Iterator<Player> it = players.iterator();
		Player winningPlayer = it.next(); //choose any player to initialise
		String region = NeighbourhoodStatQuery.getRegionFromPostcode(
				winningPlayer.postcode, chosenMetric.granularity);
		Stat s = NeighbourhoodStatQuery.getStat(chosenMetric, region);
		double currentBest = s.value;
		
		if (chosenMetric != null){ //if called by choosing player...
			while(it.hasNext()){
				Player testPlayer = it.next();
				region = NeighbourhoodStatQuery.getRegionFromPostcode(
						testPlayer.postcode, chosenMetric.granularity);
				s = NeighbourhoodStatQuery.getStat(chosenMetric, region);
				if(direction == HIGHWIN && s.value > currentBest){
					currentBest = s.value;
					winningPlayer = testPlayer;
				}
				else if(direction == LOWWIN && s.value < currentBest){
					currentBest = s.value;
					winningPlayer = testPlayer;
				}		
			}
			resultAvailable = true;
		}
		
		//block until result is available
		while(!resultAvailable){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//return result to all players
	public Card choose(){
		return new Card();//DEBUG STUB
	}
	
	public void prepareGame(){
		//TODO calc this
		minGranularity = Metric.LOCALAUTH;
	}

}
