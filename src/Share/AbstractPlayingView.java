package Share;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ChessGame.ChessBoard;
import ChessGame.GameModel;

public abstract class AbstractPlayingView {
	
	static public String colorTitle = "#3369e7";
	static public String colorNormal = "#9000f0";
	static public String colorBlack = "#222222" ;
	static public String colorRed = "#ff0000" ;
	static public String colorInfo = "#2baf2b" ;
	static public int fontSizeTitle = 18 ;
	static public int fontSizeContext = 16 ;
	
	protected PlayingModel model ;
	protected PlayingController controller;
	
	protected JLayeredPane layeredPane = new JLayeredPane();
	protected JPanel framePanel ;
	
	protected AbstractPlayingPlayerView panelP1 ;
	protected AbstractPlayingPlayerView panelP2 ;
	protected ChessBoard chessBoard ;
	
	protected JButton buttonStart ;
	protected JButton buttonWatchGame;
	protected JButton buttonPause ;
	protected JButton buttonInverse;
	protected JButton buttonExit ;
	
	/**
	 * 建立Title
	 * @return
	 */
	protected abstract void setTitle();
	
	/**
	 * 建立棋盤View
	 * @return
	 */
	protected void setChessBoard() {
		// Background Image's Panel
		this.chessBoard = new ChessBoard();
		layeredPane.add(this.chessBoard, new Integer(1));
	}
	
	/**
	 * 建立下方Button Set
	 * @return
	 */
	protected void setButtonSet() {

		// Button Set's Panel
		JPanel panelButtonSet = new JPanel();
		panelButtonSet.setLocation(167, 660);
		panelButtonSet.setSize(589, 60);
		panelButtonSet.setOpaque(false);
		panelButtonSet.setLayout(new GridLayout(1, 5, 20, 0));
				
		// Button Set
		this.buttonStart = new JButton("開始對弈");
		buttonStart.setFont(new Font(null, Font.PLAIN, 17));
		buttonStart.setActionCommand("Play");
		buttonStart.addActionListener(this.controller);
		
		this.buttonWatchGame = new JButton("觀看過程");
		buttonWatchGame.setFont(new Font(null, Font.PLAIN, 17));
		buttonWatchGame.setActionCommand("WatchGame");
		buttonWatchGame.addActionListener(this.controller);
		buttonWatchGame.setEnabled(false);
		
		this.buttonPause = new JButton("暫停遊戲");
		buttonPause.setFont(new Font(null, Font.PLAIN, 17));
		buttonPause.setActionCommand("Pause");
		buttonPause.addActionListener(this.controller);
		buttonPause.setEnabled(false);
		
		this.buttonInverse = new JButton("顛倒棋盤");
		buttonInverse.setFont(new Font(null, Font.PLAIN, 17));
		buttonInverse.setActionCommand("Inverse");
		buttonInverse.addActionListener(this.controller);

		this.buttonExit = new JButton("回主選單");
		buttonExit.setFont(new Font(null, Font.PLAIN, 17));
		buttonExit.setActionCommand("Exit");
		buttonExit.addActionListener(this.controller);
				
		// Add Button to Panel
		panelButtonSet.add(buttonStart);
		panelButtonSet.add(buttonWatchGame);
		panelButtonSet.add(buttonPause);
		panelButtonSet.add(buttonInverse);
		panelButtonSet.add(buttonExit);
				
		// Add Button's Panel to layeredPane(1)
		layeredPane.add(panelButtonSet, new Integer(1));
	}
	
	/**
	 * 當點擊「開始遊戲」按鈕後，更新Button狀態
	 * @param gameModal 接收GameModal使PlayerPanel之相關類別對其進行Observe
	 */
	public void clickGameStart(GameModel gameModal){
		this.buttonStart.setEnabled(false);
		this.buttonWatchGame.setEnabled(false);
		this.buttonPause.setEnabled(true);
		
		this.panelP1.setTimer();
		this.panelP1.addModelAsObserver(gameModal);
		this.panelP2.addModelAsObserver(gameModal);
	}
	
	/**
	 * 當點擊「整理棋盤」按鈕後，做出畫面更新
	 */
	public void clickResetChessBoardButton(){
		// 修改按鈕
		this.buttonStart.setText("開始對弈");
		this.buttonStart.setActionCommand("Play");
		// 棋盤重置
		this.layeredPane.remove(this.chessBoard);
		this.setChessBoard();
		// 計時器重置
		this.panelP1.iniTimer();
		this.panelP2.iniTimer();
		// 上一步重置
		this.panelP1.resetLastMove();
		this.panelP2.resetLastMove();
		// 紅方玩家互換
//		this.panelP1.updatePlayerInfo();
//		this.panelP2.updatePlayerInfo();
	}
	
	/**
	 * 遊戲結束後，有下一局，更新下方按鈕狀態
	 */
	public void gameOverHasNextGame(){
		this.buttonStart.setText("整理棋盤");
		this.buttonStart.setEnabled(true);
		this.buttonStart.setActionCommand("ResetChessBoard");
		this.buttonPause.setEnabled(false);
		this.buttonWatchGame.setEnabled(true);
	}
	
	public ChessBoard getChessBoard(){
		return this.chessBoard;
	}
	
	public JButton getButtonStart(){
		return this.buttonStart;
	}
	
	public JButton getButtonWatchGame(){
		return this.buttonWatchGame;
	}
	
	public JButton getButtonPause(){
		return this.buttonPause ;
	}

	public JButton getButtonExit(){
		return this.buttonExit ;
	}
	
	public AbstractPlayingPlayerView getPlayerPanelP1(){
		return this.panelP1 ;
	}
	
	public AbstractPlayingPlayerView getPlayerPanelP2(){
		return this.panelP2;
	}
	
	
}