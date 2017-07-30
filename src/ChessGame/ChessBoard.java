package ChessGame;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import FrameView.MainFrame;

/**
 * 棋盤 class
 * 包含一個ChessMap負責建構棋盤座標系
 * 與建構棋盤的View、創造Chess並擺到正確的位置上
 */
public class ChessBoard extends JLayeredPane implements Serializable{
	
	final static private Point[] chessInitPos = {
			new Point(4,9), new Point(3,9), new Point(5,9), new Point(2,9), new Point(6,9), new Point(0,9),
			new Point(8,9), new Point(1,9), new Point(7,9), new Point(1,7), new Point(7,7), new Point(0,6),
			new Point(2,6), new Point(4,6), new Point(6,6), new Point(8,6), new Point(4,0), new Point(3,0), 
			new Point(5,0), new Point(2,0), new Point(6,0), new Point(0,0), new Point(8,0), new Point(1,0), 
			new Point(7,0), new Point(1,2), new Point(7,2), new Point(0,3), new Point(2,3), new Point(4,3),
			new Point(6,3), new Point(8,3), 
	};
	private static final long serialVersionUID = 1L;
	private static final int X_MAX = 9; // 棋盤座標X軸
	private static final int Y_MAX = 10; // 棋盤座標Y軸
	
	private JPanel boardPanel = new JPanel();
	private JLayeredPane chessPanel = new JLayeredPane();
	private ChessMap map;
	private Chess[] chess;
	
	public ChessBoard(){
		this.map = new ChessMap(new Point(0, 20), X_MAX, Y_MAX, 58, 54);
		this.chess = createChess();
		
		this.setSize(550,565);
		this.setLocation(187, 75);
		this.setOpaque(false);
		this.setLayout(null);
		
		this.setBackground();
		this.add(this.boardPanel,new Integer(1));
		this.add(this.chessPanel,new Integer(2));
	}
	
	/**
	 * 設定棋盤的背景圖
	 */
	private void setBackground(){
		this.boardPanel.setSize(550, 565);
		this.boardPanel.setLayout(new GridLayout(1, 0, 0, 0));
		// Background Image Set
		ImageIcon img = new ImageIcon(MainFrame.class.getClass().getResource("/img/chessboard.jpg"));
		img = (new ImageIcon(img.getImage().getScaledInstance(550, 565, Image.SCALE_SMOOTH)));
		this.boardPanel.add(new JLabel(img));
	}
	
	/**
	 * 建立Chess陣列，並初始化其值後回傳
	 * 索引值分別設定如下：
	 * 紅軍：帥 1 仕 2 仕 3 相 4 相 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16
	 * 黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32
	 * @return chess[]
	 */
	private Chess[] createChess() {
		this.chessPanel.setSize(780, 740);
		this.chessPanel.setLocation(15, -10);
		this.chessPanel.setOpaque(false);
		this.chessPanel.setLayout(null);
		
		Chess[] chess = new Chess[32];

		for (int i = 0; i < 32; i++) {

			if (i < 16)
				chess[i] = new Chess(Chess.chessName[i],i+1, true, ChessSide.RED,ChessBoard.chessInitPos[i],this.chessPanel);
			else
				chess[i] = new Chess(Chess.chessName[i],i+1, true, ChessSide.BLACK,ChessBoard.chessInitPos[i],this.chessPanel);

			chess[i].setBorder(null);
			chess[i].setOpaque(false);
			chess[i].setContentAreaFilled(false);
			
			chess[i].setLocation(this.map.getChessLoc(chess[i].getChessLoc()));
			this.chessPanel.add(chess[i], new Integer(400));

		}

		return chess;
	}
	
	/**
	 * 整理棋盤，將原本的棋子去除，並重新create新的棋子，並擺到正確的位置
	 */
	public void resetChess(){
		this.chessPanel.removeAll();
		for(int i=0;i<32;i++){
			this.chess[i] = null ;
		}
		this.chess = createChess();
	}
	
	/**
	 * 取得棋盤上的Chess陣列
	 * @return Chess[]
	 */
	public Chess[] getAllChess(){
		return this.chess;
	}
	
	/**
	 * 藉由索引值取得特定棋子
	 * @param index 欲取得棋子的index
	 * @return Chess[]
	 */
	public Chess getChess(int index){
		return this.chess[index];
	}
	
	/**
	 * 取得放置棋子的LayeredPanel
	 * @return JLayeredPane
	 */
	public JLayeredPane getChessPanel(){
		return this.chessPanel;
	}
	
	/**
	 * 取得棋盤上的ChessMap
	 * @return ChessMap
	 */
	public ChessMap getChessMap(){
		return this.map;
	}

}
