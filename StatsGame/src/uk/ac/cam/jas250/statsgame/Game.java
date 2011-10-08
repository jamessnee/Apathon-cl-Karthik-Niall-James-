package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Metric;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class Game {
	Set<Player> players;
	final int MINCOUNT = 3;
	int maxGranularity;
	Set<Metric> metrics;
	Set<Player> playerTurnList;
	boolean turnInProgress;
	int playersWaitingForResult;
	
	//indicates whether the winning value is the highest or the lowest
	static final int HIGHWIN = 0;
	static final int LOWWIN = 1;
	
	public Game(Set<Metric> m){
		metrics = m;
		players = new HashSet<Player>();
		playerTurnList = new HashSet<Player>();
		turnInProgress = false;
	}
	
	public Set<Player> getPlayers(){
		return players;
	}
	
	public boolean ready(){
		return players.size() >= MINCOUNT;
	}
	
	public Card getCard(Player p){
		if(!turnInProgress){//A turn is in progress when the first player calls
			turnInProgress = true;
			playersWaitingForResult = 0;
			calcMaxGranularity();
		}
		Player nextPlayer = null;
		Iterator<Player> it = playerTurnList.iterator();
		while(nextPlayer == null){
			while(it.hasNext() && nextPlayer == null){
				Player temp = it.next();
				if(players.contains(temp)) //if temp is still playing...
					nextPlayer = temp;
			}
			if(nextPlayer == null){
				playerTurnList = new HashSet<Player>(players);
			}
		}
		
		return new Card();
	}
	
	/*
	 * Player with turn passes chosen metric, other
	 * players pass null, return winning player
	 */
	public Player choose(Metric chosenMetric, int direction){
		Iterator<Player> it = players.iterator();
		Player winningPlayer = it.next(); //choose any player to initialise
		String region = NeighbourhoodStatQuery.getRegionFromPostcode(
				winningPlayer.getPostcode(), chosenMetric.getGranularity());
		Stat s = NeighbourhoodStatQuery.getStat(chosenMetric, region);
		double currentBest = s.getValue();
		
		if (chosenMetric != null){ //if called by choosing player...
			while(it.hasNext()){
				Player testPlayer = it.next();
				region = NeighbourhoodStatQuery.getRegionFromPostcode(
						testPlayer.getPostcode(), chosenMetric.getGranularity());
				s = NeighbourhoodStatQuery.getStat(chosenMetric, region);
				if(direction == HIGHWIN && s.getValue() > currentBest){
					currentBest = s.getValue();
					winningPlayer = testPlayer;
				}
				else if(direction == LOWWIN && s.getValue() < currentBest){
					currentBest = s.getValue();
					winningPlayer = testPlayer;
				}		
			}
		}
		
		//block until all players have reached this point
		playersWaitingForResult++;
		while(playersWaitingForResult < players.size()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//return result to all players
		return winningPlayer;
	}

	public Card choose(){
		return new Card();//DEBUG STUB
	}
	
	public void calcMaxGranularity(){
		//TODO calc this
		maxGranularity = Metric.LOCALAUTH;
	}

}
