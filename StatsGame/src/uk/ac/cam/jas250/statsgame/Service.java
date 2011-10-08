package uk.ac.cam.jas250.statsgame;
import javax.jdo.PersistenceManager;



public class Service {
	
	Lobby l;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	public Service(){
		l = new Lobby();
	}
	
	public Lobby getLobby(){
		return l;
	}
	
	public long createPlayer(Player p){
		try {
			pm.makePersistent(p);
		} finally {
			pm.close();
		}
		
		return p.getKey().getId();
	}
	
	public Player getPlayer(long id){
		return pm.getObjectById(Player.class, id);
	}
	
	public Metric getMetric(long id){
		return pm.getObjectById(Metric.class, id);
	}

}
