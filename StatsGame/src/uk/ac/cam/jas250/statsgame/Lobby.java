package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
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
	Set<Player> currentPlayers;
	
	@Persistent
	Game nextGame;
	
	@Persistent
	Set<Game> gamesInProgress;
	
	@Persistent
	Set<Metric> metrics;
	
	public Lobby(){
		key = KeyFactory.createKey(Lobby.class.getSimpleName(), Lobby.SINGLETON_KEY);
		currentPlayers = new HashSet<Player>();
		nextGame = new Game();
	}
	
	public void join(Player p){
		currentPlayers.add(p);
		
	}
	
	public Game getGame(Player p){
		nextGame.getPlayers().add(p);
		while(!nextGame.ready()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Game temp = nextGame;
		nextGame = new Game();
		return temp;
	}
	
	public Set<Player> getPlayers(){
		return currentPlayers;
	}

}
