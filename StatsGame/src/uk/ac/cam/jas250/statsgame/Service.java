package uk.ac.cam.jas250.statsgame;
import java.util.HashSet;
import java.util.Set;



public class Service {
	
	Lobby l;
	Set<Player> players;
	Set<Metric> metrics;
	
	public Service(){
		l = new Lobby();
		players = new HashSet<Player>();
		metrics = new HashSet<Metric>();
	}
	
	public Lobby getLobby(){
		return l;
	}
	
	public void createPlayer(Player p){
		players.add(p);
	}
	
	public void updatePlayer(Player p){
		players.remove(p);
		players.add(p);
	}

}
