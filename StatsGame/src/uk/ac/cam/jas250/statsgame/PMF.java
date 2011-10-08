package uk.ac.cam.jas250.statsgame;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	
	private static final PersistenceManagerFactory pmfFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF(){};
	
	
	//Make sure only a single instance is returned.
	public static PersistenceManagerFactory get(){
		return pmfFactory;
	}
}
