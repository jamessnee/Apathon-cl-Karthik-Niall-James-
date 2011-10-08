package uk.ac.cam.jas250.statsgame;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;



public class Service {
	
	Lobby l;
	HashMap<Key, Player> players;
	Set<Metric> metrics;
	
	public Service(){
		metrics = new HashMap<Metric>();
		l = new Lobby(metrics);
		players = new HashSet<Key, Player>();
	}
	
	public Lobby getLobby(){
		return l;
	}
	
	public String createPlayer(Player p){
		players.put(p.getKey(), p);
		return "";//TODO map key to string p.getKey();
	}
	
	public Player getPlayer(String keyString){
		//TODO map string to key
		return new Player("", "");
	}

}
