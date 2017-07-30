package ChessGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.AbstractBorder;

import FrameView.MainFrame;
import ObserverData.MyMove;
import ObserverData.DragData;

/**
 * 棋子 class<br>
 * 記錄棋子的基本資訊與圖片設定，並藉由繼承JButton進行View呈現<br>
 * 使用者在撰寫時只需藉由get method來取得棋子的相關資訊即可
 */
public class Chess extends JButton implements Serializable, Observer{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 55;
	private static int HEIGHT =55;
	
	/**
	 *  依照索引表記錄棋子名稱的陣列<br>
	 * 	索引值分別設定如下：<br>
	 *  紅軍：帥 1 仕 2 仕 3 相 4 相 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16<br>
	 *  黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32<br>
	 */
	final static public String[] chessName = {
			"帥","仕","仕","相","相","俥","俥","傌","傌","炮","炮","兵","兵","兵","兵","兵",
			"將","士","士","象","象","車","車","馬","馬","砲","砲","卒","卒","卒","卒","卒"
	};
	
	private String name;
	private ChessSide side ;
	private boolean live;
	private int index ;
	private Point loc;
//	public int weight; // 暗棋規則使用
	private JLayeredPane chessPanel ;
	
	/* 建立新棋子時使用建構子 */
	public Chess(String name,int index, boolean live, ChessSide side, Point loc, JLayeredPane cp){
		this.setOpaque(false);
		this.setBorder(null);
		this.setLocation(0, 0);
		this.setContentAreaFilled(false);
		this.setSize(WIDTH, HEIGHT);
		
		this.name = new String(name);
		this.side = side;
		this.live = live ;
		this.index = index;
		this.loc = new Point(loc.x,loc.y);
		
		this.setDefaultImage();
		this.chessPanel = cp ;
	}
	
	/* 重播時移動軌跡使用 */
	public Chess(Point p){
		this.setOpaque(false);
		this.setBorder(null);
		this.setLocation(0, 0);
		this.setContentAreaFilled(false);
		this.setSize(WIDTH, HEIGHT);
		this.setBorder(new DashedBorder());
		this.loc = new Point(p);
	}
	
	/* 顛倒棋盤時使用 */
	public Chess(String name,int index, boolean live, ChessSide side, Point loc){
		this.name = new String(name);
		this.side = side;
		this.live = live ;
		this.index = index;
		this.loc = new Point(loc.x,loc.y);
	}
	
	/* 撰寫AI時複製使用 */
	public Chess(Chess c){
		this.name = new String(c.name);
		this.side = c.side;
		this.live = c.live ;
		this.index = c.index;
		this.loc = new Point(c.loc.x,c.loc.y);
	}
	
	/* dotted border */
	@SuppressWarnings("serial")
	private class DashedBorder extends AbstractBorder {
	    @Override
	    public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
	        Graphics2D gg = (Graphics2D) g;
	        gg.setColor(Color.BLUE);
	        gg.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1}, 0));
	        gg.drawRect(x, y, w - 1, h - 1);
	    }
	}
	
	/**
	 * 回傳Chess的中文名字
	 * @return 回傳這個棋子的中文名稱
	 */
	public String getChessName(){
		return new String(this.name);
	}
	
	/**
	 * 回傳Chess的index
	 * @return 回傳這個棋子的index（請參照索引表）
	 */
	public int getChessIndex(){
		int x = this.index;
		return x;
	}
	
	/**
	 * 複製Chess的座標軸位置並回傳（請參考文件之座標說明）
	 * @return 回傳這個棋子在棋盤上的座標位置
	 */
	public Point getChessLoc(){
		return new Point(this.loc.x,this.loc.y);
	}
	
	/**
	 * 設定Chess的座標軸位置（請參考文件之座標說明）
	 * @param loc 欲設定的位置
	 */
	public void setChessLoc(Point loc){
		this.loc = new Point(loc) ;
	}
	
	/**
	 * 將Chess之live設為false(死亡)
	 */
	public void setChessDie(){
		this.live = false;
	}
	
	/**
	 * 若Chess為紅棋回傳Chess.RED，為黑棋則回傳Chess.BLACK
	 * @return 回傳這顆棋子的顏色，Chess.RED為紅棋、Chess.BLACK為黑棋
	 */
	public ChessSide getChessSide(){
		if(this.side == ChessSide.BLACK)
			return ChessSide.BLACK;
		else
			return ChessSide.RED;
	}
	
	/**
	 * 回傳Chess的存活狀況，若存活，回傳true，否則回傳false
	 * @return 回傳這顆棋子的存活狀況，true為存活、false為死亡
	 */
	public boolean isAlive(){
		if(this.live == true)
			return true;
		else
			return false;
	}
	
	/**
	 * 藉由索引值加上棋子正確的圖片<br>
	 * 索引值分別設定如下：<br>
	 * 紅軍：帥 1 仕 2 仕 3 相 4 相 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16<br>
	 * 黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32<br>
	 */
	private void setDefaultImage() {

		ImageIcon icon;

		switch (this.index) {

		case 1:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/1.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 2:
		case 3:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/2.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 4:
		case 5:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/3.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 6:
		case 7:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/4.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 8:
		case 9:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/5.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 10:
		case 11:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/6.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/7.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 17:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/8.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 18:
		case 19:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/9.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 20:
		case 21:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/10.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 22:
		case 23:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/11.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 24:
		case 25:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/12.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 26:
		case 27:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/13.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/14.png"));
			icon = (new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			this.setIcon(icon);
			this.setPressedIcon(icon);
			break;
		}

	}
	
	/**
	 * Observer呼叫<br>
	 * 1.當滑鼠拖移移動經判斷合法後，由Observer GameController發出通知，並告知棋子進行更新座標<br>
	 * 2.當棋子被吃或違規時，修改棋子的存活狀態<br>
	 * 3.當棋子移動或被吃時，修改棋子的座標
	 */
	@Override
	final public void update(Observable o, Object arg) {
		
		if(arg instanceof DragData){
			if(((DragData) arg).c == this){
				this.chessPanel.moveToFront(this);
				this.setLocation(((DragData) arg).x, ((DragData) arg).y);
			}
		}else if(arg instanceof Integer){ /* 違規或吃棋時致死使用 */
			if(((Integer) arg).intValue() == this.index){
				this.live = false ;
			}
		}else if(arg instanceof MyMove){
			if(((MyMove) arg).getChess().getChessIndex() == this.index){
				MyMove data = (MyMove) arg ;
				/* 棋子被吃，將座標設為(-1,-1) */
				if(data.getTargetLoc().x == -1 && data.getTargetLoc().y == -1){
					this.loc.setLocation(-1, -1);
					this.live = false;
				}else{
				/* 修改棋子座標 */
					this.loc = new Point(data.getTargetLoc().x,data.getTargetLoc().y);
				}
			}
		}
		
	}


}
