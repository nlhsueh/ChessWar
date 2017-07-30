package Save;

import java.awt.Point;
import java.util.ArrayList;

import ChessGame.Chess;
import ChessGame.ChessSide;
import ObserverData.MoveRecordData;
import Share.GameSetting;

public class RecordGame {
	
	private GameSetting gameSetting ; // 這場遊戲的GameSetting
	private ArrayList <MoveRecordData> data = new ArrayList<MoveRecordData>(); // 這場遊戲的過程
	private int round = 1 ; // 第幾手
	
	public RecordGame(GameSetting gameSetting){
		this.gameSetting = gameSetting ;
	}
	
	/* move & break */
	public void addData(Chess c, Point p, String status){
		if(status.equals("move")){
			this.data.add(new MoveRecordData(this.round, c.getChessIndex(), c.getChessName(),c.getChessLoc(), p, c.getChessSide() ,"move"));
			this.round++;
		}else if(status.equals("break")){
			this.data.add(new MoveRecordData(this.round, c.getChessIndex(), c.getChessName(),c.getChessLoc(), p, c.getChessSide() ,"break"));
			this.round++;
		}else if(status.equals("timeout")){
			this.data.add(new MoveRecordData(this.round, c.getChessIndex(), c.getChessName(),c.getChessLoc(), p, c.getChessSide() ,"timeout"));
			this.round++;
		}else{
			System.out.println("Error RecordGame Status.");
		}
	}
	
	/* eat */
	public void addData(Chess c, Point p, String status, int oldChessIndex){
		if(status.equals("eat")){
			this.data.add(new MoveRecordData(this.round, c.getChessIndex(), c.getChessName(),c.getChessLoc(), p, c.getChessSide() ,"eat", oldChessIndex));
			this.round++;	
		}else{
			System.out.println("Error RecordGame Status.");
		}
	}
	
	/* timeout */
	public void addData(ChessSide side, String status){
		if(status.equals("timeout")){
			this.data.add(new MoveRecordData(this.round, side ,new String(status)));
			this.round++;	
		}else{
			System.out.println("Error RecordGame Status.");
		}
	}
	
	public MoveRecordData getLastestData(){
		return this.data.get(round-2);
	}
	
	public GameSetting getGameSetting(){
		return this.gameSetting;
	}
	
	public MoveRecordData getMoveRecordData(int round){
		return this.data.get(round-1);
	}
	
	public int getRound(){
		return this.round;
	}
	
}