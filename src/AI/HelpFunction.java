package AI;

import java.awt.Point;

import ChessGame.Chess;
import ChessGame.ChessSide;
import ObserverData.MyMove;

public class HelpFunction {
	
	/**
	 * 檢查遊戲是否已經結束
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @return 遊戲是否已經結束
	 */
	public static boolean isGameOver(Chess[] chess){
		if ((chess[0].isAlive() == false) || (chess[16].isAlive() == false)) {
			return true;
		}
		return false;
	}

	/**
	 * 檢查棋子是否能夠移動到該位置，包含吃棋也算可以移動
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param c 欲檢查棋子
	 * @param p 欲移動到的位置
	 * @return 是否可以移動或吃棋
	 */
	public static boolean isCouldMove(Chess[]chess, Chess c, Point p) {

		if (c.getChessLoc().equals(p))
			return false;
		Point locP = c.getChessLoc();
		int locX = locP.x;
		int locY = locP.y;
		switch (c.getChessIndex()) {
		
		case 1:
			if ((p.x >= 3 && p.y >= 7) && (p.x <= 5 && p.y <= 9)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			// 王衝王
			}else if(p.x == locX && getChess(chess, p).getChessIndex() == 17){
				if (calObstacle(chess, c, p) == 0) {
					return true;
				}
			}
			break;
		case 2:
		case 3:
			if ((p.x >= 3 && p.y >= 7) && (p.x <= 5 && p.y <= 9)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 1))) {
					return true;
				}
			}
			break;
		case 4:
		case 5:
			if ((p.x >= 0 && p.y >= 5) && (p.x <= 8 && p.y <= 9)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(2, 2))) {
					if(calObstacleForElephant(chess, c, p) == 0){
						return true;
					}
				}
			}
			break;
		case 6:
		case 7:
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(chess, c, p) == 0) {
					return true;
				}
			}
			break;
		case 8:
		case 9:
			if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 2))) {
				if(calObstacleForHorse(chess, c, p) == 0){
					return true;
				}
			}
			break;
		case 10:
		case 11:
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(chess, c, p) == 1) {
					if (hasChess(chess, p)) {
						return true;
					}
				} else if (calObstacle(chess, c, p) == 0) {
					if (!hasChess(chess, p)) {
						return true;
					}
				}
			}
			break;
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			if ((locY <= 4) && (p.y <= locY)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			} else {
				if ((locX == p.x) && (p.y < locY) && (calDistance(locP, p) == 1)) {
					return true;
				}
			}
			break;
		case 17:
			// 九格內
			if ((p.x >= 3 && p.y >= 0) && (p.x <= 5 && p.y <= 2)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			// 王衝王
			}else if(p.x == locX && getChess(chess, p).getChessIndex() == 1){
				if (calObstacle(chess, c, p) == 0) {
					return true;
				}
			}
			break;
		case 18:
		case 19:
			if ((p.x >= 3 && p.y >= 0) && (p.x <= 5 && p.y <= 2)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 1))) {
					return true;
				}
			}
			break;
		case 20:
		case 21:
			if ((p.x >= 0 && p.y >= 0) && (p.x <= 8 && p.y <= 4)) {
				if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(2, 2))) {
					if(calObstacleForElephant(chess, c, p) == 0){
						return true;
					}
				}
			}
			break;
		case 22:
		case 23:
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(chess, c, p) == 0) {
					return true;
				}
			}
			break;
		case 24:
		case 25:
			if (calDistance(locP, p) == calDistance(new Point(0, 0), new Point(1, 2))) {
				if(calObstacleForHorse(chess, c, p) == 0){
					return true;
				}
			}
			break;
		case 26:
		case 27:
			if ((locX == p.x) || (locY == p.y)) {
				if (calObstacle(chess, c, p) == 1) {
					if (hasChess(chess, p)) {
						return true;
					}
				} else if (calObstacle(chess, c, p) == 0) {
					if (!hasChess(chess, p)) {
						return true;
					}
				}
			}
			break;
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
			if ((locY >= 5) && (p.y >= locY)) {
				if (calDistance(locP, p) == 1) {
					return true;
				}
			} else {
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
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param c 欲移動棋子
	 * @param p 欲移動至的位置
	 * @return 中間的棋子數量
	 */
	public static int calObstacle(Chess[]chess, Chess c, Point p) {
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
					if (hasChess(chess, new Point(i, j)))
						total++;
				}
			}
		} else if (dx >= 0 && dy < 0) {
			for (int i = x1; i <= x2; i++) {
				for (int j = y2; j <= y1; j++) {
					if (hasChess(chess, new Point(i, j)))
						total++;
				}
			}
		} else if (dx < 0 && dy >= 0) {
			for (int i = x2; i <= x1; i++) {
				for (int j = y1; j <= y2; j++) {
					if (hasChess(chess, new Point(i, j)))
						total++;
				}
			}
		} else if (dx < 0 && dy < 0) {
			for (int i = x2; i <= x1; i++) {
				for (int j = y2; j <= y1; j++) {
					if (hasChess(chess, new Point(i, j)))
						total++;
				}
			}
		}
		if (hasChess(chess, p))
			total--;
		total--;
		return total;

	}
	
	/**
	 * 計算“象”棋子移動時所經過的棋子數量，不包含自己與目標位置的棋子（拐象腳規則）
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param c 欲移動棋子
	 * @param p 欲移動至的位置
	 * @return 障礙物數量
	 */
	
	public static int calObstacleForElephant(Chess[]chess, Chess c, Point p){
		int total = 0;
		int x1 = c.getChessLoc().x;
		int y1 = c.getChessLoc().y;
		int x2 = p.x;
		int y2 = p.y;
		// 右上
		if(x1<x2 && y1>y2){
			if(hasChess(chess, new Point(x2-1,y1-1))){
				total++;
			}
		// 右下
		}else if(x1<x2 && y2>y1){
			if(hasChess(chess, new Point(x2-1,y2-1))){
				total++;
			}
		// 左上
		}else if(x1>x2 && y1>y2){
			if(hasChess(chess, new Point(x1-1,y1-1))){
				total++;
			}
		// 左下
		}else if(x1>x2 && y1<y2){
			if(hasChess(chess, new Point(x1-1,y2-1))){
				total++;
			}
		}
		
		return total ;
	}
	
	/**
	 * 計算“馬”移動時所經過的棋子數量，不包含自己與目標位置的棋子（拐馬腳規則）
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param c 欲移動棋子
	 * @param p 欲移動至的位置
	 * @return 障礙物數量
	 */
	
	public static int calObstacleForHorse(Chess[]chess, Chess c, Point p){
		int total = 0;
		int x1 = c.getChessLoc().x;
		int y1 = c.getChessLoc().y;
		int x2 = p.x;
		int y2 = p.y;
		
		// 往上
		if(y1-y2 == 2){
			if(hasChess(chess, new Point(x1,y1-1))){
				total++;
			}
		// 往下
		}else if(y2-y1 == 2){
			if(hasChess(chess, new Point(x1,y1+1))){
				total++;
			}
		// 往右
		}else if(x2-x1 == 2){
			if(hasChess(chess, new Point(x1+1,y1))){
				total++;
			}
		// 往左
		}else if(x1-x2 == 2){
			if(hasChess(chess, new Point(x1-1,y1))){
				total++;
			}
		}
		
		return total ;
	}
	
	/**
	 * 檢查該座標是否有棋子存在
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param p 欲檢查位置座標
	 * @return boolean
	 */
	public static boolean hasChess(Chess[] chess, Point p) {

		for (int i = 0; i < 32; i++) {
			if ( (chess[i].getChessLoc().x == p.x) && (chess[i].getChessLoc().y == p.y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 透過座標取得該棋子
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param p 欲取得的位置，請先用hasChess檢查該位置確定有棋子
	 * @return 回傳該Chess，若不存在回傳null
	 */
	public static Chess getChess(Chess chess[], Point p) {

		for (int i = 0; i < 32; i++) {
			if ( (chess[i].getChessLoc().x == p.x) && (chess[i].getChessLoc().y == p.y) ) {
				return chess[i];
			}
		}
		return null;
	}
	
	/**
	 * 檢查該座標是否有我方棋子存在
	 * @param chess 目前棋盤上的所有棋子狀態
	 * @param p 欲取得的位置，請先用hasChess檢查該位置確定有棋子
	 * @param side 我方棋子的顏色
	 * @return 回傳該Chess，若不存在回傳null
	 */
	public static boolean hasMyChess(Chess chess[], Point p, ChessSide side) {

		for (int i = 0; i < 32; i++) {
			if ( (chess[i].getChessLoc().x == p.x) && (chess[i].getChessLoc().y == p.y) && chess[i].getChessSide() == side) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 計算兩點之間的距離
	 * @param p1 Point 點1
	 * @param p2 Point 點2
	 * @return 回傳兩點距離
	 */
	public static double calDistance(Point p1, Point p2) {

		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));

	}
	
	/**
	 * 確認此位置是否存在對方的將或帥
	 * @param chess Chess[] 目前盤面
	 * @param p Point 欲吃棋之位置
	 * @param ChessSide 我方的顏色
	 * @return 回傳是否可吃對方將帥
	 */
	public static boolean isExistKing(Chess[] chess, Point p, ChessSide side) {
		
		if(HelpFunction.hasChess(chess, p)){
			Chess eatedChess = HelpFunction.getChess(chess, p);
			if(side == ChessSide.BLACK){
				if(eatedChess.getChessName().equals("帥")){
					return true;
				}else{
					return false;
				}
			}else{
				if(eatedChess.getChessName().equals("將")){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}

	}
	
}
