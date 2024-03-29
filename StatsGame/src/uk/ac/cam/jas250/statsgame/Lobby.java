package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable
public class Lobby {
	public static final String SINGLETON_KEY = "OneAndOnlyLobby";

	@PrimaryKey
    @Persistent
    private Key key;
	
	@Persistent
	Set<Key> currentPlayers;
	
	@Persistent
	List<Game> gamesInProgress;
	
	@Persistent
	Set<Metric> metrics;
	
	public Lobby(){
		key = KeyFactory.createKey(Lobby.class.getSimpleName(), Lobby.SINGLETON_KEY);
		currentPlayers = new HashSet<Key>();
		gamesInProgress = new LinkedList<Game>();
		gamesInProgress.add(new Game());
	}
	
	public void join(Player p){
		currentPlayers.add(p.getKey());
		
	}
	
	public Game joinGame(Key playerKey){
		Game nextGame = gamesInProgress.get(0);
		nextGame.getPlayers().add(playerKey);
		if (nextGame.ready()){
			gamesInProgress.add(0,new Game());
		}
		return nextGame;
	}
		
	public Set<Key> getPlayers(){
		return currentPlayers;
	}

}
