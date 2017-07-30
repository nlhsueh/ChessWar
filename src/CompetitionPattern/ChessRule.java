package CompetitionPattern;

import java.awt.Point;

import ChessGame.Chess;
import ObserverData.MyMove;

/**
 * 提供方法回傳棋子座標、判斷該座標有無棋子、確定有無違規等方法
 * 索引值分別設定如下：
 * 紅軍：帥 1 仕 2 仕 3 相 4 相 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16
 * 黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32
 */

public class ChessRule {
	
	private Chess[] chess ;
	private GameModel gameModel;
	
	public ChessRule(Chess[] chess, GameModel gameModel){
		this.chess = chess ;
		this.gameModel = gameModel;
	}
	
	/**
	 * 透過座標取得該棋子
	 * @param p 欲取得棋子得座標點
	 * @return Chess 若該點有棋子，則回傳該Chess，若無，回傳null
	 */
	public Chess getChess(Point p) {

		for (int i = 0; i < 32; i++) {
			if ( (this.chess[i].getChessLoc().x == p.x) && (this.chess[i].getChessLoc().y == p.y) ) {
				return chess[i];
			}
		}
		return null;
	}
	
	/**
	 * 進行吃子的動作，會將被吃的棋子座標設為(-1,-1)且死亡並隱藏
	 * @param p 被吃棋子的座標位置
	 */
	public void eatChess(Point p) {
		Chess c = getChess(p);
		this.gameModel.setChanged();
		this.gameModel.notifyObservers(new Integer(c.getChessIndex()));
		this.gameModel.setChanged();
		this.gameModel.notifyObservers(new MyMove(c,new Point(-1, -1)));
		c.setVisible(false);
	}
	
