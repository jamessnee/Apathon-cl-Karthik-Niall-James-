package uk.ac.cam.jas250.statsgame;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	
	private static final PersistenceManagerFactory pmfFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF(){};
	
	public static PersistenceManagerFactory get(){
		return pmfFactory;
	}
}
