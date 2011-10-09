package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Metric;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Game {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	Set<Player> players;
	
	final int MINCOUNT = 3;
	
	@Persistent
	int maxGranularity;
	
	@Persistent
	int playerTurnIndex;
	
	@Persistent
	boolean turnInProgress;
	
	@Persistent
	int playersWaiting;
	
	@Persistent
	Set<Metric> currentMetrics;
	
	//indicates whether the winning value is the highest or the lowest
	static final int HIGHWIN = 1;
	static final int LOWWIN = -1;
	
	public Game(){
		players = new HashSet<Player>();
		playerTurnIndex = 0;
		turnInProgress = false;
		playersWaiting = 0;
	}
	
	public Set<Player> getPlayers(){
		return players;
	}
	
	public boolean ready(){
		return players.size() >= MINCOUNT;
	}
	
	public Card getCard(Player p){
		//wait for all players to call for next card (in case players have left)
		Player nextPlayer = null;
		joinPlayers();
		
		//now the player list is finalised for this turn
		if(players.size() < 2){//not enough players, end game
			return new Card(null, false, true);
		}
		else{//deal cards	
			if(!turnInProgress){//A turn is in progress when the first player calls
				turnInProgress = true;
				
				playerTurnIndex = (playerTurnIndex + 1) % players.size();
				
				currentMetrics = chooseMetrics();
			}
			//wait for all players before returning
			joinPlayers();	
			return new Card(getPlayerStats(p, currentMetrics), 
					p.equals(nextPlayer), false);
		}
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
		
		//wait for all players and return
		joinPlayers();
		return winningPlayer;
	}
	
	private void joinPlayers(){//wait for all players before returning
		playersWaiting++;
		while(playersWaiting < players.size()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		playersWaiting = 0;
	}
	
	private void calcMaxGranularity(){
		//TODO calc this
		maxGranularity = Metric.LOCALAUTH;
	}
	
	private Set<Metric> chooseMetrics(){
		calcMaxGranularity();
		//TODO choose metrics
		return new HashSet<Metric>();
	}
	
	private Set<Stat> getPlayerStats(Player p, Set<Metric> metrics){
		Set<Stat> s = new HashSet<Stat>();
		Iterator<Metric> it = metrics.iterator();
		while(it.hasNext()){
			s.add(getPlayerStat(p, it.next()));
		}
		return s;
	}
	
	private Stat getPlayerStat(Player p, Metric m){
		String region = NeighbourhoodStatQuery.getRegionFromPostcode(
				p.getPostcode(), m.getGranularity());
		return NeighbourhoodStatQuery.getStat(m, region);
	}

}
