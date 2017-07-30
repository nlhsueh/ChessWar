package Replay;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ChessGame.ChessBoard;
import Save.RecordGame;
import Share.AbstractPlayingPlayerView;
import ViewComponent.DrawBackground;

public class ReplayMainPanel{
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private JPanel framePanel ;
	private ChessBoard chessBoard;
	
	private ReplayButtonSet btnSet;
	
	// 1: 對羿模式, 開發模式 , 2: 比賽模式
	private int pattern = 1 ;
	
	public ReplayMainPanel(JPanel framePanel, RecordGame record){
		this.framePanel = framePanel;
		this.framePanel.add(this.layeredPane,"WatchGame");
		
		// add title
		JLabel title = new JLabel();
		title.setText("<html><font color='"+AbstractPlayingPlayerView.colorTitle+"'>"+"觀棋"+"</font></html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Microsoft JhenHei", Font.PLAIN, 40));
		title.setSize(924,50);
		title.setLocation(0,10);
		layeredPane.add(title, new Integer(0));
		// add background panel
		layeredPane.add(new DrawBackground("replay"), new Integer(0));
		// add chessBoard ;
		this.chessBoard = new ChessBoard();
		layeredPane.add(this.chessBoard, new Integer(1));
		// add background panel
		this.btnSet = new ReplayButtonSet(this.framePanel, this.pattern);
		btnSet.getMotionController().setChessBoard(chessBoard);
		btnSet.getMotionController().setRecordGame(record);
		layeredPane.add(btnSet, new Integer(1));
		// add gameInfo panel
		ReplayGameInfoView gameInfoView = new ReplayGameInfoView(record.getGameSetting(), record.getRound());
		layeredPane.add(gameInfoView, new Integer(1));
		btnSet.getMotionController().setRoundLb(gameInfoView.getRoundLb());
		btnSet.getMotionController().setMoveLb(gameInfoView.getMoveLb());
		
		btnSet.getMotionController().resetChess(); // 避免上一步bug
	}
	
	public void setPattern(int pattern){
		this.pattern = pattern;
		this.btnSet.setPattern(pattern);
	}
	
}
