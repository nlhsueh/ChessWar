package Replay;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ChessGame.Chess;
import ChessGame.ChessBoard;
import ChessGame.ChessMap;
import FrameView.MainFrame;
import NormalPattern.PlayingPlayerView;
import ObserverData.MoveRecordData;
import ObserverData.MyMove;
import Save.RecordGame;

// 顯示每一首移動、顯示第幾手、顯示黑棋紅棋是誰
// 移動軌跡優化
// 客製化調整速度

public class MotionController extends Observable{
	
	private ChessBoard chessBoard;
	private RecordGame record ;
	private ChessMap chessMap;
	private int round = 1 ;
	private int totalRound ;
	
	private JButton nextBtn;
	private JButton previousBtn;
	private JButton autoBtn;
	
	private JLabel roundLb;
	private JLabel moveLb;
	
	private Timer autoPlayTimer;
	
	private Chess previousMoveBorder ;
	private Chess previousMoveChess ;
	
	private enum ChessColorPosition {
		RedDownBlackUp,RedUpBlackDown
	} 
	private ChessColorPosition colorPosition = ChessColorPosition.RedDownBlackUp;
	
	public MotionController(){
		
	}
	
	public void setChessBoard(ChessBoard chessBoard){
		this.chessBoard = chessBoard;
		this.chessMap = chessBoard.getChessMap();
		
		Chess[] chess = this.chessBoard.getAllChess();
		for(int i=0;i<32;i++){
			this.addObserver(chess[i]);
		}
	}
	
	public void setRecordGame(RecordGame record){
		this.record = record;
		this.totalRound = record.getRound();
	}
	
	public void setNextBtn(JButton nextBtn){
		this.nextBtn = nextBtn;
	}
	
	public void setPreviousBtn(JButton previousBtn){
		this.previousBtn = previousBtn;
	}
	
	public void setAutoBtn(JButton autoBtn){
		this.autoBtn = autoBtn;
	}
	
	public void setRoundLb(JLabel roundLb){
		this.roundLb = roundLb;
	}
	
	public void setMoveLb(JLabel moveLb){
		this.moveLb = moveLb ;
	}
	
