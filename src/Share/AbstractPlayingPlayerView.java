package Share;

import javax.swing.JPanel;

import ChessGame.ChessSide;
import ChessGame.GameModel;
import ChessGame.PlayerInfo;
import NormalPattern.NormalGameSetting;

public abstract class AbstractPlayingPlayerView extends JPanel{
	
	protected static final long serialVersionUID = 1L;
	protected GameSetting gameSetting ;
	
	static public String colorTitle = "#3369e7";
	static public String colorNormal = "#9000f0";
	static public String colorBlack = "#222222" ;
	static public String colorRed = "#ff0000" ;
	static public String colorInfo = "#2baf2b" ;
	static public int fontSizeTitle = 18 ;
	static public int fontSizeContext = 16 ;
	
	protected PlayerInfo playerInfo = new PlayerInfo();
	protected PlayingTimeoutView playerTimeoutView ;
	protected PlayingLastMoveView playerLastMoveView ;
	protected InterfacePlayeringPlayerInfoView playerInfoView;
	
	/**
	 * 使Player Panel部分觀察Model
	 */
	public void addModelAsObserver(GameModel model){
		model.addObserver(this.playerTimeoutView);
		model.addObserver(this.playerLastMoveView);
	}
	
	/**
	 * 當遊戲開始時 set PlayerTimeoutView的Timer
	 */
	public void setTimer(){
		this.playerTimeoutView.setTimer();
	}
	
	/**
	 * 當點擊「整理棋盤」時使PlayerTimeoutView的Timer pause
	 */
	public void iniTimer(){
		this.playerTimeoutView.iniTimer();
	}
	
	/**
	 * 當點擊「整理棋盤」時使PlayerLastMoveView的資訊重置
	 */
	public void resetLastMove(){
		this.playerLastMoveView.resetLastMove();
	}
	
	/**
	 * 當點擊「遊戲暫停」時使PlayerTimeoutView的Timer pause
	 */
	public void pauseTimer(){
		this.playerTimeoutView.pauseTimer();
	}
	
	/**
	 * 當點擊「繼續遊戲」時使PlayerTimeoutView的Timer continue
	 */
	public void continueTimer(){
		this.playerTimeoutView.continueTimer();
	}
	
	/**
	 * 賽局結束後，更新使用者資訊參數
	 */
	public void updatePlayerInfo(){
		this.gameSetting = this.gameSetting.getReverseSetting();
		/* 設定Player Info裡的本局顏色參數 */
		if(this.playerInfo.getPlayerColor() == ChessSide.RED){
			this.playerInfo.setPlayerNickname(this.gameSetting.nickname1);
			this.playerInfo.setPlayerType(this.gameSetting.player1); 
			this.playerInfo.setExternalName(this.gameSetting.externalAIName1);
		}else{
			this.playerInfo.setPlayerNickname(this.gameSetting.nickname2);
			this.playerInfo.setPlayerType(this.gameSetting.player2); 
			this.playerInfo.setExternalName(this.gameSetting.externalAIName2);
		}
		/* 更新Panel資訊 */
		this.playerInfoView.changePlayerUpdate();
	}
	
}
