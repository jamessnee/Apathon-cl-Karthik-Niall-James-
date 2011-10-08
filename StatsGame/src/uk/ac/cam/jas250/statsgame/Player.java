package uk.ac.cam.jas250.statsgame;

public class Player {
	Integer id;
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id.hashCode();
	}
	
	String name;
	int score;
	String postcode;

}
