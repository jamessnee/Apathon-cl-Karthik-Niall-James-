package uk.ac.cam.jas250.statsgame;

public class Test {
	public static void main(String [] args){
		Lobby l = new Lobby();
		l.join(new Player("Niall Murphy", "CB3 9EU"));
	}
	
//	class PlayerThread extends Thread{
//		private Player player;
//		
//		public PlayerThread(Player p){
//			player = p;
//		}
//		
//		public void run(){
//			player.getGame()
//		}
//	}
}
