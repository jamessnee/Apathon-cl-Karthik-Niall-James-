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
	
	public enum GAME_STATE { PRE_START, COLLECT_CARDS, CHOOSE, GET_RESULT };
	
	@Persistent
	public GAME_STATE currentState;
	
	@Persistent
	Set<Key> players;
	
	final int MINCOUNT = 3;
	
	@Persistent
	int maxGranularity;
	
	@Persistent
	Key choosingPlayer;
	
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
		players = new HashSet<Key>();
		choosingPlayer = null;
		turnInProgress = false;
		playersWaiting = 0;
		currentState = GAME_STATE.PRE_START;
	}
	
	public Set<Key> getPlayers(){
		return players;
	}
	
	public boolean ready(){
		return players.size() >= MINCOUNT;
	}
	
	public Card getCard(Key playerKey){
		
		//now the player list is finalised for this turn
		if(players.size() < 2){//not enough players, end game
			return new Card(null, false, true);
		}
		else{//deal cards	
			return new Card(getPlayerStats(playerKey, currentMetrics), 
					playerKey.equals(choosingPlayer), false);
		}
	}
	
	/*
	 * Player with turn passes chosen metric, other
	 * players pass null, return winning player
	 */
	public void choose(Metric chosenMetric, int direction){
		Iterator<Key> it = players.iterator();
		Player winningPlayer = PMF.get().getPersistenceManager().getObjectById(Player.class, it.next()); //choose any player to initialise
		String region = NeighbourhoodStatQuery.getAreasFromPostcode(
				winningPlayer.getPostcode())[chosenMetric.getGranularity()];
		Stat s = NeighbourhoodStatQuery.getStat(chosenMetric, region);
		double currentBest = s.getValue();
		
		if (chosenMetric != null){ //if called by choosing player...
			while(it.hasNext()){
				Player testPlayer = PMF.get().getPersistenceManager().getObjectById(Player.class, it.next());
				region = NeighbourhoodStatQuery.getAreasFromPostcode(
						testPlayer.getPostcode())[chosenMetric.getGranularity()];
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
			currentState = GAME_STATE.GET_RESULT;
		}
		
		//wait for all players and return
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
	
	private Set<Stat> getPlayerStats(Key p, Set<Metric> metrics){
		Set<Stat> s = new HashSet<Stat>();
		Iterator<Metric> it = metrics.iterator();
		while(it.hasNext()){
			s.add(getPlayerStat(p, it.next()));
		}
		return s;
	}
	
	private Stat getPlayerStat(Key p, Metric m){
		String region = NeighbourhoodStatQuery.getAreasFromPostcode(
				PMF.get().getPersistenceManager().getObjectById(Player.class, p).getPostcode())[m.getGranularity()];
		return NeighbourhoodStatQuery.getStat(m, region);
	}
	
	public GAME_STATE getState() {
		if (currentState == GAME_STATE.PRE_START) {
			if (ready()) dealCards();
		}
		if (currentState == GAME_STATE.COLLECT_CARDS) {
			Iterator<Key> i = players.iterator();
			for (;i.hasNext();) {
				Key next = i.next();
				Player p = PMF.get().getPersistenceManager().getObjectById(Player.class, next);
				if (!p.getCollected())
					break;
				
				
			}
			if (!i.hasNext())
				currentState = GAME_STATE.CHOOSE;
		}
		
		
		
		return currentState;
	}
	
	private void dealCards() {
		Iterator<Key> i = players.iterator();
		Key first = i.next();
		Key next = first;
		boolean found = false;
		for (;i.hasNext();) {
			if (found) {
				choosingPlayer = next;
				found = false;
				break;
			}
			if (next.equals(choosingPlayer))
				found = true;
			next = i.next();
		}

		if (found || choosingPlayer == null)
			choosingPlayer = first;
		
		i = players.iterator();
		for (;i.hasNext();) {
			next = i.next();
			Player p = PMF.get().getPersistenceManager().getObjectById(Player.class, next);
			p.setCollected(false);
		}
			
		currentState = GAME_STATE.COLLECT_CARDS;
		
	}
	

}
