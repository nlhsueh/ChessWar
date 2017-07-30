package ObserverData;

import java.awt.Point;

import ChessGame.Chess;

/**
 * 用來回傳我方AI移動的資料結構類別<br>
 * 撰寫者只需在實作nextMove時，建立一個此物件回傳即可<br>
 * 在建立時請以建構子帶入：<br>
 * 1.Chess target(欲移動的棋子)<br>
 * 2.Point loc(欲移動到的位置座標)
 */
public class MyMove {
	
	private Chess target; // 選擇移動的棋子
	private Point loc; // 移動後的座標
	
	/**
	 * 接收欲移動的Chess，與欲移動的Chess移動之後的座標，並set
	 * @param target 欲移動的棋子
	 * @param loc 欲移動到的位置座標
	 */
	public MyMove(Chess target, Point loc){
		this.target = target ;
		this.loc = loc ;
	}
	
	/**
	 * 取得傳送的棋子物件
	 * @return AI決定移動的棋子
	 */
	public Chess getChess(){
		return this.target;
	}
	
	/**
	 * 取得決定移動之後的棋子座標
	 * @return AI決定該顆棋子要移動到的位子座標
	 */
	public Point getTargetLoc(){
		return this.loc;
	}
}
