package Share;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ChessGame.ChessBoard;
import ChessGame.ChessSide;
import ChessGame.GameModel;
import DevelopPattern.DevelopGameSetting;
import NormalPattern.NormalGameSetting;
import FrameView.MainFrame;
import ObserverData.GameOverData;
import Save.RecordGame;

/**
 * Play Page的Modal class
 */

public class PlayingModel implements Serializable,Observer{
	
	private static final long serialVersionUID = 1L;
	private GameSetting gameSetting ;
	
	private AbstractPlayingView playingView ;
	
	private GameModel gameModel ;
	private RecordGame record;
	
	public PlayingModel(JPanel framePanel, GameSetting gameSetting){
		this.gameSetting = gameSetting ;
		
		if(gameSetting instanceof NormalGameSetting){
			this.playingView = new NormalPattern.PlayingView(this, framePanel);
		}else if(gameSetting instanceof DevelopGameSetting){
			this.playingView = new DevelopPattern.PlayingView(this, framePanel);
		}
		
		/* 遊戲參數初始化配置 */
		ChessBoard chessBoard = this.playingView.getChessBoard();
		this.gameModel = new GameModel(this.gameSetting, chessBoard.getAllChess(), chessBoard.getChessMap(),chessBoard.getChessPanel());
		TimeTask.setGameModel(this.gameModel);
		this.gameModel.addObserver(this);
	}
	
	public GameSetting getGameSetting(){
		return this.gameSetting ;
	}
	
	public GameModel getGameModel(){
		return this.gameModel;
	}
	
	public RecordGame getGameRecord(){
		return this.record;
	}
	
	
	/**
	 * 當點擊「遊戲開始」按鈕後，建立遊戲參數初始化
	 */
	public void clickGameStart(){
		/* 通知View更新Button狀態 */
		this.playingView.clickGameStart(this.gameModel);
		/* 檢查紅棋是否為AI，若是，則通知AI遊戲開始 */
		this.gameModel.gameStart();
		this.gameModel.notifyAIFirstMove();
	}
	
	/**
	 * 當點擊「暫停遊戲」按鈕後，使計時器暫停，並更改Button文字
	 */
	public void clickPauseButton(){

		ChessSide turn = this.gameModel.getTurn();
		if(this.gameModel.getPauseStatus() == false){
			if(turn == ChessSide.RED){
				this.playingView.getPlayerPanelP1().pauseTimer();
			}else{
				this.playingView.getPlayerPanelP2().pauseTimer();
			}
			this.gameModel.changePauseStatus();
			this.playingView.getButtonPause().setText("繼續遊戲");
		}else{
			if(turn == ChessSide.RED){
				this.playingView.getPlayerPanelP1().continueTimer();
			}else{
				this.playingView.getPlayerPanelP2().continueTimer();
			}
			this.gameModel.changePauseStatus();
			this.playingView.getButtonPause().setText("暫停遊戲");
		}
		
	}
	
	/**
	 * 當點擊「顛倒棋盤」按鈕後，將棋盤顛倒
	 */
	public void clickInverseButton(){
		this.gameModel.inverseChessBoard();
	}
	
	/**
	 * 當點擊「整理棋盤」按鈕後，通知View做修改
	 */
	public void clickResetChessBoardButton(){
		/* 詢問是否換邊 */
		Object[] options = {"是", "否"};
		int res = JOptionPane.showOptionDialog(null,
		"請問是否要換邊？（紅黑方互換）",
		"是否要換邊",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		MainFrame.icon_alert,     //do not use a custom Icon
		options,  //the titles of buttons
		options[0]); //default button title
		
		switch (res) {
	        case JOptionPane.YES_OPTION:
	        	this.playingView.getPlayerPanelP1().updatePlayerInfo();
	        	this.playingView.getPlayerPanelP2().updatePlayerInfo();
	        	this.gameModel.changeSide();
	        	break;
	        case JOptionPane.NO_OPTION:
	        	break;
		}
		/* 更換棋盤並設定給model */
		this.playingView.clickResetChessBoardButton();
		this.gameModel.setChess(this.playingView.getChessBoard().getAllChess());
		this.gameModel.setChessMap(this.playingView.getChessBoard().getChessMap());
		this.gameModel.setChessPanel(this.playingView.getChessBoard().getChessPanel());
		/* 重置AI */
		if(gameModel.getGameSetting() instanceof NormalGameSetting){
			this.gameModel.checkAI_Normal();
		}else if(gameModel.getGameSetting() instanceof DevelopGameSetting){
			this.gameModel.checkAI_Develop();
		}
		/* 重置顛倒狀態 */
		this.gameModel.resetInverse();
	}
	
	/**
	 * 當收到「Game Over」資訊後，從gameModal取得賽局記錄，並通知View做修改＆開放「觀看過程」功能
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof GameOverData){
			GameOverData data = (GameOverData) arg;
			this.record = data.record;
			this.playingView.gameOverHasNextGame();
		}
		
	}
	
}
