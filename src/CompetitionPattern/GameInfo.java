package CompetitionPattern;

import java.awt.Color;
import java.util.ArrayList;

import Save.RecordGame;
import ViewComponent.CButton;
import ViewComponent.Line;

public class GameInfo {
	
	/* 每場比賽有兩個選手 */
	private AIInfo player1 = null;
	private AIInfo player2 = null;
	/* 是否已經比完 */
	private boolean isGameOver = false ;
	/* 對比場次 */
	private GameInfo anotherGame = null;
	private GameInfo nextGame = null ;
	/* 勝利者 */
	private AIInfo winner = null;
	private int winnerInt = -1 ;
	private CButton winButton = null ;
	/* 比賽場次 */
	private int gameNo = -1;
	/* 勝利線 */
	private ArrayList<Line> winLinesP1 = new ArrayList<Line>();
	private ArrayList<Line> winLinesP2 = new ArrayList<Line>();
	/* 比賽記錄 */
	private RecordGame record = null;
	
	public GameInfo(AIInfo player1, AIInfo player2, int gameNo){
		/* 傳入兩位選手 */
		this.player1 = player1 ;
		this.player2 = player2;
		this.gameNo = gameNo;
	}
	
	public AIInfo getPlayer1(){
		return this.player1;
	}
	
	public void setPlayer1(AIInfo player1){
		this.player1 = player1;
	}
	
	public AIInfo getPlayer2(){
		return this.player2;
	}
	
	public void setPlayer2(AIInfo player2){
		this.player2 = player2;
	}
	
	public boolean isGameOver(){
		if(this.isGameOver) return true;
		else return false;
	}
	
	public AIInfo getWinner(){
		return this.winner;
	}
	
	public int getWinnerInt(){
		return this.winnerInt;
	}
	
	public int getGameNo(){
		return this.gameNo;
	}
	
	public void setGameOver(int winner){
		this.isGameOver = true;
		if(winner == 1){
			this.winner = player1;
			this.winnerInt = 1 ;
		}else if(winner == 2){
			this.winner = player2;
			this.winnerInt = 2 ;
		}
	}
	
	public void addWinLine(int player, int x1,int y1,int x2,int y2){
		if(player == 1){
			winLinesP1.add(new Line(x1,y1,x2,y2,Color.blue));
		}else if(player == 2){
			winLinesP2.add(new Line(x1,y1,x2,y2,Color.blue));
		}
	}
	
	public ArrayList<Line> getWinLineP1(){
		return this.winLinesP1;
	}
	
	public ArrayList<Line> getWinLineP2(){
		return this.winLinesP2;
	}
	
	public void setWinButton(CButton winButton){
		this.winButton = winButton;
	}
	
	public CButton getWinButton(){
		return this.winButton;
	}
	
	public void setAnotherGame(GameInfo anotherGame){
		this.anotherGame = anotherGame;
	}
	
	public GameInfo getAnotherGame(){
		return this.anotherGame;
	}
	
	public void setNextGame(GameInfo nextGame){
		this.nextGame = nextGame;
	}
	
	public GameInfo getNextGame(){
		return this.nextGame;
	}
	
	public void setRecordGame(RecordGame record){
		this.record = record;
	}
	
	public RecordGame getRecordGame(){
		return this.record;
	}
}
