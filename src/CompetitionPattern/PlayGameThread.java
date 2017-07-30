package CompetitionPattern;

public class PlayGameThread extends Thread{
	
	private GameModel game ;
	
	public PlayGameThread(GameModel game){
		this.game = game;
	}
	
	public void run(){
		game.gameStart();
	}

}
