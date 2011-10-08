package uk.ac.cam.jas250.statsgame;
import uk.ac.cam.jas250.statsgame.Player;

import java.util.HashSet;
import java.util.Set;



public class Lobby {
	
	Set<Player> currentPlayers;
	Game nextGame;
	Set<Metric> metrics;
	
	public Lobby(Set<Metric> m){
		metrics = m;
		currentPlayers = new HashSet<Player>();
		nextGame = new Game(m);
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
		nextGame = new Game(metrics);
		return temp;
	}
	
	public Set<Player> getPlayers(){
		return currentPlayers;
	}

}
