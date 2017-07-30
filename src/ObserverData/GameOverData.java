package ObserverData;

import CompetitionPattern.GameInfo;
import Save.RecordGame;

public class GameOverData {
	
	public int winner ;
	public boolean again ;
	public RecordGame record;
	/* 比賽模式之比賽記錄 */
	public GameInfo gameInfo ;
	
	public GameOverData(int winner,boolean again,RecordGame record){
		this.winner = winner ;
		this.again = again;
		this.record = record;
	}
	
	public GameOverData(GameInfo gameInfo,RecordGame record){
		this.gameInfo = gameInfo;
		this.record = record;
	}
	
}