	public void resetChess(){
		this.chessBoard.resetChess();
		this.round = 1 ;
		this.nextBtn.setEnabled(true);
		this.previousBtn.setEnabled(false);
		this.colorPosition = MotionController.ChessColorPosition.RedDownBlackUp;
		this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"0"+"</font> 手</html>");
		this.moveLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>");
	}
	
	public void next(){

		MoveRecordData data = this.record.getMoveRecordData(this.round);
		
		/* 檢查時否違規 */
		if(data.isBreak()){
			JOptionPane.showMessageDialog(new JPanel(), data.getBreakWord()
					, "犯規", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
			/* 觀戰時左方看板資訊 */
			this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+this.round+"</font> 手</html>");
			this.moveLb.setText("<html>"+data.getBreakWord()+"</html>");
			this.round ++ ;
			this.nextBtn.setEnabled(false);
			return;
		}
		
		/* 檢查是否timeout */
		if(data.isTimeout()){
			JOptionPane.showMessageDialog(new JPanel(), data.getTimeoutWord()
					, "超過思考時間", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
			/* 觀戰時左方看板資訊 */
			this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+this.round+"</font> 手</html>");
			this.moveLb.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+"超過思考時間"+"</font></html>");
			this.round ++ ;
			this.nextBtn.setEnabled(false);
			return;
		}
		
		/* 取得移動資訊 */
		Chess c = this.chessBoard.getChess(data.getIndex()-1);
		JLayeredPane cp = this.chessBoard.getChessPanel();
		Point newPos = new Point(data.getNewPos());
		
		/* 移動軌跡：第一手(沒有上一手了)以外，需移除原本Border */
		if(this.round != 1){
			cp.remove(this.previousMoveBorder);
			cp.repaint();
			this.previousMoveChess.setBorder(null);
		}
		
		/* 移動軌跡：原本位置增加Border */
		Point oldPos = new Point(data.getOldPos());
		this.previousMoveBorder = new Chess(oldPos);
		changeChessViewPosition(previousMoveBorder,oldPos);
		cp.add(previousMoveBorder,  new Integer(401));
		
		/* 移動軌跡：移動之後的位置增加Border */
		this.previousMoveChess = c ;
		c.setBorder(new LineBorder(Color.BLUE,2));
		
		/* 吃棋處理 */
		if(data.isEat()){
			Chess oldC = this.chessBoard.getChess(data.getBeEatedChessIndex()-1);
			oldC.setLocation(-1, -1);
			oldC.setVisible(false);
			this.setChanged();
			this.notifyObservers(new MyMove(oldC,new Point(-1, -1)));
		}
		
		/* 更改View顯示 */
		changeChessViewPosition(c,newPos);
		this.setChanged();
		this.notifyObservers(new MyMove(c,new Point(newPos)));
		
		/* 觀戰時左方看板資訊 */
		this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+this.round+"</font> 手</html>");
		this.moveLb.setText("<html>"+data.getMoveWord()+"</html>");
		
		this.round++;
		
		if(this.round == this.totalRound){
			// 賽局結束，禁止再按next
			this.nextBtn.setEnabled(false);
		}else{
			this.previousBtn.setEnabled(true);
		}
	}
	
	public void previous(){
		/* Button Set 第一手時禁止按 */
		MoveRecordData data = this.record.getMoveRecordData(this.round-1);
		Chess c = this.chessBoard.getChess(data.getIndex()-1);
		Point oldPos = new Point(data.getOldPos());
		Point newPos = new Point(data.getNewPos());
		
		JLayeredPane cp = this.chessBoard.getChessPanel();
		
		/* 移動軌跡：移除原本Border */
		cp.remove(this.previousMoveBorder);
		this.previousMoveChess.setBorder(null);
		cp.repaint();
		
		if(this.round != 2){
			MoveRecordData dataPre = this.record.getMoveRecordData(this.round-2);
			/* 原本位置增加Border */
			this.previousMoveBorder = new Chess(dataPre.getOldPos());
			changeChessViewPosition(previousMoveBorder,dataPre.getOldPos());
			cp.add(previousMoveBorder,  new Integer(401));
			/* 移動之後的位置增加Border */
			this.previousMoveChess = this.chessBoard.getChess(dataPre.getIndex()-1) ;
			this.previousMoveChess.setBorder(new LineBorder(Color.BLUE,2));
		}else{
			this.previousMoveChess = null;
			this.previousMoveBorder = null;
		}
		
		/* 吃棋處理 */
		if(data.isEat()){
			Chess BeEatedC = this.chessBoard.getChess(data.getBeEatedChessIndex()-1);
			changeChessViewPosition(BeEatedC,newPos);
			BeEatedC.setVisible(true);
			this.setChanged();
			this.notifyObservers(new MyMove(BeEatedC,new Point(newPos.x, newPos.y)));
			BeEatedC.repaint();
		}
		
		/* 修改View位置 */
		changeChessViewPosition(c,oldPos);
		this.setChanged();
		this.notifyObservers(new MyMove(c,new Point(oldPos)));
		c.repaint();
		
		/* 修改觀戰標籤 */
		this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+(this.round-2)+"</font> 手</html>");
		if(this.round != 2){
			this.moveLb.setText("<html>"+this.record.getMoveRecordData(this.round-2).getMoveWord()+"</html>");
		}
		this.round--;
		
		if(this.round == 1){
			// 賽局剛開始，禁止再按previous
			this.previousBtn.setEnabled(false);
			// 回到第一手，左方看板初始狀態
			this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"0"+"</font> 手</html>");
			this.moveLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>");
		}else{
			this.nextBtn.setEnabled(true);
		}
	}
	
	private class AutoPlayTask extends TimerTask{
		
		MotionController ctr;
		
		public void setCtr(MotionController ctr){
			this.ctr = ctr;
		}
	
		@Override
		public void run() {
			if(ctr.round == ctr.totalRound){
				// 賽局結束，禁止再next
				ctr.autoPlayTimer.cancel();
				// 重置btn
				ctr.autoBtn.setText("重新播放");
				ctr.autoBtn.setActionCommand("Replay");
				return;
			}
			ctr.next();
		}
		
	}
	
	public void autoPlay(){
		this.autoBtn.setText("暫停播放");
		this.autoBtn.setActionCommand("PausePlay");
		this.autoPlayTimer = new Timer();
		AutoPlayTask task = new AutoPlayTask();
		task.setCtr(this);
		autoPlayTimer.schedule(task, 0, 1500);
	}
	
	public void pausePlay(){
		this.autoBtn.setText("自動播放");
		this.autoBtn.setActionCommand("AutoPlay");
		autoPlayTimer.cancel();
	}
	
	public void replay(){
		this.roundLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"0"+"</font> 手</html>");
		this.moveLb.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>");
		this.autoBtn.setText("重新播放");
		this.autoBtn.setActionCommand("Replay");
	}
	
	/**
	 * 顛倒棋盤<br>
	 * 只更改視覺上的View和ChessColorPosition變數
	 */
	public void inverseChessBoard(){
		/* 視覺顛倒 */
		Chess[] chess = this.chessBoard.getAllChess();
		for(int i=0;i<32;i++){
			if(chess[i].getChessLoc().x != -1){
				if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
					chess[i].setLocation(this.chessMap.getChessLoc(new Point(chess[i].getChessLoc().x,chess[i].getChessLoc().y)));
				}else if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
					chess[i].setLocation(this.chessMap.getChessLoc(new Point(chess[i].getChessLoc().x,9-chess[i].getChessLoc().y)));
				}
			}
		}
		/* 邊框那顆棋子也要做修改 */
		if(round != 1){
			if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
				Point borderPoint = new Point(previousMoveBorder.getChessLoc().x,previousMoveBorder.getChessLoc().y);
				this.previousMoveBorder.setLocation(this.chessMap.getChessLoc(borderPoint));
			}else if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
				Point borderPoint = new Point(previousMoveBorder.getChessLoc().x,9-previousMoveBorder.getChessLoc().y);
				this.previousMoveBorder.setLocation(this.chessMap.getChessLoc(borderPoint));
			}
		}
		
		if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
			this.colorPosition = ChessColorPosition.RedUpBlackDown;
		}else if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
			this.colorPosition = ChessColorPosition.RedDownBlackUp;
		}
	}
	
	/**
	 * 如果棋子沒有死亡，檢查是否顛倒後，依照傳入之p更改其view位置
	 * @param c 欲修改位置之棋子
	 * @param p 欲移動到之位置
	 */
	public void changeChessViewPosition(Chess c,Point p){
		/* 顛倒狀態做視覺顛倒 */
		if(c.getChessLoc().x != -1){
			if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
				c.setLocation(this.chessMap.getChessLoc(new Point(p.x,9-p.y)));
			}else if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
				c.setLocation(this.chessMap.getChessLoc(p));
			}
		}
	}
	
	/**
	 * 回傳目前是否有顛倒棋盤
	 * @return boolean 目前是否顛倒棋盤(紅上黑下)
	 */
	public boolean isInverse(){
		if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 重置棋盤顛倒變數為預設
	 */
	public void resetInverse(){
		this.colorPosition = ChessColorPosition.RedDownBlackUp;
	}
	
}