	/**
	 * 檢查該座標是否有棋子存在
	 * @param p 欲檢查的座標位置
	 * @return boolean
	 */
	public boolean hasChess(Point p) {
		for (int i = 0; i < 32; i++) {
			if ( (this.chess[i].getChessLoc().x == p.x) && (this.chess[i].getChessLoc().y == p.y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 計算兩點之間的距離
	 * @param p1 第一個點的Point座標
	 * @param p2 第二個點的Point座標
	 * @return double
	 */
	public static double calDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
	
	/**
	 * 檢查棋子是否能夠移動到該位置，包含吃也算可以移動
	 * @param c Chess
	 * @param p Point
	 * @return boolean
	 */
	public boolean isCouldMove(Chess c, Point p) {

		if (c.getChessLoc().equals(p))
			return false;
		
		int locX = c.getChessLoc().x ;
		int locY = c.getChessLoc().y ;
		Point locP = new Point(locX,locY) ;
		
		switch (c.getChessIndex()) {
		
		/* 帥 */
		case 1:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 3 && p.y >= 7) && (p.x <= 5 && p.y <= 9)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			/* 檢查是否王衝王 */
			}else if(p.x == locX && getChess(p).getChessIndex() == 17){
				if (calObstacle(c, p) == 0) {
					return true;
				}
			}
			break;
		/* 仕 */
		case 2:
		case 3:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 3 && p.y >= 7) && (p.x <= 5 && p.y <= 9)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 1))) {
					return true;
				}
			}
			break;
		/* 相 */
		case 4:
		case 5:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 0 && p.y >= 5) && (p.x <= 8 && p.y <= 9)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(2, 2))) {
					/* 檢查是否拐象腳 */
					if(calObstacleForElephant(c, p) == 0){
						return true;
					}
				}
			}
			break;
		/* 俥 */
		case 6:
		case 7:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(c, p) == 0) {
					return true;
				}
			}
			break;
		/* 傌 */
		case 8:
		case 9:
			/* 檢查移動步數與移動範圍是否合法 */
			if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 2))) {
				/* 檢查是否拐馬腳 */
				if(calObstacleForHorse(c, p) == 0){
					return true;
				}
			}
			break;
		/* 炮 */
		case 10:
		case 11:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(c, p) == 1) {
					if (hasChess(p)) {
						return true;
					}
				} else if (calObstacle(c, p) == 0) {
					if (!hasChess(p)) {
						return true;
					}
				}
			}
			break;
		/* 兵 */
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			/* 檢查移動步數與移動範圍是否合法---過河後 */
			if ((locY <= 4) && (p.y <= locY)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			}else{ /* 檢查移動步數與移動範圍是否合法---過河前 */
				if ((locX == p.x) && (p.y < locY) && (calDistance(locP, p) == 1)) {
					return true;
				}
			}
			break;
		/* 將 */
		case 17:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 3 && p.y >= 0) && (p.x <= 5 && p.y <= 2)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
				/* 檢查是否王衝王 */
			}else if(p.x == locX && getChess(p).getChessIndex() == 1){
				if (calObstacle(c, p) == 0) {
					return true;
				}
			}
			break;
		/* 士 */
		case 18:
		case 19:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 3 && p.y >= 0) && (p.x <= 5 && p.y <= 2)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 1))) {
					return true;
				}
			}
			break;
		/* 象 */
		case 20:
		case 21:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((p.x >= 0 && p.y >= 0) && (p.x <= 8 && p.y <= 4)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(2, 2))) {
					/* 檢查是否拐象腳 */
					if(calObstacleForElephant(c, p) == 0){
						return true;
					}
				}
			}
			break;
		/* 車 */ 
		case 22:
		case 23:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(c, p) == 0) {
					return true;
				}
			}
			break;
		/* 馬 */
		case 24:
		case 25:
			/* 檢查移動步數與移動範圍是否合法 */
			if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 2))) {
				/* 檢查是否拐馬腳 */
				if(calObstacleForHorse(c, p) == 0){
					return true;
				}
			}
			break;
		/* 砲 */
		case 26:
		case 27:
			/* 檢查移動步數與移動範圍是否合法 */
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(c, p) == 1) {
					if (hasChess(p)) {
						return true;
					}
				} else if (calObstacle(c, p) == 0) {
					if (!hasChess(p)) {
						return true;
					}
				}
			}
			break;
		/* 卒 */
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
			/* 檢查移動步數與移動範圍是否合法---過河後 */
			if ((locY >= 5) && (p.y >= locY)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			}else{ /* 檢查移動步數與移動範圍是否合法---過河前 */
				if ((locX == p.x) && (p.y > locY) && (calDistance(locP, p) == 1)) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	/**
	 * 計算棋子移動時所經過的棋子數量，不包含自己與目標位置的棋子
	 * @param c Chess
	 * @param p Point
	 * @return int
	 */
	public int calObstacle(Chess c, Point p) {
		int total = 0;
		int x1 = c.getChessLoc().x;
		int y1 = c.getChessLoc().y;
		int x2 = p.x;
		int y2 = p.y;
		int dx = x2 - x1;
		int dy = y2 - y1;
		if (dx >= 0 && dy >= 0) {
			for (int i = x1; i <= x2; i++) {
				for (int j = y1; j <= y2; j++) {
					if (hasChess(new Point(i, j)))
						total++;
				}
			}
		} else if (dx >= 0 && dy < 0) {
			for (int i = x1; i <= x2; i++) {
				for (int j = y2; j <= y1; j++) {
					if (hasChess(new Point(i, j)))
						total++;
				}
			}
		} else if (dx < 0 && dy >= 0) {
			for (int i = x2; i <= x1; i++) {
				for (int j = y1; j <= y2; j++) {
					if (hasChess(new Point(i, j)))
						total++;
				}
			}
		} else if (dx < 0 && dy < 0) {
			for (int i = x2; i <= x1; i++) {
				for (int j = y2; j <= y1; j++) {
					if (hasChess(new Point(i, j)))
						total++;
				}
			}
		}
		if (hasChess(p))
			total--;
		total--;
		return total;

	}
	
	/**
	 * 計算“象”棋子移動時所經過的棋子數量，不包含自己與目標位置的棋子（拐象腳規則）
	 * @param c Chess
	 * @param p Point
	 * @return int
	 */
	public int calObstacleForElephant(Chess c, Point p){
		int total = 0;
		int x1 = c.getChessLoc().x;
		int y1 = c.getChessLoc().y;
		int x2 = p.x;
		int y2 = p.y;
		// 右上
		if(x1<x2 && y1>y2){
			if(hasChess(new Point(x2-1,y1-1))){
				total++;
			}
		// 右下
		}else if(x1<x2 && y2>y1){
			if(hasChess(new Point(x2-1,y2-1))){
				total++;
			}
		// 左上
		}else if(x1>x2 && y1>y2){
			if(hasChess(new Point(x1-1,y1-1))){
				total++;
			}
		// 左下
		}else if(x1>x2 && y1<y2){
			if(hasChess(new Point(x1-1,y2-1))){
				total++;
			}
		}
		
		return total ;
	}
	
	/**
	 * 計算“馬”移動時所經過的棋子數量，不包含自己與目標位置的棋子（拐馬腳規則）
	 * @param c 欲進行檢查的Chess（馬）物件
	 * @param p 欲移動之後的座標點
	 * @return int
	 */
	public int calObstacleForHorse(Chess c, Point p){
		int total = 0;
		int x1 = c.getChessLoc().x;
		int y1 = c.getChessLoc().y;
		int x2 = p.x;
		int y2 = p.y;
		
		// 往上
		if(y1-y2 == 2){
			if(hasChess(new Point(x1,y1-1))){
				total++;
			}
		// 往下
		}else if(y2-y1 == 2){
			if(hasChess(new Point(x1,y1+1))){
				total++;
			}
		// 往右
		}else if(x2-x1 == 2){
			if(hasChess(new Point(x1+1,y1))){
				total++;
			}
		// 往左
		}else if(x1-x2 == 2){
			if(hasChess(new Point(x1-1,y1))){
				total++;
			}
		}
		
		return total ;
	}
}
